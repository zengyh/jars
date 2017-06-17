                yAxis:{<#rt/>
<#if parameters.categories?if_exists != "">categories: ${parameters.categories},</#if><#rt/>
<#if parameters.showFirstLabel?if_exists != "">showFirstLabel:${parameters.showFirstLabel},</#if><#rt/>
<#if parameters.showLastLabel?if_exists != "">showLastLabel:${parameters.showLastLabel},</#if><#rt/>
<#if parameters.tickInterval?if_exists != "">tickInterval: ${parameters.tickInterval},</#if><#rt/>
<#if parameters.maxZoom?if_exists != "">maxZoom: ${parameters.maxZoom},</#if><#rt/>
<#if parameters.max?if_exists != "">max: ${parameters.max},</#if><#rt/>
<#if parameters.min?if_exists != "">min: ${parameters.min},</#if><#rt/>