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
    final ArrayList<AnimationElement> animEvents = new ArrayList<AnimationElement>();
    
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
