// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WindowTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            BoxTag, Window

public class WindowTag extends BoxTag
{

    public WindowTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Window(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Window cmp = (Window)component;
        cmp.setDisabled(disabled);
        cmp.setAutoOpen(autoOpen);
        cmp.setDraggable(draggable);
        cmp.setMaxHeight(maxHeight);
        cmp.setMaxHeight(maxHeight);
        cmp.setMinHeight(minHeight);
        cmp.setMinWidth(minWidth);
        cmp.setModal(modal);
        cmp.setPosition(position);
        cmp.setResizable(resizable);
        cmp.setStack(stack);
        cmp.setTitle(title);
        cmp.setZIndex(zIndex);
    }

    public void setDisabled(String disabled)
    {
        this.disabled = disabled;
    }

    public void setAutoOpen(String autoOpen)
    {
        this.autoOpen = autoOpen;
    }

    public void setDraggable(String draggable)
    {
        this.draggable = draggable;
    }

    public void setMaxHeight(String maxHeight)
    {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(String maxWidth)
    {
        this.maxWidth = maxWidth;
    }

    public void setMinHeight(String minHeight)
    {
        this.minHeight = minHeight;
    }

    public void setMinWidth(String minWidth)
    {
        this.minWidth = minWidth;
    }

    public void setModal(String modal)
    {
        this.modal = modal;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void setResizable(String resizable)
    {
        this.resizable = resizable;
    }

    public void setStack(String stack)
    {
        this.stack = stack;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setZIndex(String zIndex)
    {
        this.zIndex = zIndex;
    }

    private String disabled;
    private String autoOpen;
    private String draggable;
    private String maxHeight;
    private String maxWidth;
    private String minHeight;
    private String minWidth;
    private String modal;
    private String position;
    private String resizable;
    private String stack;
    private String title;
    private String zIndex;
}
