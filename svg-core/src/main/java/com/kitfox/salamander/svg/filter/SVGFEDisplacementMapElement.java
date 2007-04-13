/*
 * SVGFEDisplacementMapElement.java
 *
 * Created on April 13, 2007, 11:10 AM
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
 * 
 * The SVGFEDisplacementMapElement  interface corresponds to the 'feDisplacementMap' element.
 * @author kitfox
 */
public interface SVGFEDisplacementMapElement extends SVGElement,
        SVGFilterPrimitiveStandardAttributes
{
    public static enum Channel
    {
        /**
         * The type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value R.
         */
        R, 
        /**
         * Corresponds to value G.
         */
        G, 
        /**
         * Corresponds to value B.
         */
        B, 
        /**
         * Corresponds to value A.
         */
        A};
    /**
     * Corresponds to attribute in on the given 'feDisplacementMap'  element.
     */
    public SVGAnimatedString getIn1();
    /**
     * Corresponds to attribute in2 on the given 'feDisplacementMap'  element.
     */
    public SVGAnimatedString getIn2();
    /**
     * Corresponds to attribute scale on the given 'feDisplacementMap'  element.
     */
    public SVGAnimatedNumber getScale();
    /**
     * Corresponds to attribute xChannelSelector on the given 'feDisplacementMap'  element.
     */
    public SVGAnimatedEnumeration<Channel> getXChannelSelector();
    /**
     * Corresponds to attribute yChannelSelector on the given 'feDisplacementMap'  element.
     */
    public SVGAnimatedEnumeration<Channel> getYChannelSelector();
}
