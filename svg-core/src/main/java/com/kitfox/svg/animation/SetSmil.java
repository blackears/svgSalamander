/*
 * SVG Salamander
 * Copyright (c) 2004, Mark McKay
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *   - Redistributions of source code must retain the above 
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials 
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 * 
 * Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 * projects can be found at http://www.kitfox.com
 *
 * Created on August 15, 2004, 2:51 AM
 */

package com.kitfox.svg.animation;

import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGLoaderHelper;
import com.kitfox.svg.animation.parser.AnimTimeParser;
import com.kitfox.svg.xml.StyleAttribute;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * Set is used to set a textual value; most likely for a style element.
 *
 * @author Mark McKay
 * @author <a href="mailto:mark@kitfox.com">Mark McKay</a>
 */
public class SetSmil extends AnimationElement
{
    public static final String TAG_NAME = "set";
    
    private String toValue;
    
    /** Creates a new instance of Set */
    public SetSmil()
    {
    }

    @Override
    public String getTagName()
    {
        return TAG_NAME;
    }

    @Override
    public void loaderStartElement(SVGLoaderHelper helper, Attributes attrs, SVGElement parent) throws SAXException
    {
		//Load style string
        super.loaderStartElement(helper, attrs, parent);

        toValue = attrs.getValue("to");
    }

    @Override
    protected void rebuild(AnimTimeParser animTimeParser) throws SVGException
    {
        super.rebuild(animTimeParser);

        StyleAttribute sty = new StyleAttribute();

        if (getPres(sty.setName("to")))
        {
            String newVal = sty.getStringValue();
            toValue = newVal;
        }
    }

    /**
     * @return the toValue
     */
    public String getToValue()
    {
        return toValue;
    }

    /**
     * @param toValue the toValue to set
     */
    public void setToValue(String toValue)
    {
        this.toValue = toValue;
    }
}
