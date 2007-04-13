/*
 * SVGClipPathElement.java
 *
 * Created on April 13, 2007, 10:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.clip;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import com.kitfox.salamander.svg.basic.SVGUnitTypes;

/**
 * The SVGClipPathElement  interface corresponds to the 'clipPath' element.
 * @author kitfox
 */
public interface SVGClipPathElement extends SVGElement,
        SVGTests,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGTransformable,
        SVGUnitTypes
{
    /**
     * Corresponds to attribute clipPathUnits on the given 'clipPath' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getClipPathUnits();
}
