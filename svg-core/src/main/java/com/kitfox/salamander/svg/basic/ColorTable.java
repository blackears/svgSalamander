/*
 * ColorTable.java
 *
 *
 *  The Salamander Project - 2D and 3D graphics libraries in Java
 *  Copyright (C) 2004 Mark McKay
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *  Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 *  projects can be found at http://www.kitfox.com
 *
 * Created on January 26, 2004, 4:34 AM
 */

package com.kitfox.svg.xml;

import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class ColorTable 
{

    static final Map colorTable;
    static {
        HashMap table = new HashMap();
        table.put("aliceblue", new Color(240, 248, 255));
        table.put("antiquewhite", new Color(250, 235, 215));
        table.put("aqua", new Color(0, 255, 255));
        table.put("aquamarine", new Color(127, 255, 212));
        table.put("azure", new Color(240, 255, 255));
        table.put("beige", new Color(245, 245, 220));
        table.put("bisque", new Color(255, 228, 196));
        table.put("black", new Color(0, 0, 0));
        table.put("blanchedalmond", new Color(255, 235, 205));
        table.put("blue", new Color(0, 0, 255));
        table.put("blueviolet", new Color(138, 43, 226));
        table.put("brown", new Color(165, 42, 42));
        table.put("burlywood", new Color(222, 184, 135));
        table.put("cadetblue", new Color(95, 158, 160));
        table.put("chartreuse", new Color(127, 255, 0));
        table.put("chocolate", new Color(210, 105, 30));
        table.put("coral", new Color(255, 127, 80));
        table.put("cornflowerblue", new Color(100, 149, 237));
        table.put("cornsilk", new Color(255, 248, 220));
        table.put("crimson", new Color(220, 20, 60));
        table.put("cyan", new Color(0, 255, 255));
        table.put("darkblue", new Color(0, 0, 139));
        table.put("darkcyan", new Color(0, 139, 139));
        table.put("darkgoldenrod", new Color(184, 134, 11));
        table.put("darkgray", new Color(169, 169, 169));
        table.put("darkgreen", new Color(0, 100, 0));
        table.put("darkgrey", new Color(169, 169, 169));
        table.put("darkkhaki", new Color(189, 183, 107));
        table.put("darkmagenta", new Color(139, 0, 139));
        table.put("darkolivegreen", new Color(85, 107, 47));
        table.put("darkorange", new Color(255, 140, 0));
        table.put("darkorchid", new Color(153, 50, 204));
        table.put("darkred", new Color(139, 0, 0));
        table.put("darksalmon", new Color(233, 150, 122));
        table.put("darkseagreen", new Color(143, 188, 143));
        table.put("darkslateblue", new Color(72, 61, 139));
        table.put("darkslategray", new Color(47, 79, 79));
        table.put("darkslategrey", new Color(47, 79, 79));
        table.put("darkturquoise", new Color(0, 206, 209));
        table.put("darkviolet", new Color(148, 0, 211));
        table.put("deeppink", new Color(255, 20, 147));
        table.put("deepskyblue", new Color(0, 191, 255));
        table.put("dimgray", new Color(105, 105, 105));
        table.put("dimgrey", new Color(105, 105, 105));
        table.put("dodgerblue", new Color(30, 144, 255));
        table.put("firebrick", new Color(178, 34, 34));
        table.put("floralwhite", new Color(255, 250, 240));
        table.put("forestgreen", new Color(34, 139, 34));
        table.put("fuchsia", new Color(255, 0, 255));
        table.put("gainsboro", new Color(220, 220, 220));
        table.put("ghostwhite", new Color(248, 248, 255));
        table.put("gold", new Color(255, 215, 0));
        table.put("goldenrod", new Color(218, 165, 32));
        table.put("gray", new Color(128, 128, 128));
        table.put("grey", new Color(128, 128, 128));
        table.put("green", new Color(0, 128, 0));
        table.put("greenyellow", new Color(173, 255, 47));
        table.put("honeydew", new Color(240, 255, 240));
        table.put("hotpink", new Color(255, 105, 180));
        table.put("indianred", new Color(205, 92, 92));
        table.put("indigo", new Color(75, 0, 130));
        table.put("ivory", new Color(255, 255, 240));
        table.put("khaki", new Color(240, 230, 140));
        table.put("lavender", new Color(230, 230, 250));
        table.put("lavenderblush", new Color(255, 240, 245));
        table.put("lawngreen", new Color(124, 252, 0));
        table.put("lemonchiffon", new Color(255, 250, 205));
        table.put("lightblue", new Color(173, 216, 230));
        table.put("lightcoral", new Color(240, 128, 128));
        table.put("lightcyan", new Color(224, 255, 255));
        table.put("lightgoldenrodyellow", new Color(250, 250, 210));
        table.put("lightgray", new Color(211, 211, 211));
        table.put("lightgreen", new Color(144, 238, 144));
        table.put("lightgrey", new Color(211, 211, 211));
        table.put("lightpink", new Color(255, 182, 193));
        table.put("lightsalmon", new Color(255, 160, 122));
        table.put("lightseagreen", new Color(32, 178, 170));
        table.put("lightskyblue", new Color(135, 206, 250));
        table.put("lightslategray", new Color(119, 136, 153));
        table.put("lightslategrey", new Color(119, 136, 153));
        table.put("lightsteelblue", new Color(176, 196, 222));
        table.put("lightyellow", new Color(255, 255, 224));
        table.put("lime", new Color(0, 255, 0));
        table.put("limegreen", new Color(50, 205, 50));
        table.put("linen", new Color(250, 240, 230));
        table.put("magenta", new Color(255, 0, 255));
        table.put("maroon", new Color(128, 0, 0));
        table.put("mediumaquamarine", new Color(102, 205, 170));
        table.put("mediumblue", new Color(0, 0, 205));
        table.put("mediumorchid", new Color(186, 85, 211));
        table.put("mediumpurple", new Color(147, 112, 219));
        table.put("mediumseagreen", new Color(60, 179, 113));
        table.put("mediumslateblue", new Color(123, 104, 238));
        table.put("mediumspringgreen", new Color(0, 250, 154));
        table.put("mediumturquoise", new Color(72, 209, 204));
        table.put("mediumvioletred", new Color(199, 21, 133));
        table.put("midnightblue", new Color(25, 25, 112));
        table.put("mintcream", new Color(245, 255, 250));
        table.put("mistyrose", new Color(255, 228, 225));
        table.put("moccasin", new Color(255, 228, 181));
        table.put("navajowhite", new Color(255, 222, 173));
        table.put("navy", new Color(0, 0, 128));
        table.put("oldlace", new Color(253, 245, 230));
        table.put("olive", new Color(128, 128, 0));
        table.put("olivedrab", new Color(107, 142, 35));
        table.put("orange", new Color(255, 165, 0));
        table.put("orangered", new Color(255, 69, 0));
        table.put("orchid", new Color(218, 112, 214));
        table.put("palegoldenrod", new Color(238, 232, 170));
        table.put("palegreen", new Color(152, 251, 152));
        table.put("paleturquoise", new Color(175, 238, 238));
        table.put("palevioletred", new Color(219, 112, 147));
        table.put("papayawhip", new Color(255, 239, 213));
        table.put("peachpuff", new Color(255, 218, 185));
        table.put("peru", new Color(205, 133, 63));
        table.put("pink", new Color(255, 192, 203));
        table.put("plum", new Color(221, 160, 221));
        table.put("powderblue", new Color(176, 224, 230));
        table.put("purple", new Color(128, 0, 128));
        table.put("red", new Color(255, 0, 0));
        table.put("rosybrown", new Color(188, 143, 143));
        table.put("royalblue", new Color(65, 105, 225));
        table.put("saddlebrown", new Color(139, 69, 19));
        table.put("salmon", new Color(250, 128, 114));
        table.put("sandybrown", new Color(244, 164, 96));
        table.put("seagreen", new Color(46, 139, 87));
        table.put("seashell", new Color(255, 245, 238));
        table.put("sienna", new Color(160, 82, 45));
        table.put("silver", new Color(192, 192, 192));
        table.put("skyblue", new Color(135, 206, 235));
        table.put("slateblue", new Color(106, 90, 205));
        table.put("slategray", new Color(112, 128, 144));
        table.put("slategrey", new Color(112, 128, 144));
        table.put("snow", new Color(255, 250, 250));
        table.put("springgreen", new Color(0, 255, 127));
        table.put("steelblue", new Color(70, 130, 180));
        table.put("tan", new Color(210, 180, 140));
        table.put("teal", new Color(0, 128, 128));
        table.put("thistle", new Color(216, 191, 216));
        table.put("tomato", new Color(255, 99, 71));
        table.put("turquoise", new Color(64, 224, 208));
        table.put("violet", new Color(238, 130, 238));
        table.put("wheat", new Color(245, 222, 179));
        table.put("white", new Color(255, 255, 255));
        table.put("whitesmoke", new Color(245, 245, 245));
        table.put("yellow", new Color(255, 255, 0));
        table.put("yellowgreen", new Color(154, 205, 50));
        
        colorTable = Collections.unmodifiableMap(table);
    }

    static ColorTable singleton = new ColorTable();

    /** Creates a new instance of ColorTable */
    protected ColorTable() {
//        buildColorList();
    }

    static public ColorTable instance() { return singleton; }

    public Color lookupColor(String name) {
        Object obj = colorTable.get(name.toLowerCase());
        if (obj == null) return null;

        return (Color)obj;
    }

    public static Color parseColor(String val)
    {
        Color retVal = null;

        if (val.charAt(0) == '#')
        {
            String hexStrn = val.substring(1);
            
            if (hexStrn.length() == 3)
            {
                hexStrn = "" + hexStrn.charAt(0) + hexStrn.charAt(0) + hexStrn.charAt(1) + hexStrn.charAt(1) + hexStrn.charAt(2) + hexStrn.charAt(2);
            }
            int hexVal = parseHex(hexStrn);

            retVal = new Color(hexVal);
        }
        else
        {
            final Matcher rgbMatch = Pattern.compile("rgb\\((\\d+),(\\d+),(\\d+)\\)", Pattern.CASE_INSENSITIVE).matcher("");

            rgbMatch.reset(val);
            if (rgbMatch.matches())
            {
                int r = Integer.parseInt(rgbMatch.group(1));
                int g = Integer.parseInt(rgbMatch.group(2));
                int b = Integer.parseInt(rgbMatch.group(3));
                retVal = new Color(r, g, b);
            }
            else
            {
                Color lookupCol = ColorTable.instance().lookupColor(val);
                if (lookupCol != null) retVal = lookupCol;
            }
        }

        return retVal;
    }

    public static int parseHex(String val)
    {
        int retVal = 0;
        
        for (int i = 0; i < val.length(); i++)
        {
            retVal <<= 4;
            
            char ch = val.charAt(i);
            if (ch >= '0' && ch <= '9')
            {
                retVal |= ch - '0';
            }
            else if (ch >= 'a' && ch <= 'z')
            {
                retVal |= ch - 'a' + 10;
            }
            else if (ch >= 'A' && ch <= 'Z')
            {
                retVal |= ch - 'A' + 10;
            }
            else throw new RuntimeException();
        }
        
        return retVal;
    }

}
