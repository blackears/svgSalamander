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
 * The SVGPathSegLinetoVerticalAbs  interface corresponds to an "absolute vertical lineto" (V) path data command.
 * @author kitfox
 */
public interface SVGPathSegLinetoVerticalAbs extends SVGPathSeg
{
    /**
     * The absolute Y coordinate for the end point of this path segment.
     */
    public float getY();
}
