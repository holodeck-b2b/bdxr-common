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
	 * @return	collection of process information
	 */
	Collection<? extends ProcessInfo>	getProcessInfo();
}
