// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritTableAction.java

package com.sinitek.spirit.webcontrol.table;

import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.webcontrol.userconfig.ISpiritUserConfigAware;
import com.sinitek.spirit.webcontrol.userconfig.SpiritUserConfigFacory;
import com.sinitek.spirit.webcontrol.utils.ClockUtils;
import com.sinitek.spirit.webcontrol.utils.FormatUtils;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.table:
//            ITableAware, TableResultMetaDBHql, TableResultMetaDBSql, TableResultList, 
//            ITableBatch, ICheckBoxPluginAware, IRadioBoxPluginAware, AbsTableResult, 
//            TableUtils

public class SpiritTableAction
{

    public SpiritTableAction()
    {
    }

    public Map init(Map tableConfig, Map columnConfig[], String targetPage, Map param, String sortInfo, HttpServletRequest request)
        throws Exception
    {
        ClockUtils clock = new ClockUtils(LOG);
        Map result = new HashMap();
        String className = (String)tableConfig.get("actionClass");
        String subList = (String)tableConfig.get("subList");
        String faster = (String)tableConfig.get("faster");
        try
        {
            String realPageSize = getRealPageSize(tableConfig, request);
            if(realPageSize != null)
            {
                param.put("__pageSize", realPageSize);
                param.put("__pageNum", targetPage != null ? ((Object) (targetPage)) : "1");
            }
            ITableAware action = (ITableAware)Class.forName(className).newInstance();
            Object o = action.getResult(param, request);
            int count = 0;
            List data;
            if(o instanceof IMetaDBQuery)
            {
                IMetaDBQuery query = (IMetaDBQuery)o;
                setMetadbOrderBy(tableConfig, param, sortInfo, action, query, request);
                if(realPageSize != null)
                {
                    query.setFirstResult(TableUtils.getStartRow(realPageSize, new Integer(targetPage)).intValue());
                    if(StringUtils.isBlank(realPageSize))
                        ((IMetaDBQuery)o).setMaxResult(count);
                    else
                        ((IMetaDBQuery)o).setMaxResult(TableUtils.getPageSize(realPageSize).intValue());
                }
                count = query.getResultCount();
                clock.printStep((new StringBuilder()).append("\u3010").append(className).append("\u3011getResultCount").toString());
                AbsTableResult strategy;
                if(query.getEntity() != null)
                    strategy = new TableResultMetaDBHql();
                else
                    strategy = new TableResultMetaDBSql();
                data = strategy.getResultList(o, tableConfig);
                clock.printStep((new StringBuilder()).append("\u3010").append(className).append("\u3011getResult").toString());
                result.put("resultType", "metadb");
            } else
            if(o instanceof List)
            {
                List query = (List)o;
                count = query.size();
                if(realPageSize != null)
                    if(!"false".equals(subList))
                    {
                        int start = TableUtils.getStartRow(realPageSize, new Integer(targetPage)).intValue();
                        int end = TableUtils.getEndRow(realPageSize, Integer.valueOf(query.size()), new Integer(targetPage)).intValue();
                        if(start > count)
                            query = Collections.EMPTY_LIST;
                        else
                        if(query == null || query.isEmpty())
                            query = Collections.EMPTY_LIST;
                        else
                            query = query.subList(start, end);
                    } else
                    if(param.get("__allRow") != null)
                        count = (new Integer((String)param.get("__allRow"))).intValue();
                AbsTableResult strategy = new TableResultList();
                data = strategy.getResultList(query, tableConfig);
                result.put("resultType", "list");
            } else
            {
                throw new RuntimeException("\u4E0D\u652F\u6301\u8BE5\u6570\u636E\u7C7B\u578B");
            }
            if(action instanceof ITableBatch)
                data = ((ITableBatch)action).tableBatch(data, param, request);
            if(data == null)
                data = new ArrayList();
            result.put("data", data);
            Map parseredMap = parseredData(data, tableConfig, columnConfig, targetPage, request);
            if("true".equals(faster))
            {
                result.put("dataArray", parseredMap.get("dataArray"));
                result.put("dataLeftArray", parseredMap.get("dataLeftArray"));
            } else
            {
                result.put("htmlData", parseredMap.get("text"));
                result.put("html", parseredMap.get("html"));
                result.put("htmlleft", parseredMap.get("htmlleft"));
            }
            result.put("pageInfo", getPageInfo(tableConfig, Integer.valueOf(count), targetPage, realPageSize, request));
        }
        catch(Exception e)
        {
            result.put("error", (new StringBuilder()).append("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25\uFF1A").append(e.getMessage()).toString());
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
        return result;
    }

    private String getRealPageSize(Map tableConfig, HttpServletRequest request)
        throws Exception
    {
        String pageSize = (String)tableConfig.get("pageSize");
        if("true".equals(tableConfig.get("allowConfigPageSize")) && tableConfig.get("pageSize") != null && "normal".equals(tableConfig.get("pageInfoType")))
        {
            String userConfigClass = SpiritUserConfigFacory.getInstance(request).getClassName();
            ISpiritUserConfigAware userConfig = (ISpiritUserConfigAware)Class.forName(userConfigClass).newInstance();
            Integer _pageSize = userConfig.loadPageSize((String)tableConfig.get("id"), (String)tableConfig.get("actionClass"), request);
            if(_pageSize != null && _pageSize.intValue() > 0)
                pageSize = (new StringBuilder()).append("").append(_pageSize).toString();
        }
        return pageSize;
    }

    public Map initCollect(Map tableConfig, Map columnConfig[], Map collectConfig, Map param, HttpServletRequest request)
        throws Exception
    {
        Map result = new HashMap();
        String className = (String)tableConfig.get("actionClass");
        try
        {
            Map resultHtml = new HashMap();
            Map resultData = new HashMap();
            ITableAware action = (ITableAware)Class.forName(className).newInstance();
            Object o = action.getResult(param, request);
            if((o instanceof IMetaDBQuery) && ((IMetaDBQuery)o).getEntity() == null)
            {
                IMetaDBQuery tmp = (IMetaDBQuery)o;
                String queryString = "select ";
                Iterator keyIt = collectConfig.keySet().iterator();
                int count = 0;
                while(keyIt.hasNext()) 
                {
                    String info = (String)keyIt.next();
                    String property = info.split("__")[0];
                    int colNum = (new Integer(info.split("__")[1])).intValue();
                    String collectMethods[] = ((String)collectConfig.get(info)).split(",");
                    String arr$[] = collectMethods;
                    int len$ = arr$.length;
                    int i$ = 0;
                    while(i$ < len$) 
                    {
                        String collectMethod = arr$[i$];
                        if(++count != 1)
                            queryString = (new StringBuilder()).append(queryString).append(",").toString();
                        queryString = (new StringBuilder()).append(queryString).append(collectMethod).append("(").append(property).append(") as ").append(property).append("__").append(colNum).append("__").append(collectMethod).toString();
                        i$++;
                    }
                }
                queryString = (new StringBuilder()).append(queryString).append(" from (").append(tmp.getQueryString()).append(")").toString();
                IMetaDBQuery query = (new MetaDBTemplate()).createSqlQuery(queryString);
                query.setParameters(tmp.getParameters());
                Map collectResult = (Map)query.getResult().get(0);
                String method;
                List methodList;
                for(Iterator i$ = collectResult.keySet().iterator(); i$.hasNext(); resultHtml.put(method, methodList))
                {
                    String key = (String)i$.next();
                    String property = key.split("__")[0];
                    int colNum = (new Integer(key.split("__")[1])).intValue();
                    method = key.split("__")[2];
                    Map column = columnConfig[colNum];
                    column.put("property", key);
                    Map data = new HashMap();
                    data.put("property", property);
                    data.put("method", method);
                    data.put("colNum", Integer.valueOf(colNum));
                    resultData.put((new StringBuilder()).append(property).append("_").append(method).toString(), collectResult.get((new StringBuilder()).append(property).append("__").append(colNum).append("__").append(method).toString()));
                    String value = (String)TableUtils.translateVariables(collectResult, Collections.EMPTY_MAP, column, Integer.valueOf(9999), Integer.valueOf(colNum), null, null, request).get("text");
                    methodList = (List)resultHtml.get(method);
                    if(methodList == null)
                        methodList = new ArrayList();
                    Map html = new HashMap();
                    html.put("colNum", Integer.valueOf(colNum));
                    html.put("html", (new StringBuilder()).append("<td class=\"stk-table-td\" colNum='").append(colNum).append("' width='").append(column.get("width")).append("' align='").append(column.get("align")).append("'><b>").append(getCollectName(method)).append("\uFF1A").append(value).append("</b></td>").toString());
                    methodList.add(html);
                }

                result.put("html", resultHtml);
                result.put("data", resultData);
            } else
            {
                throw new Exception("\u8868\u683C\u63A7\u4EF6\u6C47\u603B\u53EA\u652F\u6301MetaDB Sql\u67E5\u8BE2");
            }
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
        return result;
    }

    public Object callCheckBoxPlugin(Map tableConfig, List data, Map param, HttpServletRequest request)
        throws Exception
    {
        String className = (String)tableConfig.get("actionClass");
        try
        {
            ICheckBoxPluginAware action = (ICheckBoxPluginAware)Class.forName(className).newInstance();
            return action.callCheckBoxPlugin(data, param, request);
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    public Object callRadioBoxPlugin(Map tableConfig, Map data, Map param, HttpServletRequest request)
        throws Exception
    {
        String className = (String)tableConfig.get("actionClass");
        try
        {
            IRadioBoxPluginAware action = (IRadioBoxPluginAware)Class.forName(className).newInstance();
            return action.callRadioBoxPlugin(data, param, request);
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    Map getPageInfo(Map tableConfig, Integer allRow, String targetPage, String configPageSize, HttpServletRequest request)
    {
        Map result = new HashMap();
        String pageSize = (String)tableConfig.get("pageSize");
        if("true".equals(tableConfig.get("allowConfigPageSize")) && pageSize != null && StringUtils.isNotBlank(configPageSize) && (new Integer(configPageSize)).intValue() > 0)
            pageSize = configPageSize;
        Integer allPage = TableUtils.getAllPage(pageSize, allRow);
        Integer itargetPage = TableUtils.getTargetPage((String)tableConfig.get("targetPage"), allPage);
        result.put("allCount", (new StringBuilder()).append("").append(allRow).toString());
        result.put("pageSize", TableUtils.getPageSize(pageSize));
        result.put("targetPage", targetPage);
        result.put("allPage", allPage);
        result.put("startRow", TableUtils.getStartRow(pageSize, itargetPage));
        result.put("endRow", TableUtils.getEndRow(pageSize, allRow, itargetPage));
        return result;
    }

    public String detail(Map tableConfig, Map columnConfig[], Map param, String id, HttpServletRequest request)
        throws Exception
    {
        StringBuilder sb = new StringBuilder("<table class=\"stk-form-ui-st1\">");
        String className = (String)tableConfig.get("actionClass");
        Map data = new HashMap();
        try
        {
            ITableAware action = (ITableAware)Class.forName(className).newInstance();
            Object o = action.getResult(param, request);
            if(o instanceof IMetaDBQuery)
            {
                IMetaDBQuery tmp = (IMetaDBQuery)o;
                if(tmp.getEntity() == null)
                {
                    String queryString = (new StringBuilder()).append("select * from(").append(tmp.getQueryString()).append(") where objid=:_objid").toString();
                    IMetaDBQuery query = (new MetaDBTemplate()).createSqlQuery(queryString);
                    Map _param = tmp.getParameters();
                    _param.put("_objid", id);
                    query.setParameters(_param);
                    List list = query.getResult();
                    if(list.size() == 1)
                        data = (Map)list.get(0);
                    else
                        data = null;
                } else
                {
                    com.sinitek.base.metadb.IEntity entity = tmp.getEntity();
                    Map tmpMap = (new MetaDBTemplate()).load(entity, (new Integer(id)).intValue());
                    Map map = new CaseInsensitiveMap();
                    map.putAll(tmpMap);
                    map.put("objid", Integer.valueOf(((IMetaObject)tmp).getId()));
                    map.put("createtimestamp", ((IMetaObject)tmp).getCreateTimestamp());
                    map.put("updatetimestamp", ((IMetaObject)tmp).getUpdateTimestamp());
                    data = map;
                }
            } else
            if(o instanceof List)
            {
                List tmp = (List)o;
                Iterator i$ = tmp.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    Map aTmp = (Map)i$.next();
                    String objid = null;
                    if(aTmp.get("objid") != null)
                        objid = aTmp.get("objid").toString();
                    if(id.equals(objid))
                        data = aTmp;
                } while(true);
            }
            int k = 0;
            for(int i = 0; i < columnConfig.length; i++)
            {
                Map acolumnConfig = columnConfig[i];
                Map requestMap = new CaseInsensitiveMap();
                if(!"false".equals(acolumnConfig.get("allowDetail")))
                {
                    requestMap.putAll(data);
                    if(!"false".equals(acolumnConfig.get("show")) && !"false".equals(acolumnConfig.get("allowDetail")) && allowGen(acolumnConfig))
                    {
                        if(k % 2 == 0)
                            sb.append("<tr>");
                        try
                        {
                            Map dataMap = TableUtils.translateVariables(requestMap, tableConfig, acolumnConfig, Integer.valueOf(0), Integer.valueOf(i), data, null, request);
                            String text = (String)dataMap.get("text");
                            sb.append("<td class=\"hd\" width='100px'>");
                            sb.append((String)acolumnConfig.get("title"));
                            sb.append("</td>");
                            sb.append("<td class=\"bd\" width='200px'>");
                            if("link,".equals(acolumnConfig.get("pluginType")))
                            {
                                text = text.replaceAll("</a>", "");
                                int x = text.indexOf(">");
                                text = text.substring(x + 1);
                            }
                            sb.append(text);
                            sb.append("</td>");
                        }
                        catch(Exception e)
                        {
                            LOG.error("\u8868\u683C\u8BE6\u7EC6\u751F\u6210\u5931\u8D25", e);
                            throw new Exception((new StringBuilder()).append("\u8868\u683C\u8BE6\u7EC6\u751F\u6210\u5931\u8D25:").append(e.getMessage()).toString(), e);
                        }
                        if(k % 2 == 1)
                            sb.append("</tr>");
                        k++;
                    }
                }
            }

        }
        catch(Exception e)
        {
            LOG.error("\u5BFC\u51FA\u5931\u8D25", e);
            throw e;
        }
        sb.append("</table>");
        return sb.toString();
    }

    private void setMetadbOrderBy(Map tableConfig, Map param, String sortInfo, ITableAware action, IMetaDBQuery query, HttpServletRequest request)
        throws Exception
    {
        String allowDuplicate = (String)tableConfig.get("allowDuplicate");
        String extraOrderBy = (String)tableConfig.get("extraOrderBy");
        String tmpOrderby = TableUtils.getOrderBy(action.setDefaultOrderBy(param, request), sortInfo);
        if("false".equals(allowDuplicate))
            if(StringUtils.isBlank(tmpOrderby))
                tmpOrderby = "rownum";
            else
                tmpOrderby = (new StringBuilder()).append(tmpOrderby).append(",rownum").toString();
        if(StringUtils.isNotBlank(extraOrderBy))
            if(StringUtils.isBlank(tmpOrderby))
                tmpOrderby = extraOrderBy;
            else
                tmpOrderby = (new StringBuilder()).append(tmpOrderby).append(",").append(extraOrderBy).toString();
        query.setOrderBy(tmpOrderby);
    }

    public void exportExcel(Map tableConfig, Map columnConfig[], Map param, String sortInfo, String fileName, String ids[], HttpServletRequest request)
        throws Exception
    {
        String className;
        WritableWorkbook workbook;
        className = (String)tableConfig.get("actionClass");
        workbook = null;
        try
        {
            ITableAware action = (ITableAware)Class.forName(className).newInstance();
            Object o = action.getResult(param, request);
            List list = new ArrayList();
            List tmp = Collections.EMPTY_LIST;
            if(o instanceof IMetaDBQuery)
            {
                IMetaDBQuery query = (IMetaDBQuery)o;
                setMetadbOrderBy(tableConfig, param, sortInfo, action, query, request);
                AbsTableResult strategy;
                if(query.getEntity() != null)
                    strategy = new TableResultMetaDBHql();
                else
                    strategy = new TableResultMetaDBSql();
                tmp = strategy.getResultList(o, tableConfig);
            } else
            if(o instanceof List)
                tmp = (List)o;
            if(ids != null && ids.length > 0)
            {
                for(Iterator i$ = tmp.iterator(); i$.hasNext();)
                {
                    Object aTmp = i$.next();
                    Map data = (Map)aTmp;
                    String arr$[] = ids;
                    int len$ = arr$.length;
                    int i$ = 0;
                    while(i$ < len$) 
                    {
                        String id = arr$[i$];
                        if(id.equals((new StringBuilder()).append("").append(data.get("objid")).toString()))
                            list.add(data);
                        i$++;
                    }
                }

            } else
            {
                list = tmp;
            }
            if(action instanceof ITableBatch)
                list = ((ITableBatch)action).tableBatch(list, param, request);
            File dir = new File(request.getSession().getServletContext().getRealPath("/spirittemp/"));
            if(!dir.exists())
            {
                dir.mkdirs();
            } else
            {
                File delFile[] = dir.listFiles();
                try
                {
                    File arr$[] = delFile;
                    int len$ = arr$.length;
                    for(int i$ = 0; i$ < len$; i$++)
                    {
                        File file = arr$[i$];
                        if(file.lastModified() + 0x36ee80L < System.currentTimeMillis())
                            file.delete();
                    }

                }
                catch(Exception ex) { }
            }
            String filenamedownload = (new StringBuilder()).append(request.getSession().getServletContext().getRealPath("/spirittemp/")).append(File.separator).append(fileName).toString();
            File file = new File(filenamedownload);
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("\u6570\u636E", 0);
            WritableFont font1 = new WritableFont(WritableFont.createFont("\u9ED1\u4F53"), 12, WritableFont.BOLD);
            WritableFont font2 = new WritableFont(WritableFont.createFont("\u5B8B\u4F53"), 12);
            WritableFont font3 = new WritableFont(WritableFont.createFont("\u9ED1\u4F53"), 16);
            WritableFont font4 = new WritableFont(WritableFont.createFont("\u9ED1\u4F53"), 12, WritableFont.BOLD);
            WritableFont font5 = new WritableFont(WritableFont.createFont("\u5B8B\u4F53"), 12);
            WritableCellFormat format1 = new WritableCellFormat(font1);
            WritableCellFormat format2 = new WritableCellFormat(font2);
            WritableCellFormat format3 = new WritableCellFormat(font3);
            format1.setAlignment(Alignment.CENTRE);
            format3.setAlignment(Alignment.CENTRE);
            WritableCellFormat format4 = new WritableCellFormat(font4);
            format4.setAlignment(Alignment.RIGHT);
            WritableCellFormat format5 = new WritableCellFormat(font5);
            format5.setAlignment(Alignment.RIGHT);
            format1.setWrap(true);
            format1.setVerticalAlignment(VerticalAlignment.CENTRE);
            format2.setWrap(true);
            format2.setVerticalAlignment(VerticalAlignment.CENTRE);
            format3.setWrap(true);
            format3.setVerticalAlignment(VerticalAlignment.CENTRE);
            format4.setWrap(true);
            format4.setVerticalAlignment(VerticalAlignment.CENTRE);
            format5.setWrap(true);
            format5.setVerticalAlignment(VerticalAlignment.CENTRE);
            int currentRow = 0;
            List groupTopInfo = new ArrayList();
            int i = 0;
            do
            {
                if(i >= columnConfig.length)
                    break;
                Map map = columnConfig[i];
                String show = (String)map.get("show");
                if(!"false".equals(show) && !"false".equals(map.get("allowExport")) && allowGen(map) && StringUtils.isNotBlank((String)map.get("groupTop")))
                {
                    currentRow++;
                    for(int j = 0; j < columnConfig.length; j++)
                    {
                        Map map2 = columnConfig[j];
                        String show2 = (String)map2.get("show");
                        if("false".equals(show2) || "false".equals(map2.get("allowExport")) || !allowGen(map2))
                            continue;
                        String group = (String)map2.get("groupTop");
                        if(StringUtils.isBlank(group))
                            group = "";
                        if(groupTopInfo.size() == 0)
                        {
                            groupTopInfo.add((new StringBuilder()).append(group).append("_").append(1).toString());
                            continue;
                        }
                        String last = (String)groupTopInfo.get(groupTopInfo.size() - 1);
                        String array[] = last.split("_");
                        String _group;
                        String _count;
                        if(array.length == 1)
                        {
                            _group = "";
                            _count = array[0];
                        } else
                        {
                            _group = array[0];
                            _count = array[1];
                        }
                        if(_group.equals(group))
                            groupTopInfo.set(groupTopInfo.size() - 1, (new StringBuilder()).append(group).append("_").append((new Integer(_count)).intValue() + 1).toString());
                        else
                            groupTopInfo.add((new StringBuilder()).append(group).append("_").append(1).toString());
                    }

                    int start = 0;
                    for(Iterator i$ = groupTopInfo.iterator(); i$.hasNext();)
                    {
                        String info = (String)i$.next();
                        String array[] = info.split("_");
                        String _group;
                        String _count;
                        if(array.length == 1)
                        {
                            _group = "";
                            _count = array[0];
                        } else
                        {
                            _group = array[0];
                            _count = array[1];
                        }
                        int end = (start + (new Integer(_count)).intValue()) - 1;
                        sheet.mergeCells(start, currentRow - 1, end, currentRow - 1);
                        sheet.addCell(new Label(start, currentRow - 1, _group, format1));
                        start += (new Integer(_count)).intValue();
                    }

                    break;
                }
                i++;
            } while(true);
            List groupInfo = new ArrayList();
            int i = 0;
            do
            {
                if(i >= columnConfig.length)
                    break;
                Map map = columnConfig[i];
                String show = (String)map.get("show");
                if(!"false".equals(show) && !"false".equals(map.get("allowExport")) && allowGen(map) && StringUtils.isNotBlank((String)map.get("group")))
                {
                    currentRow++;
                    for(int j = 0; j < columnConfig.length; j++)
                    {
                        Map map2 = columnConfig[j];
                        String show2 = (String)map2.get("show");
                        if("false".equals(show2) || "false".equals(map2.get("allowExport")) || !allowGen(map2))
                            continue;
                        String group = (String)map2.get("group");
                        if(StringUtils.isBlank(group))
                            group = "";
                        if(groupInfo.size() == 0)
                        {
                            groupInfo.add((new StringBuilder()).append(group).append("_").append(1).toString());
                            continue;
                        }
                        String last = (String)groupInfo.get(groupInfo.size() - 1);
                        String array[] = last.split("_");
                        String _group;
                        String _count;
                        if(array.length == 1)
                        {
                            _group = "";
                            _count = array[0];
                        } else
                        {
                            _group = array[0];
                            _count = array[1];
                        }
                        if(_group.equals(group))
                            groupInfo.set(groupInfo.size() - 1, (new StringBuilder()).append(group).append("_").append((new Integer(_count)).intValue() + 1).toString());
                        else
                            groupInfo.add((new StringBuilder()).append(group).append("_").append(1).toString());
                    }

                    int start = 0;
                    for(Iterator i$ = groupInfo.iterator(); i$.hasNext();)
                    {
                        String info = (String)i$.next();
                        String array[] = info.split("_");
                        String _group;
                        String _count;
                        if(array.length == 1)
                        {
                            _group = "";
                            _count = array[0];
                        } else
                        {
                            _group = array[0];
                            _count = array[1];
                        }
                        int end = (start + (new Integer(_count)).intValue()) - 1;
                        sheet.mergeCells(start, currentRow - 1, end, currentRow - 1);
                        sheet.addCell(new Label(start, currentRow - 1, _group, format1));
                        start += (new Integer(_count)).intValue();
                    }

                    break;
                }
                i++;
            } while(true);
            Map mergeColMap = new HashMap();
            int col = 0;
            for(int i = 0; i < columnConfig.length; i++)
            {
                Map map = columnConfig[i];
                String xlsColWidth = (String)map.get("xlsColWidth");
                String show = (String)map.get("show");
                if("false".equals(show) || "false".equals(map.get("allowExport")) || !allowGen(map))
                    continue;
                boolean mergeCol = "true".equals(map.get("mergeCol"));
                if(mergeCol)
                    mergeColMap.put(Integer.valueOf(col), map);
                if(!StringUtils.isNotBlank((String)map.get("title")))
                    continue;
                int iXlsColWidth = 25;
                if(xlsColWidth != null)
                    iXlsColWidth = (new Integer(xlsColWidth)).intValue();
                String title = (String)map.get("title");
                title = FormatUtils.htmlToText(title);
                sheet.setColumnView(col, iXlsColWidth);
                sheet.addCell(new Label(col++, currentRow, title, format1));
            }

            String exportMaxRow = (String)tableConfig.get("exportMaxRow");
            int iExportMaxRow = 0;
            if(StringUtils.isNotBlank(exportMaxRow))
                iExportMaxRow = (new Integer(exportMaxRow)).intValue();
            int j = 0;
            do
            {
                if(j >= list.size())
                    break;
                if(iExportMaxRow > 0 && j == iExportMaxRow)
                {
                    sheet.addCell(new Label(0, currentRow + j + 1, (new StringBuilder()).append("\u6700\u591A\u663E\u793A").append(exportMaxRow).append("\u6761\uFF0C\u5269\u4E0B\u90E8\u5206\u7701\u7565...").toString(), format1));
                    break;
                }
                col = 0;
                Map data = (Map)list.get(j);
                for(int i = 0; i < columnConfig.length; i++)
                {
                    Map map = columnConfig[i];
                    Map requestMap = new CaseInsensitiveMap();
                    requestMap.putAll(data);
                    String show = (String)map.get("show");
                    if("false".equals(show) || "false".equals(map.get("allowExport")) || !allowGen(map) || !StringUtils.isNotBlank((String)map.get("title")))
                        continue;
                    try
                    {
                        WritableCellFormat f;
                        if("number".equals(map.get("colType")))
                        {
                            f = format5;
                        } else
                        {
                            f = new WritableCellFormat(font2);
                            f.setVerticalAlignment(VerticalAlignment.CENTRE);
                            String align = (String)map.get("align");
                            if("center".equals(align))
                                f.setAlignment(Alignment.CENTRE);
                            else
                            if("left".equals(align))
                                f.setAlignment(Alignment.LEFT);
                            else
                            if("right".equals(align))
                                f.setAlignment(Alignment.RIGHT);
                            f.setWrap(true);
                        }
                        String text = (String)TableUtils.translateVariables(requestMap, tableConfig, map, Integer.valueOf(j), Integer.valueOf(i), data, Integer.valueOf(j), request).get("text");
                        String property = (String)map.get("property");
                        Object orgData = null;
                        if(property != null)
                            orgData = data.get(property.toLowerCase());
                        if("true".equals(map.get("filter")))
                        {
                            sheet.addCell(new Label(col++, currentRow + j + 1, text.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<br />", "\n"), f));
                            continue;
                        }
                        if("link,".equals(map.get("pluginType")))
                        {
                            text = text.replaceAll("</a>", "");
                            int x = text.indexOf(">");
                            text = text.substring(x + 1);
                            sheet.addCell(new Label(col++, currentRow + j + 1, text, f));
                            continue;
                        }
                        if("number".equals(map.get("colType")) && orgData != null)
                        {
                            if(orgData instanceof BigDecimal)
                            {
                                sheet.addCell(new Number(col++, currentRow + j + 1, ((BigDecimal)orgData).doubleValue(), f));
                                continue;
                            }
                            if(orgData instanceof Number)
                                sheet.addCell(new Number(col++, currentRow + j + 1, ((Number)orgData).doubleValue(), f));
                            else
                                sheet.addCell(new Label(col++, currentRow + j + 1, text, f));
                        } else
                        {
                            sheet.addCell(new Label(col++, currentRow + j + 1, text, f));
                        }
                        continue;
                    }
                    catch(Exception e)
                    {
                        LOG.error("\u5BFC\u51FA\u5931\u8D25", e);
                        throw new Exception((new StringBuilder()).append("\u5BFC\u51FA\u5931\u8D25:").append(e.getMessage()).toString(), e);
                    }
                }

                j++;
            } while(true);
            for(Iterator i$ = mergeColMap.keySet().iterator(); i$.hasNext();)
            {
                Integer columnNum = (Integer)i$.next();
                Map map = (Map)mergeColMap.get(columnNum);
                String property = (String)map.get("property");
                int startRow = currentRow + 1;
                int endRow = currentRow + 1;
                Object currentData = null;
                int j = 0;
                while(j < list.size()) 
                {
                    Map data = (Map)list.get(j);
                    if(j == 0)
                    {
                        startRow = currentRow + 1;
                        endRow = currentRow + 1;
                        currentData = data.get(property);
                    } else
                    if(currentData != null && currentData.equals(data.get(property)))
                    {
                        endRow++;
                        if(j == list.size() - 1 && startRow != endRow)
                            sheet.mergeCells(columnNum.intValue(), startRow, columnNum.intValue(), endRow);
                    } else
                    {
                        if(startRow != endRow)
                            sheet.mergeCells(columnNum.intValue(), startRow, columnNum.intValue(), endRow);
                        startRow = currentRow + 1 + j;
                        endRow = startRow;
                        currentData = data.get(property);
                    }
                    j++;
                }
            }

            workbook.write();
        }
        catch(Exception e)
        {
            LOG.error("\u5BFC\u51FA\u5931\u8D25", e);
            throw e;
        }
        if(workbook != null)
            workbook.close();
        break MISSING_BLOCK_LABEL_3261;
        Exception exception;
        exception;
        if(workbook != null)
            workbook.close();
        throw exception;
    }

    private boolean allowGen(Map columConfig)
    {
        return StringUtils.isBlank((String)columConfig.get("pluginType")) || "link,".equals(columConfig.get("pluginType"));
    }

    private String getCollectName(String s)
    {
        String result;
        if("AVG".equalsIgnoreCase(s))
            result = "\u5E73\u5747";
        else
        if("COUNT".equalsIgnoreCase(s))
            result = "\u603B\u6570";
        else
        if("MAX".equalsIgnoreCase(s))
            result = "\u6700\u5927";
        else
        if("SUM".equalsIgnoreCase(s))
            result = "\u5408\u8BA1";
        else
        if("MIN".equalsIgnoreCase(s))
            result = "\u6700\u5C0F";
        else
            result = s;
        return result;
    }

    private boolean isFixTable(Map tableConfig)
    {
        return "tablefix".equals(tableConfig.get("cn"));
    }

    private Map parseredData(List data, Map tableConfig, Map columnConfig[], String targetPage, HttpServletRequest request)
        throws Exception
    {
        StringBuilder sbAll = new StringBuilder();
        StringBuilder sb_leftAll = new StringBuilder();
        List textList = new ArrayList();
        List dataArray = new ArrayList();
        List dataLeftArray = new ArrayList();
        String realPageSize = getRealPageSize(tableConfig, request);
        for(int j = 0; j < data.size(); j++)
        {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb_left = new StringBuilder();
            Map textData = new CaseInsensitiveMap();
            sb.append("<tr rowNum='").append(j).append("'");
            sb_left.append("<tr rowNum='").append(j).append("'");
            if(data.get(j) instanceof IMetaObject)
            {
                sb.append(" objid='");
                sb.append(((IMetaObject)data.get(j)).getId());
                sb.append("' ");
                sb_left.append(" objid='");
                sb_left.append(((IMetaObject)data.get(j)).getId());
                sb_left.append("' ");
            } else
            if((data.get(j) instanceof Map) && ((Map)data.get(j)).get("objid") != null)
            {
                sb.append(" objid='");
                sb.append(((Map)data.get(j)).get("objid"));
                sb.append("' ");
                sb_left.append(" objid='");
                sb_left.append(((Map)data.get(j)).get("objid"));
                sb_left.append("' ");
            }
            if(j % 2 != 0)
            {
                sb.append(" class='stk-table-tr-even'");
                sb_left.append(" class='stk-table-tr-even'");
            }
            sb.append(">");
            sb_left.append(">");
            for(int i = 0; i < columnConfig.length; i++)
            {
                Map acolumnConfig = columnConfig[i];
                Map requestMap = new CaseInsensitiveMap();
                if(!(data.get(j) instanceof Map))
                    data.set(j, FormatUtils.beanToMap(data.get(j)));
                requestMap.putAll((Map)data.get(j));
                if(data.get(j) instanceof IMetaObject)
                {
                    requestMap.put("objid", Integer.valueOf(((IMetaObject)data.get(j)).getId()));
                    requestMap.put("createtimestamp", ((IMetaObject)data.get(j)).getCreateTimestamp());
                    requestMap.put("updatetimestamp", ((IMetaObject)data.get(j)).getUpdateTimestamp());
                } else
                {
                    ((Map)data.get(j)).put("rowNum", Integer.valueOf(j));
                }
                try
                {
                    Map dataMap = TableUtils.translateVariables(requestMap, tableConfig, acolumnConfig, Integer.valueOf(j), Integer.valueOf(i), data.get(j), TableUtils.getRealRow(Integer.valueOf(j), realPageSize, targetPage), request);
                    if(isFixTable(tableConfig))
                    {
                        if("true".equals(acolumnConfig.get("fixed")))
                            sb_left.append(dataMap.get("html"));
                        else
                            sb.append(dataMap.get("html"));
                    } else
                    {
                        sb.append(dataMap.get("html"));
                    }
                    String property = (String)acolumnConfig.get("property");
                    if(StringUtils.isNotBlank(property))
                        textData.put(property, dataMap.get("text"));
                    else
                        textData.put((new StringBuilder()).append("").append(i).toString(), dataMap.get("text"));
                    continue;
                }
                catch(Exception e)
                {
                    LOG.error("\u8868\u683C\u63A7\u4EF6\u751F\u6210\u5931\u8D25", e);
                    throw new Exception((new StringBuilder()).append("\u8868\u683C\u63A7\u4EF6\u751F\u6210\u5931\u8D25:").append(e.getMessage()).toString(), e);
                }
            }

            sb.append("</tr>");
            sb_left.append("</tr>");
            textList.add(textData);
            sb_leftAll.append(sb_left);
            sbAll.append(sb);
            dataArray.add(sb.toString());
            dataLeftArray.add(sb_left.toString());
        }

        if("true".equals(tableConfig.get("allowBlankRow")))
        {
            Integer pageSize = TableUtils.getPageSize((String)tableConfig.get("pageSize"));
            for(int i = 0; i < pageSize.intValue() - data.size(); i++)
            {
                sbAll.append("<tr>");
                sb_leftAll.append("<tr>");
                Map arr$[] = columnConfig;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    Map acolumnConfig = arr$[i$];
                    if("true".equals(acolumnConfig.get("fixed")))
                        sb_leftAll.append("<td width='").append(acolumnConfig.get("width")).append("' colNum='").append(acolumnConfig.get("colNum")).append("' class=\"stk-table-tdline\" style='height:").append((String)tableConfig.get("rowHeight")).append("'>&nbsp;</td>");
                    else
                        sbAll.append("<td width='").append(acolumnConfig.get("width")).append("' colNum='").append(acolumnConfig.get("colNum")).append("' class=\"stk-table-tdline\" style='height:").append((String)tableConfig.get("rowHeight")).append("'>&nbsp;</td>");
                }

                sbAll.append("</tr>");
                sb_leftAll.append("</tr>");
            }

        }
        Map result = new HashMap();
        result.put("text", textList);
        result.put("html", sbAll.toString());
        result.put("htmlleft", sb_leftAll.toString());
        result.put("dataArray", dataArray);
        result.put("dataLeftArray", dataLeftArray);
        return result;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/table/SpiritTableAction);

}
