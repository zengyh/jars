
<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#if parameters.type?if_exists != ""> type="${parameters.type?html}"</#if><#rt/>
<#if parameters.queryForm?if_exists != ""> queryForm="${parameters.queryForm?html}"</#if><#rt/>
<#if parameters.mode?if_exists != ""> mode="${parameters.mode?html}"</#if><#rt/>
<#if parameters.pageSize?exists> pageSize="${parameters.pageSize?html}"</#if><#rt/>
<#if parameters.actionClass?if_exists != ""> actionClass="${parameters.actionClass?html}"</#if><#rt/>
<#if parameters.rowHeight?if_exists != ""> rowHeight="${parameters.rowHeight?html}"</#if><#rt/>
<#if parameters.tableWidth?if_exists != ""> tableWidth="${parameters.tableWidth?html}"</#if><#rt/>
<#if parameters.width?if_exists != ""> _width="${parameters.width?html}"</#if><#rt/>
<#if parameters.height?if_exists != ""> _height="${parameters.height?html}"</#if><#rt/>
<#if parameters.allowInit?if_exists != ""> allowInit="${parameters.allowInit?html}"</#if><#rt/>
<#if parameters.personal?if_exists != ""> personal="${parameters.personal?html}"</#if><#rt/>
<#if parameters.allowDetail?if_exists != ""> allowDetail="${parameters.allowDetail?html}"</#if><#rt/>
<#if parameters.allowColumnOrd?if_exists != ""> allowColumnOrd="${parameters.allowColumnOrd?html}"</#if><#rt/>
<#if parameters.allowCollect?if_exists != ""> allowCollect="${parameters.allowCollect?html}"</#if><#rt/>
<#if parameters.allowExport?if_exists != ""> allowExport="${parameters.allowExport?html}"</#if><#rt/>
<#if parameters.exportOption?if_exists != ""> exportOption="${parameters.exportOption?html}"</#if><#rt/>
<#if parameters.title?if_exists != ""> _title="${parameters.title?html}"</#if><#rt/>
<#if parameters.sortType?if_exists != ""> sortType="${parameters.sortType?html}"</#if><#rt/>
<#if parameters.pageInfoType?if_exists != ""> pageInfoType="${parameters.pageInfoType?html}"</#if><#rt/>
<#if parameters.allowBlankRow?if_exists != ""> allowBlankRow="${parameters.allowBlankRow?html}"</#if><#rt/>
<#if parameters.blankText?if_exists != ""> blankText="${parameters.blankText?html}"</#if><#rt/>
<#if parameters.allowConfigPageSize?if_exists != ""> allowConfigPageSize="${parameters.allowConfigPageSize?html}"</#if><#rt/>
<#if parameters.exportFileName?if_exists != ""> exportFileName="${parameters.exportFileName?html}"</#if><#rt/>
<#if parameters.exportMaxRow?if_exists != ""> exportMaxRow="${parameters.exportMaxRow?html}"</#if><#rt/>
<#if parameters.subList?if_exists != ""> subList="${parameters.subList?html}"</#if><#rt/>
<#if parameters.allowDuplicate?if_exists != ""> allowDuplicate="${parameters.allowDuplicate?html}"</#if><#rt/>
<#if parameters.extraOrderBy?if_exists != ""> extraOrderBy="${parameters.extraOrderBy?html}"</#if><#rt/>
<#if parameters.adjustRow?if_exists != ""> adjustRow="${parameters.adjustRow?html}"</#if><#rt/>
<#if parameters.faster?if_exists != ""> faster="${parameters.faster?html}"</#if><#rt/>
 style="<#if parameters.width?if_exists != "">width:${parameters.width};
<#include "inc_component_style.ftl" />"</#if>
>
<#if parameters.title?if_exists != ""><div id="${parameters.id?html}_title">${parameters.title?html}</div></#if><#rt/>
<div class="stk-ui-bar stk-table-opt-bar" style="display:none;"><ul id="${parameters.id?html}_topBar" class="btn-box"></ul></div><#rt/>
<div style="border-style:solid;border-bottom-width:1px; border-color:#bbb;clear:both;overflow:hidden;width:100%;<#if parameters.height?if_exists != "" && parameters.componentName == "table">height:${parameters.height?html}</#if>" id="${parameters.id?html}_grid" class="stk-ui-table-scroll">