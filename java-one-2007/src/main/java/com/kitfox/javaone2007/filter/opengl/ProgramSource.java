/*
 * ProgramSource.java
 *
 * Created on May 3, 2007, 11:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter.opengl;

import com.sun.opengl.util.BufferUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

/**
 *
 * @author kitfox
 */
public class ProgramSource
{
    public static enum Type { 
        VERTEX
        {
            public int getGlType()
            {
                return GL.GL_VERTEX_SHADER;
            }
        }, 
        FRAGMENT
        {
            public int getGlType()
            {
                return GL.GL_FRAGMENT_SHADER;
            }
        };
        
        abstract public int getGlType();
    };
    private final Type type;
    private final URL source;
    
    /** Creates a new instance of ProgramSource */
    public ProgramSource(Type type, URL source)
    {
        this.type = type;
        this.source = source;
    }

    int load(GL gl) throws IOException
    {
        int shaderName = gl.glCreateShader(type.getGlType());
        String[] lines = loadShader(source.openStream());
        IntBuffer sizes = stringSizes(lines);
        gl.glShaderSource(shaderName, lines.length, lines, sizes);
        gl.glCompileShader(shaderName);
        return shaderName;
    }
    
    private String[] loadShader(InputStream is) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(is);
        
        BufferedReader br = new BufferedReader(reader);
     
        ArrayList<String> lines = new ArrayList<String>();
        for (String line = br.readLine(); line != null; line = br.readLine())
        {
            lines.add(line);
        }
        
        return lines.toArray(new String[lines.size()]);
    }

    private IntBuffer stringSizes(String[] lines)
    {
        IntBuffer buf = BufferUtil.newIntBuffer(lines.length);
        for (String line: lines)
        {
            buf.put(line.length());
        }
        buf.rewind();
        return buf;
    }

    public Type getType()
    {
        return type;
    }

    public URL getSource()
    {
        return source;
    }
    
}
