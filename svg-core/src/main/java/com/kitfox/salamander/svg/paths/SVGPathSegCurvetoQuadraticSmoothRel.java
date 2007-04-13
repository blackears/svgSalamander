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
 * The SVGPathSegCurvetoQuadraticSmoothRel  interface corresponds to a "relative smooth quadratic curveto" (t) path data command.
 * @author kitfox
 */
public interface SVGPathSegCurvetoQuadraticSmoothRel extends SVGPathSeg
{
    /**
     * The relative X coordinate for the end point of this path segment.
     */
    public float getX();
    /**
     * The relative Y coordinate for the end point of this path segment.
     */
    public float getY();
}
