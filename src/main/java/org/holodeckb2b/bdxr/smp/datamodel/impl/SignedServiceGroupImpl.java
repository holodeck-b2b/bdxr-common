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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroupV2;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;
import org.holodeckb2b.bdxr.smp.datamodel.SignedQueryResult;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SignedServiceGroupImpl extends ServiceGroupV2Impl implements SignedQueryResult {
	private static final long serialVersionUID = 8176068786266042600L;
	
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
    public SignedServiceGroupImpl(Identifier participant, Collection<? extends ServiceReference> svcRefs,
								 X509Certificate cert, List<Extension<?>> exts) {
		super(participant, svcRefs, exts);
		this.signingCert = cert;
    }

	/**
	 * Creates a new instance copying the data from the given [unsigned] Service Group instance and set the signing 
	 * certificate used to sign the Service Group.
	 *
	 * @param src	the instance to copy the data from, should not be a signed version
	 * @param cert	certificate used by the SMP server to sign the result
	 */
    public SignedServiceGroupImpl(final ServiceGroupV2 src, final X509Certificate cert) {
    	this(src.getParticipantId(), src.getServiceReferences(), cert, src.getExtensions());
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

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SignedQueryResult) || !(o instanceof ServiceGroupV2))
			return false;

		return super.equals(o) && Utils.nullSafeEqual(signingCert, ((SignedQueryResult) o).getSigningCertificate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(signingCert);
		return result;
	}
}
