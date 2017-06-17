// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AutoComplete.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextField

public class AutoComplete extends TextField
{

    public AutoComplete(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "autocomplete";
    }

    protected String getDefaultTemplate()
    {
        return "autocomplete-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("size", size);
        addProperty("componentName", "autocomplete");
        addProperty("clazz", clazz);
        addProperty("delay", delay);
        addProperty("jsParam", jsParam);
        addProperty("minChars", minChars, "1");
        addProperty("maxNum", maxNum, "100");
        if(StringUtils.isNotBlank(selectWidth))
            selectWidth = selectWidth.replaceAll("px", "");
        addProperty("selectWidth", selectWidth, "300");
        addProperty("showId", showId, "false");
        if(StringUtils.isNotBlank(scrollHeight))
            scrollHeight = scrollHeight.replaceAll("px", "");
        addProperty("scrollHeight", scrollHeight, "150");
        addProperty("showOnFocus", showOnFocus, "false");
        addProperty("interactFunction", interactFunction);
    }

    public void setDelay(String delay)
    {
        this.delay = delay;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getJsParam()
    {
        return jsParam;
    }

    public void setJsParam(String jsParam)
    {
        this.jsParam = jsParam;
    }

    public String getMinChars()
    {
        return minChars;
    }

    public void setMinChars(String minChars)
    {
        this.minChars = minChars;
    }

    public String getMaxNum()
    {
        return maxNum;
    }

    public void setMaxNum(String maxNum)
    {
        this.maxNum = maxNum;
    }

    public String getSelectWidth()
    {
        return selectWidth;
    }

    public void setSelectWidth(String selectWidth)
    {
        this.selectWidth = selectWidth;
    }

    public String getShowId()
    {
        return showId;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    public String getScrollHeight()
    {
        return scrollHeight;
    }

    public void setScrollHeight(String scrollHeight)
    {
        this.scrollHeight = scrollHeight;
    }

    public String getInteractFunction()
    {
        return interactFunction;
    }

    public void setInteractFunction(String interactFunction)
    {
        this.interactFunction = interactFunction;
    }

    public void setShowOnFocus(String showOnFocus)
    {
        this.showOnFocus = showOnFocus;
    }

    private static final String TEMPLATE = "autocomplete";
    private static final String TEMPLATE_CLOSE = "autocomplete-close";
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
