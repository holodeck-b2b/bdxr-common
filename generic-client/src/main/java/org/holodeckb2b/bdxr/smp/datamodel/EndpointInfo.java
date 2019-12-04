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
package org.holodeckb2b.bdxr.smp.datamodel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.holodeckb2b.bdxr.utils.Utils;


/**
 * Represents the SMP meta-data of one <i>Endpoint</i>.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class EndpointInfo {

    private String                  transportProfile;
    private String                  endpointURL;
    private Boolean                 businessLevelSignatureRequired;
    private String                  minimumAuthenticationLevel;
    private ZonedDateTime           serviceActivationDate;
    private ZonedDateTime           serviceExpirationDate;
    private List<Certificate>	    certificates;
    private String					description;
    private String					contactInfo;
    private List<IExtension>        extensions;

    /**
     * Default constructor creates "empty" instance
     */
    public EndpointInfo() {}

    /**
     * Creates a new instance that only contains the transport profile and URL
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     */
    public EndpointInfo(final String profile, final String url) {
        this(profile, url, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a new instance containing the transport profile, URL and a single certificate
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     * @param cert                      The certificate used by the endpoint
     */
    public EndpointInfo(final String profile, final String url, final Certificate cert) {
        this(profile, url, null, null, null, null, Collections.singletonList(cert), null, null, null);
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     * @param blsRequired               Indicator whether a business level signature is required
     * @param minAuthenticationLvl      Description of the mininum authentication level
     * @param activationDate            The timestamp from which the endpoint is active
     * @param expirationDate            The timestamp until which the endpoint is active
     * @param certs                     The certificates used by the endpoint
     * @param description				Human readable text describing the endpoint
     * @param contact					Contact information for this endpoint  
     * @param ext                       Any extra information related to the endpoint
     */
    public EndpointInfo(final String profile, final String url, final Boolean blsRequired,
                        final String minAuthenticationLvl, final ZonedDateTime activationDate,
                        final ZonedDateTime expirationDate, final List<Certificate> certs,
                        final String description, final String contact,
                        final List<IExtension> ext) {
        this.transportProfile = profile;
        this.endpointURL = url;
        this.businessLevelSignatureRequired = blsRequired;
        this.minimumAuthenticationLevel = minAuthenticationLvl;
        this.serviceActivationDate = activationDate;
        this.serviceExpirationDate = expirationDate;
        this.certificates = certs;
        this.description = description;
        this.contactInfo = contact;
        this.extensions = ext;
    }

    /**
     * Gets the transport profile the endpoint supports
     *
     * @return The transport profile
     */
    public String getTransportProfile() {
        return transportProfile;
    }

    /**
     * Sets the transport profile the endpoint supports
     *
     * @param transportProfile  The transport profile
     */
    public void setTransportProfile(String transportProfile) {
        this.transportProfile = transportProfile;
    }

    /**
     * Gets the URL where the endpoint receives messages.
     *
     * @return The Endpoint's URI
     */
    public String getEndpointURL() {
        return endpointURL;
    }

    /**
     * Sets the URL where the endpoint receives messages
     *
     * @param endpointURL The URL to use for the Endpoint
     */
    public void setEndpointURI(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Indicates whether a business level signature is required.
     *
     * @return  <code>Boolean.TRUE</code> if a business level signature is required, or<br>
     *          <code>Boolean.FALSE</code> if such signature is not required,or<br>
     *          <code>null</code> if not defined
     */
    public Boolean getBusinessLevelSignatureRequired() {
        return businessLevelSignatureRequired;
    }

    /**
     * Sets the indicator whether a business level signature should be used.
     *
     * @param businessLevelSignatureRequired Boolean indicator if business level signature is required, or <code>null
     *                                       </code> if not specified.
     */
    public void setBusinessLevelSignatureRequired(Boolean businessLevelSignatureRequired) {
        this.businessLevelSignatureRequired = businessLevelSignatureRequired;
    }

    /**
     * Gets the minimum authentication level required by this endpoint
     *
     * @return The identifier of the minimum authentication level required, or<br>
     *         <code>null</code> if no such level is defined
     */
    public String getMinimumAuthenticationLevel() {
        return minimumAuthenticationLevel;
    }

    /**
     * Sets the minimum authentication level required by this endpoint
     *
     * @param minimumAuthenticationLevel The identifier of the minimum authentication level required
     */
    public void setMinimumAuthenticationLevel(String minimumAuthenticationLevel) {
        this.minimumAuthenticationLevel = minimumAuthenticationLevel;
    }

    /**
     * Gets the date and time after which the endpoint can be used
     *
     * @return The endpoint's activation date
     */
    public ZonedDateTime getServiceActivationDate() {
        return serviceActivationDate;
    }

    /**
     * Sets the date and time after which the endpoint will be available
     *
     * @param serviceActivationDate The endpoint's activation date
     */
    public void setServiceActivationDate(ZonedDateTime serviceActivationDate) {
        this.serviceActivationDate = serviceActivationDate;
    }

    /**
     * Gets the date and time when the endpoint will expire and should not be used anymore.
     *
     * @return The timestamp when the service expires
     */
    public ZonedDateTime getServiceExpirationDate() {
        return serviceExpirationDate;
    }

    /**
     * Sets the date and time when the endpoint will expire
     *
     * @param serviceExpirationDate The expiration date to set
     */
    public void setServiceExpirationDate(ZonedDateTime serviceExpirationDate) {
        this.serviceExpirationDate = serviceExpirationDate;
    }

    /**
     * Gets the certificate information of the endpoint.
     *
     * @return The certificates used by the endpoint
     */
    public List<Certificate> getCertificates() {
        return certificates;
    }

    /**
     * Returns the first endpoint certificate that is registered for the requested usage. If no certificate is included
     * for the requested usage, the first certificate that has no explicit usage defined is returned. 
     *
     * @param usage	The intended use of the certificate
     * @return		The first endpoint certificate for the requested usage,<br>
     * 				or if that does not exist the first certificate without explicit usage indicator, or <br>
     * 			    <code>null</code> if no such certificate exists either
     */
    public Certificate getCertificateFor(final String usage) {
		if (Utils.isNullOrEmpty(certificates))
			return null;
		else {
			Optional<Certificate> cert = certificates.parallelStream().filter(c -> c.getUsage().contains(usage))
																	 .findFirst();
			if (cert.isPresent())
				return cert.get();
			else {
				// Search for certificate without usage indication
				cert = certificates.parallelStream().filter(c -> c.getUsage().isEmpty()).findFirst();
				return cert.isPresent() ? cert.get() : null;
			}					
		}
    }

    /**
     * Sets the certificates used by the endpoint
     *
     * @param certificates The certificates to set
     */
    public void setCertificates(List<Certificate> certs) {
        this.certificates = certs;
    }

    /**
     * Gets the (human readable) description of this endpoint.
     *  
     * @return	The endpoint's descriptive text
     */
    public String getDescription() {
    	return description;
    }

    /**
     * Sets the (human readable) description of this endpoint.
     *  
     * @param description 	Text to use as endpoint's description 
     */
    public void setDescription(final String description) {
    	this.description = description;
    }
    
    /**
     * Gets the [technical] contact information for this endpoint.
     *  
     * @return	The contact details for this endpoint
     */
    public String getContactInfo() {
    	return contactInfo;
    }

    /**
     * Sets the [technical] contact information for this endpoint.
     *  
     * @param contact	The contact details for this endpoint
     */
    public void setContactInfo(final String contact) {
    	this.contactInfo = contact;
    }
    
    /**
     * Gets the additional, non standard, information related to this endpoint
     *
     * @return The extended meta-data
     */
    public List<IExtension> getExtensions() {
        return extensions;
    }

    /**
     * Sets the additional, non standard, information related to this endpoint
     *
     * @param extension The extended meta-data
     */
    public void setExtensions(List<IExtension> extension) {
        this.extensions = extension;
    }

    /**
     * Add one specific extension to the meta-data
     *
     * @param   ext     The extension to add to set of extended meta-data
     */
    public void addExtension(final IExtension ext) {
        if (ext == null)
            throw new IllegalArgumentException("A extention must be specified");
        if (this.extensions == null)
            this.extensions = new ArrayList<>();
        this.extensions.add(ext);
    }
}
