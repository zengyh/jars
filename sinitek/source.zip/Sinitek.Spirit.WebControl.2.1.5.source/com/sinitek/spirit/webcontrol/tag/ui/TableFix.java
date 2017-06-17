// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableFix.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Table

public class TableFix extends Table
{

    public TableFix(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return TEMPLATE;
    }

    protected String getDefaultTemplate()
    {
        return TEMPLATE_CLOSE;
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        String userAgent = request.getHeader("user-agent");
        if(userAgent != null)
            userAgent = userAgent.toLowerCase();
        boolean ios = userAgent != null && (userAgent.contains("ipad") || userAgent.contains("iphone"));
        if(ios)
        {
            TEMPLATE = "table";
            TEMPLATE_CLOSE = "table-close";
            addProperty("componentName", "table");
        } else
        {
            TEMPLATE = "tablefix";
            TEMPLATE_CLOSE = "tablefix-close";
            addProperty("componentName", "tablefix");
        }
        addProperty("fixWidth", fixWidth, "160px");
        addProperty("tableWidth", tableWidth, "150%");
        addProperty("mode", mode, "fixed");
        addProperty("nowrap", nowrap, "true");
        addProperty("adjustRow", adjustRow, "false");
    }

    public void setFixWidth(String fixWidth)
    {
        this.fixWidth = fixWidth;
    }

    public void setAdjustRow(String adjustRow)
    {
        this.adjustRow = adjustRow;
    }

    private static String TEMPLATE = "tablefix";
    private static String TEMPLATE_CLOSE = "tablefix-close";
    private String fixWidth;
    private String adjustRow;

}
