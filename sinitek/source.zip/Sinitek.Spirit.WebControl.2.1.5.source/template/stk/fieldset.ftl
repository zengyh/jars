
<div class="stk_component_wrap"<#rt/>
<#include "inc_component_wrap_noclass.ftl" />
<#include "inc_box_wrap.ftl" />
 style="position:relative;<#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
<fieldset id="${parameters.id}" class="stk_fieldset">
<#if parameters.title?if_exists != "">
<legend style="font-weight:bold;"><table
    <#if parameters.collapsible == "true">style="cursor:pointer;"
        onclick="stk.fieldset.toggleCollapse('${parameters.id?html}');"<#rt/></#if>
        ><tr><td><div>${parameters.title?html}</div></td>
    <td><#rt/>
    <#if parameters.collapsible == "true">
        <#if parameters.collapsed == "true">
        <div class="stk_fieldset_collapse_button_open" id="${parameters.id?html}_collapse_button" style="margin-left: 5px"></div><#rt/>
        <#else>
        <div class="stk_fieldset_collapse_button_close" id="${parameters.id?html}_collapse_button" style="margin-left: 5px"></div><#rt/>
        </#if>
    </#if></td></tr></table></legend>
</#if>
    <div id="${parameters.id?html}_body" class="stk_fieldset_body" style="<#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>
            <#if parameters.collapsible == "true" && parameters.collapsed == "true">
            display:none;<#rt/>
            </#if>
            "><#rt/>
    <#include "inc_box_div.ftl" />