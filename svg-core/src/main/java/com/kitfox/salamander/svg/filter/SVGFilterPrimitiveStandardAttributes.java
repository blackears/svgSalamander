/*
 * SVGFilterPrimitiveStandardAttributes.java
 *
 * Created on April 13, 2007, 10:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGStylable;

/**
 * This interface defines the set of DOM attributes that are common across the filter interfaces.
 * @author kitfox
 */
public interface SVGFilterPrimitiveStandardAttributes extends SVGStylable
{
    /**
     * Corresponds to attribute x on the given element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * Corresponds to attribute result on the given element.
     */
    public SVGAnimatedString getResult();
}
