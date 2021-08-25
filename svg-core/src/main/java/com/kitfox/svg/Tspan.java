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
 *
 * Created on January 26, 2004, 1:56 AM
 */
package com.kitfox.svg;

import com.kitfox.svg.util.FontUtil;
import com.kitfox.svg.xml.StyleAttribute;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class Tspan extends ShapeElement
{

    public static final String TAG_NAME = "tspan";
    float[] x = null;
    float[] y = null;
    float[] dx = null;
    float[] dy = null;
    float[] rotate = null;

    float textLength = -1;
    String lengthAdjust = "spacing";

    // List of strings and tspans containing the content of this node
    private final List<Serializable> content = new ArrayList<>();
    protected final ArrayList<TextSegment> segments = new ArrayList<>();
    protected Rectangle2D textBounds;
    protected Path2D fullPath;

    protected FontUtil.FontInfo fontInfo;
    private Font font;

    /**
     * Creates a new instance of Tspan
     */
    public Tspan()
    {
    }

    @Override
    public String getTagName()
    {
        return TAG_NAME;
    }

    /**
     * Called after the start element but before the end element to indicate
     * each child tag that has been processed
     */
    @Override
    public void loaderAddChild(SVGLoaderHelper helper, SVGElement child) throws SVGElementException
    {
        super.loaderAddChild(helper, child);

        content.add(child);
    }

    /**
     * Called during load process to add text scanned within a tag
     */
    @Override
    public void loaderAddText(SVGLoaderHelper helper, String text)
    {
        Matcher matchWs = Pattern.compile("\\s*").matcher(text);
        if (!matchWs.matches())
        {
            content.add(text);
        }
    }

    public List<Serializable> getContent()
    {
        return content;
    }

    /**
     * Removes all strings and Tspan elements that are children of this element.
     */
    public void clearContent()
    {
        content.clear();
    }

    public void appendText(String text)
    {
        content.add(text);
    }

    public void appendTspan(Tspan tspan) throws SVGElementException
    {
        super.loaderAddChild(null, tspan);
        content.add(tspan);
    }

    protected void buildAttributes(StyleAttribute sty) throws SVGException
    {
        if (getPres(sty.setName("x")))
        {
            x = sty.getFloatListWithUnits();
        }

        if (getPres(sty.setName("y")))
        {
            y = sty.getFloatListWithUnits();
        }

        if (getPres(sty.setName("dx")))
        {
            dx = sty.getFloatListWithUnits();
        }

        if (getPres(sty.setName("dy")))
        {
            dy = sty.getFloatListWithUnits();
        }

        if (getPres(sty.setName("rotate")))
        {
            rotate = sty.getFloatList();
            for (int i = 0; i < this.rotate.length; i++)
            {
                rotate[i] = (float) Math.toRadians(this.rotate[i]);
            }
        }

        if (getStyle(sty.setName("textLength")))
        {
            textLength = sty.getFloatValueWithUnits();
        }
        else
        {
            textLength = -1;
        }

        if (getStyle(sty.setName("lengthAdjust")))
        {
            lengthAdjust = sty.getStringValue();
        }
        else
        {
            lengthAdjust = "spacing";
        }
    }

    protected void buildShapeInformation() throws SVGException
    {
        StyleAttribute sty = new StyleAttribute();
        buildAttributes(sty);

        fontInfo = FontUtil.parseFontInfo(this, sty);
        font = FontUtil.getFont(fontInfo, diagram);
    }

    protected void buildTextShape(Cursor cursor) throws SVGException
    {
        buildShapeInformation();

        fullPath = new GeneralPath();

        if (cursor == null) {
            cursor = new Cursor(getXCursorForIndex(0, 0),
                                getYCursorForIndex(0, 0));
        }

        segments.clear();
        segments.ensureCapacity(content.size());
        int currentCursorOffset = cursor.offset;
        cursor.offset = 0;

        AffineTransform transform = new AffineTransform();

        float spaceAdvance = font.getGlyph(" ").getHorizAdvX();

        for (Serializable obj : content)
        {
            if (obj instanceof String)
            {
                String text = ((String) obj);
                String trimmed = text.trim();
                if (!text.isEmpty() && text.charAt(0) <= ' ')
                    cursor.x += font.getGlyph(" ").getHorizAdvX();
                Path2D textPath = createStringSegmentPath(trimmed, font, cursor, transform);
                if (!text.isEmpty() && text.charAt(text.length() - 1) <= ' ')
                    cursor.x += spaceAdvance;

                fullPath.append(textPath, false);
                segments.add(new TextSegment(textPath, this));
            } else if (obj instanceof Tspan)
            {
                Tspan tspan = (Tspan) obj;
                tspan.buildTextShape(cursor);
                fullPath.append(tspan.fullPath, false);
                segments.add(new TextSegment(null, (Tspan) obj));
            }
        }

        textBounds = fullPath.getBounds2D();
        cursor.offset += currentCursorOffset;
    }

    private Path2D createStringSegmentPath(String text, Font font, Cursor cursor, AffineTransform xform)
    {
        Path2D textPath = new GeneralPath();

        for (int i = 0; i < text.length(); i++)
        {
            // The positions are specified for the whole recursive content of a span.
            // We need to account for any eventual text which occurred before.
            cursor.x = getXCursorForIndex(cursor.x, cursor.offset + i);
            cursor.y = getYCursorForIndex(cursor.y, cursor.offset + i);

            xform.setToTranslation(cursor.x, cursor.y);
            if (rotate != null && cursor.offset + i < rotate.length)
            {
                xform.rotate(rotate[cursor.offset + i]);
            }

            String unicode = text.substring(i, i + 1);
            MissingGlyph glyph = font.getGlyph(unicode);

            Shape path = glyph.getPath();
            if (path != null)
            {
                path = xform.createTransformedShape(path);
                textPath.append(path, false);
            }
            cursor.x += glyph.getHorizAdvX() + fontInfo.letterSpacing;
        }
        cursor.offset += text.length();

        strokeWidthScalar = 1f;
        return textPath;
    }

    private float getXCursorForIndex(float current, int index)
    {
        return getCursorForIndex(current, index, x, dx);
    }

    private float getYCursorForIndex(float current, int index)
    {
        return getCursorForIndex(current, index, y, dy);
    }

    private float getCursorForIndex(float current, int index, float[] absolutes, float[] deltas)
    {
        if (absolutes != null && index < absolutes.length)
        {
            current = absolutes[index];
        } else if (deltas != null && index < deltas.length)
        {
            current += deltas[index];
        }
        return current;
    }

    @Override
    protected void doRender(Graphics2D g) throws SVGException
    {
        beginLayer(g);
        for (TextSegment segment : segments) {
            if (segment.textPath != null)
            {
                // Text portion of this span.
                segment.element.renderShape(g, segment.textPath);
            } else
            {
                // Child span.
                segment.element.doRender(g);
            }
        }
        finishLayer(g);
    }

    @Override
    public Shape getShape()
    {
        return shapeToParent(fullPath);
    }

    @Override
    public Rectangle2D getBoundingBox()
    {
        return boundsToParent(textBounds);
    }

    /**
     * Updates all attributes in this diagram associated with a time event. Ie,
     * all attributes with track information.
     *
     * @return - true if this node has changed state as a result of the time
     * update
     */
    @Override
    public boolean updateTime(double curTime) throws SVGException
    {
        //Tspan does not change
        return false;
    }

    public String getText()
    {
        return getText(new StringBuilder());
    }

    private String getText(StringBuilder builder)
    {
        for (Serializable serializable : content)
        {
            if (serializable instanceof Tspan)
            {
                ((Tspan) serializable).getText(builder);
                builder.append(' ');
            } else if (serializable != null)
            {
              builder.append(serializable).append(' ');
            }
        }
        if (builder.length() > 0)
        {
            // Remove trailing space.
            return builder.substring(0, builder.length() - 1);
        } else
        {
            return "";
        }
    }

    protected static class TextSegment {
        final Path2D textPath;
        final Tspan element;

        private TextSegment(Path2D textPath, Tspan element) {
            this.textPath = textPath;
            this.element = element;
        }
    }

    protected static class Cursor {
        float x;
        float y;
        int offset;

        public Cursor(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
