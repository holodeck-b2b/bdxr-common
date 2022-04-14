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

import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import org.holodeckb2b.bdxr.smp.datamodel.Certificate;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class CertificateImpl extends ExtensibleMetadataClass implements org.holodeckb2b.bdxr.smp.datamodel.Certificate {

	private X509Certificate	certificate;
	private String			usage;
	private String			description;
	private ZonedDateTime	activation;
	private ZonedDateTime	expiration;

	/**
	 * Creates a new <code>Certificate</code> instance with the given X509 Certificate and non usage indication.
	 *
	 * @param cert		The X509 Certificate
	 */
	public CertificateImpl(final X509Certificate cert) {
		this(cert, null, null, null, null, null);
	}

	/**
	 * Creates a new <code>Certificate</code> instance with the given X509 Certificate and usage indication. If no
	 * usage indication is given the certificate can be used for both signing and encryption.
	 *
	 * @param cert		The X509 Certificate
	 * @param usage		Indication how this certificate is used
	 */
	public CertificateImpl(final X509Certificate cert, final String usage) {
		this(cert, usage, null, null, null, null);
	}

	/**
	 * Creates a new <code>Certificate</code> instance with the given meta-data
	 *
	 * @param cert			The X509 Certificate
	 * @param usage			Indication how this certificate is used
	 * @param activation	the activation time stamp of the certificate.
	 * @param expiration	the time stamp until which the certificate can be used
	 * @param description	description of the certificate
	 * @param exts			additional meta-data to be added as extensions
	 */
	public CertificateImpl(final X509Certificate cert, final String usage, final ZonedDateTime activation,
						final ZonedDateTime expiration, final String description, List<Extension> exts) {
		super(exts);
		this.certificate = cert;
		this.usage = usage;
		this.activation = activation;
		this.expiration = expiration;
		this.description = description;
	}

	/**
	 * Creates a new <code>Certificate</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
	public CertificateImpl(final org.holodeckb2b.bdxr.smp.datamodel.Certificate src) {
		this.certificate = src.getX509Cert();
		this.usage = src.getUsage();
		this.description = src.getDescription();
		this.activation = src.getActivationDate();
		this.expiration = src.getExpirationDate();
	}

	/**
	 * Gets the X509Certificate.
	 *
	 * @return	the certificate
	 */
	@Override
	public X509Certificate getX509Cert() {
		return certificate;
	}

	/**
	 * Sets the X509Certificate.
	 *
	 * @return	the certificate
	 */
	public void setX509Cert(final X509Certificate cert) {
		this.certificate = cert;
	}

	/**
	 * Returns the indication how this certificate can be used
	 *
	 * @return	A set of usage indicators
	 */
	@Override
	public String getUsage() {
		return usage;
	}

	/**
	 * Sets the indication how this certificate may be used
	 *
	 * @param usage		usage indicator
	 */
	public void setUsage(final String usage) {
		this.usage = usage;
	}

	/**
	 * Gets the date from which on the certificate can be used.
	 * <p>NOTE: This activation date can be different from the date the certificate itself is valid.
	 *
	 * @return	the activation date of the certificate.
	 */
	@Override
	public ZonedDateTime getActivationDate() {
		return activation;
	}

	/**
	 * Sets the date from which on the certificate can be used.
	 *
	 * @param activation	the activation date of the certificate.
	 */
	public void setActivationDate(final ZonedDateTime activation) {
		this.activation = activation;
	}

	/**
	 * Gets the date until which the certificate can be used.
	 * <p>NOTE: This date can be before the date the certificate itself expires to indicate it just should not be used
	 * anymore.
	 *
	 * @return	the date from which on the certificate should not be used
	 */
	@Override
	public ZonedDateTime getExpirationDate() {
		return expiration;
	}

	/**
	 * Sets the date from which on the certificate should not be used anymore.
	 *
	 * @param expiration	the expiration date of the certificate.
	 */
	public void setExpirationDate(final ZonedDateTime expiration) {
		this.expiration = expiration;
	}

	/**
	 * Gets the descriptive text on the certificate.
	 *
	 * @return the description of the certificate
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the descriptive text on the certificate.
	 *
	 * @param description	the description of the certificate
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Certificate))
			return false;

		Certificate c = (Certificate) o;
		return super.equals(o)
			&& Utils.nullSafeEqual(this.certificate, c.getX509Cert())
			&& Utils.nullSafeEqual(this.usage, c.getUsage())
			&& Utils.nullSafeEqual(this.activation, c.getActivationDate())
			&& Utils.nullSafeEqual(this.expiration, c.getExpirationDate())
			&& Utils.nullSafeEqual(this.description, c.getDescription());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(activation != null ? activation.toInstant() : null,
												certificate, description,
												expiration != null ? expiration.toInstant() : null, usage);
		return result;
	}
}

