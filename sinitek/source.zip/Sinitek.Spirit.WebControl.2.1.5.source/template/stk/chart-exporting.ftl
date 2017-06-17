                exporting:{<#rt/>
<#if parameters.enabled?if_exists != "">enabled: ${parameters.enabled},</#if><#rt/>
<#if parameters.filename?if_exists != "">filename:'${parameters.filename}',</#if><#rt/>
<#if parameters.type?if_exists != "">type:'${parameters.type}',</#if><#rt/>
<#if parameters.url?if_exists != "">url: '${parameters.url}',</#if><#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width},</#if><#rt/>