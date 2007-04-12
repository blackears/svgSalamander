/*
 * SVGICCColor.java
 *
 * Created on April 12, 2007, 2:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * The SVGICCColor  interface expresses an ICC-based color specification.
 * @author kitfox
 */
public interface SVGICCColor
{
    /**
     * The name of the color profile, which is the first parameter of an ICC color specification.
     */
    public String getColorProfile();
    /**
     * The list of color values that define this ICC color. Each color value is an arbitrary floating point number.
     */
    public SVGNumberList getColors();
}
