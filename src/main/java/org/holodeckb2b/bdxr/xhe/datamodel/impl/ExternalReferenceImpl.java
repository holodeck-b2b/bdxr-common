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

import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.impl.IdentifierImpl;
import org.holodeckb2b.bdxr.xhe.datamodel.ExternalReference;

/**
 * Is a basic implementation of the {@link ExternalReference} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class ExternalReferenceImpl implements ExternalReference {

	private Identifier 		id;
	private ZonedDateTime 	availabilityStart;
	private ZonedDateTime 	availabilityEnd;
	private String 			loginDetails;
	private String 			password;
	
	public ExternalReferenceImpl(String uri) {
		this(new IdentifierImpl(uri));
	}

	public ExternalReferenceImpl(Identifier id) {
		this.id = id;
	}
	
	public Identifier getId() {
		return id;
	}
	public void setId(Identifier id) {
		this.id = id;
	}
	public ZonedDateTime getAvailabilityStart() {
		return availabilityStart;
	}
	public void setAvailabilityStart(ZonedDateTime availabilityStart) {
		this.availabilityStart = availabilityStart;
	}
	public ZonedDateTime getAvailabilityEnd() {
		return availabilityEnd;
	}
	public void setAvailabilityEnd(ZonedDateTime availabilityEnd) {
		this.availabilityEnd = availabilityEnd;
	}
	public String getLoginDetails() {
		return loginDetails;
	}
	public void setLoginDetails(String loginDetails) {
		this.loginDetails = loginDetails;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
