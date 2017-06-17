 class="stk-table-th" align="center" <#if parameters.colNum?if_exists != "">colNum="${parameters.colNum}"</#if><#rt/>
<#if parameters.colType?if_exists != ""> colType="${parameters.colType}"</#if><#rt/>
<#if parameters.property?if_exists != ""> property="${parameters.property}"</#if><#rt/>
<#if parameters.width?if_exists != ""> width="${parameters.width}"</#if><#rt/>
<#if parameters.xlsColWidth?if_exists != ""> xlsColWidth="${parameters.xlsColWidth}"</#if><#rt/>
<#if parameters.align?if_exists != ""> _align="${parameters.align}"</#if><#rt/>
<#if parameters.valign?if_exists != ""> _valign="${parameters.valign}"</#if><#rt/>
<#if parameters.expr?if_exists != ""> expr="${parameters.expr}"</#if><#rt/>
<#if parameters.maxWords?if_exists != ""> maxWords="${parameters.maxWords}"</#if><#rt/>
<#if parameters.fixed?if_exists != ""> fixed="${parameters.fixed}"</#if><#rt/>
<#if parameters.show?if_exists != ""> show="${parameters.show}"</#if><#rt/>
<#if parameters.filter?if_exists != ""> filter="${parameters.filter}"</#if><#rt/>
<#if parameters.collect?if_exists != ""> collect="${parameters.collect}"</#if><#rt/>
<#if parameters.plugin?if_exists != ""> plugin="${parameters.plugin}"</#if><#rt/>
<#if parameters.id?if_exists != ""> tableId="${parameters.parentId}"</#if><#rt/>
<#if parameters.group?if_exists != ""> group="${parameters.group}"</#if><#rt/>
<#if parameters.groupTop?if_exists != ""> groupTop="${parameters.groupTop}"</#if><#rt/>
<#if parameters.sortType?if_exists != ""> sortType="${parameters.sortType}"</#if><#rt/>
<#if parameters.nowrap?if_exists != ""> _nowrap="${parameters.nowrap}"</#if><#rt/>
<#if parameters.allowTip?if_exists != ""> allowTip="${parameters.allowTip}"</#if><#rt/>
<#if parameters.allowExport?if_exists != ""> allowExport="${parameters.allowExport}"</#if><#rt/>
<#if parameters.allowDetail?if_exists != ""> allowDetail="${parameters.allowDetail}"</#if><#rt/>
<#if parameters.helpContent?if_exists != ""> helpContent="${parameters.helpContent}"</#if><#rt/>
<#if parameters.mergeCol?if_exists != ""> mergeCol="${parameters.mergeCol}"</#if><#rt/>
<#if parameters.title?if_exists != ""> _title="${parameters.title}"</#if><#rt/>
<#if parameters.property?exists && parameters.allowSort?if_exists =='true' && parameters.sortType?if_exists !='none'>
 onclick="${parameters.parentId}_setSort(this);" style="white-space:nowrap;overflow:hidden;cursor:pointer;<#if parameters.width?if_exists != "">width:${parameters.width};</#if>"
<#else>
 style="white-space:nowrap;overflow:hidden;<#if parameters.width?if_exists != "">width:${parameters.width};</#if>"
</#if><#rt/>