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
import java.awt.geom.*;
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
public class TrackMotion extends TrackBase
{
    public TrackMotion(AnimationElement ele) throws SVGElementException
    {
        //The motion element implies a CSS attribute of transform
//        super(ele.getParent(), "transform", AnimationElement.AT_CSS);
        super(ele.getParent(), ele);
    }

    @Override
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

        for (AnimationElement animationElement : animEvents) {
            AnimateMotion ele = (AnimateMotion)animationElement;
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
