/*
 * TestNPE.java
 *
 * Created on January 10, 2007, 9:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.svg;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

public class TestIconPreferredSizeNull {

    public static void main(String[] args) {
	
        StringReader reader = new StringReader(makeDynamicSVG());
        SVGUniverse uni = SVGCache.getSVGUniverse();
        URI svguri = uni.loadSVG(reader, "mysvg");
		
        SVGIcon icon = new SVGIcon();
        icon.setSvgURI(svguri); // here NPE is generated
        
        icon = null;
    }
	
    private static String makeDynamicSVG() {
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
}
