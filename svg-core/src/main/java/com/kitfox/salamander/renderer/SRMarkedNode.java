/*
 * SRTreeNode.java
 *
 * Created on April 12, 2007, 8:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.renderer;

import com.kitfox.salamander.renderer.SRTreeNode.RenderingSurface;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Nodes that provide points where markers can be placed.
 *
 * @author kitfox
 */
abstract public class SRMarkedNode extends SRTreeNode
{
    /** Creates a new instance of SRTreeNode */
    public SRMarkedNode()
    {
    }

    abstract protected List<Point2D.Float> getMarkerPoints();
    
    /**
     * Draw content specific to this element (not including child elements)
     */
    protected void renderLocal(RenderingSurface surface)
    {
        super.renderLocal(surface);
        
        for (Point2D.Float pt: getMarkerPoints())
        {
            getMarkerPoints();
        }
    }
}
