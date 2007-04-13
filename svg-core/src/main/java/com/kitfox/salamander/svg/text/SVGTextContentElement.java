/*
 * SVGTextContentElement.java
 *
 * Created on April 13, 2007, 9:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.text;

import com.kitfox.salamander.svg.basic.SVGAnimatedEnumeration;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGRect;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.coordSystems.SVGPoint;
import org.w3c.dom.DOMException;
import org.w3c.dom.events.EventTarget;

/**
 * 
 * The SVGTextContentElement interface is inherited by various text-related interfaces, such as SVGTextElement, SVGTSpanElement, SVGTRefElement, SVGAltGlyphElement and SVGTextPathElement.
 * @author kitfox
 */
public interface SVGTextContentElement extends SVGElement,
        SVGTests,
        SVGLangSpace,
        SVGExternalResourcesRequired,
        SVGStylable,
        EventTarget
{
    public static enum LengthAdjust
    {
        /**
         * 	  	The enumeration was set to a value that is not one of predefined types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * Corresponds to value spacing.
         */
        SPACING, 
        /**
         * 	  	Corresponds to value spacingAndGlyphs.
         */
        SPACING_AND_GLYPHS};
    /**
     * Corresponds to attribute textLength on the given element.
     */
    public SVGAnimatedLength getTextLength();
    /**
     * Corresponds to attribute lengthAdjust on the given element. The value must be one of the length adjust constants specified above.
     */
    public SVGAnimatedEnumeration getLengthAdjust();
    
    /**
     * Returns the total number of characters to be rendered within the current element. Includes characters which are included via a 'tref' reference.
     * @return Total number of characters.
     */
    public int getNumberOfChars();
    /**
     * The total sum of all of the advance values from rendering all of the characters within this element, including the advance value on the glyphs (horizontal or vertical), the effect of properties 'kerning', 'letter-spacing' and 'word-spacing' and adjustments due to attributes dx and dy on 'tspan' elements. For non-rendering environments, the user agent shall make reasonable assumptions about glyph metrics.
     * @return The text advance distance.
     */
    public float getComputedTextLength();
    /**
     * The total sum of all of the advance values from rendering the specified substring of the characters, including the advance value on the glyphs (horizontal or vertical), the effect of properties 'kerning', 'letter-spacing' and 'word-spacing' and adjustments due to attributes dx and dy on 'tspan' elements. For non-rendering environments, the user agent shall make reasonable assumptions about glyph metrics.
     * @param charnum The index of the first character in the substring, where the first character has an index of 0.
     * @param nchars The number of characters in the substring.
     * @return The text advance distance.
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum+nchars is greater than or equal to the number of characters at this node.
     */
    public float getSubStringLength(long charnum, long nchars) throws DOMException;
    /**
     * Returns the current text position before rendering the character in the user coordinate system for rendering the glyph(s) that correspond to the specified character. The current text position has already taken into account the effects of any inter-character adjustments due to properties 'kerning', 'letter-spacing' and 'word-spacing' and adjustments due to attributes x, y, dx and dy. If multiple consecutive characters are rendered inseparably (e.g., as a single glyph or a sequence of glyphs), then each of the inseparable characters will return the start position for the first glyph.
     * @param charnum The index of the character, where the first character has an index of 0.
     * @return The character's start position.
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum is greater than or equal to the number of characters at this node.
     */
    public SVGPoint getStartPositionOfChar(long charnum) throws DOMException;
    /**
     * Returns the current text position after rendering the character in the user coordinate system for rendering the glyph(s) that correspond to the specified character. This current text position does not  take into account the effects of any inter-character adjustments to prepare for the next character, such as properties 'kerning', 'letter-spacing' and 'word-spacing' and adjustments due to attributes x, y, dx and dy. If multiple consecutive characters are rendered inseparably (e.g., as a single glyph or a sequence of glyphs), then each of the inseparable characters will return the end position for the last glyph.
     * @param charnum The index of the character, where the first character has an index of 0.
     * @return The character's end position.
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum is greater than or equal to the number of characters at this node. 
     */
    public SVGPoint getEndPositionOfChar(long charnum) throws DOMException;
    /**
     * Returns a tightest rectangle which defines the minimum and maximum X and Y values in the user coordinate system for rendering the glyph(s) that correspond to the specified character. The calculations assume that all glyphs occupy the full standard glyph cell for the font. If multiple consecutive characters are rendered inseparably (e.g., as a single glyph or a sequence of glyphs), then each of the inseparable characters will return the same extent.
     * @param charnum The index of the character, where the first character has an index of 0.
     * @return The rectangle which encloses all of the rendered glyph(s).
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum is greater than or equal to the number of characters at this node. 
     */
    public SVGRect getExtentOfChar(long charnum) throws DOMException;
    /**
     * Returns the rotation value relative to the current user coordinate system used to render the glyph(s) corresponding to the specified character. If multiple glyph(s) are used to render the given character and the glyphs each have different rotations (e.g., due to text-on-a-path), the user agent shall return an average value (e.g., the rotation angle at the midpoint along the path for all glyphs used to render this character). The rotation value represents the rotation that is supplemental to any rotation due to properties 'glyph-orientation-horizontal'  and 'glyph-orientation-vertical'; thus, any glyph rotations due to these properties are not included into the returned rotation value. If multiple consecutive characters are rendered inseparably (e.g., as a single glyph or a sequence of glyphs), then each of the inseparable characters will return the same rotation value.
     * @param charnum The index of the character, where the first character has an index of 0.
     * @return The rotation angle.
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum is greater than or equal to the number of characters at this node.
     */
    public float getRotationOfChar(long charnum) throws DOMException;
    /**
     * Returns the index of the character whose corresponding glyph cell bounding box contains the specified point. The calculations assume that all glyphs occupy the full standard glyph cell for the font. If no such character exists, a value of -1 is returned. If multiple such characters exist, the character within the element whose glyphs were rendered last (i.e., take into account any reordering such as for bidirectional text) is used. If multiple consecutive characters are rendered inseparably (e.g., as a single glyph or a sequence of glyphs), then the user agent shall allocate an equal percentage of the text advance amount to each of the contributing characters in determining which of the characters is chosen.
     * @param point A point in user space.
     * @return The index of the character which is at the given point, where the first character has an index of 0.
     */
    public int getCharNumAtPosition(SVGPoint point);
    /**
     * Causes the specified substring to be selected just as if the user selected the substring interactively.
     * @param charnum The index of the start character which is at the given point, where the first character has an index of 0.
     * @param nchars The number of characters in the substring. If nchars specifies more characters than are available, then the substring will consist of all characters starting with charnum until the end of the list of characters.
     * @throws org.w3c.dom.DOMException INDEX_SIZE_ERR: Raised if the charnum is negative or if charnum is greater than or equal to the number of characters at this node. 
     */
    public void selectSubString(long charnum, long nchars) throws DOMException;
    
}
