SVG Salamander
==============
SVG Salamander is an SVG engine for Java that's designed to be small, fast, and allow programmers to use it with a minimum of fuss. It's in particular targeted for making it easy to integrate SVG into Java games and making it much easier for artists to design 2D game content - from rich interactive menus to charts and graphcs to complex animations.

Features
--------
* Ant task to allow easy conversion from SVG to images from within Ant scripts
* SVGIcon class greatly simplifies loading and drawing images to screen
* A much smaller code foot print than Batik, and only one JAR file to include
* Direct access to the scene graph tree. You can use Java commands to manipulate it directly.
* An index of all named shapes in the SVG graph is easily accessible.
* Picking shapes given (x, y) coordinates is possible, and can be used to implement graphical buttons selected by the mouse
* Clip region sensitivity makes for fast rendering when only updating part of the image. This makes panning the camera quite efficient.
* Easy rendering to any Graphics2D or BufferedImage. Unlike Batik, the SVG Salamander engine does not own the graphics context, so you can pass it whatever graphics context you like.
* Internal and external links are implemented as URIs, which allows the engine to automatically import linked documents - even if they're stored on a remote server.
* SVG can be read from an InputStream, so you can create documents dynamicly from an in-program XSLT transformation.

Current status
--------------
SalamanderSVG is part of the Salamander project hosted on http://www.kitfox.com

Projects using SVG Salamander
-----------------------------
* [Apache Pivot](http://pivot.apache.org/) - An alternate crossplatform GUI for Java.
* [VisiCut](http://visicut.org/) - A tool for laser precision cutting.
* [Xoetrope](http://www.xoetrope.com/) - An alternate crossplatform GUI for Java.
* [Power Line](http://suchanek.name/programs/powerline/index.html) - A slide editor for SVG.
* [Tygron](http://www.tygron.com/) - Serious games illustrating urban planning and climate change.
* [NeoLogica](http://www.neologica.it/eng/Home.php) - Medical imaging.

License
-------
SVG Salamander is avaible both under the [LGPL](https://svgsalamander.java.net/license/license-lgpl.txt) and [BSD](https://svgsalamander.java.net/license/license-bsd.txt) licenses.
