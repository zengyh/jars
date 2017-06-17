// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartExportingTag.java

package com.sinitek.spirit.webcontrol.tag.chart;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.chart:
//            AbsChartTag, ChartExporting

public class ChartExportingTag extends AbsChartTag
{

    public ChartExportingTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ChartExporting(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ChartExporting param = (ChartExporting)component;
        param.setEnabled(enabled);
        param.setFilename(filename);
        param.setType(type);
        param.setUrl(url);
        param.setWidth(width);
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

    private String enabled;
    private String filename;
    private String type;
    private String url;
    private String width;
}
