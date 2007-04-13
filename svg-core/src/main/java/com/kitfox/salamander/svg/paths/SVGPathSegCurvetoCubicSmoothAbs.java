/*
 * SVGPathSegClosePath.java
 *
 * Created on April 12, 2007, 8:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.paths;

/**
 * The SVGPathSegCurvetoCubicSmoothAbs  interface corresponds to an "absolute smooth cubic curveto" (S) path data command.
 * @author kitfox
 */
public interface SVGPathSegCurvetoCubicSmoothAbs extends SVGPathSeg
{
    /**
     * The absolute X coordinate for the end point of this path segment.
     */
    public float getX();
    /**
     * The absolute Y coordinate for the end point of this path segment.
     */
    public float getY();
    /**
     * The absolute X coordinate for the second control point.
     */
    public float getX2();
    /**
     * The absolute Y coordinate for the second control point.
     */
    public float getY2();
}
