/*
Perform simple normal map shading
*/

uniform sampler2DRect sourceColor;
uniform sampler2DRect sourceNormal;

uniform bool highlight;
uniform vec4 lightPosition;


void main(void)
{

    vec4 col = texture2DRect(sourceColor, gl_TexCoord[0].st);
    vec3 norm = texture2DRect(sourceNormal, gl_TexCoord[0].st).xyz;

    norm *= 2.0;
    norm -= 1.0;

    vec3 lightDir;
    if (lightPosition.w == 0.0)
    {
        lightDir = lightPosition.xyz;
    }
    else
    {
         lightDir = lightPosition.xyz - vec3(gl_TexCoord[0].st, 0);
    }

    lightDir = normalize(lightDir);
    float cosAng = dot(lightDir, norm);

    if (highlight)
    {
        col = clamp(col + vec4(.3, .3, .3, 0), 0.0, 1.0);
/*
        col = clamp(col + vec4(.2, .2, .2, 0), 0.0, 1.0);
        col = vec4(0.0, 1.0, 0.0, 0.0);
        col = vec4(.2, .8, .2, 1);
*/
    }

    gl_FragColor = col * cosAng;
}


