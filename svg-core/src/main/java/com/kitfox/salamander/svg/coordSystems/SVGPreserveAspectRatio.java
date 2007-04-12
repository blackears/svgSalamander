/*
 * SVGPreserveAspectRatio.java
 *
 * Created on April 12, 2007, 3:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.coordSystems;

import com.kitfox.salamander.svg.basic.SVGDataType;

/**
 * The SVGPreserveAspectRatio  interface corresponds to the preserveAspectRatio attribute, which is available for some of SVG's elements.
 * @author kitfox
 */
public interface SVGPreserveAspectRatio extends SVGDataType
{
    public static enum Type {
        /**
         *  	The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * 	  	Corresponds to value 'none' for attribute preserveAspectRatio.
         */
        NONE, 
        /**
         * Corresponds to value 'xMinYMin' for attribute preserveAspectRatio.
         */
    X_MIN_Y_MIN, 
        /**
         * Corresponds to value 'xMidYMin' for attribute preserveAspectRatio.
         */
    X_MID_Y_MIN, 
        /**
         * 	  	Corresponds to value 'xMaxYMin' for attribute preserveAspectRatio.
         */
    X_MAX_Y_MIN, 
        /**
         * Corresponds to value 'xMinYMid' for attribute preserveAspectRatio.
         */
    X_MIN_Y_MID, 
        /**
         * Corresponds to value 'xMidYMid' for attribute preserveAspectRatio.
         */
    X_MID_Y_MID, 
        /**
         * 	  	Corresponds to value 'xMaxYMid' for attribute preserveAspectRatio.
         */
    X_MAX_Y_MID, 
        /**
         * 	  	Corresponds to value 'xMinYMax' for attribute preserveAspectRatio.
         */
    X_MIN_Y_MAX, 
        /**
         * Corresponds to value 'xMidYMax' for attribute preserveAspectRatio.
         */
    X_MID_Y_MAX, 
        /**
         * 	  	Corresponds to value 'xMaxYMax' for attribute preserveAspectRatio.
         */
    X_MAX_Y_MAX, 
    };
    
    public static enum MeetOrSlice {
        /**
         * The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         *  	Corresponds to value 'meet' for attribute preserveAspectRatio.
         */
        MEET, 
        /**
         * 	  	Corresponds to value 'slice' for attribute preserveAspectRatio.
         */
        SLICE};
    
    /**
     * The type of the alignment value as specified by one of the constants specified above.
     */
    public Type getAlign();
    /**
     * The type of the meet-or-slice value as specified by one of the constants specified above.
     */
    public MeetOrSlice getMeetOrSlice();
}
