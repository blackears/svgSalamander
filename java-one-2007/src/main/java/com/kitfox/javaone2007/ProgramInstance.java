/*
 * ProgramInstnace.java
 *
 * Created on April 24, 2007, 9:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.media.opengl.GL;

/**
 *
 * @author kitfox
 */
public class ProgramInstance
{
    HashMap<Integer, TexInput> inputs = new HashMap<Integer, TexInput>();
    private TexOutput output = new TexOutput();
    private int width;
    private int height;
    
    private Program program;
    
    /** Creates a new instance of ProgramInstnace */
    public ProgramInstance()
    {
    }
    
    public void addInput(int index, TexInput input)
    {
        inputs.put(index, input);
    }
    
    public TexInput getInput(int index)
    {
        return inputs.get(index);
    }
    
    public Set<Integer> getInputIndicies()
    {
        return Collections.unmodifiableSet(inputs.keySet());
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public TexOutput getOutput()
    {
        return output;
    }

    void setupAttributes(GL gl, ShaderBuffer shaderBuffer)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
