varying vec2 inset;

void main(void)
{
    gl_FragColor = vec4(inset.x, inset.y, 0, 1.0);
/*
    gl_FragColor = vec4(1, 0, 0, 1.0);
*/
}

