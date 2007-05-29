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
 * Created on September 21, 2004, 11:34 PM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.xml.StyleAttribute;
import java.awt.*;
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
public class TrackColor extends TrackBase
{

    public TrackColor(AnimationElement ele) throws SVGElementException
    {
        super(ele.getParent(), ele);
    }

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

        for (Iterator it = animEvents.iterator(); it.hasNext();)
        {
            AnimateBase ele = (AnimateBase)it.next();
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
