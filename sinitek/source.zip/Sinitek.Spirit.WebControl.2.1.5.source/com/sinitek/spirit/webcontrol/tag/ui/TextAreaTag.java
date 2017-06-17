// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextAreaTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            TextFieldTag, TextArea

public class TextAreaTag extends TextFieldTag
{

    public TextAreaTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new TextArea(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        TextArea cmp = (TextArea)component;
        cmp.setRows(rows);
        cmp.setCols(cols);
    }

    public void setRows(String rows)
    {
        this.rows = rows;
    }

    public void setCols(String cols)
    {
        this.cols = cols;
    }

    private String rows;
    private String cols;
}
