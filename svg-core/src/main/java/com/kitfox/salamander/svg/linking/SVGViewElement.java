/*
 * SVGViewElement.java
 *
 * Created on April 13, 2007, 11:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.linking;

import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGFitToViewBox;
import com.kitfox.salamander.svg.basic.SVGStringList;
import com.kitfox.salamander.svg.basic.SVGZoomAndPan;

/**
 * The SVGViewElement  interface corresponds to the 'view' element.
 * @author kitfox
 */
public interface SVGViewElement extends SVGElement,
        SVGExternalResourcesRequired,
        SVGFitToViewBox,
        SVGZoomAndPan
{
    /**
     * Corresponds to attribute viewTarget on the given 'view' element. A list of DOMString values which contain the names listed in the viewTarget attribute. Each of the DOMString values can be associated with the corresponding element using the getElementById() method call.
     */
    public SVGStringList getViewTarget();
}
