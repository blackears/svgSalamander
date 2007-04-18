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
import com.kitfox.svg.app.beans.SVGIcon;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import javax.imageio.ImageIO;

/**
 *
 * @author kitfox
 */
public class ScaleImageTest
{
    SVGUniverse universe;
    
    /** Creates a new instance of NewMain */
    public ScaleImageTest()
    {
        this.universe = new SVGUniverse();
        StringReader reader = new StringReader(makeDynamicSVG());
        URI uri = universe.loadSVG(reader, "myImage");
        
        SVGIcon icon = new SVGIcon();
        icon.setSvgUniverse(universe);
        icon.setSvgURI(uri);
        icon.setAntiAlias(true);
        icon.setScaleToFit(true);
        
        int width = 400;
        int height = 400;
        icon.setPreferredSize(new Dimension(width, height));
        icon.setClipToViewbox(false);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        
        File outFile = new File("testScaleImage.png");
        
        try
        {
            ImageIO.write(image, "png", outFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private String makeDynamicSVG()
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        pw.println("<svg width=\"200\" height=\"200\" style=\"fill:none;stroke-width:4\">");
        pw.println("    <g>");
        pw.println("        <circle cx=\"100\" cy=\"100\" r=\"100\" fill=\"red\" stroke=\"black\"/>");
        pw.println("        <circle cx=\"200\" cy=\"200\" r=\"50\" fill=\"blue\" stroke=\"black\"/>");
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
        ScaleImageTest app = new ScaleImageTest();
    }
    
}
