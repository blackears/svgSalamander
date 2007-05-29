/*
 * TimeDiscrete.java
 *
 *  The Salamander Project - 2D and 3D graphics libraries in Java
 *  Copyright (C) 2004 Mark McKay
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *  Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 *  projects can be found at http://www.kitfox.com
 *
 * Created on August 15, 2004, 3:33 AM
 */

package com.kitfox.svg.animation;

/**
 * This is a time that represents a specific number of milliseconds
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class TimeLookup extends TimeBase
{
    /** 
     * This time can only be resolved in relation to it's parent
     */
    private AnimationElement parent;
    
    /**
     * Node this lookup acts upon
     */
    String node;
    
    /**
     * Event to evalutae on this node
     */
    String event;
    
    /**
     * Optional parameter used by some events
     */
    String paramList;
    
    /** Creates a new instance of TimeDiscrete */
    public TimeLookup(AnimationElement parent, String node, String event, String paramList)
    {
        this.parent = parent;
        this.node = node;
        this.event = event;
        this.paramList = paramList;
    }
    
    public double evalTime()
    {
        return 0.0;
    }
    
    public void setParentElement(AnimationElement ele)
    {
        parent = ele;
    }
}
