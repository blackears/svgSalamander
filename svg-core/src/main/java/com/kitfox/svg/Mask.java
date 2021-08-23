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

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.List;

/**
 * Implements the mask element.
 *
 * @author Jannis Weis
 */
public class Mask extends Group
{
    public static final boolean DEBUG_PAINT = false;
    public static final String TAG_NAME = "mask";

    @Override
    public String getTagName() {
        return TAG_NAME;
    }

    @Override
    public void render(Graphics2D g)
    {
    }

    public void renderElement(Graphics2D g, RenderableElement element) throws SVGException
    {
        AffineTransform transform = g.getTransform();

        Graphics2D gg = (Graphics2D) g.create();
        Rectangle elementBounds = element.getBoundingBox().getBounds();
        Rectangle transformedBounds = transform.createTransformedShape(elementBounds).getBounds();

        BufferedImage elementImage = paintToBuffer(gg, transform, transformedBounds, element, null);

        // Draw the mask image. Implicitly the mask is empty i.e. has a completely black background.
        // We can't draw the mask directly to the elementImage using the mask composite as
        // masks may change the mask value a location at any time during mask realization.
        BufferedImage maskImage = paintToBuffer(gg, transform, transformedBounds, this, Color.BLACK);

        Graphics2D elementGraphics = (Graphics2D) elementImage.getGraphics();
        elementGraphics.setRenderingHints(gg.getRenderingHints());
        elementGraphics.setComposite(createMaskComposite());
        elementGraphics.drawImage(maskImage, 0, 0, null);
        elementGraphics.dispose();

        // Reset the transform. We already accounted for it in the buffer image.
        gg.setTransform(new AffineTransform());
        gg.drawImage(elementImage, transformedBounds.x, transformedBounds.y, null);
        if (DEBUG_PAINT)
        {
            gg.setXORMode(Color.BLACK);
            gg.drawRect(transformedBounds.x, transformedBounds.y, transformedBounds.width, transformedBounds.height);
        }
        gg.dispose();
    }

    /*
     * The srcBounds parameter is expected to be pre-transformed by the given transform.
     */
    private BufferedImage paintToBuffer(Graphics2D g, AffineTransform transform,
                                        Rectangle srcBounds, RenderableElement element,
                                        Color bgColor) throws SVGException
    {
        BufferedImage img = new BufferedImage(srcBounds.width, srcBounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgGraphics = (Graphics2D) img.getGraphics();
        if (g != null)
        {
            imgGraphics.setRenderingHints(g.getRenderingHints());
        }
        if (bgColor != null)
        {
            imgGraphics.setColor(bgColor);
            imgGraphics.fillRect(0,0, img.getWidth(), img.getHeight());
        }
        // Because we blit the image at the transformed location we have to compensate for the
        // element location.
        imgGraphics.translate(-srcBounds.x, -srcBounds.y);
        imgGraphics.transform(transform);
        element.doRender(imgGraphics);
        imgGraphics.dispose();
        return img;
    }

    private Composite createMaskComposite()
    {
        return new MaskComposite();
    }

    @Override
    void pick(Point2D point, boolean boundingBox, List<List<SVGElement>> retVec)
    {
    }

    @Override
    void pick(Rectangle2D pickArea, AffineTransform ltw, boolean boundingBox, List<List<SVGElement>> retVec)
    {
    }

    public void pickElement(Point2D point, boolean boundingBox,
                            List<List<SVGElement>> retVec, RenderableElement element) throws SVGException
    {
        if (boundingBox)
        {
            element.doPick(point, true, retVec);
        } else
        {
            Rectangle pickPoint = new Rectangle((int) point.getX(), (int) point.getY(), 1, 1);
            BufferedImage img = paintToBuffer(null, new AffineTransform(), pickPoint, this, Color.BLACK);
            // Only try picking the element if the picked point is visible.
            if (luminanceToAlpha(img.getRGB(0, 0)) > 0)
            {
                element.doPick(point, false, retVec);
            }
        }
    }

    public void pickElement(Rectangle2D pickArea, AffineTransform ltw, boolean boundingBox,
                            List<List<SVGElement>> retVec, RenderableElement element) throws SVGException
    {
        // If at any point the considered picking area becomes empty we break out early.
        if (pickArea.isEmpty()) return;
        if (boundingBox)
        {
            element.doPick(pickArea, ltw,true, retVec);
        } else
        {
            // Clip with the element bounds to avoid creating a larger buffer than needed.
            Area transformedBounds = new Area(ltw.createTransformedShape(element.getBoundingBox()));
            transformedBounds.intersect(new Area(pickArea));
            if (transformedBounds.isEmpty()) return;

            Rectangle pickRect = transformedBounds.getBounds();
            if (pickRect.isEmpty()) return;

            BufferedImage maskArea = paintToBuffer(null, ltw, pickRect,this, Color.BLACK);

            // Pick if any pixel in the pick area is visible.
            if (hasVisiblePixel(maskArea))
            {
                element.doPick(pickArea, ltw, false, retVec);
            }
        }
    }

    private boolean hasVisiblePixel(BufferedImage img)
    {
        Raster raster = img.getRaster();
        int x = raster.getMinX();
        int w = raster.getWidth();
        int y = raster.getMinY();
        int h = raster.getHeight();
        int[] srcPix = raster.getPixels(x, y, w, h, (int[]) null);
        boolean hasVisiblePixel = false;
        for (int i = 0; i < srcPix.length; i += 4)
        {
            int sr = srcPix[i];
            int sg = srcPix[i + 1];
            int sb = srcPix[i + 2];
            if (luminanceToAlpha(sr, sg, sb) > 0)
            {
                hasVisiblePixel = true;
                break;
            }
        }
        return hasVisiblePixel;
    }

    private static double luminanceToAlpha(int rgb)
    {
        return luminanceToAlpha((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
    }

    private static double luminanceToAlpha(int r, int g, int b)
    {
        // Assuming 'linearRGB' as the 'color-interpolation' value of the mask.
        return 0.2125 * r + 0.7154 * g + 0.0721 * b;
    }

    private static class MaskComposite implements Composite, CompositeContext
    {

        @Override
        public CompositeContext createContext(ColorModel srcColorModel,
                                              ColorModel dstColorModel, RenderingHints hints)
        {
            return this;
        }

        @Override
        public void dispose()
        {
        }

        public void composeRGB(int[] src, int[] dst)
        {
            int w = src.length;

            for (int i = 0; i < w; i += 4)
            {
                int sr = src[i];
                int sg = src[i + 1];
                int sb = src[i + 2];
                int da = dst[i + 3];
                double luminance = luminanceToAlpha(sr, sg, sb) / 255d;
                da *= luminance;
                dst[i + 3] = Math.min(255, Math.max(0, da));
            }
        }

        @Override
        public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
            assert dstIn == dstOut;
            assert src.getNumBands() == dstIn.getNumBands();

            int x = dstOut.getMinX();
            int w = dstOut.getWidth();
            int y = dstOut.getMinY();
            int h = dstOut.getHeight();
            int[] srcPix = src.getPixels(x, y, w, h, (int[]) null);
            int[] dstPix = dstIn.getPixels(x, y, w, h, (int[]) null);
            composeRGB(srcPix, dstPix);
            dstOut.setPixels(x, y, w, h, dstPix);
        }
    }
}
