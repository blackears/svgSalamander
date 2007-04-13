/*
 * SVGFEBlendElement.java
 *
 * Created on April 13, 2007, 10:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * 
 * The SVGFEBlendElement  interface corresponds to the 'feBlend' element.
 * @author kitfox
 */
public interface SVGFEBlendElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Mode
    {
        /**
         * 	  	The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value normal.
         */
        NORMAL, 
        /**
         * Corresponds to value multiply.
         */
        MULTIPLY, 
        /**
         * Corresponds to value screen.
         */
        SCREEN, 
        /**
         * Corresponds to value darken.
         */
        DARKEN, 
        /**
         * Corresponds to value lighten.
         */
        LIGHTEN};
    /**
     * Corresponds to attribute in on the given 'feBlend' element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute in2 on the given 'feBlend' element.
     */
    public SVGAnimatedString getIn2();
    /**
     * Corresponds to attribute mode on the given 'feBlend' element.
     */
    public SVGAnimatedEnumeration<Mode> getMode();
    
}
