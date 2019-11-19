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
package org.holodeckb2b.bdxr.impl.oasis_smp2;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.busdox.servicemetadata.publishing._1.ServiceInformationType;
import org.busdox.servicemetadata.publishing._1.SignedServiceMetadataType;
import org.holodeckb2b.bdxr.api.ISMPResultProcessor;
import org.holodeckb2b.bdxr.api.SMPQueryException;
import org.holodeckb2b.bdxr.datamodel.Certificate;
import org.holodeckb2b.bdxr.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.datamodel.IExtension;
import org.holodeckb2b.bdxr.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.datamodel.Identifier;
import org.holodeckb2b.bdxr.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.datamodel.Redirection;
import org.holodeckb2b.bdxr.datamodel.ServiceInformation;
import org.holodeckb2b.bdxr.datamodel.ServiceMetadataResult;
import org.holodeckb2b.bdxr.utils.CertificateHelper;
import org.oasis_open.docs.bdxr.ns.smp._2.aggregatecomponents.EndpointType;
import org.oasis_open.docs.bdxr.ns.smp._2.aggregatecomponents.ProcessMetadataType;
import org.oasis_open.docs.bdxr.ns.smp._2.aggregatecomponents.ProcessType;
import org.oasis_open.docs.bdxr.ns.smp._2.aggregatecomponents.RedirectType;
import org.oasis_open.docs.bdxr.ns.smp._2.servicegroup.ServiceGroupType;
import org.oasis_open.docs.bdxr.ns.smp._2.servicemetadata.ServiceMetadataType;
import org.w3c.dom.Document;

/**
 * Is the {@link ISMPResultProcessor} implementation that handles the XML format as defined in the OASIS SMP 
 * version 2.0 specification.    
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since  NEXT_VERSION
 */
public class OASISv2ResultProcessor implements ISMPResultProcessor {
    private static final Logger	log = LogManager.getLogger(OASISv2ResultProcessor.class);

    /**
     * The JAXB context for the conversion of XML into Java objects
     */
    private static final JAXBContext jaxbContext;
    static {
        try {
            // Initialize the JAXB Context used in processing of the PEPPOL responses
            jaxbContext = JAXBContext.newInstance(ServiceGroupType.class, ServiceMetadataType.class);
        } catch (JAXBException jaxbFailure) {
            log.fatal("Could not load the JAXB Context required for processing SMP response! Details: {}",
                      jaxbFailure.getMessage());
            throw new RuntimeException(jaxbFailure.getMessage(), jaxbFailure);
        }
    }

    /**
     * The namespace URI of the SMP XML <i>ServiceMetadata</i> document as specified in the OASIS SMP V2 specification
     */
    public static final String SVC_METADATA_NS_URI = "http://docs.oasis-open.org/bdxr/ns/SMP/2/ServiceMetadata";
    /**
     * The namespace URI of the SMP XML <i>ServiceGroup</i> document as specified in the OASIS SMP V2 specification
     */
    public static final String SVC_GROUP_NS_URI = "http://docs.oasis-open.org/bdxr/ns/SMP/2/ServiceGroup";
        
    @Override
    public boolean canProcess(final String namespaceURI) {
    	return SVC_METADATA_NS_URI.equals(namespaceURI) || SVC_GROUP_NS_URI.equals(namespaceURI);
    }

    @Override	
    public ISMPQueryResult processResult(Document xmlDocument) throws SMPQueryException {
        JAXBElement jaxbDoc;
        try {
            log.debug("Convert the XML into Java objects");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            jaxbDoc = (JAXBElement) unmarshaller.unmarshal(xmlDocument);
            log.debug("XML converted into Java objects");
        } catch (JAXBException parsingError) {
            log.error("Could not convert the XML document into Java objects! Details: {}", parsingError.getMessage());
            throw new SMPQueryException("XML could not be parsed as PEPPOL SMP result");
        }

        // Now check the root element of the response and process accordingly
        final Class rootType = jaxbDoc.getDeclaredType();
        if (ServiceGroupType.class.equals(rootType)) {
            log.debug("Document is a Service Group response");
            //@todo: Handle Service Group responses
            return null;
        } else {
            log.debug("Document is a Service Metadata response");
            ServiceMetadataType svcMetadata = (ServiceMetadataType) jaxbDoc.getValue();
            final ServiceInformation svcInfo = new ServiceInformation();
            svcInfo.setParticipantId(new Identifier(svcMetadata.getParticipantID().getValue(),
                                                    svcMetadata.getParticipantID().getSchemeID()));
            svcInfo.setServiceId(new Identifier(svcMetadata.getID().getValue(), svcMetadata.getID().getSchemeID()));
            // Convert the list of ProcessMetadata element
            for(ProcessMetadataType p : svcMetadata.getProcessMetadata())
                svcInfo.addProcessInformation(processProcessMetadata(p));

            svcInfo.setExtensions(handleServiceMetadataExtensions(svcMetadata.getSMPExtensions()));
            
            if (svcMetadata. != null) {
                log.debug("Service Metadata contain a Redirect");
                result = processRedirection(svcMetadata.getRedirect());
            } else {
                log.debug("Service Metadata contain ServiceInformation");
                result = processServiceInformation(svcMetadata.getServiceInformation());
            }
            log.debug("Completely processed the response document");
            return result;
        }
    }
    
    /**
     * Processes the <i>service meta-data</i> included in the <code>ServiceInformation</code> element of the response.
     *
     * @param redirectXML   The JAXB representation of the <code>Redirect</code> element of the response
     * @return  A {@link Redirection} instance with the information on the redirection
     * @throws SMPQueryException When there is a problem in converting the XML into the object model
     */
    private Redirection processRedirection(RedirectType redirectXML) throws SMPQueryException {
    	try {
    		final Redirection redirectionInfo = new Redirection(new URI(redirectXML.getHref()));
            redirectionInfo.setExtensions(handleRedirectionExtensions(redirectXML.getExtension()));
    		return redirectionInfo;
    	} catch (NullPointerException | URISyntaxException invalidURL) {
    		log.error("The Redirection response includes an invalid new target URL: {}", redirectXML.getHref());
    		throw new SMPQueryException("Invalid redirection response received!");
    	}
    }    

    /**
     * Converts the <code>Extension</code> child elements of the <code>Redirection</code> element into the object 
     * representation.
     * <p><b>NOTE: </b> This default implementation <b>ignores</b> all included extensions. If a network uses 
     * extension you should create a descendant class and override this method to correctly handle the network 
     * specific extensions.
     * 
	 * @param extensions	The extension included with the <code>Redirection</code> element
	 * @return				The object representation of the extensions
	 */
	protected List<IExtension> handleRedirectionExtensions(List<ExtensionType> extensions) {
		return null;
	}

    /**
     * Converts the <code>Extension</code> child elements of the <code>ServiceInformation</code> element into the 
     * object representation.
     * <p><b>NOTE: </b> This default implementation <b>ignores</b> all included extensions. If a network uses 
     * extension you should create a descendant class and override this method to correctly handle the network 
     * specific extensions.
     * 
	 * @param extensions	The extension included with the <code>ServiceInformation</code> element
	 * @return				The object representation of the extensions
	 */
	protected List<IExtension> handleServiceInfoExtensions(List<ExtensionType> extensions) {
		return null;
	}
	
    /**
     * Converts the information contained in the <code>ProcessMetadata</code> element into a <code>ProcesInfo</code> 
     * instance.
     *
     * @param procMetadataXML   The JAXB representation of the <code>ProcessMetadata</code> element
     * @return              The <code>ProcesInfo</code> representation of the meta-data
     * @throws SMPQueryException When there is a problem in converting the Process XML into the object model
     */
    private ProcessInfo processProcessMetadata(ProcessMetadataType procMetadataXML) throws SMPQueryException {
        final ProcessInfo procInfo = new ProcessInfo();

        for(ProcessType pi : procMetadataXML.getProcess()) {
        	procInfo.addProcessId(new Identifier(pi.getID().getValue(), pi.getID().getSchemeID()));
        }
        // Convert the Endpoint elements into object model
        for(EndpointType ep : procMetadataXML.getServiceEndpointList().getEndpoint())
            procInfo.addEndpoint(processEndpoint(ep));

        procInfo.setExtensions(handleProcessInfoExtensions(procMetadataXML.getExtension()));
        
        return procInfo;
    }

    /**
     * Converts the <code>Extension</code> child elements of the <code>Process</code> element into the object 
     * representation.
     * <p><b>NOTE: </b> This default implementation <b>ignores</b> all included extensions. If a network uses 
     * extension you should create a descendant class and override this method to correctly handle the network 
     * specific extensions.
     * 
	 * @param extensions	The extension included with the <code>Process</code> element
	 * @return				The object representation of the extensions
	 */
	protected List<IExtension> handleProcessInfoExtensions(List<ExtensionType> extensions) {
		return null;
	}
	
    /**
     * Converts the information contained in an <code>Endpoint</code> element into an <code>EndpointInfo</code> instance.
     *
     * @param epInfoXML   The JAXB representation of the <code>Endpoint</code> element
     * @return            The <code>EndpointInfo</code> representation of the meta-data
     * @throws SMPQueryException When the XML certificate string could not be converted into object representation
     */
    private EndpointInfo processEndpoint(EndpointType epInfoXML) throws SMPQueryException {
        final EndpointInfo epInfo = new EndpointInfo();

        epInfo.setTransportProfile(epInfoXML.getTransportProfile());
        epInfo.setEndpointURI(epInfoXML.getEndpointURI());
        epInfo.setBusinessLevelSignatureRequired(epInfoXML.isRequireBusinessLevelSignature());
        epInfo.setMinimumAuthenticationLevel(epInfoXML.getMinimumAuthenticationLevel());
        final XMLGregorianCalendar svcActivationDate = epInfoXML.getServiceActivationDate();
        if (svcActivationDate != null)
            epInfo.setServiceActivationDate(svcActivationDate.toGregorianCalendar().toZonedDateTime());
        final XMLGregorianCalendar svcExpirationDate = epInfoXML.getServiceExpirationDate();
        if (svcExpirationDate != null)
            epInfo.setServiceExpirationDate(svcExpirationDate.toGregorianCalendar().toZonedDateTime());
        try {
            epInfo.setCertificates(Collections.singletonList(
                                      new Certificate(CertificateHelper.getCertificate(epInfoXML.getCertificate()))));
        } catch (CertificateException certReadError) {
            log.error("Could not read the Certificate from the SMP response! Details: {}", certReadError.getMessage());
            throw new SMPQueryException("Could not read the Certificate from the SMP response");
        }
        epInfo.setDescription(epInfoXML.getServiceDescription());
        epInfo.setContactInfo(epInfoXML.getTechnicalContactUrl());
        
        epInfo.setExtensions(handleEndpointInfoExtensions(epInfoXML.getExtension()));

        return epInfo;
    }

    /**
     * Converts the <code>Extension</code> child elements of the <code>Endpoint</code> element into the object 
     * representation.
     * <p><b>NOTE: </b> This default implementation <b>ignores</b> all included extensions. If a network uses 
     * extension you should create a descendant class and override this method to correctly handle the network 
     * specific extensions.
     * 
	 * @param extensions	The extension included with the <code>Endpoint</code> element
	 * @return				The object representation of the extensions
	 */
	protected List<IExtension> handleEndpointInfoExtensions(List<ExtensionType> extensions) {
		return null;
	}    
}
