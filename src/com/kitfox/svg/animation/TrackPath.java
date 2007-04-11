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
import java.awt.geom.*;
import java.util.*;

import com.kitfox.svg.pathcmd.*;
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
public class TrackPath extends TrackBase
{

    public TrackPath(AnimationElement ele) throws SVGElementException
    {
        super(ele.getParent(), ele);
    }

    public boolean getValue(StyleAttribute attrib, double curTime)
    {
        GeneralPath path = getValue(curTime);
        if (path == null) return false;

        attrib.setStringValue(PathUtil.buildPathString(path));
        return true;
    }

    public GeneralPath getValue(double curTime)
    {
        GeneralPath retVal = null;
        AnimationTimeEval state = new AnimationTimeEval();

        for (Iterator it = animEvents.iterator(); it.hasNext();)
        {
            AnimateBase ele = (AnimateBase)it.next();
            Animate eleAnim = (Animate)ele;
            ele.evalParametric(state, curTime);

            //Reject value if it is in the invalid state
            if (Double.isNaN(state.interp)) continue;

            if (retVal == null)
            {
                retVal = eleAnim.evalPath(state.interp);
                continue;
            }
            
            GeneralPath curPath = eleAnim.evalPath(state.interp);
            switch (ele.getAdditiveType())
            {
                case AnimationElement.AD_REPLACE:
                    retVal = curPath;
                    break;
                case AnimationElement.AD_SUM:
                    throw new RuntimeException("Not implemented");
//                    retVal = new Color(curCol.getRed() + retVal.getRed(), curCol.getGreen() + retVal.getGreen(), curCol.getBlue() + retVal.getBlue());
//                    break;
                default:
                    throw new RuntimeException();
            }
        }

        return retVal;
    }
}
