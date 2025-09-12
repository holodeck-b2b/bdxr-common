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

import org.holodeckb2b.bdxr.common.datamodel.Code;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.Text;
import org.holodeckb2b.bdxr.xhe.datamodel.ExternalReference;
import org.holodeckb2b.bdxr.xhe.datamodel.Payload;
import org.holodeckb2b.bdxr.xhe.datamodel.PayloadContent;

/**
 * Is a basic implementation of the {@link Payload} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class PayloadImpl implements Payload {

	private Identifier 			id;
	private Identifier 			customizationId;
	private Identifier 			profileId;
	private Identifier 			profileExecutionId;
	private Code 				contentType;
	private Code 				documentType;
	private Code				validationType;
	private Identifier			validationVersion;
	private Identifier 			handlingService;
	private Collection<Text> 	descriptions;
	private boolean				isEncrypted = false;
	private Text 				encryptionMethod;
	private byte[] 				encryptionHashValue;
	private ExternalReference 	externalDecryptionInfo;
	private ExternalReference 	externalDecryptionKey;
	private ExternalReference 	externalPayloadReference;
	private Collection<ExternalReference> 	additionalExternalInfo;
	private PayloadContent<?>	content;
	
	public Identifier getId() {
		return id;
	}
	
	public void setId(Identifier id) {
		this.id = id;
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
	
	public Code getContentType() {
		return contentType;
	}
	
	public void setContentType(Code contentType) {
		this.contentType = contentType;
	}
	
	public Code getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(Code documentType) {
		this.documentType = documentType;
	}
	
	public Code getValidationType() {
		return validationType;
	}
	
	public void setValidationType(Code validationType) {
		this.validationType = validationType;
	}
	
	public Identifier getValidationVersion() {
		return validationVersion;
	}
	
	public void setValidationVersion(Identifier validationVersion) {
		this.validationVersion = validationVersion;
	}
	
	public Identifier getHandlingService() {
		return handlingService;
	}
	
	public void setHandlingService(Identifier handlingService) {
		this.handlingService = handlingService;
	}
	
	public Collection<Text> getDescriptions() {
		return descriptions != null ? descriptions : Collections.emptyList();		
	}
	
	public void setDescriptions(Collection<Text> descriptions) {
		this.descriptions = descriptions;
	}
	
	public void addDescription(Text description) {
		if (this.descriptions == null) 
			this.descriptions = new ArrayList<>();
		this.descriptions.add(description);
	}
	
	public boolean isEncrypted() {
		return isEncrypted;
	}
	
	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}
	
	public Text getEncryptionMethod() {
		return encryptionMethod;
	}
	
	public void setEncryptionMethod(Text encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
		this.isEncrypted = encryptionMethod != null;
	}
	
	public byte[] getEncryptionHashValue() {
		return encryptionHashValue;
	}
	
	public void setEncryptionHashValue(byte[] encryptionHashValue) {
		this.encryptionHashValue = encryptionHashValue;
	}
	
	public ExternalReference getExternalDecryptionInfo() {
		return externalDecryptionInfo;
	}
	
	public void setExternalDecryptionInfo(ExternalReference externalDecryptionInfo) {
		this.externalDecryptionInfo = externalDecryptionInfo;
		this.isEncrypted = externalDecryptionInfo != null;
	}
	
	public ExternalReference getExternalDecryptionKey() {
		return externalDecryptionKey;
	}
	
	public void setExternalDecryptionKey(ExternalReference externalDecryptionKey) {
		this.externalDecryptionKey = externalDecryptionKey;
		this.isEncrypted = externalDecryptionKey != null;
	}
	
	public ExternalReference getExternalPayloadReference() {
		return externalPayloadReference;
	}
	
	public void setExternalPayloadReference(ExternalReference externalPayloadReference) {
		this.externalPayloadReference = externalPayloadReference;
	}
	
	public Collection<ExternalReference> getAdditionalExternalInfo() {
		return additionalExternalInfo;
	}
	
	public void setAdditionalExternalInfo(Collection<ExternalReference> additionalExternalInfo) {
		this.additionalExternalInfo = additionalExternalInfo;
	}
	
	public void addAdditionalExternalInfo(ExternalReference additionalExternalInfo) {
		if (this.additionalExternalInfo == null)
			this.additionalExternalInfo = new ArrayList<>();
		this.additionalExternalInfo.add(additionalExternalInfo);
	}
	
	public PayloadContent<?> getContent() {
		return content;
	}
	
	public void setContent(PayloadContent<?> content) {
		this.content = content;
	}
}
