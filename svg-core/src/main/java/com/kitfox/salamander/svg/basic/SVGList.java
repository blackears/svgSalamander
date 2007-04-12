/*
 * SVGLengthList.java
 *
 * Created on April 12, 2007, 1:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMException;
import com.kitfox.salamander.svg.SVGException;


/**
 * This interface defines a list of DOMSVGLength objects.
 * 
 * SVGSVGLengthList has the same attributes and methods as other SVGxxxList interfaces. Implementers may consider using a single base class to implement the various SVGxxxList interfaces.
 * @author kitfox
 */
public interface SVGList<T extends SVGDataType> extends SVGDataType
{
    /**
     * The number of items in the list.
     */
    public int getNumberOfItems();
    
    /**
     * Clears all existing current items from the list, with the result being an empty list.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     */
    public void clear() throws DOMException;
    /**
     * Clears all existing current items from the list and re-initializes the list to hold the single item specified by the parameter.
     * @param newItem The item which should become the only member of the list.
     * @return The item being inserted into the list.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     * @throws com.kitfox.salamander.svg.SVGException SVG_WRONG_TYPE_ERR: Raised if parameter newItem is the wrong type of object for the given list.
     */
    public T initialize(SVGLength newItem) throws DOMException, SVGException;
    /**
     * Returns the specified item from the list.
     * @param index The index of the item from the list which is to be returned. The first item is number 0.
     * @return The selected item.
     * @throws com.kitfox.salamander.svg.DOMException INDEX_SIZE_ERR: Raised if the index number is negative or greater than or equal to numberOfItems. 
     */
    public T getItem(int index) throws DOMException;
    /**
     * Inserts a new item into the list at the specified position. The first item is number 0. If newItem is already in a list, it is removed from its previous list before it is inserted into this list.
     * @param newItem The item which is to be inserted into the list.
     * @param index The index of the item before which the new item is to be inserted. The first item is number 0.
     * If the index is equal to 0, then the new item is inserted at the front of the list. If the index is greater than or equal to numberOfItems, then the new item is appended to the end of the list.
     * @return The inserted item.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     * @throws com.kitfox.salamander.svg.SVGException SVG_WRONG_TYPE_ERR: Raised if parameter newItem is the wrong type of object for the given list. 
     */
    public T insertItemBefore(SVGLength newItem, int index) throws DOMException, SVGException;
    /**
     * Replaces an existing item in the list with a new item. If newItem is already in a list, it is removed from its previous list before it is inserted into this list.
     * @param newItem The item which is to be inserted into the list.
     * @param index The index of the item which is to be replaced. The first item is number 0.
     * @return The inserted item.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     * INDEX_SIZE_ERR: Raised if the index number is negative or greater than or equal to numberOfItems. 
     * @throws com.kitfox.salamander.svg.SVGException SVG_WRONG_TYPE_ERR: Raised if parameter newItem is the wrong type of object for the given list.
     */
    public T replaceItem(SVGLength newItem, int index) throws DOMException, SVGException;
    /**
     * Removes an existing item from the list.
     * @param index The index of the item which is to be removed. The first item is number 0.
     * @return The removed item.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     * INDEX_SIZE_ERR: Raised if the index number is negative or greater than or equal to numberOfItems. 
     */
    public T removeItem(int index) throws DOMException;
    /**
     * Inserts a new item at the end of the list. If newItem is already in a list, it is removed from its previous list before it is inserted into this list.
     * @param newItem The item which is to be inserted into the list. The first item is number 0.
     * @return The inserted item.
     * @throws com.kitfox.salamander.svg.DOMException NO_MODIFICATION_ALLOWED_ERR: Raised when the list cannot be modified.
     * @throws com.kitfox.salamander.svg.SVGException SVG_WRONG_TYPE_ERR: Raised if parameter newItem is the wrong type of object for the given list.
     */
    public T appendItem(SVGLength newItem) throws DOMException, SVGException;
    
}
