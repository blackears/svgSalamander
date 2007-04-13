/*
 * SVGFEMergeNodeElement.java
 *
 * Created on April 13, 2007, 11:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.filter;

import com.kitfox.salamander.svg.basic.SVGAnimatedString;
import com.kitfox.salamander.svg.basic.SVGElement;

/**
 * The SVGFEMergeNodeElement interface corresponds to the 'feMergeNode' element.
 * @author kitfox
 */
public interface SVGFEMergeNodeElement extends SVGElement
{
    /**
     * Corresponds to attribute in on the given 'feMergeNode' element.
     */
    public SVGAnimatedString getIn1();
}
