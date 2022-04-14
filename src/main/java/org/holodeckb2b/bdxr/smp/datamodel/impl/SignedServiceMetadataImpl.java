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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceMetadata;
import org.holodeckb2b.bdxr.smp.datamodel.SignedQueryResult;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class SignedServiceMetadataImpl extends ServiceMetadataImpl implements SignedQueryResult {

    private X509Certificate		signingCert;

    /**
     * Default constructor creates "empty" instance
     */
    public SignedServiceMetadataImpl() {
    	this(null, null, null, null, null);
    }

    /**
     * Creates a new object representing the SMP meta-data for a specific participant and service type.
     *
     * @param participant   The participant's identifiers
     * @param service       The service identifier
     * @param processes     Set of process groups in which the service is supported
	 * @param cert			Certificate used to sign the service meta-data
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public SignedServiceMetadataImpl(final Identifier participant, final Identifier service,
									 final Set<ProcessGroup> processes, final X509Certificate cert,
									 final List<Extension> exts) {
		super(participant, service, processes, exts);
        this.signingCert = cert;
    }

	/**
	 * Creates a new instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public SignedServiceMetadataImpl(final ServiceMetadata src) {
    	super(src);
		if (src instanceof SignedQueryResult)
			this.signingCert = ((SignedQueryResult) src).getSigningCertificate();
    }

	/**
	 * Creates a new instance copying the data from the given instance and signing certificate.
	 *
	 * @param src	the instance to copy the data from, should not be a signed version
	 * @param cert	certificate used by the SMP server to sign the result
	 */
    public SignedServiceMetadataImpl(final ServiceMetadata src, final X509Certificate cert) {
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

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SignedQueryResult) || !(o instanceof ServiceMetadata))
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
