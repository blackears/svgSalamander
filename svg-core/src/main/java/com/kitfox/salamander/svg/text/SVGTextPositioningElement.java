/*
 * SVGTextPositioningElement.java
 *
 * Created on April 13, 2007, 9:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.text;

import com.kitfox.salamander.svg.basic.SVGAnimatedLengthList;
import com.kitfox.salamander.svg.basic.SVGAnimatedNumberList;

/**
 * The SVGTextPositioningElement  interface is inherited by text-related interfaces: SVGTextElement, SVGTSpanElement, SVGTRefElement and SVGAltGlyphElement.
 * @author kitfox
 */
public interface SVGTextPositioningElement extends SVGTextContentElement
{
    /**
     * Corresponds to attribute x on the given element.
     */
    public SVGAnimatedLengthList getX();
    /**
     * Corresponds to attribute y on the given element.
     */
    public SVGAnimatedLengthList getY();
    /**
     * Corresponds to attribute dx on the given element.
     */
    public SVGAnimatedLengthList getDx();
    /**
     * Corresponds to attribute dy on the given element.
     */
    public SVGAnimatedLengthList getDy();
    /**
     * Corresponds to attribute rotate on the given element.
     */
    public SVGAnimatedNumberList getRotate();
    
}
