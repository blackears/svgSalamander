/*
 * SVGFECompositeElement.java
 *
 * Created on April 13, 2007, 10:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFECompositeElement interface corresponds to the 'feComposite' element.
 * @author kitfox
 */
public interface SVGFECompositeElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Operator
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value over.
         */
        OVER, 
        /**
         * Corresponds to value in.
         */
        IN, 
        /**
         * Corresponds to value out.
         */
        OUT, 
        /**
         * Corresponds to value atop.
         */
        ATOP, 
        /**
         * Corresponds to value xor.
         */
        XOR, 
        /**
         * Corresponds to value arithmetic.
         */
        ARITHMETIC};
    /**
     * Corresponds to attribute in on the given 'feComposite' element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute in2 on the given 'feComposite' element.
     */
    public SVGAnimatedString getIn2();
    /**
     * Corresponds to attribute operator on the given 'feComposite' element. Takes one of the Composite Operators.
     */
    public SVGAnimatedEnumeration<Operator> getOperator();
    /**
     * Corresponds to attribute k1 on the given 'feComposite' element.
     */
    public SVGAnimatedNumber getK1();
    /**
     * Corresponds to attribute k2 on the given 'feComposite' element.
     */
    public SVGAnimatedNumber getK2();
    /**
     * Corresponds to attribute k3 on the given 'feComposite' element.
     */
    public SVGAnimatedNumber getK3();
    /**
     * Corresponds to attribute k4 on the given 'feComposite' element.
     */
    public SVGAnimatedNumber getK4();
}
