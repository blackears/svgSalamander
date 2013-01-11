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
 * Created on January 26, 2004, 5:25 PM
 */

package com.kitfox.svg;

import com.kitfox.svg.xml.StyleAttribute;
import com.kitfox.svg.xml.*;
import org.xml.sax.*;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class Ellipse extends ShapeElement {

    float cx = 0.0f;
    float cy = 0.0f;
    float rx = 0.0f;
    float ry = 0.0f;

    Ellipse2D.Float ellipse = new Ellipse2D.Float();

    /** Creates a new instance of Rect */
    public Ellipse() {
    }
/*
    protected void init(String idIn, Style parentStyle, String cx, String cy, String rx, String ry) {
        super.init(idIn, parentStyle);

        this.cx = parseDouble(cx);
        this.cy = parseDouble(cy);
        this.rx = parseDouble(rx);
        this.ry = parseDouble(ry);

        setBounds(this.cx - this.rx, this.cy - this.ry, this.rx * 2.0, this.ry * 2.0);
    }
*/
    /*
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent)
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        String cx = attrs.getValue("cx");
        String cy = attrs.getValue("cy");
        String rx = attrs.getValue("rx");
        String ry = attrs.getValue("ry");

        this.cx = XMLParseUtil.parseDouble(cx);
        this.cy = XMLParseUtil.parseDouble(cy);
        this.rx = XMLParseUtil.parseDouble(rx);
        this.ry = XMLParseUtil.parseDouble(ry);

        build();
    }
    */
    
    /*
    public void loaderEndElement(SVGLoaderHelper helper)
    {
        super.loaderEndElement(helper);

        build();
    }
     */
    
    protected void build() throws SVGException
    {
        super.build();
        
        StyleAttribute sty = new StyleAttribute();
        
        if (getPres(sty.setName("cx"))) cx = sty.getFloatValueWithUnits();
        
        if (getPres(sty.setName("cy"))) cy = sty.getFloatValueWithUnits();
        
        if (getPres(sty.setName("rx"))) rx = sty.getFloatValueWithUnits();
        
        if (getPres(sty.setName("ry"))) ry = sty.getFloatValueWithUnits();
        
        ellipse.setFrame(cx - rx, cy - ry, rx * 2f, ry * 2f);
    }

    public void render(Graphics2D g) throws SVGException
    {
        beginLayer(g);
        renderShape(g, ellipse);
        finishLayer(g);
    }

    public Shape getShape()
    {
        return shapeToParent(ellipse);
    }

    public Rectangle2D getBoundingBox() throws SVGException
    {
        return boundsToParent(includeStrokeInBounds(ellipse.getBounds2D()));
    }
    
    /**
     * Updates all attributes in this diagram associated with a time event.
     * Ie, all attributes with track information.
     * @return - true if this node has changed state as a result of the time
     * update
     */
    public boolean updateTime(double curTime) throws SVGException
    {
//        if (trackManager.getNumTracks() == 0) return false;
        boolean changeState = super.updateTime(curTime);

        //Get current values for parameters
        StyleAttribute sty = new StyleAttribute();
        boolean shapeChange = false;
        
        if (getPres(sty.setName("cx")))
        {
            float newCx = sty.getFloatValueWithUnits();
            if (newCx != cx)
            {
                cx = newCx;
                shapeChange = true;
            }
        }
        
        if (getPres(sty.setName("cy")))
        {
            float newCy = sty.getFloatValueWithUnits();
            if (newCy != cy)
            {
                cy = newCy;
                shapeChange = true;
            }
        }
        
        if (getPres(sty.setName("rx")))
        {
            float newRx = sty.getFloatValueWithUnits();
            if (newRx != rx)
            {
                rx = newRx;
                shapeChange = true;
            }
        }
        
        if (getPres(sty.setName("ry")))
        {
            float newRy = sty.getFloatValueWithUnits();
            if (newRy != ry)
            {
                ry = newRy;
                shapeChange = true;
            }
        }
        
        if (shapeChange)
        {
            build();
//            ellipse.setFrame(cx - rx, cy - ry, rx * 2f, ry * 2f);
//            return true;
        }
        
        return changeState || shapeChange;
    }
}
