
<div <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
<#include "inc_field_wrap.ftl" />
 style="display:inline-block;*display:inline;*zoom:1;<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html}</#if><#rt/>
<#include "inc_component_style.ftl" />"<#rt/>
>
    <div class="stk-person-select"<#rt/> style="
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};min-height:25px;</#if><#rt/>
"
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
></div>
<script type="text/javascript">
    <#if parameters.removeFunction?if_exists != "">
    var ${parameters.id}_removeFunction = function(para1) {
        ${parameters.removeFunction}(para1)
    };
    </#if>
    var ${parameters.id?html}_data = {
    <@s.iterator value="parameters.list">
            <#if parameters.listKey?if_exists != "">
                <#assign itemKey = stack.findValue(parameters.listKey)/>
            <#else>
                <#assign itemKey = stack.findValue('top')/>
            </#if>
            <#assign itemKeyStr = itemKey.toString() />
            <#if parameters.listValue?if_exists != "">
                <#assign itemValue = stack.findString(parameters.listValue)/>
            <#else>
                <#assign itemValue = stack.findString('top')/>
            </#if>
    "${itemKeyStr?html}":"${itemValue?html}",
    </@s.iterator>
    undefined:undefined
    };
    var ${parameters.id}_hasInit;
    if(${parameters.id}_hasInit == undefined)
    {
        $("#${parameters.id?html}").stk_addOptions(${parameters.id?html}_data);
        ${parameters.id}_hasInit = true;
    }
</script>
