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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
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
    static final Matcher matchPoint = Pattern.compile("\\s*(\\d+)[^\\d]+(\\d+)\\s*").matcher("");
    
//    protected double fromValue;
//    protected double toValue;
    GeneralPath path;
    int rotateType = RT_ANGLE;
    double rotate;  //Static angle to rotate by
    
    public static final int RT_ANGLE = 0;  //Rotate by constant 'rotate' degrees
    public static final int RT_AUTO = 1;  //Rotate to reflect tangent of position on path
    
    final ArrayList bezierSegs = new ArrayList();
    double curveLength;
    
    /** Creates a new instance of Animate */
    public AnimateMotion()
    {
    }
    
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);
        
        //Motion element implies animating the transform element
        if (attribName == null) 
        {
            attribName = "transform";
            attribType = AT_AUTO;
            additiveType = AD_SUM;
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
        for (Iterator it = bezierSegs.iterator(); it.hasNext();)
        {
            Bezier bez = (Bezier)it.next();
            
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
}
