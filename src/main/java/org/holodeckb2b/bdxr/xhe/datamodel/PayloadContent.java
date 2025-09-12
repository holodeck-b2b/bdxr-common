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

import org.w3c.dom.Element;

/**
 * Represents the content of a payload included in a <code>Payload</code> instance contained in the XHE envelope. See
 * <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-PAYLOAD-CONTENT">section 5.6.3 of the 
 * OASIS XHE Standard</a>. As the payload content can basically be any kind of data, the type of the content is defined
 * as a generic type parameter. 
 * 
 * @param T the type representing the content of the payload
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0 
 */
public interface PayloadContent<T> {

	/**
	 * Gets the object representation of the content of the payload.
	 * 
	 * @return object representing the payload's content
	 */
	T getContent();
	
	/**
	 * Gets the XML representation of the content of this payload. This is the element that will be included as child
	 * element of the <code>PayloadContent</code> element in the XHE envelope. 
	 * 
	 * @return the XML representation of the payload's content
	 */
	Element toXML();
}
