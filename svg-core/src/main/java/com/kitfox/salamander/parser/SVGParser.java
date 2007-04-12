/*
 * SVGParser.java
 *
 * Created on April 11, 2007, 5:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.parser;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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

    /**
     * Parse an SVG document.  The document may be either uncompressed XML (.svg) 
     * or a zipped document (.svgz).  This routine will automatically detect
     * zipped documents and unzip them.
     */
    public static void parse(File source, SVGParserListener listener)
    {
        try
        {
            XMLReader parser;
            parser = XMLReaderFactory.createXMLReader();
            SVGParserHandler handler = new SVGParserHandler(listener);
            parser.setContentHandler(handler);

            FileInputStream fis = new FileInputStream(source);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.mark(4);
            //Check for gzip magic number
            DataInputStream din = new DataInputStream(bis);
            long magicNumber = din.readLong();
            bis.reset();
            
            InputStream svgStream;
            if ((int)magicNumber == 0x4b50)
            {
                //PK Zip file
                ZipInputStream zin = new ZipInputStream(bis);
                ZipEntry entry = zin.getNextEntry();
                byte[] buf = new byte[(int)entry.getSize()];
                for (int offset = 0; offset < buf.length; offset += zin.read(buf, offset, buf.length - offset));
                zin.closeEntry();
                zin.close();
                
                svgStream = new ByteArrayInputStream(buf);
            }
            else
            {
                //Treat input as uncompressed XML
                svgStream = bis;
            }
            
            InputSource is = new InputSource(svgStream);
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
