/*
 * TimeBase.java
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
 * Created on August 15, 2004, 3:31 AM
 */

package com.kitfox.svg.animation;

import java.util.regex.*;

/**
 * SVG has a complicated way of specifying time.  Potentially, a time could
 * be represened as a summation of discrete times and times of other animation
 * events.  This provides a root for the many elements we will need to define
 * time.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
abstract public class TimeBase
{
    static final Matcher matchIndefinite = Pattern.compile("\\s*indefinite\\s*").matcher("");
    static final Matcher matchUnitTime = Pattern.compile("\\s*([-+]?((\\d*\\.\\d+)|(\\d+))([-+]?[eE]\\d+)?)\\s*(h|min|s|ms)?\\s*").matcher("");
    
    /*
    public static TimeBase parseTime(String text) 
    { 
        if (text == null) return null;
        
        if (text.indexOf('+') == -1)
        {
            return parseTimeComponent(text);
        }
        
        return new TimeCompound(text);
    }
     */
    
    protected static TimeBase parseTimeComponent(String text)
    {
        matchIndefinite.reset(text);
        if (matchIndefinite.matches()) return new TimeIndefinite();
        
        matchUnitTime.reset(text);
        if (matchUnitTime.matches())
        {
            String val = matchUnitTime.group(1);
            String units = matchUnitTime.group(6);
            
            double time = 0;
            try { time = Double.parseDouble(val); }
            catch (Exception e) {}
            
            if (units.equals("ms")) time *= .001;
            else if (units.equals("min")) time *= 60;
            else if (units.equals("h")) time *= 3600;
            
            return new TimeDiscrete(time);
        }
        
        return null;
    }
    
    /**
     * Calculates the (greater than or equal to 0) time in seconds this 
     * time represents.  If the time cannot be determined, returns 
     * Double.NaN.  If this represents an infinte amount of time, returns 
     * Double.POSITIVE_INFINITY.
     */
    abstract public double evalTime();
    
    /**
     * Some time elements need to refer to the animation element that contains 
     * them to evaluate correctly
     */
    public void setParentElement(AnimationElement ele)
    {
    }
}
