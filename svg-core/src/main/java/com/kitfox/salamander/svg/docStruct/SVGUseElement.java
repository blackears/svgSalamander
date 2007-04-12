/*
 * SVGGElement.java
 *
 * Created on April 12, 2007, 6:14 PM
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
import org.w3c.dom.events.EventTarget;

/**
 * The SVGUseElement  interface corresponds to the 'use' element.
 * @author kitfox
 */
public interface SVGUseElement extends SVGElement, SVGURIReference, 
        SVGTests, SVGLangSpace, SVGExternalResourcesRequired, 
        SVGStylable, SVGTransformable, EventTarget
{
    /**
     * Corresponds to attribute x on the given 'use' element.
     */
    public SVGAnimatedLength getX();
    /**
     * 
     *    Corresponds to attribute y on the given 'use' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'use' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'use' element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * The root of the "instance tree". See description of SVGElementInstance  for a discussion on the instance tree.
     */
    public SVGElementInstance getInstanceRoot();
    /**
     * If the 'href' attribute is being animated, contains the current animated root of the "instance tree". If the 'href' attribute is not currently being animated, contains the same value as 'instanceRoot'. The root of the "instance tree". See description of SVGElementInstance  for a discussion on the instance tree.
     */
    public SVGElementInstance getAnimatedInstanceRoot();
}
