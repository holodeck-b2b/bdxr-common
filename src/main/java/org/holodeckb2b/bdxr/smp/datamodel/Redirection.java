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

import java.net.URL;

/**
 * Represents the meta-data about a <i>Redirection</i> that indicates the queried server does not longer maintain the
 * service meta-data about the participant or process (this is new in the OASIS SMP V2 Spec).
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface Redirection extends ExtensibleMetadata {

	/**
	 * Gets the URL to which the query should be redirected
	 *
	 * @return	The URL to use for a new query
	 */
	URL getNewSMPURL();

	/**
	 * Determines if the given object represents the same Redirection meta-data.
	 *
	 * @param o		the object the compare
	 * @return		<code>true</code> iff <code>o</code> is an instance of <code>Redirection</code> and represent the
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
