
<div class="stk_component_wrap"<#rt/>
<#include "inc_component_wrap_noclass.ftl" />
<#include "inc_box_wrap.ftl" />
 style="position:relative;display:inline;<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
    <#if parameters.title?if_exists != ""><table height="20px" width="100%" cellpadding="0" cellspacing="0" border="0"
    <#if parameters.collapsible == "true">style="cursor:pointer;"
        onclick="stk.panel.toggleCollapse('${parameters.id?html}');"<#rt/></#if>
        ><tr class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix"><td><div>&nbsp;${parameters.title?html}</div></td>
    <td><#rt/>
    <#if parameters.collapsible == "true">
        <#if parameters.collapsed == "true">
        <div class="stk_panel_collapse_button_open" id="${parameters.id?html}_collapse_button" style="margin-left: 5px"></div><#rt/>
        <#else>
        <div class="stk_panel_collapse_button_close" id="${parameters.id?html}_collapse_button" style="margin-left: 5px"></div><#rt/>
        </#if>
    </#if></td></tr></table>
    </#if>
    <div id="${parameters.id?html}" class="stk_panel_body" style="<#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>
            <#if parameters.collapsible == "true" && parameters.collapsed == "true">
            display:none;<#rt/>
            </#if>
            "><#rt/>
    <#include "inc_box_div.ftl" />
    
