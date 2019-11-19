/*
 * Copyright (C) 2019 The Holodeck B2B Team
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
package org.holodeckb2b.bdxr.sbdh;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.holodeckb2b.bdxr.utils.XMLElementReader;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocument;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocumentHeader;
import org.w3c.dom.Element;

/**
 * 
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since  NEXT_VERSION
 */
public class SBDHParser {
	
	private static final QName  Q_SBDH = new QName(
												"http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader",
												"StandardBusinessDocumentHeader");
	
	private static JAXBContext	jaxbContext;
	
	static {
        try {
            jaxbContext = JAXBContext.newInstance(StandardBusinessDocument.class, StandardBusinessDocumentHeader.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
	
	/**
	 * Reads up to the given number of bytes from the given input stream to parse the SBDH from it and on request resets 
	 * the stream afterwards. 
	 * 
	 * @param is		input stream containing the SBD
	 * @param limit 	maximum number of bytes to read from the stream
	 * @param reset 	indicator whether the stream should be reset after reading the SBDH. NOTE: this requires that
	 * 					the given input stream supports the mark operation.  
	 * @return			The parsed SBDH if found, <code>null</code> if none is found within the given limit
	 * @throws IllegalArgumentException	When the given stream does not support the mark operation and a reset is 
	 * 									requested or when the given stream does not contain a XML document	
	 */
	public static StandardBusinessDocumentHeader parseSBDHOnly(InputStream is, final int limit, final boolean reset) {
		
		if (reset && !is.markSupported())
			throw new IllegalArgumentException("Reset not available on given stream");
		
		// Wrap stream in buffered input stream to enable limit on number of bytes to read
		if (!is.markSupported())
			is = new BufferedInputStream(is, limit);		
		is.mark(limit);
		
		// Try to read the SBDH element from the stream
		final Element sbdhElement;
		sbdhElement = XMLElementReader.parse(is, Q_SBDH);
		
		if (sbdhElement == null)
			return null;
		
		// Convert into JAXB representation
		StandardBusinessDocumentHeader sbdh = null;
		try {
			sbdh = jaxbContext.createUnmarshaller()
							  .unmarshal(sbdhElement, StandardBusinessDocumentHeader.class).getValue();
		} catch (JAXBException e) {
			throw new IllegalArgumentException("Stream does not contain a SBDH (within given limit)");
		}
		
		if (reset)
			try {
				is.reset();
			} catch (IOException e) {
				throw new IllegalStateException("Unable to reset intput stream" + e, e);
			}
		
		return sbdh;		
	}	
}
