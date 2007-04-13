/*
 * SVGAnimatedPathData.java
 *
 * Created on April 12, 2007, 9:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.paths;

/**
 * 
 * The SVGAnimatedPathData interface supports elements which have a 'd' attribute which holds SVG path data, and supports the ability to animate that attribute.
 * 
 * The SVGAnimatedPathData interface provides two lists to access and modify the base (i.e., static) contents of the d attribute:
 * 
 *    * DOM attribute pathSegList provides access to the static/base contents of the d attribute in a form which matches one-for-one with SVG's syntax.
 *    * DOM attribute normalizedPathSegList provides normalized access to the static/base contents of the d attribute where all path data commands are expressed in terms of the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z).
 * 
 * and two lists to access the current animated values of the d attribute:
 * 
 *    * DOM attribute animatedPathSegList provides access to the current animated contents of the d attribute in a form which matches one-for-one with SVG's syntax.
 *    * DOM attribute animatedNormalizedPathSegList provides normalized access to the current animated contents of the d attribute where all path data commands are expressed in terms of the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z).
 * 
 * Each of the two lists are always kept synchronized. Modifications to one list will immediately cause the corresponding list to be modified. Modifications to normalizedPathSegList might cause entries in pathSegList to be broken into a set of normalized path segments.
 * 
 * Additionally, the 'd' attribute on the 'path' element accessed via the XML DOM (e.g., using the getAttribute() method call) will reflect any changes made to pathSegList or normalizedPathSegList.
 * @author kitfox
 */
public interface SVGAnimatedPathData
{
    /**
     * Provides access to the base (i.e., static) contents of the d attribute in a form which matches one-for-one with SVG's syntax. Thus, if the d attribute has an "absolute moveto (M)" and an "absolute arcto (A)" command, then pathSegList will have two entries: a SVG_PATHSEG_MOVETO_ABS and a SVG_PATHSEG_ARC_ABS.
     */
    public SVGPathSegList getPathSegList();
    /**
     * 
     * 
     *    Provides access to the base (i.e., static) contents of the d attribute in a form where all path data commands are expressed in terms of the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z). Thus, if the d attribute has an "absolute moveto (M)" and an "absolute arcto (A)" command, then pathSegList will have one SVG_PATHSEG_MOVETO_ABS entry followed by a series of SVG_PATHSEG_LINETO_ABS entries which approximate the arc. This alternate representation is available to provide a simpler interface to developers who would benefit from a more limited set of commands.
     * 
     *    The only valid SVGPathSeg types are SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z).
     */
    public SVGPathSegList getNormalizedPathSegList();
    /**
     * Provides access to the current animated contents of the d attribute in a form which matches one-for-one with SVG's syntax. If the given attribute or property is being animated, contains the current animated value of the attribute or property, and both the object itself and its contents are readonly. If the given attribute or property is not currently being animated, contains the same value as 'pathSegList'.
     */
    public SVGPathSegList getAnimatedPathSegList();
    /**
     * Provides access to the current animated contents of the d attribute in a form where all path data commands are expressed in terms of the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z). If the given attribute or property is being animated, contains the current animated value of the attribute or property, and both the object itself and its contents are readonly. If the given attribute or property is not currently being animated, contains the same value as 'normalizedPathSegList'.
     */
    public SVGPathSegList getAnimatedNormalizedPathSegList();
}
