/*
 * SVGPathSeg.java
 *
 * Created on April 12, 2007, 7:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.paths;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGDataType;

/**
 * 
 * The SVGPathSeg  interface is a base interface that corresponds to a single command within a path data specification.
 * @author kitfox
 */
public interface SVGPathSeg extends SVGDataType
{
    public static enum Type
    {
        /**
         *  	The unit type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        PATHSEG_UNKNOWN,
        /**
         * Corresponds to a "closepath" (z) path data command.
         */
        PATHSEG_CLOSEPATH,
        /**
         * Corresponds to an "absolute moveto" (M) path data command.
         */
        PATHSEG_MOVETO_ABS,
        /**
         *  	Corresponds to a "relative moveto" (m) path data command.
         */
        PATHSEG_MOVETO_REL,
        /**
         *  	Corresponds to an "absolute lineto" (L) path data command.
         */
        PATHSEG_LINETO_ABS,
        /**
         * 	  	Corresponds to a "relative lineto" (l) path data command.
         */
        PATHSEG_LINETO_REL,
        /**
         * 	  	Corresponds to an "absolute cubic Bézier curveto" (C) path data command.
         */
        PATHSEG_CURVETO_CUBIC_ABS,
        /**
         *  	Corresponds to a "relative cubic Bézier curveto" (c) path data command.
         */
        PATHSEG_CURVETO_CUBIC_REL,
        /**
         * 	  	Corresponds to an "absolute quadratic Bézier curveto" (Q) path data command.
         */
        PATHSEG_CURVETO_QUADRATIC_ABS,
        /**
         * Corresponds to a "relative quadratic Bézier curveto" (q) path data command.
         */
        PATHSEG_CURVETO_QUADRATIC_REL,
        /**
         * 	  	Corresponds to an "absolute arcto" (A) path data command.
         */
        PATHSEG_ARC_ABS,
        /**
         * Corresponds to a "relative arcto" (a) path data command.
         */
        PATHSEG_ARC_REL,
        /**
         * Corresponds to an "absolute horizontal lineto" (H) path data command.
         */
        PATHSEG_LINETO_HORIZONTAL_ABS,
        /**
         * Corresponds to a "relative horizontal lineto" (h) path data command.
         */
        PATHSEG_LINETO_HORIZONTAL_REL,
        /**
         * 	  	Corresponds to an "absolute vertical lineto" (V) path data command.
         */
        PATHSEG_LINETO_VERTICAL_ABS,
        /**
         * 	  	Corresponds to a "relative vertical lineto" (v) path data command.
         */
        PATHSEG_LINETO_VERTICAL_REL,
        /**
         *  	Corresponds to an "absolute smooth cubic curveto" (S) path data command.
         */
        PATHSEG_CURVETO_CUBIC_SMOOTH_ABS,
        /**
         * 	  	Corresponds to a "relative smooth cubic curveto" (s) path data command.
         */
        PATHSEG_CURVETO_CUBIC_SMOOTH_REL,
        /**
         * Corresponds to an "absolute smooth quadratic curveto" (T) path data command.
         */
        PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS,
        /**
         * Corresponds to a "relative smooth quadratic curveto" (t) path data command.
         */
        PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL
    };
    
    /**
     * The type of the path segment as specified by one of the constants specified above.
     */
    public Type getPathSegType();
    /**
     * The type of the path segment, specified by the corresponding one character command name.
     */
    public DOMString getPathSegTypeAsLetter();
}
