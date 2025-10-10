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
import java.util.Set;

import org.holodeckb2b.bdxr.common.datamodel.Identifier;

/**
 * Represent the <i>Header</i> class from the XHE specification. Contains the meta-data about the content of the 
 * business document and which are [probably] needed for handling of the document. 
 * <p>
 * See <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-HEADER-INFORMATION">section 
 * 2.3 of the OASIS XHE Standard</a> for the specification of the data elements contained in this class.  
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface Header {
	
	/**
	 * @return the REQUIRED unique identifier of this document
	 */
	Identifier getId();
	
	/**
	 * @return an additional identifier of this document
	 */
	Identifier getUUID();
	
	/**
	 * @return the REQUIRED creation date and time when this document was created
	 */
	ZonedDateTime getCreationDateTime();
	
	/**
	 * Gets the meta-data on the sender of the document. This is the party who provided the business data to construct
	 * the envelope and therefore may be a different party than the one that actually creates the envelope as this may 
	 * be done by a <i>Service Provider</i> acting on behalf of the party providing the business data. 
	 * 
	 * @return meta-data on the party that originated the envelope.
	 */
	Party getFrom();

	/**
	 * Gets the meta-data on the intended receiver(s) of the document. These is/are the party/parties who should receive 
	 * the business data contained in the envelope and therefore may be a different than the one(s) that actually 
	 * receive(s) the envelope as this/these may be (a) <i>Service Provider</i> acting on behalf of the party/parties 
	 * who should receive the business data. 
	 * 
	 * @return meta-data on the party/parties to received the envelope
	 */
	Set<Party> getTo();

	/**
	 * Gets the meta-data on the business scrope in which the document is used, e.g. the business process or 
	 * geographical region.
	 * 
	 * @return meta-data of the business scope in which the document is used
	 */
	BusinessScope getBusinessScope();
}
