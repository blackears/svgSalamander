/*
 * SVGFETurbulenceElement.java
 *
 * Created on April 13, 2007, 11:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedInteger;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFETurbulenceElement  interface corresponds to the 'feTurbulence' element.
 * @author kitfox
 */
public interface SVGFETurbulenceElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Turbulence
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value fractalNoise.
         */
        FRACTAL_NOISE, 
        /**
         * Corresponds to value turbulence.
         */
        TURBULENCE};
    public static enum StitchType
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value stitch.
         */
        STITCH, 
        /**
         * Corresponds to value noStitch.
         */
        NO_STITCH};
    /**
     * Corresponds to attribute baseFrequencyX on the given 'feTurbulence'  element.
     */
    public SVGAnimatedNumber getBaseFrequencyX();
    /**
     * Corresponds to attribute baseFrequencyY on the given 'feTurbulence'  element.
     */
    public SVGAnimatedNumber getBaseFrequencyY();
    /**
     * Corresponds to attribute numOctaves on the given 'feTurbulence'  element.
     */
    public SVGAnimatedInteger getNumOctaves();
    /**
     * Corresponds to attribute seed on the given 'feTurbulence'  element.
     */
    public SVGAnimatedNumber getSeed();
    /**
     * Corresponds to attribute stitchTiles on the given 'feTurbulence'  element.
     */
    public SVGAnimatedEnumeration<StitchType> getStitchTiles();
    /**
     * Corresponds to attribute type on the given 'feTurbulence'  element.
     */
    public SVGAnimatedEnumeration<Turbulence> getType();
}
