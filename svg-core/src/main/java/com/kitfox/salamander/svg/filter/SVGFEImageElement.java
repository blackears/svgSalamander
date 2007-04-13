/*
 * SVGFEImageElement.java
 *
 * Created on April 13, 2007, 11:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import com.kitfox.salamander.svg.coordSystems.SVGAnimatedPreserveAspectRatio;

/**
 * The SVGFEImageElement  interface corresponds to the 'feImage' element.
 * @author kitfox
 */
public interface SVGFEImageElement extends SVGElement,
        SVGURIReference,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGFilterPrimitiveStandardAttributes
{
    /**
     * Corresponds to attribute preserveAspectRatio on the given element.
     */
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio();
}
