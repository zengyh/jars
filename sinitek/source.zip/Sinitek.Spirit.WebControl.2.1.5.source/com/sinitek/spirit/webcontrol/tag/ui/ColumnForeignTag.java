// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnForeignTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnForeign

public class ColumnForeignTag extends ColumnTag
{

    public ColumnForeignTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnForeign(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnForeign cmp = (ColumnForeign)component;
        cmp.setFormat(format);
        cmp.setTableName(tableName);
        cmp.setColumnName(columnName);
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

    private String tableName;
    private String columnName;
    private String format;
}
