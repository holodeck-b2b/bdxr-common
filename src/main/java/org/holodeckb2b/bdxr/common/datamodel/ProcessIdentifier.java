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
package org.holodeckb2b.bdxr.common.datamodel;

/**
 * Represent the definition of the Process identifiers used in the BDXR data models. It is based on the generic {@link
 * Identifier} but adds a function to indicate that the identifier does identify the "no process" which is used in the
 * SMP specifications for service meta-data that does not relate to any process. Because this represented in the
 * specifications by a specific, but different, string we use a indicator which should be set when the actual data is
 * processed.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0 In package <code>org.holodeckb2b.bdxr.common.datamodel</code>
 */
public interface ProcessIdentifier extends Identifier {

	/**
	 * Indicates whether this identifier is the special "no-process" identifier as defined in the specifications.
	 *
	 * @return	<code>true</code> if it is the special "no-process" identifier,<br>
	 * 			<code>false</code> if not
	 */
	boolean isNoProcess();
}
