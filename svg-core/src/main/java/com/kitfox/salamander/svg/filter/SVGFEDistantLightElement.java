/*
 * SVGFEDistantLightElement.java
 *
 * Created on April 13, 2007, 11:05 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEDistantLightElement  interface corresponds to the 'feDistantLight' element.
 * @author kitfox
 */
public interface SVGFEDistantLightElement extends SVGElement
{
    /**
     * Corresponds to attribute azimuth on the given 'feDistantLight'  element.
     */
    public SVGAnimatedNumber getAzimuth();
    /**
     * Corresponds to attribute elevation on the given 'feDistantLight'  element.
     */
    public SVGAnimatedNumber getElevation();
}
