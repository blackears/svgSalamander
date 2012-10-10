/*
 * SVGIcon.java
 *
 * Created on April 21, 2005, 10:43 AM
 */

package com.kitfox.svg.app.beans;

import com.kitfox.svg.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author  kitfox
 */
public class SVGPanel extends JPanel
{
    public static final long serialVersionUID = 1;


    SVGUniverse svgUniverse = SVGCache.getSVGUniverse();
    
    private boolean antiAlias;
    
//    private String svgPath;
    URI svgURI;

    private boolean scaleToFit;
    AffineTransform scaleXform = new AffineTransform();
    
    /** Creates new form SVGIcon */
    public SVGPanel()
    {
        initComponents();
    }
        
    public int getSVGHeight()
    {
        if (scaleToFit)
        {
            return getPreferredSize().height;
        }
        
        SVGDiagram diagram = svgUniverse.getDiagram(svgURI);
        if (diagram == null)
        {
            return 0;
        }
        return (int)diagram.getHeight();
    }
    
    public int getSVGWidth()
    {
        if (scaleToFit)
        {
            return getPreferredSize().width;
        }
        
        SVGDiagram diagram = svgUniverse.getDiagram(svgURI);
        if (diagram == null)
        {
            return 0;
        }
        return (int)diagram.getWidth();
    }
    
    public void paintComponent(Graphics gg)
    {
        super.paintComponent(gg);

        Graphics2D g = (Graphics2D)gg.create();
        paintComponent(g);
        g.dispose();
    }
    
    private void paintComponent(Graphics2D g)
    {
        Object oldAliasHint = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);

        
        SVGDiagram diagram = svgUniverse.getDiagram(svgURI);
        if (diagram == null)
        {
            return;
        }
        
        if (!scaleToFit)
        {
            try
            {
                diagram.render(g);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAliasHint);
            }
            catch (SVGException e)
            {
                throw new RuntimeException(e);
            }
            return;
        }
        
        Dimension dim = getSize();
        final int width = dim.width;
        final int height = dim.height;
//        int width = getWidth();
//        int height = getHeight();
        
//        if (width == 0 || height == 0)
//        {
//           //Chances are we're rendering offscreen
//            Dimension dim = getSize();
//            width = dim.width;
//            height = dim.height;
//            return;
//        }
        
//        g.setClip(0, 0, dim.width, dim.height);

            
        final Rectangle2D.Double rect = new Rectangle2D.Double();
        diagram.getViewRect(rect);
        
        scaleXform.setToScale(width / rect.width, height / rect.height);
        
        AffineTransform oldXform = g.getTransform();
        g.transform(scaleXform);

        try
        {
            diagram.render(g);
        }
        catch (SVGException e)
        {
            throw new RuntimeException(e);
        }
        
        g.setTransform(oldXform);
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAliasHint);
    }
    
    public SVGUniverse getSvgUniverse()
    {
        return svgUniverse;
    }

    public void setSvgUniverse(SVGUniverse svgUniverse)
    {
        SVGUniverse old = this.svgUniverse;
        this.svgUniverse = svgUniverse;
        firePropertyChange("svgUniverse", old, svgUniverse);
    }

    public URI getSvgURI()
    {
        return svgURI;
    }

    public void setSvgURI(URI svgURI)
    {
        URI old = this.svgURI;
        this.svgURI = svgURI;
        firePropertyChange("svgURI", old, svgURI);
    }
    
    /**
     * Most resources your component will want to access will be resources on your classpath.  
     * This method will interpret the passed string as a path in the classpath and use
     * Class.getResource() to determine the URI of the SVG.
     */
    public void setSvgResourcePath(String resourcePath) throws SVGException
    {
        URI old = this.svgURI;
        
        try
        {
            svgURI = new URI(getClass().getResource(resourcePath).toString());
//System.err.println("SVGPanel: new URI " + svgURI + " from path " + resourcePath);
            
            firePropertyChange("svgURI", old, svgURI);
            
            repaint();
        }
        catch (Exception e)
        {
            throw new SVGException("Could not resolve path " + resourcePath, e);
//            svgURI = old;
        }
    }
    
    public boolean isScaleToFit()
    {
        return scaleToFit;
    }

    public void setScaleToFit(boolean scaleToFit)
    {
        boolean old = this.scaleToFit;
        this.scaleToFit = scaleToFit;
        firePropertyChange("scaleToFit", old, scaleToFit);
    }
    
    /**
     * @return true if antiAliasing is turned on.
     * @deprecated
     */
    public boolean getUseAntiAlias()
    {
        return getAntiAlias();
    }

    /**
     * @param antiAlias true to use antiAliasing.
     * @deprecated
     */
    public void setUseAntiAlias(boolean antiAlias)
    {
        setAntiAlias(antiAlias);
    }
    
    /**
     * @return true if antiAliasing is turned on.
     */
    public boolean getAntiAlias()
    {
        return antiAlias;
    }

    /**
     * @param antiAlias true to use antiAliasing.
     */
    public void setAntiAlias(boolean antiAlias)
    {
        boolean old = this.antiAlias;
        this.antiAlias = antiAlias;
        firePropertyChange("antiAlias", old, antiAlias);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.BorderLayout());

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
