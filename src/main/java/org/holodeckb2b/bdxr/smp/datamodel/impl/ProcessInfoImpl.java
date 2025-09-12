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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.ProcessIdentifier;
import org.holodeckb2b.bdxr.common.datamodel.impl.ExtensibleMetadataClass;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.commons.util.Utils;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessInfoImpl extends ExtensibleMetadataClass implements ProcessInfo {
    private static final long serialVersionUID = 4873041878958165465L;
    
	private ProcessIdentifier	processId;
    private Set<Identifier>		roles;

    /**
     * Default constructor creates "empty" instance
     */
    public ProcessInfoImpl() {
    	this(null, null, null);
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param procId    The process identifier
     * @param exts		Extended information available about this process
     */
    public ProcessInfoImpl(ProcessIdentifier procId, List<Extension<?>> exts) {
    	this(procId, null, exts);
    }

    /**
     * Creates a new instance with the given meta-data
     *
     * @param procId    The process identifier
     * @param roles		The list of roles the participant acts in
     * @param exts		Extended information available about this process
     */
    public ProcessInfoImpl(ProcessIdentifier procId, final Set<? extends Identifier> roles, List<Extension<?>> exts) {
		super(exts);
        this.processId = procId;
        setRoles(roles);
    }

	/**
     * Gets the process identifier
     */
	@Override
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
	 * Gets the roles the participant acts in for this process(es)
	 *
	 * @return	List of Role identifiers
	 */
	@Override
	public Set<? extends Identifier> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles the participant acts in for this process(es)
	 *
	 * @param roles	List of Role identifiers
	 */
	public void setRoles(Set<? extends Identifier> roles) {
		this.roles = new HashSet<>(roles != null ? roles.size() : 0);
		if (roles != null)
			this.roles.addAll(roles);
	}

	/**
	 * Adds a role the participant can play in this process.
	 *
	 * @param role	Identifier of the role to be added
	 */
	public void addRole(Identifier role) {
        if (role == null)
            throw new IllegalArgumentException("A role identifier object must be provided");
        if (this.roles == null)
			this.roles = new HashSet<>(1);
		this.roles.add(role);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ProcessInfo))
			return false;

		ProcessInfo p = (ProcessInfo) o;
		return super.equals(o)
			&& Utils.nullSafeEqual(processId, p.getProcessId())
			&& Utils.areEqual(roles, p.getRoles());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(processId, Utils.isNullOrEmpty(roles) ? null : roles);
		return result;
	}
}
