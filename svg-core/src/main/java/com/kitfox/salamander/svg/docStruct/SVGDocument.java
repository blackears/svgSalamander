/*
 * SVGDocument.java
 *
 * Created on April 12, 2007, 6:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import com.kitfox.salamander.svg.DOMString;
import org.w3c.dom.Document;
import org.w3c.dom.events.DocumentEvent;

/**
 * 
 * When an 'svg' element is embedded inline as a component of a document from another namespace, such as when an 'svg' element is embedded inline within an XHTML document [XHTML], then an SVGDocument object will not exist; instead, the root object in the document object hierarchy will be a Document object of a different type, such as an HTMLDocument object.
 * 
 * However, an SVGDocument object will indeed exist when the root element of the XML document hierarchy is an 'svg' element, such as when viewing a stand-alone SVG file (i.e., a file with MIME type "image/svg+xml"). In this case, the SVGDocument object will be the root object of the document object model hierarchy.
 * 
 * In the case where an SVG document is embedded by reference, such as when an XHTML document has an 'object' element whose href attribute references an SVG document (i.e., a document whose MIME type is "image/svg+xml" and whose root element is thus an 'svg' element), there will exist two distinct DOM hierarchies. The first DOM hierarchy will be for the referencing document (e.g., an XHTML document). The second DOM hierarchy will be for the referenced SVG document. In this second DOM hierarchy, the root object of the document object model hierarchy is an SVGDocument object.
 * 
 * The SVGDocument interface contains a similar list of attributes and methods to the HTMLDocument interface described in the Document Object Model (HTML) Level 1 chapter of the [DOM1] specification.
 * @author kitfox
 */
public interface SVGDocument extends Document, DocumentEvent
{
    /**
     * The title of a document as specified by the title sub-element of the 'svg' root element (i.e., <svg><title>Here is the title</title>...</svg>)
     */
    public DOMString getTitle();
    /**
     * Returns the URI of the page that linked to this page. The value is an empty string if the user navigated to the page directly (not through a link, but, for example, via a bookmark).
     */
    public DOMString getReferrer();
    /**
     * The domain name of the server that served the document, or a null string if the server cannot be identified by a domain name.
     */
    public DOMString getDomain();
    /**
     * The complete URI of the document.
     */
    public DOMString getURL();
    /**
     * The root 'svg'  element in the document hierarchy.
     */
    public SVGSVGElement getRootElement();
}
