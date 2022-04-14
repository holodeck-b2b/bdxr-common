/*
 * Copyright (C) 2022 The Holodeck B2B Team
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
 * This package includes the interfaces representing the SMP data model. It supports both the data models of the OASIS
 * Service Metadata Publishing Standards <a href="http://docs.oasis-open.org/bdxr/bdx-smp/v1.0/os/bdx-smp-v1.0-os.html">
 * version 1.0</a> and <a href="https://docs.oasis-open.org/bdxr/bdx-smp/v2.0/os/bdx-smp-v2.0-os.html">version 2.0</a>
 * as well as the <a href="https://docs.peppol.eu/edelivery/smp/PEPPOL-EDN-Service-Metadata-Publishing-1.2.0-2021-02-24.pdf">
 * PEPPOL specification</a>.
 * <p>The structure of the interfaces follows the class model as defined in the OASIS SMP V2 standard and adds specific
 * interfaces if the OASIS V1 and PEPPOL specification deviate (these share the data model).
 * <p>Note that the interfaces define the <code>boolean equals(Object o)</code> and <code>int hashCode()</code> methods.
 * Although both methods are already implemented by <code>Object</code> these are added to make explicit that
 * implementations MUST implement these methods to compare the actual meta-data. This implies that these implementations
 * must not only compare objects of the same class but also of other classes implementing the same interface!
 */
package org.holodeckb2b.bdxr.smp.datamodel;