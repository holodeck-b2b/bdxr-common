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
package org.holodeckb2b.bdxr.xhe.datamodel;

import java.util.Collection;

import org.holodeckb2b.bdxr.common.datamodel.Code;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.common.datamodel.Text;

/**
 * Represent the <i>Payload</i> class from the XHE specification. Contains the meta-data about the actual business data
 * contained in the envelope. 
 * <p>
 * See <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-PAYLOAD-INFORMATION">section 
 * 2.9 of the OASIS XHE Standard</a> for the specification of the data elements contained in this class.  
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface Payload {

	/**
	 * @return unique identification of this payload contained within the envelope.
	 */
	Identifier getId();
	
	/**
	 * Gets the human-readable descriptions of the payload. When multiple descriptions are included, each should be in a
	 * different language, i.e. {@link Text#getLanguage() getLanguage()} should be different for each description. 
	 * 
	 * @return	the descriptions of the payload
	 */
	Collection<Text> getDescriptions();
	
	/**
	 * @return the abstract archetype of the payload 
	 */
	Code getDocumentType();
	
	/**
	 * Gets the file format or octet representation of the payload. Generally this is the MIME type, but envelope 
	 * profiles may specify a different code list to be used.
	 * 
	 * @return	code identifying the format of the payload content
	 */
	Code getContentType();
	
	/**
	 * @return the identification of a customization that applies to the payload.
	 */
	Identifier getCustomizationId();
	
	/**
	 * @return the identification of a specific profile found within the customization that applies to the payload.
	 */
	Identifier getProfileId();
	
	/**
	 * @return the identification of a particular instance of using the given profile that applies to the payload.
	 */
	Identifier getProfileExecutionId();

	/**
	 * @return the identification of the service that should process the payload.
	 */
	Identifier getHandlingService();
	
	/**
	 * @return the validation type of the payload, used for the task of verifying that the payload is valid.
	 */
	Code getValidationType();
	
	/**
	 * @return identification of the version of the validation type 
	 */
	Identifier getValidationVersion();
	
	/**
	 * Indicates whether the payload is encrypted. Note that the OASIS XHE Standard does require that this indicator is
	 * included and therefore the return value is specified as a primitive <code>boolean</code>.
	 * 
	 * @return <code>true</code> if the payload is encrypted, <code>false</code> if not
	 */
	boolean isEncrypted();
	
	/**
	 * Gets the encryption method used to encrypt the payload. Note that the XHE Standard specifies this information
	 * element to be of type <code>Text</code> and therefore includes the language code. This could be used to indicate
	 * the format used to described the encryption algorithm.
	 * 
	 * @return the encryption method used to encrypt the payload data
	 */
	Text getEncryptionMethod();
	
	/**
	 * Gets the SHA-256 hash value of the unencrypted payload data. Although the OASIS XHE Standard specifies this 
	 * information element to be of type <code>Text</code> the value is here defined as a byte array as the hash value
	 * is a binary value. Also note that this hash value on itself does not provide integrity protection of the payload 
	 * and needs signing of the whole envelope to provide that.
	 * 
	 * @return	the SHA-256 hash value of unencrypted payload data 
	 */
	byte[] getEncryptionHashValue();
	
	/**
	 * @return reference to external information for decryption of the payload
	 */
	ExternalReference getExternalDecryptionInfo();
	
	/**
	 * @return reference to external decryption key to decrypt the payload
	 */
	ExternalReference getExternalDecryptionKey();
	
	/**
	 * @return the collection of references to external information about the payload
	 */
	Collection<ExternalReference> getAdditionalExternalInfo();
	
	/**
	 * Gets the reference to the external location where the payload content is available. When this element is present
	 * the {@link #getContent()} method SHOULD return <code>null</code> as content is not included in the envelope.
	 * 
	 * @return reference to external location of the payload
	 */
	ExternalReference getExternalPayloadReference();
	
	/**
	 * Gets the payload's content as contained in the envelope. When this element is present the {@link 
	 * #getExternalPayloadReference()} SHOULD return <code>null</code> as the content is included in the envelope.
	 * 
	 * @return the content of the payload
	 */
	PayloadContent<?> getContent();
}
