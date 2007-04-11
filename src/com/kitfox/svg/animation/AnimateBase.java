/*
 * Animate.java
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
 * Created on August 15, 2004, 2:51 AM
 */

package com.kitfox.svg.animation;

import java.io.*;
import org.xml.sax.*;

import com.kitfox.svg.*;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
abstract public class AnimateBase extends AnimationElement
{
    protected double repeatCount = Double.NaN;
    protected TimeBase repeatDur;
    
    /** Creates a new instance of Animate */
    public AnimateBase()
    {
    }
    
    public void evalParametric(AnimationTimeEval state, double curTime)
    {
        evalParametric(state, curTime, repeatCount, repeatDur == null ? Double.NaN : repeatDur.evalTime());
    }
    
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        String repeatDurTime = attrs.getValue("repeatDur");

        try
        {
            if (repeatDurTime != null)
            {
                helper.animTimeParser.ReInit(new StringReader(repeatDurTime));
                this.repeatDur = helper.animTimeParser.Expr();
                this.repeatDur.setParentElement(this);
            }
        }
        catch (Exception e)
        {
            throw new SAXException(e);
        }
        
//        this.repeatDur = TimeBase.parseTime(repeatDurTime);

        String strn = attrs.getValue("repeatCount");
        if (strn == null)
        {
            repeatCount = 1;
        }
        else if ("indefinite".equals(strn))
        {
            repeatCount = Double.POSITIVE_INFINITY;
        }
        else
        {
            try { repeatCount = Double.parseDouble(strn); } 
            catch (Exception e) { repeatCount = Double.NaN; }
        }
    }

}
