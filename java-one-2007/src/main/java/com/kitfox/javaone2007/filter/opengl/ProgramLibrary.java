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
public class ProgramLibrary
{
    static final ProgramLibrary singleton = new ProgramLibrary();
    
    /** Creates a new instance of TextureLibrary */
    private ProgramLibrary()
    {
    }
    
    public static ProgramLibrary getInstance()
    {
        return singleton;
    }
}
