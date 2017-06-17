// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ColumnTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Column

public class ColumnTag extends ComponentTag
{

    public ColumnTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Column(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Column cmp = (Column)component;
        ((Column)component).setProperty(property);
        cmp.setWidth(width);
        cmp.setAlign(align);
        cmp.setValign(valign);
        cmp.setAllowSort(allowSort);
        cmp.setMaxWords(maxWords);
        cmp.setFixed(fixed);
        cmp.setFilter(filter);
        cmp.setCollect(collect);
        cmp.setShow(show);
        cmp.setAfterText(afterText);
        cmp.setBeforeText(beforeText);
        cmp.setPlugin(plugin);
        cmp.setGroup(group);
        cmp.setNowrap(nowrap);
        cmp.setAllowTip(allowTip);
        cmp.setAllowDetail(allowDetail);
        cmp.setAllowExport(allowExport);
        cmp.setGroupTop(groupTop);
        cmp.setHelpContent(helpContent);
        cmp.setMergeCol(mergeCol);
        cmp.setXlsColWidth(xlsColWidth);
    }

    public void setXlsColWidth(String xlsColWidth)
    {
        this.xlsColWidth = xlsColWidth;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setValign(String valign)
    {
        this.valign = valign;
    }

    public void setAllowSort(String allowSort)
    {
        this.allowSort = allowSort;
    }

    public void setMaxWords(String maxWords)
    {
        this.maxWords = maxWords;
    }

    public void setFixed(String fixed)
    {
        this.fixed = fixed;
    }

    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    public void setCollect(String collect)
    {
        this.collect = collect;
    }

    public void setShow(String show)
    {
        this.show = show;
    }

    public void setAfterText(String afterText)
    {
        this.afterText = afterText;
    }

    public void setBeforeText(String beforeText)
    {
        this.beforeText = beforeText;
    }

    public void setPlugin(String plugin)
    {
        this.plugin = plugin;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public void setNowrap(String nowrap)
    {
        this.nowrap = nowrap;
    }

    public void setAllowTip(String allowTip)
    {
        this.allowTip = allowTip;
    }

    public void setAllowExport(String allowExport)
    {
        this.allowExport = allowExport;
    }

    public void setAllowDetail(String allowDetail)
    {
        this.allowDetail = allowDetail;
    }

    public void setGroupTop(String groupTop)
    {
        this.groupTop = groupTop;
    }

    public void setHelpContent(String helpContent)
    {
        this.helpContent = helpContent;
    }

    public void setMergeCol(String mergeCol)
    {
        this.mergeCol = mergeCol;
    }

    private String property;
    private String width;
    private String align;
    private String valign;
    private String allowSort;
    private String maxWords;
    private String fixed;
    private String filter;
    private String collect;
    private String show;
    private String afterText;
    private String beforeText;
    private String plugin;
    private String group;
    private String groupTop;
    private String nowrap;
    private String allowTip;
    private String allowExport;
    private String allowDetail;
    private String helpContent;
    private String mergeCol;
    private String xlsColWidth;
}
