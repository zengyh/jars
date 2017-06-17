<div<#rt/>
<#include "inc-colplugin-property.ftl" />
<#if parameters.allowCache?if_exists != ""> pluginParam3="${parameters.allowCache}"</#if><#rt/>
<#if parameters.name?if_exists != ""> pluginParam="${parameters.name}"</#if><#rt/>
<#if parameters.selectAll?if_exists != ""> pluginParam2="${parameters.selectAll}"</#if><#rt/>
>
<script type="text/javascript">
    function ${parameters.id}_callTablePlugin(param,callBack)
    {
        var tableId = $("th[plugin='${parameters.id}']").attr("tableId");
        var config = stk.table.config[tableId];
        var allowCache = ${parameters.allowCache};
        var data = stk.table.getCheckedData(config,"${parameters.name}",allowCache);
        SpiritTableAction.callCheckBoxPlugin(config.tableConfig,data,param, {
            callback:function(result)
            {
                if(typeof(callBack) == 'function') callBack(result);
            },
            exceptionHandler:function(msg)
            {
                stk.alert("error£º"+msg);
            }
        });
    }
    function ${parameters.id}_plugin_click(${parameters.dataVar},${parameters.checkedVar})
    {
        <#if parameters.selectAll == "allowCache"><#rt/>
        if(${parameters.checkedVar} == true)
            stk.cache.pluginCheckbox.add("${parameters.name}",${parameters.dataVar}.objid)
        else
        </#if>