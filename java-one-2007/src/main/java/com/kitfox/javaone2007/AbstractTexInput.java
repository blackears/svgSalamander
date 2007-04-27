/*
 * AbstractTexInput.java
 *
 * Created on April 24, 2007, 11:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author kitfox
 */
public class AbstractTexInput implements TexInput
{
    ArrayList<TexInputListener> listeners = new ArrayList<TexInputListener>();
    
    /** Creates a new instance of AbstractTexInput */
    public AbstractTexInput()
    {
    }

    public Rectangle getImageRegion()
    {
        return null;
    }

    public int getWidthPow2()
    {
        return 0;
    }

    public int getHeightPow2()
    {
        return 0;
    }

    public ByteBuffer getData()
    {
        return null;
    }

    public void addTexInputListener(TexInputListener listener)
    {
        listeners.add(listener);
    }
    
}
