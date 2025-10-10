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
package org.holodeckb2b.bdxr.smp.datamodel.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.impl.ExtensibleMetadataClass;
import org.holodeckb2b.bdxr.common.datamodel.impl.IdentifierImpl;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Redirection;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessGroupImpl extends ExtensibleMetadataClass implements ProcessGroup {
    private static final long serialVersionUID = 3331734042907859447L;
    
	private Collection<ProcessInfo>		processes;
    private Redirection			 		redirect;
    private Collection<EndpointInfo>   	endpoints;

    /**
     * Default constructor creates "empty" instance
     */
    public ProcessGroupImpl() {
    	this(null, null);
		this.endpoints = new ArrayList<>();
    }

	/**
	 * Performs basic initialisation of a new instance
	 *
	 * @param processes	set of process meta-data
	 * @param exts		list of extension to be included
	 */
	protected ProcessGroupImpl(Collection<? extends ProcessInfo> processes, List<Extension<?>> exts) {
		super(exts);
        setProcessInfo(processes);
	}

    /**
     * Creates a new process group sharing the same endpoints.
     *
     * @param processes     The set of process meta-data
     * @param endpoints     The list of available endpoints for this process
     * @param extendedInfo  Extended information available about this process
     */
    public ProcessGroupImpl(Collection<? extends ProcessInfo> processes, Collection<? extends EndpointInfo> endpoints, 
    						List<Extension<?>> extendedInfo) {
		this(processes, extendedInfo);
        setEndpoints(endpoints);
    }

    /**
     * Creates a new process group that is redirected to another SMP.
     *
     * @param processes     The set of process meta-data
     * @param redirect		Meta-data on the redirection to another SMP server
     * @param extendedInfo	Extended information available about this process
     */
    public ProcessGroupImpl(final Set<ProcessInfo> processes, final Redirection redirect,
    				   final List<Extension<?>> extendedInfo) {
		this(processes, extendedInfo);
        this.redirect   = redirect;
    }

	/**
     * Gets the list of process meta-data on the processes in which the endpoints are / redirection is used.
     *
     * @return List of process meta-data
     */
	@Override
    public Collection<? extends ProcessInfo> getProcessInfo() {
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
     */
	public boolean supportsProcess(final IdentifierImpl processId, final IdentifierImpl role) {
        return Utils.isNullOrEmpty(processes)
        	|| processes.parallelStream().anyMatch(pi -> processId.equals(pi.getProcessId())
        												&& (role == null || pi.getRoles().isEmpty()
        													|| pi.getRoles().stream().anyMatch(r -> role.equals(r))));
    }

    /**
     * Sets the process info
     *
     * @param processInfo The collection of process information to set
     */
    public void setProcessInfo(Collection<? extends ProcessInfo> processInfo) {
        this.processes = new ArrayList<>(processInfo != null ? processInfo.size() : 0);
		if (processInfo != null) 
			this.processes.addAll(processInfo);
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
			this.processes = new HashSet<>(1);
        this.processes.add(processInfo);
    }

    /**
     * Gets the list of available endpoints for this process
     *
     * @return The list of endpoints
     */
	@Override
    public Collection<? extends EndpointInfo> getEndpoints() {
        return endpoints;
    }

    /**
     * Set the endpoints available for this process. Note that endpoints can only be set if there is no
	 * redirection specified.
     *
     * @param endpoints     The collection of endpoints available for this process
	 * @throws IllegalStateException when a redirection is already defined
     */
    public void setEndpoints(Collection<? extends EndpointInfo> endpoints) {
		if (this.redirect != null && !Utils.isNullOrEmpty(endpoints))
			throw new IllegalStateException("Redirection is already set");

        this.endpoints = new ArrayList<>(endpoints != null ? endpoints.size() : 0);
		if (endpoints != null) 
			this.endpoints.addAll(endpoints);
    }

    /**
     * Add an endpoint to the set of available endpoints.Note that endpoints can only be set if there is no
	 * redirection specified.
     *
     * @param endpoint      The endpoint meta-data to add
	 * @throws IllegalStateException when a redirection is already defined
     */
    public void addEndpoint(final EndpointInfo endpoint) {
        if (endpoint == null)
            throw new IllegalArgumentException("A EndpointInfo object must be provided");
		if (this.redirect != null)
			throw new IllegalStateException("Redirection is already set");
		if (this.endpoints == null)
			this.endpoints = new HashSet<>(1);
		this.endpoints.add(endpoint);
    }

    /**
     * Get the redirect information.
     *
     * @return Meta-data about the redirection
     */
	@Override
	public Redirection getRedirection() {
		return redirect;
	}

    /**
     * Sets the redirect information. Note that a redirect can only be set if there are no endpoints registered.
     *
     * @param redirect The meta-data about the redirection
	 * @throws IllegalStateException when one or more endpoint have already been registered
     */
	public void setRedirection(Redirection redirect) {
		if (redirect != null && !this.endpoints.isEmpty())
			throw new IllegalStateException("Endpoints have already been set");
		this.redirect = redirect;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ProcessGroup))
			return false;

		ProcessGroup pg = (ProcessGroup) o;
		return super.equals(o)
			&& Utils.areEqual(endpoints, pg.getEndpoints())
			&& Utils.areEqual(processes, pg.getProcessInfo())
			&& Utils.nullSafeEqual(redirect, pg.getRedirection());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(Utils.isNullOrEmpty(endpoints) ? null : endpoints,
											   Utils.isNullOrEmpty(processes) ? null : processes, redirect);
		return result;
	}

}
