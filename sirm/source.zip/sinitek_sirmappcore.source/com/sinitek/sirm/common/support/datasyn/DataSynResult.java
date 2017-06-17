// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynResult.java

package com.sinitek.sirm.common.support.datasyn;

import java.util.ArrayList;
import java.util.List;

public class DataSynResult
{

    public DataSynResult()
    {
        success = false;
        messages = new ArrayList();
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public List getMessages()
    {
        return messages;
    }

    public void setMessages(List messages)
    {
        this.messages = messages;
    }

    public void addMessage(String message)
    {
        messages.add(message);
    }

    private boolean success;
    private List messages;
}
