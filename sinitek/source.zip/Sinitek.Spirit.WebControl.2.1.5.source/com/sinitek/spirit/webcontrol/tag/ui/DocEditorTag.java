// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocEditorTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, DocEditor

public class DocEditorTag extends FieldTag
{

    public DocEditorTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new DocEditor(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        DocEditor cmp = (DocEditor)component;
        cmp.setHeight(height);
        cmp.setEmptyText(emptyText);
        cmp.setImageUploadJson(imageUploadJson);
        cmp.setItems(items);
    }

    public void setEmptyText(String emptyText)
    {
        this.emptyText = emptyText;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setImageUploadJson(String imageUploadJson)
    {
        this.imageUploadJson = imageUploadJson;
    }

    public void setItems(String items)
    {
        this.items = items;
    }

    private String height;
    private String emptyText;
    private String imageUploadJson;
    private String items;
}
