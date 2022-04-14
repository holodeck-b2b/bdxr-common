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
package org.holodeckb2b.bdxr.smp.datamodel.impl;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.holodeckb2b.bdxr.smp.datamodel.Certificate;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class EndpointInfoImpl extends ExtensibleMetadataClass implements EndpointInfo {

    private String              transportProfile;
    private URL                 endpointURL;
    private ZonedDateTime       serviceActivationDate;
    private ZonedDateTime       serviceExpirationDate;
    private String				description;
    private String				contactInfo;
    private Set<Certificate>	certificates;

    /**
     * Default constructor creates "empty" instance
     */
    public EndpointInfoImpl() {
		this(null, null, null, null, null, null, null, null);
	}

    /**
     * Creates a new instance that only contains the transport profile and URL
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     */
    public EndpointInfoImpl(final String profile, final URL url) {
        this(profile, url, null, null, null, null, null, null);
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param profile			The transport profile
     * @param url				The endpoint's URL
     * @param activationDate	The timestamp from which the endpoint is active
     * @param expirationDate	The timestamp until which the endpoint is active
     * @param description		Human readable text describing the endpoint
     * @param contact			Contact information for this endpoint
	 * @param certs				The meta-data on the certificates used by the endpoint
     * @param exts              Any extra information related to the endpoint
     */
    public EndpointInfoImpl(final String profile, final URL url,
								  final ZonedDateTime activationDate, final ZonedDateTime expirationDate,
								  final String description, final String contact,
								  final Set<Certificate> certs,
								  final List<Extension> exts) {
		super(exts);
        this.transportProfile = profile;
        this.endpointURL = url;
        this.serviceActivationDate = activationDate;
        this.serviceExpirationDate = expirationDate;
        this.description = description;
        this.contactInfo = contact;
        this.certificates = certs;
    }

	/**
	 * Creates a new instance copying the data from the given endpoint object.
	 *
	 * @param src the instance to copy the data from
	 */
    public EndpointInfoImpl(final EndpointInfo src) {
		super(src.getExtensions());
        this.transportProfile = src.getTransportProfile();
        this.endpointURL = src.getEndpointURL();
        this.serviceActivationDate = src.getServiceActivationDate();
        this.serviceExpirationDate = src.getServiceExpirationDate();
        this.description = src.getDescription();
        this.contactInfo = src.getContactInfo();
		this.certificates = (Set<Certificate>) src.getCertificates();
    }

    /**
     * Gets the transport profile the endpoint supports
     *
     * @return The transport profile
     */
	@Override
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
     * @return The Endpoint's URL
     */
	@Override
    public URL getEndpointURL() {
        return endpointURL;
    }

    /**
     * Sets the URL where the endpoint receives messages
     *
     * @param endpointURL The URL to use for the Endpoint
     */
    public void setEndpointURL(URL endpointURL) {
        this.endpointURL = endpointURL;
    }

    /**
     * Gets the date and time after which the endpoint can be used
     *
     * @return The endpoint's activation date
     */
	@Override
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
	@Override
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
     * Gets the (human readable) description of this endpoint.
     *
     * @return	The endpoint's descriptive text
     */
	@Override
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
	@Override
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
     * Gets the certificate information of the endpoint.
     *
     * @return The certificates used by the endpoint
     */
	@Override
    public Set<Certificate> getCertificates() {
        return certificates;
    }

    /**
     * Sets the meta-data on the certificates used by the endpoint
     *
     * @param certs		The meta-data on the certificates
     */
    public void setCertificates(Set<Certificate> certs) {
        this.certificates = certs;
    }

    /**
     * Add meta-data on a certificate to the endpoint meta-data
     *
     * @param cert    The certificate meta-data
     */
    public void addCertificate(final Certificate cert) {
        if (cert == null)
            throw new IllegalArgumentException("Certificate data must be specified");
		if (this.certificates == null)
			this.certificates = new HashSet<>(1);
        this.certificates.add(cert);
    }

    public boolean equals(Object o) {
    	if (o == null || !(o instanceof EndpointInfo))
    		return false;
    	else {
    		EndpointInfo e = (EndpointInfo) o;
    		return super.equals(o)
    			&& Utils.areEqual(certificates, e.getCertificates())
    			&& Utils.nullSafeEqual(contactInfo, e.getContactInfo())
    			&& Utils.nullSafeEqual(description, e.getDescription())
    			&& Utils.nullSafeEqual(serviceActivationDate, e.getServiceActivationDate())
    			&& Utils.nullSafeEqual(serviceExpirationDate, e.getServiceExpirationDate())
    			&& Utils.nullSafeEqual(transportProfile, e.getTransportProfile());
    	}
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(Utils.isNullOrEmpty(certificates) ? null : certificates , contactInfo,
											   description, endpointURL,
											   serviceActivationDate != null ? serviceActivationDate.toInstant() : null,
											   serviceExpirationDate != null ? serviceExpirationDate.toInstant() : null,
											   transportProfile);
		return result;
	}
}
