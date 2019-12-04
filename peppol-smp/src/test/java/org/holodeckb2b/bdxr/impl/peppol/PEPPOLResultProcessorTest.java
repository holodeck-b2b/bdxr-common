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
package org.holodeckb2b.bdxr.impl.peppol;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.holodeckb2b.bdxr.impl.peppol.PEPPOLResultProcessor;
import org.holodeckb2b.bdxr.smp.api.SMPQueryException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Sander Fieten (sander at holodeck-b2b.org)
 */
public class PEPPOLResultProcessorTest {

    @Test
    public void testSignedServiceMetadata() {
        File input = new File(this.getClass().getClassLoader().getResource("peppol_signedsvcmd.xml").getPath());
        Document xmlResult = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlResult = db.parse(input);
        } catch (ParserConfigurationException | SAXException | IOException parsingError) {
            fail("Could not parse the XML file");
        }

        try {
            new PEPPOLResultProcessor().processResult(xmlResult);
        } catch (SMPQueryException ex) {
            fail();
        }
    }
}
