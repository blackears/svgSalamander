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
import java.awt.geom.*;
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
public class TrackMotion extends TrackBase
{
    public TrackMotion(AnimationElement ele) throws SVGElementException
    {
        //The motion element implies a CSS attribute of transform
//        super(ele.getParent(), "transform", AnimationElement.AT_CSS);
        super(ele.getParent(), ele);
    }

    public boolean getValue(StyleAttribute attrib, double curTime) throws SVGException
    {
        AffineTransform retVal = new AffineTransform();
        retVal = getValue(retVal, curTime);
//        AffineTransform val = getValue(curTime);
//        if (val == null) return false;

        double[] mat = new double[6];
        retVal.getMatrix(mat);
        attrib.setStringValue("matrix(" + mat[0] + " " + mat[1] + " " + mat[2] + " " + mat[3] + " " + mat[4] + " " + mat[5] + ")");
        return true;
    }

    public AffineTransform getValue(AffineTransform retVal, double curTime) throws SVGException
    {
        //Init transform with default state
        StyleAttribute attr = null;
        switch (attribType)
        {
            case AnimationElement.AT_CSS:
                attr = parent.getStyleAbsolute(attribName);
                retVal.setTransform(SVGElement.parseSingleTransform(attr.getStringValue()));
                break;
            case AnimationElement.AT_XML:
                attr = parent.getPresAbsolute(attribName);
                retVal.setTransform(SVGElement.parseSingleTransform(attr.getStringValue()));
                break;
            case AnimationElement.AT_AUTO:
                attr = parent.getStyleAbsolute(attribName);
                if (attr == null) attr = parent.getPresAbsolute(attribName);
                retVal.setTransform(SVGElement.parseSingleTransform(attr.getStringValue()));
                break;
        }


        //Update transform with time based information
        AnimationTimeEval state = new AnimationTimeEval();
        AffineTransform xform = new AffineTransform();
//        boolean pastEnd = true;

        for (Iterator it = animEvents.iterator(); it.hasNext();)
        {
            AnimateMotion ele = (AnimateMotion)it.next();
            ele.evalParametric(state, curTime);

            //Go to next element if this one does not affect processing
            if (Double.isNaN(state.interp)) continue;

            switch (ele.getAdditiveType())
            {
                case AnimationElement.AD_SUM:
                    retVal.concatenate(ele.eval(xform, state.interp));
                    break;
                case AnimationElement.AD_REPLACE:
                    retVal.setTransform(ele.eval(xform, state.interp));
                    break;
            }

            //Evaluate accumulation if applicable
/*
            if (state.rep > 0)
            {
                switch (ele.getAccumulateType())
                {
                    case AnimationElement.AC_SUM:
                        retVal += ele.repeatSkipSize(state.rep);
                        break;
                }

            }
*/
        }

        return retVal;
    }
}
