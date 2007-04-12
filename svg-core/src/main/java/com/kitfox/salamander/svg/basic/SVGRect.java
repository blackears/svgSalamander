/*
 * SVGString.java
 *
 * Created on April 12, 2007, 2:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * Rectangles are defined as consisting of a (x,y) coordinate pair identifying a minimum X value, a minimum Y value, and a width and height, which are usually constrained to be non-negative.
 * @author kitfox
 */
public interface SVGRect extends SVGDataType
{
    /**
     * Corresponds to attribute x on the given element.
     */
    public float getX();
    /**
     * Corresponds to attribute y on the given element.
     */
    public float getY();
    /**
     * Corresponds to attribute width on the given element.
     */
    public float getWidth();
    /**
     * Corresponds to attribute height on the given element.
     */
    public float getHeight();
}
