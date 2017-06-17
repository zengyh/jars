// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITaskDealer.java

package com.sinitek.base.taskgroup;


// Referenced classes of package com.sinitek.base.taskgroup:
//            TaskGroupException, ITaskInfo

public interface ITaskDealer
{

    public abstract void deal(ITaskInfo itaskinfo)
        throws TaskGroupException;
}
