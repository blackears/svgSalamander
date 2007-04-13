/*
 * SVGAltGlyphElement.java
 *
 * Created on April 13, 2007, 9:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.text;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGURIReference;

/**
 * The SVGAltGlyphElement  interface corresponds to the 'altGlyph' element.
 * @author kitfox
 */
public interface SVGAltGlyphElement extends SVGTextPositioningElement,
        SVGURIReference
{
    /**
     * Corresponds to attribute glyphRef on the given element.
     */
    public DOMString getGlyphRef();
    /**
     * Corresponds to attribute format on the given element.
     */
    public DOMString getFormat();
    
}
