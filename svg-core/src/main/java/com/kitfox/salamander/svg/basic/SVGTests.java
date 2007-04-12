/*
 * SVGTests.java
 *
 * Created on April 12, 2007, 3:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMString;

/**
 * 
 * Interface SVGTests  defines an interface which applies to all elements which have attributes requiredFeatures, requiredExtensions and systemLanguage.
 * @author kitfox
 */
public interface SVGTests
{
    /**
     * Corresponds to attribute requiredFeatures on the given element.
     */
    public SVGStringList getRequiredFeatures();
    /**
     * Corresponds to attribute requiredExtensions on the given element.
     */
    public SVGStringList getRequiredExtensions();
    /**
     * Corresponds to attribute systemLanguage on the given element.
     */
    public SVGStringList getSystemLanguage();
    /**
     * Returns true if the user agent supports the given extension, specified by a URI.
     * @param extension The name of the extension, expressed as a URI.
     * @return True or false, depending on whether the given extension is supported.
     */
    public boolean hasExtension(DOMString extension);
}
