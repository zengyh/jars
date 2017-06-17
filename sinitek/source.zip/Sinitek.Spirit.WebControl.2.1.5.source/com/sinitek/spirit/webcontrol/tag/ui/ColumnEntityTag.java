// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnEntityTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ColumnTag, ColumnEntity

public class ColumnEntityTag extends ColumnTag
{

    public ColumnEntityTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ColumnEntity(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ColumnEntity cmp = (ColumnEntity)component;
        cmp.setFormat(format);
        cmp.setEntityName(entityName);
        cmp.setForeignProperty(foreignProperty);
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public void setForeignProperty(String foreignProperty)
    {
        this.foreignProperty = foreignProperty;
    }

    private String format;
    private String entityName;
    private String foreignProperty;
}
