// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContextInvocationInfo.java

package com.sinitek.base.metadb.support;

import java.util.Stack;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IMethodInvocationInfo

public class ContextInvocationInfo
{
    private static class NotifyStack extends Stack
    {

        public synchronized Object pop()
        {
            Object ret = super.pop();
            targetStack.pop();
            return ret;
        }

        public Object push(Object item)
        {
            Object ret = super.push(item);
            targetStack.push(Boolean.FALSE);
            return ret;
        }

        private Stack targetStack;

        private NotifyStack(Stack targetStack)
        {
            this.targetStack = targetStack;
        }

    }


    public ContextInvocationInfo()
    {
        openMarkStack = new Stack();
        methodStack = new NotifyStack(openMarkStack);
        contextStack = new Stack();
        currentInvocationInfoStack = new Stack();
    }

    public Stack getMethodStack()
    {
        return methodStack;
    }

    public Stack getContextStack()
    {
        return contextStack;
    }

    public IMethodInvocationInfo getCurrentInvocationInfo()
    {
        return (IMethodInvocationInfo)currentInvocationInfoStack.peek();
    }

    public Stack getCurrentInvocationInfoStack()
    {
        return currentInvocationInfoStack;
    }

    public void setCurrentInvocationInfoStack(Stack currentInvocationInfoStack)
    {
        this.currentInvocationInfoStack = currentInvocationInfoStack;
    }

    public Stack getOpenMarkStack()
    {
        return openMarkStack;
    }

    public void setOpenMarkStack(Stack openMarkStack)
    {
        this.openMarkStack = openMarkStack;
    }

    public boolean isCurrentMethodOpenContext()
    {
        return openMarkStack.size() != 0 && ((Boolean)openMarkStack.peek()).booleanValue();
    }

    public void markCurrentMethodOpenContext()
    {
        openMarkStack.pop();
        openMarkStack.push(Boolean.TRUE);
    }

    private Stack methodStack;
    private Stack contextStack;
    private Stack openMarkStack;
    private Stack currentInvocationInfoStack;
}
