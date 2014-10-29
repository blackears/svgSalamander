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
 * Created on Mar 10, 2010, 11:25:47 AM
 */

package com.kitfox.svg.example;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.ShapeElement;
import com.kitfox.svg.app.beans.SVGPanel;
import java.awt.BorderLayout;
import java.awt.geom.Point2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kitfox
 */
public class SimpleIconFrame extends javax.swing.JFrame
{
    SVGPanel panel = new SVGPanel();

    /** Creates new form SimpleIconFrame */
    public SimpleIconFrame()
    {
        initComponents();

        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setAntiAlias(true);
        panel.setAutosize(SVGPanel.AUTOSIZE_NONE);
        
        try {
//            panel.setSvgURI(SimpleIconFrame.class.getResource("tux.svg").toURI());
            panel.setSvgURI(SimpleIconFrame.class.getResource("radialGradient.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("jira43-tspanVariableX.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("tree.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("jt11.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("viewboxTest.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("boundingBoxPicking.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("issue.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("intro_flash.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("clipIssue.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("bug-31-path.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("100x100-mm.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("gradient.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("map2.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("imageClip.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("markerEnd.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("casing2.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("casing2-opt.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("PseudoColor.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("degrade.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("Z.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("test-brd.svg").toURI());
//              panel.setSvgURI(SimpleIconFrame.class.getResource("nonScalingStroke.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("areaCrash.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("use.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("data-uri-scheme-test-image.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("marker.svg").toURI());
//            panel.setSvgURI(SimpleIconFrame.class.getResource("markerTest.svg").toURI());

//            panel.setSvgURI(SimpleIconFrame.class.getResource("class.svg").toURI());
//            SVGDiagram diagram = panel.getSvgUniverse().getDiagram(SimpleIconFrame.class.getResource("class.svg").toURI());
//            SVGRoot root = diagram.getRoot();
//            StyleSheet ss = new StyleSheet();
//            root.setStyleSheet(ss);
//            ss.addStyleRule(new StyleSheetRule("stroke-width", "rect", null), "5");
//            ss.addStyleRule(new StyleSheetRule("fill", "rect", null), "none");
//            ss.addStyleRule(new StyleSheetRule("stroke", "rect", "alpha"), "red");
//            ss.addStyleRule(new StyleSheetRule("stroke", "rect", null), "green");
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(SimpleIconFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        panel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                panelMousePressed(evt);
            }
        });
        
        setSize(640, 480);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMousePressed(java.awt.event.MouseEvent evt)                                  
    {                                      
        SVGUniverse uni = panel.getSvgUniverse();
        SVGDiagram dia = uni.getDiagram(panel.getSvgURI());
        
        ArrayList result = new ArrayList();
        try
        {
            dia.pick(new Point2D.Float(evt.getX(), evt.getY()), result);

            System.out.println("---");
            for (int i = 0; i < result.size(); ++i)
            {
                List path = (List)result.get(i);

                ShapeElement ele = (ShapeElement)path.get(path.size() - 1);
//                    ele.getParent().removeChild(ele);
                repaint();
                System.out.println(ele.getId());
            }
        }
        catch (SVGException ex)
        {
            Logger.getLogger(SimpleIconFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                 
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimpleIconFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
