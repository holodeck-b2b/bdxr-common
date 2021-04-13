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
package org.holodeckb2b.bdxr.smp.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.holodeckb2b.commons.util.Utils;

/**
 * Represents the list of processes in which a specific <i>service</i>/<i>document</i> is offered/used and which use the 
 * same <i>messaging endpoints</i>. The association with the service is implemented by containment of this class in 
 * {@link ServiceInformation} mirroring the structure of the SMP XML structure.
 * <p>As the redirection feature has been moved the level of process in the OASIS SMP V2 Specification this class either
 * contains meta-data on the available endpoints or a redirection to another SMP. 
 * <p>NOTE: In the OASIS v1 and PEPPOL SMP specifications the number of processes is limited to one, if several
 * processes use the same endpoint multiple <code>ProcessList</code> instances will occur.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since NEXT_VERSION To support OASIS SMP V2 the redirect and role meta-data have been added 
 */
public class ProcessList {

    private List<ProcessInfo>		processes;
    private Redirection			 	redirect;
    private List<EndpointInfo>   	endpoints;
    private List<IExtension>    	extensions;

    /**
     * Default constructor creates "empty" instance
     */
    public ProcessList() {
    	this.processes = new ArrayList<>();
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param procInfo      The list of process meta-data
     * @param endpoints     The list of available endpoints for this process
     * @param extendedInfo  Extended information available about this process
     * @since 2.0.0
     */
    public ProcessList(final List<ProcessInfo> procInfo, final List<EndpointInfo> endpoints, 
    				   final List<IExtension> extendedInfo)
    {
        this.processes = procInfo;
        this.endpoints  = endpoints;
        this.extensions = extendedInfo;
    }

    /**
     * Creates a new instance with the given meta-data
     * 
     * @param procInfo      The list of process meta-data
     * @param redirect		Meta-data on the redirection to another SMP server
     * @param extendedInfo	Extended information available about this process
     * @since 2.0.0
     */
    public ProcessList(final List<ProcessInfo> procInfo, final Redirection redirect, 
    				   final List<IExtension> extendedInfo) {
    	this.processes = procInfo;
    	this.redirect   = redirect;
    	this.extensions = extendedInfo;
    }
        
    /**
     * Gets the list of process meta-data on the processes in which the endpoints are / redirection is used. 
     *
     * @return List of process meta-data 
     */
    public List<ProcessInfo> getProcessInfo() {
        return processes;
    }

    /**
     * Checks if this given process is included in the set of processes that is represented by this meta-data and if
     * a role is specified if the participant plays that role in the process.
     *
     * @param processId     identifier of the process to check
     * @param role     		role the participant should play in the process, may be <code>null</code>
     * @return              <code>true</code> if the list of <code>ProcessInfo</code>s is empty or includes an instance 
     * 						that uses the given process id and has either an empty list of role identifiers or one that
     * 						includes the specified role,<br>
     * 						<code>false</code> otherwise
     * @since 2.0.0 Support for checking on role
     */
    public boolean supportsProcess(final Identifier processId, final Identifier role) {
        return Utils.isNullOrEmpty(processes) 
        	|| processes.parallelStream().anyMatch(pi -> processId.equals(pi.getProcessId()) 
        												&& (role == null || pi.getRoles().isEmpty() 
        													|| pi.getRoles().stream().anyMatch(r -> role.equals(r)))); 
    }

    /**
     * Sets the process info
     *
     * @param processInfo The list of process information to set
     */
    public void setProcessInfo(List<ProcessInfo> processInfo) {
    	if (processInfo == null)
    		this.processes = new ArrayList<>();
    	else
    		this.processes = processInfo;
    }

    /**
     * Add one {@linkplain ProcessInfo} instance to the list of processes that support the service and use the same 
     * endpoints / redirect.
     *
     * @param processInfo The process info
     */
    public void addProcessInfo(final ProcessInfo processInfo) {
        if (processInfo == null)
            throw new IllegalArgumentException("A process info object must be provided");
        if (this.processes == null)
            this.processes = new ArrayList<>();
        this.processes.add(processInfo);
    }

    /**
     * Gets the list of available endpoints for this process
     *
     * @return The list of endpoints
     */
    public List<EndpointInfo> getEndpoints() {
        return endpoints;
    }

    /**
     * Set the list of endpoints available for this process
     *
     * @param endpoints     The list of endpoints available for this process
     */
    public void setEndpoints(List<EndpointInfo> endpoints) {
        this.endpoints = endpoints;
    }

    /**
     * Add an endpoint to the list of available endpoints
     *
     * @param endpoint      The endpoint meta-data to add
     */
    public void addEndpoint(final EndpointInfo endpoint) {
        if (endpoint == null)
            throw new IllegalArgumentException("A EndpointInfo object must be provided");
        if (this.endpoints == null)
            this.endpoints = new ArrayList<>();
        this.endpoints.add(endpoint);
    }


    /**
     * Gets the additional, non standard, information related to this endpoint
     *
     * @return The extended meta-data
     */
    public List<IExtension> getExtensions() {
        return extensions;
    }

    /**
     * Sets the additional, non standard, information related to this endpoint
     *
     * @param extension The extended meta-data
     */
    public void setExtensions(List<IExtension> extension) {
        this.extensions = extension;
    }

    /**
     * Add one specific extension to the meta-data
     *
     * @param   ext     The extension to add to set of extended meta-data
     */
    public void addExtension(final IExtension ext) {
        if (ext == null)
            throw new IllegalArgumentException("A extention must be specified");
        if (this.extensions == null)
            this.extensions = new ArrayList<>();
        this.extensions.add(ext);
    }

    /**
     * Get the redirect information.
     * 
     * @return Meta-data about the redirection
     * @since  2.0.0
     */
	public Redirection getRedirection() {
		return redirect;
	}

    /**
     * Sets the redirect information.
     * 
     * @param redirect The meta-data about the redirection
     * @since 2.0.0
     */
	public void setRedirection(Redirection redirect) {
		this.redirect = redirect;
	}
}
