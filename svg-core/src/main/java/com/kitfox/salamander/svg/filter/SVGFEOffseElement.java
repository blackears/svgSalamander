/*
 * SVGFEOffseElement.java
 *
 * Created on April 13, 2007, 11:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEOffsetElement  interface corresponds to the 'feOffset' element.
 * @author kitfox
 */
public interface SVGFEOffseElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feOffset' element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute dx on the given 'feOffset' element.
     */
    public SVGAnimatedNumber getDx();
    /**
     * Corresponds to attribute dy on the given 'feOffset' element.
     */
    public SVGAnimatedNumber getDy();
}
