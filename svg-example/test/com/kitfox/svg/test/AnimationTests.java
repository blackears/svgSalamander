package com.kitfox.svg.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import com.kitfox.svg.Circle;
import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGElementException;
import com.kitfox.svg.SVGRoot;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.animation.AnimateMotion;
import com.kitfox.svg.animation.AnimationElement;

public class AnimationTests
{
	private final String UNIT_TEXT_SECOND = "s";
	private AnimationElement animation;
	
	public AnimationTests(AnimationElement element, String attributeName){
		animation = element;
		
		addPresentationAttribute("attributeName", attributeName);
		addCalcAttribute();
		addFillAttribute();
		addStartAttribute();
		URI uri = addDiagramToUniverse();
		SVGElement rootElement = SVGCache.getSVGUniverse().getDiagram(uri).getRoot();
		Circle c = new Circle();
		addToElement(rootElement, c);
		addToElement(c, animation);
		
	}
	private void addToElement(SVGElement rootElement, SVGElement child)
	{
		try
		{
			rootElement.loaderAddChild(null, child);
		} catch (SVGElementException e)
		{
			e.printStackTrace();
		}
		
	}
	private URI addDiagramToUniverse() {
		URI uri = null;
		try {
			File f = new File("test.svg");
			f.createNewFile();
			fillFile(f);
			uri = f.toURI();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SVGUniverse universe = SVGCache.getSVGUniverse();
		SVGDiagram diagram = new SVGDiagram(uri, universe);
		diagram.setRoot(new SVGRoot());
		return uri;
	}
	private void fillFile(File f) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		appendHeader(builder);
		builder.append("<svg> </svg>");
		FileWriter fw = new FileWriter(f);
		
		fw.write(builder.toString());
		
		fw.flush();
		fw.close();
	}
	private void appendHeader(StringBuilder builder)
	{
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("\n");
		builder.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
		builder.append("\n");
	}
	private void addCalcAttribute() {
		addPresentationAttribute("calcMode",
			"freeze");
	}
	private void addFillAttribute() {
		addPresentationAttribute("fill", "freeze");
	}
	private void addStartAttribute() {
		addPresentationAttribute("begin",
				String.valueOf(2) + UNIT_TEXT_SECOND);
		addPresentationAttribute("dur",
				String.valueOf(3) + UNIT_TEXT_SECOND);
	}
	private void addPresentationAttribute(String attributeName, String attributeValue) {
		try
		{
			animation.addAttribute(attributeName, AnimationElement.AT_XML, attributeValue);
		} catch (SVGElementException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		new AnimationTests(new AnimateColor(), "color");
//		new AnimationTests(new AnimateTransform(), null);
//		new AnimationTests(new Animate(), null);
		new AnimationTests(new AnimateMotion(), null);
	}
}
