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
 * Represents the meta-data about a <i>Service</i> that a participant in the network supports. Although the SMP
 * specifications currently in use (OASIS v1 and PEPPOL) are more <i>document</i> based we use the term <i>service</i>
 * as the new OASIS SMP specification will also be services based and it also is a more generic term. Handling a
 * certain document can also be seen as a service to process it.
 *
 * <p>Corresponds to the <code>ServiceMetadata</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ServiceMetadata extends ExtensibleMetadata, QueryResult {

	/**
     * Gets the service identifier to which these service meta-data apply
     *
     * @return The service id
     */
	Identifier	getServiceId();

	/**
	 * Gets the meta-data on the processes in which the service is supported by the participant.
	 *
	 * @return collection of {@link ProcessGroup}s, must contain at least one element.
	 */
	Collection<? extends ProcessGroup> getProcessMetadata();

	/**
	 * Determines if the given object represents the same Service meta-data.
	 *
	 * @param o		the object the compare
	 * @return		<code>true</code> iff <code>o</code> is an instance of <code>ServiceMetadata</code> and represent
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
