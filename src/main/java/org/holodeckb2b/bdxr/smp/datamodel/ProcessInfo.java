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

import java.util.ArrayList;
import java.util.List;

import org.holodeckb2b.commons.util.Utils;

/**
 * Represents the SMP meta-data of processes in which a specific <i>service</i>/<i>document</i> is offered/used and
 * in which the participant plays the same role. The association with the service is implemented by containment of this
 * class in {@link ServiceInformation} mirroring the structure of the SMP XML structure.
 * <p>NOTE: In the OASIS v1 and PEPPOL SMP specifications there is no possibility to indicate the role of the 
 * participant.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessInfo {

    private ProcessIdentifier	processId;
    private List<Identifier> 	roles;
    private List<IExtension>   	extensions;

    /**
     * Default constructor creates "empty" instance
     */
    public ProcessInfo() {
    	this.roles = new ArrayList<>();
    }

    /**
     * Creates a new instance with the given meta-data 
     *
     * @param procId        The process identifier
     * @param extendedInfo  Extended information available about this process
     */
    public ProcessInfo(final ProcessIdentifier procId,  final List<IExtension> extendedInfo)
    {
    	this(procId, null, extendedInfo);
    }
    
    /**
     * Creates a new instance with the given meta-data
     *
     * @param procId        The process identifier
     * @param roles			The list of roles the participant acts in
     * @param extendedInfo  Extended information available about this process
     * @since 2.0.0
     */
    public ProcessInfo(final ProcessIdentifier procId, final List<Identifier> roles, 
    				   final List<IExtension> extendedInfo)
    {
        this.processId = procId;
        setRoles(roles);
        setExtensions(extendedInfo);
    }
    
	/** 
	 * Creates a new <code>ProcessInfo</code> instance copying the data from the given instance.
	 *  
	 * @param src the instance to copy the data from
	 * @since 2.0.0
	 */
    public ProcessInfo(final ProcessInfo src) {
    	this.processId = src.processId;
    	this.roles = new ArrayList<>(src.roles);
    	this.extensions = new ArrayList<>(src.extensions);
    }


    /**
     * Gets the process identifier
     */
    public ProcessIdentifier getProcessId() {
        return processId;
    }

    /**
     * Sets the process identifier
     *
     * @param processId The process identifier to set
     */
    public void setProcessId(ProcessIdentifier processId) {
        this.processId = processId;
    }

    /**
     * Gets the additional, non standard, information related to this endpoint
     *
     * @return The extended meta-data
     */
    public List<IExtension> getExtensions() {
        return extensions;
    }

    /**
     * Sets the additional, non standard, information related to this endpoint
     *
     * @param extensions The extended meta-data
     */
    public void setExtensions(List<IExtension> extensions) {
    	this.extensions = extensions != null ? extensions : new ArrayList<>();
    }

    /**
     * Add one specific extension to the meta-data
     *
     * @param   ext     The extension to add to set of extended meta-data
     */
    public void addExtension(final IExtension ext) {
        if (ext == null)
            throw new IllegalArgumentException("A extention must be specified");
        this.extensions.add(ext);
    }

	/**
	 * Gets the roles the participant acts in for this process(es)
	 * 
	 * @return	List of Role identifiers
	 * @since 2.0.0
	 */
	public List<Identifier> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles the participant acts in for this process(es)
	 * 
	 * @param roles	List of Role identifiers
	 * @since 2.0.0
	 */
	public void setRoles(List<Identifier> roles) {
		this.roles = roles == null ? new ArrayList<>() : roles;
	}
	
	/**
	 * Adds a role the participant can play in this process.
	 * 
	 * @param role	Identifier of the role to be added
	 * @since 2.0.0
	 */
	public void addRole(Identifier role) {
        if (role == null)
            throw new IllegalArgumentException("A role identifier object must be provided");
        this.roles.add(role);		
	}
}
