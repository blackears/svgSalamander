/*
 * TexInputEvent.java
 *
 * Created on April 24, 2007, 11:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.util.EventObject;

/**
 *
 * @author kitfox
 */
public class TexInputEvent extends EventObject
{
    
    /** Creates a new instance of TexInputEvent */
    public TexInputEvent(TexInput source)
    {
        super(source);
    }
    
    public TexInput getSource()
    {
        return (TexInput)super.getSource();
    }
}
