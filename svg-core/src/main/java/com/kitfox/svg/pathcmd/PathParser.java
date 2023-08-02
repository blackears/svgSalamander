/*
 * SVG Salamander
 * Copyright (c) 2004, Mark McKay
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *   - Redistributions of source code must retain the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 * projects can be found at http://www.kitfox.com
 *
 */
package com.kitfox.svg.pathcmd;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper for parsing {@link PathCommand}s.
 *
 * @author Jannis Weis
 */
public class PathParser
{
    /*
     * This was part of NumberCharState. Unfortunately, it is not inlined as of Java 20. Maybe when Java has value
     * classes this will change.
     */
    int iteration = 0;
    boolean dotAllowed = true;
    boolean signAllowed = true;
    boolean exponentAllowed = true;
    /* End NumberCharState class information */

    private final String input;
    private final int inputLength;
    private int index;
    private char currentCommand;

    public PathParser(String input) {
        this.input = input.trim();
        this.inputLength = this.input.length();
    }

    private boolean isCommandChar(char c)
    {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
    }

    private boolean isWhiteSpaceOrSeparator(char c)
    {
        return c <= ' ' || c == ',';
    }

    private char peek()
    {
        return input.charAt(index);
    }

    private void consume()
    {
        index++;
    }

    private boolean hasNext()
    {
        return index < inputLength;
    }

    // This only checks for the rough structure of a number as we need to know
    // when to separate the next token.
    // Explicit parsing is done by Float#parseFloat.
    private boolean isValidNumberChar(char c, PathParser state)
    {
        boolean valid = '0' <= c && c <= '9';
        if (valid && state.iteration == 1 && input.charAt(index - 1) == '0')
        {
            // Break up combined zeros into multiple numbers.
            return false;
        }
        state.signAllowed = state.signAllowed && !valid;
        if (state.dotAllowed && !valid)
        {
            valid = c == '.';
            state.dotAllowed = !valid;
        }
        if (state.signAllowed && !valid)
        {
            valid = c == '+' || c == '-';
            state.signAllowed = valid;
        }
        if (state.exponentAllowed && !valid)
        {
            // Possible exponent notation. Needs at least one preceding number
            valid = c == 'e' || c == 'E';
            state.exponentAllowed = !valid;
            state.signAllowed = valid;
        }
        state.iteration++;
        return valid;
    }

    private void consumeWhiteSpaceOrSeparator() {
        while (hasNext() && isWhiteSpaceOrSeparator(peek())) {
            consume();
        }
    }

    private float nextFloat()
    {
        int start = index;
        PathParser state = this.resetNumberCharState();
        while (hasNext() && isValidNumberChar(peek(), state)) {
            consume();
        }
        int end = index;
        consumeWhiteSpaceOrSeparator();
        String token = input.substring(start, end);
        try
        {
            return Float.parseFloat(token);
        } catch (NumberFormatException e)
        {
            String msg = "Unexpected element while parsing cmd '" + currentCommand
                    + "' encountered token '" + token + "' rest=" + input.substring(start, Math.min(input.length(), start + 10))
                    + " (index=" + index + " in input=" + input + ")";
            throw new IllegalStateException(msg, e);
        }
    }

    public PathCommand[] parsePathCommand()
    {
        List<PathCommand> commands = new ArrayList<>();
        
        currentCommand = 'Z';
        while (hasNext())
        {
            char peekChar = peek();
            if (isCommandChar(peekChar))
            {
                consume();
                currentCommand = peekChar;
            }
            consumeWhiteSpaceOrSeparator();

            PathCommand cmd;
            switch (currentCommand)
            {
                case 'M':
                    cmd = new MoveTo(false, nextFloat(), nextFloat());
                    currentCommand = 'L';
                    break;
                case 'm':
                    cmd = new MoveTo(true, nextFloat(), nextFloat());
                    currentCommand = 'l';
                    break;
                case 'L':
                    cmd = new LineTo(false, nextFloat(), nextFloat());
                    break;
                case 'l':
                    cmd = new LineTo(true, nextFloat(), nextFloat());
                    break;
                case 'H':
                    cmd = new Horizontal(false, nextFloat());
                    break;
                case 'h':
                    cmd = new Horizontal(true, nextFloat());
                    break;
                case 'V':
                    cmd = new Vertical(false, nextFloat());
                    break;
                case 'v':
                    cmd = new Vertical(true, nextFloat());
                    break;
                case 'A':
                    cmd = new Arc(false, nextFloat(), nextFloat(),
                                  nextFloat(),
                                  nextFloat() == 1f, nextFloat() == 1f,
                                  nextFloat(), nextFloat());
                    break;
                case 'a':
                    cmd = new Arc(true, nextFloat(), nextFloat(),
                                  nextFloat(),
                                  nextFloat() == 1f, nextFloat() == 1f,
                                  nextFloat(), nextFloat());
                    break;
                case 'Q':
                    cmd = new Quadratic(false, nextFloat(), nextFloat(),
                                        nextFloat(), nextFloat());
                    break;
                case 'q':
                    cmd = new Quadratic(true, nextFloat(), nextFloat(),
                                        nextFloat(), nextFloat());
                    break;
                case 'T':
                    cmd = new QuadraticSmooth(false, nextFloat(), nextFloat());
                    break;
                case 't':
                    cmd = new QuadraticSmooth(true, nextFloat(), nextFloat());
                    break;
                case 'C':
                    cmd = new Cubic(false, nextFloat(), nextFloat(),
                                    nextFloat(), nextFloat(),
                                    nextFloat(), nextFloat());
                    break;
                case 'c':
                    cmd = new Cubic(true, nextFloat(), nextFloat(),
                                    nextFloat(), nextFloat(),
                                    nextFloat(), nextFloat());
                    break;
                case 'S':
                    cmd = new CubicSmooth(false, nextFloat(), nextFloat(),
                                          nextFloat(), nextFloat());
                    break;
                case 's':
                    cmd = new CubicSmooth(true, nextFloat(), nextFloat(),
                                          nextFloat(), nextFloat());
                    break;
                case 'Z':
                case 'z':
                    cmd = new Terminal();
                    break;
                default:
                    throw new RuntimeException("Invalid path element "
                            + currentCommand + "(at index=" + index + " in input=" + input + ")");
            }
            commands.add(cmd);
        }
        return commands.toArray(new PathCommand[0]);
    }

    /**
     * Reset the NumberCharState
     * @return {this}, for ease of changing back to a NumberCharState class later.
     */
    private PathParser resetNumberCharState() {
        this.iteration = 0;
        this.dotAllowed = true;
        this.signAllowed = true;
        this.exponentAllowed = true;
        return this;
    }
}
