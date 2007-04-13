/*
 * SVGEvent.java
 *
 * Created on April 13, 2007, 11:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.script;

import com.kitfox.salamander.svg.basic.SVGElement;
import org.w3c.dom.events.Event;

/**
 * 
 * The SVG event set contains a list of special event types which are available in SVG.
 * 
 * A DOM consumer can use the hasFeature of the DOMImplementation interface to determine whether the SVG event set has been implemented by a DOM implementation. The feature string for this event set is "SVGEvents". This string is also used with the createEvent method.
 * 
 * The SVG events use the base DOM Event interface to pass contextual information.
 * 
 * The different types of such events that can occur are:
 * 
 * SVGLoad
 *    See SVGLoad event.
 * 
 *        * Bubbles: No
 *        * Cancelable: No
 *        * Context Info: None
 * 
 * SVGUnload
 *    See SVGUnload event.
 * 
 *        * Bubbles: No
 *        * Cancelable: No
 *        * Context Info: None
 * 
 * SVGAbort
 *    See SVGAbort event.
 * 
 *        * Bubbles: Yes
 *        * Cancelable: No
 *        * Context Info: None
 * 
 * SVGError
 *    See SVGError event.
 * 
 *        * Bubbles: Yes
 *        * Cancelable: No
 *        * Context Info: None
 * 
 * SVGResize
 *    See SVGResize event.
 * 
 *        * Bubbles: Yes
 *        * Cancelable: No
 *        * Context Info: None
 * 
 * SVGScroll
 *    See SVGScroll event.
 * 
 *        * Bubbles: Yes
 *        * Cancelable: No
 *        * Context Info: None
 * @author kitfox
 */
public interface SVGEvent extends Event
{
}
