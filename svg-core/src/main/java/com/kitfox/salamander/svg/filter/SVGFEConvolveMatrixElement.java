/*
 * SVGFEConvolveMatrixElement.java
 *
 * Created on April 13, 2007, 10:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedBoolean;
import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedInteger;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumberList;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEConvolveMatrixElement  interface corresponds to the 'feConvolveMatrix' element.
 * @author kitfox
 */
public interface SVGFEConvolveMatrixElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum EdgeMode
    { 
        /**
         *  	The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value duplicate.
         */
        DUPLICATE, 
        /**
         * Corresponds to value wrap.
         */
        WRAP, 
        /**
         * Corresponds to value none.
         */
        NONE};
    /**
     * Corresponds to attribute orderX on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedInteger getOrderX();
    /**
     * Corresponds to attribute orderY on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedInteger getOrderY();
    /**
     * Corresponds to attribute kernelMatrix on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedNumberList getKernelMatrix();
    /**
     * Corresponds to attribute divisor on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedNumber getDivisor();
    /**
     * Corresponds to attribute bias on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedNumber getBias();
    /**
     * Corresponds to attribute targetX on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedInteger getTargetX();
    /**
     * Corresponds to attribute targetY on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedInteger getTargetY();
    /**
     * Corresponds to attribute edgeMode on the given 'feConvolveMatrix'  element.  Takes one of the EdgeMode Types.
     */
    public SVGAnimatedEnumeration<EdgeMode> getEdgeMode();
    /**
     * Corresponds to attribute kernelUnitLengthX on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedNumber getKernelUnitLengthX();
    /**
     * Corresponds to attribute kernelUnitLengthY on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedNumber getKernelUnitLengthY();
    /**
     * Corresponds to attribute preserveAlpha on the given 'feConvolveMatrix'  element.
     */
    public SVGAnimatedBoolean getPreserveAlpha();
}
