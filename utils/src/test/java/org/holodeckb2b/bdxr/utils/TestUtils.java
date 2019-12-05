package org.holodeckb2b.bdxr.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Some test utilities.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class TestUtils {

    /**
     * Returns a multi platform path to the specified resource.
     * 
     * @param resourceName the name of the resource, which path we want to get
     * @return path to the resource
     * @throws FileNotFoundException when the resource path could not be created
     */
    public static Path getPath(String resourceName) throws FileNotFoundException {
        try {
            return Paths.get(TestUtils.class.getClassLoader().getResource(resourceName).toURI());
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }   
    
    /**
     * Gets the XML Document from the specified resource 
     * 
     * @param resourceName the name of the resource, which path we want to get
     * @return the DOM representation of the XML contained in the resource
     * @throws IOException when the resource could not be read
     * @throws IllegalArgumentException when the resource does not contain well formed XML
     */
    public static Document getXMLFromFile(String resourceName) throws IOException {
        try (FileInputStream fis = new FileInputStream(getPath(resourceName).toFile())) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(fis);
        } catch (ParserConfigurationException | SAXException parsingError) {
            throw new IllegalArgumentException("File does not contain well formed XML");
        } 
    }
}
