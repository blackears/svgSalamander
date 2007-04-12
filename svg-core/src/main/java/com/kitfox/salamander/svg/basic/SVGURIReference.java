/*
 * SVGURIReference.java
 *
 * Created on April 12, 2007, 3:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Interface SVGURIReference defines an interface which applies to all elements which have the collection of XLink attributes, such as xlink:href, which define a URI reference.
 * @author kitfox
 */
public interface SVGURIReference
{
    /**
     * Corresponds to attribute xlink:href on the given element.
     */
    public SVGAnimatedString getHref();
}
