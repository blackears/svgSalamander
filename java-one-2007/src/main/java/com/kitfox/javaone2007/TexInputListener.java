/*
 * InputListener.java
 *
 * Created on April 24, 2007, 11:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.util.EventListener;

/**
 *
 * @author kitfox
 */
public interface TexInputListener extends EventListener
{
    public void textureResized();
    public void textureDataChanged();
}
