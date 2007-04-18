/*
 * JSTest.java
 *
 * Created on April 16, 2007, 12:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.javascript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * http://java.sun.com/developer/technicalArticles/J2SE/Desktop/scripting/
 *
 * @author kitfox
 */
public class JSTest
{
    
    /** Creates a new instance of JSTest */
    public JSTest()
    {
          ScriptEngineManager mgr = new ScriptEngineManager();
          ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
          try {
            jsEngine.eval("print('Hello, world!')");
          } catch (ScriptException ex) {
              ex.printStackTrace();
          }   
    }

    public static void main(String[] args)
    {
        new JSTest();
    }
}
