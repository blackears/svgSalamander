package com.kitfox.svg.app;

import com.kitfox.svg.*;
import com.kitfox.svg.animation.AnimationElement;
import com.kitfox.svg.app.beans.SVGIcon;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URI;

public class SVGAnimated {
    public static final int AUTOSIZE_NONE = 0;
    public static final int AUTOSIZE_HORIZ = 1;
    public static final int AUTOSIZE_VERT = 2;
    public static final int AUTOSIZE_BESTFIT = 3;
    public static final int AUTOSIZE_STRETCH = 4;

    public static final int INTERP_NEAREST_NEIGHBOR = 0;
    public static final int INTERP_BILINEAR = 1;
    public static final int INTERP_BICUBIC = 2;

    private boolean antiAlias = true;
    private int interpolation = INTERP_BILINEAR;
    private boolean clipToViewbox = false;
    private int autosize = AUTOSIZE_BESTFIT;

    private SVGAnimatedCache svgCache = SVGAnimatedCache.getInstance(); //new SVGAnimatedCache(); //SVGCache.getSVGUniverse();
    private SVGDiagram diagram;

    private Dimension preferredSize;
    private AffineTransform scaleSVG = new AffineTransform();

    private Component parent;
    private boolean isAnimationRunning = false;

    public SVGAnimated(String svg, Component parent) {
        this.parent = parent;
        try {
            URI svgURI = svgCache.getSVGUniverse().loadSVG(getClass().getResourceAsStream(svg), svg);
            diagram = svgCache.getSVGUniverse().getDiagram(svgURI);
            diagram.setParentComponent(parent);

            if (diagram != null) {
                Dimension size = getPreferredSize();
                if (size == null) {
                    size = new Dimension((int) diagram.getRoot().getDeviceWidth(), (int) diagram.getRoot().getDeviceHeight());
                }
                diagram.setDeviceViewport(new Rectangle(0, 0, size.width, size.height));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dimension getPreferredSize() {
        if (preferredSize == null) {
            setPreferredSize(new Dimension((int) diagram.getWidth(), (int) diagram.getHeight()));
        }

        return new Dimension(preferredSize);
    }

    public void setPreferredSize(Dimension preferredSize) {
        Dimension old = this.preferredSize;
        this.preferredSize = preferredSize;

        diagram.setDeviceViewport(new Rectangle(0, 0, preferredSize.width, preferredSize.height));
        calculateScale();
//        changes.firePropertyChange("preferredSize", old, preferredSize);
    }

    public int getIconWidthIgnoreAutosize() {
        if (preferredSize != null &&
                (autosize == AUTOSIZE_HORIZ || autosize == AUTOSIZE_STRETCH
                        || autosize == AUTOSIZE_BESTFIT)) {
            return preferredSize.width;
        }

        if (diagram == null) {
            return 0;
        }
        return (int) diagram.getWidth();
    }

    public int getIconHeightIgnoreAutosize() {
        if (preferredSize != null &&
                (autosize == AUTOSIZE_VERT || autosize == AUTOSIZE_STRETCH
                        || autosize == AUTOSIZE_BESTFIT)) {
            return preferredSize.height;
        }

        if (diagram == null) {
            return 0;
        }
        return (int) diagram.getHeight();
    }

    private void calculateScale() {
        final int width = getIconWidthIgnoreAutosize();
        final int height = getIconHeightIgnoreAutosize();

        if (width == 0 || height == 0) {
            scaleSVG.setToScale(1, 1);
            return;
        }

        double diaWidth = diagram.getWidth();
        double diaHeight = diagram.getHeight();

        double scaleW = 1;
        double scaleH = 1;
        switch (autosize){
            case AUTOSIZE_BESTFIT:
                scaleW = scaleH = (height / diaHeight < width / diaWidth)
                        ? height / diaHeight : width / diaWidth;
                break;
            case AUTOSIZE_HORIZ:
                scaleW = scaleH = width / diaWidth;
                break;
            case AUTOSIZE_VERT:
                scaleW = scaleH = height / diaHeight;
                break;
            case AUTOSIZE_STRETCH:
                scaleW = width / diaWidth;
                scaleH = height / diaHeight;
                break;
            default: break;
        }
        scaleSVG.setToScale(scaleW, scaleH);
    }

    public void render(Graphics2D g, int x, int y) {
        if(!isAnimationRunning) return;
        diagram.setRepaintParentComponent(parent, x, y, preferredSize.width, preferredSize.height);

        Object oldAliasHint = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);

        Object oldInterpolationHint = g.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        switch (interpolation) {
            case SVGIcon.INTERP_NEAREST_NEIGHBOR:
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                break;
            case SVGIcon.INTERP_BILINEAR:
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                break;
            case SVGIcon.INTERP_BICUBIC:
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                break;
        }

        if (diagram == null) {
            System.err.println("No diagram");
            return;
        }

        g.translate(x, y);
        diagram.setIgnoringClipHeuristic(!clipToViewbox);
        if (clipToViewbox) {
            g.setClip(new Rectangle2D.Float(0, 0, diagram.getWidth(), diagram.getHeight()));
        }

        AffineTransform oldXform = g.getTransform();
        g.transform(scaleSVG);

        try {
            diagram.render(g);
        } catch (SVGException e) {
            throw new RuntimeException(e);
        }

        g.setTransform(oldXform);
        g.translate(-x, -y);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAliasHint);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldInterpolationHint);
    }

    public void startAnimation() {
        isAnimationRunning = true;
        svgCache.startAnimation();
    }

    public void stopAnimation(){
        isAnimationRunning = false;
        svgCache.stopAnimation();
    }

    public void setColor(Color color) {
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        if(diagram != null){
            for(SVGElement elem : diagram.getRoot().getChildren(null)){
                try {
                    setColor(hexColor, elem);
                } catch (SVGElementException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setColor(String color, SVGElement element) throws SVGElementException {
        if(element.hasAttribute("fill", AnimationElement.AT_AUTO)){
            element.setAttribute("fill", AnimationElement.AT_AUTO, color);
        }

        for(SVGElement elem : element.getChildren(null)){
            setColor(color, elem);
        }
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    public int getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(int interpolation) {
        this.interpolation = interpolation;
    }

    public boolean isClipToViewbox() {
        return clipToViewbox;
    }

    public void setClipToViewbox(boolean clipToViewbox) {
        this.clipToViewbox = clipToViewbox;
    }

    public int getAutosize() {
        return autosize;
    }

    public void setAutosize(int autosize) {
        this.autosize = autosize;
    }
}
