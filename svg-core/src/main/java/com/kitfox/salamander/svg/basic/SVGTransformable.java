/*
 * SVGTransformable.java
 *
 * Created on April 12, 2007, 3:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.coordSystems.SVGAnimatedTransformList;

/**
 * 
 * Interface SVGTransformable contains properties and methods that apply to all elements which have attribute transform.
 * @author kitfox
 */
public interface SVGTransformable extends SVGLocatable
{
    /**
     * Corresponds to attribute transform on the given element.
     */
    public SVGAnimatedTransformList getTransform();
}
