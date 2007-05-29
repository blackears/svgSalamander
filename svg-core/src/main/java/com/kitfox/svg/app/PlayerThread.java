/*
 * PlayerThread.java
 *
 *
 *  The Salamander Project - 2D and 3D graphics libraries in Java
 *  Copyright (C) 2004 Mark McKay
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *  Mark McKay can be contacted at mark@kitfox.com.  Salamander and other
 *  projects can be found at http://www.kitfox.com
 *
 * Created on September 28, 2004, 10:07 PM
 */


package com.kitfox.svg.app;

import java.util.*;

/**
 *
 * @author  kitfox
 */
public class PlayerThread implements Runnable
{
    HashSet listeners = new HashSet();
    
    double curTime = 0;
    double timeStep = .2;
    
    public static final int PS_STOP = 0;
    public static final int PS_PLAY_FWD = 1;
    public static final int PS_PLAY_BACK = 2;
    
    int playState = PS_STOP;
    
    Thread thread;
    
    /** Creates a new instance of PlayerThread */
    public PlayerThread()
    {
        thread = new Thread(this);
        thread.start();
    }
    
    public void run()
    {
        while (thread != null)
        {
            synchronized (this)
            {
                switch (playState)
                {
                    case PS_PLAY_FWD:
                        curTime += timeStep;
                        break;
                    case PS_PLAY_BACK:
                        curTime -= timeStep;
                        if (curTime < 0) curTime = 0;
                        break;
                    default:
                    case PS_STOP:
                        break;
                }
                
                fireTimeUpdateEvent();
            }
            
            try
            {
                Thread.sleep((long)(timeStep * 1000));
            }
            catch (Exception e) 
            { 
                throw new RuntimeException(e); 
            }
        }
    }
    
    public void exit() { thread = null; }
    public synchronized void addListener(PlayerThreadListener listener) 
    {
        listeners.add(listener); 
    }
    
    public synchronized double getCurTime() { return curTime; }
    
    public synchronized void setCurTime(double time)
    {
        curTime = time;
    }
    
    public synchronized double getTimeStep() { return timeStep; }
    
    public synchronized void setTimeStep(double time)
    {
        timeStep = time;
        if (timeStep < .01) timeStep = .01;
    }
    
    public synchronized int getPlayState() { return playState; }
    
    public synchronized void setPlayState(int playState)
    {
        this.playState = playState;
    }
    
    private void fireTimeUpdateEvent()
    {
        for (Iterator it = listeners.iterator(); it.hasNext();)
        {
            PlayerThreadListener listener = (PlayerThreadListener)it.next();
            listener.updateTime(curTime, timeStep, playState);
        }
    }
}
