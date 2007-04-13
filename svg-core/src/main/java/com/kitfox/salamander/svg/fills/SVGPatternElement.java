/*
 * SVGStopElement.java
 *
 * Created on April 13, 2007, 10:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.fills;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGFitToViewBox;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import com.kitfox.salamander.svg.basic.SVGUnitTypes;
import com.kitfox.salamander.svg.coordSystems.SVGAnimatedTransformList;

/**
 * The SVGPatternElement  interface corresponds to the 'pattern' element.
 * @author kitfox
 */
public interface SVGPatternElement extends SVGElement,
        SVGURIReference,
        SVGTests,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGFitToViewBox,
        SVGUnitTypes
{
    /**
     * Corresponds to attribute patternUnits on the given 'pattern' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getPatternUnits();
    /**
     * Corresponds to attribute patternContentUnits on the given 'pattern' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getPatternContentUnits();
    /**
     * Corresponds to attribute patternTransform on the given 'pattern' element.
     */
    public SVGAnimatedTransformList getPatternTransform();
    /**
     * Corresponds to attribute x on the given 'pattern' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'pattern' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'pattern' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'pattern' element.
     */
    public SVGAnimatedLength getHeight();
}
