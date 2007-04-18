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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import javax.imageio.ImageIO;
import org.apache.tools.ant.BuildException;

/**
 *
 * @author kitfox
 */
public class SaveAsImageTest
{
    SVGUniverse universe;
    
    /** Creates a new instance of NewMain */
    public SaveAsImageTest()
    {
        this.universe = new SVGUniverse();
        StringReader reader = new StringReader(makeDynamicSVG());
        URI uri = universe.loadSVG(reader, "myImage");
        
        SVGIcon icon = new SVGIcon();
        icon.setSvgUniverse(universe);
        icon.setSvgURI(uri);
        icon.setAntiAlias(true);
        
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        icon.setClipToViewbox(false);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        
        File outFile = new File("testSaveImage.png");
        
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
        
        pw.println("<svg width=\"400\" height=\"400\" style=\"fill:none;stroke-width:4\">");
        pw.println("    <g>");
        pw.println("        <path d=\"M 0 0 L 100 0 L 100 100 L 0 100 z\" fill=\"red\" stroke=\"black\"/>");
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
        SaveAsImageTest app = new SaveAsImageTest();
    }
    
}
