/*
 * Copyright (C) 2019 The Holodeck B2B Team
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

import org.holodeckb2b.bdxr.smp.datamodel.IDScheme;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessIdentifier;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessIdentifierImpl extends IdentifierImpl implements ProcessIdentifier {

	private boolean isNoProcess = false;

	/**
	 * Creates a new "no-process" Process Identifier
	 */
	public ProcessIdentifierImpl() {
		super();
		this.isNoProcess = true;
	}

	/**
	 * Creates a new, non "no-process", Process identifier
	 *
	 * @param id	the identifier value
	 */
	public ProcessIdentifierImpl(String id) {
		super(id);
	}

	/**
	 * Creates a new, non "no-process", Process identifier that is defined in the scheme with the given scheme id.
	 *
	 * @param id		the identifier value
	 * @param scheme 	scheme in which the identifier is defined
	 */
	public ProcessIdentifierImpl(String id, IDScheme scheme) {
		super(id, scheme);
	}

	/**
     * Creates a new, non "no-process" Process identifier that is defined in the scheme with the given scheme id. It is
	 * assumed that the scheme uses case insensitive identifiers.
     *
     * @param id        The identifier value
     * @param schemeId  The identifier of the scheme in which the id is defined, may be <code>null</code> to indicate
	 *					there is no identifier scheme
     */
    public ProcessIdentifierImpl(final String id, final String schemeId) {
        super(id, schemeId);
    }

	/**
	 * Creates a new <code>ProcessIdentifier</code> instance copying the data from the given instance.
	 *
	 * @param src the instance to copy the data from
	 */
    public ProcessIdentifierImpl(final org.holodeckb2b.bdxr.smp.datamodel.ProcessIdentifier src) {
    	super(src);
    	this.isNoProcess = src.isNoProcess();
    }

	/**
	 * Indicates whether this identifier is the special "no-process" identifier as defined in the specifications.
	 *
	 * @return	<code>true</code> if it is the special "no-process" identifier,<br>
	 * 			<code>false</code> if not
	 */
	@Override
	public boolean isNoProcess() {
		return isNoProcess;
	}

	/**
	 * Sets the indicator whether this identifier is the special "no-process" identifier as defined in the
	 * specifications.
	 *
	 * @param isNoProcess  <code>true</code> if it is the special "no-process" identifier,<br>
	 *					   <code>false</code> if not
	 */
	public void setIsNoProcess(boolean isNoProcess) {
		this.isNoProcess = isNoProcess;
		if (isNoProcess) {
			this.value = null;
			this.scheme = null;
		}
	}

	/**
     * Sets new value for the process identifier without an identifier scheme
     *
     * @param id    The new identifier value
     */
	@Override
    public void setValue(final String id) {
		super.setValue(id);
		this.isNoProcess = false;
    }

    /**
     * Sets new value for the process identifier and the scheme it is defined in
     *
     * @param id        The new identifier value
     * @param scheme    The identifier scheme the id is defined in
     */
	@Override
    public void setValue(final String id, final IDScheme scheme) {
		super.setValue(id, scheme);
		this.isNoProcess = false;
	}

    @Override
    public String toString() {
		return isNoProcess ? "{{No-Process}}" : super.toString();
    }
}
