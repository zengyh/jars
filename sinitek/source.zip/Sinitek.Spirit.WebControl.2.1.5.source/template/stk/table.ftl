<#include "inc_table.ftl" />
    <table class="stk-table" id="${parameters.id?html}"<#rt/>
<#if parameters.name?if_exists != ""> name="${parameters.name?html}"</#if><#rt/>
<#if parameters.tableWidth?if_exists != ""> width="${parameters.tableWidth}"</#if><#rt/>
<#if !(parameters.tableWidth?exists)> width="100%"</#if><#rt/>
 style="min-height:30px;table-layout:${parameters.mode?html};<#if parameters.allowHead=="false">border-top:1px solid #bbb;</#if><#rt/>
">
    <thead<#if parameters.allowHead=="false"> style="display:none;"</#if>><tr>