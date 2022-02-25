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
 * Represents a group of processes which share the same <i>messaging endpoints</i> or <i>redirection</i> to another SMP.
 * Either a list of endpoints or a redirection must be provided, but never both as it would be impossible to tell what
 * the correct list of endpoints would be.
 * <p>NOTE: In the OASIS v1 and PEPPOL SMP specifications there is no possibility to express the sharing of endpoints
 * between process and endpoint meta-data needs to be included in XML per process, i.e. the set of processes is one.
 * Note however that implementation MAY group processes and it is up to the components responsible for serialising and
 * parsing of the XML to ensure conformance to the spec.
 *
 * <p>Corresponds to the <code>ProcessMetadata</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ProcessGroup extends ExtensibleMetadata {

	/**
	 * Gets the group of process meta-data on the processes in which the endpoints are / redirection is used.
	 *
	 * @return Collection of process meta-data
	 */
	Collection<? extends ProcessInfo> getProcessInfo();

	/**
	 * Gets the list of available endpoints for this process group.
	 *
	 * @return The collection of endpoints
	 */
	Collection<? extends EndpointInfo> getEndpoints();

	/**
	 * Get the redirection information for this process group.
	 *
	 * @return Meta-data about the redirection
	 */
	Redirection getRedirection();
}
