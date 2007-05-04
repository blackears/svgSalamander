/*
 * Filter.java
 *
 * Created on May 3, 2007, 12:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.javaone2007.filter;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.media.opengl.GL;

/**
 *
 * @author kitfox
 */
public class Filter
{
    final FilterImageView viewOutput;
    
    //Program made up of a chain of filter primitives
//    ArrayList<FilterPrimitive> primitives = new ArrayList<FilterPrimitive>();
    final FilterPrimitive[] primitives;
    final HashMap<Integer, FilterImageView> allocBufs;
    
    /** Creates a new instance of Filter */
    public Filter(FilterPrimitive[] primitives, HashMap<Integer, FilterImageView> inputs, FilterImageView output)
    {
        this.primitives = primitives.clone();
        
        this.viewOutput = output;
        FilterTransform coordSys = output.viewToRaster;
        
        if (!output.rasterConstainsView())
        {
            throw new IllegalArgumentException("View extends beyond raster");
        }
        
        //Make sure backing rasters of all input images fit output resolution
        for (Integer i: inputs.keySet())
        {
            if (i == 0)
            {
                throw new IllegalArgumentException("Register 0 is reserved for filter output");
            }
            
            FilterImageView img = inputs.get(i);
            if (!img.viewToRaster.equals(coordSys))
            {
                throw new IllegalArgumentException("Input image " + i + " has a different coordinate system than output filter");
            }
            
            if (!img.rasterConstainsView())
            {
                throw new IllegalArgumentException("View of image " + i + " extends beyond raster");
            }
        }
        
        //Allocate temporary intermediate buffers
        allocBufs = new HashMap<Integer, FilterImageView>();
        allocBufs.putAll(inputs);
        allocBufs.put(0, output);
        for (FilterPrimitive prim: primitives)
        {
            int[] inputArr = prim.getRegInputs();
            for (int i: inputArr)
            {
                if (!allocBufs.containsKey(i))
                {
                    throw new IllegalArgumentException("Could not find input for register " + i);
                }
            }

            //Determine output size
            FilterImageView[] inputImages = new FilterImageView[inputArr.length];
            for (int i = 0; i < inputImages.length; i++)
            {
                inputImages[i] = allocBufs.get(i);
            }
            Rectangle2D.Float viewArea = prim.getOutputViewArea(output.viewArea, inputImages);
            
            //Register output of primitive
            int outReg = prim.getRegOut();
            FilterImageView in = allocBufs.get(outReg);
            if (in != null)
            {
                //Check bounds
                if (!in.contains(viewArea))
                {
                    throw new IllegalArgumentException();
                }
                //Currently assigned image is adequate
            }
            else
            {
                //Create new buffer
                Rectangle rasterArea = new Rectangle();
                coordSys.calcMinRaster(viewArea, rasterArea);
                IntermediateRaster imgRas = new IntermediateRaster(rasterArea);
                FilterImageView imgView = new FilterImageView(viewArea, coordSys, imgRas);
                allocBufs.put(outReg, imgView);
            }
        }
    }
    
    public void render(GL gl)
    {
        for (FilterPrimitive prim: primitives)
        {
            prim.run(gl, allocBufs);
        }

        
    }
}
