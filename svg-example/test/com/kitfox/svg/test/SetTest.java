package com.kitfox.svg.test;

import java.net.URI;
import java.net.URISyntaxException;

import com.kitfox.svg.Circle;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGElementException;
import com.kitfox.svg.SVGRoot;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.animation.AnimationElement;

public class SetTest
{
	public static void main(String[] args) throws SVGElementException {
		SVGUniverse universe = new SVGUniverse();
		universe.setVerbose(true);
		URI uri = null;
		try
		{
			uri = new URI("local/salaTest.svg");
		} catch (URISyntaxException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SVGDiagram diagram = new SVGDiagram(uri, universe);
		diagram.setRoot(new SVGRoot());
		Circle c = new Circle();
		c.addAttribute("cx", AnimationElement.AT_XML, "3");
		c.addAttribute("id", AnimationElement.AT_XML, "circle");
		//Here it throws the NPE. 
		diagram.getRoot().loaderAddChild(null, c);
		System.out.println(c.getParent());
		SVGElement element = diagram.getRoot().getChild("circle");
		System.out.println(element);
	}
}
