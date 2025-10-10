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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.xhe.datamodel.Envelope;
import org.holodeckb2b.bdxr.xhe.datamodel.Header;
import org.holodeckb2b.bdxr.xhe.datamodel.Payload;

/**
 * Is a basic implementation of the {@link Envelope} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class EnvelopeImpl implements Envelope {
	private static final long serialVersionUID = 5341220854419790408L;
	
	private Identifier 			XHEVersionId;	
	private Identifier 			customizationId;
	private Identifier 			profileId;
	private Identifier 			profileExecutionId;
	private Header 				header;
	private Collection<Payload> payloads; 	
	private List<Extension<?>> 	extensions;
	
	public Identifier getXHEVersionId() {
		return XHEVersionId;
	}

	public void setXHEVersionId(Identifier xHEVersionId) {
		XHEVersionId = xHEVersionId;
	}

	public Identifier getCustomizationId() {
		return customizationId;
	}

	public void setCustomizationId(Identifier customizationId) {
		this.customizationId = customizationId;
	}

	public Identifier getProfileId() {
		return profileId;
	}

	public void setProfileId(Identifier profileId) {
		this.profileId = profileId;
	}

	public Identifier getProfileExecutionId() {
		return profileExecutionId;
	}

	public void setProfileExecutionId(Identifier profileExecutionId) {
		this.profileExecutionId = profileExecutionId;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	@Override
	public Collection<Payload> getPayloads() {
		return (payloads != null) ? payloads : Collections.emptyList();
	}

	public void setPayloads(Collection<Payload> payloads) {
		this.payloads = payloads;
	}

	/**
	 * Adds a Payload to the envelope.
	 * 
	 * @param payload the payload to add
	 */
	public void addPayload(Payload payload) {
		if (payloads == null) {
			payloads = new ArrayList<>();
		}
		payloads.add(payload);
	}

	@Override
	public List<Extension<?>> getExtensions() {
		return extensions != null ? extensions : Collections.emptyList();
	}
	
	public void setExtensions(List<Extension<?>> extensions) {
		this.extensions = extensions;
	}
	
	/**
	 * Adds an Extension to the envelope.
	 * 
	 * @param extension the payload to add
	 */
	public void addExtension(Extension<?> extension) {
		if (extensions == null) {
			extensions = new ArrayList<>();
		}
		extensions.add(extension);
	}
}
