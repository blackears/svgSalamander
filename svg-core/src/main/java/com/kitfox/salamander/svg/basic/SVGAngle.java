/*
 * SVGAngle.java
 *
 * Created on April 12, 2007, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

import com.kitfox.salamander.svg.DOMString;

/**
 * 
 * The SVGAngle interface corresponds to the &lt;angle&gt; basic data type.
 * @author kitfox
 */
public interface SVGAngle extends SVGDataType
{
    public static enum Type {
        /**
         * The unit type is not one of predefined unit types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * No unit type was provided (i.e., a unitless value was specified). For angles, a unitless value is treated the same as if degrees were specified.
         */
        UNSPECIFIED, 
        /**
         * The unit type was explicitly set to degrees.
         */
        DEG, 
        /**
         * The unit type is radians.
         */
        RAD, 
        /**
         * The unit type is grads.
         */
        GRAD};
    
    /**
     * The type of the value as specified by one of the constants specified above.
     */
    public Type getUnitType();
    /**
     * The angle value as a floating point value, in degrees. Setting this attribute will cause valueInSpecifiedUnits and valueAsString to be updated automatically to reflect this setting.
     */
    public float getValue();
    /**
     * The angle value as a floating point value, in the units expressed by unitType. Setting this attribute will cause value and valueAsString to be updated automatically to reflect this setting.
     */
    public float getValueInSpecifiedUnits();
    /**
     * The angle value as a string value, in the units expressed by unitType. Setting this attribute will cause value and valueInSpecifiedUnits to be updated automatically to reflect this setting.
     */
    public DOMString getValueAsString();
    
    /**
     * Reset the value as a number with an associated unitType, thereby replacing the values for all of the attributes on the object.
     * @param unitType The unitType for the angle value (e.g., SVG_ANGLETYPE_DEG).
     * @param valueInSpecifiedUnits The angle value.
     */
    public void newValueSpecifiedUnits(Type unitType, float valueInSpecifiedUnits);
    /**
     * Preserve the same underlying stored value, but reset the stored unit identifier to the given unitType. Object attributes unitType, valueAsSpecified and valueAsString might be modified as a result of this method.
     * @param unitType The unitType to switch to (e.g., SVG_ANGLETYPE_DEG).
     */
    public void convertToSpecifiedUnits(Type unitType);
}
