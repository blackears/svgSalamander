/*
 * SVGString.java
 *
 * Created on April 12, 2007, 2:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg;

import com.kitfox.salamander.svg.basic.*;

/**
 * Used for attributes of basic type 'string'.
 * @author kitfox
 */
public interface DOMString extends SVGDataType
{
    /**
     * The value of the given attribute.
     */
    public String getValue();
    
}
