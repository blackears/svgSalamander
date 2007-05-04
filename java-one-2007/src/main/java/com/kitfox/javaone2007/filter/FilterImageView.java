/*
 * FilterBuffer.java
 *
 * Created on May 4, 2007, 3:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.geom.Rectangle2D;

/**
 * Represents a buffer that can hold intermediate image values calculated
 * in filter operations.
 *
 * @author kitfox
 */
public class FilterImageView
{
    //Viewport area
    Rectangle2D.Float viewArea;
    
    //Map viewport coordinates to raster space
    FilterTransform viewToRaster;
    
    //Image data
    FilterImageRaster backingRaster;
    
    /** Creates a new instance of FilterBuffer */
    public FilterImageView()
    {
    }

    public FilterImageView(Rectangle2D.Float viewArea, FilterTransform viewToRaster, FilterImageRaster backingRaster)
    {
        this.viewArea = viewArea;
        this.viewToRaster = viewToRaster;
        this.backingRaster = backingRaster;
    }

    boolean rasterConstainsView()
    {
        Rectangle2D.Float rasArea = (Rectangle2D.Float)viewArea.clone();
        viewToRaster.transform(rasArea);
        
        return backingRaster.getRasterBounds().contains(rasArea);
    }

    boolean contains(Rectangle2D.Float viewArea)
    {
        return this.viewArea.contains(viewArea);
    }
    
}
