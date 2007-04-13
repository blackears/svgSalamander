/*
 * SVGScriptElement.java
 *
 * Created on April 13, 2007, 11:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.script;

import com.kitfox.salamander.svg.*;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * The SVGScriptElement  interface corresponds to the 'script' element.
 * @author kitfox
 */
public interface SVGScriptElement extends SVGElement,
        SVGURIReference,
        SVGExternalResourcesRequired
{
    /**
     * Corresponds to attribute type on the given 'script' element.
     */
    public DOMString getType();
}
