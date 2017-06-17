
<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_box_wrap.ftl" />
 style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
    <#include "inc_box_div.ftl" />    
    
