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
package org.holodeckb2b.bdxr.smp.datamodel.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.holodeckb2b.bdxr.smp.datamodel.ExtensibleMetadata;
import org.holodeckb2b.bdxr.smp.datamodel.Extension;
import org.holodeckb2b.commons.util.Utils;

/**
 * Is a base class for implementing the SMP meta-data classes that support extensions.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public abstract class ExtensibleMetadataClass {

	protected List<Extension>	extensions;

	/**
	 * Constructs a new instance without extensions.
	 */
	protected ExtensibleMetadataClass() {
		this(null);
	}

	/**
	 * Constructs a new instance with the given extensions.
	 *
	 * @param exts	the extensions to add to the new instance
	 */
	protected ExtensibleMetadataClass(List<Extension> exts) {
		extensions = new ArrayList<>();
		if (!Utils.isNullOrEmpty(exts))
			extensions.addAll(exts);
	}

	/**
	 * Gets the additional, non standard, information related to the meta-data.
	 *
	 * @return The extended meta-data
	 */
	public List<Extension> getExtensions() {
		return extensions;
	}

	/**
	 * Adds an extension to the meta-data.
	 *
	 * @param ext	the extension to add
	 */
	public void addExtension(Extension ext) {
		if (ext == null)
			throw new IllegalArgumentException();

		extensions.add(ext);
	}

	/**
	 * Sets the list of extensions of this the meta-data instance.
	 *
	 * @param exts	the extensions to add
	 */
	public void setExtensions(List<Extension> exts) {
		extensions = exts;
	}

	/**
	 * Removes an extension from the list of extensions, i.e. it removes the extension <code>e</code> from the list for
	 * which <code>e.equals(ext) == true</code>. Therefore for this operation to work correctly, the implementation of
	 * {@link Extension} must implement the <code>equals(Object o)</code> method.
	 *
	 * @param ext the extension to remove
	 */
	public boolean removeExtension(Extension ext) {
		return extensions.remove(ext);
	}

	/**
	 * Removes all extensions from this meta-data instance.
	 */
	public void removeAll() {
		extensions.clear();
	}

	@Override
	public boolean equals(Object o) {
		return Utils.areEqual(this.extensions, ((ExtensibleMetadata) o).getExtensions());
	}

	@Override
	public int hashCode() {
		return Objects.hash(Utils.isNullOrEmpty(extensions) ? null : extensions);
	}

}
