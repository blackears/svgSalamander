/*
 * SVGComponentTransferFunctionElement.java
 *
 * Created on April 13, 2007, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumberList;

/**
 * This interface defines a base interface used by the component transfer function interfaces.
 * @author kitfox
 */
public interface SVGComponentTransferFunctionElement
{
    public static enum Type
    { 
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value identity.
         */
        IDENTITY, 
        /**
         * Corresponds to value table.
         */
        TABLE, 
        /**
         * Corresponds to value discrete.
         */
        DISCRETE, 
        /**
         * Corresponds to value linear.
         */
        LINEAR, 
        /**
         * Corresponds to value gamma.
         */
        GAMMA};
    /**
     * Corresponds to attribute type on the given element. Takes one of the Component Transfer Types.
     */
    public SVGAnimatedEnumeration<Type> getType();
    /**
     * Corresponds to attribute tableValues on the given element.
     */
    public SVGAnimatedNumberList getTableValues();
    /**
     * Corresponds to attribute slope on the given element.
     */
    public SVGAnimatedNumber getSlope();
    /**
     * Corresponds to attribute intercept on the given element.
     */
    public SVGAnimatedNumber getIntercept();
    /**
     * Corresponds to attribute amplitude on the given element.
     */
    public SVGAnimatedNumber getAmplitude();
    /**
     * Corresponds to attribute exponent on the given element.
     */
    public SVGAnimatedNumber getExponent();
    /**
     * Corresponds to attribute offset on the given element.
     */
    public SVGAnimatedNumber getOffset();
}
