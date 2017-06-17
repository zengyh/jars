
<div <#rt/>
<#include "inc_component_wrap.ftl" />
 style="position:relative;<#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
    <div id="${parameters.id}" style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>
"<#rt/>></div>
<script type="text/javascript">
    var _${parameters.id};
    function ${parameters.id}_init(callback)
    {
        var options = {
            <#if parameters.clazz?if_exists != "">clazz:'${parameters.clazz}'</#if>
        };
        var param = {};
    <#if parameters.jsParam?if_exists != "">
        if (typeof(${parameters.jsParam}) == "function")
            param = ${parameters.jsParam}();
    </#if>
        options.id = "${parameters.id}";
    <#if parameters.name?if_exists != "">
        options.name = "${parameters.name}";
    </#if>
        var waitLeft = $("#${parameters.id}").width()/2;
        var waitTop = $("#${parameters.id}").height()/2;
        $("#${parameters.id}").append("<img style='margin-top:"+waitTop+"px;margin-left:"+waitLeft+"px;' src='"+__contextPath+"/framework/css/common/images/icon/wait.gif\'>");
        <#if parameters.clazz?if_exists != "">
        SpiritChartAction.getChartResult(options, param, {
            callback:function(${parameters.dataVar}) {</#if>
                _${parameters.id} = new Highcharts.<#if parameters.type == "stock">Stock</#if>Chart({

