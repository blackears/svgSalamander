/*
 * SVGPoint.java
 *
 * Created on April 12, 2007, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.coordSystems;

import com.kitfox.salamander.svg.basic.SVGDataType;

/**
 * 
 * Many of the SVG DOM interfaces refer to objects of class SVGPoint. An SVGPoint is an (x,y) coordinate pair. When used in matrix operations, an SVGPoint is treated as a vector of the form:
 * 
 * <code>
 * [x]
 * [y]
 * [1]
 * </code>
 * @author kitfox
 */
public interface SVGPoint extends SVGDataType
{
    /**
     * The x coordinate.
     */
    public float getX();
    /**
     * The y coordinate.
     */
    public float getY();
    
    /**
     * Applies a 2x3 matrix transformation on this SVGPoint object and returns a new, transformed SVGPoint object:
     * 
     * <CODE>newpoint = matrix * thispoint</CODE>
     * @param matrix The matrix which is to be applied to this SVGPoint object.
     * @return A new SVGPoint object.
     */
    public SVGPoint matrixTransform(SVGMatrix matrix);
}
