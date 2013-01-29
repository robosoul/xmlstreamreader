/*
 * Copyright 2013 Luka Obradovic.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hoshi.xml.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLReader {
    private File file;
    
    public XMLReader(File file) {
        this.file = file;
    }
    
    public <T extends JAXBNode> void parse(String nodeName, Class<T> type)
            throws FileNotFoundException,
                   XMLStreamException,
                   JAXBException {

        FileReader fr = new FileReader(file);
        
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader xsr = xif.createXMLStreamReader(fr);

        try {
            // Move from the begining of the file
            xsr.nextTag();
            
            JAXBContext jaxbContext   = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            JAXBNode node = null;
            while (xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
                xsr.require(XMLStreamConstants.START_ELEMENT, null, nodeName);
                
                node = (JAXBNode) unmarshaller.unmarshal(xsr);
                if (node != null) {
                    node.process();
                }
            }
        } finally {
            xsr.close();
            
            try { fr.close(); } catch (Exception ignored) { /* OK */ }
        }
    }
}
