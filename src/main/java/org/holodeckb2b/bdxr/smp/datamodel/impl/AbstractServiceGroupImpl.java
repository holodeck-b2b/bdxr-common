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
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public abstract class AbstractServiceGroupImpl<T> extends ExtensibleMetadataClass implements ServiceGroup<T> {

	private Identifier  participantId;
    private Set<T>		serviceRefs;

/**
     * Default constructor creates "empty" instance
     */
    public AbstractServiceGroupImpl() {
    	this(null, null, null);
    }

    /**
     * Creates a new object representing an overview of the services supported by a participant.
     *
     * @param participant   The participant identifier
     * @param svcRefs       Set of references to services which the participant supports
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public AbstractServiceGroupImpl(final Identifier participant, Set<T> svcRefs, final List<Extension> exts) {
		super(exts);
        this.participantId = participant;
        this.serviceRefs = svcRefs;
    }

	/**
	 * Creates a new <code>ServiceGroup</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public AbstractServiceGroupImpl(final ServiceGroup<T> src) {
    	super(src.getExtensions());
		this.participantId = src.getParticipantId();
        this.serviceRefs = (Set<T>) src.getServiceReferences();
    }

    /**
     * Gets the identifier of the participant the service meta-data applies to
     *
     * @return The participant id
     */
	@Override
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
	 * Gets the set of service and process identifier combinations which are supported by the participant.
	 *
	 * @return collection of {@link ServiceReference}s
	 */
	@Override
	public Collection<? extends T> getServiceReferences() {
		return serviceRefs;
	}

	/**
     * Sets the references to services which the participant supports
     *
     * @param svcRefs set of service references
     */
    public void setServiceReferences(Set<T> svcRefs) {
        this.serviceRefs = svcRefs;
    }

    /**
     * Adds a service reference to the list of supported services
     *
     * @param svcRef  service reference
     */
    public void addServiceReference(final T svcRef) {
		if (this.serviceRefs == null)
			this.serviceRefs = new HashSet<>(1);
        this.serviceRefs.add(svcRef);
    }
}
