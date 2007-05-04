/*
 * IntermediateRaster.java
 *
 * Created on May 4, 2007, 8:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.Rectangle;

/**
 *
 * @author kitfox
 */
public class IntermediateRaster extends FilterImageRaster
{
    Rectangle bounds;
    
    /** Creates a new instance of IntermediateRaster */
    public IntermediateRaster(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public Rectangle getRasterBounds()
    {
        return bounds;
    }
    
}
