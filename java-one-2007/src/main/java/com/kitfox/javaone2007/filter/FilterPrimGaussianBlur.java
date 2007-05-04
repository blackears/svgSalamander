/*
 * FilterPrimTranslate.java
 *
 * Created on May 3, 2007, 2:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.util.HashMap;
import javax.media.opengl.GL;

/**
 *
 * @author kitfox
 */
public class FilterPrimGaussianBlur extends FilterPrimitive
{
    int[] registers = new int[1];
    private int regOut;
    
    private float stdDeviation;
    
    /** Creates a new instance of FilterPrimTranslate */
    public FilterPrimGaussianBlur()
    {
    }

    public int[] getRegInputs()
    {
        return registers.clone();
    }

    public int getRegIn1()
    {
        return registers[0];
    }

    public void setRegIn1(int value)
    {
        registers[0] = value;
    }
    
    public int getRegOut()
    {
        return regOut;
    }

    public void setRegOut(int regOut)
    {
        this.regOut = regOut;
    }

    public float getStdDeviation()
    {
        return stdDeviation;
    }

    public void setStdDeviation(float stdDeviation)
    {
        this.stdDeviation = stdDeviation;
    }

    void run(GL gl, HashMap<Integer, FilterImageView> allocBufs)
    {
    }
    
}
