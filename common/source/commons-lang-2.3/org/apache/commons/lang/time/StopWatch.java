// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StopWatch.java

package org.apache.commons.lang.time;


// Referenced classes of package org.apache.commons.lang.time:
//            DurationFormatUtils

public class StopWatch
{

    public StopWatch()
    {
        runningState = 0;
        splitState = 10;
        startTime = -1L;
        stopTime = -1L;
    }

    public void start()
    {
        if(runningState == 2)
            throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
        if(runningState != 0)
        {
            throw new IllegalStateException("Stopwatch already started. ");
        } else
        {
            stopTime = -1L;
            startTime = System.currentTimeMillis();
            runningState = 1;
            return;
        }
    }

    public void stop()
    {
        if(runningState != 1 && runningState != 3)
            throw new IllegalStateException("Stopwatch is not running. ");
        if(runningState == 1)
            stopTime = System.currentTimeMillis();
        runningState = 2;
    }

    public void reset()
    {
        runningState = 0;
        splitState = 10;
        startTime = -1L;
        stopTime = -1L;
    }

    public void split()
    {
        if(runningState != 1)
        {
            throw new IllegalStateException("Stopwatch is not running. ");
        } else
        {
            stopTime = System.currentTimeMillis();
            splitState = 11;
            return;
        }
    }

    public void unsplit()
    {
        if(splitState != 11)
        {
            throw new IllegalStateException("Stopwatch has not been split. ");
        } else
        {
            stopTime = -1L;
            splitState = 10;
            return;
        }
    }

    public void suspend()
    {
        if(runningState != 1)
        {
            throw new IllegalStateException("Stopwatch must be running to suspend. ");
        } else
        {
            stopTime = System.currentTimeMillis();
            runningState = 3;
            return;
        }
    }

    public void resume()
    {
        if(runningState != 3)
        {
            throw new IllegalStateException("Stopwatch must be suspended to resume. ");
        } else
        {
            startTime += System.currentTimeMillis() - stopTime;
            stopTime = -1L;
            runningState = 1;
            return;
        }
    }

    public long getTime()
    {
        if(runningState == 2 || runningState == 3)
            return stopTime - startTime;
        if(runningState == 0)
            return 0L;
        if(runningState == 1)
            return System.currentTimeMillis() - startTime;
        else
            throw new RuntimeException("Illegal running state has occured. ");
    }

    public long getSplitTime()
    {
        if(splitState != 11)
            throw new IllegalStateException("Stopwatch must be split to get the split time. ");
        else
            return stopTime - startTime;
    }

    public String toString()
    {
        return DurationFormatUtils.formatDurationHMS(getTime());
    }

    public String toSplitString()
    {
        return DurationFormatUtils.formatDurationHMS(getSplitTime());
    }

    private static final int STATE_UNSTARTED = 0;
    private static final int STATE_RUNNING = 1;
    private static final int STATE_STOPPED = 2;
    private static final int STATE_SUSPENDED = 3;
    private static final int STATE_UNSPLIT = 10;
    private static final int STATE_SPLIT = 11;
    private int runningState;
    private int splitState;
    private long startTime;
    private long stopTime;
}
