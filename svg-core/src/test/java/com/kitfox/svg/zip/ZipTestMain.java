/*
 * ZipTestMain.java
 *
 * Created on July 18, 2007, 12:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.svg.zip;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author kitfox
 */
public class ZipTestMain
{
    
    /** Creates a new instance of ZipTestMain */
    public ZipTestMain() throws IOException
    {
        URL url = ZipTestMain.class.getResource("/missing.svgz");
//        URL url = ZipTestMain.class.getResource("/AdamTagletClasses.svg");
        InputStream is = url.openStream();
        BufferedInputStream bin = new BufferedInputStream(is);
        
        bin.mark(2);
        int b0 = bin.read();
        int b1 = bin.read();
        bin.reset();
        
        InputStreamReader reader;
        
        //Check for gzip magic number
        if ((b1 << 8 | b0) == GZIPInputStream.GZIP_MAGIC)
        {
            GZIPInputStream iis = new GZIPInputStream(bin);
            reader = new InputStreamReader(iis);
        }
        else
        {
            //Plain text
            reader = new InputStreamReader(bin);
        }
        
        
        BufferedReader br = new BufferedReader(reader);
        for (String s = br.readLine(); s != null; s = br.readLine())
        {
            System.err.println(s);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            new ZipTestMain();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
}
