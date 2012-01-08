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
        
        public int hashCode()
        {
            int hash = name == null ? 0 : name.hashCode();
            hash = hash * 97 + type;
            return hash;
        }

        public boolean equals(Object obj) 
        {
            if (!(obj instanceof TrackKey)) return false;
            TrackKey key = (TrackKey)obj;
            return key.type == type && key.name.equals(name);
        }
    }
    
    HashMap tracks = new HashMap();
    
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
    
    public Iterator iterator()
    {
        return tracks.values().iterator();
    }
}
