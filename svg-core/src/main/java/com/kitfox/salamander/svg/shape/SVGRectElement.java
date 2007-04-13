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
 * The SVGRectElement  interface corresponds to the 'rect' element.
 * @author kitfox
 */
public interface SVGRectElement extends SVGElement, SVGTests, SVGLangSpace,
        SVGExternalResourcesRequired, SVGStylable, SVGTransformable,
        EventTarget
{
    /**
     * Corresponds to attribute x on the given 'rect' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'rect' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'rect' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'rect' element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * Corresponds to attribute rx on the given 'rect' element.
     */
    public SVGAnimatedLength getRx();
    /**
     * Corresponds to attribute ry on the given 'rect' element.
     */
    public SVGAnimatedLength getRy();
}
