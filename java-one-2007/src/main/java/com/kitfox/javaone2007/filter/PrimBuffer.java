/*
 * PrimBuffer.java
 *
 * Created on May 3, 2007, 11:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Defines an intermediate raster in a chain of filter primitive operations.
 *
 * @author kitfox
 */
public class PrimBuffer
{
    Rectangle2D.Float bounds = new Rectangle2D.Float();
    Point2D.Float offset = new Point2D.Float();
    Dimension rasterBounds;
    
    int glTexName;
    
//    boolean solidBlack;  //True if RGB chanels should be treated as if solid black
//    boolean solidAlpha;  //True if alpha channel 
    
    /** Creates a new instance of PrimBuffer */
    public PrimBuffer()
    {
    }
    
}
