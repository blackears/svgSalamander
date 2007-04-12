/*
 * SurfaceManager.java
 *
 * Created on April 12, 2007, 8:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.renderer;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.WeakHashMap;

/**
 *
 * @author kitfox
 */
public class SurfaceManager implements Runnable
{
    public class SurfaceInfo
    {
        private BufferedImage image;
        private BufImgInfo info;
        
        SurfaceInfo(BufferedImage image, BufImgInfo info)
        {
            this.image = image;
            this.info = info;
        }

        public BufferedImage getBuffer()
        {
            return image;
        }
        
        /**
         * Release resources to be recycled.  Do not use the image after calling this.
         */
        public void dispose()
        {
            image = null;
            allocatedSurfs.remove(info);
            pooledSurfs.add(info);
            info.touch();
            info = null;
        }
    }
    
    static class BufImgInfo implements Comparable<BufImgInfo>
    {
        final int width;
        final int height;
        final int area;
        WeakReference<SurfaceInfo> surfRef;
        final BufferedImage image;
        long touchTime;
        
        BufImgInfo(BufferedImage image)
        {
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.area = width * height;
//            ref = new SoftReference(img);
            this.image = image;
//            this.surfRef = new WeakReference<SurfaceInfo>(null);
        }

        public void attach(SurfaceInfo surf)
        {
            surfRef = new WeakReference(surf);
//            touch();
        }
        
        public int compareTo(SurfaceManager.BufImgInfo obj)
        {
            return area < obj.area ? -1 : (area > obj.area ? 1 : 0);
        }
        
        void touch()
        {
            touchTime = System.currentTimeMillis();
        }
    }
    
    private static final SurfaceManager singleton = new SurfaceManager();
    TreeSet<BufImgInfo> pooledSurfs = new TreeSet<BufImgInfo>();
    HashSet<BufImgInfo> allocatedSurfs = new HashSet<BufImgInfo>();
//    WeakHashMap<SurfaceInfo, BufImgInfo> surfaces = new WeakHashMap<SurfaceInfo, BufImgInfo>();
    
    int SWEEP_DELAY = 10000;  //Every 10 seconds
    
    /** Creates a new instance of SurfaceManager */
    private SurfaceManager()
    {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public static SurfaceManager getDefault()
    {
        return singleton;
    }
    
    /**
     * @return a new, cleared temporary surface to render upon.  Remember to call
     * dispose() on the SurfaceInfo when you're finished with it.
     */
    public SurfaceInfo getSurface(int width, int height, GraphicsConfiguration gc)
    {
        SurfaceInfo surfInfo;
        BufImgInfo bufInfo = null;
        int targetArea = width * height;
        
        //Search for exising surface
        Iterator<BufImgInfo> it = pooledSurfs.iterator();
        for (; it.hasNext();)
        {
            BufImgInfo info = it.next();
            if (info.area >= targetArea) 
            {
                if (info.width >= width && info.height >= height)
                {
                    bufInfo = info;
                }
                break;
            }
        }
        
        if (bufInfo == null)
        {
            for (; it.hasNext();)
            {
                BufImgInfo info = it.next();
                if (info.area / 2 <= targetArea) 
                {
                    //Do not use exceptionally poorly fit buffers
                    break;
                }
                if (info.width >= width && info.height >= height)
                {
                    bufInfo = info;
                    break;
                }
            }
        }
        
        if (bufInfo != null)
        {
            //Refurbish
//            BufferedImage img = bufInfo.ref.get();
            BufferedImage img = bufInfo.image;
            
            Graphics2D g = img.createGraphics();

            g.setComposite(AlphaComposite.Clear);
            g.fillRect(0, 0, width, height);

            g.dispose();

            surfInfo = new SurfaceInfo(img.getSubimage(0, 0, width, height), bufInfo);
//            surfaces.put(surfInfo, bufInfo);
            allocatedSurfs.add(bufInfo);
            pooledSurfs.remove(bufInfo);
        }
        else
        {
            //Create a new surface
            BufferedImage img;
            if (gc.getColorModel().hasAlpha())
            {
                img = gc.createCompatibleImage(width, height);
            }
            else
            {
                img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
            
            bufInfo = new BufImgInfo(img);
            surfInfo = new SurfaceInfo(img, bufInfo);
//            surfaces.put(surfInfo, bufInfo);
            allocatedSurfs.add(bufInfo);
        }
        
        bufInfo.attach(surfInfo);
        return surfInfo;
    }

    public void run()
    {
        while (true)
        {
            /*
            //Clean up memory leaked images
            for (Iterator<BufImgInfo> it = allocatedSurfs.iterator(); it.hasNext();)
            {
                BufImgInfo info = it.next();
                if (info.surfRef.get() == null)
                {
                    it.remove();
                    pooledSurfs.add(info);
                    info.touch();
                }
            }
             */
            
            //Kill out of date pooled surfaces
            long curTime = System.currentTimeMillis();
            for (Iterator<BufImgInfo> it = pooledSurfs.iterator(); it.hasNext();)
            {
                BufImgInfo info = it.next();
                if ((curTime - info.touchTime) > SWEEP_DELAY)
                {
                    it.remove();
                }
            }
            
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
