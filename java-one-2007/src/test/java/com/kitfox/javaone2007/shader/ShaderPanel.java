/*
 * TexRectanglePanel.java
 *
 * Created on April 25, 2007, 8:59 PM
 */

package com.kitfox.javaone2007.shader;

import com.sun.opengl.util.BufferUtil;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author  kitfox
 */
public class ShaderPanel extends javax.swing.JPanel implements GLEventListener
{
    private GLCanvas canvas;
    
    int texId;
    ByteBuffer imgData;
    WritableRaster raster;

    int program;
    
    /** Creates new form TexRectanglePanel */
    public ShaderPanel()
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
        
        gl.glClearColor(0, 0, 0, 0);
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        {
            gl.glLoadIdentity();
            gl.glOrtho(0, 1, 0, 1, -1, 1);
        }
        gl.glMatrixMode(GL.GL_MODELVIEW);

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

        
        //-----------------------------------------------
        IntBuffer texBuf = BufferUtil.newIntBuffer(1);
        gl.glGenTextures(1, texBuf);
        texId = texBuf.get(0);
        imgData.rewind();
        
//        gl.glClientActiveTexture(GL.GL_TEXTURE0);

        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texId);
        gl.glTexImage2D(GL.GL_TEXTURE_RECTANGLE_ARB, 0, GL.GL_RGBA, 
                raster.getWidth(), raster.getHeight(), 
                0, GL.GL_RGBA, 
                GL.GL_UNSIGNED_BYTE, imgData);
        
        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);
        
//        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//        gl.glShadeModel(GL.GL_FLAT);
        
        //----------------------------------------------
        // Build shaders
        String[] lines;
        IntBuffer sizes;
        try
        {
            int vertShad = gl.glCreateShader(GL.GL_VERTEX_SHADER);
            lines = loadShader(getClass().getResource("/com/kitfox/javaone2007/shader/filter.vert").openStream());
            sizes = stringSizes(lines);
            gl.glShaderSource(vertShad, lines.length, lines, sizes);
            gl.glCompileShader(vertShad);
            System.err.println("***" + getLog(gl, vertShad));

            int fragShad = gl.glCreateShader(GL.GL_FRAGMENT_SHADER);
            lines = loadShader(getClass().getResource("/com/kitfox/javaone2007/shader/filter.frag").openStream());
            sizes = stringSizes(lines);
            gl.glShaderSource(fragShad, lines.length, lines, sizes);
            gl.glCompileShader(fragShad);
            System.err.println("***" + getLog(gl, fragShad));

            
            program = gl.glCreateProgram();
            gl.glAttachShader(program, vertShad);
            gl.glAttachShader(program, fragShad);
//            gl.glAttachObjectARB(program, vertShad);
//            gl.glAttachObjectARB(program, fragShad);
            gl.glLinkProgram(program);
            
            //Check log
            System.err.println("***" + getLog(gl, program));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        
    }
    
    public String getLog(GL gl, int handle)
    {
        IntBuffer ibuf = BufferUtil.newIntBuffer(1);
        gl.glGetObjectParameterivARB(handle, GL.GL_OBJECT_INFO_LOG_LENGTH_ARB, ibuf);
        int len = ibuf.get(0);
        if (len == 0) return "";
        
        ibuf.rewind();
        ByteBuffer bbuf = BufferUtil.newByteBuffer(len);
        gl.glGetInfoLogARB(program, len, ibuf, bbuf);
        
        bbuf.rewind();
        byte[] bytes = new byte[ibuf.get(0)];
        bbuf.get(bytes);
        return new String(bytes);
    }
    
    public void display(GLAutoDrawable gLAutoDrawable)
    {
        GL gl = gLAutoDrawable.getGL();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
/*
        IntBuffer ibuf = BufferUtil.newIntBuffer(3);
//        gl.glGetObjectParameterivARB(program, GL.GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB, ibuf);
//        int attrMaxLength = ibuf.get(0);
        gl.glGetObjectParameterivARB(program, GL.GL_OBJECT_ACTIVE_UNIFORMS_ARB, ibuf);
        int numUniformVar = ibuf.get(0);
        gl.glGetObjectParameterivARB(program, GL.GL_OBJECT_ACTIVE_UNIFORM_MAX_LENGTH_ARB, ibuf);
        int maxUniformNameLength = ibuf.get(0);
        //gl.glGetActiveUniform(program, 
        ByteBuffer buf = BufferUtil.newByteBuffer(maxUniformNameLength);
        IntBuffer ibufLen = BufferUtil.newIntBuffer(3);
        IntBuffer ibufSize = BufferUtil.newIntBuffer(3);
        IntBuffer ibufType = BufferUtil.newIntBuffer(3);
        for (int i = 0; i < numUniformVar; i++)
        {
            gl.glGetActiveUniform(program, i, maxUniformNameLength, ibufLen, ibufSize, ibufType, buf);
            buf.rewind();
            byte[] arr = new byte[ibufLen.get(0)];
            buf.get(arr);
            buf.rewind();
            String name = new String(arr);
        }
 */
        
//        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texId);
//        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        
        gl.glUseProgramObjectARB(program);
//        int sizeId = gl.glGetUniformLocation(program, "size");
//        int ableId = gl.glGetUniformLocation(program, "able");
//        int bakerId = gl.glGetUniformLocation(program, "baker");
//        int charlieId = gl.glGetUniformLocation(program, "charlie");
//        int deltaId = gl.glGetUniformLocation(program, "delta");
//        int echoId = gl.glGetUniformLocation(program, "echo");
//        gl.glUniform2f(sizeId, raster.getWidth(), raster.getHeight());
        int sourceMapId = gl.glGetUniformLocationARB(program, "sourcemap");
        gl.glUniform1i(sourceMapId, 0);
        
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        
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
        
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int i, int i0, int i1, int i2)
    {
    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean b, boolean b0)
    {
    }
    
    public String[] loadShader(InputStream is) throws IOException
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
