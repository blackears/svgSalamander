/*
 * SVGColor.java
 *
 * Created on April 12, 2007, 2:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.SVGException;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;

/**
 * 
 * The SVGColor interface corresponds to color value definition for properties 'stop-color', 'flood-color' and 'lighting-color' and is a base class for interface SVGPaint. It incorporates SVG's extended notion of color, which incorporates ICC-based color specifications.
 * 
 * Interface SVGColor does not correspond to the <color> basic data type. For the <color> basic data type, the applicable DOM interfaces are defined in [DOM2-CSS]; in particular, see the [DOM2-CSS-RGBCOLOR].
 * @author kitfox
 */
public interface SVGColor extends SVGDataType, CSSValue
{
    public static enum Type {
        /**
         * The color type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * An sRGB color has been specified without an alternative ICC color specification.
         */
        RGBCOLOR, 
        /**
         * An sRGB color has been specified along with an alternative ICC color specification.
         */
        RGBCOLOR_ICCCOLOR, 
        /**
         * Corresponds to when keyword 'currentColor' has been specified.
         */
        CURRENTCOLOR};
    
    /**
     * The type of the value as specified by one of the constants specified above.
     */
    public Type getColorType();
    /**
     * The color specified in the sRGB color space.
     */
    public RGBColor getRGBColor();
    /**
     * The alternate ICC color specification.
     */
    public SVGICCColor getICCColor();
    
    /**
     * Modifies the color value to be the specified sRGB color without an alternate ICC color specification.
     * @param rgbColor The new color value.
     * @throws com.kitfox.salamander.svg.SVGException SVG_INVALID_VALUE_ERR: Raised if one of the parameters has an invalid value.
     */
    public void setRGBColor(DOMString rgbColor) throws SVGException;
    /**
     * Modifies the color value to be the specified sRGB color with an alternate ICC color specification.
     * @param rgbColor The new color value.
     * @param iccColor The alternate ICC color specification.
     * @throws com.kitfox.salamander.svg.SVGException SVG_INVALID_VALUE_ERR: Raised if one of the parameters has an invalid value.
     */
    public void setRGBColorICCCOlor(DOMString rgbColor, DOMString iccColor) throws SVGException;
    /**
     * Sets the colorType as specified by the parameters. If colorType requires an RGBColor, then rgbColor must be a valid RGBColor object; otherwise, rgbColor must be null. If colorType requires an SVGICCColor, then iccColor must be a valid SVGICCColor object; otherwise, iccColor must be null.
     * @param colorType One of the defined constants for colorType.
     * @param rgbColor The specification of an sRGB color, or null.
     * @param iccColor The specification of an ICC color, or null.
     * @throws com.kitfox.salamander.svg.SVGException SVG_INVALID_VALUE_ERR: Raised if one of the parameters has an invalid value.
     */
    public void setColor(Type colorType, DOMString rgbColor, DOMString iccColor) throws SVGException;
    
}
