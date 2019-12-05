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

import org.holodeckb2b.bdxr.smp.api.ISMPResultProcessor;

/**
 * Represent the definition of the Process identifiers used in the SMP data. It is based on the generic {@link 
 * Identifier} but adds a function to indicate that the identifier does identify the "no process" which is used in the
 * SMP specifications for service meta-data that does not relate to any process. Because this represented in the 
 * specifications by a specific, but different, string we use a indicator which should be set by the {@link 
 * ISMPResultProcessor}.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 2.0.0
 */
public class ProcessIdentifier extends Identifier {

	private boolean isNoProcess = false;
	
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
	public ProcessIdentifier(String id, String scheme) {
		super(id, scheme);		
	}
	
	/**
	 * Creates a new "no-process" Process Identifier
	 */
	public ProcessIdentifier() {
		super();
		this.isNoProcess = true;
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
}
