# Using SVG Salamander

SVG Salamander uses Java2D for all rendering, so it should integrate nicely with Swing projects.

The included `com.kitfox.svg.app.SVGPlayer` and `com.kitfox.svg.app.SVGViewer` classes implement a simple movie player and viewer for SVG content and examining the code is a great starting point for understanding the technical underpinnings of SVG Salamander.

The basic process is:

- Create an `SVGUniverse`
- Call `universe.loadSVG()` to load the SVG file into memory.  (You can call this several times to load multiple files into the same universe.  If an SVG file references another SVG file, the referenced files will be automatically loaded)
- Call `universe.getDiagram()` to get an `SVGDiagram` object that can be used to render the SVG file.
- Create a Graphics2D that the SVG should be rendered to.  Make sure that either the `Graphics2D` has it's clip area set or that you call `diagram.setIgnoringClipHeuristic(true)`.  If the clip heuristic is not being ignored, shapes that do not appear within the clip area will not be rendered in the first place.
- To change rendering with time, call `universe.setCurTime()` and then `universe.updateTime()` to update the universe to the time required.  Call `diagram.render()` again to draw the new frame at this updated time.

The `SVGDiagram` is the object you are going to be most concerned with.  It provides a way to access the root of the SVG document you loaded and a means to render it to a `Graphics2D`.

## Accessing the document

All elements in an SVG document can be given an id that can be used to access the document in parts.  For example, you can create a circle with the code `<circle id="myCircle" cx="100" cy="100" r="50"/>`. This circle can be referenced later with the key word `myCircle`.

SVG Salamander provides the ability to access document parts with SVGDiagram.getElement(String elementName).  You can then read and write the attributes of the element directly.

## Using SVG in forms

The Salamander JAR contains some controls to let you use SVG easily in Swing forms.  They are:

- com.kitfox.svg.app.beans.SVGPanel
- com.kitfox.svg.app.beans.SVGIcon

These components are both beans and can be used in NetBeans or other beans aware Java editors.  Once you have installed the Jar in your editor (see your IDE's documentation for how to do this), you can place them on your form.

Both of these beans have anti-aliasing turned off by default.  To turn it on, be sure to call `setAntiAlias(true)`.

A fast way to place an SVG element on your form:

- Place an SVG file in the resources directory of your project
- Place a SVGPanel on your form
- Set SvgResourcePath to the path of your resource
- Check ScaleToFit to force the graphic to scale to the fill size of the panel
    
## Updating the SVG document

SVG Salamander parallels the DOM model internally, and can be interactively updated at runtime.  That is, you can change colors, positions and other aspects of a loaded SVGDiagram.

### Tree Navigation

Use `SVGDiagram.getRoot()` to get an instance of SVGRoot.  The SVGRoot corresponds to the <SVG> element of an SVG document.  You can then get a list of it's children by calling `SVGRoot.getChildren(null)`.  The tree can be further navigated at deeper depths by calling the `getChildren()` method.

### Adding and Editing Attributes

SVG Salamander stores attributes as strings and parses them into values at runtime to draw the SVG elements.  This allows for the rich variety of types of values that the SVG specification defines.  Attributes can also be either style attributes or presentation attributes. Style attributes are defined in the `style` attribute of an SVG element.  Presentation attributes are the attributes defined as attributes outside of the `style` attribute.  If both style and presentation attributes are present, the style attribute overrides the presentation attribute.  For example:
    
```
<ellipse id="myCircle" style="fill:red; stroke:black; stroke-width:5;" cx="30" cy="80" rx="10" ry="20"/> 
```

In the above, the style attributes are fill, stroke and stroke-width while the presentation attributes are cx, cy, rx and ry.

SVG Salamander distinguishes between the two types of attributes by using the `AnimationElement.AT_CSS` and `AnimationElement.AT_XML` constants.  `AnimationElement.AT_CSS` represents style attributes, and `AnimationElement.AT_XML` represents presentation attributes.  If both style and presentation attributes exist for the same attribute name, the style attribute takes precedence.

To check if an attribute is defined:

```
svgElement.hasAttribute("x", AnimationElement.AT_XML);
```

To add a new attribute, call your element with the name of the attribute, a constant representing whether this attribute should be a style or presentation attribute, and a valid string representing the value of this attribute.  If this attribute is already defined, this will throw a `RuntimeException`, so make sure to call `hasAttribute()` first if you're not sure if this attribute exists.  The following example adds a new style attribute to the given SVG element:

```
svgElement.addAttribute("fill-opacity", AnimationElement.AT_CSS, "0.5");
```

To set an attribute, call the set function with the string representation of the value to set the attribute to.  Make sure that the element already exists, or this will throw an exception:

```
svgElement.setAttribute("x", AnimationElement.AT_XML, "0.5");
```

Sometimes after setting an attribute, it will be necessary to take an animation step to update the element to reflect it's current state.  For example, after setting the `r` attribute of a circle, it will be necessary to call `updateTime(timeInSeconds)` on the circle or on one of the circle's ancestors to update the circle's shape.  If your SVG diagram isn't using animation, you can pass any value in as the time.  Not all attributes will require `updateTime()` to be called, but calling this will not hurt.  Typically, attributes that alter shape will require an update while attributes that alter fills and colors do not.  For convenience, you might want to update all the elements in a diagram, and then call `svgDiagram.updateTime()` to update the entire document at once.

```
circle.setAttribute("r", AnimationElement.AT_XML, "10");
circle.updateTime(0f); 
```

At the moment, you can only set the text of `tspan` elements.  You can do this by calling `setText()` on the `tspan` element.  After doing this, you need to call `rebuild()` on the text element that contains the `tspan` element to update the caching data.

To get the value of an attribute, you can create an attribute and call `getStyle()`.  This will overwrite the value in the passed style attribute with the element's value of the attribute.

```
StyleAttribute style = new StyleAttribute();
style.setName("fill");
svgElement.getStyle(style);
double value = style.getDoubleValue();
```

Note that getting the value of a style attribute will first check to see if this attribute has a style set.  If not, it checks to find the closest ancestor that may have this style set.  If no ancestor has this attribute set, it then checks to see if a presentation attribute with the same name is set on this element.  This is in keeping with the SVG convention that style attributes override first ancestors and then presentation attributes.  To get the value of only the style attribute of this element, call `getStyleAbsolute()` instead.  `getPresAbsolute()` will check for the local presentation attribute.

### Adding and removing children

To add a new element, create an instance of the element you wish to add, set its attributes, and then add it to its parent element in the scene graph:

```
SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
Group group = (Group)diagram.getElement("parentGroup"); 
Circle circle = new Circle(); 
try { 
    circle.addAttribute("cx", AnimationElement.AT_XML, "80"); 
    circle.addAttribute("cy", AnimationElement.AT_XML, "80"); 
    circle.addAttribute("r", AnimationElement.AT_XML, "10"); 
    group.loaderAddChild(null, circle);
    
    //Update animation state for group and it's descendants so that it reflects new animation values.
    // We could also call diagram.update(0.0) or SVGCache.getSVGUniverse().update().  Note that calling
    // circle.update(0.0) won't display anything since even though it will update the circle's state,
    // it won't update the parent group's state.
    group.updateTime(0.0);    
} catch (SVGException e) { 
    e.printStackTrace(); 
}
```

To remove an element, call `removeChild()` on an element capable of having children:

```
SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
Group group = (Group)diagram.getElement("circleGroup"); 
Circle circle = (Circle)diagram.getElement("circleToRemove"); 
try { 
    group.removeChild(circle); 
} catch (SVGException e) { 
    e.printStackTrace(); 
}
```

## Picking

To pick shapes within an SVGDiagram, use the `pick()` method:

```
SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri); 
Point pickPoint = new Point(20, 80); 
Vector pickedElements = diagram.pick(pickPoint, null);
```

The `pick` method will return a vector of vectors of `SVGElements`.  Each vector contains the path from the root of the diagram to the picked `RenderableElement`.

## Ant Task

The SVG Salamander Ant task allows you to easily convert SVG documents to images from an Ant script.  To use it, include the SVGSalamander.jar in your class path and write an Ant target similar to:

```
    <target name="testAntTask">
        <typedef name="SVGToImage" classname="com.kitfox.svg.app.ant.SVGToImageAntTask" classpath="www/binaries/svgSalamander.jar"/>

        <SVGToImage format="png" antiAlias="false" verbose="true">
            <fileset dir="examples">
                <include name="hinorei.svg"/>
            </fileset>
        </SVGToImage>
    </target>
```

### Parameters:

- destDir - If present, specifies a directory to write SVG files to.  Otherwise writes images to directory SVG file was found in
- format - File format for output images.  The java core javax.imageio.ImageIO class is used for creating images, so format strings will depend on what files your system is configured to handle.  By default, "gif", "jpg" and "png" files are guaranteed to be present.  If omitted, "png" is used by default.
- backgroundColor - Optional background color.  Color can be specified as a standard HTML color.  That is, as the name of a standard color such as "blue" or "limegreen", using the # notation as in #ff00ff for magenta, or in rgb format listing the components as in rgb(255, 192, 192) for pink.  If omitted, background is transparent.
- antiAlias - If set, shapes are drawn using anti-aliasing.  Defaults to true.
- interpolation - String describing image interpolation algorithm.  Can be one of "nearest neighbor", "bilinear" or "bicubic".  Defaults to "bicubic".
- width - If greater than 0, determines the width of the written image.  Otherwise, the width is obtained from the SVG document.  Defaults to -1;
- height - If greater than 0, determines the height of the written image.  Otherwise, the height is obtained from the SVG document.  Defaults to -1.
- sizeToFit - If true and the width and height of the output image differ from that of the SVG image, the valid area of the SVG image will be resized to fit the specified size.
- verbose - If true, prints out diagnostic information about processing.  Defaults to false.








