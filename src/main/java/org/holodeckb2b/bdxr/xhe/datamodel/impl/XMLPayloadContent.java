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
package org.holodeckb2b.bdxr.xhe.datamodel.impl;

import org.holodeckb2b.bdxr.xhe.datamodel.PayloadContent;
import org.w3c.dom.Element;

/**
 * Is a basic implementation of the {@link PayloadContent} interface that simply wraps the XML element containing the 
 * payload's content. 
 *  
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class XMLPayloadContent implements PayloadContent<Element> {

	private Element		content;

	public XMLPayloadContent(Element content) {
		this.content = content;
	}
	
	@Override
	public Element getContent() {
		return content;
	}

	/**
	 * Sets the XML element with the payload's content
	 * 
	 * @param content	the XML element containing the payload content
	 */
	public void setContent(Element content) {
		this.content = content;
	}
	
	@Override
	public Element toXML() {
		return content;
	}
}
