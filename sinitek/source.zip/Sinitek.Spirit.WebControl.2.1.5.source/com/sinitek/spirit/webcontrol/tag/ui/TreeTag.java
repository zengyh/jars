// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Tree, Component

public class TreeTag extends ComponentTag
{

    public TreeTag()
    {
    }

    public com.sinitek.spirit.webcontrol.tag.ui.Component getBean(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return new Tree(valueStack, httpServletRequest, httpServletResponse);
    }

    protected void populateParams()
    {
        super.populateParams();
        ((Tree)component).setLines(lines);
        ((Tree)component).setRootText(rootText);
        ((Tree)component).setHrefTarget(hrefTarget);
        ((Tree)component).setHidden(hidden);
        ((Tree)component).setRootIcon(rootIcon);
        ((Tree)component).setJsList(jsList);
        ((Tree)component).setClazz(clazz);
        ((Tree)component).setHeight(height);
        ((Tree)component).setRootExpanded(rootExpanded);
        ((Tree)component).setWidth(width);
        ((Tree)component).setJsParam(jsParam);
    }

    public void setHidden(String hidden)
    {
        this.hidden = hidden;
    }

    public void setHrefTarget(String hrefTarget)
    {
        this.hrefTarget = hrefTarget;
    }

    public void setLines(String lines)
    {
        this.lines = lines;
    }

    public void setRootText(String rootText)
    {
        this.rootText = rootText;
    }

    public void setRootIcon(String rootIcon)
    {
        this.rootIcon = rootIcon;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setJsList(String jsList)
    {
        this.jsList = jsList;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setRootExpanded(String rootExpanded)
    {
        this.rootExpanded = rootExpanded;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setJsParam(String jsParam)
    {
        this.jsParam = jsParam;
    }

    public volatile Component getBean(ValueStack x0, HttpServletRequest x1, HttpServletResponse x2)
    {
        return getBean(x0, x1, x2);
    }

    private String lines;
    private String rootText;
    private String rootIcon;
    private String hrefTarget;
    private String hidden;
    private String clazz;
    private String jsList;
    private String height;
    private String width;
    private String rootExpanded;
    private String jsParam;
}
