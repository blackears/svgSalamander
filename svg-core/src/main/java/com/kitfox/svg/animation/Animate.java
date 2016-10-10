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
import com.kitfox.svg.xml.ColorTable;
import com.kitfox.svg.xml.StyleAttribute;
import com.kitfox.svg.xml.XMLParseUtil;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static final String TAG_NAME = "animate";
    
//    StyleAttribute retAttrib = new StyleAttribute
    public static final int DT_REAL = 0;
    public static final int DT_COLOR = 1;
    public static final int DT_PATH = 2;
    int dataType = DT_REAL;
    
    private double fromValue = Double.NaN;
    private double toValue = Double.NaN;
    private double byValue = Double.NaN;
    private double[] valuesValue;
    
    private Color fromColor = null;
    private Color toColor = null;

    private GeneralPath fromPath = null;
    private GeneralPath toPath = null;

    /** Creates a new instance of Animate */
    public Animate()
    {
    }

    @Override
    public String getTagName()
    {
        return TAG_NAME;
    }

    public int getDataType()
    {
        return dataType;
    }
    
    @Override
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
        else if (toExists)
        {
            StyleAttribute style = new StyleAttribute(getAttribName());
            try
            {
                getParent().getStyle(style, true, false);
            }
            catch (SVGException ex)
            {
                Logger.getLogger(SVGConst.SVG_LOGGER).log(Level.WARNING, 
                    "Could not get from value", ex);
            }
            double from = style.getDoubleValue();
            return toValue * interp + from * (1.0 - interp);
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
            
            for (; !itFrom.isDone(); itFrom.next(), itTo.next())
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

    @Override
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

    /**
     * @return the fromValue
     */
    public double getFromValue()
    {
        return fromValue;
    }

    /**
     * @param fromValue the fromValue to set
     */
    public void setFromValue(double fromValue)
    {
        this.fromValue = fromValue;
    }

    /**
     * @return the toValue
     */
    public double getToValue()
    {
        return toValue;
    }

    /**
     * @param toValue the toValue to set
     */
    public void setToValue(double toValue)
    {
        this.toValue = toValue;
    }

    /**
     * @return the byValue
     */
    public double getByValue()
    {
        return byValue;
    }

    /**
     * @param byValue the byValue to set
     */
    public void setByValue(double byValue)
    {
        this.byValue = byValue;
    }

    /**
     * @return the valuesValue
     */
    public double[] getValuesValue()
    {
        return valuesValue;
    }

    /**
     * @param valuesValue the valuesValue to set
     */
    public void setValuesValue(double[] valuesValue)
    {
        this.valuesValue = valuesValue;
    }

    /**
     * @return the fromColor
     */
    public Color getFromColor()
    {
        return fromColor;
    }

    /**
     * @param fromColor the fromColor to set
     */
    public void setFromColor(Color fromColor)
    {
        this.fromColor = fromColor;
    }

    /**
     * @return the toColor
     */
    public Color getToColor()
    {
        return toColor;
    }

    /**
     * @param toColor the toColor to set
     */
    public void setToColor(Color toColor)
    {
        this.toColor = toColor;
    }

    /**
     * @return the fromPath
     */
    public GeneralPath getFromPath()
    {
        return fromPath;
    }

    /**
     * @param fromPath the fromPath to set
     */
    public void setFromPath(GeneralPath fromPath)
    {
        this.fromPath = fromPath;
    }

    /**
     * @return the toPath
     */
    public GeneralPath getToPath()
    {
        return toPath;
    }

    /**
     * @param toPath the toPath to set
     */
    public void setToPath(GeneralPath toPath)
    {
        this.toPath = toPath;
    }
    
}
