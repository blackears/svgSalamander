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
 * Created on July 23, 2007
 */

package com.kitfox.svg.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author kitfox
 */
public class Base64InputStream extends FilterInputStream implements Base64Consts
{
    static final HashMap<Byte, Integer> lookup64 = new HashMap<Byte, Integer>();
    static {
        byte[] ch = BASE64_CHARS.getBytes();
        for (int i = 0; i < ch.length; i++)
        {
            lookup64.put(new Byte(ch[i]), new Integer(i));
        }
    }
    
    int buf;
    int charsInBuf;
    
    /** Creates a new instance of Base64InputStream */
    public Base64InputStream(InputStream in)
    {
        super(in);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        for (int i = 0; i < len; ++i)
        {
            int val = read();
            if (val == -1)
            {
                return i == 0 ? -1 : i;
            }
            b[off + i] = (byte)val;
        }
        return len;
    }


    @Override
    public int read() throws IOException
    {
        if (charsInBuf == 0)
        {
            fillBuffer();
            if (charsInBuf == 0)
            {
                return -1;
            }
        }
        
        return (buf >> (--charsInBuf * 8)) & 0xff;
    }
    
    private void fillBuffer() throws IOException
    {
        //Read next 4 characters
        int bitsRead = 0;
        while (bitsRead < 24)
        {
            int val = in.read();
            if (val == -1 || val == '=') break;

            Integer lval = (Integer)lookup64.get(new Byte((byte)val));
            if (lval == null) continue;

            buf = buf << 6 | lval.byteValue();
            bitsRead += 6;
        }

        switch (bitsRead)
        {
            case 6:
            {
                throw new RuntimeException("Invalid termination of base64 encoding.");
            }
            case 12:
            {
                buf >>= 4;
                bitsRead = 8;
                break;
            }
            case 18:
            {
                buf >>= 2;
                bitsRead = 16;
                break;
            }
            case 0:
            case 24:
            {
                break;
            }
            default:
            {
                assert false : "Should never encounter other bit counts";
            }
        }

        charsInBuf = bitsRead / 8;
    }
}
