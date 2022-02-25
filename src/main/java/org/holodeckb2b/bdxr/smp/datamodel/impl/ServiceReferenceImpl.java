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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ServiceReferenceImpl extends ExtensibleMetadataClass implements ServiceReference {

	private Identifier	serviceId;
	private Set<ProcessInfo>	processInfo;

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
    public ServiceReferenceImpl(final Identifier service, Set<ProcessInfo> processes, final List<Extension> exts){
		super(exts);
        this.serviceId = service;
        this.processInfo = processes;
    }

	/**
	 * Creates a new <code>ServiceReference</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public ServiceReferenceImpl(final ServiceReference src) {
    	super(src.getExtensions());
		this.serviceId = src.getServiceId();
        this.processInfo = (Set<ProcessInfo>) src.getProcessInfo();
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
    public void setProcesses(Set<ProcessInfo> processes) {
        this.processInfo = processes;
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
}
