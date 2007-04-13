/*
 * SVGFilterElement.java
 *
 * Created on April 13, 2007, 10:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedInteger;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGURIReference;
import com.kitfox.salamander.svg.basic.SVGUnitTypes;

/**
 * The SVGFilterElement  interface corresponds to the 'filter' element.
 * @author kitfox
 */
public interface SVGFilterElement extends SVGElement,
        SVGURIReference,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        SVGUnitTypes
{
    /**
     * Corresponds to attribute filterUnits on the given 'filter' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getFilterUnits();
    /**
     * Corresponds to attribute primitiveUnits on the given 'filter' element. Takes one of the constants defined in SVGUnitTypes.
     */
    public SVGAnimatedEnumeration<SVGUnitTypes.Type> getPrimitiveUnits();
    /**
     * Corresponds to attribute x on the given 'filter' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'filter' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'filter' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'filter' element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * Corresponds to attribute filterResX on the given 'filter' element.
     */
    public SVGAnimatedInteger getFilterResX();
    /**
     * Corresponds to attribute filterResY on the given 'filter' element.
     */
    public SVGAnimatedInteger getFilterResY();
    /**
     * Sets the values for attribute filterRes.
     * @param filterResX The X component of attribute filterRes.
     * @param filterResY The Y component of attribute filterRes.
     */
    public void setFilterRes(int filterResX, int filterResY);
    
}
