
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#if parameters.checked?if_exists != ""> checked="${parameters.checked?html}"<#rt/></#if>
 style="<#include "inc_component_style.ftl" />"
>
<#if parameters.swichStyle?if_exists != "">
    <input id="${parameters.id?html}_style" type="button" class="buttonswich${parameters.swichStyle}<#if parameters.checked='true'>_toggle</#if>" onclick="$('#${parameters.id?html}').stk_toggleChecked()" styleType='${parameters.swichStyle}'>
</#if>
    <input <#if parameters.swichStyle?if_exists != "">style="display: none"</#if> type="checkbox"<#rt/>
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
<#if parameters.checked='true'>
 checked="checked"<#rt/>
</#if>
<#if parameters.nameValue?if_exists != ""> value="${parameters.nameValue?html}"</#if><#rt/>
/><#rt/>
<#if parameters.label?if_exists != "">
<label for="${parameters.id?html}">${parameters.label?html}</label><#rt/>
</#if>
<#if parameters.nameValue?if_exists != "">
<input type="hidden" name="__checkbox_${parameters.name?html}" value="${parameters.nameValue?html}"/><#rt/>
</#if>