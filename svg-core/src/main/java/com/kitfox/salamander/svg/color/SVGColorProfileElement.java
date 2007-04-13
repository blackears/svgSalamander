/*
 * SVGColorProfileElement.java
 *
 * Created on April 13, 2007, 10:05 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.color;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGRenderingIntent;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * The SVGColorProfileElement  interface corresponds to the 'color-profile' element.
 * @author kitfox
 */
public interface SVGColorProfileElement extends SVGElement,
        SVGURIReference,
        SVGRenderingIntent
{
    /**
     * Corresponds to attribute local on the given element.
     */
    public DOMString getLocal();
    /**
     * Corresponds to attribute name on the given element.
     */
    public DOMString getName();
    /**
     * Corresponds to attribute rendering-intent on the given element. The type of rendering intent, identified by one of the SVGRenderingIntent constants.
     */
    public SVGRenderingIntent getRenderingIntent();
}
