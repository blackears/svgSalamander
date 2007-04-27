/*
Variable names must be all lower case
*/
varying vec2 inset;

uniform float delta;
uniform float echo;
uniform sampler2DRect sourcemap;

void main(void)
{
    vec4 sourceCol = texture2DRect(sourcemap, gl_TexCoord[0].st).xyzw;
    gl_FragColor = sourceCol;

/*
    vec3 sourceCol = texture2DRect(sourcemap, vec2(0.5, 0.5)).xyz;
    gl_FragColor = vec4(sourceCol, 1.0);

    vec3 sourceCol = texture2D(sourcemap, gl_TexCoord[0].st).xyz;
    gl_FragColor = vec4(sourceCol.yzx, 1.0);

    vec3 sourceCol = texture2D(sourcemap, gl_TexCoord[0].st / vec2(128, 128)).xyz;
    gl_FragColor = vec4(sourceCol.yzx, 1.0);

    vec3 sourceCol = texture2D(sourcemap, gl_TexCoord[0].st).xyz;
    gl_FragColor = vec4(sourceCol, 1.0);

    vec3 sourceCol = texture2D(sourcemap, gl_TexCoord[0].st / vec2(200, 100)).xyz;
    gl_FragColor = vec4(sourceCol.yzx, 1.0);

    //Iterate over unit square
    gl_FragColor = vec4(gl_TexCoord[0].st / vec2(200, 100), 0, 1.0);

    vec3 sourceCol = vec3(texture2D(sourceMap, gl_TexCoord[0].st));
    gl_FragColor = vec4(sourceCol, 1.0);


    vec3 sourceCol = vec3(texture2D(sourceMap, gl_TexCoord[0].st));
    vec3 sourceCol = vec3(texture2D(sourceMap, gl_TexCoord[0].st / vec2(200, 100)));
    vec3 sourceCol = vec3(texture2D(sourceMap, gl_TexCoord[0].st));
    gl_FragColor = vec4(gl_TexCoord[0].st / vec2(200, 100), 0, 1.0);
    gl_FragColor = vec4(gl_TexCoord[0].st / vec2(10, 10), 0, 1.0);
    gl_FragColor = vec4(gl_TexCoord[0].st * vec2(640, 480), 0, 1.0);
    gl_FragColor = vec4(1, 0, 0, 1.0);
    gl_FragColor = vec4(sourceCol, 1.0);
*/
}

