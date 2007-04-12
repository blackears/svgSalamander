/*
 * SVGZoomAndPan.java
 *
 * Created on April 12, 2007, 3:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * The SVGZoomAndPan  interface defines attribute "zoomAndPan" and associated constants.
 * @author kitfox
 */
public interface SVGZoomAndPan
{
    public static enum Type {
        /**
         * The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value disable.
         */
        DISABLE, 
        /**
         * Corresponds to value magnify.
         */
        MAGNIFY};
    
    /**
     * Corresponds to attribute zoomAndPan on the given element. The value must be one of the zoom and pan constants specified above.
     */
    public Type getZoomAndPan();
}
