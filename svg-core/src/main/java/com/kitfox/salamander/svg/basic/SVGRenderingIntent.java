/*
 * SVGRenderingIntent.java
 *
 * Created on April 12, 2007, 3:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 *
 * The SVGRenderingIntent  interface defines the enumerated list of possible values for 'rendering-intent' attributes or descriptors.
 * @author kitfox
 */
public interface SVGRenderingIntent
{
    public static enum Intent
    {
        /**
         *  	The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN,
        /**
         * Corresponds to a value of auto.
         */
        AUTO,
        /**
         * Corresponds to a value of perceptual.
         */
        PERCEPTUAL,
        /**
         * Corresponds to a value of relative-colorimetric.
         */
        RELATIVE_COLORIMETRIC,
        /**
         * Corresponds to a value of saturation.
         */
        SATURATION,
        /**
         * Corresponds to a value of absolute-colorimetric.
         */
        ABSOLUTE_COLORIMETRIC
    }
}
