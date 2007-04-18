package com.kitfox.svg;
/*
 * CheckInTests.java
 */

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Tests to run before every check in.
 *
 * @author <a href="mailto:david@walend.net>dwalend</a>
 */
public class CheckInTests extends TestCase
{
    public CheckInTests(String methodName)
    {
        super(methodName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(com.kitfox.svg.app.beans.SVGIconTest.checkInTests());
        
        return suite;
    }    
}
