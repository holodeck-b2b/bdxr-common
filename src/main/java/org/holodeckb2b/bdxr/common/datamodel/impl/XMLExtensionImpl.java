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
package org.holodeckb2b.bdxr.common.datamodel.impl;

import java.net.URI;
import java.util.Objects;

import org.holodeckb2b.bdxr.common.datamodel.Code;
import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.Text;
import org.w3c.dom.Element;

/**
 * Is a basic implementation of the {@link Extension} interface that uses the {@link Element} class as content type. 
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class XMLExtensionImpl implements Extension<Element> {
	private static final long serialVersionUID = 9172213401089567372L;
	
	private Identifier 	id;
	private Text 		name;
	private Code 		reasonCode;
	private URI 		uri;	
	private Element 	content;
	
	/**
	 * Creates an extension instance with no content
	 */
	public XMLExtensionImpl() {}
	
	/**
	 * Creates an Extension instance with the given XML element as content.
	 * 
	 * @param content	XML element to use as content for this Extension
	 */
	public XMLExtensionImpl(Element content) {
		this.content = content;
	}
	
	@Override
	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}

	@Override
	public Text getName() {
		return name;
	}

	public void setName(Text name) {
		this.name = name;
	}

	@Override
	public Code getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Code reasonCode) {
		this.reasonCode = reasonCode;
	}

	@Override
	public URI getExtensionURI() {
		return uri;
	}

	public void setExtensionURI(URI uri) {
		this.uri = uri;
	}

	public void setContent(Element content) {
		this.content = content;
	}

	@Override
	public Element getContent() {
		return content;
	}
	
	@Override
	public Element toXML() {
		return content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, reasonCode, uri);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLExtensionImpl other = (XMLExtensionImpl) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(reasonCode, other.reasonCode) && Objects.equals(uri, other.uri);
	}
}
