                marker:{<#rt/>
<#if parameters.enabled?if_exists != "">enabled: ${parameters.enabled},</#if><#rt/>
<#if parameters.fillColor?if_exists != "">fillColor: '${parameters.fillColor}',</#if><#rt/>
<#if parameters.lineColor?if_exists != "">lineColor: '${parameters.lineColor}',</#if><#rt/>
<#if parameters.lineWidth?if_exists != "">lineWidth: ${parameters.lineWidth},</#if><#rt/>
<#if parameters.radius?if_exists != "">radius: ${parameters.radius},</#if><#rt/>
<#if parameters.symbol?if_exists != "">symbol: '${parameters.symbol}',</#if><#rt/>
