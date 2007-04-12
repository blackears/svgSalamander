/*
 * SVGExternalResourcesRequired.java
 *
 * Created on April 12, 2007, 3:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Interface SVGExternalResourcesRequired  defines an interface which applies to all elements where this element or one of its descendants can reference an external resource.
 * @author kitfox
 */
public interface SVGExternalResourcesRequired
{
    /**
     * Corresponds to attribute externalResourcesRequired on the given element. Note that the SVG DOM defines the attribute externalResourcesRequired as being of type SVGAnimatedBoolean, whereas the SVG language definition says that externalResourcesRequired is not animated. Because the SVG language definition states that externalResourcesRequired  cannot be animated, the animVal will always be the same as the baseVal.
     */
    public SVGAnimatedBoolean getExternalResourcesRequired();
}
