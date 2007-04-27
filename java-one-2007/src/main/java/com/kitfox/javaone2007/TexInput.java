/*
 * TexInput.java
 *
 * Created on April 24, 2007, 11:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.awt.Rectangle;
import java.nio.ByteBuffer;

/**
 * Implementing classes provide data for images.
 * @author kitfox
 */
public interface TexInput
{
    public Rectangle getImageRegion();
    
    public int getWidthPow2();
    
    public int getHeightPow2();
    
    /**
     * Byte buffer of RGBA data that fills widthPow2 x heightPow2 rectangle
     */
    public ByteBuffer getData();
    
    public void addTexInputListener(TexInputListener listener);
}
