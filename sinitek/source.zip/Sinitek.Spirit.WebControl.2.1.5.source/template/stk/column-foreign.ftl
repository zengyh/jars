<#if parameters.colNum?if_exists != ""><th <#else><div </#if><#rt/>
<#include "inc-column-property.ftl" />
<#if parameters.format?if_exists != ""> format="${parameters.format}"</#if><#rt/>
<#if parameters.tableName?if_exists != ""> tableName="${parameters.tableName}"</#if><#rt/>
<#if parameters.columnName?if_exists != ""> columnName="${parameters.columnName}"</#if><#rt/>
>
<#include "inc-column-body.ftl" />