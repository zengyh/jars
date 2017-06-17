
<form action="${parameters.action}" method="${parameters.actionMethod}" onsubmit="return false;" <#rt/>
 type="component" class="stk_component_wrap" cn="${parameters.componentName}" id="${parameters.id}"<#rt/>
<#if parameters.hidden?if_exists != ""> _hidden="${parameters.hidden?html}"<#rt/></#if>
<#if parameters.hideMode?if_exists != ""> hideMode="${parameters.hideMode?html}"</#if><#rt/>
<#if parameters.feature?if_exists != ""> feature="${parameters.feature?html}"</#if><#rt/>
<#if parameters.themeName?if_exists != ""> themeName="${parameters.themeName?html}"</#if><#rt/>
<#if parameters.feature?if_exists != ""> feature="${parameters.feature?html}"</#if><#rt/>
<#if parameters.clazz?if_exists != ""> clazz="${parameters.clazz?html}"</#if><#rt/>
<#if parameters.method?if_exists != ""> _method="${parameters.method?html}"</#if><#rt/>
<#if parameters.allowFileUpload?if_exists != ""> allowFileUpload="${parameters.allowFileUpload?html}"</#if><#rt/>
<#if parameters.htmlConvertIgnore?if_exists != ""> htmlConvertIgnore="${parameters.htmlConvertIgnore?html}"</#if><#rt/>
 style="width: 100%;<#include "inc_component_style.ftl" />"
>