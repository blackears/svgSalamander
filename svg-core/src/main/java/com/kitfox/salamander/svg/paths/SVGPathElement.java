/*
 * SVGPathElement.java
 *
 * Created on April 12, 2007, 9:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template the editor.
 */

package com.kitfox.salamander.svg.paths;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGTransformable;
import com.kitfox.salamander.svg.coordSystems.SVGPoint;
import org.w3c.dom.events.EventTarget;

/**
 * 
 * The SVGPathElement  interface corresponds to the 'path' element.
 * @author kitfox
 */
public interface SVGPathElement extends SVGElement, SVGTests, SVGLangSpace,
        SVGExternalResourcesRequired, SVGStylable, SVGTransformable,
        EventTarget, SVGAnimatedPathData
{
    /**
     * Corresponds to attribute pathLength on the given 'path' element.
     */
    public SVGAnimatedNumber getPathLength();
    /**
     * Returns the user agent's computed value for the total length of the path using the user agent's distance-along-a-path algorithm, as a distance in the current user coordinate system.
     * @return The total length of the path.
     */
    public float getTotalLength();
    /**
     * Returns the (x,y) coordinate in user space which is distance units along the path, utilizing the user agent's distance-along-a-path algorithm.
     * @param distance The distance along the path, relative to the start of the path, as a distance in the current user coordinate system.
     * @return The returned point in user space.
     */
    public SVGPoint getPointAtLength(float distance);
    /**
     * Returns the index into pathSegList which is distance units along the path, utilizing the user agent's distance-along-a-path algorithm.
     * @param distance The distance along the path, relative to the start of the path, as a distance in the current user coordinate system.
     * @return The index of the path segment, where the first path segment is number 0.
     */
    public int getPathSegAtLength(float distance);
    /**
     * Returns a stand-alone, parentless SVGPathSegClosePath object.
     * @return A stand-alone, parentless SVGPathSegClosePath object.
     */
    public SVGPathSegClosePath createSVGPathSegClosePath();
    /**
     * Returns a stand-alone, parentless SVGPathSegMovetoAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegMovetoAbs object.
     */
    public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegMovetoRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegMovetoRel object.
     */
    public SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegLinetoAbs object.
     */
    public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegLinetoRel object.
     */
    public SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoCubicAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @param x1 The absolute X coordinate for the first control point.
     * @param y1 The absolute Y coordinate for the first control point.
     * @param x2 The absolute X coordinate for the second control point.
     * @param y2 The absolute Y coordinate for the second control point.
     * @return A stand-alone, parentless SVGPathSegCurvetoCubicAbs object.
     */
    public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x, float y, float x1, float y1, float x2, float y2);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoCubicRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @param x1 The relative X coordinate for the first control point.
     * @param y1 The relative Y coordinate for the first control point.
     * @param x2 The relative X coordinate for the second control point.
     * @param y2 The relative Y coordinate for the second control point.
     * @return A stand-alone, parentless SVGPathSegCurvetoCubicRel object.
     */
    public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x, float y, float x1, float y1, float x2, float y2);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoQuadraticAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @param x1 The absolute X coordinate for the control point.
     * @param y1 The absolute Y coordinate for the control point.
     * @return A stand-alone, parentless SVGPathSegCurvetoQuadraticAbs object.
     */
    public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoQuadraticRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @param x1 The relative X coordinate for the control point.
     * @param y1 The relative Y coordinate for the control point.
     * @return A stand-alone, parentless SVGPathSegCurvetoQuadraticRel object.
     */
    public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float x, float y, float x1, float y1);
    /**
     * Returns a stand-alone, parentless SVGPathSegArcAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @param r1 The x-axis radius for the ellipse (i.e., r1).
     * @param r2 The y-axis radius for the ellipse (i.e., r2).
     * @param angle The rotation angle in degrees for the ellipse's x-axis relative to the x-axis of the user coordinate system.
     * @param largeArcFlag The value for the large-arc-flag parameter.
     * @param sweepFlag The value for the sweep-flag parameter.
     * @return   	A stand-alone, parentless SVGPathSegArcAbs object.	A stand-alone, parentless SVGPathSegArcAbs object.
     */
    public SVGPathSegCurvetoArcAbs createSVGPathSegArcAbs(float x, float y, float r1, float r2, float angle, boolean largeArcFlag, boolean sweepFlag);
    /**
     * Returns a stand-alone, parentless SVGPathSegArcRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @param r1 The x-axis radius for the ellipse (i.e., r1).
     * @param r2 The y-axis radius for the ellipse (i.e., r2).
     * @param angle The rotation angle in degrees for the ellipse's x-axis relative to the x-axis of the user coordinate system.
     * @param largeArcFlag The value for the large-arc-flag parameter.
     * @param sweepFlag The value for the sweep-flag parameter.
     * @return A stand-alone, parentless SVGPathSegArcRel object.
     */
    public SVGPathSegCurvetoArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle, boolean largeArcFlag, boolean sweepFlag);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoHorizontalAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegLinetoHorizontalAbs object.
     */
    public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoHorizontalRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegLinetoHorizontalRel object.
     */
    public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoVerticalAbs object.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegLinetoVerticalAbs object.
     */
    public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegLinetoVerticalRel object.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @return   	A stand-alone, parentless SVGPathSegLinetoVerticalRel object.
     */
    public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoCubicSmoothAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @param x2 The absolute X coordinate for the second control point.
     * @param y2 The absolute Y coordinate for the second control point.
     * @return   	A stand-alone, parentless SVGPathSegCurvetoCubicSmoothAbs object.
     */
    public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x, float y, float x2, float y2);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoCubicSmoothRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @param x2 The relative X coordinate for the second control point.
     * @param y2 The relative Y coordinate for the second control point.
     * @return A stand-alone, parentless SVGPathSegCurvetoCubicSmoothRel object.
     */
    public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x, float y, float x2, float y2);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoQuadraticSmoothAbs object.
     * @param x The absolute X coordinate for the end point of this path segment.
     * @param y The absolute Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegCurvetoQuadraticSmoothAbs object.
     */
    public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y);
    /**
     * Returns a stand-alone, parentless SVGPathSegCurvetoQuadraticSmoothRel object.
     * @param x The relative X coordinate for the end point of this path segment.
     * @param y The relative Y coordinate for the end point of this path segment.
     * @return A stand-alone, parentless SVGPathSegCurvetoQuadraticSmoothRel object.
     */
    public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y);
}
