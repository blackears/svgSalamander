/*
 * SVGViewSpec.java
 *
 * Created on April 12, 2007, 3:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.coordSystems.SVGTransformList;

/**
 * 
 * The interface corresponds to an SVG View Specification.
 * @author kitfox
 */
public interface SVGViewSpec extends SVGZoomAndPan, SVGFitToViewBox
{
    /**
     * Corresponds to the transform setting on the SVG View Specification.
     */
    public SVGTransformList getTransform();
    /**
     * Corresponds to the viewTarget setting on the SVG View Specification.
     */
    public SVGElement getViewTarget();
    /**
     * Corresponds to the viewBox setting on the SVG View Specification.
     */
    public SVGString getViewBoxString();
    /**
     * Corresponds to the preserveAspectRatio setting on the SVG View Specification.
     */
    public SVGString getPreserveAspectRatioString();
    /**
     * Corresponds to the transform setting on the SVG View Specification.
     */
    public SVGString getTransformString();
    /**
     * Corresponds to the viewTarget setting on the SVG View Specification.
     */
    public SVGString getTargetString();
}
