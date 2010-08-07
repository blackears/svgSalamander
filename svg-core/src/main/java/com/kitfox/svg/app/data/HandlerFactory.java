/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kitfox.svg.app.data;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 *
 * @author kitfox
 */
public class HandlerFactory implements URLStreamHandlerFactory
{
    static Handler handler = new Handler();

    public URLStreamHandler createURLStreamHandler(String protocol)
    {
        if ("data".equals(protocol))
        {
            return handler;
        }
        return null;
    }
}
