// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrepareForm.java

package com.sinitek.ds.core.service.webtest;

import org.apache.struts.action.ActionForm;

public class PrepareForm extends ActionForm
{

    public PrepareForm()
    {
    }

    public String getCallType()
    {
        return callType;
    }

    public void setCallType(String callType)
    {
        this.callType = callType;
    }

    public String getRemoteUrl()
    {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl)
    {
        this.remoteUrl = remoteUrl;
    }

    public String getJndi()
    {
        return jndi;
    }

    public void setJndi(String jndi)
    {
        this.jndi = jndi;
    }

    public String getServerType()
    {
        return serverType;
    }

    public void setServerType(String serverType)
    {
        this.serverType = serverType;
    }

    public String getConfigFile()
    {
        return configFile;
    }

    public void setConfigFile(String configFile)
    {
        this.configFile = configFile;
    }

    private String callType;
    private String remoteUrl;
    private String jndi;
    private String serverType;
    private String configFile;
}
