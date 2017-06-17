// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITaskGroup.java

package com.sinitek.base.taskgroup;


// Referenced classes of package com.sinitek.base.taskgroup:
//            TaskGroupException, ITaskInfo, ITaskDealer

public interface ITaskGroup
{

    public abstract void start()
        throws TaskGroupException;

    public abstract void terminate()
        throws TaskGroupException;

    public abstract void markEnd();

    public abstract void addTask(ITaskInfo itaskinfo);

    public abstract int getThreadCount();

    public abstract ITaskDealer getDealer();

    public abstract boolean isFinished();

    public abstract void waitForFinish();
}
