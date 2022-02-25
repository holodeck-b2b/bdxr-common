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
package org.holodeckb2b.bdxr.smp.datamodel.impl;

import java.util.Objects;

import org.holodeckb2b.commons.util.Utils;
import org.holodeckb2b.bdxr.smp.datamodel.IDScheme;

/**
 * Implements the {@link IDScheme} interface. The default case sensitivity policy is to threat identifiers case 
 * insensitive.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class IDSchemeImpl implements IDScheme {
	
    protected String  schemeId = null;
    protected boolean caseSensitive = false;

    /**
     * Creates a new identifier scheme with case insensitive policy 
     *
     * @param id    The scheme identifier 
     */
    public IDSchemeImpl(final String id) {
        this(id, false);
    }

    /**
     * Creates a new identifier scheme with specified case sensivity policy.
     *
     * @param id        	The scheme identifier
     * @param caseSensitive Indicates whether the identifiers of this scheme must be treated case sensitive
     */
    public IDSchemeImpl(final String id, final boolean caseSensitive) {
		Utils.requireNotNullOrEmpty(id);
    	this.schemeId = id;
		this.caseSensitive = caseSensitive;
    }

	/** 
	 * Creates a new instance copying the data from the given identifier scheme.
	 *  
	 * @param src the instance to copy the data from
	 */
    public IDSchemeImpl(final IDScheme src) {
		if (src == null)
			throw new IllegalArgumentException();
		Utils.requireNotNullOrEmpty(src.getSchemeId());
        this.schemeId =  src.getSchemeId();
        this.caseSensitive = src.isCaseSensitive();
    }

    /**
     * Gets the scheme identifier.
     * 
     * @return the scheme id
     */
	@Override
	public String getSchemeId() {
		return schemeId;
	}

	/**
	 * Sets the scheme identifier.
	 * 
	 * @param schemeId	the scheme identifier
	 */
	public void setSchemeId(String schemeId) {
		Utils.requireNotNullOrEmpty(schemeId);			
		this.schemeId = schemeId;
	}

	/**
	 * Indicates whether identifiers from this identifier scheme should be treated case sensitively or not.
	 * 
	 * @return	<code>true</code> when the identifiers should be treated case sensitively,<br/>
	 * 			<code>false</code> when the identifiers should be treated case insensitively
	 */
	@Override
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
     * Generates a hashCode for this identifier scheme. Similar to the {@link 
	 * IDScheme#equals(org.holodeckb2b.bdxr.smp.datamodel.IDScheme)} only the identifier is taken into account here as 
	 * this determines scheme equality.
     * 
     * @return hash code of this instance
     */	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + Objects.hashCode(this.schemeId);
		return hash;
	}
}
