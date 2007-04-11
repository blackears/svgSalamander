/*
 * SVGParser.java
 *
 * Created on April 11, 2007, 5:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author kitfox
 */
public class SVGParser
{
    public static interface SVGParserListener
    {
        
    }
    
    /** Creates a new instance of SVGParser */
    public SVGParser()
    {
    }

    public static void parse(File source, SVGParserListener listener, boolean skipUpToDateFiles)
    {
        try
        {
            XMLReader parser;
            parser = XMLReaderFactory.createXMLReader();
            SVGParserHandler handler = new SVGParserHandler(listener);
            parser.setContentHandler(handler);

            InputSource is = new InputSource(new FileInputStream(source));
            parser.parse(is);

        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
