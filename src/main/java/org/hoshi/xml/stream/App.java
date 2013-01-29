package org.hoshi.xml.stream;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.hoshi.xml.vinquery.VQVINNode;

public class App {
    public static void main( String[] args ) {
        XMLReader reader = new XMLReader(new File(args[0]));
        
        try {
            reader.parse("VIN", VQVINNode.class);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XMLStreamException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
