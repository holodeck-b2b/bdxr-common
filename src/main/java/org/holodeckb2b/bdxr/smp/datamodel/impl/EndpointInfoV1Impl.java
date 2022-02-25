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
import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfoV1;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class EndpointInfoV1Impl extends EndpointInfoImpl implements EndpointInfoV1 {

    private Boolean             businessLevelSignatureRequired;
    private String              minimumAuthenticationLevel;
    private URL					techInfoURL;

    /**
     * Default constructor creates "empty" instance
     */
    public EndpointInfoV1Impl() {
		this(null, null, null, null, null, null, null, null, null, null, null);
	}

    /**
     * Creates a new instance that only contains the transport profile and URL
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     */
    public EndpointInfoV1Impl(final String profile, final URL url) {
        this(profile, url, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a new instance containing the transport profile, URL and a single certificate
     *
     * @param profile                   The transport profile
     * @param url                       The endpoint's URL
     * @param cert                      The certificate used by the endpoint
     */
    public EndpointInfoV1Impl(final String profile, final URL url, final X509Certificate cert) {
		this(profile, url, cert, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param profile               The transport profile
     * @param url                   The endpoint's URL
     * @param cert                  The certificate used by the endpoint
     * @param blsRequired           Indicator whether a business level signature is required
     * @param minAuthenticationLvl  Description of the mininum authentication level
     * @param activationDate        The timestamp from which the endpoint is active
     * @param expirationDate        The timestamp until which the endpoint is active
     * @param description			Human readable text describing the endpoint
     * @param contact				Contact information for this endpoint
	 * @param techInfoUrl			URL to the technical documentation of the endpoint
     * @param exts                  Any extra information related to the endpoint
     */
    public EndpointInfoV1Impl(final String profile, final URL url, final X509Certificate cert, final Boolean blsRequired,
						  final String minAuthenticationLvl, final ZonedDateTime activationDate,
						  final ZonedDateTime expirationDate, final String description, final String contact,
						  final URL techInfoUrl, final List<Extension> exts) {
        super(profile, url, activationDate, expirationDate, description, contact,
			  cert == null ? null : Collections.singleton(new CertificateImpl(cert)), exts);
        this.businessLevelSignatureRequired = blsRequired;
        this.minimumAuthenticationLevel = minAuthenticationLvl;
		this.techInfoURL = techInfoUrl;
    }

	/**
	 * Creates a new instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public EndpointInfoV1Impl(final EndpointInfoV1 src) {
		super(src);
        this.businessLevelSignatureRequired = src.getBusinessLevelSignatureRequired();
        this.minimumAuthenticationLevel = src.getMinimumAuthenticationLevel();
		this.techInfoURL = src.getTechnicalInformationURL();
    }

    /**
     * Indicates whether a business level signature is required.
     *
     * @return  <code>Boolean.TRUE</code> if a business level signature is required, or<br>
     *          <code>Boolean.FALSE</code> if such signature is not required,or<br>
     *          <code>null</code> if not defined
     */
	@Override
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
	@Override
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
	 * Gets the URL to a human readable documentation of the service format.
	 *
	 * @return URL to technical documentation
	 */
	@Override
	public URL getTechnicalInformationURL() {
		return techInfoURL;
	}

	/**
	 * Sets the URL to a human readable documentation of the service format.
	 *
	 * @param techInfoUrl URL to technical documentation
	 */
	public void setTechnicalInformationURL(URL techInfoUrl) {
		this.techInfoURL = techInfoUrl;
	}
}
