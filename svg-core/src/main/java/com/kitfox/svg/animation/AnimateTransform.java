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

import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGLoaderHelper;
import com.kitfox.svg.animation.parser.AnimTimeParser;
import com.kitfox.svg.xml.StyleAttribute;
import com.kitfox.svg.xml.XMLParseUtil;
import java.awt.geom.AffineTransform;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class AnimateTransform extends AnimateXform
{
    public static final String TAG_NAME = "animateTransform";
    
//    protected AffineTransform fromValue;
//    protected AffineTransform toValue;
//    protected double[] fromValue;  //Transform parameters
//    protected double[] toValue;
    private double[][] values;
    private double[] keyTimes;

    public static final int AT_REPLACE = 0;
    public static final int AT_SUM = 1;

    private int additive = AT_REPLACE;

    public static final int TR_TRANSLATE = 0;
    public static final int TR_ROTATE = 1;
    public static final int TR_SCALE = 2;
    public static final int TR_SKEWY = 3;
    public static final int TR_SKEWX = 4;
    public static final int TR_INVALID = 5;

    private int xformType = TR_INVALID;

    /** Creates a new instance of Animate */
    public AnimateTransform()
    {
    }

    @Override
    public String getTagName()
    {
        return TAG_NAME;
    }

    @Override
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        //Type of matrix of transform.  Should be one of the known names used to
        // define matrix transforms
        // valid types: translate, scale, rotate, skewX, skewY
        // 'matrix' not valid for animation
        String type = attrs.getValue("type").toLowerCase();
        if (type.equals("translate")) xformType = TR_TRANSLATE;
        if (type.equals("rotate")) xformType = TR_ROTATE;
        if (type.equals("scale")) xformType = TR_SCALE;
        if (type.equals("skewx")) xformType = TR_SKEWX;
        if (type.equals("skewy")) xformType = TR_SKEWY;

        String fromStrn = attrs.getValue("from");
        String toStrn = attrs.getValue("to");
        if (fromStrn != null && toStrn != null)
        {
            //fromValue = parseSingleTransform(type + "(" + strn + ")");
            double[] fromValue = XMLParseUtil.parseDoubleList(fromStrn);
            fromValue = validate(fromValue);

    //        toValue = parseSingleTransform(type + "(" + strn + ")");
            double[] toValue = XMLParseUtil.parseDoubleList(toStrn);
            toValue = validate(toValue);
            
            values = new double[][]{fromValue, toValue};
            keyTimes = new double[]{0, 1};
        }

        String keyTimeStrn = attrs.getValue("keyTimes");
        String valuesStrn = attrs.getValue("values");
        if (keyTimeStrn != null && valuesStrn != null)
        {
            keyTimes = XMLParseUtil.parseDoubleList(keyTimeStrn);
            
            String[] valueList = Pattern.compile(";").split(valuesStrn);
            values = new double[valueList.length][];
            for (int i = 0; i < valueList.length; i++)
            {
                double[] list = XMLParseUtil.parseDoubleList(valueList[i]);
                values[i] = validate(list);
            }
        }
        
        //Check our additive state
        String additive = attrs.getValue("additive");
        if (additive != null)
        {
            if (additive.equals("sum")) this.additive = AT_SUM;
        }
    }

    /**
     * Check list size against current xform type and ensure list
     * is expanded to a standard list size
     */
    private double[] validate(double[] paramList)
    {
        switch (xformType)
        {
            case TR_SCALE:
            {
                if (paramList == null)
                {
                    paramList = new double[]{1, 1};
                }
                else if (paramList.length == 1)
                {
                    paramList = new double[]{paramList[0], paramList[0]};
                    
//                    double[] tmp = paramList;
//                    paramList = new double[2];
//                    paramList[0] = paramList[1] = tmp[0];
                }
            }
        }

        return paramList;
    }

    /**
     * Evaluates this animation element for the passed interpolation time.  Interp
     * must be on [0..1].
     */
    @Override
    public AffineTransform eval(AffineTransform xform, double interp)
    {
        int idx = 0;
        for (; idx < keyTimes.length - 1; idx++)
        {
            if (interp >= keyTimes[idx])
            {
                idx--;
                if (idx < 0) idx = 0;
                break;
            }
        }
        
        double spanStartTime = keyTimes[idx];
        double spanEndTime = keyTimes[idx + 1];
//        double span = spanStartTime - spanEndTime;
        
        interp = (interp - spanStartTime) / (spanEndTime - spanStartTime);
        double[] fromValue = values[idx];
        double[] toValue = values[idx + 1];
        
        switch (xformType)
        {
            case TR_TRANSLATE:
            {
                double x0 = fromValue.length >= 1 ? fromValue[0] : 0;
                double x1 = toValue.length >= 1 ? toValue[0] : 0;
                double y0 = fromValue.length >= 2 ? fromValue[1] : 0;
                double y1 = toValue.length >= 2 ? toValue[1] : 0;
                
                double x = lerp(x0, x1, interp);
                double y = lerp(y0, y1, interp);
                
                xform.setToTranslation(x, y);
                break;
            }
            case TR_ROTATE:
            {
                double x1 = fromValue.length == 3 ? fromValue[1] : 0;
                double y1 = fromValue.length == 3 ? fromValue[2] : 0;
                double x2 = toValue.length == 3 ? toValue[1] : 0;
                double y2 = toValue.length == 3 ? toValue[2] : 0;
                
                double theta = lerp(fromValue[0], toValue[0], interp);
                double x = lerp(x1, x2, interp);
                double y = lerp(y1, y2, interp);
                xform.setToRotation(Math.toRadians(theta), x, y);
                break;
            }
            case TR_SCALE:
            {
                double x0 = fromValue.length >= 1 ? fromValue[0] : 1;
                double x1 = toValue.length >= 1 ? toValue[0] : 1;
                double y0 = fromValue.length >= 2 ? fromValue[1] : 1;
                double y1 = toValue.length >= 2 ? toValue[1] : 1;
                
                double x = lerp(x0, x1, interp);
                double y = lerp(y0, y1, interp);
                xform.setToScale(x, y);
                break;
            }
            case TR_SKEWX:
            {
                double x = lerp(fromValue[0], toValue[0], interp);
                xform.setToShear(Math.toRadians(x), 0.0);
                break;
            }
            case TR_SKEWY:
            {
                double y = lerp(fromValue[0], toValue[0], interp);
                xform.setToShear(0.0, Math.toRadians(y));
                break;
            }
            default:
                xform.setToIdentity();
                break;
        }

        return xform;
    }

    @Override
    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("type")))
        {
            String strn = sty.getStringValue().toLowerCase();
            if (strn.equals("translate")) xformType = TR_TRANSLATE;
            if (strn.equals("rotate")) xformType = TR_ROTATE;
            if (strn.equals("scale")) xformType = TR_SCALE;
            if (strn.equals("skewx")) xformType = TR_SKEWX;
            if (strn.equals("skewy")) xformType = TR_SKEWY;
        }

        String fromStrn = null;
        if (getPres(sty.setName("from")))
        {
            fromStrn = sty.getStringValue();
        }

        String toStrn = null;
        if (getPres(sty.setName("to")))
        {
            toStrn = sty.getStringValue();
        }

        if (fromStrn != null && toStrn != null)
        {
            double[] fromValue = XMLParseUtil.parseDoubleList(fromStrn);
            fromValue = validate(fromValue);

            double[] toValue = XMLParseUtil.parseDoubleList(toStrn);
            toValue = validate(toValue);

            values = new double[][]{fromValue, toValue};
        }

        String keyTimeStrn = null;
        if (getPres(sty.setName("keyTimes")))
        {
            keyTimeStrn = sty.getStringValue();
        }

        String valuesStrn = null;
        if (getPres(sty.setName("values")))
        {
            valuesStrn = sty.getStringValue();
        }

        if (keyTimeStrn != null && valuesStrn != null)
        {
            keyTimes = XMLParseUtil.parseDoubleList(keyTimeStrn);

            String[] valueList = Pattern.compile(";").split(valuesStrn);
            values = new double[valueList.length][];
            for (int i = 0; i < valueList.length; i++)
            {
                double[] list = XMLParseUtil.parseDoubleList(valueList[i]);
                values[i] = validate(list);
            }
        }

        //Check our additive state

        if (getPres(sty.setName("additive")))
        {
            String strn = sty.getStringValue().toLowerCase();
            if (strn.equals("sum")) this.additive = AT_SUM;
        }
    }

    /**
     * @return the values
     */
    public double[][] getValues()
    {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(double[][] values)
    {
        this.values = values;
    }

    /**
     * @return the keyTimes
     */
    public double[] getKeyTimes()
    {
        return keyTimes;
    }

    /**
     * @param keyTimes the keyTimes to set
     */
    public void setKeyTimes(double[] keyTimes)
    {
        this.keyTimes = keyTimes;
    }

    /**
     * @return the additive
     */
    public int getAdditive()
    {
        return additive;
    }

    /**
     * @param additive the additive to set
     */
    public void setAdditive(int additive)
    {
        this.additive = additive;
    }

    /**
     * @return the xformType
     */
    public int getXformType()
    {
        return xformType;
    }

    /**
     * @param xformType the xformType to set
     */
    public void setXformType(int xformType)
    {
        this.xformType = xformType;
    }
}
