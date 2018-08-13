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
package org.holodeckb2b.bdxr.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.holodeckb2b.bdxr.utils.Utils;

/**
 * Represents the SMP meta-data of processes in which a specific <i>service</i>/<i>document</i> is offered/used and
 * which use the same <i>messaging endpoints</i>. The association with the service is implemented by containment of this
 * class in {@link ServiceInformation} mirroring the structure of the SMP XML structure.
 * <p>NOTE: In the OASIS v1 and PEPPOL SMP specifications the number of processes is limited to one, if several
 * processes use the same endpoint multiple <code>ProcessInfo</code> instances will occur.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class ProcessInfo {

    private List<Identifier>    processIds;
    private List<EndpointInfo>  endpoints;
    private List<IExtension>    extensions;

    /**
     * Default constructor creates "empty" instance
     */
    public ProcessInfo() {}

    /**
     * Creates a new instance with the given meta-data
     *
     * @param procIds       The list identifiers process identifiers
     * @param endpoints     The list of available endpoints for this process
     * @param extendedInfo  Extended information available about this process
     */
    public ProcessInfo(final List<Identifier> procIds, final List<EndpointInfo> endpoints,
                       final List<IExtension> extendedInfo)
    {
        this.processIds = procIds;
        this.endpoints  = endpoints;
        this.extensions = extendedInfo;
    }

    /**
     * Gets the list process identifiers
     *
     * @return The process ids
     */
    public List<Identifier> getProcessIds() {
        return processIds;
    }

    /**
     * Checks if this given process is included in the set of processes that is represented by this meta-data.
     * <p>NOTE: When no processes are listed in the meta-data it is assumed that the meta-data apply to all processes.
     *
     * @param processId     The identifier of the process to check
     * @return              <code>true</code> if the process is included in the list of supported process <b>or</b>
     *                      when the process list is empty,<br>
     *                      <code>false</code> otherwise
     *
     */
    public boolean supportsProcess(final Identifier processId) {
        return Utils.isNullOrEmpty(processIds) ? true : processIds.contains(processId);
    }

    /**
     * Sets the process identifiers
     *
     * @param processIds The process Ids to set
     */
    public void setProcessId(List<Identifier> processIds) {
        this.processIds = processIds;
    }

    /**
     * Add a process identifier to the list of processes that support the service and use the same endpoints.
     *
     * @param processId The process id
     */
    public void addProcessId(final Identifier processId) {
        if (processId == null)
            throw new IllegalArgumentException("A process identifier object must be provided");
        if (this.processIds == null)
            this.processIds = new ArrayList<>();
        this.processIds.add(processId);
    }

    /**
     * Gets the list of available endpoints for this process
     *
     * @return The list of endpoints
     */
    public List<EndpointInfo> getEndpoints() {
        return endpoints;
    }

    /**
     * Set the list of endpoints available for this process
     *
     * @param endpoints     The list of endpoints available for this process
     */
    public void setEndpoints(List<EndpointInfo> endpoints) {
        this.endpoints = endpoints;
    }

    /**
     * Add an endpoint to the list of available endpoints
     *
     * @param endpoint      The endpoint meta-data to add
     */
    public void addEndpoint(final EndpointInfo endpoint) {
        if (endpoint == null)
            throw new IllegalArgumentException("A EndpointInfo object must be provided");
        if (this.endpoints == null)
            this.endpoints = new ArrayList<>();
        this.endpoints.add(endpoint);
    }


    /**
     * Gets the additional, non standard, information related to this endpoint
     *
     * @return The extended meta-data
     */
    public List<IExtension> getExtensions() {
        return extensions;
    }

    /**
     * Sets the additional, non standard, information related to this endpoint
     *
     * @param extension The extended meta-data
     */
    public void setExtensions(List<IExtension> extension) {
        this.extensions = extension;
    }

    /**
     * Add one specific extension to the meta-data
     *
     * @param   ext     The extension to add to set of extended meta-data
     */
    public void addExtension(final IExtension ext) {
        if (ext == null)
            throw new IllegalArgumentException("A extention must be specified");
        if (this.extensions == null)
            this.extensions = new ArrayList<>();
        this.extensions.add(ext);
    }
}
