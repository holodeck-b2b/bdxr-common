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
package org.holodeckb2b.bdxr.smp.processing;

import com.chasquismessaging.commons.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.holodeckb2b.bdxr.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.datamodel.ServiceMetadataResult;
import org.holodeckb2b.bdxr.smp.SMPQueryException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Is the component responsible for parsing the SMP response into a XML document and finding the right {@link
 * ISMPResultProcessor} to convert the XML document into a object representation of the SMP result.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SMPResultReader {
    private static final Logger	log = LogManager.getLogger(SMPResultReader.class);

    /**
     * Namespace URI of XML-dsig
     */
    private static final String XMLDSIG_NS = "http://www.w3.org/2000/09/xmldsig#";
    /**
     * Local name of the XML element containing the signature
     */
    private static final String XMLDSIG_SIGNATURE = "Signature";

    /**
     * Map of namespace URI to the {@link ISMPResultProcessor} implementation that can convert XML documents with that
     * namespace to object representation
     */
    private static final Map<String, ISMPResultProcessor>   PROCESSORS;
    static {    	
        PROCESSORS = new HashMap<>(2);
        PROCESSORS.put(PEPPOLResultProcessor.NAMESPACE_URI, new PEPPOLResultProcessor());
    }

    /**
     * Processes the SMP response and converts it into the object representation.
     *
     * @param is    The input stream that contains the SMP response
     * @return      A {@link ISMPQueryResult} instance that represent the response received from the SMP server
     * @throws SMPQueryException    When the SMP server returned an invalid response.
     * @throws IOException          When the input stream could not be completely processed.
     */
    public static ISMPQueryResult handleResponse(final InputStream is) throws SMPQueryException, IOException {
        Document xmlResult;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            log.debug("Parsing the SMP response");
            xmlResult = db.parse(is);
            log.debug("Successfully parsed the SMP response into XML document");
        } catch (ParserConfigurationException | SAXException parsingError) {
            log.error("Could not parse the XML returned by the SMP server! Details: {}" + parsingError.getMessage());
            throw new SMPQueryException("Invalid response from SMP server!", parsingError);
        }

        final Element rootElement = xmlResult.getDocumentElement();
        final NodeList signatures = rootElement.getElementsByTagNameNS(XMLDSIG_NS, XMLDSIG_SIGNATURE);
        X509Certificate signingCert = null;
        if (signatures.getLength() > 0) {
            if (signatures.getLength() > 1)
                log.warn("Response is signed more than once, using first signature");
            signingCert = verifySignature(xmlResult, (Element) signatures.item(0));
        }

        // Get the name space of the root element to determine the correct result processor
        final String resultNamespace = rootElement.getNamespaceURI();
        log.debug("Finding processor for namespace URI of SMP response: {}", resultNamespace);
        ISMPResultProcessor processor = PROCESSORS.get(resultNamespace);
        if (processor == null) {
            log.error("Could not find a suitable result processor for SMP response with namespace {}", resultNamespace);
            throw new SMPQueryException("Unknown XML document received from SMP server!");
        }
        log.debug("Using {} processor to convert XML into object representation", processor.getClass().getName());
        ISMPQueryResult objResult = processor.processResult(xmlResult);
        log.debug("Response meta-data successfully converted into object model representation");
        // Process signature of meta-data if available
        if (signingCert != null) {
            log.debug("Response was signed, add signature meta-data to the result meta-data");            
            // If the response was signed it must have been a ServiceMetadata
            ((ServiceMetadataResult) objResult).setSignerCertificate(signingCert);            
        }

        log.debug("Successfully converted the XML format to object model");

        return objResult;
    }

    /**
     * Validates the signature that was placed on the SMP result.
     * 
     * @param xmlResultDoc			The complete XML result document 
     * @param signatureElement		The XML element containing the signature to be validated
     * @return	The X509 Certificate used for signing the SMP result.
     * @throws SMPQueryException 	When the signature could not be validated
     */
    private static X509Certificate verifySignature(final Document xmlResultDoc, final Element signatureElement) 
    																					throws SMPQueryException {
        try {
            DefaultKeySelector keySelector = new DefaultKeySelector();
            log.debug("Preparing context for signature verification");
            DOMValidateContext valContext = new DOMValidateContext(keySelector, signatureElement);
            XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");
            XMLSignature signature = xmlSignatureFactory.unmarshalXMLSignature(valContext);
            log.debug("Verifying the signature...");
            if (!signature.validate(valContext)) {
            	log.error("The signature on the SMP result could not be verified!");
                throw new SMPQueryException("The signature on the SMP result could not be verified!");
            }

            log.debug("Signature succesfully verified.");
            return keySelector.getCertificate();        
        } catch (XMLSignatureException | MarshalException verificationFailed) {
            log.error("An error occurred during signature verification!\n\tDetails: {}", 
            			Utils.getExceptionTrace(verificationFailed));
            throw new SMPQueryException("Unable to verify signature.", verificationFailed);
        }        
    }
}
