/*
 * GetSVGDocument.java
 *
 * Created on April 12, 2007, 6:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import org.w3c.dom.DOMException;

/**
 * 
 * In the case where an SVG document is embedded by reference, such as when an XHTML document has an 'object' element whose href (or equivalent) attribute references an SVG document (i.e., a document whose MIME type is "image/svg+xml" and whose root element is thus an 'svg' element), the SVG user agent is required to implement the GetSVGDocument interface for the element which references the SVG document (e.g., the HTML 'object' or comparable referencing elements).
 * @author kitfox
 */
public interface GetSVGDocument
{
    /**
     * Returns the SVGDocument  object for the referenced SVG document.
     * @return The SVGDocument  object for the referenced SVG document.
     * @throws org.w3c.dom.DOMException NOT_SUPPORTED_ERR: No SVGDocument object is available.
     */
    public SVGDocument getSVGDocument() throws DOMException;
}
