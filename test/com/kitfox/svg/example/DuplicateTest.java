/*
 * NewMain.java
 *
 * Created on July 30, 2006, 2:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.svg.example;

import com.kitfox.svg.SVGUniverse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

/**
 *
 * @author kitfox
 */
public class DuplicateTest
{
    SVGUniverse universe;
    
    /** Creates a new instance of NewMain */
    public DuplicateTest()
    {
        this.universe = new SVGUniverse();
        StringReader reader = new StringReader(makeDynamicSVG());
        
        URI uri = universe.loadSVG(reader, "myImage");
        try
        {
            universe.duplicate();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private String makeDynamicSVG()
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        pw.println("<svg width=\"400\" height=\"400\" style=\"fill:none;stroke-width:4\">");
        pw.println("    <g>");
        pw.println("    <clipPath id=\"MyClip\">");
        pw.println("        <path d=\"M 0 0 L 100 0 L 100 100 L 0 100 z\" fill=\"red\" stroke=\"black\"/>");
        pw.println("    </clipPath>");
        pw.println("    </g>");
        pw.println("</svg>");
        
        pw.close();
        return sw.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DuplicateTest app = new DuplicateTest();
    }
    
}
