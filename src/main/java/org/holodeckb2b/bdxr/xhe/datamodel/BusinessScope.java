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

/**
 * Represent the <i>BusinessScope</i> class from the XHE specification. Contains the meta-data about the context in 
 * which the business document is used, for example the business process. These can be either directly included in the
 * envelope or be a reference to an external location.
 * <p>
 * See <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/os/xhe-v1.0-os-oasis.html#S-BUSINESS-SCOPE-INFORMATION">
 * section 2.6 of the OASIS XHE Standard</a> for the specification of the data elements contained in this class.  
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public interface BusinessScope {

	/**
	 * @return collection of the business scope meta-data contained in the envelope
	 */
	Collection<BusinessScopeCriterion> getBusinessScopeCriteria();
	
	/**
	 * @return collection of references to external locations containing the business scope meta-data 
	 */
	Collection<ExternalReference> getExternalReferences();
}
