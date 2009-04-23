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
import java.awt.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class AnimateColor extends AnimateBase implements AnimateColorIface
{
    
    protected Color fromValue;
    protected Color toValue;
    
    /** Creates a new instance of Animate */
    public AnimateColor()
    {
    }
    
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        String strn = attrs.getValue("from");
        fromValue = ColorTable.parseColor(strn);

        strn = attrs.getValue("to");
        toValue = ColorTable.parseColor(strn);
    }

    
    /**
     * Evaluates this animation element for the passed interpolation time.  Interp
     * must be on [0..1].
     */
    public Color evalColor(double interp)
    {
        int r1 = fromValue.getRed();
        int g1 = fromValue.getGreen();
        int b1 = fromValue.getBlue();
        int r2 = toValue.getRed();
        int g2 = toValue.getGreen();
        int b2 = toValue.getBlue();
        double invInterp = 1.0 - interp;
        
        return new Color((int)(r1 * invInterp + r2 * interp), 
            (int)(g1 * invInterp + g2 * interp), 
            (int)(b1 * invInterp + b2 * interp));
    }

    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("from")))
        {
            String strn = sty.getStringValue();
            fromValue = ColorTable.parseColor(strn);
        }

        if (getPres(sty.setName("to")))
        {
            String strn = sty.getStringValue();
            toValue = ColorTable.parseColor(strn);
        }
    }
}
