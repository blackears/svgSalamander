/*
 * SVGFEGaussianBlurElement.java
 *
 * Created on April 13, 2007, 11:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * 
 * The SVGFEGaussianBlurElement  interface corresponds to the 'feGaussianBlur' element.
 * @author kitfox
 */
public interface SVGFEGaussianBlurElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feGaussianBlur'  element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute stdDeviation on the given 'feGaussianBlur' element. Contains the X component of attribute stdDeviation.
     */
    public SVGAnimatedNumber getStdDeviationX();
    /**
     * Corresponds to attribute stdDeviation on the given 'feGaussianBlur' element. Contains the Y component (possibly computed automatically) of attribute stdDeviation.
     */
    public SVGAnimatedNumber getStdDeviationY();
    /**
     * Sets the values for attribute stdDeviation.
     * @param stdDeviationX The X component of attribute stdDeviation.
     * @param stdDeviationY The Y component of attribute stdDeviation.
     */
    public void setStdDeviation(float stdDeviationX, float stdDeviationY);
}
