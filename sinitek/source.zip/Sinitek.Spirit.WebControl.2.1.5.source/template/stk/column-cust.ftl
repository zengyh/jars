<#if parameters.colNum?if_exists != ""><th <#else><div </#if><#rt/>
<#include "inc-column-property.ftl" />
<#if parameters.clazz?if_exists != ""> clazz="${parameters.clazz}"</#if><#rt/>
<#if parameters.method?if_exists != ""> _method="${parameters.method}"</#if><#rt/>
>
<#include "inc-column-body.ftl" />