/*
 * SVGFESpecularLightingElement.java
 *
 * Created on April 13, 2007, 11:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFESpecularLightingElement  interface corresponds to the 'feSpecularLighting' element.
 * @author kitfox
 */
public interface SVGFESpecularLightingElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feSpecularLighting'  element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute surfaceScale on the given 'feSpecularLighting'  element.
     */
    public SVGAnimatedNumber getSurfaceScale();
    /**
     * Corresponds to attribute specularConstant on the given 'feSpecularLighting'  element.
     */
    public SVGAnimatedNumber getSpecularConstant();
    /**
     * Corresponds to attribute specularExponent on the given 'feSpecularLighting'  element.
     */
    public SVGAnimatedNumber getSpecularExponent();
}
