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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceMetadata;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ServiceMetadataImpl extends ExtensibleMetadataClass implements ServiceMetadata {

    private Identifier  		participantId;
    private Identifier  		serviceId;
    private Set<ProcessGroup>   processGroups;

    /**
     * Default constructor creates "empty" instance
     */
    public ServiceMetadataImpl() {
    	this(null, null, null, null);
    }

    /**
     * Creates a new object representing the SMP meta-data for a specific participant and service type.
     *
     * @param participant   The participant's identifiers
     * @param service       The service identifier
     * @param processes     Set of process groups in which the service is supported
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public ServiceMetadataImpl(final Identifier participant, final Identifier service, Set<ProcessGroup> processes,
                           	  final List<Extension> exts) {
		super(exts);
        this.participantId = participant;
        this.serviceId = service;
        this.processGroups = processes;
    }

	/**
	 * Creates a new <code>ServiceInformation</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public ServiceMetadataImpl(final ServiceMetadata src) {
    	super(src.getExtensions());
    	this.participantId = src.getParticipantId();
		this.serviceId = src.getServiceId();
        this.processGroups = (Set<ProcessGroup>) src.getProcessMetadata();
    }

    /**
     * Gets the identifier of the participant the service meta-data applies to
     *
     * @return The participant id
     */
    public Identifier getParticipantId() {
        return participantId;
    }

    /**
     * Sets the identifier of the participant to which the service meta-data apply
     *
     * @param participantId The participant id
     */
    public void setParticipantId(Identifier participantId) {
        this.participantId =  participantId;
    }

    /**
     * Gets the service identifier to which these service meta-data apply
     *
     * @return The service id
     */
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
     * Gets the set of processes in which this service is used by the participant.
     *
     * @return Set of process groups in which this service is used by the participant
	 */
	@Override
	public Set<ProcessGroup> getProcessMetadata() {
		return processGroups;
	}

	/**
     * Sets the meta-data of the processes in which the participant offers this service
     *
     * @param processGroups set of process groups in which the service is supported
     */
    public void setProcessInformation(Set<ProcessGroup> processGroups) {
        this.processGroups = processGroups;
    }

    /**
     * Adds the meta-data of one process group in which this service if offered to the list of process meta-data
     *
     * @param processGroup   process group in which the service is supported
     */
    public void addProcessGroup(final ProcessGroup processGroup) {
        if (processGroup == null)
            throw new IllegalArgumentException("A process group meta-data object must be provided");
		if (this.processGroups == null)
			this.processGroups = new HashSet<>(1);
        this.processGroups.add(processGroup);
    }
}
