/*
 * SVGFESpotLightElement.java
 *
 * Created on April 13, 2007, 11:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFESpotLightElement interface corresponds to the 'feSpotLight' element.
 * @author kitfox
 */
public interface SVGFESpotLightElement extends SVGElement
{
    /**
     * Corresponds to attribute x on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getX();
    /**
     * Corresponds to attribute y on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getY();
    /**
     * Corresponds to attribute z on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getZ();
    /**
     * Corresponds to attribute pointsAtX on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getPointsAtX();
    /**
     * Corresponds to attribute pointsAtY on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getPointsAtY();
    /**
     * Corresponds to attribute pointsAtZ on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getPointsAtZ();
    /**
     * Corresponds to attribute specularExponent on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getSpecularExponent();
    /**
     * Corresponds to attribute limitingConeAngle on the given 'feSpotLight' element.
     */
    public SVGAnimatedNumber getLimitingConeAngle();
}
