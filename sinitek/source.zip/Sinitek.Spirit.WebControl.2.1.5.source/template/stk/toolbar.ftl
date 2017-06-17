<div class="stk-ui-bar stk-table-opt-bar" <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
 style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>>
<ul class="btn-box" id="${parameters.id?html}">