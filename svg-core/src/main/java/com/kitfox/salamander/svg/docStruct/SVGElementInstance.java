/*
 * SVGElementInstance.java
 *
 * Created on April 12, 2007, 6:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

import com.kitfox.salamander.svg.basic.SVGElement;
import org.w3c.dom.events.EventTarget;

/**
 * 
 * For each 'use' element, the SVG DOM maintains a shadow tree (the "instance tree") of objects of type SVGElementInstance. A SVGElementInstance  represents a single node in the instance tree. The root object in the instance tree is pointed to by the instanceRoot attribute on the SVGUseElement object for the corresponding 'use' element.
 * 
 * If the 'use' element references a simple graphics element such as a 'rect', then there is only a single SVGElementInstance object, and the correspondingElement attribute on this SVGElementInstance object is the SVGRectElement that corresponds to the referenced 'rect' element.
 * 
 * If the 'use' element references a 'g' which contains two 'rect' elements, then the instance tree contains three SVGElementInstance objects, a root SVGElementInstance object whose correspondingElement is the SVGGElement object for the 'g', and then two child SVGElementInstance objects, each of which has its correspondingElement that is an SVGRectElement object.
 * 
 * If the referenced object is itself a 'use', or if there are 'use' subelements within the referenced object, the instance tree will contain recursive expansion of the indirect references to form a complete tree. For example, if a 'use' element references a 'g', and the 'g' itself contains a 'use', and that 'use' references a 'rect', then the instance tree for the original (outermost) 'use' will consist of a hierarchy of SVGElementInstance objects, as follows:
 * <CODE>
 * SVGElementInstance #1 (parentNode=null, firstChild=#2, correspondingElement is the 'g')
 *  SVGElementInstance #2 (parentNode=#1, firstChild=#3, correspondingElement is the other 'use')
 *    SVGElementInstance #3 (parentNode=#2, firstChild=null, corresponding Element is the 'rect')
 * </CODE>
 * @author kitfox
 */
public interface SVGElementInstance extends EventTarget
{
    /**
     * The corresponding element to which this object is an instance. For example, if a 'use' element references a 'rect' element, then an SVGElementInstance  is created, with its correspondingElement being the SVGElementInstance object for the 'rect' element.
     */
    public SVGElement getCorrespondingElement();
    /**
     * The corresponding 'use' element to which this SVGElementInstance object belongs. When 'use' elements are nested (e.g., a 'use' references another 'use' which references a graphics element such as a 'rect'), then the correspondingUseElement is the outermost 'use' (i.e., the one which indirectly references the 'rect', not the one with the direct reference).
     */
    public SVGUseElement getCorrespondingUseElement();
    /**
     * The parent of this SVGElementInstance within the instance tree. All SVGElementInstance  objects have a parent except the SVGElementInstance which corresponds to the element which was directly referenced by the 'use' element, in which case parentNode is null.
     */
    public SVGElementInstance getParentNode();
    /**
     * An SVGElementInstanceList  that contains all children of this SVGElementInstance within the instance tree. If there are no children, this is an SVGElementInstanceList  containing no entries (i.e., an empty list).
     */
    public SVGElementInstanceList getChildNodes();
    /**
     * The first child of this SVGElementInstance within the instance tree. If there is no such SVGElementInstance, this returns null.
     */
    public SVGElementInstance getFirstChild();
    /**
     * The last child of this SVGElementInstance within the instance tree. If there is no such SVGElementInstance, this returns null.
     */
    public SVGElementInstance getLastChild();
    /**
     * The SVGElementInstance  immediately preceding this SVGElementInstance. If there is no such SVGElementInstance, this returns null.
     */
    public SVGElementInstance getPreviousSibling();
    /**
     * The SVGElementInstance  immediately following this SVGElementInstance. If there is no such SVGElementInstance, this returns null.
     */
    public SVGElementInstance getNextSibling();
}
