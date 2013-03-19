/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kitfox.svg.xml;

import java.util.HashMap;

/**
 *
 * @author kitfox
 */
public class StyleSheet
{
    HashMap ruleMap = new HashMap();

    public void addStyleRule(StyleSheetRule rule, String value)
    {
        ruleMap.put(rule, value);
    }
    
    public boolean getStyle(StyleAttribute attrib, String tagName, String cssClass)
    {
        StyleSheetRule rule = new StyleSheetRule(attrib.getName(), tagName, cssClass);
        String value = (String)ruleMap.get(rule);
        
        if (value != null)
        {
            attrib.setStringValue(value);
            return true;
        }
        
        //Try again using just class name
        rule = new StyleSheetRule(attrib.getName(), null, cssClass);
        value = (String)ruleMap.get(rule);
        
        if (value != null)
        {
            attrib.setStringValue(value);
            return true;
        }
        
        //Try again using just tag name
        rule = new StyleSheetRule(attrib.getName(), tagName, null);
        value = (String)ruleMap.get(rule);
        
        if (value != null)
        {
            attrib.setStringValue(value);
            return true;
        }
        
        return false;
    }
    
}
