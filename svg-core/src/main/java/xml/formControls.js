
/**
 * Layout a form control.  Must be called to initialize and whenever the
 * form's dimensions change.
 */
function pack()
{
}

function moveLabel(name, x, y, width, height)
{
    var clipRect = svgDocument.getElementById(name + "-clip-rect");
    clipRect.setAttribute("x", x);
    clipRect.setAttribute("y", y);
    clipRect.setAttribute("width", width);
    clipRect.setAttribute("height", height);
    
    var rect = svgDocument.getElementById(name + "-rect");
    rect.setAttribute("x", x);
    rect.setAttribute("y", y);
    rect.setAttribute("width", width);
    rect.setAttribute("height", height);
    
    var text = svgDocument.getElementById(name + "-text");
    rect.setAttribute("x", x);
    rect.setAttribute("y", y);
}