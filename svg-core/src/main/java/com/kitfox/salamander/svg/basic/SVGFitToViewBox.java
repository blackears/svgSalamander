/*
 * SVGFitToViewBox.java
 *
 * Created on April 12, 2007, 3:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.coordSystems.SVGAnimatedPreserveAspectRatio;

/**
 * 
 * Interface SVGFitToViewBox defines DOM attributes that apply to elements which have XML attributes viewBox and preserveAspectRatio.
 * @author kitfox
 */
public interface SVGFitToViewBox
{
    /**
     * Corresponds to attribute viewBox on the given element.
     */
    public SVGAnimatedRect getViewBox();
    /**
     * Corresponds to attribute preserveAspectRatio on the given element.
     */
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio();
}
