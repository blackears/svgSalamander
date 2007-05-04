/*
 * FilterPrimTranslate.java
 *
 * Created on May 3, 2007, 2:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import com.kitfox.javaone2007.filter.opengl.TextureLibrary;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.media.opengl.GL;

/**
 *
 * @author kitfox
 */
public class FilterPrimOffset extends FilterPrimitive
{
    int[] registers = new int[1];
    private int regOut;
    
    private float dx;
    private float dy;
    
    /** Creates a new instance of FilterPrimTranslate */
    public FilterPrimOffset()
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

    public float getDx()
    {
        return dx;
    }

    public void setDx(float dx)
    {
        this.dx = dx;
    }

    public float getDy()
    {
        return dy;
    }

    public void setDy(float dy)
    {
        this.dy = dy;
    }

    void run(GL gl, HashMap<Integer, FilterImageView> allocBufs)
    {
        /*
        FilterImageView viewSource = allocBufs.get(registers[0]);
        FilterImageView viewDest = allocBufs.get(regOut);

        FilterTransform filtXform = viewDest.viewToRaster;
        
        //Find drawing regions in raster space
        Rectangle2D.Float rasterSrc = (Rectangle2D.Float)viewSource.viewArea.clone();
        filtXform.transform(rasterSrc);
        
        Rectangle2D.Float rasterDest = (Rectangle2D.Float)viewDest.viewArea.clone();
        filtXform.transform(rasterDest);
        
        
        int program = TextureLibrary.getInstance().getProgramName();
        gl.glUseProgramObjectARB(program);

        int uniSource = gl.glGetUniformLocationARB(program, "source");
        gl.glUniform1i(uniSource, 0);
        int uniDest = gl.glGetUniformLocationARB(program, "dest");
        gl.glUniform1i(uniDest, 1);
        int uniSrcRegion = gl.glGetUniformLocationARB(program, "srcRegion");
        gl.glUniform4f(uniSrcRegion, rasterSrc.x, rasterSrc.y, rasterSrc.width, rasterSrc.height);
        int uniDestRegion = gl.glGetUniformLocationARB(program, "destRegion");
        gl.glUniform4f(uniDestRegion, rasterDest.x, rasterDest.y, rasterDest.width, rasterDest.height);
        int uniDx = gl.glGetUniformLocationARB(program, "dx");
        gl.glUniform1f(uniDx, dx);
        int uniDy = gl.glGetUniformLocationARB(program, "dy");
        gl.glUniform1f(uniDx, dy);

        
        int srcId = TextureLibrary.getInstance().getTextureName(viewSource.backingRaster);
        int destId = TextureLibrary.getInstance().getTextureName(viewDest.backingRaster);

        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, srcId);
//        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
//        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);

        gl.glActiveTexture(GL.GL_TEXTURE1);
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, destId);
//        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
//        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);
        
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL.GL_QUADS);
        {
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(0, 0);
            gl.glTexCoord2f(raster.getWidth(), 0);
            gl.glVertex2f(1, 0);
            gl.glTexCoord2f(raster.getWidth(), raster.getHeight());
            gl.glVertex2f(1, 1);
            gl.glTexCoord2f(0, raster.getHeight());
            gl.glVertex2f(0, 1);
        }
        gl.glEnd();
        
        gl.glUseProgram(0);
        
//        gl.glFlush();
         */
    }
    
}
