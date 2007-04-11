/*
 * GraphicsDocumentHandler.java
 *
 * Created on March 3, 2007, 7:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.parser;

import com.kitfox.salamander.parser.SVGParser.SVGParserListener;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 *
 * @author kitfox
 */
public class SVGParserHandler implements ContentHandler
{
//    LinkedList<CodeGenNode> nodeStack = new LinkedList<CodeGenNode>();
    
    final SVGParserListener listener;
    
    /** Creates a new instance of GraphicsDocumentHandler */
    public SVGParserHandler(SVGParserListener listener)
    {
        this.listener = listener;
    }
    
    public void setDocumentLocator(Locator locator)
    {
    }
    
    public void startDocument() throws SAXException
    {
    }
    
    public void endDocument() throws SAXException
    {
    }
    
    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
    }
    
    public void endPrefixMapping(String prefix) throws SAXException
    {
    }
    
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
    {
    }
        
    
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException
    {
    }
    
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
    {
    }
    
    public void processingInstruction(String target, String data) throws SAXException
    {
    }
    
    public void skippedEntity(String name) throws SAXException
    {
    }
    
}
