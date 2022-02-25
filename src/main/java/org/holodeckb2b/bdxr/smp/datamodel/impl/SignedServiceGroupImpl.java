/*
 * Copyright (C) 2022 The Holodeck B2B Team
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
import java.util.List;
import java.util.Set;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroupV2;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;
import org.holodeckb2b.bdxr.smp.datamodel.SignedQueryResult;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SignedServiceGroupImpl extends ServiceGroupV2Impl implements SignedQueryResult {

	private X509Certificate		signingCert;

	/**
     * Default constructor creates "empty" instance
     */
    public SignedServiceGroupImpl() {
    	this(null, null, null, null);
    }

    /**
     * Creates a new object representing an overview of the services supported by a participant.
     *
     * @param participant   The participant identifier
     * @param svcRefs		Set of references to services which the participant supports
	 * @param cert			Certificate used to sign the service meta-data
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public SignedServiceGroupImpl(final Identifier participant, Set<ServiceReference> svcRefs,
								final X509Certificate cert,	final List<Extension> exts) {
		super(participant, svcRefs, exts);
		this.signingCert = cert;
    }

	/**
	 * Creates a new <code>ServiceGroupV2</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public SignedServiceGroupImpl(final ServiceGroupV2 src) {
    	super(src);
		if (src instanceof SignedServiceGroupImpl)
			this.signingCert = ((SignedServiceGroupImpl) src).signingCert;
    }

	/**
	 * Creates a new instance copying the data from the given instance and signing certificate.
	 *
	 * @param src	the instance to copy the data from, should not be a signed version
	 * @param cert	certificate used by the SMP server to sign the result
	 */
    public SignedServiceGroupImpl(final ServiceGroupV2 src, final X509Certificate cert) {
    	super(src);
		this.signingCert = cert;
    }	

	/**
	 * Gets the certificate that was used to sign the result.
	 *
	 * @return the signing certificate if the result was signed
	 */
	@Override
	public X509Certificate getSigningCertificate() {
		return signingCert;
	}

	/**
	 * Sets the X509 certificate that was used by the SMP server to sign the result
	 *
	 * @param cert	SMP signing cert
	 */
	public void setSigningCertificate(X509Certificate cert) {
		this.signingCert = cert;
	}
}
