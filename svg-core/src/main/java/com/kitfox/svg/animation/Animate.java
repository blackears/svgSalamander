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
import com.kitfox.svg.xml.ColorTable;
import com.kitfox.svg.xml.StyleAttribute;
import com.kitfox.svg.xml.XMLParseUtil;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * Animate is a really annoying morphic tag that could represent a real value,
 * a color or a path
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class Animate extends AnimateBase implements AnimateColorIface
{
//    StyleAttribute retAttrib = new StyleAttribute
    public static final int DT_REAL = 0;
    public static final int DT_COLOR = 1;
    public static final int DT_PATH = 2;
    int dataType = DT_REAL;
    
    protected double fromValue = Double.NaN;
    protected double toValue = Double.NaN;
    protected double byValue = Double.NaN;
    protected double[] valuesValue;
    
    protected Color fromColor = null;
    protected Color toColor = null;

    protected GeneralPath fromPath = null;
    protected GeneralPath toPath = null;

    /** Creates a new instance of Animate */
    public Animate()
    {
    }
    
    public int getDataType()
    {
        return dataType;
    }
    
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        String strn = attrs.getValue("from");
        if (strn != null)
        {
            if (XMLParseUtil.isDouble(strn))
            {
                fromValue = XMLParseUtil.parseDouble(strn); 
            } 
//            else if (attrs.getValue("attributeName").equals("d"))
//            {
//                fromPath = this.buildPath(strn, GeneralPath.WIND_EVEN_ODD);
//                dataType = DT_PATH;
//            }
            else
            {
                fromColor = ColorTable.parseColor(strn); 
                if (fromColor == null)
                {
                    //Try path
                    fromPath = this.buildPath(strn, GeneralPath.WIND_EVEN_ODD);
                    dataType = DT_PATH;
                }
                else dataType = DT_COLOR;
            }
        }

        strn = attrs.getValue("to");
        if (strn != null)
        {
            if (XMLParseUtil.isDouble(strn))
            {
                toValue = XMLParseUtil.parseDouble(strn); 
            } 
            else
            {
                toColor = ColorTable.parseColor(strn); 
                if (toColor == null)
                {
                    //Try path
                    toPath = this.buildPath(strn, GeneralPath.WIND_EVEN_ODD);
                    dataType = DT_PATH;
                }
                else dataType = DT_COLOR;
            }
        }

        strn = attrs.getValue("by");
        try 
        {
            if (strn != null) byValue = XMLParseUtil.parseDouble(strn); 
        } catch (Exception e) {}

        strn = attrs.getValue("values");
        try 
        {
            if (strn != null) valuesValue = XMLParseUtil.parseDoubleList(strn); 
        } catch (Exception e) {}
    }
    
    /**
     * Evaluates this animation element for the passed interpolation time.  Interp
     * must be on [0..1].
     */
    public double eval(double interp)
    {
        boolean fromExists = !Double.isNaN(fromValue);
        boolean toExists = !Double.isNaN(toValue);
        boolean byExists = !Double.isNaN(byValue);
        boolean valuesExists = valuesValue != null;
        
        if (valuesExists)
        {
            double sp = interp * valuesValue.length;
            int ip = (int)sp;
            double fp = sp - ip;
            
            int i0 = ip;
            int i1 = ip + 1;
            
            if (i0 < 0) return valuesValue[0];
            if (i1 >= valuesValue.length) return valuesValue[valuesValue.length - 1];
            return valuesValue[i0] * (1 - fp) + valuesValue[i1] * fp;
        }
        else if (fromExists && toExists)
        {
            return toValue * interp + fromValue * (1.0 - interp);
        }
        else if (fromExists && byExists)
        {
            return fromValue + byValue * interp;
        }
        else if (toExists && byExists)
        {
            return toValue - byValue * (1.0 - interp);
        }
        else if (byExists)
        {
            return byValue * interp;
        }
  
        //Should not reach this line
        throw new RuntimeException("Animate tag could not be evalutated - insufficient arguements");
    }

    public Color evalColor(double interp)
    {
        if (fromColor == null && toColor != null)
        {
            float[] toCol = new float[3];
            toColor.getColorComponents(toCol);
            return new Color(toCol[0] * (float)interp, 
                toCol[1] * (float)interp, 
                toCol[2] * (float)interp);
        }
        else if (fromColor != null && toColor != null)
        {
            float nInterp = 1 - (float)interp;
            
            float[] fromCol = new float[3];
            float[] toCol = new float[3];
            fromColor.getColorComponents(fromCol);
            toColor.getColorComponents(toCol);
            return new Color(fromCol[0] * nInterp + toCol[0] * (float)interp, 
                fromCol[1] * nInterp + toCol[1] * (float)interp, 
                fromCol[2] * nInterp + toCol[2] * (float)interp);
        }
        
        throw new RuntimeException("Animate tag could not be evalutated - insufficient arguements");
    }

    public GeneralPath evalPath(double interp)
    {
        if (fromPath == null && toPath != null)
        {
            PathIterator itTo = toPath.getPathIterator(new AffineTransform());
            
            GeneralPath midPath = new GeneralPath();
            float[] coordsTo = new float[6];
            
            for (; !itTo.isDone(); itTo.next())
            {
                int segTo = itTo.currentSegment(coordsTo);
                
                switch (segTo)
                {
                    case PathIterator.SEG_CLOSE:
                        midPath.closePath();
                        break;
                    case PathIterator.SEG_CUBICTO:
                        midPath.curveTo(
                                (float)(coordsTo[0] * interp), 
                                (float)(coordsTo[1] * interp), 
                                (float)(coordsTo[2] * interp), 
                                (float)(coordsTo[3] * interp), 
                                (float)(coordsTo[4] * interp), 
                                (float)(coordsTo[5] * interp)
                                );
                        break;
                    case PathIterator.SEG_LINETO:
                        midPath.lineTo(
                                (float)(coordsTo[0] * interp), 
                                (float)(coordsTo[1] * interp)
                                );
                        break;
                    case PathIterator.SEG_MOVETO:
                        midPath.moveTo(
                                (float)(coordsTo[0] * interp), 
                                (float)(coordsTo[1] * interp)
                                );
                        break;
                    case PathIterator.SEG_QUADTO:
                        midPath.quadTo(
                                (float)(coordsTo[0] * interp), 
                                (float)(coordsTo[1] * interp), 
                                (float)(coordsTo[2] * interp), 
                                (float)(coordsTo[3] * interp)
                                );
                        break;
                }
            }
            
            return midPath;
        }
        else if (toPath != null)
        {
            PathIterator itFrom = fromPath.getPathIterator(new AffineTransform());
            PathIterator itTo = toPath.getPathIterator(new AffineTransform());
            
            GeneralPath midPath = new GeneralPath();
            float[] coordsFrom = new float[6];
            float[] coordsTo = new float[6];
            
            for (; !itFrom.isDone(); itFrom.next())
            {
                int segFrom = itFrom.currentSegment(coordsFrom);
                int segTo = itTo.currentSegment(coordsTo);
                
                if (segFrom != segTo)
                {
                    throw new RuntimeException("Path shape mismatch");
                }
                
                switch (segFrom)
                {
                    case PathIterator.SEG_CLOSE:
                        midPath.closePath();
                        break;
                    case PathIterator.SEG_CUBICTO:
                        midPath.curveTo(
                                (float)(coordsFrom[0] * (1 - interp) + coordsTo[0] * interp), 
                                (float)(coordsFrom[1] * (1 - interp) + coordsTo[1] * interp), 
                                (float)(coordsFrom[2] * (1 - interp) + coordsTo[2] * interp), 
                                (float)(coordsFrom[3] * (1 - interp) + coordsTo[3] * interp), 
                                (float)(coordsFrom[4] * (1 - interp) + coordsTo[4] * interp), 
                                (float)(coordsFrom[5] * (1 - interp) + coordsTo[5] * interp)
                                );
                        break;
                    case PathIterator.SEG_LINETO:
                        midPath.lineTo(
                                (float)(coordsFrom[0] * (1 - interp) + coordsTo[0] * interp), 
                                (float)(coordsFrom[1] * (1 - interp) + coordsTo[1] * interp)
                                );
                        break;
                    case PathIterator.SEG_MOVETO:
                        midPath.moveTo(
                                (float)(coordsFrom[0] * (1 - interp) + coordsTo[0] * interp), 
                                (float)(coordsFrom[1] * (1 - interp) + coordsTo[1] * interp)
                                );
                        break;
                    case PathIterator.SEG_QUADTO:
                        midPath.quadTo(
                                (float)(coordsFrom[0] * (1 - interp) + coordsTo[0] * interp), 
                                (float)(coordsFrom[1] * (1 - interp) + coordsTo[1] * interp), 
                                (float)(coordsFrom[2] * (1 - interp) + coordsTo[2] * interp), 
                                (float)(coordsFrom[3] * (1 - interp) + coordsTo[3] * interp)
                                );
                        break;
                }
            }
            
            return midPath;
        }
        
        throw new RuntimeException("Animate tag could not be evalutated - insufficient arguements");
    }
    
    /**
     * If this element is being accumulated, detemine the delta to accumulate by
     */
    public double repeatSkipSize(int reps)
    {
        boolean fromExists = !Double.isNaN(fromValue);
        boolean toExists = !Double.isNaN(toValue);
        boolean byExists = !Double.isNaN(byValue);
        
        if (fromExists && toExists)
        {
            return (toValue - fromValue) * reps;
        }
        else if (fromExists && byExists)
        {
            return (fromValue + byValue) * reps;
        }
        else if (toExists && byExists)
        {
            return toValue * reps;
        }
        else if (byExists)
        {
            return byValue * reps;
        }

        //Should not reach this line
        return 0;
    }

    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("from")))
        {
            String strn = sty.getStringValue();
            if (XMLParseUtil.isDouble(strn))
            {
                fromValue = XMLParseUtil.parseDouble(strn);
            }
            else
            {
                fromColor = ColorTable.parseColor(strn);
                if (fromColor == null)
                {
                    //Try path
                    fromPath = this.buildPath(strn, GeneralPath.WIND_EVEN_ODD);
                    dataType = DT_PATH;
                }
                else dataType = DT_COLOR;
            }
        }

        if (getPres(sty.setName("to")))
        {
            String strn = sty.getStringValue();
            if (XMLParseUtil.isDouble(strn))
            {
                toValue = XMLParseUtil.parseDouble(strn);
            }
            else
            {
                toColor = ColorTable.parseColor(strn);
                if (toColor == null)
                {
                    //Try path
                    toPath = this.buildPath(strn, GeneralPath.WIND_EVEN_ODD);
                    dataType = DT_PATH;
                }
                else dataType = DT_COLOR;
            }
        }

        if (getPres(sty.setName("by")))
        {
            String strn = sty.getStringValue();
            if (strn != null) byValue = XMLParseUtil.parseDouble(strn);
        }

        if (getPres(sty.setName("values")))
        {
            String strn = sty.getStringValue();
            if (strn != null) valuesValue = XMLParseUtil.parseDoubleList(strn);
        }
    }
    
}
