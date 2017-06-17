<#if parameters.disabled?default(false)> disabled="disabled"</#if><#rt/>
<#if parameters.readOnly == "true"> readonly="readonly"</#if><#rt/>
<#if parameters.title?if_exists != ""> title="${parameters.title?html}"</#if><#rt/>