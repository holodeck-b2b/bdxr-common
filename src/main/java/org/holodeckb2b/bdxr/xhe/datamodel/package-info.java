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
/**
 * This package includes the interfaces representing the XHE data model which specifies the meta-data of business 
 * documents. The XHE data model is specified in the <a href="https://docs.oasis-open.org/bdxr/xhe/v1.0/xhe-v1.0-oasis.html">
 * OASIS XHE Standard</a>. This standard is a joint work between the OASIS BDXR TC and the UN/CEFACT Methodology and 
 * Technology PDA and as a result contains two naming conventions to specify the data model. The interfaces in this 
 * package follow the OASIS naming conventions. Note that it is also possible to use this data model to represent  
 * Standard Business Documents Headers (SBDH) as specified by UN/CEFACT.
 * <p>Most data elements defined in the XHE data model are optional and it is up to business domain specific profile to
 * further specify their use and whether they are required. Therefore almost all methods can return <code>null</code> or
 * empty collections (multi-valued data elements should never return <code>null</code>). When an element is required 
 * this is explicitly mentioned.  
 * <p>Note that the interfaces define the <code>boolean equals(Object o)</code> and <code>int hashCode()</code> methods.
 * Although both methods are already implemented by <code>Object</code> these are added to make explicit that
 * implementations MUST implement these methods to compare the actual meta-data. This implies that these implementations
 * must not only compare objects of the same class but also of other classes implementing the same interface!
 */
package org.holodeckb2b.bdxr.xhe.datamodel;