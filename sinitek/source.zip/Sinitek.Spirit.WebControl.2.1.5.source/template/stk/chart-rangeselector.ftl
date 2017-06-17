                rangeSelector:{inputStyle:{width:100},<#rt/>
<#if parameters.enabled?if_exists != "">enabled: ${parameters.enabled},</#if><#rt/>
<#if parameters.buttons?if_exists != "">buttons:${parameters.buttons},
<#else>buttons:[{type: 'month',count: 1,text: '一个月'},{type: 'month',count: 3,text: '三个月'},{type: 'month',count: 6,text: '半年'},{type: 'ytd',text: '今年'},{type: 'year',count: 1,text: '一年'},{type: 'all',text: '全部'}],</#if><#rt/>
<#if parameters.selected?if_exists != "">selected:${parameters.selected},</#if><#rt/>