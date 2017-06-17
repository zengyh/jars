
<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#if parameters.labelWidth?if_exists != ""> labelWidth="${parameters.labelWidth?html}"</#if><#rt/>
 style="<#rt/>
<#if parameters.underLine=="true">border-bottom:1px dotted #DEDEDE;</#if><#rt/>
<#if parameters.marginBottom?if_exists != "">margin-bottom:${parameters.marginBottom?html};</#if><#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
    <table width="100%" cellspacing="0" cellpadding="0" id="${parameters.id?html}"><tr><#if parameters.label?if_exists != ""><td align="right" style="width:${parameters.labelWidth?html}"
 class="<#if parameters.feature?exists && parameters.feature?index_of("labelBg") != -1>phone-result-bg</#if>">${parameters.label?html}</td></#if>
        <td align="${parameters.align?html}" style="vertical-align:middle;padding-left:3px;text-align:${parameters.align?html};<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>">


