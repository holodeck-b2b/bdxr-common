/*
 * Copyright (C) 2025 The Holodeck B2B Team
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

import java.net.URI;

/**
 * Represents the generic definition of elements used within BDXR models whose allowed values are defined by a code 
 * list, for example the <i>BusinessScopeCriterionCode</i> to specify the business context property that applies to an
 * XHE instance.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface Code {

	/**
	 * @return  the code value
	 */
	String getCode();
	
	/**
	 * @return	identification of the list that specifies the allowed values of the code
	 */
	Identifier getListId();
	
	/**
	 * @return	the name of the list that specifies the allowed values of the code
	 */
	String 	getListName();
	
	/**
	 * @return the version of the list that specifies the allowed values of the code
	 */
	Identifier getListVersion();
	
	/**
	 * @return the URI that identifies where the code list scheme is located
	 */
	URI getListURI();
}
