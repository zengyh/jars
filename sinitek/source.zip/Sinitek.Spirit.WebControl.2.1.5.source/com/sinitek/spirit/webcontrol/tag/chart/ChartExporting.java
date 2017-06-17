// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartExporting.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChart

public class ChartExporting extends AbsChart
{

    public ChartExporting(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "chart-exporting";
    }

    protected String getDefaultTemplate()
    {
        return "chart-exporting-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "chart_title");
        addProperty("enabled", enabled);
        addProperty("filename", filename);
        addProperty("type", type);
        addProperty("url", url);
        if(StringUtils.isNotBlank(width))
            width = width.replaceAll("px", "");
        addProperty("width", width);
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    private static final String TEMPLATE = "chart-exporting";
    private static final String TEMPLATE_CLOSE = "chart-exporting-close";
    private String enabled;
    private String filename;
    private String type;
    private String url;
    private String width;
}
