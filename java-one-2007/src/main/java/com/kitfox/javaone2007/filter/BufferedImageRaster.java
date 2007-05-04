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
import java.awt.image.WritableRaster;

/**
 *
 * @author kitfox
 */
public class BufferedImageRaster extends FilterImageRaster
{
    final WritableRaster backing;
    
//    Rectangle bounds;
    
    /** Creates a new instance of IntermediateRaster */
    public BufferedImageRaster(WritableRaster backing)
    {
        this.backing = backing;
//        this.bounds = bounds;
    }

    public Rectangle getRasterBounds()
    {
        return new Rectangle(0, 0, backing.getWidth(), backing.getHeight());
    }
    
}
