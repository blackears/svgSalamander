/*
 * SVGAElement.java
 *
 * Created on April 13, 2007, 11:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.linking;

import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import org.w3c.dom.events.EventTarget;

/**
 * The SVGAElement  interface corresponds to the 'a' element.
 * @author kitfox
 */
public interface SVGAElement extends SVGElement,
        SVGURIReference,
        SVGTests,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGTransformable,
        EventTarget
{
    /**
     * Corresponds to attribute target on the given 'a' element.
     */
    public SVGAnimatedString getTarget();
}
