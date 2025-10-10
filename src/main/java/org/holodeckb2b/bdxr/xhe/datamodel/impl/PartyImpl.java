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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.xhe.datamodel.Party;

/**
 * Is a basic implementation of the {@link Party} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class PartyImpl implements Party {

	private Set<Identifier> identifiers;
	
	public PartyImpl() {}
	
	public PartyImpl(Set<Identifier> identifiers) {
		this.setIdentifiers(identifiers);
	}

	public Set<Identifier> getIdentifiers() {
		return identifiers != null ? identifiers : Collections.emptySet();
	}

	public void setIdentifiers(Set<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifier(Identifier id) {
		if (this.identifiers == null)
			this.identifiers = new HashSet<>();
		this.identifiers.add(id);
	}
}
