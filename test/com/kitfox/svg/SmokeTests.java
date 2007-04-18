package com.kitfox.svg;
/*
 * SmokeTests.java
 */

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Tests to run with every build
 *
 * @author <a href="mailto:david@walend.net>dwalend</a>
 */
public class SmokeTests extends TestCase
{
    public SmokeTests(String methodName)
    {
        super(methodName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(com.kitfox.svg.app.beans.SVGIconTest.smokeTests());
        
        return suite;
    }    
}
