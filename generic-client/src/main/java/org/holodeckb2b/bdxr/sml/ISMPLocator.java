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
package org.holodeckb2b.bdxr.sml;

import java.net.URI;

import org.holodeckb2b.bdxr.datamodel.Identifier;

/**
 * Defines the interface for the component responsible for locating the <i>service metadata provider</i> where a
 * participant has registered the information on the documents it can receive.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface ISMPLocator {

    /**
     * Gets the base URL of the SMP that serves the given participant.
     *
     * @param participant   The identifier of the participant
     * @return              Base URL of the SMP serving the participant
     * @throws SMPLocatorException  When there is a problem in locating the SMP for the given participant. This can be
     *                              caused by in error in the lookup, an invalid participant identifier or that there
     *                              is no SMP registered for the participant.
     */
    URI locateSMP(final Identifier participant) throws SMPLocatorException;
}
