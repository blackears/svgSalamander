/*
 * FilterImageRaster.java
 *
 * Created on May 4, 2007, 5:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.Rectangle;

/**
 * An array of pixels that hold image data for filtering operations.
 *
 * @author kitfox
 */
abstract public class FilterImageRaster
{
    /** Creates a new instance of FilterImageRaster */
    public FilterImageRaster()
    {
    }
    
    abstract public Rectangle getRasterBounds();
    
}
