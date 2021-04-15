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

import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the certificate data included in a SMP response consisting of the X509 certificate itself and associated 
 * meta-data. Part of the meta-data is an indication of how the certificate should be or is used, for example for 
 * encryption or signing. It should be noted that when the Certificate is included as part of Endpoint meta-data and
 * has an indication of "for signing" it applies to message send by the participant and not to messages send to it! In
 * other words the certificate can be used to verify that it was indeed the participant that responded.     
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Certificate {
	
	private X509Certificate	certificate;
	private Set<String>		usage;
	private String			description;
	private ZonedDateTime	activation;
	private ZonedDateTime	expiration;	
		
	/**
	 * Creates a new <code>Certificate</code> instance with the given X509 Certificate and usage indication. If no 
	 * usage indication is given the certificate can be used for both signing and encryption.
	 * 
	 * @param cert		The X509 Certificate 
	 * @param usages	Indication how this certificate is used as a variable array of indicators.
	 */
	public Certificate(final X509Certificate cert, final String... usages) {
		this.certificate = cert;
		this.usage = new HashSet<>(usages.length);
		for (String u : usages)
			this.usage.add(u);
	}
	
	/** 
	 * Creates a new <code>Certificate</code> instance copying the data from the given instance.
	 *  
	 * @param src the instance to copy the data from
	 * @since 2.0.0
	 */
	public Certificate(final Certificate src) {
		this.certificate = src.certificate;
		this.usage = new HashSet<>(src.getUsage());
		this.description = src.description;
		this.activation = src.activation;
		this.expiration = src.expiration;
	}
	
	/**
	 * Gets the X509Certificate.
	 * 
	 * @return	the certificate
	 */
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
	public Set<String> getUsage() {
		return usage;
	}
	
	/**
	 * Sets the indication how this certificate may be used
	 * 
	 * @param usages		variable array of usage indicators, must include at least one
	 */
	public void setUsage(final String... usages) {
		this.usage = new HashSet<>(usages.length);
		for (String u : usages)
			this.usage.add(u);	
	}
	
	/**
	 * Gets the date from which on the certificate can be used.
	 * <p>NOTE: This activation date can be different from the date the certificate itself is valid.
	 * 
	 * @return	the activation date of the certificate. Although 
	 */
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
}

