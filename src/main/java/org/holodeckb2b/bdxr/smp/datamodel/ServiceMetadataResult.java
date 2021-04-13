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
import java.util.ArrayList;
import java.util.List;

/**
 * Is a base class for the possibly signed <i>Service Metadata</i> response from the SMP. This response can either
 * contain the actual meta-data on a service or it can contain a "redirect" to another SMP that holds the meta-data.
 * Each response type has its own implementing class providing the specific meta-data, this class provides the
 * information about the signing of the response.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public abstract class ServiceMetadataResult implements ISMPQueryResult {
    /**
     * The X509.v3 certificate that was used to sign the service meta-data
     */
    private X509Certificate signerCertificate;
    /**
     * The meta-data included in the extension elements. 
     */
    private List<IExtension>    	extensions;

    /**
     * Default constructor initializes "empty" instance
     */
    public ServiceMetadataResult() {}

    /**
     * Initializes a new instance with the given signing certificate and no extensions.
     *
     * @param cert The X509.v3 certificate that was used to signed the service meta-data
     */
    public ServiceMetadataResult(final X509Certificate cert) {
        this.signerCertificate = cert;
    }
    
    /**
     * Initializes a new instance with the given signing certificate and extensions.
     *
     * @param cert The X509.v3 certificate that was used to signed the service meta-data
     * @param extensions List containing the meta-data included in the extension points
     */
    public ServiceMetadataResult(final X509Certificate cert, final List<IExtension> extensions) {
        this.signerCertificate = cert;
        this.extensions = extensions;
    }    

    /**
     * Indicates whether the service meta-data were signed.
     *
     * @return <code>true</code> if the service meta-data were signed,
     *         <code>false</code> if no signature was applied
     */
    public boolean isSigned() {
        return signerCertificate != null;
    }

    /**
     * Gets the X509 Certificate that was used to signed the service meta-data. As signing of the meta-data is
     * optional a <code>null</code> result indicates that the meta-data was not signed.
     *
     * @return the signer certificate, <code>null</code> if the meta-data were not signed
     */
    public X509Certificate getSignerCertificate() {
        return signerCertificate;
    }

    /**
     * Sets the X509 Certificate that is used to sign the service meta-data. Note that this class is only a place
     * holder for the meta-data and setting the certificate does not imply actual signing of the meta-data by this
     * certificate.
     *
     * @param signerCertificate The certificate used to sign the meta-data
     */
    public void setSignerCertificate(X509Certificate signerCertificate) {
        this.signerCertificate = signerCertificate;
    }


    /**
     * Gets the additional, non standard, information related to this service
     *
     * @return The extended meta-data
     */
    public List<IExtension> getExtensions() {
        return extensions;
    }

    /**
     * Sets the additional, non standard, information related to this service
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
            throw new IllegalArgumentException("A extension must be specified");
        if (this.extensions == null)
            this.extensions = new ArrayList<>();
        this.extensions.add(ext);
    }    
}
