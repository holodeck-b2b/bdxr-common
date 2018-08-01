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
package org.holodeckb2b.bdxr.datamodel;

import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the certificate data included in a SMP response. This consists of the X509 certificate itself and an
 * indication of how this certificate can be used. Note that the certificates included in the SMP registration are 
 * always including only the <b>public key</b>, so an indication that the certificate is used for <i>signing</i> means
 * that the participant will use this certificate for signing the messages it sends and has no effect on the message
 * send to the participant! This certificate could be used for verification of acknowledgements like the AS4 Receipt.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Certificate {

	/**
	 * Enumerates the ways the certificate may be used 
	 */
	public enum Usage { Signing, Encryption };
	
	private X509Certificate	certificate;
	private Set<Usage>		usage;
		
	/**
	 * Creates a new <code>Certificate</code> instance with the given X509 Certificate and usage indication. If no 
	 * usage indication is given the certificate can be used for both signing and encryption.
	 * 
	 * @param cert		The X509 Certificate 
	 * @param usages		Indication how this certificate is used as a variable array of indicators.
	 */
	public Certificate(final X509Certificate cert, final Usage... usages ) {
		this.certificate = cert;
		this.usage = new HashSet<>();		
		if (usages.length == 0) {
			usage.add(Usage.Signing);
			usage.add(Usage.Encryption);
		} else
			for (Usage u : usages)
				this.usage.add(u);		
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
	public Set<Usage> getUsage() {
		return usage;
	}
	
	/**
	 * Sets the indication how this certificate may be used
	 * 
	 * @param usages		variable array of usage indicators, must include at least one
	 */
	public void setUsage(final Usage... usages) {
		if (usages == null || usages.length == 0) 
			throw new IllegalArgumentException("At least on usage indication should be given");	
		this.usage = new HashSet<>();		
		for (Usage u : usages)
			this.usage.add(u);		
	}
}

