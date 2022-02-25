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

import java.io.Serializable;

/**
 * Represents an <i>Extension</i> which can be used to include non standardised meta-data in an SMP registration.
 * Although the OASIS SMP v2 specification defines some generic meta-data fields for extensions, their use is optional
 * and depends on the use-case. Therefore this interface does not define any methods and it is left to implementation
 * to define the actually data.
 * <p>Corresponds to the <code>SMPExtension</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface Extension extends Serializable {
}
