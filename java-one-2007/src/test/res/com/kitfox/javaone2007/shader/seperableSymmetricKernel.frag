/*
Implements a seperable symmetric kernel.  

The passed in kernel arrays are assumed to have odd sidelength and be reflected
about their midpoint.  Eg, if kernelSizeX == 3 and the passed in kernelX array is

[.5 .2 .1],

this is preumed to represent the expanded vector

[.1 .2 .5 .2 .1]

The final convolution matrix is equal to the product of the Y vector and the
X vector.  It will be of size m x n, where m is the length of the X vector
and n is the length of the Y vector.

*/

uniform sampler2DRect source;

const int maxKernelSize = 5;

uniform float kernelX[maxKernelSize];  
uniform float kernelY[maxKernelSize];
/*
uniform int kernelSizeX;
uniform int kernelSizeY;
*/
vec4 buf[maxKernelSize * 2 - 1];


int absInt(in int index)
{
    if (index >= 0)
    {
        return index;
    }
    else
    {
        return -index;
    }
}

void main(void)
{
    int i, j;

    for (i = 0; i < maxKernelSize * 2 - 1; i++)
    {
        buf[i] = vec4(0.0);
        for (j = 0; j < maxKernelSize * 2 - 1; j++)
        {
            vec4 samp = texture2DRect(source, gl_TexCoord[0].st + vec2(i - maxKernelSize + 1, j - maxKernelSize + 1));
            buf[i] += samp * kernelY[absInt(j - maxKernelSize + 1)];
        }
    }

    vec4 res = vec4(0.0);
    for (i = 0; i < maxKernelSize * 2 - 1; i++)
    {
        res += buf[i] * kernelX[absInt(i - maxKernelSize + 1)];
    }

/*
    i = 0;
    while (i < kernelSizeX)
    {
        i++;
    }

    for (i = 0; i < kernelSizeX; i++)
    {
    }
*/

    /*Sum vertical*/
/*
    for (i = 0; i < kernelSizeX * 2 - 1; i++)
    {
        for (j = 0; j < kernelSizeY * 2 - 1; j++)
        {
            vec4 samp = texture2DRect(source, gl_TexCoord[0].st + vec2(i - kernelSizeX + 1, j - kernelSizeY + 1));
            buf[i] += samp * kernelY[absInt(j - kernelSizeY + 1)];
        }
    }
*/

    /*Sum horizontal*/
/*
    vec4 res = vec4(0.0);
    for (i = 0; i < kernelSizeX * 2 - 1; i++)
    {
        res += buf[i] * kernelX[absInt(i - kernelSizeX + 1)];
    }
*/

    gl_FragColor = res;
}


