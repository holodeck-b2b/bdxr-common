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
package org.holodeckb2b.bdxr.smp.processing;

import org.holodeckb2b.bdxr.datamodel.ISMPQueryResult;
import org.holodeckb2b.bdxr.smp.SMPQueryException;
import org.w3c.dom.Document;

/**
 * Defines the interface for processing an XML document that was received as a result of a SMP request and transforming
 * it into the object model defined in {@link com.chasquisservices.bdxr.datamodel}.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ISMPResultProcessor {

    /**
     * Transforms the XML representation of a SMP query result into an object model representation.
     *
     * @param xmlDocument   The XML representation of the SMP result
     * @return              A {@link ISMPQueryResult} instance that contains the object model representation. MUST NOT
     *                      be <code>null</code>. If the processor cannot convert the given XML it should throw an
     *                      exception.
     * @throws SMPQueryException When the given XML document cannot be converted into an object model representation.
     */
    ISMPQueryResult processResult(Document xmlDocument) throws SMPQueryException;
}
