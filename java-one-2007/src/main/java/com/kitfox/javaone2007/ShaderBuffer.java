/*
 * ShaderBuffer.java
 *
 * Created on April 23, 2007, 11:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007;

import com.sun.opengl.util.BufferUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.WeakHashMap;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLPbuffer;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author kitfox
 */
public class ShaderBuffer implements GLEventListener
{
    class TexInputInfo
    {
        final WeakReference<TexInput> ref;
        final int glName;
        
        TexInputInfo(TexInput input, int glName)
        {
            this.ref = new WeakReference(input);
            this.glName = glName;
        }

        public int getGlName()
        {
            return glName;
        }
        
        public TexInput getTexInput()
        {
            return ref.get();
        }
    }
    
    class ProgramInfo
    {
        final WeakReference<Program> ref;
        private final int glNameProg;
        
        ProgramInfo(Program program, int glNameProg)
        {
            this.ref = new WeakReference(program);
            this.glNameProg = glNameProg;
        }

        public int getGlNameProg()
        {
            return glNameProg;
        }
        
        public Program getProgram()
        {
            return ref.get();
        }
    }
    
    GLPbuffer pbuffer;
    
    WeakHashMap<TexInput, TexInputInfo> texInputMap = new WeakHashMap<TexInput, TexInputInfo>();
    //Keep a hard pointer to each TexInputInfo
    HashSet<TexInputInfo> texInfoCache = new HashSet<TexInputInfo>();
    
    WeakHashMap<Program, ProgramInfo> programMap = new WeakHashMap<Program, ProgramInfo>();
    //Keep a hard pointer to each TexInputInfo
    HashSet<ProgramInfo> progInfoCache = new HashSet<ProgramInfo>();
    
    LinkedList<ProgramInstance> progQueue = new LinkedList<ProgramInstance>();
    
    
    /** Creates a new instance of ShaderBuffer */
    public ShaderBuffer(GLAutoDrawable drawable)
    {
        initGL(drawable);
    }
    
    
    private void initGL(GLAutoDrawable drawable)
    {
        
        GLCapabilities caps = new GLCapabilities();
//        caps.setPbufferRenderToTexture(true);
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
        
//        canvas.display();
        pbuffer.display();
    }
    
//    public int addProgram(Program program)
//    {
//        int idx = programIdx++;
//        programs.put(idx, program);
//        return idx;
//    }
    
    public void queueProgram(ProgramInstance program)
    {
        synchronized (progQueue)
        {
            int maxWidth = Math.max(program.getWidth(), pbuffer.getWidth());
            int maxHeight = Math.max(program.getHeight(), pbuffer.getHeight());
            if (maxWidth > pbuffer.getWidth() ||
                    maxHeight > pbuffer.getHeight())
            {
                pbuffer.setSize(maxWidth, maxHeight);
            }
            
            progQueue.addFirst(program);
        }
        //if (program
    }
    
    public void runPrograms()
    {
        pbuffer.display();
    }
    
//    public Program removeProgram(int index)
//    {
//        return programs.remove(index);
//    }
    
    //--------------------------------
    
    public void init(GLAutoDrawable gLAutoDrawable)
    {
        System.err.println("Pbuffer init");

        GL gl = gLAutoDrawable.getGL();
        
        gl.glClearColor(0, 0, 0, 0);
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        GLU glu = new GLU();
        glu.gluOrtho2D(0, 1, 0, 1);

        gl.glMatrixMode(GL.GL_MODELVIEW);
    }
    
    public void display(GLAutoDrawable gLAutoDrawable)
    {
        GL gl = gLAutoDrawable.getGL();
        
        synchronized (progQueue)
        {
            while (!progQueue.isEmpty())
            {
                ProgramInstance programInst = progQueue.removeLast();
                
                for (Integer index: programInst.getInputIndicies())
                {
                    TexInput texInput = programInst.getInput(index);
                    TexInputInfo info = texInputMap.get(texInput);
                    if (info == null)
                    {
                        IntBuffer buf = BufferUtil.newIntBuffer(1);
                        gl.glGenTextures(1, buf);
                        int glName = buf.get(0);
                        
                        info = new TexInputInfo(texInput, glName);
                        
                        //Load texture data
                        gl.glBindTexture(GL.GL_TEXTURE_2D, glName);
//                        gl.glTexParameterIivEXT(
                        gl.glTexImage2D(GL.GL_TEXTURE_1D, 0, GL.GL_RGBA,
                                texInput.getWidthPow2(), texInput.getHeightPow2(),
                                0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
                                texInput.getData());
                        
                        texInputMap.put(texInput, info);
                        texInfoCache.add(info);
                    }
                }
                
                Program program = programInst.getProgram();
                ProgramInfo progInfo = programMap.get(program);
                if (progInfo == null)
                {
                    int vertId = gl.glCreateShader(GL.GL_VERTEX_SHADER);
                    int fragId = gl.glCreateShader(GL.GL_FRAGMENT_SHADER);
                    int progId = gl.glCreateProgram();
                    
                    String[] fragStrn = program.getFragmentStrings();
                    IntBuffer lenFragBuf = BufferUtil.newIntBuffer(fragStrn.length);
                    for (String strn: fragStrn)
                    {
                        lenFragBuf.put(strn.length());
                    }
                    gl.glShaderSource(fragId, fragStrn.length, fragStrn, lenFragBuf);
                    gl.glCompileShader(vertId);
                    
                    String[] vertStrn = program.getVertexStrings();
                    IntBuffer lenVertBuf = BufferUtil.newIntBuffer(vertStrn.length);
                    for (String strn: vertStrn)
                    {
                        lenVertBuf.put(strn.length());
                    }
                    gl.glShaderSource(vertId, vertStrn.length, vertStrn, lenVertBuf);
                    gl.glCompileShader(fragId);
                    
                    gl.glAttachObjectARB(progId, vertId);
                    gl.glDeleteShader(vertId);
                    gl.glAttachObjectARB(progId, fragId);
                    gl.glDeleteShader(fragId);
                    gl.glLinkProgram(progId);
                    
                    ProgramInfo programInfo = new ProgramInfo(program, progId);
                    programMap.put(program, programInfo);
                    progInfoCache.add(programInfo);
                }
                
                gl.glClear(GL.GL_COLOR_BUFFER_BIT);
                
                programInst.setupAttributes(gl, this);
                gl.glUseProgram(progInfo.getGlNameProg());
                
                TexOutput out = programInst.getOutput();
                gl.glRectf(0, 0, (float)out.getTargetWidth() / pbuffer.getWidth(), (float)out.getTargetHeight() / pbuffer.getHeight());
//                gl.glBegin(GL.GL_QUADS);
//                {
//                    gl.glVertex2f(0, 0);
//                    gl.glVertex2f(1, 0);
//                    gl.glVertex2f(1, 1);
//                    gl.glVertex2f(0, 1);
//                }
//                gl.glEnd();
                
                ByteBuffer bytes = BufferUtil.newByteBuffer(out.getTargetWidth() * out.getTargetHeight() * 4);
                
                //Finally, read the pbuffer back into pixel buffer
                gl.glReadPixels(0, 0, out.getTargetWidth(), out.getTargetHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, bytes);
            }


        }
        
        
        //Allocate new textures
        //Deallocate removed textures
        
        
    }
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int i, int i0, int i1, int i2)
    {
    }
    
    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean b, boolean b0)
    {
    }
}
