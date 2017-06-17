// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnForeign.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnForeign extends Column
{

    public ColumnForeign(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-foreign";
    }

    protected String getDefaultTemplate()
    {
        return "column-foreign-close";
    }

    public boolean start(Writer writer)
    {
        super.start(writer);
        return true;
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "column");
        addProperty("colType", "foreign");
        addProperty("format", format);
        addProperty("tableName", tableName);
        addProperty("columnName", columnName);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    private static final String TEMPLATE = "column-foreign";
    private static final String TEMPLATE_CLOSE = "column-foreign-close";
    private String tableName;
    private String columnName;
    private String format;
}
