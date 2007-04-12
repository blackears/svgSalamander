/*
 * SVGImageElement.java
 *
 * Created on April 12, 2007, 6:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import com.kitfox.salamander.svg.coordSystems.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.events.EventTarget;

/**
 * The SVGImageElement  interface corresponds to the 'image' element.
 *
 * @author kitfox
 */
public interface SVGImageElement extends SVGElement, SVGURIReference, SVGTests,
        SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGTransformable,
        EventTarget
{
    /**
     * Corresponds to attribute x on the given 'image' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'image' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'image' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'image' element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * Corresponds to attribute preserveAspectRatio on the given element.
     */
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio();
}
