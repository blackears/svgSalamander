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
public class TimeSum extends TimeBase
{
    //Milliseconds of delay
    TimeBase t1;
    TimeBase t2;
    boolean add;
    
    /** Creates a new instance of TimeDiscrete */
    public TimeSum(TimeBase t1, TimeBase t2, boolean add)
    {
        this.t1 = t1;
        this.t2 = t2;
        this.add = add;
    }
    
    public double evalTime()
    {
        return add ? t1.evalTime() + t2.evalTime() : t1.evalTime() - t2.evalTime();
    }
    
    public void setParentElement(AnimationElement ele)
    {
        t1.setParentElement(ele);
        t2.setParentElement(ele);
    }
}
