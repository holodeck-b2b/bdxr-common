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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import org.holodeckb2b.bdxr.smp.datamodel.IDScheme;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class IdentifierImpl implements Identifier {

    protected IDSchemeImpl  scheme = null;
    protected String		value = null;

    /**
     * Default constructor that can be used by sub classes if they want to allow <code>null</code> values.
     */
    protected IdentifierImpl() {}

    /**
     * Creates a new identifier based on the given string. It will try to parse the string into both the scheme ID and
	 * identifier value by looking for a double colon ("::") separator and use the left part as scheme ID and the right
	 * part as identifier value.
     *
     * @param id    The identifier value
     */
    public IdentifierImpl(final String id) {
		if (!Utils.isNullOrEmpty(id)) {
			final int s = id.indexOf("::");
			if (s > 0) {
				this.scheme = new IDSchemeImpl(id.substring(0, s));
				this.value = id.substring(s + 2);
			} else {
				this.scheme = null;
				this.value = id;
			}
		}
    }

    /**
     * Creates a new identifier that is defined in the scheme with the given scheme id. It is assumed that the scheme
	 * uses case insensitive identifiers.
     *
     * @param id        The identifier value
     * @param schemeId  The identifier of the scheme in which the id is defined, may be <code>null</code> to indicate
	 *					there is no identifier scheme
     */
    public IdentifierImpl(final String id, final String schemeId) {
        Utils.requireNotNullOrEmpty(id);
        this.value =  id;
        this.scheme = !Utils.isNullOrEmpty(schemeId) ? new IDSchemeImpl(schemeId) : null;
    }

	/**
     * Creates a new identifier that is defined in the given scheme.
     *
     * @param id        The identifier value
     * @param scheme    The scheme in which the id is defined
     */
    public IdentifierImpl(final String id, final IDScheme scheme) {
        Utils.requireNotNullOrEmpty(id);
        this.value =  id;
        this.scheme = scheme != null ? new IDSchemeImpl(scheme) : null;
    }

	/**
	 * Creates a new <code>Identifier</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public IdentifierImpl(final org.holodeckb2b.bdxr.smp.datamodel.Identifier src) {
		if (src == null)
			throw new IllegalArgumentException();
        this.value =  src.getValue();
        this.scheme = src.getScheme() != null ? new IDSchemeImpl(src.getScheme()) : null;
    }

    /**
     * Gets the identifier scheme to which this identifier belongs.
     *
     * @return  The identifier scheme
     */
	@Override
    public IDScheme getScheme() {
        return scheme;
    }

    /**
     * Gets the identifier value.
     *
     * @return  The identifier value.
     */
	@Override
    public String getValue() {
        return scheme != null && scheme.isCaseSensitive() ? value : value.toLowerCase();
    }

    /**
     * Sets new value for the identifier without an identifier scheme
     *
     * @param id    The new identifier value
     */
    public void setValue(final String id) {
        Utils.requireNotNullOrEmpty(id);
        this.value = id;
		this.scheme = null;
    }

    /**
     * Sets new value for the identifier and the scheme it is defined in
     *
     * @param id        The new identifier value
     * @param scheme    The identifier scheme the id is defined in
     */
    public void setValue(final String id, final IDScheme scheme) {
        Utils.requireNotNullOrEmpty(id);
        this.value = id;
        this.scheme = scheme != null ? new IDSchemeImpl(scheme) : null;
    }

    /**
     * Gets the "URL formatted" version of the identifier as described in the OASIS SMP specification.
     * <p>NOTE: The URL format is defined as <i>[{identifier scheme}::]{id}</i> in version 2.0 of the specification
     * whereas in the 1.0 and PEPPOL version there is no statement on how to handle an identifier without scheme and one
     * could reason that an identifier without scheme should be represented as <i>::{id}</i>. For uniformity we however
     * handle it the same as in version 2.0 and exclude the "::".
     *
     * @return  The identifier formatted for inclusion in a URL
     */
	@Override
    public String getURLEncoded() {
        try {
            return URLEncoder.encode(toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported.");
        }
    }

    /**
     * Gets a string representation of the identifier which is constructed by concatenating the scheme and value
     * separated by a "::".
     *
     * @return String representation of the identifier
     * @see #getURLEncoded()
     */
    @Override
    public String toString() {
		return (scheme == null ? "" : scheme.getSchemeId() + "::") + getValue();
    }

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Identifier))
			return false;

		final Identifier i = (Identifier) o;
		return Utils.nullSafeEqual(scheme, i.getScheme())
                && (scheme != null && scheme.isCaseSensitive()) ? Utils.nullSafeEqual(value, i.getValue())
															    : Utils.nullSafeEqualIgnoreCase(value, i.getValue());
	}

    @Override
    public int hashCode() {
        int hash = 3;
        if (this.scheme != null)
			hash = 47 * hash + this.scheme.hashCode();
        hash = 47 * hash + Objects.hashCode(getValue());
        return hash;
    }
}
