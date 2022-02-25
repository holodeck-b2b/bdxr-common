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
 * Indicates that the given data are the result of an SMP query, i.e. contain the meta-data about a Participant. The
 * SMP server may sign the response, in which case the certificate used for signing is also included with the result.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface QueryResult extends Serializable {

	/**
     * Gets the identifier of the participant the meta-data included in the apply to
     *
     * @return The participant id
     */
    Identifier getParticipantId();
}
