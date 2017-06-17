
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_select_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
>
    <select<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")?if_exists != "">
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?if_exists != "">
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id?if_exists != "">
 id="${parameters.id?html}"<#rt/>
</#if>
<#if !parameters.multiple?default(false)>
 class="stk-select-new"<#rt/>
</#if>
 style="<#rt/>
<#if parameters.cssStyle?if_exists != "">
 "${parameters.cssStyle?html};<#rt/></#if>
 <#if parameters.width?if_exists != "">
 width:${parameters.width?html};<#rt/></#if>
"<#rt/>
<#if parameters.title?if_exists != "">
 title="${parameters.title?html}"<#rt/>
</#if>
<#if parameters.multiple?default(false)>
 multiple="multiple"<#rt/>
</#if>
>
<#if parameters.headerKey?exists && parameters.headerValue?if_exists != "">
    <option value="${parameters.headerKey?html}"
    <#if tag.contains(parameters.nameValue, parameters.headerKey) == true>
    selected="selected"
    </#if>
    >${parameters.headerValue?html}</option>
</#if>
<#if parameters.emptyOption?default(false)>
    <option value=""></option>
</#if>
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
    <option value="${itemKeyStr?html}"<#rt/>
        <#if parameters.allowTip=="true"> title="${itemValue?html}"</#if><#rt/>
        <#if tag.contains(parameters.nameValue, itemKey) == true>
 selected="selected"<#rt/>
        </#if>
    >${itemValue?html}</option><#lt/>
</@s.iterator>
    </select>
<#if parameters.interactAction?if_exists != "">
<script type="text/javascript">
    function ${parameters.id?html}_callInteract(callBack)
    {
        SpiritInteractAction.getSelectData("${parameters.interactAction?html}",$("#${parameters.id?html}").stk_val(),{
            callback:function(result)
            {
                $("#${parameters.interactId?html}").stk_resetOptionsList(result);
                if(typeof(callBack) == 'function') callBack();
            }
        });
    }
    if(!stk.eventMap.hasBind("${parameters.id}","interact"))
    {
        $("#${parameters.id?html}").bind("change", function() {
            ${parameters.id?html}_callInteract();
        });
        stk.eventMap.add("${parameters.id}","interact");
    }
</script>
</#if>