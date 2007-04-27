/*
 * TexRectanglePanel.java
 *
 * Created on April 25, 2007, 8:59 PM
 */

package com.kitfox.javaone2007.frameBufferObject;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author  kitfox
 */
public class FBOPanel extends javax.swing.JPanel implements GLEventListener
{
    private GLCanvas canvas;
    
//    ByteBuffer imgData;
//    WritableRaster raster;
    
    int frameBufferId;
    int depthId;
    int texId;
    
    int destWidth = 640;
    int destHeight = 480;
    
    /** Creates new form TexRectanglePanel */
    public FBOPanel()
    {
        initComponents();
        
    }
    
    public void addNotify()
    {
        super.addNotify();
        canvas = new GLCanvas(new GLCapabilities());
        canvas.addGLEventListener(this);
        
        add(canvas, BorderLayout.CENTER);
    }
    

    public void init(GLAutoDrawable gLAutoDrawable)
    {
        GL gl = gLAutoDrawable.getGL();

        //Load 
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        
        //-----------------------------------------------
        IntBuffer ibuf = BufferUtil.newIntBuffer(1);
        
        gl.glGenFramebuffersEXT(1, ibuf);
        frameBufferId = ibuf.get(0);
        ibuf.rewind();
        gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, frameBufferId);
        
//        gl.glGenRenderbuffersEXT(1, ibuf);
//        depthId = ibuf.get(0);
//        ibuf.rewind();
//        gl.glBindRenderbufferEXT(GL.GL_RENDERBUFFER_EXT, depthId);
//        gl.glRenderbufferStorageEXT(GL.GL_RENDERBUFFER_EXT, GL.GL_DEPTH_COMPONENT,
//                destWidth, destHeight);
        
        gl.glGenTextures(1, ibuf);
        texId = ibuf.get(0);
        ibuf.rewind();
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texId);
        ByteBuffer imgData = BufferUtil.newByteBuffer(destWidth * destHeight * 4);
        gl.glTexImage2D(GL.GL_TEXTURE_RECTANGLE_ARB, 0, GL.GL_RGBA, 
                destWidth, destHeight, 
                0, GL.GL_RGBA, 
//                GL.GL_UNSIGNED_BYTE, 0);
                GL.GL_UNSIGNED_BYTE, imgData);
        
        //Attach texture back buffer
        gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT, 
                GL.GL_COLOR_ATTACHMENT0_EXT, GL.GL_TEXTURE_RECTANGLE_ARB, texId, 0);
        

//        gl.glFramebufferRenderbufferEXT(GL.GL_FRAMEBUFFER_EXT, 
//                GL.GL_DEPTH_ATTACHMENT_EXT, GL.GL_RENDERBUFFER_EXT, depthId);
        
        
        
        //Create empty texture
        
        int status = gl.glCheckFramebufferStatusEXT (GL.GL_FRAMEBUFFER_EXT);
        switch (status)
        {
        case GL.GL_FRAMEBUFFER_COMPLETE_EXT:
                System.out.println("FrameBuffer Created");
            break;
        case GL.GL_FRAMEBUFFER_UNSUPPORTED_EXT:
            System.out.println("FBO configuration unsupported");
            break;
        default:
            System.out.println( "FBO programmer error" );
            break;
        }        

	gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0);	// Unbind the FBO for now

        initAbc(gl);
    }
    
    int texAbcId;
    int texAbcWidth;
    int texAbcHeight;
    
    public void initAbc(GL gl)
    {
        WritableRaster raster;
        ByteBuffer imgData;
        try
        {
            URL url = getClass().getResource("/com/kitfox/javaone2007/abc.png");
            BufferedImage img = ImageIO.read(url);
            
            raster = img.getRaster();
            
            imgData = BufferUtil.newByteBuffer(raster.getWidth() * raster.getHeight() * 4);
            for (int j = raster.getHeight() - 1; j >= 0; j--)
            {
                for (int i = 0; i < raster.getWidth(); i++)
                {
                    imgData.put((byte)raster.getSample(i, j, 0));
                    imgData.put((byte)raster.getSample(i, j, 1));
                    imgData.put((byte)raster.getSample(i, j, 2));
                    imgData.put((byte)raster.getSample(i, j, 3));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        
        IntBuffer texBuf = BufferUtil.newIntBuffer(1);
        gl.glGenTextures(1, texBuf);
        texAbcId = texBuf.get(0);
        imgData.rewind();
        texAbcWidth = raster.getWidth();
        texAbcHeight = raster.getHeight();
        
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texAbcId);
        gl.glTexImage2D(GL.GL_TEXTURE_RECTANGLE_ARB, 0, GL.GL_RGBA, 
                texAbcWidth, texAbcHeight, 
                0, GL.GL_RGBA, 
                GL.GL_UNSIGNED_BYTE, imgData);
        
//        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, 0);
    }

    public void display(GLAutoDrawable gLAutoDrawable)
    {
        GL gl = gLAutoDrawable.getGL();
        
//        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, 0);
        gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, frameBufferId);
//        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texId);
        gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);

        
        gl.glViewport(0, 0, destWidth, destHeight);
        
gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        gl.glClearColor(0, 0, 0, 0);
//        gl.glClearColor(1, 1, 1, 1);
//        gl.glClearColor(1, 1, 0, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//gl.glShadeModel(GL.GL_SMOOTH);
//gl.glClearDepth(1.0f);					
//gl.glEnable(GL.GL_DEPTH_TEST);			
//gl.glDepthFunc(GL.GL_LEQUAL);				
  
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, 0);
//        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
//        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
//        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
//        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND);
        

        
        
//        gl.glEnable(GL.GL_BLEND);
//        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//        gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_ZERO);
//        gl.glBlendFunc(GL.GL_ZERO, GL.GL_DST_COLOR);
//        gl.glShadeModel(GL.GL_FLAT);
        
        
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        {
            gl.glLoadIdentity();
            gl.glFrustum(-1, 1, -1, 1, 1.5, 20);
//            gl.glOrtho(0, 1, 0, 1, -1, 1);
        }
        gl.glMatrixMode(GL.GL_MODELVIEW);
        
        /*
        gl.glColor3f(0, 0, 1);
        gl.glBegin(GL.GL_POLYGON);
        {
//            gl.glVertex3f(-1, -1, 0);
//            gl.glVertex3f(1, -1, 0);
//            gl.glVertex3f(1, 1, 0);
//            gl.glVertex3f(-1, 1, 0);
            gl.glVertex3f(.25f, .25f, 0);
            gl.glVertex3f(.75f, .25f, 0);
            gl.glVertex3f(.75f, .75f, 0);
            gl.glVertex3f(.25f, .75f, 0);
        }
        gl.glEnd();
         */
        gl.glColor3f(0, 1, 1);
        gl.glLoadIdentity();
        
        GLU glu = new GLU();
        glu.gluLookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
        gl.glScalef(1.0f, 2.0f, 1.0f);
        GLUT glut = new GLUT();
        glut.glutWireCube(1);
//        glut.glutSolidCube(1);
         
        
        //-----------------------
        // Draw rasters
        gl.glMatrixMode(GL.GL_PROJECTION);
        {
            gl.glLoadIdentity();
            gl.glOrtho(0, 1, 0, 1, -1, 1);
        }
        gl.glMatrixMode(GL.GL_MODELVIEW);
            gl.glLoadIdentity();

        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glShadeModel(GL.GL_FLAT);

        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texAbcId);
        
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL.GL_QUADS);
        {
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(0, 0);
            gl.glTexCoord2f(texAbcWidth, 0);
            gl.glVertex2f(.5f, 0);
            gl.glTexCoord2f(texAbcWidth, texAbcHeight);
            gl.glVertex2f(.5f, .5f);
            gl.glTexCoord2f(0, texAbcHeight);
            gl.glVertex2f(0, .5f);
        }
        gl.glEnd();
        
        /*
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
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
        */
        
        gl.glFlush();

        gl.glPopAttrib();
        gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0);
        
        //Save image
        ByteBuffer imgData = BufferUtil.newByteBuffer(destWidth * destHeight * 4);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texId);
        gl.glGetTexImage(GL.GL_TEXTURE_RECTANGLE_ARB, 
                0, //Mipmap level
                GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
                imgData);
        
        BufferedImage img = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_ARGB);
        imgData.rewind();
        WritableRaster raster = img.getRaster();
        for (int j = raster.getHeight() - 1; j >= 0; j--)
        {
            for (int i = 0; i < raster.getWidth(); i++)
            {
                raster.setSample(i, j, 0, imgData.get());
                raster.setSample(i, j, 1, imgData.get());
                raster.setSample(i, j, 2, imgData.get());
                raster.setSample(i, j, 3, imgData.get());
            }
        }

        try
        {
            ImageIO.write(img, "png", new File("fboImage.png"));
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        gl = gl;
        
        /*
        //-------------------------------------------
        try
        {
            URL url = getClass().getResource("/com/kitfox/javaone2007/abc.png");
            BufferedImage img = ImageIO.read(url);
            
            raster = img.getRaster();
            
            imgData = BufferUtil.newByteBuffer(raster.getWidth() * raster.getHeight() * 4);
            for (int j = raster.getHeight() - 1; j >= 0; j--)
            {
                for (int i = 0; i < raster.getWidth(); i++)
                {
                    imgData.put((byte)raster.getSample(i, j, 0));
                    imgData.put((byte)raster.getSample(i, j, 1));
                    imgData.put((byte)raster.getSample(i, j, 2));
                    imgData.put((byte)raster.getSample(i, j, 3));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        imgData.rewind();
        
         */
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int i, int i0, int i1, int i2)
    {
    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean b, boolean b0)
    {
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.BorderLayout());

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
