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

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Represents the generic meta-data of an <i>Endpoint</i> that is common in all SMP specifications. This includes the
 * meta-data on the certificate(s) used by the endpoint. In the OASIS V1 and PEPPOL specifications there can be just
 * one X509.v3 certificate associated with an endpoint, while in the OASIS V2 specification multiple certificates can
 * be related and there is also additional meta-data on its use. To make processing more uniform the extended
 * information set of the OASIS V2 spec is used and for V1 registrations the additional fields are just ignored.
 *
 * <p>Corresponds to the <code>Endpoint</code> class of the OASIS SMP V2 specification's data model.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public interface EndpointInfo extends ExtensibleMetadata {

	/**
	 * Gets the transport profile the endpoint supports
	 *
	 * @return The transport profile
	 */
	String getTransportProfile();

	/**
	 * Gets the URL where the endpoint receives messages.
	 *
	 * @return The Endpoint's URL
	 */
	URL getEndpointURL();

	/**
	 * Gets the date and time after which the endpoint can be used
	 *
	 * @return The endpoint's activation date
	 */
	ZonedDateTime getServiceActivationDate();

	/**
	 * Gets the date and time when the endpoint will expire and should not be used anymore.
	 *
	 * @return The timestamp when the service expires
	 */
	ZonedDateTime getServiceExpirationDate();

	/**
	 * Gets the (human readable) description of this endpoint.
	 *
	 * @return	The endpoint's descriptive text
	 */
	String getDescription();

	/**
	 * Gets the [technical] contact information for this endpoint.
	 *
	 * @return	The contact details for this endpoint
	 */
	String getContactInfo();

	/**
	 * Gets the certificate information of the endpoint.
	 *
	 * @return The certificates used by the endpoint
	 */
	Collection<? extends Certificate> getCertificates();
}
