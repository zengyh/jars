type="component" class="stk_component_wrap" cn="${parameters.componentName}" id="${parameters.id}_wrap"<#rt/>
<#if parameters.hidden?if_exists != ""> _hidden="${parameters.hidden?html}"<#rt/></#if>
<#if parameters.hideMode?if_exists != ""> hideMode="${parameters.hideMode?html}"</#if><#rt/>
<#if parameters.feature?if_exists != ""> feature="${parameters.feature?html}"</#if><#rt/>
<#if parameters.themeName?if_exists != ""> themeName="${parameters.themeName?html}"</#if><#rt/>
<#if parameters.feature?if_exists != ""> feature="${parameters.feature?html}"</#if><#rt/>