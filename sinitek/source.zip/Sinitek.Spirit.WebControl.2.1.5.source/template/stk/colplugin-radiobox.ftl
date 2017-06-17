<div<#rt/>
<#include "inc-colplugin-property.ftl" />
<#if parameters.name?if_exists != ""> pluginParam="${parameters.name}"</#if><#rt/>
>
<script type="text/javascript">
    function ${parameters.id}_callTablePlugin(param,callBack)
    {
        var tableId = $("th[plugin='${parameters.id}']").attr("tableId");
        var config = stk.table.config[tableId];
        var data = stk.table.getRadioData(config,"${parameters.name}");
        SpiritTableAction.callRadioBoxPlugin(config.tableConfig,data,param, {
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