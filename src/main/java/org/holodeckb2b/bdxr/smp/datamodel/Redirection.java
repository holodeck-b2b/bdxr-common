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

import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Represents the meta-data about a <i>redirection</i> response that indicates the queried server does not longer 
 * maintain the service meta-data about the participant or process (this is new in the OASIS SMP V2 Spec).
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Redirection extends ServiceMetadataResult {
	/**
	 * The new URL to use for the SMP query  
	 */
	private URL		newTargetURL; 
	/**
	 * The certificate of the SMP server to which the request should be redirected
	 */
	private X509Certificate	cert;
	
    /**
     * Default constructor creates "empty" instance
     */
    public Redirection() {}
    
    /**
     * Creates a new object representing a SMP redirection only setting the new target URL. 
     *
     * @param redirectedURL The URL to use for a new query
     */
    public Redirection(final URL redirectedURL) {
        super();
        this.newTargetURL = redirectedURL;
    }
    
    /**
     * Creates a new object representing a SMP redirection setting the new target URL and certificate of the SMP server. 
     *
     * @param redirectedURL   URL to use for a new query
     * @param smpCertificate  Certificate of the new SMP server
     * @since 2.0.0
     */
    public Redirection(final URL redirectedURL, final X509Certificate smpCert) {
    	super();
    	this.newTargetURL = redirectedURL;
    	this.setSMPCertitificate(smpCert);
    }

	/** 
	 * Creates a new <code>Redirection</code> instance copying the data from the given instance.
	 *  
	 * @param src the instance to copy the data from
	 * @since 2.0.0
	 */
    public Redirection(final Redirection src) {
    	this.newTargetURL = src.newTargetURL;
    	this.cert = src.cert;
    }
    
    /**
     * Creates a new object representing the SMP redirection. 
     *
     * @param redirectedURL The URL to use for a new query
     * @param signingCert   The X509 certificate that was used to sign the meta-data
     * @param ext           Any extended meta-data information included in the SMP record
     */
    public Redirection(final URL redirectedURL, final X509Certificate signingCert, final List<IExtension> ext) {
        super(signingCert, ext);
        this.newTargetURL = redirectedURL;
    }
	
    /**
     * Gets the URL to which the queried SMP redirected 
     * 
     * @return	The URL to use for a new query
     */
    public URL getNewSMPURL() {
    	return newTargetURL;
    }
    
    /**
     * Sets the URL to which the queried SMP is redirecting
     * 
     * @param redirectedURL		The URL to use for a new query
     */
    public void setNewSMPURL(final URL redirectedURL) {
    	this.newTargetURL = redirectedURL;
    }

    /**
     * Gets the X509 certificate that the SMP server this redirection is to should use.
     * 
     * @return	The certificate of the "redirected" SMP server
     * @since   2.0.0 
     */
	public X509Certificate getSMPCertificate() {
		return cert;
	}

    /**
     * Sets the X509 certificate that the SMP server this redirection is to should use.
     * 
     * @param cert	The certificate of the "redirected" SMP server
     * @since   2.0.0 
     */
	public void setSMPCertitificate(X509Certificate cert) {
		this.cert = cert;
	}
    
    
}
