/*
 * StringList.java
 *
 * Created on April 12, 2007, 1:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMException;
import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.SVGException;


/**
 * This interface defines a list of DOMString objects.
 * 
 * SVGStringList has the same attributes and methods as other SVGxxxList interfaces. Implementers may consider using a single base class to implement the various SVGxxxList interfaces.
 * @author kitfox
 */
public interface SVGStringList extends SVGList<DOMString>
{
}
