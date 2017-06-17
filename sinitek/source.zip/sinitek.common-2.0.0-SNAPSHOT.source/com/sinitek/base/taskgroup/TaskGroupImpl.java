// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskGroupImpl.java

package com.sinitek.base.taskgroup;

import java.util.*;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.taskgroup:
//            TaskGroupException, ITaskInfo, ITaskGroup, ITaskDealer

public class TaskGroupImpl
    implements ITaskGroup
{

    public TaskGroupImpl(ITaskDealer dealer, int threadCount, int taskListCount)
    {
        this.threadCount = threadCount;
        this.dealer = dealer;
        this.taskListCount = taskListCount;
        taskBuffer = new UnboundedFifoBuffer(taskListCount);
        threadList = new ArrayList(threadCount);
        status = 0;
    }

    public synchronized void start()
        throws TaskGroupException
    {
        if(status == 1)
        {
            status = 2;
            Runnable run = new Runnable() {

                public void run()
                {
                    threadRun();
                }

                final TaskGroupImpl this$0;

            
            {
                this$0 = TaskGroupImpl.this;
                super();
            }
            }
;
            for(int i = 0; i < threadCount; i++)
            {
                Thread dealThread = new Thread(run);
                threadList.add(dealThread);
                dealThread.start();
            }

        } else
        {
            if(status == 0)
                throw new TaskGroupException("0000");
            if(status == 2)
                throw new TaskGroupException("0002");
            if(status == 3)
                throw new TaskGroupException("0003");
            if(status == 4)
                throw new TaskGroupException("0004");
        }
    }

    public synchronized void terminate()
        throws TaskGroupException
    {
        if(status == 1 || status == 3)
        {
            status = 3;
            Iterator iter = threadList.iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                Thread dealThread = (Thread)iter.next();
                if(dealThread.isAlive())
                    dealThread.interrupt();
            } while(true);
            notifyAll();
        } else
        {
            if(status == 0)
                throw new TaskGroupException("0000");
            if(status == 2)
                throw new TaskGroupException("0002");
            if(status == 4)
                throw new TaskGroupException("0004");
        }
    }

    public synchronized void markEnd()
    {
        if(status == 1)
        {
            status = 3;
            notifyAll();
        } else
        {
            if(status == 0)
                throw new TaskGroupException("0000");
            if(status == 2)
                throw new TaskGroupException("0002");
            if(status == 3)
                throw new TaskGroupException("0003");
            if(status == 4)
                throw new TaskGroupException("0004");
        }
    }

    public synchronized void addTask(ITaskInfo taskInfo)
    {
        if(taskBuffer.size() >= taskListCount)
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                throw new TaskGroupException("0005");
            }
        taskBuffer.add(taskInfo);
        notifyAll();
    }

    public int getThreadCount()
    {
        return threadCount;
    }

    public ITaskDealer getDealer()
    {
        return dealer;
    }

    public boolean isFinished()
    {
        return status == 2 || status == 4;
    }

    public synchronized void waitForFinish()
    {
    }

    private void threadRun()
    {
        for(boolean runflag = true; runflag;)
        {
            ITaskInfo taskInfo = getTodoTask();
            if(taskInfo == null)
                runflag = false;
            else
                try
                {
                    dealer.deal(taskInfo);
                }
                catch(TaskGroupException e)
                {
                    LOGGER.error("\u4EFB\u52A1\u6267\u884C\u629B\u51FA\u5F02\u5E38", e);
                }
        }

    }

    private synchronized ITaskInfo getTodoTask()
    {
        do
        {
            if(taskBuffer.size() != 0)
                return (ITaskInfo)taskBuffer.remove();
            if(status == 2 || status == 3)
                return null;
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                throw new TaskGroupException("0006", e);
            }
        } while(true);
    }

    private int status;
    private int threadCount;
    private ITaskDealer dealer;
    private int taskListCount;
    private Buffer taskBuffer;
    private List threadList;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/taskgroup/TaskGroupImpl);


}
