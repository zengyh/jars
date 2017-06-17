// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionUtils.java

package org.apache.commons.lang.exception;

import java.io.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;
import org.apache.commons.lang.*;

// Referenced classes of package org.apache.commons.lang.exception:
//            Nestable

public class ExceptionUtils
{

    public ExceptionUtils()
    {
    }

    public static void addCauseMethodName(String methodName)
    {
        if(StringUtils.isNotEmpty(methodName) && !isCauseMethodName(methodName))
        {
            List list = getCauseMethodNameList();
            if(list.add(methodName))
                CAUSE_METHOD_NAMES = toArray(list);
        }
    }

    public static void removeCauseMethodName(String methodName)
    {
        if(StringUtils.isNotEmpty(methodName))
        {
            List list = getCauseMethodNameList();
            if(list.remove(methodName))
                CAUSE_METHOD_NAMES = toArray(list);
        }
    }

    public static boolean setCause(Throwable target, Throwable cause)
    {
        if(target == null)
            throw new NullArgumentException("target");
        Object causeArgs[] = {
            cause
        };
        boolean modifiedTarget = false;
        if(THROWABLE_INITCAUSE_METHOD != null)
            try
            {
                THROWABLE_INITCAUSE_METHOD.invoke(target, causeArgs);
                modifiedTarget = true;
            }
            catch(IllegalAccessException ignored) { }
            catch(InvocationTargetException ignored) { }
        try
        {
            Method setCauseMethod = target.getClass().getMethod("setCause", new Class[] {
                java.lang.Throwable.class
            });
            setCauseMethod.invoke(target, causeArgs);
            modifiedTarget = true;
        }
        catch(NoSuchMethodException ignored) { }
        catch(IllegalAccessException ignored) { }
        catch(InvocationTargetException ignored) { }
        return modifiedTarget;
    }

    private static String[] toArray(List list)
    {
        return (String[])list.toArray(new String[list.size()]);
    }

    private static ArrayList getCauseMethodNameList()
    {
        return new ArrayList(Arrays.asList(CAUSE_METHOD_NAMES));
    }

    public static boolean isCauseMethodName(String methodName)
    {
        return ArrayUtils.indexOf(CAUSE_METHOD_NAMES, methodName) >= 0;
    }

    public static Throwable getCause(Throwable throwable)
    {
        return getCause(throwable, CAUSE_METHOD_NAMES);
    }

    public static Throwable getCause(Throwable throwable, String methodNames[])
    {
        if(throwable == null)
            return null;
        Throwable cause = getCauseUsingWellKnownTypes(throwable);
        if(cause == null)
        {
            if(methodNames == null)
                methodNames = CAUSE_METHOD_NAMES;
            for(int i = 0; i < methodNames.length; i++)
            {
                String methodName = methodNames[i];
                if(methodName == null)
                    continue;
                cause = getCauseUsingMethodName(throwable, methodName);
                if(cause != null)
                    break;
            }

            if(cause == null)
                cause = getCauseUsingFieldName(throwable, "detail");
        }
        return cause;
    }

    public static Throwable getRootCause(Throwable throwable)
    {
        List list = getThrowableList(throwable);
        return list.size() >= 2 ? (Throwable)list.get(list.size() - 1) : null;
    }

    private static Throwable getCauseUsingWellKnownTypes(Throwable throwable)
    {
        if(throwable instanceof Nestable)
            return ((Nestable)throwable).getCause();
        if(throwable instanceof SQLException)
            return ((SQLException)throwable).getNextException();
        if(throwable instanceof InvocationTargetException)
            return ((InvocationTargetException)throwable).getTargetException();
        else
            return null;
    }

    private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName)
    {
        Method method = null;
        try
        {
            method = throwable.getClass().getMethod(methodName, null);
        }
        catch(NoSuchMethodException ignored) { }
        catch(SecurityException ignored) { }
        if(method != null && (java.lang.Throwable.class).isAssignableFrom(method.getReturnType()))
            try
            {
                return (Throwable)method.invoke(throwable, ArrayUtils.EMPTY_OBJECT_ARRAY);
            }
            catch(IllegalAccessException ignored) { }
            catch(IllegalArgumentException ignored) { }
            catch(InvocationTargetException ignored) { }
        return null;
    }

    private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName)
    {
        Field field = null;
        try
        {
            field = throwable.getClass().getField(fieldName);
        }
        catch(NoSuchFieldException ignored) { }
        catch(SecurityException ignored) { }
        if(field != null && (java.lang.Throwable.class).isAssignableFrom(field.getType()))
            try
            {
                return (Throwable)field.get(throwable);
            }
            catch(IllegalAccessException ignored) { }
            catch(IllegalArgumentException ignored) { }
        return null;
    }

    public static boolean isThrowableNested()
    {
        return THROWABLE_CAUSE_METHOD != null;
    }

    public static boolean isNestedThrowable(Throwable throwable)
    {
        if(throwable == null)
            return false;
        if(throwable instanceof Nestable)
            return true;
        if(throwable instanceof SQLException)
            return true;
        if(throwable instanceof InvocationTargetException)
            return true;
        if(isThrowableNested())
            return true;
        Class cls = throwable.getClass();
        int i = 0;
        for(int isize = CAUSE_METHOD_NAMES.length; i < isize; i++)
            try
            {
                Method method = cls.getMethod(CAUSE_METHOD_NAMES[i], null);
                if(method != null && (java.lang.Throwable.class).isAssignableFrom(method.getReturnType()))
                    return true;
            }
            catch(NoSuchMethodException ignored) { }
            catch(SecurityException ignored) { }

        try
        {
            Field field = cls.getField("detail");
            if(field != null)
                return true;
        }
        catch(NoSuchFieldException ignored) { }
        catch(SecurityException ignored) { }
        return false;
    }

    public static int getThrowableCount(Throwable throwable)
    {
        return getThrowableList(throwable).size();
    }

    public static Throwable[] getThrowables(Throwable throwable)
    {
        List list = getThrowableList(throwable);
        return (Throwable[])list.toArray(new Throwable[list.size()]);
    }

    public static List getThrowableList(Throwable throwable)
    {
        List list;
        for(list = new ArrayList(); throwable != null && !list.contains(throwable); throwable = getCause(throwable))
            list.add(throwable);

        return list;
    }

    public static int indexOfThrowable(Throwable throwable, Class clazz)
    {
        return indexOf(throwable, clazz, 0, false);
    }

    public static int indexOfThrowable(Throwable throwable, Class clazz, int fromIndex)
    {
        return indexOf(throwable, clazz, fromIndex, false);
    }

    public static int indexOfType(Throwable throwable, Class type)
    {
        return indexOf(throwable, type, 0, true);
    }

    public static int indexOfType(Throwable throwable, Class type, int fromIndex)
    {
        return indexOf(throwable, type, fromIndex, true);
    }

    private static int indexOf(Throwable throwable, Class type, int fromIndex, boolean subclass)
    {
        if(throwable == null || type == null)
            return -1;
        if(fromIndex < 0)
            fromIndex = 0;
        Throwable throwables[] = getThrowables(throwable);
        if(fromIndex >= throwables.length)
            return -1;
        if(subclass)
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

    public static void printRootCauseStackTrace(Throwable throwable)
    {
        printRootCauseStackTrace(throwable, System.err);
    }

    public static void printRootCauseStackTrace(Throwable throwable, PrintStream stream)
    {
        if(throwable == null)
            return;
        if(stream == null)
            throw new IllegalArgumentException("The PrintStream must not be null");
        String trace[] = getRootCauseStackTrace(throwable);
        for(int i = 0; i < trace.length; i++)
            stream.println(trace[i]);

        stream.flush();
    }

    public static void printRootCauseStackTrace(Throwable throwable, PrintWriter writer)
    {
        if(throwable == null)
            return;
        if(writer == null)
            throw new IllegalArgumentException("The PrintWriter must not be null");
        String trace[] = getRootCauseStackTrace(throwable);
        for(int i = 0; i < trace.length; i++)
            writer.println(trace[i]);

        writer.flush();
    }

    public static String[] getRootCauseStackTrace(Throwable throwable)
    {
        if(throwable == null)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        Throwable throwables[] = getThrowables(throwable);
        int count = throwables.length;
        ArrayList frames = new ArrayList();
        List nextTrace = getStackFrameList(throwables[count - 1]);
        for(int i = count; --i >= 0;)
        {
            List trace = nextTrace;
            if(i != 0)
            {
                nextTrace = getStackFrameList(throwables[i - 1]);
                removeCommonFrames(trace, nextTrace);
            }
            if(i == count - 1)
                frames.add(throwables[i].toString());
            else
                frames.add(" [wrapped] " + throwables[i].toString());
            for(int j = 0; j < trace.size(); j++)
                frames.add(trace.get(j));

        }

        return (String[])frames.toArray(new String[0]);
    }

    public static void removeCommonFrames(List causeFrames, List wrapperFrames)
    {
        if(causeFrames == null || wrapperFrames == null)
            throw new IllegalArgumentException("The List must not be null");
        int causeFrameIndex = causeFrames.size() - 1;
        for(int wrapperFrameIndex = wrapperFrames.size() - 1; causeFrameIndex >= 0 && wrapperFrameIndex >= 0; wrapperFrameIndex--)
        {
            String causeFrame = (String)causeFrames.get(causeFrameIndex);
            String wrapperFrame = (String)wrapperFrames.get(wrapperFrameIndex);
            if(causeFrame.equals(wrapperFrame))
                causeFrames.remove(causeFrameIndex);
            causeFrameIndex--;
        }

    }

    public static String getFullStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        Throwable ts[] = getThrowables(throwable);
        for(int i = 0; i < ts.length; i++)
        {
            ts[i].printStackTrace(pw);
            if(isNestedThrowable(ts[i]))
                break;
        }

        return sw.getBuffer().toString();
    }

    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String[] getStackFrames(Throwable throwable)
    {
        if(throwable == null)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        else
            return getStackFrames(getStackTrace(throwable));
    }

    static String[] getStackFrames(String stackTrace)
    {
        String linebreak = SystemUtils.LINE_SEPARATOR;
        StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
        List list = new ArrayList();
        for(; frames.hasMoreTokens(); list.add(frames.nextToken()));
        return toArray(list);
    }

    static List getStackFrameList(Throwable t)
    {
        String stackTrace = getStackTrace(t);
        String linebreak = SystemUtils.LINE_SEPARATOR;
        StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
        List list = new ArrayList();
        boolean traceStarted = false;
        while(frames.hasMoreTokens()) 
        {
            String token = frames.nextToken();
            int at = token.indexOf("at");
            if(at != -1 && token.substring(0, at).trim().length() == 0)
            {
                traceStarted = true;
                list.add(token);
                continue;
            }
            if(traceStarted)
                break;
        }
        return list;
    }

    public static String getMessage(Throwable th)
    {
        if(th == null)
        {
            return "";
        } else
        {
            String clsName = ClassUtils.getShortClassName(th, null);
            String msg = th.getMessage();
            return clsName + ": " + StringUtils.defaultString(msg);
        }
    }

    public static String getRootCauseMessage(Throwable th)
    {
        Throwable root = getRootCause(th);
        root = root != null ? root : th;
        return getMessage(root);
    }

    static final String WRAPPED_MARKER = " [wrapped] ";
    private static String CAUSE_METHOD_NAMES[] = {
        "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", 
        "getLinkedCause", "getThrowable"
    };
    private static final Method THROWABLE_CAUSE_METHOD;
    private static final Method THROWABLE_INITCAUSE_METHOD;

    static 
    {
        Method causeMethod;
        try
        {
            causeMethod = (java.lang.Throwable.class).getMethod("getCause", null);
        }
        catch(Exception e)
        {
            causeMethod = null;
        }
        THROWABLE_CAUSE_METHOD = causeMethod;
        try
        {
            causeMethod = (java.lang.Throwable.class).getMethod("initCause", new Class[] {
                java.lang.Throwable.class
            });
        }
        catch(Exception e)
        {
            causeMethod = null;
        }
        THROWABLE_INITCAUSE_METHOD = causeMethod;
    }
}
