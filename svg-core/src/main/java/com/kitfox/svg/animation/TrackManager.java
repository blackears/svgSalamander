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

import com.kitfox.svg.*;

import java.io.Serializable;

/**
 * Every element contains tracks, which manage the animation.  There is one track
 * for every parameter with animation, and each track in turn is composed of
 * many events.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class TrackManager implements Serializable
{
    public static final long serialVersionUID = 0;
    
    static class TrackKey
    {
        String name;
        int type;
        
        TrackKey(AnimationElement base)
        {
            this(base.getAttribName(), base.getAttribType());
        }
        
        TrackKey(String name, int type)
        {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public int hashCode()
        {
            int hash = name == null ? 0 : name.hashCode();
            hash = hash * 97 + type;
            return hash;
        }

        @Override
        public boolean equals(Object obj) 
        {
            if (!(obj instanceof TrackKey)) return false;
            TrackKey key = (TrackKey)obj;
            return key.type == type && key.name.equals(name);
        }
    }
    
    HashMap<TrackKey, TrackBase> tracks = new HashMap<TrackKey, TrackBase>();
    
    /** Creates a new instance of TrackManager */
    public TrackManager()
    {
    }
    
    /**
     * Adds a new animation element to this track
     */
    public void addTrackElement(AnimationElement element) throws SVGElementException
    {
        TrackKey key = new TrackKey(element);
        
        TrackBase track = (TrackBase)tracks.get(key);
        
        if (track == null)
        {
            //Create a track for this element
            if (element instanceof Animate)
            {
                switch (((Animate)element).getDataType())
                {
                    case Animate.DT_REAL:
                        track = new TrackDouble(element);
                        break;
                    case Animate.DT_COLOR:
                        track = new TrackColor(element);
                        break;
                    case Animate.DT_PATH:
                        track = new TrackPath(element);
                        break;
                    default:
                        throw new RuntimeException("");
                }
            }
            else if (element instanceof AnimateColor)
            {
                track = new TrackColor(element);
            }
            else if (element instanceof AnimateTransform || element instanceof AnimateMotion)
            {
                track = new TrackTransform(element);
            }
            
            tracks.put(key, track);
        }
  
        track.addElement(element);
    }
    
    public TrackBase getTrack(String name, int type)
    {
        //Handle AUTO, which will match either CSS or XML (in that order)
        if (type == AnimationElement.AT_AUTO)
        {
            TrackBase t = getTrack(name, AnimationElement.AT_CSS);
            if (t != null) return t;
            t = getTrack(name, AnimationElement.AT_XML);
            if (t != null) return t;
            return null;
        }
        
        //Get requested attribute
        TrackKey key = new TrackKey(name, type);
        TrackBase t = (TrackBase)tracks.get(key);
        if (t != null) return t;
        
        //If that didn't exist, see if one exists of type AUTO
        key = new TrackKey(name, AnimationElement.AT_AUTO);
        return (TrackBase)tracks.get(key);
    }
    
    public int getNumTracks()
    {
        return tracks.size();
    }
    
    public Iterator<TrackBase> iterator()
    {
        return tracks.values().iterator();
    }
}
