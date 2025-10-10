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

import java.util.Collection;

import org.holodeckb2b.bdxr.common.datamodel.ExtensibleMetadata;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;

/**
 * Represents the <i>Envelope</i> class from the XHE specification. Contains generic information about the envelope, 
 * such as the version of the XHE specification and business profiles used. 
 * <p>
 * See <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-HEADER-ENVELOPE-INFORMATION">
 * section 2.2 of the OASIS XHE Standard</a> for the specification of the data elements contained in this class.  
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface Envelope extends ExtensibleMetadata {
	
	/**
	 * @return The REQUIRED version of the specific XHE envelope model in use.
	 */
	Identifier getXHEVersionId();
	
	/**
	 * @return The identification of a customization or use of the envelope
	 */
	Identifier getCustomizationId();
	
	/**
	 * @return The identification of a specific profile found within the customization
	 */
	Identifier getProfileId();
	
	/**
	 * @return The identification of a particular instance of using the given profile.
	 */
	Identifier getProfileExecutionId();
	
	/**
	 * Gets the header of this envelope which contains the meta-data about the content of the document and which is
	 * [probably] needed for handling of the document.
	 * 
	 * @return	the REQUIRED header of this envelope 
	 */
	Header getHeader();
	
	/**
	 * @return collection of the meta-data about the payloads contained in the envelope
	 */
	Collection<Payload> getPayloads();
}
