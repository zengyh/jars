// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tree.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Tree extends Component
{

    public Tree(ValueStack valueStack, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        super(valueStack, httpServletRequest, httpServletResponse);
    }

    public String getDefaultOpenTemplate()
    {
        return "tree";
    }

    protected String getDefaultTemplate()
    {
        return "tree-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "tree");
        addProperty("jsParam", jsParam);
        if(lines != null)
            addParameter("lines", findString(lines));
        else
            addParameter("lines", "true");
        if(rootText != null)
            addParameter("rootText", findString(rootText));
        if(hrefTarget != null)
            addParameter("hrefTarget", findString(hrefTarget));
        else
            addParameter("hrefTarget", "_self");
        if(hidden != null)
            addParameter("hidden", findString(hidden));
        if(rootIcon != null)
            addParameter("rootIcon", findString(rootIcon));
        if(jsList != null)
            addParameter("jsList", findString(jsList));
        if(height != null)
            addParameter("height", findString(height));
        if(clazz != null)
            addParameter("clazz", findString(clazz));
        if(width != null)
            addParameter("width", findString(width));
        else
            addParameter("width", "200");
        if(rootExpanded != null)
            addParameter("rootExpanded", findString(rootExpanded));
        else
            addParameter("rootExpanded", "true");
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

    public void setJsList(String jsList)
    {
        this.jsList = jsList;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
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

    private static final String TEMPLATE = "tree";
    private static final String TEMPLATE_CLOSE = "tree-close";
    private String lines;
    private String rootText;
    private String hrefTarget;
    private String hidden;
    private String rootIcon;
    private String clazz;
    private String jsList;
    private String height;
    private String width;
    private String jsParam;
    private String rootExpanded;
}
