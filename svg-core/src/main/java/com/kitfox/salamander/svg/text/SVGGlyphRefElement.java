/*
 * SVGGlyphRefElement.java
 *
 * Created on April 13, 2007, 9:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.text;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * The SVGGlyphRefElement  interface corresponds to the 'glyphRef' element.
 * @author kitfox
 */
public interface SVGGlyphRefElement extends SVGElement,
        SVGURIReference,
        SVGStylable
{
    /**
     * Corresponds to attribute glyphRef on the given element.
     */
    public DOMString getGlyphRef();
    /**
     * Corresponds to attribute format on the given element.
     */
    public DOMString getFormat();
    /**
     * Corresponds to attribute x on the given element.
     */
    public float getX();
    /**
     * Corresponds to attribute y on the given element.
     */
    public float getY();
    /**
     * Corresponds to attribute dx on the given element.
     */
    public float getDx();
    /**
     * Corresponds to attribute dy on the given element.
     */
    public float getDy();
}
