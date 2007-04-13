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
 * The SVGPathSegCurvetoQuadraticAbs  interface corresponds to an "absolute quadratic Bézier curveto" (Q) path data command.
 * @author kitfox
 */
public interface SVGPathSegCurvetoQuadraticAbs extends SVGPathSeg
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
     * The absolute X coordinate for the control point.
     */
    public float getX1();
    /**
     * The absolute Y coordinate for the control point.
     */
    public float getY1();
}
