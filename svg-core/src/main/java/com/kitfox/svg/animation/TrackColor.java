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
 * Created on September 21, 2004, 11:34 PM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.xml.StyleAttribute;
import java.awt.*;
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
public class TrackColor extends TrackBase
{

    public TrackColor(AnimationElement ele) throws SVGElementException
    {
        super(ele.getParent(), ele);
    }

    @Override
    public boolean getValue(StyleAttribute attrib, double curTime)
    {
        Color col = getValue(curTime);
        if (col == null) return false;

        attrib.setStringValue("#" + Integer.toHexString(col.getRGB()));
        return true;
    }

    public Color getValue(double curTime)
    {
        Color retVal = null;
        AnimationTimeEval state = new AnimationTimeEval();

        for (AnimationElement animationElement : animEvents) {
            AnimateBase ele = (AnimateBase)animationElement;
            AnimateColorIface eleColor = (AnimateColorIface)ele;
            ele.evalParametric(state, curTime);

            //Reject value if it is in the invalid state
            if (Double.isNaN(state.interp)) continue;

            if (retVal == null)
            {
                retVal = eleColor.evalColor(state.interp);
                continue;
            }
            
            Color curCol = eleColor.evalColor(state.interp);
            switch (ele.getAdditiveType())
            {
                case AnimationElement.AD_REPLACE:
                    retVal = curCol;
                    break;
                case AnimationElement.AD_SUM:
                    retVal = new Color(curCol.getRed() + retVal.getRed(), curCol.getGreen() + retVal.getGreen(), curCol.getBlue() + retVal.getBlue());
                    break;
            }
        }

        return retVal;
    }
}
