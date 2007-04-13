/*
 * SVGCursorElement.java
 *
 * Created on April 13, 2007, 11:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.interactivity;

import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * The SVGCursorElement  interface corresponds to the 'cursor' element.
 * @author kitfox
 */
public interface SVGCursorElement extends SVGElement,
        SVGURIReference,
        SVGTests,
        SVGExternalResourcesRequired
{
    /**
     * Corresponds to attribute x on the given 'cursor' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'cursor' element.
     */
    public SVGAnimatedLength getY();
}
