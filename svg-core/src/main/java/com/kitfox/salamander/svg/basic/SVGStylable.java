/*
 * SVGStylable.java
 *
 * Created on April 12, 2007, 3:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMString;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

/**
 *
 * @author kitfox
 */
public interface SVGStylable
{
    /**
     * Corresponds to attribute class on the given element.
     */
    public SVGAnimatedString getClassName();
    /**
     * Corresponds to attribute style on the given element. If the user agent does not support styling with CSS, then this attribute must always have the value of null.
     */
    public CSSStyleDeclaration getStyle();
    /**
     * Returns the base (i.e., static) value of a given presentation attribute as an object of type CSSValue. The returned object is live; changes to the objects represent immediate changes to the objects to which the CSSValue is attached.
     * @param name Retrieves a "presentation attribute" by name.
     * @return The static/base value of the given presentation attribute as a CSSValue, or NULL if the given attribute does not have a specified value.
     */
    public CSSValue getPresentationAttribute(DOMString name);
}
