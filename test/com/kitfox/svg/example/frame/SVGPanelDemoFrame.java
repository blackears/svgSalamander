/*
 * SVGIconDemo.java
 *
 * Created on September 1, 2005, 7:28 PM
 */

package com.kitfox.svg.example.frame;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import com.kitfox.svg.*;
import com.kitfox.svg.animation.*;
import com.kitfox.svg.app.beans.*;


class DynamicSvgPanelPanel extends JPanel
{
    public static final long serialVersionUID = 0;
    
//    final SVGIcon icon;
    URI uri;
    
    LinkedList extraCircles = new LinkedList();
    
    SVGPanel svgPanel = new SVGPanel();
    
    public DynamicSvgPanelPanel()
    {
        setLayout(new BorderLayout());
        
        StringReader reader = new StringReader(makeDynamicSVG());
        uri = SVGCache.getSVGUniverse().loadSVG(reader, "myImage"); 
        svgPanel.setAntiAlias(true);
        svgPanel.setSvgURI(uri);
        svgPanel.setOpaque(true);
        svgPanel.setBackground(Color.orange);
        
        add(svgPanel, BorderLayout.CENTER);
//        icon = new SVGIcon();
//        icon.setAntiAlias(true);
//        icon.setSvgURI(uri);
        
        setPreferredSize(new Dimension(400, 400));
    }
    
    /*
    public void paintComponent(Graphics g)
    {
        final int width = getWidth();
        final int height = getHeight();
        
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);
        
        icon.paintIcon(this, g, 0, 0);
    }
     */
    
    private String makeDynamicSVG()
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        pw.println("<svg width=\"400\" height=\"400\" style=\"fill:none;stroke-width:16\">");
        pw.println("    <circle id=\"bigCircle\" cx=\"200\" cy=\"200\" r=\"200\" style=\"stroke:blue\"/>");
        pw.println("    <g id=\"extraCircleGroup\" style=\"stroke-width:4;fill:green\"/>");
        pw.println("    <text id=\"userTextParent\" x=\"0\" y=\"40\" style=\"font-size:40;stroke:none;fill:red\">");
        pw.println("        <tspan id=\"userText\">Hello!</tspan>");
        pw.println("    </text>");
        pw.println("</svg>");
        
        pw.close();
        return sw.toString();
    }
    
    public void setCircleForeground(Color color)
    {
        SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
        Circle circle = (Circle)diagram.getElement("bigCircle");
        
        String colorStrn = Integer.toHexString(color.getRGB() & 0xffffff);
        try
        {
            if (!circle.hasAttribute("stroke", AnimationElement.AT_CSS))
            {
                circle.addAttribute("stroke", AnimationElement.AT_CSS, "#" + colorStrn);
            }
            else
            {
                circle.setAttribute("stroke", AnimationElement.AT_CSS, "#" + colorStrn);
            }
        }
        catch (SVGElementException e)
        {
            e.printStackTrace();
        }
    }
    
    public void setCircleBackground(Color color)
    {
        SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
        Circle circle = (Circle)diagram.getElement("bigCircle");
        
        String colorStrn = Integer.toHexString(color.getRGB() & 0xffffff);
        try
        {
            if (!circle.hasAttribute("fill", AnimationElement.AT_CSS))
            {
                circle.addAttribute("fill", AnimationElement.AT_CSS, "#" + colorStrn);
            }
            else
            {
                circle.setAttribute("fill", AnimationElement.AT_CSS, "#" + colorStrn);
            }
        }
        catch (SVGElementException e)
        {
            e.printStackTrace();
        }
    }
    
    public void setText(String text)
    {
        SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
        Tspan tspan = (Tspan)diagram.getElement("userText");
        
        tspan.setText(text);
        
        Text textEle = (Text)diagram.getElement("userTextParent");
        
        try
        {
            textEle.rebuild();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addCircle()
    {
        SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
        Group group = (Group)diagram.getElement("extraCircleGroup");
        
        Circle circle = new Circle();
        try
        {
            int cx = (int)(Math.random() * 400);
            int cy = (int)(Math.random() * 400);
            
            circle.addAttribute("cx", AnimationElement.AT_XML, "" + cx);
            circle.addAttribute("cy", AnimationElement.AT_XML, "" + cy);
            circle.addAttribute("r", AnimationElement.AT_XML, "10");
            
            group.loaderAddChild(null, circle);
            
            //Update animation state or group and it's decendants so that it reflects new animation values.
            // We could also call diagram.update(0.0) or SVGCache.getSVGUniverse().update().  Note that calling
            // circle.update(0.0) won't display anything since even though it will update the circle's state,
            // it won't update the parent group's state.
            group.updateTime(0.0);
            
            //Keep track of circles so we can remove them later
            extraCircles.add(circle);
        }
        catch (SVGException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void removeCircle()
    {
        int size = extraCircles.size();
        if (size == 0) return;
        
        int idx = (int)(Math.random() * size);
        Circle circle = (Circle)extraCircles.remove(idx);
        
        SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
        Group group = (Group)diagram.getElement("extraCircleGroup");
        
        try
        {
            group.removeChild(circle);
        }
        catch (SVGException e)
        {
            e.printStackTrace();
        }
            
    }
}

/**
 *
 * @author  kitfox
 */
public class SVGPanelDemoFrame extends javax.swing.JFrame
{
    public static final long serialVersionUID = 0;
    
    DynamicSvgPanelPanel panel = new DynamicSvgPanelPanel();
    
    /** Creates new form SVGIconDemo */
    public SVGPanelDemoFrame()
    {
        initComponents();

        panel_display.add(panel, BorderLayout.CENTER);
        
        pack();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        panel_display = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        bn_back = new javax.swing.JButton();
        bn_front = new javax.swing.JButton();
        bn_add = new javax.swing.JButton();
        bn_remove = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_userText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        panel_display.setLayout(new java.awt.BorderLayout());

        getContentPane().add(panel_display, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        bn_back.setText("Background");
        bn_back.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bn_backActionPerformed(evt);
            }
        });

        jPanel3.add(bn_back);

        bn_front.setText("Foreground");
        bn_front.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bn_frontActionPerformed(evt);
            }
        });

        jPanel3.add(bn_front);

        bn_add.setText("Add");
        bn_add.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bn_addActionPerformed(evt);
            }
        });

        jPanel3.add(bn_add);

        bn_remove.setText("Remove");
        bn_remove.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bn_removeActionPerformed(evt);
            }
        });

        jPanel3.add(bn_remove);

        jPanel2.add(jPanel3);

        jLabel1.setText("Text");
        jPanel1.add(jLabel1);

        text_userText.setText("Hello!");
        text_userText.setPreferredSize(new java.awt.Dimension(200, 20));
        text_userText.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                text_userTextActionPerformed(evt);
            }
        });

        jPanel1.add(text_userText);

        jPanel2.add(jPanel1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void text_userTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_text_userTextActionPerformed
    {//GEN-HEADEREND:event_text_userTextActionPerformed
        panel.setText(text_userText.getText());
        repaint();
    }//GEN-LAST:event_text_userTextActionPerformed

    private void bn_removeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bn_removeActionPerformed
    {//GEN-HEADEREND:event_bn_removeActionPerformed
        panel.removeCircle();
        repaint();
    }//GEN-LAST:event_bn_removeActionPerformed

    private void bn_addActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bn_addActionPerformed
    {//GEN-HEADEREND:event_bn_addActionPerformed
        panel.addCircle();
        repaint();
    }//GEN-LAST:event_bn_addActionPerformed

    private void bn_frontActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bn_frontActionPerformed
    {//GEN-HEADEREND:event_bn_frontActionPerformed
        panel.setCircleForeground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
        repaint();
    }//GEN-LAST:event_bn_frontActionPerformed

    private void bn_backActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bn_backActionPerformed
    {//GEN-HEADEREND:event_bn_backActionPerformed
        panel.setCircleBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
        repaint();        
    }//GEN-LAST:event_bn_backActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new SVGIconDemoFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bn_add;
    private javax.swing.JButton bn_back;
    private javax.swing.JButton bn_front;
    private javax.swing.JButton bn_remove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panel_display;
    private javax.swing.JTextField text_userText;
    // End of variables declaration//GEN-END:variables
    
}
