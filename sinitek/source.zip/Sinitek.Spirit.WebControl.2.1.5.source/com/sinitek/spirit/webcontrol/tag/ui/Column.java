// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Column.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import com.sinitek.spirit.webcontrol.common.FrameworkConfig;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Component

public class Column extends Component
{

    public Column(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate()
    {
        return "column";
    }

    protected String getDefaultTemplate()
    {
        return "column-close";
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        addProperty("componentName", "column");
        addProperty("colType", "normal");
        addProperty("property", property);
        addProperty("width", width);
        String d_align = "center";
        String d_valign = "middle";
        if(StringUtils.isNotBlank(FrameworkConfig.getInstance().getParam("ui.table.col.align")))
            d_align = FrameworkConfig.getInstance().getParam("ui.table.col.align");
        if(StringUtils.isNotBlank(FrameworkConfig.getInstance().getParam("ui.table.col.valign")))
            d_valign = FrameworkConfig.getInstance().getParam("ui.table.col.valign");
        addProperty("align", align, d_align);
        addProperty("valign", valign, d_valign);
        addProperty("allowSort", allowSort, "true");
        addProperty("maxWords", maxWords);
        addProperty("fixed", fixed, "false");
        addProperty("filter", filter, "false");
        addProperty("collect", collect);
        addProperty("show", show, "true");
        addProperty("afterText", afterText);
        addProperty("beforeText", beforeText);
        addProperty("plugin", plugin);
        addProperty("group", group);
        addProperty("groupTop", groupTop);
        addProperty("allowTip", allowTip, "false");
        addProperty("helpContent", helpContent);
        addProperty("mergeCol", mergeCol, "false");
        addProperty("xlsColWidth", xlsColWidth);
        Map parent = getParentParam();
        if(parent != null)
        {
            addProperty("parentId", parent.get("id"));
            if(parent.get("tableId") != null)
                addProperty("parentId", parent.get("tableId"));
            addProperty("sortType", parent.get("sortType"));
            addProperty("type", parent.get("type"));
            addProperty("nowrap", parent.get("nowrap"));
            if(parent.get("colNum") != null)
            {
                Integer colNum = Integer.valueOf((new Integer((String)parent.get("colNum"))).intValue() - 1);
                String _allowExport = (String)parent.get("allowExport");
                String _allowDetail = (String)parent.get("allowDetail");
                addProperty("allowExport", allowExport, _allowExport);
                addProperty("allowDetail", allowDetail, _allowDetail);
                addProperty("colNum", Integer.toString(colNum.intValue()), "0");
            }
        }
        addProperty("nowrap", nowrap);
    }

    public boolean end(Writer writer, String s)
    {
        if(StringUtils.isNotBlank(s))
            try
            {
                s = URLEncoder.encode(s, "UTF-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new RuntimeException("\u8868\u8FBE\u5F0F\u52A0\u5BC6\u5931\u8D25", e);
            }
        super.end(writer, s);
        return true;
    }

    public boolean start(Writer writer)
    {
        Map parent = getParentParam();
        if(parent != null && parent.get("colNum") != null)
        {
            Integer colNum = new Integer((String)parent.get("colNum"));
            parent.put("colNum", Integer.toString(colNum.intValue() + 1));
        }
        super.start(writer);
        return true;
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

    public boolean usesBody()
    {
        return true;
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

    private static final String TEMPLATE = "column";
    private static final String TEMPLATE_CLOSE = "column-close";
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
