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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


/**
 * This represents a summation of other time elements.  It is used for complex
 * timing events with offsets.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class TimeCompound extends TimeBase
{
    static final Pattern patPlus = Pattern.compile("\\+");
    
    /**
     * This is a list of times.  This element's time is calculated as the greatest
     * member that is less than the current time.
    */
    final List componentTimes;

    private AnimationElement parent;
    
    /** Creates a new instance of TimeDiscrete */
    public TimeCompound(List timeBases)
    {
        componentTimes = Collections.unmodifiableList(timeBases);
    }
    
    public double evalTime()
    {
        double agg = 0.0;
        
        for (Iterator it = componentTimes.iterator(); it.hasNext();)
        {
            TimeBase timeEle = (TimeBase)it.next();
            double time = timeEle.evalTime();
            agg += time;
        }
        
        return agg;
    }
    
    public void setParentElement(AnimationElement ele)
    {
        this.parent = ele;
        
        for (Iterator it = componentTimes.iterator(); it.hasNext();)
        {
            TimeBase timeEle = (TimeBase)it.next();
            timeEle.setParentElement(ele);
        }
    }
}
