// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NestableDelegate.java

package org.apache.commons.lang.exception;

import java.io.*;
import java.util.*;

// Referenced classes of package org.apache.commons.lang.exception:
//            Nestable, ExceptionUtils

public class NestableDelegate
    implements Serializable
{

    public NestableDelegate(Nestable nestable)
    {
        this.nestable = null;
        if(nestable instanceof Throwable)
            this.nestable = (Throwable)nestable;
        else
            throw new IllegalArgumentException("The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable");
    }

    public String getMessage(int index)
    {
        Throwable t = getThrowable(index);
        if((org.apache.commons.lang.exception.Nestable.class).isInstance(t))
            return ((Nestable)t).getMessage(0);
        else
            return t.getMessage();
    }

    public String getMessage(String baseMsg)
    {
        Throwable nestedCause = ExceptionUtils.getCause(nestable);
        String causeMsg = nestedCause != null ? nestedCause.getMessage() : null;
        if(nestedCause == null || causeMsg == null)
            return baseMsg;
        if(baseMsg == null)
            return causeMsg;
        else
            return baseMsg + ": " + causeMsg;
    }

    public String[] getMessages()
    {
        Throwable throwables[] = getThrowables();
        String msgs[] = new String[throwables.length];
        for(int i = 0; i < throwables.length; i++)
            msgs[i] = (org.apache.commons.lang.exception.Nestable.class).isInstance(throwables[i]) ? ((Nestable)throwables[i]).getMessage(0) : throwables[i].getMessage();

        return msgs;
    }

    public Throwable getThrowable(int index)
    {
        if(index == 0)
        {
            return nestable;
        } else
        {
            Throwable throwables[] = getThrowables();
            return throwables[index];
        }
    }

    public int getThrowableCount()
    {
        return ExceptionUtils.getThrowableCount(nestable);
    }

    public Throwable[] getThrowables()
    {
        return ExceptionUtils.getThrowables(nestable);
    }

    public int indexOfThrowable(Class type, int fromIndex)
    {
        if(type == null)
            return -1;
        if(fromIndex < 0)
            throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex);
        Throwable throwables[] = ExceptionUtils.getThrowables(nestable);
        if(fromIndex >= throwables.length)
            throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex + " >= " + throwables.length);
        if(matchSubclasses)
        {
            for(int i = fromIndex; i < throwables.length; i++)
                if(type.isAssignableFrom(throwables[i].getClass()))
                    return i;

        } else
        {
            for(int i = fromIndex; i < throwables.length; i++)
                if(type.equals(throwables[i].getClass()))
                    return i;

        }
        return -1;
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream out)
    {
        synchronized(out)
        {
            PrintWriter pw = new PrintWriter(out, false);
            printStackTrace(pw);
            pw.flush();
        }
    }

    public void printStackTrace(PrintWriter out)
    {
        Throwable throwable = nestable;
        if(ExceptionUtils.isThrowableNested())
        {
            if(throwable instanceof Nestable)
                ((Nestable)throwable).printPartialStackTrace(out);
            else
                throwable.printStackTrace(out);
            return;
        }
        List stacks = new ArrayList();
        for(; throwable != null; throwable = ExceptionUtils.getCause(throwable))
        {
            String st[] = getStackFrames(throwable);
            stacks.add(st);
        }

        String separatorLine = "Caused by: ";
        if(!topDown)
        {
            separatorLine = "Rethrown as: ";
            Collections.reverse(stacks);
        }
        if(trimStackFrames)
            trimStackFrames(stacks);
        synchronized(out)
        {
            for(Iterator iter = stacks.iterator(); iter.hasNext();)
            {
                String st[] = (String[])iter.next();
                int i = 0;
                for(int len = st.length; i < len; i++)
                    out.println(st[i]);

                if(iter.hasNext())
                    out.print(separatorLine);
            }

        }
    }

    protected String[] getStackFrames(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        if(t instanceof Nestable)
            ((Nestable)t).printPartialStackTrace(pw);
        else
            t.printStackTrace(pw);
        return ExceptionUtils.getStackFrames(sw.getBuffer().toString());
    }

    protected void trimStackFrames(List stacks)
    {
        int size = stacks.size();
        for(int i = size - 1; i > 0; i--)
        {
            String curr[] = (String[])stacks.get(i);
            String next[] = (String[])stacks.get(i - 1);
            List currList = new ArrayList(Arrays.asList(curr));
            List nextList = new ArrayList(Arrays.asList(next));
            ExceptionUtils.removeCommonFrames(currList, nextList);
            int trimmed = curr.length - currList.size();
            if(trimmed > 0)
            {
                currList.add("\t... " + trimmed + " more");
                stacks.set(i, ((Object) (currList.toArray(new String[currList.size()]))));
            }
        }

    }

    private static final long serialVersionUID = 1L;
    private static final transient String MUST_BE_THROWABLE = "The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable";
    private Throwable nestable;
    public static boolean topDown = true;
    public static boolean trimStackFrames = true;
    public static boolean matchSubclasses = true;

}
