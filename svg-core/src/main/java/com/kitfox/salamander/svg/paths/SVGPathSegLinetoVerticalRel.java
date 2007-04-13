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
 * The SVGPathSegLinetoVerticalRel  interface corresponds to a "relative vertical lineto" (v) path data command.
 * @author kitfox
 */
public interface SVGPathSegLinetoVerticalRel extends SVGPathSeg
{
    /**
     * The relative Y coordinate for the end point of this path segment.
     */
    public float getY();
}
