/*
 * SVGZoomEvent.java
 *
 * Created on April 13, 2007, 11:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.script;

import com.kitfox.salamander.svg.basic.SVGRect;
import com.kitfox.salamander.svg.coordSystems.SVGPoint;
import org.w3c.dom.events.UIEvent;

/**
 * 
 * A DOM consumer can use the hasFeature of the DOMImplementation interface to determine whether the SVG zoom event set has been implemented by a DOM implementation. The feature string for this event set is "SVGZoomEvents". This string is also used with the createEvent method.
 * 
 * The zoom event handler occurs before the zoom event is processed. The remainder of the DOM represents the previous state of the document. The document will be updated upon normal return from the event handler.
 * 
 * The UI event type for a zoom event is:
 * 
 * SVGZoom
 *    The zoom event occurs when the user initiates an action which causes the current view of the SVG document fragment to be rescaled. Event handlers are only recognized on 'svg' elements. See SVGZoom event.
 * 
 *        * Bubbles: Yes
 *        * Cancelable: No
 *        * Context Info: zoomRectScreen, previousScale, previousTranslate, newScale, newTranslate, screenX, screenY, clientX, clientY, altKey, ctrlKey, shiftKey, metaKey, relatedNode.
 *          (screenX, screenY, clientX and clientY indicate the center of the zoom area, with clientX and clientY in viewport coordinates for the corresponding 'svg' element. relatedNode is the corresponding 'svg' element.)
 * @author kitfox
 */
public interface SVGZoomEvent extends UIEvent
{
    /**
     * 
     * 
     *    The specified zoom rectangle in screen units.
     * 
     *    The object itself and its contents are both readonly.
     */
    public SVGRect getZoomRectScreen();
    /**
     * The scale factor from previous zoom operations that was in place before the zoom operation occurred.
     */
    public float getPreviousScale();
    /**
     * 
     * 
     *    The translation values from previous zoom operations that were in place before the zoom operation occurred.
     * 
     *    The object itself and its contents are both readonly.
     */
    public SVGPoint getPreviousTranslate();
    /**
     * The scale factor that will be in place after the zoom operation has been processed.
     */
    public float getNewScale();
    /**
     * 
     * 
     *    The translation values that will be in place after the zoom operation has been processed.
     * 
     *    The object itself and its contents are both readonly.
     */
    public SVGPoint getNewTranslate();
}
