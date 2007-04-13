/*
 * SVGGradientElement.java
 *
 * Created on April 13, 2007, 10:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.fills;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import com.kitfox.salamander.svg.coordSystems.SVGAnimatedTransformList;

/**
 * 
 * The SVGGradientElement  interface is a base interface used by SVGLinearGradientElement and SVGRadialGradientElement.
 * @author kitfox
 */
public interface SVGGradientElement extends SVGElement,
        SVGURIReference,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGUnitTypes
{
    public static enum SpreadMethod
    {
        /**
         *  	The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value pad.
         */
        PAD, 
        /**
         * Corresponds to value reflect.
         */
        REFLECT, 
        /**
         * Corresponds to value repeat.
         */
        REPEAT};
    /**
     * Corresponds to attribute gradientUnits on the given element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration getGradientUnits();
    /**
     * Corresponds to attribute gradientTransform on the given element.
     */
    public SVGAnimatedTransformList getGradientTransform();
    /**
     * Corresponds to attribute spreadMethod on the given element. One of the Spread Method Types.
     */
    public SVGAnimatedEnumeration getSpreadMethod();
    
}
