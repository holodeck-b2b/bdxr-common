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

import java.util.Collection;

/**
 * Represents the service reference meta-data as specified in the OASIS SMP V2 specification consisting of the service
 * identifier and a list of process identifiers in which the service is supported by a participant.
 *
 * <p>Corresponds to the <code>ServiceReferene</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ServiceReference extends ExtensibleMetadata {

	/**
	 * Gets the identifier of the service being referenced.
	 *
	 * @return the service identifier
	 */
	Identifier	getServiceId();

	/**
	 * Gets the set of identifiers for the processes, and participant roles, in which the service is supported.
	 *
	 * @return	collection of process information, empty or <code>null</code> to indicate the service is used in all
	 *			processes
	 */
	Collection<? extends ProcessInfo>	getProcessInfo();

	/**
	 * Determines if the given object represents the same Service Reference.
	 *
	 * @param o		the object the compare
	 * @return		<code>true</code> iff <code>o</code> is an instance of <code>ServiceReference</code> and represent
	 * 				the same meta-data.
	 */
	@Override
	boolean equals(Object o);

	/**
	 * Calculates the hash value for the meta-data represented by this object. The hash value of two instances, <code>i1
	 * </code> and <code>i2</code> must be the same when they represent the same meta-data, i.e. when
	 * <code>i1.equals(i2) == true</code>.
	 *
	 * @return hash value for this instance
	 */
	@Override
	int hashCode();
}
