/*
 * SVGFEColorMatrixElement.java
 *
 * Created on April 13, 2007, 10:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumberList;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * 
 * The SVGFEColorMatrixElement  interface corresponds to the 'feColorMatrix' element.
 * @author kitfox
 */
public interface SVGFEColorMatrixElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Type
    { 
        /**
         * 	  	The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value matrix.
         */
        MATRIX, 
        /**
         * Corresponds to value saturate.
         */
        SATURATE, 
        /**
         * Corresponds to value hueRotate.
         */
        HUE_ROTATE, 
        /**
         * Corresponds to value luminanceToAlpha.
         */
        LUMINANCE_TO_ALPHA};
    /**
     * Corresponds to attribute in on the given 'feColorMatrix' element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute type on the given 'feColorMatrix' element. Takes one of the Color Matrix Types.
     */
    public SVGAnimatedEnumeration<Type> getType();
    /**
     * Corresponds to attribute values on the given 'feColorMatrix' element.
     * 
     * Provides access to the contents of the values attribute.
     */
    public SVGAnimatedNumberList getValues();
}
