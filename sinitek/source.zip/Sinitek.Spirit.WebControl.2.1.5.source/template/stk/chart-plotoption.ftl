                ${parameters.type}:{
<#if parameters.pointStart?if_exists != "">pointStart:${parameters.pointStart},</#if><#rt/>
<#if parameters.pointInterval?if_exists != "">pointInterval:${parameters.pointInterval},</#if><#rt/>
<#if parameters.visible?if_exists != "">visible:${parameters.visible},</#if><#rt/>
<#if parameters.zIndex?if_exists != "">zIndex:${parameters.zIndex},</#if><#rt/>
<#if parameters.lineWidth?if_exists != "">lineWidth:${parameters.lineWidth},</#if><#rt/>
<#if parameters.stacking?if_exists != "">stacking:'${parameters.stacking}',</#if><#rt/>
<#if parameters.allowPointSelect?if_exists != "">allowPointSelect:${parameters.allowPointSelect},</#if><#rt/>
<#if parameters.cursor?if_exists != "">cursor:'${parameters.cursor}',</#if><#rt/>