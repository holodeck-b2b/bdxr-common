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
package org.holodeckb2b.bdxr.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Is a utility class to find and parse an XML Element in an input stream. If the XML is found its DOM representation
 * will be returned. The search can be limited to a number of elements that should be checked before giving up.   
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since NEXT_VERSION
 */
public class XMLElementReader extends DefaultHandler {
	// The DOM structure being build
    private final Document document;
    private Node currentNode;

    // Name of XML element to extract into W3C DOM Document
    private QName elementToRead;
    // Indicates whether "elementToRead" has been seen yet.
    private boolean parsingActive;

    // The maximum number of element to look at
    private int	maxElements;
    // Number of start elements seen
    int elementsSeen = 0;     
    
    /**
     * Parses the given input stream until the specified element has been seen, after which the entire element
     * is converted into a W3C DOM Element object. If the specified name contains a name space URI, the search will be 
     * name space aware, i.e. both the name space URI and local name of the element must match. If no name space is
     * specified only the local name needs to match.
     *
     * @param is 		input stream to parse
     * @param element 	QName of searched element 
     * @return DOM Element instance of the searched element parsed from input stream, or <code>null</code> if element
     * 		   is not found 
     */
    public static Element parse(InputStream is, QName element) {
    	return parse(is, element, -1);
    }
    
    /**
     * Parses the given input stream until the specified element has been seen, after which the entire element
     * is converted into a W3C DOM Element object. If the requested element has not been seen after reading <code>
     * searchLimit</code> elements parsing is aborted.
     *
     * @param is			input stream to parse
     * @param element		QName of searched element 
	 * @param searchLimit	maximum number of elements to check
     * @return DOM Element instance of the searched element parsed from input stream, or <code>null</code> if element
     * 		   is not found before the given limit
     */
    public static Element parse(InputStream is, QName element, final int searchLimit) {
    	final XMLElementReader processor = new XMLElementReader(element, searchLimit);
    	try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();           
            xmlReader.setContentHandler(processor);
            xmlReader.parse(new InputSource(is));

            return processor.document.getDocumentElement();
        } catch(StopSaxParserException e){
        	return processor.document != null ? processor.document.getDocumentElement() : null;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }  

    /**
     * Creates a new instance of the parser to search for the given element with the given limit of elements to check.
     * 
     * @param element		QName of the element to read
     * @param searchLimit	maximum number of elements to check, -1 indicates no limit 
     */
    private XMLElementReader(final QName element, final int searchLimit) {
    	this.elementToRead = element;
    	this.maxElements = searchLimit;
    	
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.newDocument();
            currentNode = document;
            parsingActive = false;
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }    
    
    @Override
    public void startElement(String uri, String name, String qName, Attributes attrs) throws StopSaxParserException {
        elementsSeen++;

        // If this is the element we want to parse, activate parsing
        parsingActive |= Utils.isNullOrEmpty(elementToRead.getNamespaceURI()) ? 
											        					elementToRead.getLocalPart().equals(name) :
											        					elementToRead.equals(new QName(uri, name));
        	
        // If parsing has not started after START_ELEMENTS_THRESHOLD, we abort parsing.
        if (!parsingActive && maxElements > 0 && elementsSeen >= maxElements)
        	throw new StopSaxParserException();

        if (!parsingActive)
            return;
        
        // Creates the element.
        Element elem = document.createElementNS(uri, qName);

        // Adds each attribute.
        for (int i = 0; i < attrs.getLength(); ++i) {
            final Attr attr = document.createAttributeNS(attrs.getURI(i), attrs.getQName(i));
            attr.setValue(attrs.getValue(i));
            elem.setAttributeNodeNS(attr);
        }

        // Appends the element into the DOM tree
        currentNode.appendChild(elem);
        currentNode = elem;
    }

    @Override
    public void endElement(String uri, String name, String qName) throws StopSaxParserException {
        if (!parsingActive)
            return;
        
        currentNode = currentNode.getParentNode();
        if ((Utils.isNullOrEmpty(elementToRead.getNamespaceURI()) && elementToRead.getLocalPart().equals(name))
        	|| elementToRead.equals(new QName(uri, name)))
        	throw new StopSaxParserException();        
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (!parsingActive)
            return;
        
        currentNode.appendChild(document.createTextNode(new String(ch, start, length)));
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) {
        if (!parsingActive)
            return;
        
        currentNode.appendChild(document.createTextNode(new String(ch, start, length)));
    }

    @Override
    public void processingInstruction(String target, String data) {
        if (!parsingActive)
            return;
        
        currentNode.appendChild(document.createProcessingInstruction(target, data));
    }

    @Override
    public void error(SAXParseException e) {
        throw new IllegalStateException("Error at line " + e.getLineNumber() + ", column " + e.getColumnNumber() 
        								+ " : " + e.getMessage(), e);
    }

    @Override
    public void fatalError(SAXParseException e) {
        throw new IllegalStateException("Fatal error at line " + e.getLineNumber() + ", column " + e.getColumnNumber()
        								+ " : " + e.getMessage(), e);
    }

    @Override
    public void warning(SAXParseException e) {
    }

    /**
     * Custom Exception used to abort the SAX call back parser.
     */
    @SuppressWarnings("serial")
    private class StopSaxParserException extends SAXException {
    }
	
}
