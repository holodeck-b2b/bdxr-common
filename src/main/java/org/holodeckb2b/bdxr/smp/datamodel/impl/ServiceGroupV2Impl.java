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
package org.holodeckb2b.bdxr.smp.datamodel.impl;

import java.util.Collection;
import java.util.List;

import org.holodeckb2b.bdxr.common.datamodel.Extension;
import org.holodeckb2b.bdxr.common.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroupV2;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;

/**
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ServiceGroupV2Impl extends AbstractServiceGroupImpl<ServiceReference> implements ServiceGroupV2 {
	private static final long serialVersionUID = 1078779562903287275L;

	/**
     * Default constructor creates "empty" instance
     */
    public ServiceGroupV2Impl() {
    	this(null, null, null);
    }

    /**
     * Creates a new object representing an overview of the services supported by a participant.
     *
     * @param participant   The participant identifier
     * @param svcRefs     Set of references to services which the participant supports
     * @param exts          Any extended meta-data information included in the SMP record
     */
    public ServiceGroupV2Impl(Identifier participant, Collection<? extends ServiceReference> svcRefs, 
    						  List<Extension<?>> exts) {
		super(participant, svcRefs, exts);
    }    
}
