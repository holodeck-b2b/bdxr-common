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
import java.util.Objects;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.impl.ExtensibleMetadataClass;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public abstract class AbstractServiceGroupImpl<T> extends ExtensibleMetadataClass implements ServiceGroup<T> {
	private static final long serialVersionUID = -2119262523421530565L;
	
	private Identifier  	participantId;
    private Collection<T>	serviceRefs;

    /**
     * Default constructor creates "empty" instance
     */
    AbstractServiceGroupImpl() {
    	this(null, null, null);
    }

    /**
     * Creates a new object representing an overview of the services supported by a participant.
     *
     * @param participant   The participant identifier
     * @param svcRefs       Set of references to services which the participant supports
     * @param exts          Any extended meta-data information included in the SMP record
     */
    AbstractServiceGroupImpl(final Identifier participant, Collection<? extends T> svcRefs, List<Extension<?>> exts) {
		super(exts);
        this.participantId = participant;
        setServiceReferences(svcRefs);
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
    public void setServiceReferences(Collection<? extends T> svcRefs) {
        this.serviceRefs = new HashSet<>(svcRefs != null ? svcRefs.size() : 0);
		if (svcRefs != null)
			this.serviceRefs.addAll(svcRefs);
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

    @Override
    public boolean equals(Object o) {
    	if (o == null || !(o instanceof ServiceGroup))
    		return false;

    	@SuppressWarnings("rawtypes")
 		ServiceGroup oth = (ServiceGroup) o;
    	return super.equals(o)
    		&& Utils.nullSafeEqual(this.participantId, oth.getParticipantId())
    		&& Utils.areEqual(this.serviceRefs, oth.getServiceReferences());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(participantId, !Utils.isNullOrEmpty(serviceRefs) ? serviceRefs : null);
		return result;
	}
}
