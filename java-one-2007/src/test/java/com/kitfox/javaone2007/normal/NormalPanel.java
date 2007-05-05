/*
 * TexRectanglePanel.java
 *
 * Created on April 25, 2007, 8:59 PM
 */

package com.kitfox.javaone2007.normal;

import com.sun.opengl.util.BufferUtil;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
public class NormalPanel extends javax.swing.JPanel implements GLEventListener
{
    private GLCanvas canvas;
    
    int texColorId;
    int texNormalId;
    int width;
    int height;

    int program;
    
    int mouseX = 0;
    int mouseY = 0;
    boolean highlight;
    
    /** Creates new form TexRectanglePanel */
    public NormalPanel()
    {
        initComponents();
        
    }
    
    public void addNotify()
    {
        super.addNotify();
        canvas = new GLCanvas(new GLCapabilities());
        canvas.addGLEventListener(this);
        
        add(canvas, BorderLayout.CENTER);
        
        canvas.addMouseListener(
                new MouseListener()
        {
            public void mouseClicked(MouseEvent evt)
            {
            }

            public void mousePressed(MouseEvent evt)
            {
                int mod = evt.getModifiersEx();
                if ((mod & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK)
                {
                    updateLightPosition(evt);
                }
                if ((mod & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)
                {
                    highlight = true;
//                    System.err.println("Flame On");
                    canvas.repaint();
                }
            }

            public void mouseReleased(MouseEvent evt)
            {
                int mod = evt.getModifiersEx();
                if ((mod & MouseEvent.BUTTON1_DOWN_MASK) != MouseEvent.BUTTON1_DOWN_MASK)
                {
                    highlight = false;
//                    System.err.println("Flame Off");
                    canvas.repaint();
                }
            }

            public void mouseEntered(MouseEvent evt)
            {
            }

            public void mouseExited(MouseEvent evt)
            {
            }
        }
                );
        
        canvas.addMouseMotionListener(
                new MouseMotionListener()
                {
                    public void mouseDragged(MouseEvent evt)
                    {
                        int mod = evt.getModifiersEx();
                        if ((mod & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK)
                        {
                            updateLightPosition(evt);
                        }
                    }

                    public void mouseMoved(MouseEvent evt)
                    {
                    }
                }
                );
    }

    private void updateLightPosition(MouseEvent evt)
    {
        mouseX = width * evt.getX() / canvas.getWidth();
        mouseY = height * (canvas.getHeight() - evt.getY()) / canvas.getHeight();
        canvas.repaint();
    }

    public void init(GLAutoDrawable gLAutoDrawable)
    {
        ByteBuffer imgDataColor;
        ByteBuffer imgDataNormal;
        WritableRaster raster;
    
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
            URL url = getClass().getResource("/com/kitfox/javaone2007/abc_alpha.png");
            BufferedImage img = ImageIO.read(url);
            
            raster = img.getRaster();
            width = raster.getWidth();
            height = raster.getHeight();
            
            mouseX = width / 2;
            mouseY = height / 2;
        
            
            imgDataColor = BufferUtil.newByteBuffer(raster.getWidth() * raster.getHeight() * 4);
            for (int j = raster.getHeight() - 1; j >= 0; j--)
            {
                for (int i = 0; i < raster.getWidth(); i++)
                {
                    imgDataColor.put((byte)raster.getSample(i, j, 0));
                    imgDataColor.put((byte)raster.getSample(i, j, 1));
                    imgDataColor.put((byte)raster.getSample(i, j, 2));
                    imgDataColor.put((byte)raster.getSample(i, j, 3));
                }
            }
            
            url = getClass().getResource("/com/kitfox/javaone2007/abc_alpha_normal.png");
            img = ImageIO.read(url);
            
            raster = img.getRaster();
            
            imgDataNormal = BufferUtil.newByteBuffer(raster.getWidth() * raster.getHeight() * 4);
            for (int j = raster.getHeight() - 1; j >= 0; j--)
            {
                for (int i = 0; i < raster.getWidth(); i++)
                {
                    imgDataNormal.put((byte)raster.getSample(i, j, 0));
                    imgDataNormal.put((byte)raster.getSample(i, j, 1));
                    imgDataNormal.put((byte)raster.getSample(i, j, 2));
                    imgDataNormal.put((byte)raster.getSample(i, j, 3));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        
        //-----------------------------------------------
        IntBuffer texBuf = BufferUtil.newIntBuffer(2);
        gl.glGenTextures(2, texBuf);
        texColorId = texBuf.get(0);
        texNormalId = texBuf.get(1);

        imgDataColor.rewind();
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texColorId);
        gl.glTexImage2D(GL.GL_TEXTURE_RECTANGLE_ARB, 0, GL.GL_RGBA, 
                raster.getWidth(), raster.getHeight(), 
                0, GL.GL_RGBA, 
                GL.GL_UNSIGNED_BYTE, imgDataColor);
        
        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_RECTANGLE_ARB, GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);
        
        
        imgDataNormal.rewind();
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glActiveTexture(GL.GL_TEXTURE1);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texNormalId);
        gl.glTexImage2D(GL.GL_TEXTURE_RECTANGLE_ARB, 0, GL.GL_RGBA, 
                raster.getWidth(), raster.getHeight(), 
                0, GL.GL_RGBA, 
                GL.GL_UNSIGNED_BYTE, imgDataNormal);
        
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
            lines = loadShader(getClass().getResource("/com/kitfox/javaone2007/shader/normalShader.frag").openStream());
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
        
        gl.glUseProgramObjectARB(program);
        gl.glUniform1i(
                gl.glGetUniformLocationARB(program, "sourceColor"),
                0);
        gl.glUniform1i(
                gl.glGetUniformLocationARB(program, "sourceNormal"),
                1);
        //System.err.println("Highlight: " + highlight);
        gl.glUniform1i(
                gl.glGetUniformLocationARB(program, "highlight"),
                highlight ? 1 : 0);
        
        if (check_directional.isSelected())
        {
            gl.glUniform4f(
                    gl.glGetUniformLocationARB(program, "lightPosition"),
                    mouseX - (width / 2), (height / 2) - mouseY, slider_depth.getValue(), 0);
        }
        else
        {
            gl.glUniform4f(
                    gl.glGetUniformLocationARB(program, "lightPosition"),
                    mouseX, mouseY, slider_depth.getValue(), 1);
        }
        
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texColorId);
        
        gl.glActiveTexture(GL.GL_TEXTURE1);
        gl.glEnable(GL.GL_TEXTURE_RECTANGLE_ARB);
        gl.glBindTexture(GL.GL_TEXTURE_RECTANGLE_ARB, texNormalId);
        
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL.GL_QUADS);
        {
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(0, 0);
            gl.glTexCoord2f(width, 0);
            gl.glVertex2f(1, 0);
            gl.glTexCoord2f(width, height);
            gl.glVertex2f(1, 1);
            gl.glTexCoord2f(0, height);
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
        jPanel1 = new javax.swing.JPanel();
        check_directional = new javax.swing.JCheckBox();
        slider_depth = new javax.swing.JSlider();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        check_directional.setSelected(true);
        check_directional.setText("Directional");
        check_directional.setToolTipText("Toggle between point and directional lights");
        check_directional.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        check_directional.setMargin(new java.awt.Insets(0, 0, 0, 0));
        check_directional.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                check_directionalActionPerformed(evt);
            }
        });

        jPanel1.add(check_directional, java.awt.BorderLayout.WEST);

        slider_depth.setMaximum(200);
        slider_depth.setToolTipText("Height of light above canvas");
        slider_depth.setValue(100);
        slider_depth.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                slider_depthStateChanged(evt);
            }
        });

        jPanel1.add(slider_depth, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

    private void check_directionalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_check_directionalActionPerformed
    {//GEN-HEADEREND:event_check_directionalActionPerformed
        if (canvas != null)
        {
            canvas.repaint();
        }
    }//GEN-LAST:event_check_directionalActionPerformed

    private void slider_depthStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_slider_depthStateChanged
    {//GEN-HEADEREND:event_slider_depthStateChanged
        if (canvas != null)
        {
            canvas.repaint();
        }
    }//GEN-LAST:event_slider_depthStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox check_directional;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider slider_depth;
    // End of variables declaration//GEN-END:variables
    
}
