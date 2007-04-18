package com.kitfox.svg;
/*
 * ReleaseTests.java
 */

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Tests to run before every release.
 *
 * @author <a href="mailto:david@walend.net>dwalend</a>
 */
public class ReleaseTests 
    extends TestCase
{
    public ReleaseTests(String methodName)
    {
        super(methodName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        
        suite.addTest(com.kitfox.svg.app.beans.SVGIconTest.releaseTests());
        
        return suite;
    }    
}
