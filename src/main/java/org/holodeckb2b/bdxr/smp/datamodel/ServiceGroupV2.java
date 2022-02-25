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
 * Represents the <i>Service Group</i> meta-data as defined in the OASIS V2 specification. The Service Group consists of
 * the participant identifier and a list of service and processes combinations which are supported by the participant.
 *
 * <p>Corresponds to the <code>ServiceGroup</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ServiceGroupV2 extends ServiceGroup<ServiceReference>, ExtensibleMetadata {

//	/**
//	 * Gets the set of service and process identifier combinations which are supported by the participant.
//	 *
//	 * @return collection of {@link ServiceReference}s
//	 */
//	Collection<? extends ServiceReference> getServiceReferences();
}
