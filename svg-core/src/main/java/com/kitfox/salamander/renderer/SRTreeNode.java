/*
 * SRTreeNode.java
 *
 * Created on April 12, 2007, 8:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.renderer;

import com.kitfox.salamander.renderer.SurfaceManager.SurfaceInfo;
import java.util.ArrayList;

/**
 *
 * @author kitfox
 */
abstract public class SRTreeNode
{
    public static class RenderingSurface
    {
    }
    
    ArrayList<SRTreeNode> children = new ArrayList<SRTreeNode>();
    
    /** Creates a new instance of SRTreeNode */
    public SRTreeNode()
    {
    }
    
    public void addChild(SRTreeNode child)
    {
        children.add(child);
    }
    
    public boolean removeChild(SRTreeNode child)
    {
        return children.remove(child);
    }
    
    protected void render(RenderingSurface surface)
    {
        /*
        if (isFiltered() || isComplexClipArea())
        {
            RenderingSurface oldSurf = surface;
            //New surface initialized to transparent black
            SurfaceInfo backing = SurfaceManager.getDefault().getSurface(width, height, surface.getGraphicsConfiguration());
            surface = new RenderingSurface(backing);
            
            renderLocal(surface);

            for (SRTreeNode child: children)
            {
                child.render(surface);
            }

            applyFilters(surface);
            applyClipArea();
            oldSurf.overlay(surface);
        }
        else
        {
            applyClipArea();
            renderLocal(surface);

            for (SRTreeNode child: children)
            {
                child.render(surface);
            }
        }
         */
    }
    
    /**
     * Draw content specific to this element (not including child elements)
     */
    protected void renderLocal(RenderingSurface surface)
    {
        //No rendering by default
    }
}
