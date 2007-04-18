/*
 * ScanNodeIdsMain.java
 *
 * Created on November 6, 2006, 10:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.svg.example;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGRoot;
import com.kitfox.svg.SVGUniverse;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.Vector;

/**
 *
 * @author kitfox
 */
public class ScanNodeIdsTest
{
    SVGUniverse universe;
    
    
    /** Creates a new instance of ScanNodeIdsMain */
    public ScanNodeIdsTest()
    {
        this.universe = new SVGUniverse();
        StringReader reader = new StringReader(makeDynamicSVG());
        URI uri = universe.loadSVG(reader, "myImage");
        SVGDiagram diagram = universe.getDiagram(uri);

        SVGRoot root = diagram.getRoot();
        printIds(root, "");
    }

    private void printIds(SVGElement ele, String indent)
    {
        System.out.println(indent + "Id: " + ele.getId() + " (" + ele.getClass().getName() + ")");
        
        String nextIndent = indent + "  ";
        Vector children = ele.getChildren(new Vector());
        for (int i = 0; i < children.size(); i++)
        {
            printIds((SVGElement)children.get(i), nextIndent);
        }
    }
    
    private String makeDynamicSVG()
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        pw.println("<svg id=\"root_node\" width=\"400\" height=\"400\" style=\"fill:none;stroke-width:4\">");
        pw.println("    <g>");
        pw.println("    <rect id=\"select_area\" x=\"10\" y=\"20\" width=\"30\" height=\"40\"/>");
        pw.println("    <clipPath id=\"my_clip\">");
        pw.println("        <path id=\"my_path\" d=\"M 0 0 L 100 0 L 100 100 L 0 100 z\" fill=\"red\" stroke=\"black\"/>");
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
        ScanNodeIdsTest app = new ScanNodeIdsTest();
    }
    
}
