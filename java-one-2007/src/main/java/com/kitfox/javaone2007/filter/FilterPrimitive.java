/*
 * FilterPrimitive.java
 *
 * Created on May 3, 2007, 2:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.media.opengl.GL;

/**
 * The concept of registers is borrowed from assembler code design to represent
 * intermediate states in image processing.  Each register can contain a 
 * PrimBuffer containing intermediate image data.  An input register provides
 * raster data for the calculation and an output register coresponds to the
 * filter destination.
 * 
 * Register 0 coresponds to the final output image.
 *
 * @author kitfox
 */
abstract public class FilterPrimitive
{
    private Rectangle2D.Float preferredOutputViewArea;
    
    /** Creates a new instance of FilterPrimitive */
    public FilterPrimitive()
    {
    }
    
    abstract public int[] getRegInputs();
    
    abstract public int getRegOut();
    
    public Rectangle2D.Float getOutputViewArea(Rectangle2D.Float clip, FilterImageView... inputs)
    {
        if (preferredOutputViewArea != null)
        {
            Rectangle2D.Float area = new Rectangle2D.Float();
            Rectangle2D.Float.intersect(preferredOutputViewArea, clip, area);
            return area;
        }
        
        Rectangle2D.Float area = new Rectangle2D.Float();
        for (FilterImageView view: inputs)
        {
            if (area == null)
            {
                area = (Rectangle2D.Float)view.viewArea.clone();
            }
            else
            {
                Rectangle2D.Float.union(view.viewArea, area, area);
            }
        }
        if (area == null) return null;
        Rectangle2D.Float.intersect(area, clip, area);
        return area;
    }

    public Rectangle2D.Float getPreferredOutputViewArea()
    {
        return preferredOutputViewArea;
    }

    public void setPreferredOutputViewArea(Rectangle2D.Float preferredOutputViewArea)
    {
        this.preferredOutputViewArea = preferredOutputViewArea;
    }

    abstract void run(GL gl, HashMap<Integer, FilterImageView> allocBufs);
}
