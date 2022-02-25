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
import java.util.List;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.RedirectionV1;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class RedirectionV1Impl extends AbstractRedirectionImpl implements RedirectionV1 {
	/**
	 * The <i>Subject Unique Identifier</i> of the certificate of the SMP server to which the request should be
	 * redirected. The value of this field is defined as a bit string, therefore the type is a boolean array.
	 */
	private boolean[]	subjectUniqueID;

    /**
     * Default constructor creates "empty" instance
     */
    public RedirectionV1Impl() {
		this(null, null, null);
	}

    /**
     * Creates a new object representing a SMP redirection only setting the new target URL.
     *
     * @param redirectedURL The URL to use for a new query
     */
    public RedirectionV1Impl(final URL redirectedURL) {
        this(redirectedURL, null, null);
    }

    /**
     * Creates a new object representing a SMP redirection setting the new target URL and certificate of the SMP server.
     *
     * @param redirectedURL URL to use for a new query
     * @param subjectUID	<i>Subject Unique Identifier</i> of certificate of the new SMP server
     */
    public RedirectionV1Impl(final URL redirectedURL, final boolean[] subjectUID) {
    	this(redirectedURL, subjectUID, null);
    }

    /**
     * Creates a new object representing the SMP redirection.
     *
     * @param redirectedURL The URL to use for a new query
     * @param subjectUID	<i>Subject Unique Identifier</i> of certificate of the new SMP server
     * @param ext           Any extended meta-data information included in the SMP record
     */
    public RedirectionV1Impl(final URL redirectedURL, final boolean[] subjectUID, final List<Extension> ext) {
        super(redirectedURL, ext);
    	this.subjectUniqueID = subjectUID;
    }

	/**
	 * Creates a new instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public RedirectionV1Impl(final RedirectionV1 src) {
		super(src);
    	this.subjectUniqueID = src.getSMPSubjectUniqueID();
    }

    /**
     * Gets the <i>Subject Unique Identifier</i> of certificate of the SMP server this redirection is to should use.
     *
     * @return	The <i>Subject Unique Identifier</i> of the "redirected" SMP server
     */
	@Override
	public boolean[] getSMPSubjectUniqueID() {
		return subjectUniqueID;
	}

	/**
	 * Sets the <i>Subject Unique Identifier</i> of the SMP server this redirection is to should use.
	 *
	 * @param subjectUID	The <i>Subject Unique Identifier</i> of the certificate of the "redirected" SMP server
	 */
	public void setSMPSubjectUniqueID(boolean[] subjectUID) {
		this.subjectUniqueID = subjectUID;
	}
}
