/*
 * SVGFEFloodElement.java
 *
 * Created on April 13, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEFloodElement  interface corresponds to the 'feFlood' element.
 * @author kitfox
 */
public interface SVGFEFloodElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feFlood' element.
     */
    public SVGAnimatedString getIn1();
}
