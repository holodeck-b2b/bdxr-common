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

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the meta-data about a <i>service</i> that a participant in the network supports. Although the SMP
 * specifications currently in use (OASIS v1 and PEPPOL) are more <i>document</i> based we use the term <i>service</i>
 * as the new OASIS SMP specification will also be services based and it also is a more generic term. Handling a
 * certain document can also be seen as a service to process it.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ServiceInformation extends ServiceMetadataResult {

    private Identifier  			participantId;
    private Identifier  		   	serviceId;
    private List<ProcessInfo>   	processInformation;

    /**
     * Default constructor creates "empty" instance
     */
    public ServiceInformation() {}

    /**
     * Creates a new object representing the SMP meta-data for a specific participant and service type.
     *
     * @param participant   The participant's identifiers
     * @param service       The service identifier
     * @param processes     The information on the processes where this document is used by the participant
     * @param signingCert   The X509 certificate that was used to sign the meta-data
     * @param ext           Any extended meta-data information included in the SMP record
     */
    public ServiceInformation(final Identifier participant, final Identifier service, List<ProcessInfo> processes,
                           	  final X509Certificate signingCert, final List<IExtension> ext) {
        super(signingCert, ext);
        this.participantId = participant;
        this.serviceId = service;
        this.processInformation = processes;
    }

    /**
     * Gets the identifier of the participant the service meta-data applies to
     *
     * @return The participant id
     */
    public Identifier getParticipantId() {
        return participantId;
    }

    /**
     * Sets the identifier of the participant to which the service meta-data apply
     *
     * @param participantId The participant id
     */
    public void setParticipantId(Identifier participantId) {
        this.participantId = participantId;
    }

    /**
     * Gets the service identifier to which these service meta-data apply
     *
     * @return The service id
     */
    public Identifier getServiceId() {
        return serviceId;
    }

    /**
     * Sets the service identifier to which these service meta-data apply
     *
     * @param serviceId The service id
     */
    public void setServiceId(final Identifier serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets the list of processes in which this service is used by the participant.
     *
     * @return A list of meta-data on the processes in which this service is used by the participant
     */
    public List<ProcessInfo> getProcessInformation() {
        return processInformation;
    }

    /**
     * Sets the meta-data of the processes in which the participant offers this service
     *
     * @param processInformation The list of process meta-data
     */
    public void setProcessInformation(List<ProcessInfo> processInformation) {
        this.processInformation = processInformation;
    }

    /**
     * Adds the meta-data of one process in which this service if offered to the list of process meta-data
     *
     * @param processInfo   The process meta-data
     */
    public void addProcessInformation(final ProcessInfo processInfo) {
        if (processInfo == null)
            throw new IllegalArgumentException("Process meta-data must be provided");
        if (this.processInformation == null)
            this.processInformation = new ArrayList<>();
        this.processInformation.add(processInfo);
    }
}
