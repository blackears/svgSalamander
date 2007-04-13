/*
 * SVGStopElement.java
 *
 * Created on April 13, 2007, 10:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.fills;

import com.kitfox.salamander.svg.basic.SVGAnimatedNumber;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGStylable;

/**
 * The SVGStopElement  interface corresponds to the 'stop' element.
 * @author kitfox
 */
public interface SVGStopElement extends SVGElement,
        SVGStylable
{
    /**
     * Corresponds to attribute offset on the given 'stop' element.
     */
    public SVGAnimatedNumber getOffset();
}
