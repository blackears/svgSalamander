/*
 * TrackManager.java
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
 * Created on August 15, 2004, 11:34 PM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.xml.StyleAttribute;
import java.util.*;

import com.kitfox.svg.*;
import com.kitfox.svg.xml.*;

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

        for (Iterator it = animEvents.iterator(); it.hasNext();)
        {
            Animate ele = (Animate)it.next();
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
