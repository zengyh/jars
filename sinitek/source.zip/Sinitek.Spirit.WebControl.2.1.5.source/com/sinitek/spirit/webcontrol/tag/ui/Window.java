// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Window.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Box

public class Window extends Box
{

    public Window(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "window";
    }

    protected String getDefaultTemplate()
    {
        return "window-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "window");
        addProperty("disabled", disabled);
        addProperty("autoOpen", autoOpen, "false");
        addProperty("draggable", draggable);
        addProperty("maxHeight", maxHeight);
        addProperty("maxWidth", maxWidth);
        addProperty("minHeight", minHeight);
        addProperty("minWidth", minWidth);
        addProperty("modal", modal, "true");
        addProperty("position", position, "center");
        addProperty("resizable", resizable, "false");
        addProperty("stack", stack);
        addProperty("title", title);
        addProperty("zIndex", zIndex);
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

    private static final String TEMPLATE = "window";
    private static final String TEMPLATE_CLOSE = "window-close";
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
