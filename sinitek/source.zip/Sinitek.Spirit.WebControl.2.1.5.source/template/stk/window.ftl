<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_box_wrap.ftl" />
<#if parameters.title?if_exists != ""> _title="${parameters.title?html}"</#if><#rt/>
<#if parameters.modal?if_exists != ""> modal="${parameters.modal?html}"</#if><#rt/>
<#if parameters.width?if_exists != ""> _width="${parameters.width?html}"</#if><#rt/>
<#if parameters.height?if_exists != ""> _height="${parameters.height?html}"</#if><#rt/>
<#if parameters.position?if_exists != ""> position="${parameters.position?html}"</#if><#rt/>
 style="display:none;<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
"<#rt/>
>
    <div class="stk_box_layout_${parameters.layout?html}" id="${parameters.id?html}">

