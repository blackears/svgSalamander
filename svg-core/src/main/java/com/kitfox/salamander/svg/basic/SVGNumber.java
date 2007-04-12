/*
 * SVGNumber.java
 *
 * Created on April 12, 2007, 2:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * Used for attributes of basic type 'number'.
 * @author kitfox
 */
public interface SVGNumber extends SVGDataType
{
    /**
     * The value of the given attribute.
     */
    public float getValue();
}
