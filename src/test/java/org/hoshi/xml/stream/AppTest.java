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

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.hoshi.xml.bookstore.Book;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Unit test for simple App.
 */
public class AppTest {
    private static XMLReader reader;
    
    @BeforeClass
    public static void setUpClass() {
        reader = new XMLReader(new File("src/test/resources/bookstore.xml"));
    }
    
    @Test
    public static void testLoadBooks() 
            throws FileNotFoundException, XMLStreamException, JAXBException {
        reader.parse("Book", Book.class);
        Assert.assertEquals(Book.books.size(), 5);
    }
    
    @Test(expectedExceptions = XMLStreamException.class)
    public static void testLoadBooksException() 
            throws FileNotFoundException, XMLStreamException, JAXBException {
        reader.parse("Cook", Book.class);
    }
    
    @Test(dependsOnMethods="testLoadBooks")
    public static void testSearchBook() {
        int testid = 1121;
        
        Book found = null;
        for (Book book : Book.books) {
            if (book.getId() == testid) {
                found = book;
            }
        }
        
        Assert.assertNotNull(found);
    }
    
    @Test(dependsOnMethods="testLoadBooks")
    public static void testBookTitle() {
        int testid = 11;
        
        Book found = null;
        for (Book book : Book.books) {
            if (book.getId() == testid) {
                found = book;
            }
        }
        
        Assert.assertEquals(found.getTitle(), "Neuromancer");
    }
}
