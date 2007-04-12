/*
 * SVGAnimatedAngle.java
 *
 * Created on April 12, 2007, 1:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Corresponds to all properties and attributes whose values can be basic type 'angle' and which are animatable.
 * @author kitfox
 */
public interface SVGAnimatedDataType<T extends SVGDataType>
{
    /**
     * The base value of the given attribute before applying any animations.
     */
    public T getBaseVal();
    /**
     * If the given attribute or property is being animated, contains the current animated value of the attribute or property, and both the object itself and its contents are readonly. If the given attribute or property is not currently being animated, contains the same value as 'baseVal'.
     */
    public T getAnimVal();
}
