/*
 * SVGSVGElement.java
 *
 * Created on April 12, 2007, 5:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import com.kitfox.salamander.svg.DOMString;
import com.kitfox.salamander.svg.basic.SVGAngle;
import com.kitfox.salamander.svg.basic.SVGAnimatedLength;
import com.kitfox.salamander.svg.basic.SVGElement;
import com.kitfox.salamander.svg.basic.SVGExternalResourcesRequired;
import com.kitfox.salamander.svg.basic.SVGFitToViewBox;
import com.kitfox.salamander.svg.basic.SVGLangSpace;
import com.kitfox.salamander.svg.basic.SVGLength;
import com.kitfox.salamander.svg.basic.SVGLocatable;
import com.kitfox.salamander.svg.basic.SVGNumber;
import com.kitfox.salamander.svg.basic.SVGRect;
import com.kitfox.salamander.svg.basic.SVGStylable;
import com.kitfox.salamander.svg.basic.SVGTests;
import com.kitfox.salamander.svg.basic.SVGViewSpec;
import com.kitfox.salamander.svg.basic.SVGZoomAndPan;
import com.kitfox.salamander.svg.coordSystems.SVGMatrix;
import com.kitfox.salamander.svg.coordSystems.SVGPoint;
import com.kitfox.salamander.svg.coordSystems.SVGTransform;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.EventTarget;

/**
 * A key interface definition is the SVGSVGElement interface, which is the interface that corresponds to the 'svg' element. This interface contains various miscellaneous commonly-used utility methods, such as matrix operations and the ability to control the time of redraw on visual rendering devices.
 *
 * SVGSVGElement extends ViewCSS and DocumentCSS to provide access to the computed values of properties and the override style sheet as described in DOM2.
 *
 * {@link http://www.w3.org/TR/SVG/struct.html#NewDocument}
 * {@link http://www.w3.org/TR/SVG/struct.html#InterfaceSVGSVGElement}
 * @author kitfox
 */
public interface SVGSVGElement extends SVGElement, SVGTests, SVGLangSpace,
        SVGExternalResourcesRequired, SVGStylable, SVGLocatable, SVGFitToViewBox,
        SVGZoomAndPan, EventTarget, DocumentEvent, ViewCSS, DocumentCSS
{
    /**
     * Corresponds to attribute x on the given 'svg' element.
     */
    public SVGAnimatedLength getX();
    /**
     * Corresponds to attribute y on the given 'svg' element.
     */
    public SVGAnimatedLength getY();
    /**
     * Corresponds to attribute width on the given 'svg' element.
     */
    public SVGAnimatedLength getWidth();
    /**
     * Corresponds to attribute height on the given 'svg' element.
     */
    public SVGAnimatedLength getHeight();
    /**
     * Corresponds to attribute contentScriptType on the given 'svg' element.
     */
    public DOMString getContentScriptType();
    /**
     * Corresponds to attribute contentStyleType on the given 'svg' element.
     */
    public DOMString getContentStyleType();
    /**
     * 
     * 
     *    The position and size of the viewport (implicit or explicit) that corresponds to this 'svg' element. When the user agent is actually rendering the content, then the position and size values represent the actual values when rendering. The position and size values are unitless values in the coordinate system of the parent element. If no parent element exists (i.e., 'svg' element represents the root of the document tree), if this SVG document is embedded as part of another document (e.g., via the HTML 'object' element), then the position and size are unitless values in the coordinate system of the parent document. (If the parent uses CSS or XSL layout, then unitless values represent pixel units for the current CSS or XSL viewport, as described in the CSS2 specification.) If the parent element does not have a coordinate system, then the user agent should provide reasonable default values for this attribute.
     * 
     *    The object itself and its contents are both readonly.
     */
    public SVGRect getViewport();
    /**
     * Size of a pixel units (as defined by CSS2) along the x-axis of the viewport, which represents a unit somewhere in the range of 70dpi to 120dpi, and, on systems that support this, might actually match the characteristics of the target medium. On systems where it is impossible to know the size of a pixel, a suitable default pixel size is provided.
     */
    public float getPixelUnitToMillimeterX();
    /**
     * Corresponding size of a pixel unit along the y-axis of the viewport.
     */
    public float getPixelUnitToMillimeterY();
    /**
     * User interface (UI) events in DOM Level 2 indicate the screen positions at which the given UI event occurred. When the user agent actually knows the physical size of a "screen unit", this attribute will express that information; otherwise, user agents will provide a suitable default value such as .28mm.
     */
    public float getScreenPixelToMillimeterX();
    /**
     * Corresponding size of a screen pixel along the y-axis of the viewport.
     */
    public float getScreenPixelToMillimeterY();
    /**
     * The initial view (i.e., before magnification and panning) of the current innermost SVG document fragment can be either the "standard" view (i.e., based on attributes on the 'svg' element such as fitBoxToViewport) or to a "custom" view (i.e., a hyperlink into a particular 'view' or other element - see Linking into SVG content: URI fragments and SVG views). If the initial view is the "standard" view, then this attribute is false. If the initial view is a "custom" view, then this attribute is true.
     */
    public boolean getUseCurrentView();
    /**
     * <PRE>The definition of the initial view (i.e., before magnification and panning) of the current innermost SVG document fragment. The meaning depends on the situation:
     * 
     *    * If the initial view was a "standard" view, then:
     *          o the values for viewBox, preserveAspectRatio and zoomAndPan within currentView will match the values for the corresponding DOM attributes that are on SVGSVGElement directly
     *          o the values for transform and viewTarget within currentView will be null
     *    * If the initial view was a link into a 'view' element, then:
     *          o the values for viewBox, preserveAspectRatio and zoomAndPan within currentView will correspond to the corresponding attributes for the given 'view' element
     *          o the values for transform and viewTarget within currentView will be null
     *    * If the initial view was a link into another element (i.e., other than a 'view'), then:
     *          o the values for viewBox, preserveAspectRatio and zoomAndPan within currentView will match the values for the corresponding DOM attributes that are on SVGSVGElement directly for the closest ancestor 'svg' element
     *          o the values for transform within currentView will be null
     *          o the viewTarget within currentView will represent the target of the link
     *    * If the initial view was a link into the SVG document fragment using an SVG view specification fragment identifier (i.e., #svgView(...)), then:
     *          o the values for viewBox, preserveAspectRatio, zoomAndPan, transform and viewTarget within currentView will correspond to the values from the SVG view specification fragment identifier
     * 
     * The object itself and its contents are both readonly.</PRE>
     */
    public SVGViewSpec getCurrentView();
    /**
     * This attribute indicates the current scale factor relative to the initial view to take into account user magnification and panning operations, as described under Magnification and panning. DOM attributes currentScale and currentTranslate are equivalent to the 2x3 matrix [a b c d e f] = [currentScale 0 0 currentScale currentTranslate.x currentTranslate.y]. If "magnification" is enabled (i.e., zoomAndPan="magnify"), then the effect is as if an extra transformation were placed at the outermost level on the SVG document fragment (i.e., outside the outermost 'svg' element).
     */
    public float getCurrentScale();
    /**
     * The corresponding translation factor that takes into account user "magnification".
     */
    public SVGPoint getCurrentTranslate();
    /**
     * Takes a time-out value which indicates that redraw shall not occur until: (a) the corresponding unsuspendRedraw(suspend_handle_id) call has been made, (b) an unsuspendRedrawAll()  call has been made, or (c) its timer has timed out. In environments that do not support interactivity (e.g., print media), then redraw shall not be suspended. suspend_handle_id = suspendRedraw(max_wait_milliseconds) and unsuspendRedraw(suspend_handle_id) must be packaged as balanced pairs. When you want to suspend redraw actions as a collection of SVG DOM changes occur, then precede the changes to the SVG DOM with a method call similar to suspend_handle_id = suspendRedraw(max_wait_milliseconds) and follow the changes with a method call similar to unsuspendRedraw(suspend_handle_id). Note that multiple suspendRedraw calls can be used at once and that each such method call is treated independently of the other suspendRedraw  method calls.
     * @param max_wait_milliseconds The amount of time in milliseconds to hold off before redrawing the device. Values greater than 60 seconds will be truncated down to 60 seconds.
     * @return A number which acts as a unique identifier for the given suspendRedraw()  call. This value must be passed as the parameter to the corresponding unsuspendRedraw() method call.
     */
    public long suspendRedraw(long max_wait_milliseconds);
    /**
     * Cancels a specified suspendRedraw() by providing a unique suspend_handle_id.
     * @param suspend_handle_id A number which acts as a unique identifier for the desired suspendRedraw()  call. The number supplied must be a value returned from a previous call to suspendRedraw()
     * @throws org.w3c.dom.DOMException This method will raise a DOMException with value NOT_FOUND_ERR if an invalid value (i.e., no such suspend_handle_id is active) for suspend_handle_id is provided.
     */
    public void unsuspendRedraw(long suspend_handle_id) throws DOMException;
    /**
     * Cancels all currently active suspendRedraw() method calls. This method is most useful at the very end of a set of SVG DOM calls to ensure that all pending suspendRedraw() method calls have been cancelled.
     */
    public void unsuspendRedrawAll();
    /**
     * In rendering environments supporting interactivity, forces the user agent to immediately redraw all regions of the viewport that require updating.
     */
    public void forceRedraw();
    /**
     * Suspends (i.e., pauses) all currently running animations that are defined within the SVG document fragment corresponding to this 'svg' element, causing the animation clock corresponding to this document fragment to stand still until it is unpaused.
     */
    public void pauseAnimations();
    /**
     * Unsuspends (i.e., unpauses) currently running animations that are defined within the SVG document fragment, causing the animation clock to continue from the time at which it was suspended.
     */
    public void unpauseAnimations();
    /**
     * Returns true if this SVG document fragment is in a paused state.
     * @return Boolean indicating whether this SVG document fragment is in a paused state.
     */
    public boolean animationsPaused();
    /**
     * Returns the current time in seconds relative to the start time for the current SVG document fragment.
     * @return The current time in seconds.
     */
    public float getCurrentTime();
    /**
     * Adjusts the clock for this SVG document fragment, establishing a new current time.
     * @param seconds The new current time in seconds relative to the start time for the current SVG document fragment.
     */
    public void setCurrentTime(float seconds);
    /**
     * Returns the list of graphics elements whose rendered content intersects the supplied rectangle, honoring the 'pointer-events' property value on each candidate graphics element.
     * @param rect The test rectangle. The values are in the initial coordinate system for the current 'svg' element.
     * @param referenceElement If not null, then only return elements whose drawing order has them below the given reference element.
     * @return A list of Elements whose content intersects the supplied rectangle.
     */
    public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement);
    /**
     * Returns the list of graphics elements whose rendered content is entirely contained within the supplied rectangle, honoring the 'pointer-events' property value on each candidate graphics element.
     * @param rect The test rectangle. The values are in the initial coordinate system for the current 'svg' element.
     * @param referenceElement If not null, then only return elements whose drawing order has them below the given reference element.
     * @return A list of Elements whose content is enclosed by the supplied rectangle.
     */
    public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement);
    /**
     * Returns true if the rendered content of the given element intersects the supplied rectangle, honoring the 'pointer-events' property value on each candidate graphics element.
     * @param element The element on which to perform the given test.
     * @param rect The test rectangle. The values are in the initial coordinate system for the current 'svg' element.
     * @return True or false, depending on whether the given element intersects the supplied rectangle.
     */
    public boolean checkIntersection(SVGElement element, SVGRect rect);
    /**
     * Returns true if the rendered content of the given element is entirely contained within the supplied rectangle, honoring the 'pointer-events' property value on each candidate graphics element.
     * @param element The element on which to perform the given test.
     * @param rect The test rectangle. The values are in the initial coordinate system for the current 'svg' element.
     * @return True or false, depending on whether the given element is enclosed by the supplied rectangle.
     */
    public boolean checkEnclosure(SVGElement element, SVGRect rect);
    /**
     * Unselects any selected objects, including any selections of text strings and type-in bars.
     */
    public void deselectAll();
    /**
     * Creates an SVGNumber object outside of any document trees. The object is initialized to a value of zero.
     * @return An SVGNumber object.
     */
    public SVGNumber createSVGNumber();
    /**
     * Creates an SVGLength object outside of any document trees. The object is initialized to the value of 0 user units.
     * @return An SVGLength object.
     */
    public SVGLength createSVGLength();
    /**
     * Creates an SVGAngle object outside of any document trees. The object is initialized to the value 0 degrees (unitless).
     * @return An SVGAngle object.
     */
    public SVGAngle createSVGAngle();
    /**
     * Creates an SVGPoint object outside of any document trees. The object is initialized to the point (0,0) in the user coordinate system.
     * @return An SVGPoint object.
     */
    public SVGPoint createSVGPoint();
    /**
     * Creates an SVGMatrix object outside of any document trees. The object is initialized to the identity matrix.
     * @return An SVGMatrix object.
     */
    public SVGMatrix createSVGMatrix();
    /**
     * Creates an SVGRect object outside of any document trees. The object is initialized such that all values are set to 0 user units.
     * @return An SVGRect object.
     */
    public SVGRect createSVGRect();
    /**
     * Creates an SVGTransform object outside of any document trees. The object is initialized to an identity matrix transform (SVG_TRANSFORM_MATRIX).
     * @return An SVGTransform object.
     */
    public SVGTransform createSVGTransform();
    /**
     * Creates an SVGTransform object outside of any document trees. The object is initialized to the given matrix transform (i.e., SVG_TRANSFORM_MATRIX).
     * @param matrix The transform matrix.
     * @return An SVGTransform object.
     */
    public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);
    /**
     * Searches this SVG document fragment (i.e., the search is restricted to a subset of the document tree) for an Element whose id is given by elementId. If an Element is found, that Element is returned. If no such element exists, returns null. Behavior is not defined if more than one element has this id.
     * @param elementId The unique id value for an element.
     * @return The matching element.
     */
    public Element getElementById(DOMString elementId);
}
