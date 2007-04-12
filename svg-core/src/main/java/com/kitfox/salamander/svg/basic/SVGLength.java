/*
 * SVGAnimatedInteger.java
 *
 * Created on April 12, 2007, 12:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.svg.basic;

/**
 * 
 * The SVGLength  interface corresponds to the &lt;length&gt; basic data type.
 * @author kitfox
 */
public interface SVGLength extends SVGDataType
{
    public static enum Type {
        /**
         * The unit type is not one of predefined unit types. It is invalid to attempt to define a new value of this type or to attempt to switch an existing value to this type.
         */
        UNKNOWN, 
        /**
         * No unit type was provided (i.e., a unitless value was specified), which indicates a value in user units.
         */
        NUMBER, 
        /**
         * A percentage value was specified.
         */
        PERCENTAGE, 
        /**
         * A value was specified using the "em" units defined in CSS2.
         */
        EMS, 
        /**
         * A value was specified using the "ex" units defined in CSS2.
         */
        EXS, 
        /**
         * A value was specified using the "px" units defined in CSS2.
         */
        PX, 
        /**
         *  	A value was specified using the "cm" units defined in CSS2.
         */
        CM, 
        /**
         * A value was specified using the "mm" units defined in CSS2.
         */
        MM, 
        /**
         * A value was specified using the "in" units defined in CSS2.
         */
        IN, 
        /**
         * A value was specified using the "pt" units defined in CSS2.
         */
        PT, 
        /**
         * A value was specified using the "pc" units defined in CSS2.
         */
        PC
    };
    
    /**
     * The type of the value as specified by one of the constants specified above.
     */
    public Type getUnitType();
    
    /**
     * The value as an floating point value, in user units. Setting this attribute will cause valueInSpecifiedUnits and valueAsString to be updated automatically to reflect this setting.
     */
    public float getValue();
    /**
     * The value as an floating point value, in the units expressed by unitType. Setting this attribute will cause value and valueAsString to be updated automatically to reflect this setting.
     */
    public float getValueInSpecifiedUnits();
    
    /**
     * The value as a string value, in the units expressed by unitType. Setting this attribute will cause value and valueInSpecifiedUnits to be updated automatically to reflect this setting.
     */
    public SVGString getValueAsString();
    
    /**
     * Reset the value as a number with an associated unitType, thereby replacing the values for all of the attributes on the object.
     * @param unitType The unitType for the value (e.g., SVG_LENGTHTYPE_MM).
     * @param valueInSpecifiedUnits The new value.
     */
    public void newValueSpecifiedUnits(Type unitType, float valueInSpecifiedUnits);
    /**
     * Preserve the same underlying stored value, but reset the stored unit identifier to the given unitType. Object attributes unitType, valueAsSpecified and valueAsString might be modified as a result of this method. For example, if the original value were "0.5cm" and the method was invoked to convert to millimeters, then the unitType would be changed to SVG_LENGTHTYPE_MM, valueAsSpecified would be changed to the numeric value 5 and valueAsString would be changed to "5mm".
     * @param unitType The unitType to switch to (e.g., SVG_LENGTHTYPE_MM).
     */
    public void convertToSpecifiedUnits(Type unitType);
}
