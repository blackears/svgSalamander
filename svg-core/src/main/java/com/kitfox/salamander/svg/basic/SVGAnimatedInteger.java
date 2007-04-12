/*
 * SVGAnimatedInteger.java
 *
 * Created on April 12, 2007, 12:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Used for attributes of basic type 'integer' which can be animated.
 * @author kitfox
 */
public interface SVGAnimatedInteger
{
    /**
     * The base value of the given attribute before applying any animations.
     */
    public int getBaseVal();
    
    /**
     * If the given attribute or property is being animated, contains the 
     * current animated value of the attribute or property. If the given 
     * attribute or property is not currently being animated, contains the 
     * same value as 'baseVal'.
     */
    public int getAnimVal();
}
