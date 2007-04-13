/*
 * SVGFEPointLightElement.java
 *
 * Created on April 13, 2007, 11:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEPointLightElement  interface corresponds to the 'fePointLight' element.
 * @author kitfox
 */
public interface SVGFEPointLightElement extends SVGElement
{
    /**
     * Corresponds to attribute x on the given 'fePointLight' element.
     */
    public SVGAnimatedNumber getX();
    /**
     * Corresponds to attribute y on the given 'fePointLight' element.
     */
    public SVGAnimatedNumber getY();
    /**
     * Corresponds to attribute z on the given 'fePointLight' element.
     */
    public SVGAnimatedNumber getZ();
}
