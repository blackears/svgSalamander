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
 * The SVGPathSegArcRel  interface corresponds to a "relative arcto" (a) path data command.
 * @author kitfox
 */
public interface SVGPathSegCurvetoArcRel extends SVGPathSeg
{
    /**
     * The relative X coordinate for the end point of this path segment.
     */
    public float getX();
    /**
     * The relative Y coordinate for the end point of this path segment.
     */
    public float getY();
    /**
     * The x-axis radius for the ellipse (i.e., r1).
     */
    public float getR1();
    /**
     * The y-axis radius for the ellipse (i.e., r2).
     */
    public float getR2();
    /**
     * The rotation angle in degrees for the ellipse's x-axis relative to the x-axis of the user coordinate system.
     */
    public float getAngle();
    /**
     * The value of the large-arc-flag parameter.
     */
    public boolean getLargeArcFlag();
    /**
     * The value of the sweep-flag parameter.
     */
    public boolean getSweepFlag();
}
