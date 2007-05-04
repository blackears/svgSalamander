/*
 * Resolution.java
 *
 * Created on May 4, 2007, 5:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Maps a viewArea to raster space
 *
 * @author kitfox
 */
public class FilterTransform
{
    //Bounds to raster xform
    float scaleX = 1;
    float scaleY = 1;
    float transX;
    float transY;
    
    static final float EPSILON = .00001f;
    
    /** Creates a new instance of Resolution */
    public FilterTransform()
    {
    }
    
//    public FilterTransform(Rectangle2D.Float viewArea, Rectangle rasterArea)
//    {
//        scaleX = rasterArea.width / viewArea.width;
//        scaleY = rasterArea.height / viewArea.height;
//        transX = -viewArea.x;
//        transY = -viewArea.y;
//    }
    
    public boolean equals(Object obj)
    {
        FilterTransform res = (FilterTransform)obj;
        return res.scaleX == scaleX && res.scaleY == scaleY && res.transX == transX && res.transY == transY;
    }

    void transform(Rectangle2D.Float rasArea)
    {
        float x1 = rasArea.x;
        float y1 = rasArea.y;
        float x2 = rasArea.x + rasArea.width;
        float y2 = rasArea.y + rasArea.height;
        
        x1 = x1 * scaleX + transX;
        y1 = y1 * scaleY + transY;
        x2 = x2 * scaleX + transX;
        y2 = y2 * scaleY + transY;
        
        rasArea.x = x1;
        rasArea.y = y1;
        rasArea.width = x2 - x1;
        rasArea.height = y2 - y1;
    }

    void calcMinRaster(Rectangle2D.Float viewArea, Rectangle rasterArea)
    {
        float x1 = (viewArea.x + transX) * scaleX;
        float y1 = (viewArea.y + transY) * scaleY;
        float x2 = x1 + viewArea.width * scaleX;
        float y2 = y1 + viewArea.height * scaleY;

        rasterArea.x = (int)x1;
        rasterArea.y = (int)y1;
        rasterArea.width = (int)x2 - (int)x1 + (isInteger(x2) ? 0 : 1);
        rasterArea.height = (int)y2 - (int)y1 + (isInteger(y2) ? 0 : 1);
        
    }
    
    private boolean isInteger(float value)
    {
        return Math.abs(value - (int)value) < EPSILON;
    }
}
