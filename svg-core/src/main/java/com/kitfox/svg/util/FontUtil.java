/*
 * SVG Salamander
 * Copyright (c) 2004, Mark McKay
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *   - Redistributions of source code must retain the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 * projects can be found at http://www.kitfox.com
 */
package com.kitfox.svg.util;

import com.kitfox.svg.Font;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.Text;
import com.kitfox.svg.xml.StyleAttribute;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for parsing font information of an {@link SVGElement}.
 *
 * @author Jannis Weis
 */
public final class FontUtil
{

    private static final String DEFAULT_FONT_FAMILY = "sans-serif";
    private static final float DEFAULT_FONT_SIZE = 12f;
    private static final int DEFAULT_LETTER_SPACING = 0;
    private static final int DEFAULT_FONT_STYLE = Text.TXST_NORMAL;
    private static final int DEFAULT_FONT_WEIGHT = Text.TXWE_NORMAL;

    private FontUtil() {}

    public final static class FontInfo {
        public final String[] families;
        public final float size;
        public final int style;
        public final int weight;
        public final float letterSpacing;

        public FontInfo(String[] families, float size, int style, int weight, float letterSpacing)
        {
            this.families = families;
            this.size = size;
            this.style = style;
            this.weight = weight;
            this.letterSpacing = letterSpacing;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof FontInfo)) return false;
            FontInfo fontInfo = (FontInfo) o;
            return Float.compare(fontInfo.size, size) == 0
                   && style == fontInfo.style && weight == fontInfo.weight
                   && Float.compare(fontInfo.letterSpacing, letterSpacing) == 0
                   && Arrays.equals(families, fontInfo.families);
        }

        @Override
        public int hashCode()
        {
            int result = Objects.hash(size, style, weight, letterSpacing);
            result = 31 * result + Arrays.hashCode(families);
            return result;
        }
    }

    public static FontInfo parseFontInfo(SVGElement element, StyleAttribute sty) throws SVGException
    {
        String fontFamily = DEFAULT_FONT_FAMILY;
        if (element.getStyle(sty.setName("font-family")))
        {
            fontFamily = sty.getStringValue();
        }

        float fontSize = DEFAULT_FONT_SIZE;
        if (element.getStyle(sty.setName("font-size")))
        {
            fontSize = sty.getFloatValueWithUnits();
        }

        float letterSpacing = DEFAULT_LETTER_SPACING;
        if (element.getStyle(sty.setName("letter-spacing")))
        {
            letterSpacing = sty.getFloatValueWithUnits();
        }

        int fontStyle = DEFAULT_FONT_STYLE;
        if (element.getStyle(sty.setName("font-style")))
        {
            String s = sty.getStringValue();
            if ("normal".equals(s))
            {
                fontStyle = Text.TXST_NORMAL;
            } else if ("italic".equals(s))
            {
                fontStyle = Text.TXST_ITALIC;
            } else if ("oblique".equals(s))
            {
                fontStyle = Text.TXST_OBLIQUE;
            }
        }

        int fontWeight = DEFAULT_FONT_WEIGHT;
        if (element.getStyle(sty.setName("font-weight")))
        {
            String s = sty.getStringValue();
            if ("normal".equals(s))
            {
                fontWeight = Text.TXWE_NORMAL;
            } else if ("bold".equals(s))
            {
                fontWeight = Text.TXWE_BOLD;
            }
        }

        return new FontInfo(fontFamily.split(","), fontSize, fontStyle, fontWeight, letterSpacing);
    }

    public static Font getFont(FontInfo info, SVGDiagram diagram)
    {
        return getFont(info.families, info.style, info.weight, info.size, diagram);
    }

    private static Font getFont(String[] families, int fontStyle, int fontWeight, float fontSize, SVGDiagram diagram)
    {
        Font font = null;
        for (String family : families)
        {
            font = diagram.getUniverse().getFont(family);
            if (font != null) break;
        }
        if (font == null)
        {
            //Check system fonts
            font = FontSystem.createFont(families, fontStyle, fontWeight, fontSize);
        }
        if (font == null)
        {
            Logger.getLogger(FontSystem.class.getName())
                    .log(Level.WARNING, "Could not create font " + Arrays.toString(families));
            String[] defaultFont = new String[]{ FontUtil.DEFAULT_FONT_FAMILY };
            font = FontSystem.createFont(defaultFont, fontStyle, fontWeight, fontSize);
        }
        return font;
    }

}
