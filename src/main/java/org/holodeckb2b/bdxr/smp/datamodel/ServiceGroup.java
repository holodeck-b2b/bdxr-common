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

import java.util.Collection;

/**
 * Represents the generic <i>Service Group</i> meta-data that is common in all SMP specifications. The Service Group
 * meta-data class provides an overview of the services/documents supported by a Participant. Although the way the data
 * is structured is differently in the OASIS V2 specification and the OASIS V1 and PEPPOL specs, the base is the same
 * and consists of a set of references. Therefore this is a generic interface that defines the method to get the set of
 * references. Sub interfaces for the different specs will define the type of the references.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @see ServiceGroupV1
 * @see ServiceGroupV2
 */
public interface ServiceGroup<T> extends ExtensibleMetadata, QueryResult {

	Collection<? extends T> getServiceReferences();
}
