<#if parameters.colNum?if_exists != ""><th <#else><div </#if><#rt/>
<#include "inc-column-property.ftl" />
<#if parameters.format?if_exists != ""> format="${parameters.format}"</#if><#rt/>
>
<#include "inc-column-body.ftl" />