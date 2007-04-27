/*
 * FrameBufferTest.java
 *
 * Created on April 25, 2007, 4:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import com.sun.opengl.util.BufferUtil;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLPbuffer;

/**
 *
 * @author kitfox
 */
public class FrameBufferTest implements GLEventListener
{
    GLPbuffer pbuffer;
    
    /** Creates a new instance of FrameBufferTest */
    public FrameBufferTest(GLAutoDrawable drawable)
    {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(false);
        if (!GLDrawableFactory.getFactory().canCreateGLPbuffer())
        {
            throw new GLException("Pbuffers not supported with this graphics card");
        }
        pbuffer = GLDrawableFactory.getFactory().createGLPbuffer(caps,
                null,
//                                                             getWidth(),
//                                                             getHeight(),
                256,
                256,
                drawable.getContext());
        pbuffer.addGLEventListener(this);
        
        pbuffer.display();
    }

    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        
        IntBuffer ibuf = BufferUtil.newIntBuffer(1);
        gl.glGenFramebuffersEXT(1, ibuf);
        
        int frameBufId = ibuf.get(0);
        
        //-------------------------------
        //if framebuffer == 0, control reverts to main window
        gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, frameBufId);
        
        int texId = 0;
        gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT, 
                GL.GL_COLOR_ATTACHMENT0_EXT,
                GL.GL_TEXTURE_RECTANGLE_ARB,
                texId,
                0); //Mipmap level
        
        switch (gl.glCheckFramebufferStatusEXT(frameBufId))
        {
            case GL.GL_FRAMEBUFFER_COMPLETE_EXT:
                //Ok
                break;
            case GL.GL_FRAMEBUFFER_UNSUPPORTED_EXT:
                //Framebuffers not supported
                break;
            default:
                //Handle error code
                break;
            /*
            case GL.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_DUPLICATE_ATTACHMENT_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
                //Framebuffers not supported
                break;
            case GL.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
                //Framebuffers not supported
                break;
             */
        }
        
        
        //-------------------------------
        ibuf.put(0, frameBufId);
        gl.glDeleteFramebuffersEXT(1, ibuf);
    }

    public void display(GLAutoDrawable drawable)
    {
    }

    public void reshape(GLAutoDrawable drawable, int i, int i0, int i1, int i2)
    {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean b, boolean b0)
    {
    }
    
}
