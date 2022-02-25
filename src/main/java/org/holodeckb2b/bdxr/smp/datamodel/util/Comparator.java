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
package org.holodeckb2b.bdxr.smp.datamodel.util;

import java.util.Collection;
import org.bouncycastle.util.Arrays;
import org.holodeckb2b.bdxr.smp.datamodel.Certificate;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfo;
import org.holodeckb2b.bdxr.smp.datamodel.EndpointInfoV1;
import org.holodeckb2b.bdxr.smp.datamodel.IDScheme;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessIdentifier;
import org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo;
import org.holodeckb2b.bdxr.smp.datamodel.Redirection;
import org.holodeckb2b.bdxr.smp.datamodel.RedirectionV1;
import org.holodeckb2b.bdxr.smp.datamodel.RedirectionV2;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroup;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroupV1;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceGroupV2;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceMetadata;
import org.holodeckb2b.bdxr.smp.datamodel.ServiceReference;
import org.holodeckb2b.commons.util.Utils;

/**
 * Provides functions to check if two meta-data instances represent the same information.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class Comparator {

	/**
	 * Determines if the given Certficate objects represent the same certificate.
	 *
	 * @param c1	the first Certificate object
	 * @param c2	the second Certificate object
	 * @return		<code>true</code> if the meta-data objects represent the same certificate or if both are <code>null
	 *				</code>,<br/> <code>false</code> otherwise
	 */
	public static final boolean equalCertificates(Certificate c1, Certificate c2) {
		return (c1 == c2) || (c1 != null && c2 != null &&
				Utils.nullSafeEqual(c1.getX509Cert(), c2.getX509Cert())
				&& Utils.nullSafeEqual(c1.getUsage(), c2.getUsage())
				&& Utils.nullSafeEqual(c1.getActivationDate(), c2.getActivationDate())
				&& Utils.nullSafeEqual(c1.getExpirationDate(), c2.getExpirationDate())
				&& Utils.nullSafeEqual(c1.getDescription(), c2.getDescription())
				&& Utils.areEqual(c1.getExtensions(), c2.getExtensions()));
	}

	/**
	 * Determines if the given EndpointInfo objects represent the same endpoint.
	 *
	 * @param ep1	the first EndpointInfo object
	 * @param ep2	the second EndpointInfo object
	 * @return		<code>true</code> if the meta-data objects represent the same endpoint, or if both are <code>null
	 *				</code>, <code>false</code> otherwise
	 */
	public static final boolean equalEndpoints(EndpointInfo ep1, EndpointInfo ep2) {
		boolean equal = (ep1 == ep2) || (ep1 != null && ep2 != null &&
				Utils.nullSafeEqual(ep1.getTransportProfile(), ep2.getTransportProfile())
				&& Utils.nullSafeEqual(ep1.getContactInfo(), ep2.getContactInfo())
				&& Utils.nullSafeEqual(ep1.getServiceActivationDate(), ep2.getServiceActivationDate())
				&& Utils.nullSafeEqual(ep1.getServiceExpirationDate(), ep2.getServiceExpirationDate())
				&& Utils.nullSafeEqual(ep1.getDescription(), ep2.getDescription())
				&& areEqual(ep1.getCertificates(), ep2.getCertificates(), Comparator::equalCertificates)
				&& Utils.areEqual(ep1.getExtensions(), ep2.getExtensions()));
		if (equal && (ep1 instanceof EndpointInfoV1) && (ep2 instanceof EndpointInfoV1)) {
			EndpointInfoV1 ep1v1 = (EndpointInfoV1) ep1;
			EndpointInfoV1 ep2v1 = (EndpointInfoV1) ep2;
			equal &= Utils.nullSafeEqual(ep1v1.getBusinessLevelSignatureRequired(),
										 ep2v1.getBusinessLevelSignatureRequired());
			equal &= Utils.nullSafeEqual(ep1v1.getMinimumAuthenticationLevel(), ep2v1.getMinimumAuthenticationLevel());
			equal &= Utils.nullSafeEqual(ep1v1.getTechnicalInformationURL(), ep2v1.getTechnicalInformationURL());
		} else
			equal &= !(ep1 instanceof EndpointInfoV1) && !(ep2 instanceof EndpointInfoV1);

		return equal;
	}

	/**
	 * Determines if the given identifier scheme objects represent the same id scheme.
	 *
	 * @param s1	the first identifier scheme
	 * @param s2	the second identifier scheme
	 * @return		<code>true</code> if the scheme identifiers are equal or both are <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	public static final boolean equalIDSchemes(IDScheme s1, IDScheme s2) {
		return (s1 == s2) || (s1 != null && s2 != null && s1.getSchemeId().equals(s2.getSchemeId()));
	}

	/**
	 * Determines if the given Identifier object represent the same identifier, i.e. if both instances are from the same
	 * identifier scheme and have the same value (compared using the case sensitivity policy of the scheme).
	 *
	 * @param i1	the first identifier object
	 * @param i2	the second identifier object
	 * @return		<code>true</code> if the identifiers are equal or if both are <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	public static boolean equalIDs(Identifier i1, Identifier i2) {
		if (i1 == i2)
			return true;
		else if (i1 == null || i2 == null)
			return false;

		final IDScheme s1 = i1.getScheme(), s2 = i2.getScheme();
		final String   v1 = i1.getValue(), v2 = i2.getValue();

		return (s1 == null ? s2 == null : equalIDSchemes(s1, s2))
                && (v1 == null ? v2 == null : (s1 != null && s1.isCaseSensitive() ? v1.equals(v2)
																				  : v1.equalsIgnoreCase(v2)));
	}

	/**
	 * Determines if the given process identifiers represent the same process. The equality is the same as for generic
	 * identifiers but must also take into account the special "no-process" identifier.
	 *
	 * @param pi1	the first process identifier
	 * @param pi2	the second process identifier
	 * @return		<code>true</code> if both process identifiers represent the same process or are both <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	public static final boolean equalProcIDs(ProcessIdentifier pi1, ProcessIdentifier pi2) {
		return (pi1 != null && pi2 != null && pi1.isNoProcess() && pi2.isNoProcess())
				|| equalIDs((Identifier) pi1, (Identifier) pi2);
	}

	/**
	 * Determines if the given ProcessGroup objects represent the same group of processes.
	 *
	 * @param pg1	the first ProcessGroup object
	 * @param pg2	the second ProcessGroup object
	 * @return		<code>true</code> if the meta-data objects represent the same process group or are both <code>null
	 *				</code>, <code>false</code> otherwise
	 */
	public static final boolean equalProcGroups(ProcessGroup pg1, ProcessGroup pg2) {
		return (pg1 == pg2) || (pg1 != null && pg2 != null &&
				Utils.areEqual(pg1.getProcessInfo(), pg2.getProcessInfo())
				&& areEqual(pg1.getEndpoints(), pg2.getEndpoints(), Comparator::equalEndpoints)
				&& Utils.nullSafeEqual(pg1.getRedirection(), pg2.getRedirection())
				&& Utils.areEqual(pg1.getExtensions(), pg2.getExtensions()));
	}

	/**
	 * Determines if the given ProcessInfo objects represent the same process.
	 *
	 * @param p1	the first ProcessInfo object
	 * @param p2	the second ProcessInfo object
	 * @return		<code>true</code> if the meta-data objects represent the same process or are both <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	public static final boolean equalProcessInfos(ProcessInfo p1, ProcessInfo p2) {
		return (p1 == p2) || (p1 != null && p2 != null &&
				equalProcIDs(p1.getProcessId(), p2.getProcessId())
				&& areEqual(p1.getRoles(), p2.getRoles(), Comparator::equalIDs)
				&& Utils.areEqual(p1.getExtensions(), p2.getExtensions()));
	}

	/**
	 * Determines if the given Redirection objects represent the same redirection.
	 *
	 * @param r1	the first Redirection object
	 * @param r2	the seconde Redirection object
	 * @return		<code>true</code> if the meta-data objects represent the same redirection or are both <code>null
	 *				</code>, <code>false</code> otherwise
	 */
	public static final boolean equalRedirects(Redirection r1, Redirection r2) {
		return (r1 == r2) || (r1 != null && r2 != null &&
				Utils.nullSafeEqual(r1.getNewSMPURL(), r2.getNewSMPURL())
				&& Utils.areEqual(r1.getExtensions(), r2.getExtensions()))
				&& (r1 instanceof RedirectionV1 ? Arrays.areEqual(((RedirectionV1) r1).getSMPSubjectUniqueID(),
																  ((RedirectionV1) r2).getSMPSubjectUniqueID())
						: Utils.nullSafeEqual(((RedirectionV2) r1).getSMPCertificate(),
											  ((RedirectionV2) r2).getSMPCertificate()));
	}

	/**
	 * Determines if the generic meta-data of two ServiceGroup instances are equal.
	 *
	 * @param sg1	the first ServiceGroup object
	 * @param sg2	the second ServiceGroup object
	 * @return		<code>true</code> if the generic service group meta-data are equal or both are <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	public static final boolean equalSvcGroups(ServiceGroup sg1, ServiceGroup sg2) {
		if (sg1 == sg2)
			return true;
		else if (sg1 == null || sg2 == null || !sg1.getClass().equals(sg2.getClass()))
			return false;

		return equalIDs(sg1.getParticipantId(), sg2.getParticipantId())
			&& Utils.areEqual(sg1.getExtensions(), sg2.getExtensions())
			&& (sg1 instanceof ServiceGroupV1 ? equalV1SvcGroups((ServiceGroupV1) sg1, (ServiceGroupV1) sg2)
											  : equalV2SvcGroup((ServiceGroupV2) sg1, (ServiceGroupV2) sg2));
	}

	/**
	 * Helper method to determine if the given ServiceGroupV1 objects represents the same service group.
	 *
	 * @param sg1	the first ServiceGroupV1 object
	 * @param sg2	the second ServiceGroupV1 object
	 * @return		<code>true</code> if the generic service group meta-data are equal or both are <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	private static final boolean equalV1SvcGroups(ServiceGroupV1 sg1, ServiceGroupV1 sg2) {
		return Utils.areEqual(sg1.getServiceReferences(), sg2.getServiceReferences());
	}

	/**
	 * Helper method to determine if the given ServiceGroupV2 objects represent the same service group.
	 *
	 * @param sg1	the first ServiceGroupV1 object
	 * @param sg2	the second ServiceGroupV1 object
	 * @return		<code>true</code> if the generic service group meta-data are equal or both are <code>null</code>,
	 *				<code>false</code> otherwise
	 */
	private static final boolean equalV2SvcGroup(ServiceGroupV2 sg1, ServiceGroupV2 sg2) {
		return areEqual(sg1.getServiceReferences(), sg2.getServiceReferences(), Comparator::equalSvcRefs);
	}

	/**
	 * Determines if the given ServiceMetadata objects represent the same service meta-data.
	 *
	 * @param s1	the first ServiceMetadata object
	 * @param s2	the second ServiceMetadata object
	 * @return		<code>true</code> if the meta-data objects represent the same service meta-data or are both <code>
	 *				null</code>, <code>false</code> otherwise
	 */
	public static final boolean equalSvcMetadata(ServiceMetadata s1, ServiceMetadata s2) {
		return (s1 == s2) || (s1 != null && s2 != null &&
				Utils.nullSafeEqual(s1.getParticipantId(), s2.getParticipantId())
				&& Utils.nullSafeEqual(s1.getServiceId(), s2.getServiceId())
				&& areEqual(s1.getProcessMetadata(), s2.getProcessMetadata(), Comparator::equalProcGroups)
				&& Utils.areEqual(s1.getExtensions(), s2.getExtensions()));
	}

	/**
	 * Determines if the given ServiceReference objects represent the same reference.
	 *
	 * @param ref1	the first ServiceReference object
	 * @param ref2	the second ServiceReference object
	 * @return		<code>true</code> if the meta-data objects represent the same reference,<br/>
	 *				<code>false</code> otherwise
	 */
	public static final boolean equalSvcRefs(ServiceReference ref1, ServiceReference ref2) {
		return (ref1 == ref2) || (ref1 != null && ref2 != null &&
				Utils.nullSafeEqual(ref1.getServiceId(), ref2.getServiceId())
			&& areEqual(ref1.getProcessInfo(), ref2.getProcessInfo(), Comparator::equalProcessInfos)
			&& Utils.areEqual(ref1.getExtensions(), ref2.getExtensions()));
	}

	/**
	 * Helper method to check that two collections represent the same meta-data.
	 *
	 * @param <T>		the type of meta-data being compared
	 * @param c1		the first collection
	 * @param c2		the second collections
	 * @param checker	the function to check if the individual elements of the collections represent the same data
	 * @return <code>true</code> if both collection are <code>null</code>, empty or contain each contain the same number
	 *		   of elements which represent the same meta-data
	 */
	protected static <T> boolean areEqual(Collection<? extends T> c1, Collection<? extends T> c2, Eq<T> checker) {
		if (Utils.isNullOrEmpty(c1) && Utils.isNullOrEmpty(c2))
			return true;
		else if (Utils.isNullOrEmpty(c1) || Utils.isNullOrEmpty(c2))
			return false;
		else
			return  c1.size() == c2.size()
				&& c1.parallelStream().allMatch(e1 -> c2.parallelStream().anyMatch(e2 -> checker.areEqual(e1, e2)));
	}

	protected Comparator() {}

	protected interface Eq<T> {
		boolean areEqual(T i1, T i2);
	}
}
