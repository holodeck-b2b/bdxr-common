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
package org.holodeckb2b.bdxr.impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.holodeckb2b.bdxr.api.ISMPClient;
import org.holodeckb2b.bdxr.api.ISMPResponseConnection;
import org.holodeckb2b.bdxr.api.SMPClientBuilder;
import org.holodeckb2b.bdxr.api.SMPLocatorException;
import org.holodeckb2b.bdxr.api.SMPQueryException;
import org.holodeckb2b.bdxr.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.datamodel.Identifier;
import org.holodeckb2b.bdxr.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.datamodel.Redirection;
import org.holodeckb2b.bdxr.datamodel.ServiceInformation;
import org.holodeckb2b.bdxr.utils.Utils;

/**
 * Is the implementation of {@link ISMPClient} and controls the process of requesting the meta-data about a 
 * participant in the network from a SMP server. 
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SMPClient implements ISMPClient {
	private static final Logger	log = LogManager.getLogger(SMPClient.class);

    /**
     * The configuration to be used by this client
     */
    private SMPClientConfig 	configuration;

    /**
     * Creates a new <code>LookupCLient</code> using the given configuration. It is recommended to use the {@link 
     * SMPClientBuilder} for creating new instance of the SMP client to ensure a decoupling of the using classes with
     * the actual implementation classes.
     * 
     * @param config	The configuration to use for the new client;
     */
    public SMPClient(final SMPClientConfig config) {
        this.configuration = config;
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
    @Override
	public EndpointInfo getEndpoint(final Identifier participantId,
    								final Identifier serviceId,
    								final Identifier processId,
    								final String     transportProfile) throws SMPQueryException {
        log.debug("Lookup requested; (participant, service, process, transport) = ({},{},{}, {})",
                participantId, serviceId, processId, transportProfile);
        // First get all endpoints for the participant, serviceId and processId, then filter the result
        
    	final List<EndpointInfo> allEndpoints = getEndpoints(participantId, serviceId, processId);

    	Optional<EndpointInfo> findEndPoint = allEndpoints.parallelStream()
									                .filter(ep -> transportProfile.equals(ep.getTransportProfile()))
									                .findFirst();
    	if (findEndPoint.isPresent()) {
    		log.debug("Found endpoint for request; (participant, service, process, transport) = ({},{},{},{})", 
    				  participantId, serviceId, processId, transportProfile);
    		return findEndPoint.get();
    	} else {
    		log.debug("No endpoint found for request; (participant, service, process, transport) = ({},{},{},{})", 
  				  	  participantId, serviceId, processId, transportProfile);
    		return null;
    	}
    }
	
    /**
     * Gets the meta-data of all endpoints for the given participant, service and process.
     *
     * @param participantId		Participant's Id
     * @param serviceId			Service Id
     * @param processId			Process Id
     * @return	The endpoint meta-data if there exists an endpoint for this participant, service and process and which
     * 			supports the requested transport profile, <code>null</code> otherwise.
     * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
     */
    @Override
	public List<EndpointInfo> getEndpoints(final Identifier participantId,
									    		 final Identifier serviceId,
									    		 final Identifier processId) throws SMPQueryException {
        log.debug("Lookup requested; (participant, service, process) = ({},{},{})",
                    participantId, serviceId, processId);
        URI smpURL = null;
        try {
            log.debug("Getting URI of SMP handling participant");
            smpURL = configuration.getSMPLocator().locateSMP(participantId);
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
        try {
            int redirectCount = 0;
            do {
            	ISMPQueryResult response = null;            	
            	log.debug("Executing SMP query using executor: {}", 
            			  configuration.getRequestExecutor().getClass().getName());	            
            	ISMPResponseConnection connection = configuration.getRequestExecutor().executeRequest(queryURL);
                response = new SMPResultReader(configuration).handleResponse(connection.getInputStream());
                connection.close();
                Redirection redirect = null;
	            if (response instanceof Redirection) {
	            	redirect = (Redirection) response;
	            } else if (response instanceof ServiceInformation) {
	            	log.debug("Retrieved service information from SMP server, search for requested process and transport");
	            	List<ProcessInfo> procInfo = ((ServiceInformation) response).getProcessInformation()
																	  .parallelStream()
																	  .filter(pi -> pi.supportsProcess(processId))
																	  .collect(Collectors.toList());
	            	// The service info could include a generic process meta-data element and one specific to this
	            	// process, so if multiple are found filter out the specific one
	            	if (procInfo.size() > 1)	            		
	            		procInfo = procInfo.parallelStream().filter(pi -> pi.getProcessIds().contains(processId))
	            										    .collect(Collectors.toList());	            		
	            	if (procInfo.size() != 1) {
	            		log.error("SMP result contains inconsistent meta-data for process");
	            		throw new SMPQueryException("Inconsistent meta-data");
	            	}
	            	final ProcessInfo pi = procInfo.get(0);
            		if (pi.getRedirection() != null)
            			redirect = pi.getRedirection();
            		else {
            			log.debug("Found {} endpoint(s) for request; (participant, service, process) = ({},{},{})",
            						pi.getEndpoints().size(), participantId, serviceId, processId);
            			return pi.getEndpoints();
            		}
	            } else {
	            	log.error("Received unknown response from SMP server!");
	            	throw new SMPQueryException("Unknown response received");
	            }	
	            if (redirect != null) {
	                log.warn("Received redirection response");	                
	                queryURL = ((Redirection) response).getNewSMPURL();
	                redirectCount++;
	            }
            } while (redirectCount <= 1);
            log.error("SMP query failed due to too many redirections!");
            throw new SMPQueryException("Too many redirections");
        } catch (IOException connectionError) {
            log.error("SMP connection error! Details: {}", connectionError);
            throw new SMPQueryException("Error while connecting to the SMP server", connectionError);
        }
    }
}
