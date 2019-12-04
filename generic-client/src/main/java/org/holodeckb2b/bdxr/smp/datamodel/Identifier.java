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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import org.holodeckb2b.bdxr.utils.Utils;

/**
 * Represent the generic definition of the identifiers used in the SMP data, i.e. the <i>Participant</i>,
 * <i>Process</i> and <i>Service</i>/<i>Document</i> identifiers. Each identifier can have an <i>identifier scheme</i>
 * assigned. Although the specifications require the scheme identifier to be an URI this class does not validate this.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Identifier {

    private String scheme = null;
    private String value = null;

    /**
     * Creates a new identifier without indicating a scheme
     *
     * @param id    The identifier value
     */
    public Identifier(final String id) {
        this(id, null);
    }

    /**
     * Creates a new identifier that is defined in the given scheme.
     *
     * @param id        The identifier value
     * @param scheme    The scheme in which the id is defined
     */
    public Identifier(final String id, final String scheme) {
        if (Utils.isNullOrEmpty(id))
            throw new IllegalArgumentException("Identifier value must not be null or empty!");
        this.value = id;
        this.scheme = scheme;
    }

    /**
     * Gets the identifier scheme to which this identifier belongs.
     *
     * @return  The identifier scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Sets the identifier scheme
     *
     * @param scheme The new identifier scheme
     */
    public void setScheme(final String scheme) {
        this.scheme = scheme;
    }

    /**
     * Gets the identifier value.
     *
     * @return  The identifier value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets new value for the identifier without changing the identifier scheme
     *
     * @param id    The new identifier value
     */
    public void setValue(final String id) {
        if (Utils.isNullOrEmpty(id))
            throw new IllegalArgumentException("Identifier value must not be null or empty!");
        this.value = id;
    }

    /**
     * Sets new value for the identifier and the scheme it is defined in
     *
     * @param id        The new identifier value
     * @param scheme    The identifier scheme the id is defined in
     */
    public void setValue(final String id, final String scheme) {
        if (Utils.isNullOrEmpty(id))
            throw new IllegalArgumentException("Identifier value must not be null or empty!");
        this.value = id;
        this.scheme = scheme;
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
    public String getURLEncoded() {
        try {
            return URLEncoder.encode((Utils.isNullOrEmpty(scheme) ? "" : scheme + "::") + value, "UTF-8");
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
		return (Utils.isNullOrEmpty(scheme) ? "" : scheme + "::") + value;
    }
    
    /**
     * Checks if the given object is also an <code>Identifier</code> and if it is represent the same identifier, i.e.
     * has the same scheme and value where the scheme is compared case sensitive and the value case insensitive.
     * <p>NOTE: The difference in case sensitivity between the scheme and value is because the scheme is defined in the
     * SMP specifications as a generic URI while the identifier is explicitly defined to be case insensitive.
     *
     * @param o     The other object
     * @return      <code>true</code> if the given object represents the same identifier,<br>
     *              <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Identifier))
            return false;
        else {
            Identifier o1 = (Identifier) o;
            return (this.scheme == null ? o1.scheme == null : this.scheme.equals(o1.scheme))
                && (this.value == null ? o1.value == null : this.value.equalsIgnoreCase(o1.value));
        }
    }

    /**
     * Generates a hashCode for this identifier. Takes into account that the value should be treated case insensitive.
     * 
     * @return hash code of this instance
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.scheme);
        hash = 47 * hash + Objects.hashCode(this.value.toLowerCase());
        return hash;
    }
}
