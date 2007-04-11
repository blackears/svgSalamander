/*
 * AnimateTimeEval.java
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
 *
 * Created on September 21, 2004, 1:31 PM
 */

package com.kitfox.svg.animation;

/**
 *
 * @author  kitfox
 */
public class AnimationTimeEval
{
    /**
     * Value on [0..1] representing the interpolation value of queried animation
     * element, or Double.NaN if element does not provide a valid evalutaion
     */
    public double interp;
    
    /**
     * Number of completed repetitions
     */
    public int rep;
    
    /**
     * True if this evaluation is in a frozen state; ie, past the end of the
     * track and held in the "freeze" state.
     */
//    public boolean pastEnd;
    
    /** Creates a new instance of AnimateTimeEval */
    public AnimationTimeEval()
    {
    }
    
//    public void set(double interp, int rep, boolean pastEnd)
    public void set(double interp, int rep)
    {
        this.interp = interp;
        this.rep = rep;
//        this.pastEnd = pastEnd;
    }
}
