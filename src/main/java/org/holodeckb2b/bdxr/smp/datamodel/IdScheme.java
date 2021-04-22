/*
 * Copyright (C) 2021 The Holodeck B2B Team
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

import java.util.Objects;

import org.holodeckb2b.commons.util.Utils;

/**
 * Represents the meta-data of an <i>Identifier Scheme</i> which defines the policies for identifiers used in the 
 * SMP data, e.g. the <i>Participant</i> ID. For general processing of SMP data only the policy on whether identifiers
 * from the scheme should be treated case sensitively is relevant. As described in version 2 of the OASIS specification
 * identifier schemes are assumed to use case <b>insensitive</b> identifiers.</br>    
 * Although  the specifications require the scheme identifier to be an URI this class uses a simple string and does not 
 * validate its value to be an URI. 
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 3.0.0
 */
public class IdScheme {
	
    protected String  schemeId = null;
    protected String  description = null;
    protected String  policyRef = null;
    protected boolean caseSensitive = false;

    /**
     * Creates a new identifier scheme with case insensitive policy 
     *
     * @param id    The scheme identifier 
     */
    public IdScheme(final String id) {
        this(id, false, null, null);
    }

    /**
     * Creates a new identifier scheme with specified case sensivity policy.
     *
     * @param id        	The scheme identifier
     * @param caseSensitive Indicates whether the identifiers of this scheme must be treated case sensitive
     */
    public IdScheme(final String id, final boolean caseSensitive) {
    	this(id, caseSensitive, null, null);
    }
        
    /**
     * Creates a new identifier scheme with given meta-data.
     *
     * @param id        	The scheme identifier
     * @param caseSensitive Indicates whether the identifiers of this scheme must be treated case sensitive  
     * @param description	Description of the identifier scheme
     * @param policyRef		Reference to the policy document of the scheme
     */
    public IdScheme(final String id, final boolean caseSensitive, final String description, final String policyRef) {
        if (Utils.isNullOrEmpty(id))
            throw new IllegalArgumentException("Scheme identifier must not be null or empty!");
        this.schemeId =  id;
        this.caseSensitive = caseSensitive;
    }

	/** 
	 * Creates a new instance copying the data from the given instance.
	 *  
	 * @param src the instance to copy the data from
	 */
    public IdScheme(final IdScheme src) {
        this.schemeId =  src.schemeId;
        this.caseSensitive = src.caseSensitive;
        this.description = src.description;
        this.policyRef = src.policyRef;
    }

    /**
     * Gets the scheme identifier.
     * 
     * @return the scheme id
     */
	public String getSchemeId() {
		return schemeId;
	}

	/**
	 * Sets the scheme identifier.
	 * 
	 * @param schemeId	the scheme identifier
	 */
	public void setSchemeId(String schemeId) {
		if (Utils.isNullOrEmpty(schemeId))
			throw new IllegalArgumentException("Scheme identifier must not be null or empty!");
		this.schemeId = schemeId;
	}

	/**
	 * Gets the description of the identifier scheme.
	 * 
	 * @return	text describing the scheme
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the identifier scheme.
	 * 
	 * @param description	textual description of the scheme
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the reference to the policy document specifying the identifier scheme.
	 * 
	 * @return	policy document reference
	 */
	public String getPolicyRef() {
		return policyRef;
	}

	/**
	 * Sets the reference to the policy document specifying the identifier scheme.
	 * 
	 * @param policyRef	reference to the policy document. It is RECOMMENDED that the value is an URL.
	 */
	public void setPolicyRef(String policyRef) {
		this.policyRef = policyRef;
	}

	/**
	 * Indicates whether identifiers from this identifier scheme should be treated case sensitively or not.
	 * 
	 * @return	<code>true</code> when the identifiers should be treated case sensitively,<br/>
	 * 			<code>false</code> when the identifiers should be treated case insensitively
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Sets the case sensitivity of the identifier scheme.  
	 *  
	 * @param caseSensitive	<code>true</code> when the identifiers should be treated case sensitively,<br/>
	 * 						<code>false</code> when the identifiers should be treated case insensitively
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

    /**
     * Checks if the given object is also an <code>IdScheme</code> and if it is, represents the same identifier scheme,
     * i.e. has the same scheme id.
     *
     * @param o     The other object
     * @return      <code>true</code> if the given object represents the same identifier scheme,<br>
     *              <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o != null && (o instanceof IdScheme) && this.schemeId.equals(((IdScheme) o).schemeId);        
    }

    /**
     * Generates a hashCode for this identifier scheme. Similar to the <code>equals</code> method only the identifier 
     * is taken into account here as this determines scheme equality.
     * 
     * @return hash code of this instance
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schemeId == null) ? 0 : schemeId.hashCode());
		return result;
	}
}
