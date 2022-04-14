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
import java.util.List;

/**
 * Represents a meta-data element included in a SMP response that can be extended with non-standard, context specific
 * meta-data. This interface is not intended to be implemented directly but only serves as a "base interface" for the
 * specific meta-data elements defined in this package.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ExtensibleMetadata extends Serializable {

	/**
	 * Gets the additional, non standard, information related to the meta-data object.
	 *
	 * @return The extended meta-data, may be <code>null</code> which is considered equal to an empty collection.
	 */
	List<Extension> getExtensions();
}
