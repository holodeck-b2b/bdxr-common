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
package org.holodeckb2b.bdxr.impl.oasis_smp1;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.holodeckb2b.bdxr.smp.api.IHostNameGenerator;
import org.holodeckb2b.bdxr.smp.api.ISMPLocator;
import org.holodeckb2b.bdxr.smp.api.SMPLocatorException;
import org.holodeckb2b.bdxr.smp.datamodel.Identifier;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 * Implements the {@link ISMPLocator} interface for locating the SMP for the participant using the OASIS BDXL
 * specification, i.e. using NAPTR DNS records from the DNS entry for the generated host name based on the participant's
 * identifier.
 * <p>When creating an instance of this locator the {@link IHostNameGenerator} to use for generating host names must be
 * provided.
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class BDXLLocator implements ISMPLocator {
    private static final Logger log = LogManager.getLogger(BDXLLocator.class);

    /**
     * The host name generator to create the domain name to use for executing the SML query
     */
    private final IHostNameGenerator    hostnameGenerator;

    /**
     * Create a new <code>BDXLLocator</code> instance that will use the given generator to create the host names for
     * participants.
     *
     * @param hostnameGenerator     The host name to use for generation of host names
     */
    public BDXLLocator(IHostNameGenerator hostnameGenerator) {
        this.hostnameGenerator = hostnameGenerator;
    }

    /**
     * Executes the BDXL query to locate the SMP for the given participant identifier
     *
     * {@inheritDoc}
     */
    @Override
    public URI locateSMP(Identifier participant) throws SMPLocatorException {
        try {
            log.debug("Generate host name for participant identifier {}::{}", participant.getScheme(),
                      participant.getValue());
            final String hostname = hostnameGenerator.getHostNameForParticipant(participant);
            // Fetch all records of type NAPTR registered on hostname.
            Record[] records = new Lookup(hostname, Type.NAPTR).run();
            if (records == null) {
                log.warn("Participant with identifier {}::{} not registered.", participant.getScheme(),
                         participant.getValue());
                throw new SMPLocatorException("Participant not registered");
            }
            // Loop records found.
            for (Record record : records) {
                // Simple cast possible because we only retrieved NAPTR records
                NAPTRRecord naptrRecord = (NAPTRRecord) record;
                // Handle only those having "Meta:SMP" as service.
                if ("Meta:SMP".equals(naptrRecord.getService()) && "U".equalsIgnoreCase(naptrRecord.getFlags())) {
                    log.debug("Found \"Meta:SMP\" NAPTR record for participant, constructing URL");
                    // Get the regexp from NAPTR record and create URI and return.
                    final String regexp = naptrRecord.getRegexp();
                    final String result = handleRegex(regexp, hostname);
                    if (result != null) {
                        log.debug("Found SMP location [{}] for participant {}::{}", result, participant.getScheme(),
                           participant.getValue());
                        return URI.create(result);
                    } else {
                        log.error("Could not process regexp [{}] from NAPTR record for participant {}::{}.", regexp,
                                  participant.getScheme(), participant.getValue());
                        throw new SMPLocatorException("Participant not correctly registered in BDXL");
                    }
                }
            }
            // No "Meta:SMP" NAPTR record found
            log.warn("No \"Meta:SMP\" NAPTR record for participant with identifier {}::{}", participant.getScheme(),
                     participant.getValue());
            throw new SMPLocatorException("Participant not registered in BDXL");
        } catch (TextParseException dnsQueryError) {
            log.error("Error in DNS query execution: {}", dnsQueryError.getMessage());
            throw new SMPLocatorException("Error in execution of DNS query", dnsQueryError);
        } catch (IllegalArgumentException unsupportedId) {
            log.warn("Unsupported participant identifier: {}::{}", participant.getScheme(), participant.getValue());
            throw new SMPLocatorException("Unsupported participant identifier", unsupportedId);
        }
    }

    /**
     * Applies the regular expression from the NAPTR record to the generated host name for the participant.
     *
     * @param naptrRegex    The regexp from the NAPTR record
     * @param hostname      The generated hostname for the participant
     * @return              Result of applying the regexp to the hostname.<br><b>NOTE:</b> May be null if regexp could
     *                      not be matched to hostname
     */
    private String handleRegex(final String naptrRegex, final String hostname) {
        final String[] regexp = naptrRegex.split("!");

        // Short cut when hostname is completely replaced
        if ("^.*$".equals(regexp[1]))
            return regexp[2];

        // Apply the regexp to host name
        Pattern pattern = Pattern.compile(regexp[1]);
        Matcher matcher = pattern.matcher(hostname);
        if (matcher.matches())
            return matcher.replaceAll(regexp[2].replaceAll("\\\\{2}", "\\$"));

        // No match
        return null;
    }
}
