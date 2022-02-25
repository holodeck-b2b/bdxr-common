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

/**
 * Represents the meta-data about a <i>Redirection</i> as specified in the OASIS V1 and PEPPOL SMP Specifications. In
 * these specifications the certificate of the new SMP is referenced through the <i>Subject Unique Identifier</i>
 * certificate field.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface RedirectionV1 extends Redirection {

	/**
	 * Gets the <i>Subject Unique Identifier</i> field of the X509 certificate that the SMP server this redirection is
	 * to should use.
	 *
	 * @return	 bit string value of the <i>Subject Unique Identifier</i> field 
	 */
	boolean[] getSMPSubjectUniqueID();
}
