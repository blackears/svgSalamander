/*
 * SVGFETileElement.java
 *
 * Created on April 13, 2007, 11:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFETileElement  interface corresponds to the 'feTile' element.
 * @author kitfox
 */
public interface SVGFETileElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute in on the given 'feTile' element.
     */
    public SVGAnimatedString getIn1();
}
