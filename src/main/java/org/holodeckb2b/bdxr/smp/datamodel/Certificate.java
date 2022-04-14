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
package org.holodeckb2b.bdxr.smp.datamodel;

import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;

/**
 * Represents the certificate data included in a SMP response consisting of the X509 certificate itself and associated
 * meta-data. Part of the meta-data is an indication of how the certificate should be or is used, for example for
 * encryption or signing. It should be noted that when the Certificate is included as part of Endpoint meta-data and
 * has an indication of "for signing" it applies to message send by the participant and not to messages send to it! In
 * other words the certificate can be used to verify that it was indeed the participant that responded.
 *
 * <p>Corresponds to the <code>Certificate</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface Certificate extends ExtensibleMetadata {

	/**
	 * Gets the X509Certificate.
	 *
	 * @return	the certificate
	 */
	X509Certificate getX509Cert();

	/**
	 * Returns the code indicating how this certificate can be used.
	 * <p>NOTE: In the OASIS specification the Code has additional attributes that include meta-data on the code list
	 * it originates from. These are (currently) not included.
	 *
	 * @return	usage code
	 */
	String getUsage();

	/**
	 * Gets the time stamp from which on the certificate can be used.
	 * <p>NOTE: The activation time stamp can be different from the date the certificate itself is valid.
	 *
	 * @return	the activation time stamp of the certificate.
	 */
	ZonedDateTime getActivationDate();

	/**
	 * Gets the time stamp until which the certificate can be used.
	 * <p>NOTE: This can be before the date the certificate itself expires.
	 *
	 * @return	the time stamp from which on the certificate should not be used anymore
	 */
	ZonedDateTime getExpirationDate();

	/**
	 * Gets the descriptive text on the certificate.
	 *
	 * @return the description of the certificate
	 */
	String getDescription();

	/**
	 * Determines if the given object represents the same Certificate meta-data.
	 * <p>NOTE: The activation and expiration time stamps should be compared as instants on the time line to prevent
	 * issues with the time zone indicator which could be different on two {@link ZonedDateTime} objects that represent
	 * the same time stamp.
	 *
	 * @param o		the object the compare
	 * @return		<code>true</code> iff <code>o</code> is an instance of <code>Certificate</code> and represent the
	 * 				same meta-data.
	 */
	@Override
	boolean equals(Object o);

	/**
	 * Calculates the hash value for the meta-data represented by this object. The hash value of two instances, <code>i1
	 * </code> and <code>i2</code> must be the same when they represent the same meta-data, i.e. when
	 * <code>i1.equals(i2) == true</code>.
	 *
	 * @return hash value for this instance
	 */
	@Override
	int hashCode();
}
