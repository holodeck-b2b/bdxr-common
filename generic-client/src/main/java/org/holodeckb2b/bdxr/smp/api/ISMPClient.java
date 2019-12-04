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
package org.holodeckb2b.bdxr.smp.api;

import java.util.List;

import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;

/**
 * Defines the interface of the SMP Client that can be used to request the meta-data about a participant in the 
 * network from a SMP server. To create an SMP Client instance use the {@link SMPClientBuilder} to setup the network
 * specific parameters and create the client instance. 
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ISMPClient {

	/**
     * Gets the endpoint meta-data for the given participant, service and process and that supports the request
     * transport profile.
     * <p>NOTE: In the OASIS SMP v2 specification it is possible to register a service without binding to a specific
     * process identifier to indicate a generic endpoint for the service applying to all processes or for use cases 
     * where process does not apply. Both the PEPPOL and OASIS v2 SMP specifications defines default values for the
     * process identifier (<i>busdox:noprocess</i> resp. <i>bdx:noprocess</i>) to support these use cases.<br/> 
     * Therefore the <code>processId</code> parameter of this method is optional and can be set to <code>null</code>. 
     * When the SMP server returns a OASIS SMP v2 document only the endpoints not bound to a specific process identifier
     * will be taken into account when searching for the endpoint. In case the response is a PEPPOL or SMP v1 document
     * only the processes with the default value will be used.  
     *
     * @param participantId		Participant's Id
     * @param serviceId			Service Id
     * @param processId			Process Id
     * @param transportProfile	Requested transport profile name
     * @return	The endpoint meta-data if there exists an endpoint for this participant, service and process and which
     * 			supports the requested transport profile, <code>null</code> otherwise.
     * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
     */
	EndpointInfo getEndpoint(final Identifier participantId,
    						 final Identifier serviceId,
    						 final Identifier processId,
    						 final String     transportProfile) throws SMPQueryException;
    
    /**
     * Gets the meta-data of all endpoints for the given participant, service and process.
     * <p>NOTE: In the OASIS SMP v2 specification it is possible to register a service without binding to a specific
     * process identifier to indicate a generic endpoint for the service applying to all processes or for use cases 
     * where process does not apply. Both the PEPPOL and OASIS v2 SMP specifications defines default values for the
     * process identifier (<i>busdox:noprocess</i> resp. <i>bdx:noprocess</i>) to support these use cases.<br/> 
     * Therefore the <code>processId</code> parameter of this method is optional and can be set to <code>null</code>. 
     * When the SMP server returns a OASIS SMP v2 document only the endpoints not bound to a specific process identifier
     * will be taken into account when searching for the endpoint. In case the response is a PEPPOL or SMP v1 document
     * only the processes with the default value will be used.  
     *
     * @param participantId		Participant's Id
     * @param serviceId			Service Id
     * @param processId			Process Id
     * @return	The endpoint meta-data if there exists an endpoint for this participant, service and process and which
     * 			supports the requested transport profile, <code>null</code> otherwise.
     * @throws SMPQueryException 	When an error occurs in the lookup of the SMP location or querying the SMP server
     */
    List<EndpointInfo> getEndpoints(final Identifier participantId,
							        final Identifier serviceId,
							        final Identifier processId) throws SMPQueryException;
}
