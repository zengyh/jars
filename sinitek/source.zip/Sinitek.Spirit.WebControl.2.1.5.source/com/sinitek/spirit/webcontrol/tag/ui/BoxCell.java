// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BoxCell.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class BoxCell extends Component
{

    public BoxCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public String getDefaultOpenTemplate()
    {
        return "boxcell";
    }

    protected String getDefaultTemplate()
    {
        return "boxcell-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "boxcell");
        Map parent = getParentParam();
        if(parent != null)
        {
            String labelWidth = (String)parent.get("labelWidth");
            String cellHeight = (String)parent.get("cellHeight");
            String cellWidth = (String)parent.get("cellWidth");
            String cellUnderLine = (String)parent.get("cellUnderLine");
            String cellMarginBottom = (String)parent.get("cellMarginBottom");
            String feature = (String)parent.get("feature");
            if(this.labelWidth == null && StringUtils.isNotBlank(labelWidth))
                this.labelWidth = labelWidth;
            if(height == null && StringUtils.isNotBlank(cellHeight))
                height = cellHeight;
            if(width == null && StringUtils.isNotBlank(cellWidth))
                width = cellWidth;
            if(marginBottom == null && StringUtils.isNotBlank(cellMarginBottom))
                marginBottom = cellMarginBottom;
            if(underLine == null && StringUtils.isNotBlank(cellUnderLine))
                underLine = cellUnderLine;
            if(this.feature == null && StringUtils.isNotBlank(feature))
                this.feature = feature;
        }
        addProperty("width", width);
        addProperty("height", height, "30px");
        addProperty("labelWidth", this.labelWidth, "120px");
        addProperty("align", align, "left");
        addProperty("underLine", underLine, "false");
        addProperty("feature", this.feature);
        addProperty("marginBottom", marginBottom);
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setLabelWidth(String labelWidth)
    {
        this.labelWidth = labelWidth;
    }

    public void setUnderLine(String underLine)
    {
        this.underLine = underLine;
    }

    public void setMarginBottom(String marginBottom)
    {
        this.marginBottom = marginBottom;
    }

    private static final String TEMPLATE = "boxcell";
    private static final String TEMPLATE_CLOSE = "boxcell-close";
    private String width;
    private String underLine;
    private String align;
    private String height;
    private String labelWidth;
    private String marginBottom;
}
