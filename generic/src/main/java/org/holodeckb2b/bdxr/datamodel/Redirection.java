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

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Represents the meta-data about a <i>redirection</i> response that indicates the queried server does not longer 
 * maintain the service meta-data about the participant. 
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Redirection extends ServiceMetadataResult {
	/**
	 * The new URL to use for the SMP query  
	 */
	private URI		newTargetURL; 
	
    /**
     * Default constructor creates "empty" instance
     */
    public Redirection() {}
    
    /**
     * Creates a new object representing a SMP redirection only setting the new target URL. 
     *
     * @param redirectedURL The URL to use for a new query
     */
    public Redirection(final URI redirectedURL) {
        super();
        this.newTargetURL = redirectedURL;
    }

    /**
     * Creates a new object representing the SMP redirection. 
     *
     * @param redirectedURL The URL to use for a new query
     * @param signingCert   The X509 certificate that was used to sign the meta-data
     * @param ext           Any extended meta-data information included in the SMP record
     */
    public Redirection(final URI redirectedURL, final X509Certificate signingCert, final List<IExtension> ext) {
        super(signingCert, ext);
        this.newTargetURL = redirectedURL;
    }
	
    /**
     * Gets the URL to which the queried SMP redirected 
     * 
     * @return	The URL to use for a new query
     */
    public URI getNewSMPURL() {
    	return newTargetURL;
    }
    
    /**
     * Sets the URL to which the queried SMP is redirecting
     * 
     * @param redirectedURL		The URL to use for a new query
     */
    public void setNewSMPURL(final URI redirectedURL) {
    	this.newTargetURL = redirectedURL;
    }
}
