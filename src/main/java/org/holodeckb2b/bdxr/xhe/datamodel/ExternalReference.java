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
package org.holodeckb2b.bdxr.xhe.datamodel;

import java.time.ZonedDateTime;

import org.holodeckb2b.bdxr.common.datamodel.Identifier;

/**
 * Represent the <i>ExternalReference</i> class from the XHE specification which contains the meta-data to retrieve a
 * resource from an external location, such as the URI and the time it's available.
 * <p>
 * See <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-BUSINESS-SCOPE-INFORMATION">section 
 * 2.8 of the OASIS XHE Standard</a> for the specification of the data elements contained in this class.  
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface ExternalReference {

	/**
	 * Gets the identification of the referenced object. Typically this is an URI, but it can be any type of identifier. 
	 * 
	 * @return	identifier of the referenced object.
	 */
	Identifier getId();
	
	/**
	 * Gets the start date and time when the information is available. In general this is an inclusive timestamp, but 
	 * envelope profiles may define it to be exclusive.

	 * @return the start date and time when the information is available
	 */
	ZonedDateTime getAvailabilityStart();
	
	/**
	 * Gets the end date and time when the information is available. In general this is an exclusive timestamp, but 
	 * envelope profiles may define it to be inclusive.
	 * 
	 * @return the end date and time when the information is available
	 */
	ZonedDateTime getAvailabilityEnd();
	
	/**
	 * Gets the information about the login that may be required to access the resource. For example a username. 
	 * 
	 * @return login details needed to access the resource
	 */
	String getLoginDetails();
	
	/**
	 * Gets the password or information about it that is needed to access the resource. As exchanging password in plain
	 * text is not recommended the returned value may be the encyrpted version of the password or just a hint which 
	 * password to use.
	 * 
	 * @return password information needed to access the resource
	 */
	String getPassword();
}
