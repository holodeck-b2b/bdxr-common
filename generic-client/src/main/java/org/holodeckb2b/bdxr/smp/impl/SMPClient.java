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
package org.holodeckb2b.bdxr.smp.impl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.holodeckb2b.bdxr.smp.api.ISMPClient;
import org.holodeckb2b.bdxr.smp.api.ISMPResponseConnection;
import org.holodeckb2b.bdxr.smp.api.SMPClientBuilder;
import org.holodeckb2b.bdxr.smp.api.SMPLocatorException;
import org.holodeckb2b.bdxr.smp.api.SMPQueryException;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Redirection;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceInformation;
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
    	return getEndpoint(participantId, null, serviceId, processId, transportProfile);
    }
    
	/**
     * Gets the endpoint's meta-data for the given participant acting in the specified role for the given service and 
     * process and that supports the requested transport profile.
     * <p>NOTE: The role a participant plays in a process was added in the OASIS SMP v2 specification and therefore it 
     * can also only be evaluated if the SMP server supports this version. This implies that when the response is an 
     * older version this criterion cannot be applied and therefore there will be no result to the query.
     *
     * @param participantId		Participant's Id
     * @param role				Role of the participant
     * @param serviceId			Service Id
     * @param processId			Process Id, may be <code>null</code>
     * @param transportProfile	Requested transport profile name
     * @return	The endpoint meta-data if there exists an endpoint for this participant, service and process and which
     * 			supports the requested transport profile, <code>null</code> otherwise.
     * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
     * @since 2.0.0
     */
    @Override
	public EndpointInfo getEndpoint(final Identifier participantId,
									final Identifier role,
		    						final Identifier serviceId,
		    						final Identifier processId,
		    						final String     transportProfile) throws SMPQueryException  {
        if (Utils.isNullOrEmpty(transportProfile))
        	throw new IllegalArgumentException("No transport profile identifier provided");
        
    	log.debug("Lookup requested; (participant, role, service, process, transport) = ({},{},{},{}, {})",
                	participantId, role, serviceId, processId, transportProfile);

        // First get all endpoints for the participant, role, serviceId and processId, then filter the result        
    	final List<EndpointInfo> allEndpoints = getEndpoints(participantId, role, serviceId, processId);

    	Optional<EndpointInfo> findEndPoint = allEndpoints.parallelStream()
									                .filter(ep -> transportProfile.equals(ep.getTransportProfile()))
									                .findFirst();
    	if (findEndPoint.isPresent()) {
    		log.debug("Found endpoint for request; (participant, service, process, transport) = ({},{},{},{},{})", 
    				  participantId, role, serviceId, processId, transportProfile);
    		return findEndPoint.get();
    	} else {
    		log.debug("No endpoint found for request; (participant, service, process, transport) = ({},{},{},{},{})", 
  				  	  participantId, role, serviceId, processId, transportProfile);
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
    	return getEndpoints(participantId, null, serviceId, processId);
    }
    
	/**
	 * Gets the meta-data of all endpoints for the given participant acting in the specified role for the given service 
	 * and process and that supports the requested transport profile.
     * <p>NOTE: The role a participant plays in a process was added in the OASIS SMP v2 specification and therefore it 
     * can also only be evaluated if the SMP server supports this version. This implies that when the response is an 
     * older version this criterion cannot be applied and therefore there will be no result to the query.
	 *
	 * @param participantId		Participant's Id
	 * @param role				Role of the participant
	 * @param serviceId			Service Id
	 * @param processId			Process Id, may be <code>null</code>
	 * @return	The endpoint meta-data if there exist endpoints for this participant, role, service and process,
	 * 			<code>null</code> otherwise.
	 * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
	 * @since 2.0.0
	 */
    @Override
	public List<EndpointInfo> getEndpoints(final Identifier participantId,
										   final Identifier role,
										   final Identifier serviceId,
										   final Identifier processId) throws SMPQueryException  {
        if (participantId == null || serviceId == null)
        	throw new IllegalArgumentException("Missing either participant or service ID argument");
        
    	log.debug("Lookup requested; (participant, service, process) = ({},{},{},{})",
                    participantId, role, serviceId, processId);
        URL smpURL = null;
        try {
            log.debug("Getting URI of SMP handling participant");
            smpURL = configuration.getSMPLocator().locateSMP(participantId);
        } catch (SMPLocatorException ex) {
            log.error("An error occurred in locating the SMP server for participant {}."
                     + "\n\tDetails: {}\n\tCaused by: {}", participantId, ex.getMessage(),
                                                          Utils.getExceptionTrace(ex));
            throw new SMPQueryException("Could not locate the SMP server for participant", ex);
        }

        try {
            int redirectCount = 0;
            do {
            	// Build the query string for getting the service meta-data directly
            	String baseURL = smpURL.toString();
            	if (!baseURL.endsWith("/"))
            		baseURL += "/";
            	final URL queryURL = new URL(String.format("%s%s/services/%s", baseURL, participantId.getURLEncoded(),
            																		serviceId.getURLEncoded()));                           	            	
            	log.debug("Query the SMP: {}", queryURL.toString());
            	final ISMPResponseConnection connection = configuration.getRequestExecutor().executeRequest(queryURL);
            	final ISMPQueryResult response = new SMPResultReader(configuration)
            														.handleResponse(connection.getInputStream());
                connection.close();
                Redirection redirect = null;
	            if (response instanceof Redirection) {
	            	redirect = (Redirection) response;
	            } else if (response instanceof ServiceInformation) {
	            	log.debug("Retrieved service information from SMP server, search for requested process and transport");
	            	List<ProcessInfo> procInfo = ((ServiceInformation) response).getProcessInformation()
																	  .parallelStream()
																	  .filter(pi -> pi.supportsProcess(processId, role))
																	  .collect(Collectors.toList());
	            	/* The service info could include multiple process meta-data elements because some of them could map 
	            	 * to all processes and/or roles. Now we try to filter out the specific one that matches to the 
	            	 * given process and role identifiers.  
	            	 */
	            	if (procInfo.size() > 1) {	            		
	            		if (role != null)
	            			procInfo = procInfo.parallelStream()
	            							   .filter(pi -> !Utils.isNullOrEmpty(pi.getRoles()))	            							   
	            							   .collect(Collectors.toList());
	            		if (procInfo.size() > 1)
	            			procInfo = procInfo.parallelStream()
	            							   .filter(pi -> !Utils.isNullOrEmpty(pi.getProcessIds()))
	            							   .collect(Collectors.toList());							
	            	}
	            	if (procInfo.size() != 1) {
	            		log.error("Unable to determine unique process meta-data from SMP result!");
	            		throw new SMPQueryException("Ambigious result based on query arguments or SMP data");
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
	                smpURL = ((Redirection) response).getNewSMPURL();
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
