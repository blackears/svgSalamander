/*
 * SVGLinearGradientElement.java
 *
 * Created on April 13, 2007, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.fills;

import com.kitfox.salamander.svg.basic.SVGAnimatedLength;

/**
 * The SVGRadialGradientElement  interface corresponds to the 'radialGradient' element.
 * @author kitfox
 */
public interface SVGRadialGradientElement extends SVGGradientElement
{
    /**
     * Corresponds to attribute cx on the given 'radialGradient'  element.
     */
    public SVGAnimatedLength getCx();
    /**
     * Corresponds to attribute cy on the given 'radialGradient'  element.
     */
    public SVGAnimatedLength getCy();
    /**
     * Corresponds to attribute r on the given 'radialGradient'  element.
     */
    public SVGAnimatedLength getR();
    /**
     * Corresponds to attribute fx on the given 'radialGradient'  element.
     */
    public SVGAnimatedLength getFx();
    /**
     * Corresponds to attribute fy on the given 'radialGradient'  element.
     */
    public SVGAnimatedLength getFy();
}
