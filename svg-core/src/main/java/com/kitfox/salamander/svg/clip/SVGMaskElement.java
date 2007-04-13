/*
 * SVGMaskElement.java
 *
 * Created on April 13, 2007, 10:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.clip;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGUnitTypes;

/**
 * The SVGMaskElement  interface corresponds to the 'mask' element.
 * @author kitfox
 */
public interface SVGMaskElement extends SVGElement,
        SVGTests,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGUnitTypes
{
    /**
     * Corresponds to attribute maskUnits on the given 'mask' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getMaskUnits();
    /**
     * Corresponds to attribute maskContentUnits on the given 'mask' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getMaskContentUnits();
    /**
     * Corresponds to attribute x on the given 'mask' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'mask' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'mask' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'mask' element.
     */
    public SVGAnimatedLength getHeight();
}
