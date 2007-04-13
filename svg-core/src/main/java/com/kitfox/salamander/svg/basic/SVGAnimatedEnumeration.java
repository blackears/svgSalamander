/*
 * SVGAnimatedString.java
 *
 * Created on April 12, 2007, 2:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Used for attributes of type DOMString which can be animated.
 * @author kitfox
 */
public interface SVGAnimatedEnumeration<T extends Enum>
{
    /**
     * The base value of the given attribute before applying any animations.
     */
    public T getBaseVal();
    /**
     * If the given attribute or property is being animated, contains the current animated value of the attribute or property. If the given attribute or property is not currently being animated, contains the same value as 'baseVal'.
     */
    public T getAnimVal();
}
