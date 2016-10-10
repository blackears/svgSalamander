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
 * Created on August 15, 2004, 3:33 AM
 */

package com.kitfox.svg.animation;

import java.util.Collections;
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
    final List<TimeBase> componentTimes;

    private AnimationElement parent;
    
    /** Creates a new instance of TimeDiscrete */
    public TimeCompound(List<TimeBase> timeBases)
    {
        componentTimes = Collections.unmodifiableList(timeBases);
    }
    
    @Override
    public double evalTime()
    {
        double agg = 0.0;
        
        for (TimeBase timeEle : componentTimes) {
            double time = timeEle.evalTime();
            agg += time;
        }
        
        return agg;
    }
    
    @Override
    public void setParentElement(AnimationElement ele)
    {
        this.parent = ele;
        
        for (TimeBase timeEle : componentTimes) {
            timeEle.setParentElement(ele);
        }
    }
}
