
<li <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
 style="cursor:pointer;<#include "inc_component_style.ftl" />"
>
    <a
<#if parameters.cssStyle?if_exists != "">
 style=";${parameters.cssStyle?html}"<#rt/>
</#if>
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
 class="normal stk-button"><b class="<#if parameters.icon?if_exists != "">stk-button-icon ${parameters.icon}</#if>"></b><#if parameters.text?if_exists != ""><span name="text">${parameters.text?html}</span></#if></a><b class="split"></b>