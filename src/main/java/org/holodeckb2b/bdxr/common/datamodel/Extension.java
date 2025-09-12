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
import java.net.URI;

import org.w3c.dom.Element;

/**
 * Represents an <i>Extension</i> which can be used to describe non standardised meta-data. Although both the XHE and 
 * the OASIS SMP v2 specification define some generic meta-data fields for extensions, their use is optional and depends 
 * on the use-case. Therefore this interface only defines a methods to get the identifier and content of the Extension.
 * Other methods may be added by implementations. Because the content of the extension is not known this interface is 
 * generic and uses the content's class as type parameter.
 * <p>Corresponds to the <code>SMPExtension</code> and <code>XHEExtension</code> classes of the OASIS SMP V2 and XHE 
 * Standards' data models.
 *
 * @param T the type representing the content of the extension
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0 In package <code>org.holodeckb2b.bdxr.common.datamodel</code>
 */
public interface Extension<T> extends Serializable {
	
	/**
	 * Gets the identifier of this extension which identifies it within the document, e.g. an XHE or SMP response, in 
	 * which it is contained. The identifier is assigned by the entity that created this instance of the extension. 
	 * 
	 * @return	identifier of the extension within the document
	 */
	Identifier getId();
	
	/**
	 * Gets the descriptive name of this extension which describes its use within the document, e.g. an XHE, in 
	 * which it is contained. The identifier is assigned by the entity that created this instance of the extension. 
	 * 
	 * @return	descriptive name of the extension within the document
	 */	
	Text getName();

	/**
	 * Gets the code that indicates why this extension was added to the document.
	 * 
	 * @return	code that indicates why the extension was added
	 */
	Code getReasonCode();
	
	/**
	 * Gets the URI of this extension which identifies the extension amongst other extensions outside of any document,
	 * i.e. identifies the extension type. This URI is assigned by the creator of the extension, i.e. the entity that 
	 * defined it. Therefore it is fixed for all instances of the same extension definition.
	 * 
	 * @return	URI that globally identifies the extension
	 */
	URI getExtensionURI();		
	
	/**
	 * Gets the object representation of the content of the extension.
	 * 
	 * @return object representing the extension's content
	 */
	T getContent();
	
	/**
	 * Gets the XML representation of the content of this extension. This is the element that will be included as child
	 * element of the <code>ExtensionContent</code> element in CCTS based XML documents like XHE and OASIS SMP v2 or
	 * directly as child of the <code>Extension</code> element in the OASIS SMP v1 or Peppol SMP specification. 
	 * 
	 * @return the XML representation of the extension's content
	 */
	Element toXML();
}
