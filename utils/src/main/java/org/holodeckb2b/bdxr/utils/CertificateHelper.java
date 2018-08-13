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
package org.holodeckb2b.bdxr.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

/**
 * Is a utility class for processing of X509 certificates.
 *
 * @author Sander Fieten (sander at chasquis-consulting.com) 
 */
public class CertificateHelper {

    /**
     * Certificate factory to create the X509 Certificate object
     */
    private static CertificateFactory certificateFactory;

    /**
     * Gets the X509 Certificate from the base64 encoded byte array.
     *
     * @param b64EncodedCertificate The string containing the base64 encoded bytes
     * @return  The decoded Certificate instance
     * @throws CertificateException When the string does not contain a valid base64 encoded certificate or when there is
     *                              a problem in loading required classes for certificate processing
     */
    public static X509Certificate getCertificate(final String b64EncodedCertificate) throws CertificateException {
        final byte[] certBytes = Base64.decodeBase64(b64EncodedCertificate);
        if (certBytes == null)
            throw new CertificateException("String is not a valid base64 encoding");
        
        return getCertificate(certBytes);
    }

    /**
     * Converts the given byte array into a X509 Certificate .
     *
     * @param certBytes The byte array containing the certificate
     * @return  The Certificate instance
     * @throws CertificateException When there is a problem in loading required classes for certificate processing
     */
    public static X509Certificate getCertificate(final byte[] certBytes) throws CertificateException {
        if (certBytes == null || certBytes.length == 0)
            return null;
        else
        	return (X509Certificate) getCertificateFactory().generateCertificate(new ByteArrayInputStream(certBytes));
    }
    
    /**
     * Reads the key pair from the specified PKCS#12 file. The PKCS#12 file must contain only one key pair that uses 
     * the same password as for the file itself for this method to work.   
     * 
     * @param pkcs12File		Path to the PKCS#12 file
     * @param pkcs12Password	Password to get access to the PKCS#12 file
     * @return	The keypair read from the PKCS#12 file
     * @throws CertificateException When the keypair cannot be read from the specified file, for example because the
     * 								file is missing, an incorrect password is given or multiple entries exists in the 
     * 								given file  
     */
    public static PrivateKeyEntry readKeyPairFromPKCS12(final String pkcs12File, final String pkcs12Password)
    																throws CertificateException {
        try {
            final KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new java.io.FileInputStream(pkcs12File)) {
                keyStore.load(fis, (!Utils.isNullOrEmpty(pkcs12Password) ? pkcs12Password.toCharArray() 
                														 : new char[] {}));
            }
            final Enumeration<String> aliases = keyStore.aliases();
            final String alias = aliases.nextElement();
            if (aliases.hasMoreElements())
            	throw new KeyStoreException("More than one keypair in file!");
            
            return (PrivateKeyEntry) keyStore.getEntry(alias, 
            										   new KeyStore.PasswordProtection(pkcs12Password.toCharArray()));
        } catch (NullPointerException | IOException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException | UnrecoverableEntryException ex) {
            throw new CertificateException("Cannot load keypair from specified PKCS#12 file [" + pkcs12File + "]!",
            							   ex);
        }
    }
    
    /**
     * Gets the CN field of the provided X509 certificate's Subject.
     * 
     * @param cert	The X509 certificate
     * @return	The Subject's CN field 
     * @throws CertificateException When the certificate could not be read
     */
    public static String getCN(final X509Certificate cert) throws CertificateException {
    	try {
			X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();
			RDN cn = x500name.getRDNs(BCStyle.CN)[0];
			return IETFUtils.valueToString(cn.getFirst().getValue());
		} catch (CertificateEncodingException invalidCert) {
			throw new CertificateException("Unable to process the given certificate!", invalidCert);
		}    	
    }
    
    /**
     * Gets the {@link CertificateFactory} instance to use for creating the <code>X509Certificate</code> object from a
     * byte array.
     *
     * @return  The <code>CertificateFactory</code> to use
     * @throws CertificateException     When the certificate factory could not be loaded
     */
    private static CertificateFactory getCertificateFactory() throws CertificateException {
        if (certificateFactory == null)
            certificateFactory = CertificateFactory.getInstance("X.509");
        return certificateFactory;
    }
}
