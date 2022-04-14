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
import java.util.Objects;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.bdxr.smp.datamodel.Redirection;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
abstract class AbstractRedirectionImpl extends ExtensibleMetadataClass implements Redirection {
	/**
	 * The new URL to use for the SMP query
	 */
	private URL		newTargetURL;

    /**
     * Default constructor creates "empty" instance
     */
    AbstractRedirectionImpl() {
		this(null, null);
	}

    /**
     * Creates a new object representing a SMP redirection only setting the new target URL.
     *
     * @param redirectedURL The URL to use for a new query
     */
    AbstractRedirectionImpl(final URL redirectedURL) {
        this.newTargetURL = redirectedURL;
    }

	/**
     * Creates a new object representing the SMP redirection.
     *
     * @param redirectedURL The URL to use for a new query
     * @param ext           Any extended meta-data information included in the SMP record
     */
    AbstractRedirectionImpl(final URL redirectedURL, final List<Extension> ext) {
		super(ext);
    	this.newTargetURL = redirectedURL;
    }

	/**
	 * Creates a new instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    AbstractRedirectionImpl(final Redirection src) {
		super(src.getExtensions());
    	this.newTargetURL = src.getNewSMPURL();
    }

    /**
     * Gets the URL to which the queried SMP redirected
     *
     * @return	The URL to use for a new query
     */
	@Override
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

    @Override
    public boolean equals(Object r) {
		return this.newTargetURL.equals(((Redirection) r).getNewSMPURL()) && super.equals(r);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(newTargetURL);
		return result;
	}
}
