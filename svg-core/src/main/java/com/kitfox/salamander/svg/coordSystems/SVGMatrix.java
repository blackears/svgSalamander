/*
 * SVGMatrix.java
 *
 * Created on April 12, 2007, 1:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.coordSystems;

import com.kitfox.salamander.svg.SVGException;
import com.kitfox.salamander.svg.basic.SVGDataType;

/**
 * Many of SVG's graphics operations utilize 2x3 matrices of the form:
 * 
 * <CODE>
 * [a c e]
 * [b d f]
 * </CODE>
 * 
 * which, when expanded into a 3x3 matrix for the purposes of matrix arithmetic, become:
 * 
 * <CODE>
 * [a c e]
 * [b d f]
 * [0 0 1]
 * </CODE>
 * @author kitfox
 */
public interface SVGMatrix extends SVGDataType
{
    /**
     * The a component of the matrix.
     */
    public float getA();
    /**
     * The b component of the matrix.
     */
    public float getB();
    /**
     * The c component of the matrix.
     */
    public float getC();
    /**
     * The d component of the matrix.
     */
    public float getD();
    /**
     * The e component of the matrix.
     */
    public float getE();
    /**
     * The f component of the matrix.
     */
    public float getF();
    
    /**
     * Performs matrix multiplication. This matrix is post-multiplied by another matrix, returning the resulting new matrix.
     * @param secondMatrix The matrix which is post-multiplied to this matrix.
     * @return The resulting matrix.
     */
    public SVGMatrix multiply(SVGMatrix secondMatrix);
    /**
     * Returns the inverse matrix.
     * @return The inverse matrix.
     * @throws com.kitfox.salamander.svg.SVGException SVG_MATRIX_NOT_INVERTABLE: Raised if this matrix is not invertable.
     */
    public SVGMatrix inverse() throws SVGException;
    /**
     * Post-multiplies a translation transformation on the current matrix and returns the resulting matrix.
     * @param x The distance to translate along the x-axis.
     * @param y The distance to translate along the y-axis.
     * @return The resulting matrix.
     */
    public SVGMatrix translate(float x, float y);
    /**
     * Post-multiplies a uniform scale transformation on the current matrix and returns the resulting matrix.
     * @param scaleFactor Scale factor in both X and Y.
     * @return The resulting matrix.
     */
    public SVGMatrix scale(float scaleFactor);
    /**
     * Post-multiplies a non-uniform scale transformation on the current matrix and returns the resulting matrix.
     * @param scaleFactorX Scale factor in X.
     * @param scaleFactorY Scale factor in Y.
     * @return The resulting matrix.
     */
    public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY);
    /**
     * Post-multiplies a rotation transformation on the current matrix and returns the resulting matrix.
     * @param angle Rotation angle.
     * @return The resulting matrix.
     */
    public SVGMatrix rotate(float angle);
    /**
     * Post-multiplies a rotation transformation on the current matrix and returns the resulting matrix. The rotation angle is determined by taking (+/-) atan(y/x). The direction of the vector (x,y) determines whether the positive or negative angle value is used.
     * @param x The X coordinate of the vector (x,y). Must not be zero.
     * @param y The Y coordinate of the vector (x,y). Must not be zero.
     * @return The resulting matrix.
     * @throws com.kitfox.salamander.svg.SVGException SVG_INVALID_VALUE_ERR: Raised if one of the parameters has an invalid value.
     */
    public SVGMatrix rotateFromVector(float x, float y) throws SVGException;
    /**
     * Post-multiplies the transformation [-1 0 0 1 0 0] and returns the resulting matrix.
     * @return The resulting matrix.
     */
    public SVGMatrix flipX();
    /**
     * Post-multiplies the transformation [1 0 0 -1 0 0] and returns the resulting matrix.
     * @return The resulting matrix.
     */
    public SVGMatrix flipY();
    /**
     * Post-multiplies a skewX transformation on the current matrix and returns the resulting matrix.
     * @param angle Skew angle.
     * @return The resulting matrix.
     */
    public SVGMatrix skewX(float angle);
    /**
     * Post-multiplies a skewY transformation on the current matrix and returns the resulting matrix.
     * @param angle Skew angle.
     * @return The resulting matrix.
     */
    public SVGMatrix skewY(float angle);
}
