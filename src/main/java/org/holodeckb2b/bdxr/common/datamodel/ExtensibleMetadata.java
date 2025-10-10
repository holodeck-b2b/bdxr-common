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

import java.io.Serializable;
import java.util.List;

/**
 * Represents a meta-data element that can be extended with non-standard, context specific meta-data. This interface is 
 * not intended to be implemented directly but only serves as a "base interface" for the specific meta-data elements 
 * defined in the other data model packages.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0 In package <code>org.holodeckb2b.bdxr.common.datamodel</code>  
 */
public interface ExtensibleMetadata extends Serializable {

	/**
	 * Gets the additional, non standard, information related to the meta-data object.
	 *
	 * @return collection of the extended meta-data included with this element. If no extension are used by the element
	 * 			an empty set MUST be returned. 
	 */
	List<Extension<?>> getExtensions();
}
