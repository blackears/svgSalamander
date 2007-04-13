/*
 * SVGAnimatedPoints.java
 *
 * Created on April 12, 2007, 9:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.shape;

import com.kitfox.salamander.svg.coordSystems.SVGPointList;

/**
 * 
 * The SVGAnimatedPoints  interface supports elements which have a 'points' attribute which holds a list of coordinate values and which support the ability to animate that attribute.
 * 
 * Additionally, the 'points' attribute on the original element accessed via the XML DOM (e.g., using the getAttribute() method call) will reflect any changes made to points.
 * @author kitfox
 */
public interface SVGAnimatedPoints
{
    /**
     * Provides access to the base (i.e., static) contents of the points  attribute.
     */
    public SVGPointList getPoints();
    /**
     * Provides access to the current animated contents of the points attribute. If the given attribute or property is being animated, contains the current animated value of the attribute or property. If the given attribute or property is not currently being animated, contains the same value as 'points'.
     */
    public SVGPointList getAnimatedPoints();
}
