// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableTag.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            ComponentTag, Table

public class TableTag extends ComponentTag
{

    public TableTag()
    {
    }

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return new Table(stack, req, res);
    }

    protected void populateParams()
    {
        super.populateParams();
        Table cmp = (Table)component;
        cmp.setType(type);
        cmp.setPageSize(pageSize);
        cmp.setActionClass(actionClass);
        cmp.setWidth(width);
        cmp.setTableWidth(tableWidth);
        cmp.setAllowInit(allowInit);
        cmp.setPersonal(personal);
        cmp.setAllowDetail(allowDetail);
        cmp.setAllowColumnOrd(allowColumnOrd);
        cmp.setAllowExport(allowExport);
        cmp.setExportOption(exportOption);
        cmp.setTitle(title);
        cmp.setRowHeight(rowHeight);
        cmp.setQueryForm(queryForm);
        cmp.setSortType(sortType);
        cmp.setPageInfoType(pageInfoType);
        cmp.setNowrap(nowrap);
        cmp.setMode(mode);
        cmp.setAllowBlankRow(allowBlankRow);
        cmp.setHeight(height);
        cmp.setAllowHead(allowHead);
        cmp.setAllowConfigPageSize(allowConfigPageSize);
        cmp.setExportFileName(exportFileName);
        cmp.setBlankText(blankText);
        cmp.setSubList(subList);
        cmp.setAllowDuplicate(allowDuplicate);
        cmp.setExtraOrderBy(extraOrderBy);
        cmp.setExportMaxRow(exportMaxRow);
        cmp.setFaster(faster);
    }

    public void setFaster(String faster)
    {
        this.faster = faster;
    }

    public void setExportMaxRow(String exportMaxRow)
    {
        this.exportMaxRow = exportMaxRow;
    }

    public void setSubList(String subList)
    {
        this.subList = subList;
    }

    public void setQueryForm(String queryForm)
    {
        this.queryForm = queryForm;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public void setActionClass(String actionClass)
    {
        this.actionClass = actionClass;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setExportOption(String exportOption)
    {
        this.exportOption = exportOption;
    }

    public void setTableWidth(String tableWidth)
    {
        this.tableWidth = tableWidth;
    }

    public void setAllowInit(String allowInit)
    {
        this.allowInit = allowInit;
    }

    public void setPersonal(String personal)
    {
        this.personal = personal;
    }

    public void setAllowDetail(String allowDetail)
    {
        this.allowDetail = allowDetail;
    }

    public void setAllowColumnOrd(String allowColumnOrd)
    {
        this.allowColumnOrd = allowColumnOrd;
    }

    public void setAllowExport(String allowExport)
    {
        this.allowExport = allowExport;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setRowHeight(String rowHeight)
    {
        this.rowHeight = rowHeight;
    }

    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }

    public void setPageInfoType(String pageInfoType)
    {
        this.pageInfoType = pageInfoType;
    }

    public void setNowrap(String nowrap)
    {
        this.nowrap = nowrap;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public void setAllowBlankRow(String allowBlankRow)
    {
        this.allowBlankRow = allowBlankRow;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setAllowHead(String allowHead)
    {
        this.allowHead = allowHead;
    }

    public void setAllowConfigPageSize(String allowConfigPageSize)
    {
        this.allowConfigPageSize = allowConfigPageSize;
    }

    public void setExportFileName(String exportFileName)
    {
        this.exportFileName = exportFileName;
    }

    public void setBlankText(String blankText)
    {
        this.blankText = blankText;
    }

    public void setAllowDuplicate(String allowDuplicate)
    {
        this.allowDuplicate = allowDuplicate;
    }

    public void setExtraOrderBy(String extraOrderBy)
    {
        this.extraOrderBy = extraOrderBy;
    }

    private String type;
    private Integer pageSize;
    private String actionClass;
    private String width;
    private String height;
    private String tableWidth;
    private String rowHeight;
    private String allowInit;
    private String personal;
    private String allowDetail;
    private String allowColumnOrd;
    private String allowExport;
    private String exportOption;
    private String title;
    private String queryForm;
    private String sortType;
    private String pageInfoType;
    private String nowrap;
    private String mode;
    private String allowBlankRow;
    private String allowHead;
    private String allowConfigPageSize;
    private String exportFileName;
    private String exportMaxRow;
    private String blankText;
    private String subList;
    private String allowDuplicate;
    private String extraOrderBy;
    private String faster;
}
