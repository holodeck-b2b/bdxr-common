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
package org.holodeckb2b.bdxr.smp.datamodel;

import org.holodeckb2b.commons.util.Utils;

/**
 * Represent the definition of the Process identifiers used in the SMP data. It is based on the generic {@link 
 * Identifier} but adds a function to indicate that the identifier does identify the "no process" which is used in the
 * SMP specifications for service meta-data that does not relate to any process. Because this represented in the 
 * specifications by a specific, but different, string we use a indicator which should be set when the actual data is
 * processed.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessIdentifier extends Identifier {

	private boolean isNoProcess = false;
	
	/**
	 * Creates a new "no-process" Process Identifier
	 */
	public ProcessIdentifier() {
		super();
		this.isNoProcess = true;
	}

	/**
	 * Creates a new, non "no-process", Process identifier 
	 * 
	 * @param id	the identifier value 
	 */
	public ProcessIdentifier(String id) {
		super(id);		
	}

	/**
	 * Creates a new, non "no-process", Process identifier 
	 * 
	 * @param id		the identifier value
	 * @param scheme 	scheme in which the identifier is defined
	 */
	public ProcessIdentifier(String id, IdScheme scheme) {
		super(id, scheme);		
	}
	
	/** 
	 * Creates a new <code>ProcessIdentifier</code> instance copying the data from the given instance.
	 *  
	 * @param src the instance to copy the data from
	 * @since 2.0.0
	 */
    public ProcessIdentifier(final ProcessIdentifier src) {
    	super(src);
    	this.isNoProcess = src.isNoProcess;
    }
    
	/**
	 * Indicates whether this identifier is the special "no-process" identifier as defined in the specifications.
	 * 
	 * @return	<code>true</code> if it is the special "no-process" identifier,<br>
	 * 			<code>false</code> if not
	 */
	public boolean isNoProcess() {
		return isNoProcess;
	}
	
	public void setIsNoProcess(boolean isNoProcessId) {
		this.isNoProcess = isNoProcessId;
	}
	
	/**
     * Checks if the given object is also an <code>Identifier</code> and if it is represent the same identifier, taking 
     * into account the specific <i>no process</i> process identifier. 
     *
     * @param o     The other object
     * @return      <code>true</code> if the given object represents the same identifier,<br>
     *              <code>false</code> otherwise	  
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Identifier))
            return false;
        else if (this.isNoProcess) 
        	return (o instanceof ProcessIdentifier) && ((ProcessIdentifier) o).isNoProcess();
    	else	
    		return super.equals(o);        	
	}
	
    @Override
    public String toString() {
		return isNoProcess ? "{{No-Process}}" : super.toString();
    }	
}
