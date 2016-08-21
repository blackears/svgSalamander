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
 * Created on August 15, 2004, 11:34 PM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.xml.StyleAttribute;
import java.util.*;

import com.kitfox.svg.*;

/**
 * A track holds the animation events for a single parameter of a single SVG
 * element.  It also contains the default value for the element, should the
 * user want to see the 'unanimated' value.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class TrackDouble extends TrackBase
{
    public TrackDouble(AnimationElement ele) throws SVGElementException
    {
        super(ele.getParent(), ele);
    }

    @Override
    public boolean getValue(StyleAttribute attrib, double curTime)
    {
        double val = getValue(curTime);
        if (Double.isNaN(val)) return false;

        attrib.setStringValue("" + val);
        return true;
    }

    public double getValue(double curTime)
    {
        double retVal = Double.NaN;
        
        StyleAttribute attr = null;
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
                attr = parent.getStyleAbsolute(attribName);
                retVal = attr.getDoubleValue();
                break;
            case AnimationElement.AT_XML:
                attr = parent.getPresAbsolute(attribName);
                retVal = attr.getDoubleValue();
                break;
            case AnimationElement.AT_AUTO:
                attr = parent.getStyleAbsolute(attribName);
                if (attr == null) attr = parent.getPresAbsolute(attribName);
                retVal = attr.getDoubleValue();
                break;
        }
        
        
        
        AnimationTimeEval state = new AnimationTimeEval();
//        boolean pastEnd = true;

        for (AnimationElement animationElement : animEvents) {
            Animate ele = (Animate)animationElement;
            ele.evalParametric(state, curTime);

            //Go to next element if this one does not affect processing
            if (Double.isNaN(state.interp)) continue;

            switch (ele.getAdditiveType())
            {
                case AnimationElement.AD_SUM:
                    retVal += ele.eval(state.interp);
                    break;
                case AnimationElement.AD_REPLACE:
                    retVal = ele.eval(state.interp);
                    break;
            }
            
            //Evalutae accumulation if applicable
            if (state.rep > 0)
            {
                switch (ele.getAccumulateType())
                {
                    case AnimationElement.AC_SUM:
                        retVal += ele.repeatSkipSize(state.rep);
                        break;
                }
                
            }
        }

        return retVal;
    }
}
