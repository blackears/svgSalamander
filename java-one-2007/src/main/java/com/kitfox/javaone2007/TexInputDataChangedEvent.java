/*
 * TexInputDataChangedEvent.java
 *
 * Created on April 24, 2007, 11:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.awt.Rectangle;

/**
 *
 * @author kitfox
 */
public class TexInputDataChangedEvent extends TexInputEvent
{
    Rectangle region;
    
    /** Creates a new instance of TexInputDataChangedEvent */
    public TexInputDataChangedEvent(TexInput source, Rectangle region)
    {
        super(source);
        this.region = region;
    }
    
}
