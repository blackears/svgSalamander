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
 * Created on August 15, 2004, 2:52 AM
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
public abstract class AnimationElement extends SVGElement
{
    protected String attribName;
//    protected String attribType;
    protected int attribType = AT_AUTO;

    public static final int AT_CSS = 0;
    public static final int AT_XML = 1;
    public static final int AT_AUTO = 2;  //Check CSS first, then XML

    private TimeBase beginTime;
    private TimeBase durTime;
    private TimeBase endTime;
    private int fillType = FT_AUTO;

    /** <a href="http://www.w3.org/TR/smil20/smil-timing.html#adef-fill">More about the <b>fill</b> attribute</a> */
    public static final int FT_REMOVE = 0;
    public static final int FT_FREEZE = 1;
    public static final int FT_HOLD = 2;
    public static final int FT_TRANSITION = 3;
    public static final int FT_AUTO = 4;
    public static final int FT_DEFAULT = 5;

    /** Additive state of track */
    public static final int AD_REPLACE = 0;
    public static final int AD_SUM = 1;

    private int additiveType = AD_REPLACE;
    
    /** Accumlative state */
    public static final int AC_REPLACE = 0;
    public static final int AC_SUM = 1;

    private int accumulateType = AC_REPLACE;

    /** Creates a new instance of AnimateEle */
    public AnimationElement()
    {
    }

    public static String animationElementToString(int attrValue)
    {
        switch (attrValue)
        {
            case AT_CSS:
                return "CSS";
            case AT_XML:
                return "XML";
            case AT_AUTO:
                return "AUTO";
            default:
                throw new RuntimeException("Unknown element type");
        }
    }
    
    @Override
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        attribName = attrs.getValue("attributeName");
        String attribType = attrs.getValue("attributeType");
        if (attribType != null)
        {
            attribType = attribType.toLowerCase();
            if (attribType.equals("css")) this.attribType = AT_CSS;
            else if (attribType.equals("xml")) this.attribType = AT_XML;
        }

        String beginTime = attrs.getValue("begin");
        String durTime = attrs.getValue("dur");
        String endTime = attrs.getValue("end");

        try
        {
            if (beginTime != null)
            {
                helper.animTimeParser.ReInit(new StringReader(beginTime));
                this.beginTime = helper.animTimeParser.Expr();
                this.beginTime.setParentElement(this);
            }

            if (durTime != null)
            {
                helper.animTimeParser.ReInit(new StringReader(durTime));
                this.durTime = helper.animTimeParser.Expr();
                this.durTime.setParentElement(this);
            }

            if (endTime != null)
            {
                helper.animTimeParser.ReInit(new StringReader(endTime));
                this.endTime = helper.animTimeParser.Expr();
                this.endTime.setParentElement(this);
            }
        }
        catch (Exception e)
        {
            throw new SAXException(e);
        }
        
//        this.beginTime = TimeBase.parseTime(beginTime);
//        this.durTime = TimeBase.parseTime(durTime);
//        this.endTime = TimeBase.parseTime(endTime);

        String fill = attrs.getValue("fill");

        if (fill != null)
        {
            if (fill.equals("remove")) this.fillType = FT_REMOVE;
            if (fill.equals("freeze")) this.fillType = FT_FREEZE;
            if (fill.equals("hold")) this.fillType = FT_HOLD;
            if (fill.equals("transiton")) this.fillType = FT_TRANSITION;
            if (fill.equals("auto")) this.fillType = FT_AUTO;
            if (fill.equals("default")) this.fillType = FT_DEFAULT;
        }
        
        String additiveStrn = attrs.getValue("additive");
        
        if (additiveStrn != null)
        {
            if (additiveStrn.equals("replace")) this.additiveType = AD_REPLACE;
            if (additiveStrn.equals("sum")) this.additiveType = AD_SUM;
        }
        
        String accumulateStrn = attrs.getValue("accumulate");
        
        if (accumulateStrn != null)
        {
            if (accumulateStrn.equals("replace")) this.accumulateType = AC_REPLACE;
            if (accumulateStrn.equals("sum")) this.accumulateType = AC_SUM;
        }
    }

    public String getAttribName() { return attribName; }
    public int getAttribType() { return attribType; }
    public int getAdditiveType() { return additiveType; }
    public int getAccumulateType() { return accumulateType; }

    public void evalParametric(AnimationTimeEval state, double curTime)
    {
        evalParametric(state, curTime, Double.NaN, Double.NaN);
    }

    /**
     * Compares current time to start and end times and determines what degree
     * of time interpolation this track currently represents.  Returns
     * Float.NaN if this track cannot be evaluated at the passed time (ie,
     * it is before or past the end of the track, or it depends upon
     * an unknown event)
     * @param state - A structure that will be filled with information
     * regarding the applicability of this animatoin element at the passed
     * time.
     * @param curTime - Current time in seconds
     * @param repeatCount - Optional number of repetitions of length 'dur' to
     * do.  Set to Double.NaN to not consider this in the calculation.
     * @param repeatDur - Optional amoun tof time to repeat the animaiton.
     * Set to Double.NaN to not consider this in the calculation.
     */
    protected void evalParametric(AnimationTimeEval state, double curTime, double repeatCount, double repeatDur)
    {
        double begin = (beginTime == null) ? 0 : beginTime.evalTime();
        if (Double.isNaN(begin) || begin > curTime)
        {
            state.set(Double.NaN, 0);
            return;
        }

        double dur = (durTime == null) ? Double.NaN : durTime.evalTime();
        if (Double.isNaN(dur))
        {
            state.set(Double.NaN, 0);
            return;
        }

        //Determine end point of this animation
        double end = (endTime == null) ? Double.NaN : endTime.evalTime();
        double repeat;
//        if (Double.isNaN(repeatDur))
//        {
//            repeatDur = dur;
//        }
        if (Double.isNaN(repeatCount) && Double.isNaN(repeatDur))
        {
            repeat = Double.NaN;
        }
        else
        {
            repeat = Math.min(
                Double.isNaN(repeatCount) ? Double.POSITIVE_INFINITY : dur * repeatCount,
                Double.isNaN(repeatDur) ? Double.POSITIVE_INFINITY : repeatDur);
        }
        if (Double.isNaN(repeat) && Double.isNaN(end))
        {
            //If neither and end point nor a repeat is specified, end point is 
            // implied by duration.
            end = begin + dur;
        }

        double finishTime;
        if (Double.isNaN(end))
        {
            finishTime = begin + repeat;
        }
        else if (Double.isNaN(repeat))
        {
            finishTime = end;
        }
        else
        {
            finishTime = Math.min(end, repeat);
        }
        
        double evalTime = Math.min(curTime, finishTime);
//        if (curTime > finishTime) evalTime = finishTime;
        
        
//        double evalTime = curTime;

//        boolean pastEnd = curTime > evalTime;
        
//        if (!Double.isNaN(end) && curTime > end) { pastEnd = true; evalTime = Math.min(evalTime, end); }
//        if (!Double.isNaN(repeat) && curTime > repeat) { pastEnd = true; evalTime = Math.min(evalTime, repeat); }
        
        double ratio = (evalTime - begin) / dur;
        int rep = (int)ratio;
        double interp = ratio - rep;
        
        //Adjust for roundoff
        if (interp < 0.00001) interp = 0;

//        state.set(interp, rep);
//        if (!pastEnd)
//        {
//            state.set(interp, rep, false);
//            return;
//        }

        //If we are still within the clip, return value
        if (curTime == evalTime)
        {
            state.set(interp, rep);
            return;
        }
        
        //We are past end of clip.  Determine to clamp or ignore.
        switch (fillType)
        {
            default:
            case FT_REMOVE:
            case FT_AUTO:
            case FT_DEFAULT:
                state.set(Double.NaN, rep);
                return;
            case FT_FREEZE:
            case FT_HOLD:
            case FT_TRANSITION:
                state.set(interp == 0 ? 1 : interp, rep);
                return;
        }

    }

    double evalStartTime()
    {
        return beginTime == null ? Double.NaN : beginTime.evalTime();
    }

    double evalDurTime()
    {
        return durTime == null ? Double.NaN : durTime.evalTime();
    }

    /**
     * Evaluates the ending time of this element.  Returns 0 if not specified.
     *
     * @see hasEndTime
     */
    double evalEndTime()
    {
        return endTime == null ? Double.NaN : endTime.evalTime();
    }

    /**
     * Checks to see if an end time has been specified for this element.
     */
    boolean hasEndTime() { return endTime != null; }

    /**
     * Updates all attributes in this diagram associated with a time event.
     * Ie, all attributes with track information.
     * @return - true if this node has changed state as a result of the time
     * update
     */
    @Override
    public boolean updateTime(double curTime)
    {
        //Animation elements to not change with time
        return false;
    }

    public void rebuild() throws SVGException
    {
        AnimTimeParser animTimeParser = new AnimTimeParser(new StringReader(""));

        rebuild(animTimeParser);
    }

    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("begin")))
        {
            String newVal = sty.getStringValue();
            animTimeParser.ReInit(new StringReader(newVal));
            try {
                this.beginTime = animTimeParser.Expr();
            } catch (ParseException ex) {
                    Logger.getLogger(SVGConst.SVG_LOGGER).log(Level.WARNING, 
                        "Could not parse '" + newVal + "'", ex);
            }
        }

        if (getPres(sty.setName("dur")))
        {
            String newVal = sty.getStringValue();
            animTimeParser.ReInit(new StringReader(newVal));
            try {
                this.durTime = animTimeParser.Expr();
            } catch (ParseException ex) {
                    Logger.getLogger(SVGConst.SVG_LOGGER).log(Level.WARNING, 
                        "Could not parse '" + newVal + "'", ex);
            }
        }

        if (getPres(sty.setName("end")))
        {
            String newVal = sty.getStringValue();
            animTimeParser.ReInit(new StringReader(newVal));
            try {
                this.endTime = animTimeParser.Expr();
            } catch (ParseException ex) {
                Logger.getLogger(SVGConst.SVG_LOGGER).log(Level.WARNING, 
                    "Could not parse '" + newVal + "'", ex);
            }
        }

        if (getPres(sty.setName("fill")))
        {
            String newVal = sty.getStringValue();
            if (newVal.equals("remove")) this.fillType = FT_REMOVE;
            if (newVal.equals("freeze")) this.fillType = FT_FREEZE;
            if (newVal.equals("hold")) this.fillType = FT_HOLD;
            if (newVal.equals("transiton")) this.fillType = FT_TRANSITION;
            if (newVal.equals("auto")) this.fillType = FT_AUTO;
            if (newVal.equals("default")) this.fillType = FT_DEFAULT;
        }

        if (getPres(sty.setName("additive")))
        {
            String newVal = sty.getStringValue();
            if (newVal.equals("replace")) this.additiveType = AD_REPLACE;
            if (newVal.equals("sum")) this.additiveType = AD_SUM;
        }

        if (getPres(sty.setName("accumulate")))
        {
            String newVal = sty.getStringValue();
            if (newVal.equals("replace")) this.accumulateType = AC_REPLACE;
            if (newVal.equals("sum")) this.accumulateType = AC_SUM;
        }

    }

    /**
     * @return the beginTime
     */
    public TimeBase getBeginTime()
    {
        return beginTime;
    }

    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(TimeBase beginTime)
    {
        this.beginTime = beginTime;
    }

    /**
     * @return the durTime
     */
    public TimeBase getDurTime()
    {
        return durTime;
    }

    /**
     * @param durTime the durTime to set
     */
    public void setDurTime(TimeBase durTime)
    {
        this.durTime = durTime;
    }

    /**
     * @return the endTime
     */
    public TimeBase getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(TimeBase endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return the fillType
     */
    public int getFillType()
    {
        return fillType;
    }

    /**
     * @param fillType the fillType to set
     */
    public void setFillType(int fillType)
    {
        this.fillType = fillType;
    }

    /**
     * @param additiveType the additiveType to set
     */
    public void setAdditiveType(int additiveType)
    {
        this.additiveType = additiveType;
    }

    /**
     * @param accumulateType the accumulateType to set
     */
    public void setAccumulateType(int accumulateType)
    {
        this.accumulateType = accumulateType;
    }
}
