/*
 * SVGPaint.java
 *
 * Created on April 12, 2007, 2:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.paint;

import com.kitfox.salamander.svg.SVGException;
import com.kitfox.salamander.svg.basic.SVGDataType;
import com.kitfox.salamander.svg.DOMString;

/**
 * 
 * The SVGPaint interface corresponds to basic type <paint> and represents the values of properties 'fill' and 'stroke'.
 * @author kitfox
 */
public interface SVGPaint extends SVGDataType
{
    public static enum Type {
        /**
         * The paint type is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
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
         * Corresponds to a 'none' value on a <paint> specification.
         */
        NONE, 
        /**
         * Corresponds to a 'currentColor' value on a <paint> specification.
         */
        CURRENTCOLOR, 
        /**
         * A URI has been specified, along with an explicit 'none' as the backup paint method in case the URI is unavailable or invalid.
         */
        URI_NONE, 
        /**
         * A URI has been specified, along with 'currentColor' as the backup paint method in case the URI is unavailable or invalid.
         */
        URI_CURRENTCOLOR, 
        /**
         * A URI has been specified, along with an sRGB color as the backup paint method in case the URI is unavailable or invalid.
         */
        URI_RGBCOLOR, 
        /**
         * A URI has been specified, along with both an sRGB color and alternate ICC color as the backup paint method in case the URI is unavailable or invalid.
         */
        URI_RGBCOLOR_ICCCOLOR, 
        /**
         * Only a URI has been specified.
         */
        URI};
    
    /**
     * The type of paint, identified by one of the constants above.
     */
    public Type getPaintType();
    /**
     * When the paintType specifies a URI, this attribute holds the URI string. When the paintType does not specify a URI, this attribute is null.
     */
    public DOMString getUri();
    /**
     * Sets the paintType to SVG_PAINTTYPE_URI_NONE and sets uri to the specified value.
     * @param uri The URI for the desired paint server.
     */
    public void setUri(DOMString uri);
    /**
     * Sets the paintType as specified by the parameters. If paintType requires a URI, then uri must be non-null and a valid string; otherwise, uri must be null. If paintType requires an RGBColor, then rgbColor must be a valid RGBColor object; otherwise, rgbColor must be null. If paintType requires an SVGICCColor, then iccColor must be a valid SVGICCColor object; otherwise, iccColor must be null.
     * @param paintType One of the defined constants for paintType.
     * @param uri The URI for the desired paint server, or null.
     * @param rgbColor The specification of an sRGB color, or null.
     * @param iccColor The specification of an ICC color, or null.
     * @throws com.kitfox.salamander.svg.SVGException SVG_INVALID_VALUE_ERR: Raised if one of the parameters has an invalid value.
     */
    public void setPaint(Type paintType, String uri, String rgbColor, String iccColor) throws SVGException;
}
