// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnEntity.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Column

public class ColumnEntity extends Column
{

    public ColumnEntity(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column-entity";
    }

    protected String getDefaultTemplate()
    {
        return "column-entity-close";
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
        addProperty("colType", "entity");
        addProperty("format", format);
        addProperty("entityName", entityName);
        addProperty("foreignProperty", foreignProperty);
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

    private static final String TEMPLATE = "column-entity";
    private static final String TEMPLATE_CLOSE = "column-entity-close";
    private String format;
    private String entityName;
    private String foreignProperty;
}
