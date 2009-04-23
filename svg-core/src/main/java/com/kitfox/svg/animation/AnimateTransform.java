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
//    protected AffineTransform fromValue;
//    protected AffineTransform toValue;
//    protected double[] fromValue;  //Transform parameters
//    protected double[] toValue;
    protected double[][] values;
    protected double[] keyTimes;

    public static final int AT_REPLACE = 0;
    public static final int AT_SUM = 1;

    protected int additive = AT_REPLACE;

    public static final int TR_TRANSLATE = 0;
    public static final int TR_ROTATE = 1;
    public static final int TR_SCALE = 2;
    public static final int TR_SKEWY = 3;
    public static final int TR_SKEWX = 4;
    public static final int TR_INVALID = 5;

    protected int xformType = TR_INVALID;

    /** Creates a new instance of Animate */
    public AnimateTransform()
    {
    }

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
                double x = (1.0 - interp) * fromValue[0] + interp * toValue[0];
                double y = (1.0 - interp) * fromValue[1] + interp * toValue[1];
                xform.setToTranslation(x, y);
                break;
            }
            case TR_ROTATE:
            {
                double x1 = fromValue.length == 3 ? fromValue[1] : 0;
                double y1 = fromValue.length == 3 ? fromValue[2] : 0;
                double x2 = toValue.length == 3 ? toValue[1] : 0;
                double y2 = toValue.length == 3 ? toValue[2] : 0;
                
                double theta = (1.0 - interp) * fromValue[0] + interp * toValue[0];
                double x = (1.0 - interp) * x1 + interp * x2;
                double y = (1.0 - interp) * y1 + interp * y2;
                xform.setToRotation(Math.toRadians(theta), x, y);
                break;
            }
            case TR_SCALE:
            {
                double x = (1.0 - interp) * fromValue[0] + interp * toValue[0];
                double y = (1.0 - interp) * fromValue[1] + interp * toValue[1];
                xform.setToScale(x, y);
                break;
            }
            case TR_SKEWX:
            {
                double x = (1.0 - interp) * fromValue[0] + interp * toValue[0];
                xform.setToShear(Math.toRadians(x), 0.0);
                break;
            }
            case TR_SKEWY:
            {
                double y = (1.0 - interp) * fromValue[0] + interp * toValue[0];
                xform.setToShear(0.0, Math.toRadians(y));
                break;
            }
            default:
                xform.setToIdentity();
                break;
        }

        return xform;
    }

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
}
