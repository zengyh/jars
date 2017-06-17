// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskGroupContext.java

package com.sinitek.base.taskgroup;


// Referenced classes of package com.sinitek.base.taskgroup:
//            ITaskDealer, ITaskGroup

public class TaskGroupContext
{

    private TaskGroupContext()
    {
    }

    public static synchronized TaskGroupContext getInstance()
    {
        if(instance == null)
            instance = new TaskGroupContext();
        return instance;
    }

    public ITaskGroup createTaskGroup(int threadCount, int taskListCount, ITaskDealer dealer)
    {
        return null;
    }

    public ITaskGroup createTaskGroup(int threadCount, ITaskDealer dealer)
    {
        return createTaskGroup(threadCount, 500, dealer);
    }

    private static TaskGroupContext instance;
    public static final int DEFAULT_TASKLIST_COUNT = 500;
}
