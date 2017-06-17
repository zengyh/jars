
<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_box_wrap.ftl" />
 style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
"<#rt/>
>
    <UL id="${parameters.id?html}" class="stk-ui-tabs-bar clearfix">

