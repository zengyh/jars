<#if parameters.colNum?if_exists != ""><th <#else><div </#if><#rt/>
<#include "inc-column-property.ftl" />
<#if parameters.format?if_exists != ""> format="${parameters.format}"</#if><#rt/>
<#if parameters.entityName?if_exists != ""> entityName="${parameters.entityName}"</#if><#rt/>
<#if parameters.foreignProperty?if_exists != ""> foreignProperty="${parameters.foreignProperty}"</#if><#rt/>
>
<#include "inc-column-body.ftl" />