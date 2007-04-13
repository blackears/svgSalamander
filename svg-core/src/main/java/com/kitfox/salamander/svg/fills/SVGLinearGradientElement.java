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
 * The SVGLinearGradientElement  interface corresponds to the 'linearGradient' element.
 * @author kitfox
 */
public interface SVGLinearGradientElement extends SVGGradientElement
{
    /**
     * Corresponds to attribute x1 on the given 'linearGradient'  element.
     */
    public SVGAnimatedLength getX1();
    /**
     * Corresponds to attribute y1 on the given 'linearGradient'  element.
     */
    public SVGAnimatedLength getY1();
    /**
     * Corresponds to attribute x2 on the given 'linearGradient'  element.
     */
    public SVGAnimatedLength getX2();
    /**
     * Corresponds to attribute y2 on the given 'linearGradient'  element.
     */
    public SVGAnimatedLength getY2();
}
