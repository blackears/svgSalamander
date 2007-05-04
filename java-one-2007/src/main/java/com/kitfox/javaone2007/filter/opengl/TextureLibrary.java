/*
 * TextureLibrary.java
 *
 * Created on May 4, 2007, 9:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter.opengl;

/**
 *
 * @author kitfox
 */
public class TextureLibrary
{
    static final TextureLibrary singleton = new TextureLibrary();
    
    /** Creates a new instance of TextureLibrary */
    private TextureLibrary()
    {
    }
    
    public static TextureLibrary getInstance()
    {
        return singleton;
    }
}
