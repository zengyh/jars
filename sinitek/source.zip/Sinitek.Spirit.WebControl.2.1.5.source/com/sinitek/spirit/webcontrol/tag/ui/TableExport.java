// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableExport.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ToolBar

public class TableExport extends ToolBar
{

    public TableExport(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "tableexport";
    }

    protected String getDefaultTemplate()
    {
        return "tableexport-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "tableexport");
        Map parent = getParentParam();
        if(parent != null)
        {
            addProperty("tableId", parent.get("id"));
            addProperty("sortType", parent.get("sortType"));
            addProperty("type", parent.get("type"));
            addProperty("nowrap", parent.get("nowrap"));
            addProperty("actionClass", parent.get("actionClass"));
            addProperty("allowExport", parent.get("allowExport"));
            addProperty("allowDetail", parent.get("allowDetail"));
        }
    }

    private static final String TEMPLATE = "tableexport";
    private static final String TEMPLATE_CLOSE = "tableexport-close";
}
