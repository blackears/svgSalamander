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
 * Created on August 15, 2004, 2:51 AM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.SVGConst;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGLoaderHelper;
import com.kitfox.svg.animation.parser.AnimTimeParser;
import com.kitfox.svg.animation.parser.ParseException;
import com.kitfox.svg.xml.StyleAttribute;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
abstract public class AnimateBase extends AnimationElement
{
    private double repeatCount = Double.NaN;
    private TimeBase repeatDur;
    
    /** Creates a new instance of Animate */
    public AnimateBase()
    {
    }
    
    @Override
    public void evalParametric(AnimationTimeEval state, double curTime)
    {
        evalParametric(state, curTime, repeatCount, repeatDur == null ? Double.NaN : repeatDur.evalTime());
    }
    
    @Override
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

    @Override
    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("repeatDur")))
        {
            String strn = sty.getStringValue();
            if (strn != null)
            {
                animTimeParser.ReInit(new StringReader(strn));
                try
                {
                    this.repeatDur = animTimeParser.Expr();
                }
                catch (ParseException ex)
                {
                    Logger.getLogger(SVGConst.SVG_LOGGER).log(Level.WARNING, 
                        "Could not parse '" + strn + "'", ex);
                }
            }
        }

        if (getPres(sty.setName("repeatCount")))
        {
            String strn = sty.getStringValue();
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

    /**
     * @return the repeatCount
     */
    public double getRepeatCount()
    {
        return repeatCount;
    }

    /**
     * @param repeatCount the repeatCount to set
     */
    public void setRepeatCount(double repeatCount)
    {
        this.repeatCount = repeatCount;
    }

    /**
     * @return the repeatDur
     */
    public TimeBase getRepeatDur()
    {
        return repeatDur;
    }

    /**
     * @param repeatDur the repeatDur to set
     */
    public void setRepeatDur(TimeBase repeatDur)
    {
        this.repeatDur = repeatDur;
    }
}
