/*
 * Copyright (C) 2018 The Holodeck B2B Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.holodeckb2b.bdxr.smp;

import com.chasquismessaging.commons.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.holodeckb2b.bdxr.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.datamodel.Identifier;
import org.holodeckb2b.bdxr.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.datamodel.Redirection;
import org.holodeckb2b.bdxr.datamodel.ServiceInformation;
import org.holodeckb2b.bdxr.sml.ISMPLocator;
import org.holodeckb2b.bdxr.sml.SMPLocatorException;
import org.holodeckb2b.bdxr.smp.processing.SMPResultReader;

/**
 * Is a SMP client to request the meta-data about a participant in the network from a SMP server. The SMP server to
 * query is determined using a {@link ISMPLocator} that must be provided when creating the client.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SMPClient {
	private static final Logger	log = LogManager.getLogger(SMPClient.class);

    /**
     * The default timeout for SMP request is 10 seconds
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    /**
     * The location service to use for getting the URL of the SMP
     */
    private ISMPLocator     locator;
    /**
     * The timeout for SMP requests
     */
    private int             timeout;

    /**
     * Create a new <code>LookupCLient</code> that will use the given SMP locator to get the location of the SMP
     * for a participant and uses the default timeout.
     *
     * @param smpLocator		The SMP locator to use
     */
    public SMPClient(final ISMPLocator smpLocator) {
        this(smpLocator, DEFAULT_TIMEOUT);
    }

    /**
     * Create a new <code>LookupCLient</code> that will use the given SMP locator to get the location of the SMP
     * for a participant and use the given timeout for requests.
     *
     * @param smpLocator    The SMP locator to use
     * @param timeout       The timeout to use for SMP requests
     */
    public SMPClient(final ISMPLocator smpLocator, final int timeout) {
        this.locator = smpLocator;
        this.timeout = timeout;
    }

    /**
     * Gets the endpoint meta-data for the given participant, service and process and that supports the request
     * transport profile.
     *
     * @param participantId		Participant's Id
     * @param serviceId			Service Id
     * @param processId			Process Id
     * @param transportProfile	Requested transport profile name
     * @return	The endpoint meta-data if there exists an endpoint for this participant, service and process and which
     * 			supports the requested transport profile, <code>null</code> otherwise.
     * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
     */
    public EndpointInfo getEndpoint(final Identifier participantId,
    								final Identifier serviceId,
    								final Identifier processId,
    								final String     transportProfile) throws SMPQueryException {
        log.debug("Lookup requested; (participant, service, process, transport) = ({},{},{},{})",
                    participantId, serviceId, processId, transportProfile);
        URI smpURL = null;
        try {
            log.debug("Getting URI of SMP handling participant");
            smpURL = locator.locateSMP(participantId);
        } catch (SMPLocatorException ex) {
            log.error("An error occurred in locating the SMP server for participant {}."
                     + "\n\tDetails: {}\n\tCaused by: {}", participantId, ex.getMessage(),
                                                          Utils.getExceptionTrace(ex));
            throw new SMPQueryException("Could not locate the SMP server for participant", ex);
        }

        log.debug("Query the SMP at {}", smpURL.toString());
        // Build the query string for getting the service meta-data directly
        URI queryURL = smpURL.resolve(String.format("/%s/services/%s", participantId.getURLEncoded(),
                                                                       serviceId.getURLEncoded()));
        ServiceInformation serviceInfo = null;
        try {
            int redirectCount = 0;
            do {
            	log.debug("Getting service meta-data from SMP (URL={})", queryURL.toString());
            	URLConnection urlConnection = queryURL.toURL().openConnection();
	            urlConnection.setConnectTimeout(timeout);
	            urlConnection.setReadTimeout(timeout);
	            ISMPQueryResult response = null;
	            try (InputStream is = urlConnection.getInputStream()) {
	                log.debug("Connected to SMP, handle response");
	                response = SMPResultReader.handleResponse(is);
	            }
	            if (response instanceof Redirection) {
	                log.error("Received redirection response");
	                queryURL = ((Redirection) response).getNewSMPURL();
	                redirectCount++;
	            } else if (response instanceof ServiceInformation) 
	            	serviceInfo = (ServiceInformation) response;
	            else {
	            	log.error("Received unknown response from SMP server!");
	            	throw new SMPQueryException("Unknown response received");
	            }	            	
            } while (serviceInfo == null && redirectCount <= 1);
        } catch (IOException connectionError) {
            log.error("SMP connection error! Details: {}", connectionError);
            throw new SMPQueryException("Error while connecting to the SMP server", connectionError);
        }
        // Check if we got a ServiceInformation response or ran out of redirections
        if (serviceInfo == null) {
        	log.error("SMP query failed due to too many redirections!");
        	throw new SMPQueryException("Too many redirections");
        }
        log.debug("Retrieved service information from SMP server, search for requested process and transport");
        // We return the first endpoint that matches to the request
        EndpointInfo result = null;
        for(ProcessInfo pi : serviceInfo.getProcessInformation()) {
            if (pi.supportsProcess(processId)) {
                Optional<EndpointInfo> findEndPoint = pi.getEndpoints().parallelStream()
                                                        .filter(ep -> transportProfile.equals(ep.getTransportProfile()))
                                                        .findFirst();
                if (findEndPoint.isPresent()) {
                    result = findEndPoint.get();
                    break;
                }
            }
        }
        log.debug("Found {}endpoint for request; (participant, service, process, transport) = ({},{},{},{})",
                  result == null ? "no " : "", participantId, serviceId, processId, transportProfile);

        return result;
    }
}
