/*
 * SVG Salamander
 * Copyright (c) 2004, Mark McKay
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *   - Redistributions of source code must retain the above 
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials 
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 * 
 * Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 * projects can be found at http://www.kitfox.com
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
