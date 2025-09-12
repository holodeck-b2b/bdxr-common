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
 * This package includes a basic implementation of the interfaces in {@link org.holodeckb2b.bdxr.smp.datamodel}. These
 * do not make a copy of provided data when constructing instances or adding them to contained collections. This means
 * that modifications to objects passed to constructors and methods after calling these constructors or methods will
 * also affect the data contained in the objects. 
 */
package org.holodeckb2b.bdxr.smp.datamodel.impl;