/*
 * SVGTransform.java
 *
 * Created on April 12, 2007, 1:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.coordSystems;

import com.kitfox.salamander.svg.basic.SVGDataType;

/**
 * SVGTransform is the interface for one of the component transformations within a SVGTransformList; thus, a SVGTransform object corresponds to a single component (e.g., "scale(..)" or "matrix(...)") within a transform attribute specification.
 * @author kitfox
 */
public interface SVGTransform extends SVGDataType
{
    public static enum Type {
        /**
         * The unit type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * A "matrix(...)" transformation.
         */
        MATRIX, 
        /**
         * A "translate(...)" transformation.
         */
        TRANSLATE, 
        /**
         * A "scale(...)" transformation.
         */
        SCALE, 
        /**
         * A "rotate(...)" transformation.
         */
        ROTATE, 
        /**
         * A "skewX(...)" transformation.
         */
        SKEWX, 
        /**
         * A "skewY(...)" transformation.
         */
        SKEWY};
    
    /**
     * The type of the value as specified by one of the constants specified above.
     */
    public Type getType();
    /**
     * The matrix that represents this transformation.
     * For SVG_TRANSFORM_MATRIX, the matrix contains the a, b, c, d, e, f values supplied by the user.
     * For SVG_TRANSFORM_TRANSLATE, e and f represent the translation amounts (a=1,b=0,c=0,d=1).
     * For SVG_TRANSFORM_SCALE, a and d represent the scale amounts (b=0,c=0,e=0,f=0).
     * For SVG_TRANSFORM_ROTATE, SVG_TRANSFORM_SKEWX and SVG_TRANSFORM_SKEWY, a, b, c and d represent the matrix which will result in the given transformation (e=0,f=0).
     */
    public SVGMatrix getMatrix();
    /**
     * A convenience attribute for SVG_TRANSFORM_ROTATE, SVG_TRANSFORM_SKEWX and SVG_TRANSFORM_SKEWY. It holds the angle that was specified.
     * For SVG_TRANSFORM_MATRIX, SVG_TRANSFORM_TRANSLATE and SVG_TRANSFORM_SCALE, angle will be zero.
     */
    public float getAngle();
    
    /**
     * Sets the transform type to SVG_TRANSFORM_MATRIX, with parameter matrix defining the new transformation.
     * @param matrix The new matrix for the transformation.
     */
    public void setMatrix(SVGMatrix matrix);
    /**
     * Sets the transform type to SVG_TRANSFORM_TRANSLATE, with parameters tx and ty defining the translation amounts.
     * @param tx The translation amount in X.
     * @param ty The translation amount in Y.
     */
    public void setTranslate(float tx, float ty);
    /**
     * Sets the transform type to SVG_TRANSFORM_SCALE, with parameters sx and sy defining the scale amounts.
     * @param sx The scale factor in X.
     * @param sy The scale factor in Y.
     */
    public void setScale(float sx, float sy);
    /**
     * Sets the transform type to SVG_TRANSFORM_ROTATE, with parameter angle defining the rotation angle and parameters cx and cy defining the optional centre of rotation.
     * @param angle The rotation angle.
     * @param cx The x coordinate of centre of rotation.
     * @param cy The y coordinate of centre of rotation.
     */
    public void setRotate(float angle, float cx, float cy);
    /**
     * Sets the transform type to SVG_TRANSFORM_SKEWX, with parameter angle defining the amount of skew.
     * @param angle The skew angle.
     */
    public void setSkewX(float angle);
    /**
     * Sets the transform type to SVG_TRANSFORM_SKEWY, with parameter angle defining the amount of skew.
     * @param angle The skew angle.
     */
    public void setSkewY(float angle);
}
