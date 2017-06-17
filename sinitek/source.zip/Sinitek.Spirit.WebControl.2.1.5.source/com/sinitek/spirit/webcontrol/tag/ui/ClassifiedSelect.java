// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassifiedSelect.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public class ClassifiedSelect extends Field
{

    public ClassifiedSelect(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "classifiedselect";
    }

    protected String getDefaultTemplate()
    {
        return "classifiedselect-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "classifiedselect");
        if(value != null)
            addParameter("nameValue", value);
        addProperty("interactFunction", interactFunction);
        addProperty("clazz", clazz);
        addProperty("jsParam", jsParam);
        addProperty("multiple", multiple, "false");
        addProperty("hidden", "true");
        addProperty("cellWidth", cellWidth, "100px");
        addProperty("height", height);
        addProperty("allowInit", allowInit, "true");
        addProperty("allowSelectClass", allowSelectClass, "false");
        addProperty("target", target);
        addProperty("direction", direction, "bottom");
        if(positionY != null)
            positionY = positionY.replace("px", "");
        if(positionX != null)
            positionX = positionX.replace("px", "");
        addProperty("positionY", positionY, "0");
        addProperty("positionX", positionX, "0");
        addProperty("selectAll", selectAll, "false");
    }

    public void setCellWidth(String cellWidth)
    {
        this.cellWidth = cellWidth;
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

    private static final String TEMPLATE = "classifiedselect";
    private static final String TEMPLATE_CLOSE = "classifiedselect-close";
    private String multiple;
    private String clazz;
    private String jsParam;
    private String interactFunction;
    private String cellWidth;
    private String height;
    private String allowInit;
    private String target;
    private String allowSelectClass;
    private String direction;
    private String positionX;
    private String positionY;
    private String selectAll;
}
