/*
 * SVGLocatable.java
 *
 * Created on April 12, 2007, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.SVGException;
import com.kitfox.salamander.svg.coordSystems.SVGMatrix;

/**
 * 
 * Interface SVGLocatable  is for all elements which either have a transform attribute or don't have a transform attribute but whose content can have a bounding box in current user space.
 * @author kitfox
 */
public interface SVGLocatable
{
    /**
     * The element which established the current viewport. Often, the nearest ancestor 'svg' element. Null if the current element is the outermost 'svg' element.
     */
    public SVGElement getNearestViewportElement();
    /**
     * The farthest ancestor 'svg' element. Null if the current element is the outermost 'svg' element.
     */
    public SVGElement getFarthestViewportElement();
    
    /**
     * Returns the tight bounding box in current user space (i.e., after application of the transform attribute, if any) on the geometry of all contained graphics elements, exclusive of stroke-width and filter effects).
     * @return An SVGRect object that defines the bounding box.
     */
    public SVGRect getBBox();
    /**
     * Returns the transformation matrix from current user units (i.e., after application of the transform attribute, if any) to the viewport coordinate system for the nearestViewportElement.
     * @return An SVGMatrix object that defines the CTM.
     */
    public SVGMatrix getCTM();
    /**
     * Returns the transformation matrix from current user units (i.e., after application of the transform attribute, if any) to the parent user agent's notice of a "pixel". For display devices, ideally this represents a physical screen pixel. For other devices or environments where physical pixel sizes are not known, then an algorithm similar to the CSS2 definition of a "pixel" can be used instead.
     * @return An SVGMatrix object that defines the given transformation matrix.
     */
    public SVGMatrix getScreenCTM();
    /**
     * Returns the transformation matrix from the user coordinate system on the current element (after application of the transform attribute, if any) to the user coordinate system on parameter element  (after application of its transform attribute, if any).
     * @param element The target element.
     * @return An SVGMatrix object that defines the transformation.
     * @throws com.kitfox.salamander.svg.SVGException SVG_MATRIX_NOT_INVERTABLE: Raised if the currently defined transformation matrices make it impossible to compute the given matrix (e.g., because one of the transformations is singular).
     */
    public SVGMatrix getTransformToElement(SVGElement element) throws SVGException;
    
}
