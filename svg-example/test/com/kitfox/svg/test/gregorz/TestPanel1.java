package com.kitfox.svg.test.gregorz;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import java.awt.Transparency;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class TestPanel1 extends javax.swing.JComponent
{

    private SVGUniverse universe;
    private SVGDiagram diagram;
    private BufferedImage buffer;
//    private Graphics2D g2d;

    public TestPanel1()
    {
//        buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
//        g2d = buffer.createGraphics();
        universe = new SVGUniverse();
        try
        {
            //I think You should change the location (I have the file on my IIS - I've included it to the project)
            URL url = TestPanel1.class.getResource("/com/kitfox/svg/example/x.svg");
            diagram = universe.getDiagram(url.toURI());
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        //try this
        //optionWorking(g);
        optionNotWorking(g);
    }

    //When I render on the applets' graphics it's working OK
    private void optionWorking(Graphics g)
    {
        try
        {
            diagram.render((Graphics2D) g);
        } catch (SVGException e)
        {
            e.printStackTrace();
        }
    }
    //When I try it through a BufferedImage It doesn't work - It's like the
    //diagram.render(g2d) didn't make it's job
    //The BufferedImage is OK because a fillRect is working when You uncomment it

    private void optionNotWorking(Graphics g)
    {
        if (buffer == null)
        {
            buffer = getGraphicsConfiguration()
                    .createCompatibleImage(500, 500, Transparency.TRANSLUCENT);

            Graphics2D g2d = buffer.createGraphics();
            try
            {
//                g2d.setClip(0, 0, 500, 500);
                diagram.render(g2d);
            } catch (SVGException e)
            {
                e.printStackTrace();
            }
            g2d.dispose();

            try
            {
                ImageIO.write(buffer, "png", new File("testImageX.png"));
            } catch (IOException ex)
            {
                Logger.getLogger(TestPanel1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //If You uncomment this it will work - It will draw an white rectangle
        //g2d.fillRect(100, 100, 100, 100);
        g.drawImage(buffer, 0, 0, 500, 500, null);
    }
}
