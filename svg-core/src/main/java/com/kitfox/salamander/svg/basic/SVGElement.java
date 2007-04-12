/*
 * SVGElement.java
 *
 * Created on April 12, 2007, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * All of the SVG DOM interfaces that correspond directly to elements in the SVG language (e.g., the SVGPathElement interface corresponds directly to the 'path' element in the language) are derivative from base class SVGElement.
 * @author kitfox
 */
public interface SVGElement
{
    /**
     * The value of the id attribute on the given element.
     */
    public String getId();
    /**
     * Corresponds to attribute xml:base on the given element.
     */
    public String getXmlbase();
    /**
     * The nearest ancestor 'svg' element. Null if the given element is the outermost 'svg' element.
     */
    public SVGElement getOwnerSVGElement();
    /**
     * The element which established the current viewport. Often, the nearest ancestor 'svg' element. Null if the given element is the outermost 'svg' element.
     */
    public SVGElement getViewportElement();
}
