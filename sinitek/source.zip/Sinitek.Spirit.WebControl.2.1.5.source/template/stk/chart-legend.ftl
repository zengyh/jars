                legend:{<#rt/>
<#if parameters.align?if_exists != "">align: '${parameters.align}',</#if><#rt/>
<#if parameters.margin?if_exists != "">margin:${parameters.margin},</#if><#rt/>
<#if parameters.layout?if_exists != "">layout:'${parameters.layout}',</#if><#rt/>
<#if parameters.verticalAlign?if_exists != "">verticalAlign: '${parameters.verticalAlign}',</#if><#rt/>
<#if parameters.x?if_exists != "">x:${parameters.x},</#if><#rt/>
<#if parameters.y?if_exists != "">y:${parameters.y},</#if><#rt/>
<#if parameters.floating?if_exists != "">floating:${parameters.floating},</#if><#rt/>
<#if parameters.enabled?if_exists != "">enabled:${parameters.enabled},</#if><#rt/>