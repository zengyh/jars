
<span <#rt/>
<#include "inc_component_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
>
    <label id="${parameters.id?html}" style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
">