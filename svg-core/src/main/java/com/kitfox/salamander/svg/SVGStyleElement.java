/*
 * SVGStyleElement.java
 *
 * Created on April 12, 2007, 7:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg;

/**
 * 
 * The SVGStyleElement  interface corresponds to the 'style' element.
 * @author kitfox
 */
public interface SVGStyleElement
{
    /**
     * Corresponds to attribute xml:space on the given element.
     */
    public DOMString getXmlspace();
    /**
     * Corresponds to attribute type on the given 'style' element.
     */
    public DOMString getType();
    /**
     * Corresponds to attribute media on the given 'style' element.
     */
    public DOMString getMedia();
    /**
     * Corresponds to attribute title on the given 'style' element.
     */
    public DOMString getTitle();
}
