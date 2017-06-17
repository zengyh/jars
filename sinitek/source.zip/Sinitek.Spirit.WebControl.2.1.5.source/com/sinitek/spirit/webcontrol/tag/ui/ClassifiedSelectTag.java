// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassifiedSelectTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            FieldTag, ClassifiedSelect

public class ClassifiedSelectTag extends FieldTag
{

    public ClassifiedSelectTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new ClassifiedSelect(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        ClassifiedSelect select = (ClassifiedSelect)component;
        select.setMultiple(multiple);
        select.setClazz(clazz);
        select.setJsParam(jsParam);
        select.setInteractFunction(interactFunction);
        select.setCellWidth(cellWidth);
        select.setHeight(height);
        select.setAllowInit(allowInit);
        select.setDirection(direction);
        select.setTarget(target);
        select.setAllowSelectClass(allowSelectClass);
        select.setPositionX(positionX);
        select.setPositionY(positionY);
        select.setSelectAll(selectAll);
    }

    public void setMultiple(String multiple)
    {
        this.multiple = multiple;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public void setJsParam(String jsParam)
    {
        this.jsParam = jsParam;
    }

    public void setInteractFunction(String interactFunction)
    {
        this.interactFunction = interactFunction;
    }

    public void setCellWidth(String cellWidth)
    {
        this.cellWidth = cellWidth;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setAllowInit(String allowInit)
    {
        this.allowInit = allowInit;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public void setAllowSelectClass(String allowSelectClass)
    {
        this.allowSelectClass = allowSelectClass;
    }

    public void setPositionX(String positionX)
    {
        this.positionX = positionX;
    }

    public void setPositionY(String positionY)
    {
        this.positionY = positionY;
    }

    public void setSelectAll(String selectAll)
    {
        this.selectAll = selectAll;
    }

    private String multiple;
    private String clazz;
    private String jsParam;
    private String interactFunction;
    private String cellWidth;
    private String height;
    private String allowInit;
    private String target;
    private String direction;
    private String allowSelectClass;
    private String positionX;
    private String positionY;
    private String selectAll;
}
