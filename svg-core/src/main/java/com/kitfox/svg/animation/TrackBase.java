/*
 * TrackManager.java
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
 * Created on August 15, 2004, 11:34 PM
 */

package com.kitfox.svg.animation;

import java.util.*;

import com.kitfox.svg.xml.*;
import com.kitfox.svg.*;

/**
 * A track holds the animation events for a single parameter of a single SVG 
 * element.  It also contains the default value for the element, should the
 * user want to see the 'unanimated' value.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
abstract public class TrackBase
{
    protected final String attribName;
    protected final int attribType;  //AnimationElement.AT_*
 
    /** Element we're animating */
    protected final SVGElement parent;
    
    //It doesn't make sense to sort this, since some events will depend on
    // other events - in many cases, there will be no meaningful sorted order.
    final ArrayList animEvents = new ArrayList();
    
    /** Creates a new instance of TrackManager */
//    public TrackBase(SVGElement parent)
//    {
//        this(parent, "", AnimationElement.AT_AUTO);
//    }
    
    /**
     * Creates a track that would be valid for the name and type of element
     * passed in.  Does not actually add this elemnt to the track.
     */
    public TrackBase(SVGElement parent, AnimationElement ele) throws SVGElementException
    {
        this(parent, ele.getAttribName(), ele.getAttribType());
    }
    
    public TrackBase(SVGElement parent, String attribName, int attribType) throws SVGElementException
    {
        this.parent = parent;
        this.attribName = attribName;
        this.attribType = attribType;
        
        //Make sure parent has an attribute we will write to
        if (attribType == AnimationElement.AT_AUTO 
            && !parent.hasAttribute(attribName, AnimationElement.AT_CSS)
            && !parent.hasAttribute(attribName, AnimationElement.AT_XML))
        {
            parent.addAttribute(attribName, AnimationElement.AT_CSS, "");
        }
        else if (!parent.hasAttribute(attribName, attribType))
        {
            parent.addAttribute(attribName, attribType, "");
        }
    }
    
    public String getAttribName() { return attribName; }
    public int getAttribType() { return attribType; }
    
    public void addElement(AnimationElement ele)
    {
        animEvents.add(ele);
    }
    
    /**
     * Returns a StyleAttribute representing the value of this track at the
     * passed time.  If this track does not apply, returns null.
     * @return - True if successful, false if a value could not be obtained
     */
    abstract public boolean getValue(StyleAttribute attrib, double curTime) throws SVGException;
    
}
