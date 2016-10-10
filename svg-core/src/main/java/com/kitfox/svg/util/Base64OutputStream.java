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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author kitfox
 */
public class Base64OutputStream extends FilterOutputStream implements Base64Consts
{
    int buf;
    int bitsUsed;
    int charsPrinted;
    
    /** Creates a new instance of Base64OutputSream */
    public Base64OutputStream(OutputStream out)
    {
        super(out);
    }

    @Override
    public void close() throws IOException
    {
        writeBits();
        super.close();
    }
    
    @Override
    public void write(int b) throws IOException
    {
        buf = buf << 8 | (b & 0xff);
        bitsUsed += 8;
        if (bitsUsed == 24)
        {
            writeBits();
        }
    }

    private void writeBits() throws IOException
    {
        int padSize;
        //Pad unused bits with 0
        switch (bitsUsed)
        {
            case 8:
            {
                bitsUsed = 12;
                buf <<= 4;
                padSize = 2;
                break;
            }
            case 16:
            {
                bitsUsed = 18;
                buf <<= 2;
                padSize = 1;
                break;
            }
            default:
            {
                padSize = 0;
                break;
            }
        }
        
        if (charsPrinted == 76)
        {
            out.write('\r');
            out.write('\n');
            charsPrinted = 0;
        } 
        
        for (; bitsUsed > 0; bitsUsed -= 6)
        {
            int b = buf >> (bitsUsed - 6) & 0x3f;
            out.write(BASE64_CHARS.charAt(b));
        }
        
        for (int i = 0; i < padSize; i++)
        {
            out.write('=');
        }
        
        charsPrinted += 4;
    }
}
