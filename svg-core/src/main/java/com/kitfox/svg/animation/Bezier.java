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
 * Created on January 14, 2005, 4:08 AM
 */

package com.kitfox.svg.animation;

import java.awt.geom.*;

/**
 * http://mathworld.wolfram.com/BezierCurve.html
 * @author kitfox
 */
public class Bezier
{
    double length;
    double[] coord;

    public Bezier(double sx, double sy, double[] coords, int numCoords)
    {
        setCoords(sx, sy, coords, numCoords);
    }
    
    public void setCoords(double sx, double sy, double[] coords, int numCoords)
    {
        coord = new double[numCoords * 2 + 2];
        coord[0] = sx;
        coord[1] = sy;
        for (int i = 0; i < numCoords; i++)
        {
            coord[i * 2 + 2] = coords[i * 2];
            coord[i * 2 + 3] = coords[i * 2 + 1];
        }
        
        calcLength();        
    }
    
    /**
     * Retuns aproximation of the length of the bezier
     */
    public double getLength()
    {
        return length;
    }
    
    private void calcLength()
    {
        length = 0;
        for (int i = 2; i < coord.length; i += 2)
        {
            length += lineLength(coord[i - 2], coord[i - 1], coord[i], coord[i + 1]);
        }
    }
    
    private double lineLength(double x1, double y1, double x2, double y2)
    {
        double dx = x2 - x1, dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public Point2D.Double getFinalPoint(Point2D.Double point)
    {
        point.x = coord[coord.length - 2];
        point.y = coord[coord.length - 1];
        return point;
    }
    
    public Point2D.Double eval(double param, Point2D.Double point)
    {
        point.x = 0;
        point.y = 0;
        int numKnots = coord.length / 2;
        
        for (int i = 0; i < numKnots; i++)
        {
            double scale = bernstein(numKnots - 1, i, param);
            point.x += coord[i * 2] * scale;
            point.y += coord[i * 2 + 1] * scale;
        }
        
        return point;
    }
    
    /**
     * Calculates the bernstein polynomial for evaluating parametric bezier
     * @param numKnots - one less than number of knots in this curve hull
     * @param knotNo - knot we are evaluating Bernstein for
     * @param param - Parametric value we are evaluating at
     */
    private double bernstein(int numKnots, int knotNo, double param)
    {
        double iParam = 1 - param;
        //Faster evaluation for easy cases:
        switch (numKnots)
        {
            case 0:
                return 1;
            case 1:
            {
                switch (knotNo)
                {
                    case 0:
                        return iParam;
                    case 1:
                        return param;
                }
                break;
            }
            case 2:
            {
                switch (knotNo)
                {
                    case 0:
                        return iParam * iParam;
                    case 1:
                        return 2 * iParam * param;
                    case 2:
                        return param * param;
                }
                break;
            }
            case 3:
            {
                switch (knotNo)
                {
                    case 0:
                        return iParam * iParam * iParam;
                    case 1:
                        return 3 * iParam * iParam * param;
                    case 2:
                        return 3 * iParam * param * param;
                    case 3:
                        return param * param * param;
                }
                break;
            }
        }
        
        //If this bezier has more than four points, calculate bernstein the hard way
        double retVal = 1;
        for (int i = 0; i < knotNo; i++)
        {
            retVal *= param;
        }
        for (int i = 0; i < numKnots - knotNo; i++)
        {
            retVal *= iParam;
        }
        retVal *= choose(numKnots, knotNo);
        
        return retVal;
    }
    
    
    
    private int choose(int num, int denom)
    {
        int denom2 = num - denom;
        if (denom < denom2)
        {
            int tmp = denom;
            denom = denom2;
            denom2 = tmp;
        }
        
        int prod = 1;
        for (int i = num; i > denom; i--)
        {
            prod *= num;
        }
        
        for (int i = 2; i <= denom2; i++)
        {
            prod /= i;
        }
        
        return prod;
    }
}
