/*
 * SVGElementInstanceList.java
 *
 * Created on April 12, 2007, 6:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.docStruct;

/**
 * 
 * The SVGElementInstanceList  interface provides the abstraction of an ordered collection of SVGElementInstance  objects, without defining or constraining how this collection is implemented.
 * @author kitfox
 */
public interface SVGElementInstanceList
{
    /**
     * The number of SVGElementInstance  objects in the list. The range of valid child indices is 0 to length-1 inclusive.
     */
    public int getLength();
    /**
     * Returns the indexth item in the collection. If index is greater than or equal to the number of nodes in the list, this returns null.
     * @param index Index into the collection.
     * @return The SVGElementInstance  object at the indexth position in the SVGElementInstanceList, or null if that is not a valid index.
     */
    public SVGElementInstance item(int index);
}
