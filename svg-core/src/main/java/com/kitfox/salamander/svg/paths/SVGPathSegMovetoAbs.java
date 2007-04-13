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
 * The SVGPathSegMovetoAbs interface corresponds to an "absolute moveto" (M) path data command.
 * @author kitfox
 */
public interface SVGPathSegMovetoAbs extends SVGPathSeg
{
    /**
     * The absolute X coordinate for the end point of this path segment.
     */
    public float getX();
    /**
     * The absolute Y coordinate for the end point of this path segment.
     */
    public float getY();
}
