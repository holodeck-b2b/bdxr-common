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
package org.holodeckb2b.bdxr.smp.processing;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;

/**
 * Is an implementation of the abstract {@link KeySelector} that retrieves the public key from the X509 Certificate
 * that is included with the signature. The Certificate used is stored so it can be used after signature validation.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class DefaultKeySelector extends KeySelector {
	/**
	 * The Certificate from which the key was retrieved
	 */
    private X509Certificate certificate;

    /**
     * Attempts to get the public key for verification of the signature from the X509 Certificate that is embedded
     * in the signature, i.e. in the ./ds:KeyInfo/ds:X509Data/ds:X509Certificate element. The selector will use the
     * first Certificate it finds.
     * 
     * {@inheritDoc}
     */
    public KeySelectorResult select(final KeyInfo keyInfo, final KeySelector.Purpose purpose, 
    								final AlgorithmMethod method, final XMLCryptoContext context) 
    																					throws KeySelectorException {
    	Iterator ki = keyInfo.getContent().iterator();
        while (ki.hasNext()) {
            XMLStructure info = (XMLStructure) ki.next();
            if (!(info instanceof X509Data))
                continue;

            X509Data x509Data = (X509Data) info;
            Iterator xi = x509Data.getContent().iterator();
            while (xi.hasNext()) {
                Object o = xi.next();
                if (!(o instanceof X509Certificate))
                    continue;

                final X509Certificate foundCert = (X509Certificate) o;
                final PublicKey key = foundCert.getPublicKey();
                // Make sure the algorithm is compatible with the method.
                if (checkKeyAlgorithm(method.getAlgorithm(), key.getAlgorithm())) {
                	// Store certificate for future use and return key selector result 
                	this.certificate = foundCert;                	
                    return new KeySelectorResult() {    public Key getKey() {
								                            return key;
								                        }
								                    };
                }
            }
        }
        throw new KeySelectorException("No (usable) embedded Certificate found!");
    }

    /**
     * Check that the key can be used with the algorithm used for signing.
     * 
     * @param signatureAlgorithm	The URI identifying the signature algorithm
     * @param keyAlgorithm			The type of key
     * @return	boolean indicating whether the key can be used with the signature algorithm
     */
    private boolean checkKeyAlgorithm(final String signatureAlgorithm, final String keyAlgorithm) {
        return (keyAlgorithm.equalsIgnoreCase("DSA") && signatureAlgorithm.contains("xmldsig#dsa"))
        	|| (keyAlgorithm.equalsIgnoreCase("RSA") && (signatureAlgorithm.contains("xmldsig#rsa") 
        												|| signatureAlgorithm.contains("xmldsig-more#rsa"))
        	   );
    }

    /**
     * Gets the X509 Certificate that was retrieved from the signature and that used to verify it.
     * 
     * @return	The Certificate used for signature verification.
     */
    public X509Certificate getCertificate() {
        return certificate;
    }
}
