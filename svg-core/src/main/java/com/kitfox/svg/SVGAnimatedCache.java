package com.kitfox.svg;

public class SVGAnimatedCache {
    private static SVGAnimatedCache INSTANCE;
    private static final SVGUniverse svgUniverse = new SVGUniverse();
    private static double svgTimer = 0d, svgStep = 0.05d;
    private static final Thread svgThreadTimer = new Thread(new Runnable() {
        @Override
        public void run() {
            svgTimer += svgStep;
            try {
                svgUniverse.setCurTime(svgTimer);
                svgUniverse.updateTime();
            } catch (SVGException e1) {
                e1.printStackTrace();
            }

            try {
                Thread.sleep((long) (svgStep * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private SVGAnimatedCache() {
    }

    public static SVGUniverse getSVGUniverse() {
        return svgUniverse;
    }

    public static void startAnimation(){
        if(!svgThreadTimer.isAlive())
        svgThreadTimer.start();
    }

    public static void stopAnimation(){
        svgTimer = 0d;
        svgThreadTimer.interrupt();
    }

    public static SVGAnimatedCache getInstance() {
        if(INSTANCE == null){
            INSTANCE = new SVGAnimatedCache();
        }
        return INSTANCE;
    }
}
