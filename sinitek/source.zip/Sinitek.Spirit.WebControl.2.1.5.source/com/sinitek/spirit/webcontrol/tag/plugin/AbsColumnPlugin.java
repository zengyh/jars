// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsColumnPlugin.java

package com.sinitek.spirit.webcontrol.tag.plugin;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.tag.ui.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbsColumnPlugin extends Component
{

    AbsColumnPlugin(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("dataVar", dataVar, "dataVar");
        addProperty("checkedVar", checkedVar, "checkedVar");
    }

    public void setDataVar(String dataVar)
    {
        this.dataVar = dataVar;
    }

    public void setCheckedVar(String checkedVar)
    {
        this.checkedVar = checkedVar;
    }

    private String dataVar;
    private String checkedVar;
}
