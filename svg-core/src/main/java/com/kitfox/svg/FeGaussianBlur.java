package com.kitfox.svg;

import com.kitfox.svg.xml.StyleAttribute;

import java.awt.Rectangle;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;
import java.util.List;

public class FeGaussianBlur extends FilterEffects
{
    public static final String TAG_NAME = "fegaussianblur";

    private float[] stdDeviation;
    private float xCurrent;
    private float yCurrent;
    private ConvolveOp xBlur;
    private ConvolveOp yBlur;

    @Override
    public String getTagName()
    {
        return TAG_NAME;
    }

    @Override
    protected void build() throws SVGException
    {
        super.build();
        StyleAttribute sty = new StyleAttribute();

        stdDeviation = new float[]{0f};
        if (getPres(sty.setName("stdDeviation")))
        {
            stdDeviation = sty.getFloatList();
        }
        xBlur = null;
        yBlur = null;
    }

    @Override
    public List<FilterOp> getOperations(Rectangle inputBounds, float xScale, float yScale)
    {
        float xSigma = xScale * stdDeviation[0];
        float ySigma = yScale * stdDeviation[Math.min(stdDeviation.length - 1, 1)];

        return Arrays.asList(
                xSigma > 0
                        ? getGaussianBlurFilter(inputBounds, xSigma, true)
                        : null,
                ySigma > 0
                        ? getGaussianBlurFilter(inputBounds, ySigma, false)
                        : null
        );
    }

    public FilterOp getGaussianBlurFilter(Rectangle inputBounds, float sigma, boolean horizontal)
    {
        int multiplier = 2;
        float radius = 2f * sigma + 1;
        int size = (int) Math.ceil(radius * multiplier) + 1;
        if (horizontal && (xBlur == null || xCurrent != sigma)
            || !horizontal && (yBlur == null || yCurrent != sigma))
        {
            if (horizontal)
            {
                xCurrent = sigma;
            } else
            {
                yCurrent = sigma;
            }
            float[] data = new float[size];

            float radius2 = radius * radius;
            float twoSigmaSquare = 2.0f * sigma * sigma;
            float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
            float total = 0.0f;

            float middle = size / 2f;
            for (int i = 0; i < size; i++)
            {
                float distance = middle - i;
                distance *= distance;

                data[i] = distance > radius2
                        ? 0
                        : (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[i];
            }

            for (int i = 0; i < data.length; i++)
            {
                data[i] /= total;
            }

            if (horizontal)
            {
                xBlur = new ConvolveOp(new Kernel(size, 1, data), ConvolveOp.EDGE_NO_OP, null);
            } else
            {
                yBlur = new ConvolveOp(new Kernel(1, size, data), ConvolveOp.EDGE_NO_OP, null);
            }
        }

        Rectangle dstBounds = new Rectangle(inputBounds);
        if (horizontal)
        {
            dstBounds.grow(size, 0);
        } else
        {
            dstBounds.grow(0, size);
        }

        return new FilterOp(horizontal ? xBlur : yBlur, dstBounds);
    }
}
