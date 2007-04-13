/*
 * SVGColorProfileRule.java
 *
 * Created on April 13, 2007, 10:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.color;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGCSSRule;
import com.kitfox.salamander.svg.basic.SVGRenderingIntent;

/**
 * 
 * The SVGColorProfileRule interface represents an @color-profile rule in a CSS style sheet. An @color-profile rule identifies a ICC profile which can be referenced within a given document.
 * 
 * Support for the SVGColorProfileRule interface is only required in user agents that support styling with CSS.
 * @author kitfox
 */
public interface SVGColorProfileRule extends SVGCSSRule,
        SVGRenderingIntent
{
    /**
     * Corresponds to property src within an @color-profile rule.
     */
    public DOMString getSrc();
    /**
     * Corresponds to property name within an @color-profile rule.
     */
    public DOMString getName();
    /**
     * The type of rendering intent, identified by one of the SVGRenderingIntent constants.
     */
    public SVGRenderingIntent getRenderingIntent();
    
}
