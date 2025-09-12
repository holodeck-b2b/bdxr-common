/*
 * Copyright (C) 2025 The Holodeck B2B Team
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
package org.holodeckb2b.bdxr.xhe.datamodel.impl;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.xhe.datamodel.BusinessScope;
import org.holodeckb2b.bdxr.xhe.datamodel.Header;
import org.holodeckb2b.bdxr.xhe.datamodel.Party;

/**
 * Is a basic implementation of the {@link Header} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class HeaderImpl implements Header {

	private Identifier 		id;
	private Identifier 		uuid;
	private ZonedDateTime 	creationDateTime;
	private Party 			from;
	private Set<Party> 		to;
	private BusinessScope 	businessScope;
	
	public Identifier getId() {
		return id;
	}
	
	public void setId(Identifier id) {
		this.id = id;
	}
	
	public Identifier getUUID() {
		return uuid;
	}
	
	public void setUUID(Identifier uuid) {
		this.uuid = uuid;
	}
	
	public ZonedDateTime getCreationDateTime() {
		return creationDateTime;
	}
	
	public void setCreationDateTime(ZonedDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	
	public Party getFrom() {
		return from;
	}
	
	public void setFrom(Party from) {
		this.from = from;
	}
	
	public Set<Party> getTo() {
		return to != null ? to : Collections.emptySet();
	}
	
	public void setTo(Set<Party> to) {
		this.to = to;
	}
	
	public void addTo(Party to) {
		if (this.to == null) {
			this.to = new HashSet<>();
		}
		this.to.add(to);
	}
	
	public BusinessScope getBusinessScope() {
		return businessScope;
	}
	
	public void setBusinessScope(BusinessScope businessScope) {
		this.businessScope = businessScope;
	}
}
