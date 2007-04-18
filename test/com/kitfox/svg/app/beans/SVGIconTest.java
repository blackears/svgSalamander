/*
 * SVGIconTest.java
 * JUnit based test
 *
 * Created on May 20, 2005, 4:34 AM
 */

package com.kitfox.svg.app.beans;

import junit.framework.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import java.beans.*;

import com.kitfox.svg.*;
import com.kitfox.svg.app.beans.*;


/**
 *
 * @author kitfox
 */
public class SVGIconTest extends TestCase
{
    JFrame parent;
    
    public SVGIconTest(String testName)
    {
        super(testName);
    }

    protected void setUp() throws Exception
    {
        parent = new JFrame();
        
        parent.setVisible(true);
    }

    protected void tearDown() throws Exception
    {
        parent.setVisible(false);
        parent.dispose();
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(SVGIconTest.class);
        
        suite.addTest(new SVGIconTest("testDuke"));
        
        return suite;
    }

/**
* Test reading in an svg file from the resource path by showing it in a dialog box.
*/
    public void testInSVGIcon(String resourcePath)
    {
        
        JOptionPane optionPane = new JOptionPane();
        optionPane.setOptionType(JOptionPane.YES_NO_OPTION);
        
        SVGIcon icon = new SVGIcon();        

        icon.setSvgResourcePath(resourcePath);
//        icon.setScaleToFit(true);
        icon.setAntiAlias(true);

        optionPane.setIcon(icon);
        optionPane.setMessage("Does this icon look like " + resourcePath + " should?");
        
        JDialog dialog = optionPane.createDialog(parent, resourcePath);
        dialog.setVisible(true);
        Integer selectedValue = (Integer)optionPane.getValue();
        
        int selectedInt = selectedValue.intValue();
        assertTrue(resourcePath + " did not work.", JOptionPane.YES_OPTION == selectedInt);

        parent.setVisible(false);

    }

    /**
    * Test the famous Duke.svg.
    */
    
    public void testDuke()
    {
        testInSVGIcon("/example/duke.svg");
    }

    public static TestSuite smokeTests()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(new SVGIconTest("testDuke"));
        
        return suite;
    }

    public static TestSuite checkInTests()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(smokeTests());
        
        return suite;
    }

    public static TestSuite releaseTests()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(checkInTests());
        
        return suite;
    }
    
    
}
