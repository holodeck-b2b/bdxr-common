/*
 * Copyright (C) 2022 The Holodeck B2B Team
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

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.impl.ExtensibleMetadataClass;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ServiceReferenceImpl extends ExtensibleMetadataClass implements ServiceReference {
	private static final long serialVersionUID = -125965889098620891L;
	
	private Identifier	serviceId;
	private Collection<ProcessInfo>	processInfo;

	/**
     * Default constructor creates "empty" instance
     */
    public ServiceReferenceImpl() {
    	this(null, null, null);
    }

    /**
     * Creates a new object representing the processes in which a service is supported by a participant.
     *
     * @param service       The service identifier
     * @param processes     Set of process meta-data in which the service is supported
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public ServiceReferenceImpl(Identifier service, Collection<? extends ProcessInfo> processes, 
    							List<Extension<?>> exts){
		super(exts);
        this.serviceId = service;
        setProcesses(processes);
    }

	/**
     * Gets the service identifier to which these service meta-data apply
     *
     * @return The service id
     */
	@Override
	public Identifier getServiceId() {
		return serviceId;
	}

    /**
     * Sets the service identifier to which these service meta-data apply
     *
     * @param serviceId The service id
     */
    public void setServiceId(final Identifier serviceId) {
        this.serviceId = serviceId;
    }

	/**
	 * Gets the set of identifiers for the processes and roles in which the service is supported.
	 *
	 * @return	collection of process identifiers
	 */
	@Override
	public Collection<? extends ProcessInfo> getProcessInfo() {
		return processInfo;
	}

	/**
     * Sets the processes meta-data in which the participant offers this service
     *
     * @param processes set of process meta-data
     */
    public void setProcesses(Collection<? extends ProcessInfo> processes) {
        this.processInfo = new ArrayList<>(processes != null ? processes.size() : 0);
		if (processes != null)
			this.processInfo.addAll(processes);
    }

    /**
     * Adds the meta-data of one process in which this service if offered to the list of process ids
     *
     * @param process   process meta-data
     */
    public void addProcessInfo(final ProcessInfo process) {
		if (this.processInfo == null)
			this.processInfo = new HashSet<>(1);
        this.processInfo.add(process);
    }

    @Override
    public boolean equals(Object o) {
	    if (o == null || !(o instanceof ServiceReference))
	    	return false;

	    ServiceReference r = (ServiceReference) o;
	    return super.equals(o)
    		&& Utils.areEqual(processInfo, r.getProcessInfo())
    		&& Utils.nullSafeEqual(serviceId, r.getServiceId());
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(processInfo, serviceId);
		return result;
	}
}
