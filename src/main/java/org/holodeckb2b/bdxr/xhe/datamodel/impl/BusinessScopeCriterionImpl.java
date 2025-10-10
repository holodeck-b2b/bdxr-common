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

import org.holodeckb2b.bdxr.common.datamodel.Code;
import org.holodeckb2b.bdxr.common.datamodel.Text;
import org.holodeckb2b.bdxr.common.datamodel.impl.TextImpl;
import org.holodeckb2b.bdxr.xhe.datamodel.BusinessScopeCriterion;

/**
 * Is a basic implementation of the {@link BusinessScopeCriterion} interface.
 * 
 * @author Sander Fieten (sander at holodeck-b2b.org)
 * @since 5.0.0
 */
public class BusinessScopeCriterionImpl implements BusinessScopeCriterion {

	private Code 		propertyCode;
	private TextImpl 	value;
	
	public BusinessScopeCriterionImpl(Code propertyCode, Text value) {
		this.propertyCode = propertyCode;
		this.value = new TextImpl(value);
	}
	
	public Code getPropertyCode() {
		return propertyCode;
	}
	
	public void setPropertyCode(Code propertyCode) {
		this.propertyCode = propertyCode;
	}
	
	public Text getValue() {
		return value;
	}
	
	public void setValue(Text value) {
		this.value = new TextImpl(value);
	}
	
	public void setValue(String value) {
		this.value = new TextImpl(value);
	}
}
