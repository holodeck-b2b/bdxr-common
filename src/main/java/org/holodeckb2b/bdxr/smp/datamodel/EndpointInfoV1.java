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

import java.net.URL;

/**
 * Represents the meta-data of an <i>Endpoint</i> as specified in the OASIS SMP V1 and PEPPOL specifications. In
 * addition to the generic meta-data it adds the meta-data whether a business level signature is required and what the
 * minimum authentication level should be. Note that these meta-data information items are almost never used and have
 * also been removed in the OASIS V2 specification.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface EndpointInfoV1 extends EndpointInfo {
	/**
	 * Indicates whether a business level signature is required.
	 *
	 * @return  <code>Boolean.TRUE</code> if a business level signature is required, or<br>
	 *          <code>Boolean.FALSE</code> if such signature is not required,or<br>
	 *          <code>null</code> if not defined
	 */
	Boolean getBusinessLevelSignatureRequired();

	/**
	 * Gets the minimum authentication level required by this endpoint
	 *
	 * @return The identifier of the minimum authentication level required, or<br>
	 *         <code>null</code> if no such level is defined
	 */
	String getMinimumAuthenticationLevel();

	/**
	 * Gets the URL to a human readable documentation of the service format.
	 *
	 * @return URL to technical documentation
	 */
	URL getTechnicalInformationURL();
}
