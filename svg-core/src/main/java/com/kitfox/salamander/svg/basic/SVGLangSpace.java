/*
 * SVGLangSpace.java
 *
 * Created on April 12, 2007, 3:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Interface SVGLangSpace  defines an interface which applies to all elements which have attributes xml:lang and xml:space.
 * @author kitfox
 */
public interface SVGLangSpace
{
    /**
     * Corresponds to attribute xml:lang on the given element.
     */
    public SVGString getXmllang();
    /**
     * Corresponds to attribute xml:space on the given element.
     */
    public SVGString getXmlspace();
}
