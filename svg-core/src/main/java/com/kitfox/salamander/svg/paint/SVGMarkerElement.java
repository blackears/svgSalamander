/*
 * SVGMarkerElement.java
 *
 * Created on April 13, 2007, 9:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.paint;

import com.kitfox.salamander.svg.basic.SVGAngle;
import com.kitfox.salamander.svg.basic.SVGAnimatedAngle;
import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGFitToViewBox;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;

/**
 * 
 * The SVGMarkerElement  interface corresponds to the 'marker' element.
 * @author kitfox
 */
public interface SVGMarkerElement extends SVGElement,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGFitToViewBox
{
    public static enum Units
    {
        /**
         * The marker unit type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * The value of attribute markerUnits is 'userSpaceOnUse'.
         */
        USER_SPACE_ON_USE, 
        /**
         * The value of attribute markerUnits is 'strokeWidth'.
         */
        STROKE_WIDTH};
    public static enum Orient
    {
        /**
         * The marker orientation is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Attribute orient has value 'auto'.
         */
        AUTO, 
        /**
         * Attribute orient has an angle value.
         */
        ANGLE};
    
    /**
     * Corresponds to attribute refX on the given 'marker' element.
     */
    public SVGAnimatedLength getRefX();
    /**
     * Corresponds to attribute refY on the given 'marker' element.
     */
    public SVGAnimatedLength getRefY();
    /**
     * Corresponds to attribute markerUnits on the given 'marker' element. One of the Marker Units Types defined above.
     */
    public SVGAnimatedEnumeration getMarkerUnits();
    /**
     * Corresponds to attribute markerWidth on the given 'marker' element.
     */
    public SVGAnimatedLength getMarkerWidth();
    /**
     * Corresponds to attribute markerHeight on the given 'marker' element.
     */
    public SVGAnimatedLength getMarkerHeight();
    /**
     * Corresponds to attribute orient on the given 'marker' element. One of the Marker Orientation Types defined above.
     */
    public SVGAnimatedEnumeration getOrientType();
    /**
     * Corresponds to attribute orient on the given 'marker' element. If markerUnits is SVG_MARKER_ORIENT_ANGLE, the angle value for attribute orient; otherwise, it will be set to zero.
     */
    public SVGAnimatedAngle getOrientAngle();
    /**
     * Sets the value of attribute orient to 'auto'.
     */
    public void setOrientToAuto();
    /**
     * Sets the value of attribute orient to the given angle.
     * @param angle The angle value to use for attribute orient.
     */
    public void setOrientToAngle(SVGAngle angle);
    
}
