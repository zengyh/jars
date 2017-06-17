<#if parameters.colNum?if_exists != ""><th <#else><div </#if><#rt/>
<#include "inc-column-property.ftl" />
<#if parameters.pointPlace?if_exists != ""> pointPlace="${parameters.pointPlace}"</#if><#rt/>
<#if parameters.isMoney?if_exists != ""> isMoney="${parameters.isMoney}"</#if><#rt/>
<#if parameters.afterText?if_exists != ""> afterText="${parameters.afterText}"</#if><#rt/>
<#if parameters.beforeText?if_exists != ""> beforeText="${parameters.beforeText}"</#if><#rt/>
>
<#include "inc-column-body.ftl" />