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
import java.util.List;
import java.util.Objects;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.RedirectionV2;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class RedirectionV2Impl extends AbstractRedirectionImpl implements RedirectionV2 {
	/**
	 * The certificate of the SMP server to which the request should be redirected
	 */
	private X509Certificate	cert;

    /**
     * Default constructor creates "empty" instance
     */
    public RedirectionV2Impl() {
		this(null, null, null);
	}

    /**
     * Creates a new object representing a SMP redirection only setting the new target URL.
     *
     * @param redirectedURL The URL to use for a new query
     */
    public RedirectionV2Impl(final URL redirectedURL) {
        this(redirectedURL, null, null);
    }

    /**
     * Creates a new object representing a SMP redirection setting the new target URL and certificate of the SMP server.
     *
     * @param redirectedURL URL to use for a new query
     * @param smpCert		Certificate of the new SMP server
     */
    public RedirectionV2Impl(final URL redirectedURL, final X509Certificate smpCert) {
    	this(redirectedURL, smpCert, null);
    }

    /**
     * Creates a new object representing the SMP redirection.
     *
     * @param redirectedURL The URL to use for a new query
     * @param smpCert		Certificate of the new SMP server
     * @param ext           Any extended meta-data information included in the SMP record
     */
    public RedirectionV2Impl(final URL redirectedURL, final X509Certificate smpCert, final List<Extension> ext) {
        super(redirectedURL, ext);
    	this.cert = smpCert;
    }

	/**
	 * Creates a new instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public RedirectionV2Impl(final RedirectionV2 src) {
		super(src);
    	this.cert = src.getSMPCertificate();
    }

    /**
     * Gets the X509 certificate that the SMP server this redirection is to should use.
     *
     * @return	The certificate of the "redirected" SMP server
     */
	@Override
	public X509Certificate getSMPCertificate() {
		return cert;
	}

    /**
     * Sets the X509 certificate that the SMP server this redirection is to should use.
     *
     * @param cert	The certificate of the "redirected" SMP server
     */
	public void setSMPCertitificate(X509Certificate cert) {
		this.cert = cert;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof RedirectionV2))
			return false;

		return super.equals(o) && Utils.nullSafeEqual(cert, ((RedirectionV2) o).getSMPCertificate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cert);
		return result;
	}
}
