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

import java.io.Serializable;

/**
 * Represents the meta-data of an <i>Identifier Scheme</i> which defines the policies for identifiers used in the
 * SMP data, e.g. the <i>Participant</i> ID. As only the policy on whether identifiers from the scheme should be
 * treated case sensitively is relevant for general processing of SMP data the interface is limited
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface IDScheme extends Serializable {
	/**
	 * Gets the unique identification of the scheme.
	 *
	 * @return the scheme id
	 */
	String getSchemeId();

	/**
	 * Indicates whether identifiers from this identifier scheme should be treated case sensitively or not.
	 *
	 * @return	<code>true</code> when the identifiers should be treated case sensitively,<br/>
	 * 			<code>false</code> when the identifiers should be treated case insensitively
	 */
	boolean isCaseSensitive();
}
