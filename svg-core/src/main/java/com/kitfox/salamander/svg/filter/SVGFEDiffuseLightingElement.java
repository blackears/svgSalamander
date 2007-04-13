/*
 * SVGFEDiffuseLightingElement.java
 *
 * Created on April 13, 2007, 11:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEDiffuseLightingElement  interface corresponds to the 'feDiffuseLighting' element.
 * @author kitfox
 */
public interface SVGFEDiffuseLightingElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feDiffuseLighting'  element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute surfaceScale on the given 'feDiffuseLighting'  element.
     */
    public SVGAnimatedNumber getSurfaceScale();
    /**
     * Corresponds to attribute diffuseConstant on the given 'feDiffuseLighting'  element.
     */
    public SVGAnimatedNumber getDiffuseConstant();
    /**
     * Corresponds to attribute kernelUnitLengthX on the given 'feDiffuseLighting'  element.
     */
    public SVGAnimatedNumber getKernelUnitLengthX();
    /**
     * Corresponds to attribute kernelUnitLengthY on the given 'feDiffuseLighting'  element.
     */
    public SVGAnimatedNumber getKernelUnitLengthY();
}
