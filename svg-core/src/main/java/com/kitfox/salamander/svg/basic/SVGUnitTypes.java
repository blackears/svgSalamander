/*
 * SVGUnitTypes.java
 *
 * Created on April 12, 2007, 2:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * The SVGUnitTypes  interface defines a commonly used set of constants and is a base interface used by SVGGradientElement, SVGPatternElement, SVGClipPathElement, SVGMaskElement, and SVGFilterElement.
 * @author kitfox
 */
public interface SVGUnitTypes
{
    public static enum Type
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN,
        /**
         * Corresponds to value userSpaceOnUse.
         */
        USER_SPACE_ON_USE,
        /**
         * Corresponds to value objectBoundingBox.
         */
        OBJECT_BOUNDING_BOX
    };
}
