// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToolBarButtonTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ButtonTag, ToolBarButton

public class ToolBarButtonTag extends ButtonTag
{

    public ToolBarButtonTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ToolBarButton(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
    }
}