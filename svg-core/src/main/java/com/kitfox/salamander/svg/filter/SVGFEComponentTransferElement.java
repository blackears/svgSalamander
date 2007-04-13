/*
 * SVGFEComponentTransferElement.java
 *
 * Created on April 13, 2007, 10:45 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEComponentTransferElement  interface corresponds to the 'feComponentTransfer' element.
 * @author kitfox
 */
public interface SVGFEComponentTransferElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feComponentTransfer'  element.
     */
    public SVGAnimatedString getIn1();
}
