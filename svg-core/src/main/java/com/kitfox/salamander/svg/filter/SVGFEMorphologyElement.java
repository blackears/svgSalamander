/*
 * SVGFEMorphologyElement.java
 *
 * Created on April 13, 2007, 11:20 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEMorphologyElement  interface corresponds to the 'feMorphology' element.
 * @author kitfox
 */
public interface SVGFEMorphologyElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Operator
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value erode.
         */
        ERODE, 
        /**
         * Corresponds to value dilate.
         */
        DILATE};
    /**
     * Corresponds to attribute in on the given 'feMorphology' element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute operator on the given 'feMorphology' element. Takes one of the Morphology Operators.
     */
    public SVGAnimatedEnumeration<Operator> getOperator();
    /**
     * Corresponds to attribute radiusX on the given 'feMorphology' element.
     */
    public SVGAnimatedNumber getRadiusX();
    /**
     * Corresponds to attribute radiusY on the given 'feMorphology' element.
     */
    public SVGAnimatedNumber getRadiusY();
}
