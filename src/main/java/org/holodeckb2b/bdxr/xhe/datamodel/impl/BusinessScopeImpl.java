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
package org.holodeckb2b.bdxr.xhe.datamodel.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.holodeckb2b.bdxr.xhe.datamodel.BusinessScope;
import org.holodeckb2b.bdxr.xhe.datamodel.BusinessScopeCriterion;
import org.holodeckb2b.bdxr.xhe.datamodel.ExternalReference;

/**
 * Is a basic implementation of the {@link BusinessScope} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class BusinessScopeImpl implements BusinessScope {

	private Collection<BusinessScopeCriterion> 	businessScopeCriteria;
	private Collection<ExternalReference> 		externalReferences;
	
	public Collection<BusinessScopeCriterion> getBusinessScopeCriteria() {
		return businessScopeCriteria != null ? businessScopeCriteria : Collections.emptyList();
	}
	
	public void setBusinessScopeCriteria(Collection<BusinessScopeCriterion> businessScopeCriteria) {
		this.businessScopeCriteria = businessScopeCriteria;
	}
	
	public void addBusinessScopeCriterion(BusinessScopeCriterion businessScopeCriterion) {
		if (this.businessScopeCriteria == null)
			this.businessScopeCriteria = new ArrayList<>();
		this.businessScopeCriteria.add(businessScopeCriterion);
	}
	
	public Collection<ExternalReference> getExternalReferences() {
		return externalReferences != null ? externalReferences : Collections.emptyList();
	}
	
	public void setExternalReferences(Collection<ExternalReference> externalReferences) {
		this.externalReferences = externalReferences;
	}

	public void addExternalReference(ExternalReference externalReference) {
		if (this.externalReferences == null)
			this.externalReferences = new ArrayList<>();
		this.externalReferences.add(externalReference);
	}
}
