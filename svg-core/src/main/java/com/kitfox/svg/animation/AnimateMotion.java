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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class AnimateMotion extends AnimateXform
{
    public static final String TAG_NAME = "animateMotion";
    
    static final Matcher matchPoint = Pattern.compile("\\s*(\\d+)[^\\d]+(\\d+)\\s*").matcher("");
    
//    protected double fromValue;
//    protected double toValue;
    private GeneralPath path;
    private int rotateType = RT_ANGLE;
    private double rotate;  //Static angle to rotate by
    
    public static final int RT_ANGLE = 0;  //Rotate by constant 'rotate' degrees
    public static final int RT_AUTO = 1;  //Rotate to reflect tangent of position on path
    
    final ArrayList<Bezier> bezierSegs = new ArrayList<Bezier>();
    double curveLength;
    
    /** Creates a new instance of Animate */
    public AnimateMotion()
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
        
        //Motion element implies animating the transform element
        if (attribName == null) 
        {
            attribName = "transform";
            attribType = AT_AUTO;
            setAdditiveType(AD_SUM);
        }
        

        String path = attrs.getValue("path");
        if (path != null)
        {
            this.path = buildPath(path, GeneralPath.WIND_NON_ZERO);
        }
        
        //Now parse rotation style
        String rotate = attrs.getValue("rotate");
        if (rotate != null)
        {
            if (rotate.equals("auto"))
            {
                this.rotateType = RT_AUTO;
            }
            else
            {
                try { this.rotate = Math.toRadians(Float.parseFloat(rotate)); } catch (Exception e) {}
            }
        }

        //Determine path
        String from = attrs.getValue("from");
        String to = attrs.getValue("to");

        buildPath(from, to);
    }
    
    protected static void setPoint(Point2D.Float pt, String x, String y)
    {
        try { pt.x = Float.parseFloat(x); } catch (Exception e) {};
        
        try { pt.y = Float.parseFloat(y); } catch (Exception e) {};
    }

    private void buildPath(String from, String to)
    {
        if (from != null && to != null)
        {
            Point2D.Float ptFrom = new Point2D.Float(), ptTo = new Point2D.Float();

            matchPoint.reset(from);
            if (matchPoint.matches())
            {
                setPoint(ptFrom, matchPoint.group(1), matchPoint.group(2));
            }

            matchPoint.reset(to);
            if (matchPoint.matches())
            {
                setPoint(ptFrom, matchPoint.group(1), matchPoint.group(2));
            }

            if (ptFrom != null && ptTo != null)
            {
                path = new GeneralPath();
                path.moveTo(ptFrom.x, ptFrom.y);
                path.lineTo(ptTo.x, ptTo.y);
            }
        }

        paramaterizePath();
    }
    
    private void paramaterizePath()
    {
        bezierSegs.clear();
        curveLength = 0;
        
        double[] coords = new double[6];
        double sx = 0, sy = 0;
        
        for (PathIterator pathIt = path.getPathIterator(new AffineTransform()); !pathIt.isDone(); pathIt.next())
        {
            Bezier bezier = null;
                    
            int segType = pathIt.currentSegment(coords);
            
            switch (segType)
            {
                case PathIterator.SEG_LINETO: 
                {
                    bezier = new Bezier(sx, sy, coords, 1);
                    sx = coords[0];
                    sy = coords[1];
                    break;
                }
                case PathIterator.SEG_QUADTO:
                {
                    bezier = new Bezier(sx, sy, coords, 2);
                    sx = coords[2];
                    sy = coords[3];
                    break;
                }
                case PathIterator.SEG_CUBICTO:
                {
                    bezier = new Bezier(sx, sy, coords, 3);
                    sx = coords[4];
                    sy = coords[5];
                    break;
                }
                case PathIterator.SEG_MOVETO:
                {
                    sx = coords[0];
                    sy = coords[1];
                    break;
                }
                case PathIterator.SEG_CLOSE:
                    //Do nothing
                    break;
                
            }

            if (bezier != null)
            {
                bezierSegs.add(bezier);
                curveLength += bezier.getLength();
            }
        }
    }
    
    /**
     * Evaluates this animation element for the passed interpolation time.  Interp
     * must be on [0..1].
     */
    @Override
    public AffineTransform eval(AffineTransform xform, double interp)
    {
        Point2D.Double point = new Point2D.Double();
        
        if (interp >= 1)
        {
            Bezier last = (Bezier)bezierSegs.get(bezierSegs.size() - 1);
            last.getFinalPoint(point);
            xform.setToTranslation(point.x, point.y);
            return xform;
        }
        
        double curLength = curveLength * interp;
        for (Bezier bez : bezierSegs) {
            double bezLength = bez.getLength();
            if (curLength < bezLength)
            {
                double param = curLength / bezLength;
                bez.eval(param, point);
                break;
            }
            
            curLength -= bezLength;
        }
        
        xform.setToTranslation(point.x, point.y);
        
        return xform;
    }
    

    @Override
    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("path")))
        {
            String strn = sty.getStringValue();
            this.path = buildPath(strn, GeneralPath.WIND_NON_ZERO);
        }

        if (getPres(sty.setName("rotate")))
        {
            String strn = sty.getStringValue();
            if (strn.equals("auto"))
            {
                this.rotateType = RT_AUTO;
            }
            else
            {
                try { this.rotate = Math.toRadians(Float.parseFloat(strn)); } catch (Exception e) {}
            }
        }

        String from = null;
        if (getPres(sty.setName("from")))
        {
            from = sty.getStringValue();
        }

        String to = null;
        if (getPres(sty.setName("to")))
        {
            to = sty.getStringValue();
        }
        
        buildPath(from, to);
    }

    /**
     * @return the path
     */
    public GeneralPath getPath()
    {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(GeneralPath path)
    {
        this.path = path;
    }

    /**
     * @return the rotateType
     */
    public int getRotateType()
    {
        return rotateType;
    }

    /**
     * @param rotateType the rotateType to set
     */
    public void setRotateType(int rotateType)
    {
        this.rotateType = rotateType;
    }

    /**
     * @return the rotate
     */
    public double getRotate()
    {
        return rotate;
    }

    /**
     * @param rotate the rotate to set
     */
    public void setRotate(double rotate)
    {
        this.rotate = rotate;
    }
}
