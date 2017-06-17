// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AutoCompleteTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextFieldTag, AutoComplete

public class AutoCompleteTag extends TextFieldTag
{

    public AutoCompleteTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new AutoComplete(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        AutoComplete cmp = (AutoComplete)component;
        cmp.setClazz(clazz);
        cmp.setDelay(delay);
        cmp.setJsParam(jsParam);
        cmp.setMinChars(minChars);
        cmp.setMaxNum(maxNum);
        cmp.setSelectWidth(selectWidth);
        cmp.setShowId(showId);
        cmp.setScrollHeight(scrollHeight);
        cmp.setInteractFunction(interactFunction);
        cmp.setShowOnFocus(showOnFocus);
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setJsParam(String jsParam)
    {
        this.jsParam = jsParam;
    }

    public void setMinChars(String minChars)
    {
        this.minChars = minChars;
    }

    public void setMaxNum(String maxNum)
    {
        this.maxNum = maxNum;
    }

    public void setSelectWidth(String selectWidth)
    {
        this.selectWidth = selectWidth;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    public void setScrollHeight(String scrollHeight)
    {
        this.scrollHeight = scrollHeight;
    }

    public void setInteractFunction(String interactFunction)
    {
        this.interactFunction = interactFunction;
    }

    public void setDelay(String delay)
    {
        this.delay = delay;
    }

    public void setShowOnFocus(String showOnFocus)
    {
        this.showOnFocus = showOnFocus;
    }

    private String clazz;
    private String delay;
    private String jsParam;
    private String minChars;
    private String maxNum;
    private String selectWidth;
    private String showId;
    private String scrollHeight;
    private String interactFunction;
    private String showOnFocus;
}
