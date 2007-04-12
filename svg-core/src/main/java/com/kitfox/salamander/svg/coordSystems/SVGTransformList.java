/*
 * SVGTransformList.java
 *
 * Created on April 12, 2007, 1:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.coordSystems;

import com.kitfox.salamander.svg.basic.SVGList;


/**
 * <p>This interface defines a list of SVGTransform objects.</p>
 * 
 * <p>The SVGTransformList and SVGTransform interfaces correspond to the various attributes which specify a set of transformations, such as the transform attribute which is available for many of SVG's elements.</p>
 * 
 * <p>SVGTransformList has the same attributes and methods as other SVGxxxList interfaces. Implementers may consider using a single base class to implement the various SVGxxxList interfaces.</p>
 * @author kitfox
 */
public interface SVGTransformList extends SVGList<SVGTransform>
{
    /**
     * Creates an SVGTransform object which is initialized to transform of type SVG_TRANSFORM_MATRIX and whose values are the given matrix.
     * @param matrix The matrix which defines the transformation.
     * @return The returned SVGTransform  object.
     */
    public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);
    /**
     * Consolidates the list of separate SVGTransform objects by multiplying the equivalent transformation matrices together to result in a list consisting of a single SVGTransform  object of type SVG_TRANSFORM_MATRIX.
     * @return The resulting SVGTransform  object which becomes single item in the list. If the list was empty, then a value of null is returned.
     */
    public SVGTransform consolidate();
}
