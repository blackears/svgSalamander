
varying vec2 inset;

uniform vec2 size;
uniform float able;
uniform float baker;
uniform float charlie;

void main(void)
{
    inset = gl_Vertex.xy;
/*
    inset = vec2(gl_Vertex.x, gl_Vertex.y);
    inset = (gl_Vertex.x / size.x, gl_Vertex.y / size.y);
    inset = gl_Vertex.xy / size;
    inset = gl_Vertex.xy / (width, height);
*/
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position = ftransform();
}

