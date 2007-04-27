/*
uniform vec2 origin;
uniform vec2 size;
*/

varying vec2 coordPos;

void main(void)
{
    coordPos = gl_Vertex.xy;
    gl_Position = fTransform();
}