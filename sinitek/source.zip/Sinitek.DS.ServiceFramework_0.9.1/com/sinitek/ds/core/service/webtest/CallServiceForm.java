// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallServiceForm.java

package com.sinitek.ds.core.service.webtest;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CallServiceForm extends ActionForm
{

    public CallServiceForm()
    {
        callParams = new HashMap();
        nullMarkParam = new HashMap();
    }

    public Map getCallParams()
    {
        return callParams;
    }

    public void setCallParams(Map callParams)
    {
        this.callParams = callParams;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public boolean isSaveParam()
    {
        return saveParam;
    }

    public void setSaveParam(boolean saveParam)
    {
        this.saveParam = saveParam;
    }

    public boolean isUpdateCurrent()
    {
        return updateCurrent;
    }

    public void setUpdateCurrent(boolean updateCurrent)
    {
        this.updateCurrent = updateCurrent;
    }

    public boolean isNewInput()
    {
        return newInput;
    }

    public void setNewInput(boolean newInput)
    {
        this.newInput = newInput;
    }

    public String getCurrentFileName()
    {
        return currentFileName;
    }

    public void setCurrentFileName(String currentFileName)
    {
        this.currentFileName = currentFileName;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public Map getNullMarkParam()
    {
        return nullMarkParam;
    }

    public void setNullMarkParam(Map nullMarkParam)
    {
        this.nullMarkParam = nullMarkParam;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest)
    {
        super.reset(actionMapping, httpServletRequest);
        saveParam = false;
        updateCurrent = false;
        newInput = false;
        nullMarkParam.clear();
    }

    private String serviceName;
    private Map callParams;
    private boolean saveParam;
    private boolean updateCurrent;
    private boolean newInput;
    private String currentFileName;
    private Map nullMarkParam;
    private String info;
}
