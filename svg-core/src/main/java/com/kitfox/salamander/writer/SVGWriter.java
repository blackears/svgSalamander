/*
 * SVGWriter.java
 *
 * Created on April 12, 2007, 7:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.writer;

import com.kitfox.salamander.SVGConstants;
import java.io.PrintWriter;

/**
 *
 * @author kitfox
 */
public class SVGWriter implements SVGConstants
{
    final PrintWriter pw;
    
    /** Creates a new instance of SVGWriter */
    public SVGWriter(PrintWriter pw)
    {
        this.pw = pw;
    }

    private void writeHeader()
    {
        pw.printf("<!DOCTYPE svg %s \"%s\"\n", PUBLIC_IDENTIFIER_SVG, SYSTEM_IDENTIFIER_SVG);
    }
    
}
