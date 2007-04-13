/*
 * SVGTextPathElement.java
 *
 * Created on April 13, 2007, 9:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.text;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * 
 * The SVGTextPathElement  interface corresponds to the 'textPath' element.
 * @author kitfox
 */
public interface SVGTextPathElement extends SVGTextContentElement, SVGURIReference
{
    public static enum MethodType
    {
        /**
         * The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value align.
         */
        ALIGN, 
        /**
         * Corresponds to value stretch.
         */
        STRETCH};
    public static enum SpacingType
    {
        /**
         * The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value auto.
         */
        AUTO, 
        /**
         * Corresponds to value exact.
         */
        EXACT};
    /**
     * Corresponds to attribute startOffset on the given 'textPath' element.
     */
    public SVGAnimatedLength getStartOffset();
    /**
     * Corresponds to attribute method on the given 'textPath' element. The value must be one of the method type constants specified above.
     */
    public SVGAnimatedEnumeration<MethodType> getMethod();
    /**
     * Corresponds to attribute spacing on the given 'textPath' element. The value must be one of the spacing type constants specified above.
     */
    public SVGAnimatedEnumeration<SpacingType> getSpacing();
}
