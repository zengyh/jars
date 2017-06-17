
<span <#rt/>
<#include "inc_component_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"<#rt/>
>
    <a id="${parameters.id?html}"
<#if parameters.title?if_exists != ""> title="${parameters.title?html}"</#if><#rt/>
<#if parameters.href?if_exists != ""> href="${parameters.href?html}"</#if><#rt/>
<#if parameters.target?if_exists != ""> target="${parameters.target?html}"</#if><#rt/>
>