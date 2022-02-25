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
 * Represents the meta-data on the Process in which a Participant is involved and the Role(s) it plays within that
 * Process.
 * <p>NOTE: In the OASIS v1 and PEPPOL SMP specifications there is no possibility to indicate the role of the
 * participant.
 * <p>Corresponds to the <code>Process</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ProcessInfo extends ExtensibleMetadata {
	/**
	 * Gets the process identifier
	 *
	 * @return the identifier of the process
	 */
	ProcessIdentifier getProcessId();

	/**
	 * Gets the roles the participant acts in for this process
	 *
	 * @return	collection of Role identifiers, empty when no specific Roles are defined
	 */
	Collection<? extends Identifier> getRoles();
}
