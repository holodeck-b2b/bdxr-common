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
package org.holodeckb2b.bdxr.common.datamodel.impl;

import java.net.URI;
import java.util.Objects;

import org.holodeckb2b.bdxr.common.datamodel.Code;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class CodeImpl implements Code {
	
	private String 		code;
	private Identifier 	listId;	
	private String 		listName;
	private Identifier 	listVersion;
	private URI 		listURI;	
	
	public CodeImpl(String code) {
		this.code = code;
	}
	
	public CodeImpl(String code, Identifier listId) {
		this.code = code;
		this.listId = listId;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Identifier getListId() {
		return listId;
	}
	public void setListId(Identifier listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public Identifier getListVersion() {
		return listVersion;
	}
	public void setListVersion(Identifier listVersion) {
		this.listVersion = listVersion;
	}
	public URI getListURI() {
		return listURI;
	}
	public void setListURI(URI listURI) {
		this.listURI = listURI;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Code))
			return false;
		Code other = (Code) obj;
		return Objects.equals(code, other.getCode()) && Objects.equals(listId, other.getListId())
				&& Objects.equals(listName, other.getListName()) && Objects.equals(listURI, other.getListURI())
				&& Objects.equals(listVersion, other.getListVersion());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code, listId, listName, listURI, listVersion);
	}
}
