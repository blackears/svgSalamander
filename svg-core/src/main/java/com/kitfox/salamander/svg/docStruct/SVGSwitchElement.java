/*
 * SVGGElement.java
 *
 * Created on April 12, 2007, 6:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import org.w3c.dom.events.EventTarget;

/**
 * The SVGSwitchElement  interface corresponds to the 'switch' element.
 *
 * @author kitfox
 */
public interface SVGSwitchElement extends SVGElement, SVGTests, SVGLangSpace,
        SVGExternalResourcesRequired, SVGStylable, SVGTransformable,
        EventTarget
{
    
}
