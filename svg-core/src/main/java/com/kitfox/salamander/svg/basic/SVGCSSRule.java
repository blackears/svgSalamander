/*
 * SVGCSSRule.java
 *
 * Created on April 12, 2007, 3:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import org.w3c.dom.css.CSSRule;

/**
 * 
 * SVG extends interface CSSRule with interface SVGCSSRule by adding an SVGColorProfileRule rule to allow for specification of ICC-based color.
 * 
 * It is likely that this extension will become part of a future version of CSS and DOM.
 * @author kitfox
 */
public interface SVGCSSRule extends CSSRule
{
    /**
     * The rule is an @color-profile.
     */
    public static final short COLOR_PROFILE_RULE = 7;
}
