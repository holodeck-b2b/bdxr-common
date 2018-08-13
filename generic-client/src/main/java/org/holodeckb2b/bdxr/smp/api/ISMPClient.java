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

import org.holodeckb2b.bdxr.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.datamodel.Identifier;

/**
 * Defines the interface of the SMP client that can be used to request the meta-data about a participant in the 
 * network from a SMP server. To create an instance of the client use the {@link SMPClientBuilder} methods to setup
 * the configuration and create a client. 
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ISMPClient {

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
    								final String     transportProfile) throws SMPQueryException;
    
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
    public List<EndpointInfo> getEndpoints(final Identifier participantId,
							    		   final Identifier serviceId,
							    		   final Identifier processId) throws SMPQueryException;
}
