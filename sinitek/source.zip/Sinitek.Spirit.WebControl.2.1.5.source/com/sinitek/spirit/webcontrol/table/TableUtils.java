// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableUtils.java

package com.sinitek.spirit.webcontrol.table;

import com.sinitek.base.enumsupport.AbstractEnumItem;
import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.IMetaRelaEntityObject;
import com.sinitek.spirit.webcontrol.utils.DataParserUtils;
import com.sinitek.spirit.webcontrol.utils.OgnlUtils;
import java.net.URLDecoder;
import java.sql.Clob;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;

class TableUtils
{

    TableUtils()
    {
    }

    public static Integer getStartRow(String pageSize, Integer targetPage)
    {
        Integer result = Integer.valueOf(0);
        if(StringUtils.isNotBlank(pageSize))
            result = Integer.valueOf((targetPage.intValue() - 1) * getPageSize(pageSize).intValue());
        return result;
    }

    public static Integer getPageSize(String pageSize)
    {
        if(StringUtils.isNotBlank(pageSize) && StringUtils.isNumeric(pageSize))
            return new Integer(pageSize);
        else
            return Integer.valueOf(1);
    }

    public static Integer getTargetPage(String targetPage, Integer allPage)
    {
        Integer result;
        if(StringUtils.isBlank(targetPage) || !StringUtils.isNumeric(targetPage) || Integer.parseInt(targetPage) < 1)
            result = Integer.valueOf(1);
        else
        if(Integer.parseInt(targetPage) > allPage.intValue())
            result = allPage;
        else
            result = Integer.valueOf(Integer.parseInt(targetPage));
        return result;
    }

    public static Integer getAllPage(String pageSize, Integer allRow)
    {
        if(StringUtils.isBlank(pageSize))
            pageSize = "1";
        Double x = new Double(allRow.intValue());
        Double y = new Double(pageSize);
        return Integer.valueOf((new Double(Math.ceil(x.doubleValue() / y.doubleValue()))).intValue());
    }

    public static Integer getEndRow(String pageSize, Integer allRow, Integer targetPage)
    {
        Integer result = allRow;
        if(StringUtils.isNotBlank(pageSize))
        {
            result = Integer.valueOf(getStartRow(pageSize, targetPage).intValue() + getPageSize(pageSize).intValue());
            if(result.intValue() > allRow.intValue())
                result = allRow;
        }
        return result;
    }

    public static Integer getRealRow(Integer rowNum, String pageSize, String targetPage)
    {
        Integer result = Integer.valueOf(rowNum.intValue() + 1);
        if(StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(targetPage))
            result = Integer.valueOf(result.intValue() + ((new Integer(targetPage)).intValue() - 1) * (new Integer(pageSize)).intValue());
        return result;
    }

    public static String getOrderBy(String defalutOrderBy, String sortInfo)
    {
        String result = "";
        if(StringUtils.isNotBlank(sortInfo))
            result = sortInfo;
        if(StringUtils.isBlank(result))
            result = defalutOrderBy;
        if(StringUtils.isBlank(result))
            result = "1";
        return result;
    }

    public static Map translateVariables(Map obj, Map table, Map colum, Integer rowNumber, Integer colNumber, Object rowData, Integer realRowNumber, HttpServletRequest request)
        throws Exception
    {
        Object data = null;
        String text = "";
        StringBuilder sb = new StringBuilder();
        boolean parsereError = false;
        String colType = (String)colum.get("colType");
        String mode = (String)table.get("mode");
        String property = (String)colum.get("property");
        String expr = (String)colum.get("expr");
        String maxWords = (String)colum.get("maxWords");
        String filter = (String)colum.get("filter");
        String align = (String)colum.get("align");
        String width = (String)colum.get("width");
        String valign = (String)colum.get("valign");
        String height = (String)table.get("rowHeight");
        String beforeText = (String)colum.get("beforeText");
        String afterText = (String)colum.get("afterText");
        String pointPlace = (String)colum.get("pointPlace");
        String isMoney = (String)colum.get("isMoney");
        String format = (String)colum.get("format");
        String entityName = (String)colum.get("entityName");
        String plugin = (String)colum.get("plugin");
        String pluginType = (String)colum.get("pluginType");
        String pluginParam = (String)colum.get("pluginParam");
        String foreignProperty = (String)colum.get("foreignProperty");
        String tableName = (String)colum.get("tableName");
        String columnName = (String)colum.get("columnName");
        String clazz = (String)colum.get("clazz");
        String method = (String)colum.get("method");
        String pluginParam2 = (String)colum.get("pluginParam2");
        String tableId = (String)colum.get("tableId");
        String nowrap = (String)colum.get("nowrap");
        String blankText = (String)table.get("blankText");
        String allowTip = (String)colum.get("allowTip");
        if("checkbox,".equals(pluginType) || "radiobox".equals(pluginType) || "rownum".equals(pluginType))
        {
            align = "center";
            width = "20px";
        }
        sb.append("<td class=\"stk-table-td\" colNum='").append(colNumber).append("' style='");
        if("true".equals(nowrap))
            sb.append("white-space:nowrap;overflow:hidden;");
        else
            sb.append("white-space:normal;");
        if(StringUtils.isNotBlank(width))
        {
            if(!width.contains("px") && !width.contains("%"))
                width = (new StringBuilder()).append(width).append("px").toString();
            sb.append("width:");
            sb.append(width);
            sb.append(";");
        }
        if(StringUtils.isNotBlank(height))
        {
            if(!height.contains("px") && !height.contains("%"))
                height = (new StringBuilder()).append(height).append("px").toString();
            sb.append("height:");
            sb.append(height);
            sb.append(";");
        }
        if(align != null)
        {
            sb.append("text-align:");
            sb.append(align);
            sb.append(";");
        }
        if(valign != null)
        {
            sb.append("vertical-align:");
            sb.append(valign);
            sb.append("!important;");
        }
        sb.append("'");
        if(align != null)
        {
            sb.append(" align='");
            sb.append(align);
            sb.append("'");
        }
        if(valign != null)
        {
            sb.append(" valign='");
            sb.append(valign);
            sb.append("'");
        }
        if(property != null)
            sb.append(" property='").append(property).append("'");
        sb.append(">");
        if(StringUtils.isNotBlank(property))
            data = obj.get(property.toLowerCase());
        if(StringUtils.isNotBlank(expr))
        {
            expr = URLDecoder.decode(expr, "UTF-8");
            Map result = new HashMap();
            result.putAll(obj);
            result.put("request", request);
            result.put("result", obj);
            result.put("session", request.getSession());
            result.put("rowNum", rowNumber);
            result.put("colNum", colNumber);
            OgnlUtils ognl = new OgnlUtils(result);
            data = ognl.getValue(expr);
        }
        if(data != null)
            text = data.toString();
        if(property != null && "true".equals(filter) && !parsereError)
            text = HtmlUtils.htmlEscape(text);
        String orgText = new String(text);
        if(maxWords != null && StringUtils.isNumeric(maxWords))
        {
            int iMaxWord = (new Integer(maxWords)).intValue();
            if(text.length() > iMaxWord)
                text = (new StringBuilder()).append(text.substring(0, iMaxWord - 1)).append("...").toString();
        }
        if("cust".equals(colType))
        {
            text = DataParserUtils.formatCust(obj, clazz, method, request);
            if(text == null)
                text = "";
        } else
        if(StringUtils.isNotBlank(plugin))
        {
            text = "";
            String plugins[] = plugin.split(",");
            String pluginTypes[] = pluginType.split(",");
            String pluginParams[] = pluginParam.split(",");
            String pluginParams2[] = pluginParam2.split(",");
            for(int i = 0; i < plugins.length; i++)
            {
                plugin = plugins[i];
                pluginType = pluginTypes[i];
                pluginParam = pluginParams[i];
                pluginParam2 = pluginParams2[i];
                if("undefined".equals(pluginParam))
                    pluginParam = null;
                if("undefined".equals(pluginParam2))
                    pluginParam2 = null;
                if("button".equals(pluginType))
                {
                    text = (new StringBuilder()).append(text).append("<button type=\"button\" class='stk-button stk-button-st-3' onclick='").append(plugin).append("_plugin_click(_").append(tableId).append(".data[").append(rowNumber).append("]);'").toString();
                    if(plugins.length > 1)
                        text = (new StringBuilder()).append(text).append(" style= 'margin-left:2px;margin-right:2px;'").toString();
                    text = (new StringBuilder()).append(text).append(">").toString();
                    if(StringUtils.isNotBlank(pluginParam2))
                        text = (new StringBuilder()).append(text).append("<span class='stk-button-icon ").append(pluginParam2).append("'></span>").toString();
                    if(StringUtils.isNotBlank(pluginParam))
                        text = (new StringBuilder()).append(text).append("<span>").append(pluginParam).append("</span>").toString();
                    text = (new StringBuilder()).append(text).append("</button>").toString();
                } else
                if("link".equals(pluginType))
                {
                    if(StringUtils.isBlank(pluginParam))
                        pluginParam = orgText;
                    if(maxWords != null && StringUtils.isNumeric(maxWords))
                    {
                        int iMaxWord = (new Integer(maxWords)).intValue();
                        if(pluginParam.length() > iMaxWord)
                            pluginParam = (new StringBuilder()).append(pluginParam.substring(0, iMaxWord - 1)).append("...").toString();
                    }
                    text = (new StringBuilder()).append(text).append("<a style='white-space: nowrap;' href=\"javascript:").append(plugin).append("_plugin_click(_").append(tableId).append(".data[").append(rowNumber).append("]);\"").toString();
                    text = (new StringBuilder()).append(text).append(">").append(pluginParam).append("</a>").toString();
                    if(i != plugins.length - 1)
                        text = (new StringBuilder()).append(text).append("<b class=\"separate_line\">|</b>").toString();
                } else
                if("checkbox".equals(pluginType))
                {
                    String objid = "";
                    text = (new StringBuilder()).append(text).append("<input type='checkbox'").toString();
                    if(plugins.length > 1)
                        text = (new StringBuilder()).append(text).append(" style= 'margin-left:2px;margin-right:2px;'").toString();
                    if(rowData instanceof IMetaObject)
                        objid = (new StringBuilder()).append("").append(((IMetaObject)rowData).getId()).toString();
                    else
                    if((rowData instanceof Map) && ((Map)rowData).get("objid") != null)
                        objid = (new StringBuilder()).append("").append(((Map)rowData).get("objid")).toString();
                    if(StringUtils.isNotBlank(objid))
                        text = (new StringBuilder()).append(text).append(" value='").append(objid).append("'").toString();
                    text = (new StringBuilder()).append(text).append(" name='").append(pluginParam).append("' onclick=").append(plugin).append("_plugin_click(_").append(tableId).append(".data[").append(rowNumber).append("],$(this).attr('checked')); >").toString();
                } else
                if("radiobox".equals(pluginType))
                {
                    String objid = "";
                    text = (new StringBuilder()).append(text).append("<input type='radio'").toString();
                    if(plugins.length > 1)
                        text = (new StringBuilder()).append(text).append(" style= 'margin-left:2px;margin-right:2px;'").toString();
                    if(rowData instanceof IMetaObject)
                        objid = (new StringBuilder()).append("").append(((IMetaObject)rowData).getId()).toString();
                    else
                    if((rowData instanceof Map) && ((Map)rowData).get("objid") != null)
                        objid = (new StringBuilder()).append("").append(((Map)rowData).get("objid")).toString();
                    if(StringUtils.isNotBlank(objid))
                        text = (new StringBuilder()).append(text).append(" value='").append(objid).append("'").toString();
                    text = (new StringBuilder()).append(text).append(" name='").append(pluginParam).append("' onclick=").append(plugin).append("_plugin_click(_").append(tableId).append(".data[").append(rowNumber).append("],$(this).attr('checked')); >").toString();
                } else
                if("rownum".equals(pluginType) && realRowNumber != null)
                    text = (new StringBuilder()).append("<span title='").append(realRowNumber).append("'>").append(realRowNumber).append("</span>").toString();
            }

        } else
        if(data != null && ((data instanceof IMetaObject) || (data instanceof Clob) || StringUtils.isNotBlank(data.toString())))
        {
            if(data instanceof IMetaRelaEntityObject)
            {
                IMetaRelaEntityObject eo = (IMetaRelaEntityObject)data;
                data = eo.getEntityOrigValue();
            }
            if(data instanceof Clob)
            {
                Clob clob = (Clob)data;
                data = clob.getSubString(1L, (int)clob.length());
            }
            if("number".equals(colType))
                text = DataParserUtils.formatNumber(data, pointPlace, isMoney);
            else
            if("date".equals(colType) || "normal".equals(colType) && (data instanceof Date))
                text = DataParserUtils.formatDate(data, format);
            else
            if("enum".equals(colType) || "normal".equals(colType) && (data instanceof AbstractEnumItem))
                text = DataParserUtils.formatEnum(data, clazz, format);
            else
            if("entity".equals(colType))
                text = DataParserUtils.formatEntity(data, entityName, foreignProperty, format);
            else
            if("foreign".equals(colType))
                text = DataParserUtils.formatForeign(data, tableName, columnName, format);
            else
            if("boolean".equals(colType))
                text = DataParserUtils.formatBoolean(data, format);
        }
        if(StringUtils.isNotBlank(text))
        {
            if(StringUtils.isNotBlank(beforeText))
                text = (new StringBuilder()).append(beforeText).append(text).toString();
            if(StringUtils.isNotBlank(afterText))
                text = (new StringBuilder()).append(text).append(afterText).toString();
        }
        obj.put(property, text);
        Map result = new HashMap();
        result.put("text", text);
        if(StringUtils.isBlank(text))
        {
            text = blankText;
        } else
        {
            String tmp = "<div ";
            if("date".equals(colType))
                tmp = (new StringBuilder()).append(tmp).append(" class='size10'").toString();
            tmp = (new StringBuilder()).append(tmp).append(" style='").toString();
            if("true".equals(nowrap))
                tmp = (new StringBuilder()).append(tmp).append("white-space:nowrap;overflow:hidden;").toString();
            else
                tmp = (new StringBuilder()).append(tmp).append("white-space:normal;").toString();
            tmp = (new StringBuilder()).append(tmp).append("'").toString();
            if("true".equals(allowTip))
                tmp = (new StringBuilder()).append(tmp).append(" title=\"").append(HtmlUtils.htmlEscape(orgText)).append("\" tipPos='").append(align).append("'").toString();
            tmp = (new StringBuilder()).append(tmp).append(">").append(text).append("</div>").toString();
            text = tmp;
        }
        sb.append(text).append("</td>");
        result.put("html", sb.toString());
        return result;
    }

    public static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/table/TableUtils);

}
