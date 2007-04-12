/*
 * SVGException.java
 *
 * Created on April 12, 2007, 1:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg;

/**
 *
 * @author kitfox
 */
public class SVGException extends java.lang.Exception
{
    
    /**
     * Creates a new instance of <code>SVGException</code> without detail message.
     */
    public SVGException()
    {
    }
    
    
    /**
     * Constructs an instance of <code>SVGException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SVGException(String msg)
    {
        super(msg);
    }
}
