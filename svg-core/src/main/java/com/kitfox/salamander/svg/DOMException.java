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
public class DOMException extends java.lang.Exception
{
    
    /**
     * Creates a new instance of <code>SVGException</code> without detail message.
     */
    public DOMException()
    {
    }
    
    
    /**
     * Constructs an instance of <code>SVGException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DOMException(String msg)
    {
        super(msg);
    }
}
