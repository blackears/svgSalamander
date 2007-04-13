/*
 * SVGRectElement.java
 *
 * Created on April 12, 2007, 9:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.shape;

import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import org.w3c.dom.events.EventTarget;

/**
 * The SVGCircleElement  interface corresponds to the 'circle' element.
 * @author kitfox
 */
public interface SVGCircleElement extends SVGElement, SVGTests, SVGLangSpace,
        SVGExternalResourcesRequired, SVGStylable, SVGTransformable,
        EventTarget
{
    /**
     * Corresponds to attribute cx on the given 'circle' element.
     */
    public SVGAnimatedLength getCx();
    /**
     * Corresponds to attribute cy on the given 'circle' element.
     */
    public SVGAnimatedLength getCy();
    /**
     * Corresponds to attribute r on the given 'circle' element.
     */
    public SVGAnimatedLength getX();
}
