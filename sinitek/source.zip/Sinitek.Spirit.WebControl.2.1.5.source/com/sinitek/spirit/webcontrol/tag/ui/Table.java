// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Table.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.common.FrameworkConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Table extends Component
{

    public Table(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "table";
    }

    protected String getDefaultTemplate()
    {
        return "table-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "table");
        addProperty("colNum", "0");
        addProperty("type", type, "normal");
        addProperty("pageSize", pageSize);
        addProperty("actionClass", actionClass);
        addProperty("width", width, "100%");
        addProperty("height", height);
        addProperty("tableWidth", tableWidth);
        addProperty("rowHeight", rowHeight, "20px");
        addProperty("allowInit", allowInit, "true");
        addProperty("personal", personal);
        addProperty("allowDetail", allowDetail, "false");
        addProperty("allowColumnOrd", allowColumnOrd, "true");
        addProperty("allowExport", allowExport, "false");
        addProperty("title", title);
        addProperty("exportFileName", exportFileName);
        String _exportMaxRow = null;
        if(StringUtils.isNotBlank(FrameworkConfig.getInstance().getParam("ui.table.exportMaxRow")))
            _exportMaxRow = FrameworkConfig.getInstance().getParam("ui.table.exportMaxRow");
        addProperty("exportMaxRow", exportMaxRow, _exportMaxRow);
        addProperty("queryForm", queryForm);
        addProperty("sortType", sortType, "normal");
        addProperty("exportOption", exportOption, "all");
        addProperty("pageInfoType", pageInfoType, "normal");
        addProperty("nowrap", nowrap, "false");
        addProperty("mode", mode, "fixed");
        addProperty("allowBlankRow", allowBlankRow, "false");
        addProperty("allowHead", allowHead, "true");
        addProperty("allowConfigPageSize", allowConfigPageSize, "true");
        addProperty("blankText", blankText, "&nbsp;");
        addProperty("subList", subList, "true");
        addProperty("allowDuplicate", allowDuplicate, "true");
        addProperty("extraOrderBy", extraOrderBy);
        addProperty("faster", faster, "false");
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

    public void setExportOption(String exportOption)
    {
        this.exportOption = exportOption;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setAllowBlankRow(String allowBlankRow)
    {
        this.allowBlankRow = allowBlankRow;
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

    private static final String TEMPLATE = "table";
    private static final String TEMPLATE_CLOSE = "table-close";
    private String type;
    private Integer pageSize;
    private String actionClass;
    private String width;
    private String height;
    String tableWidth;
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
    String nowrap;
    String mode;
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
