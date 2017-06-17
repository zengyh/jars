<div<#rt/>
<#include "inc-colplugin-property.ftl" />
<#if parameters.text?if_exists != ""> pluginParam="${parameters.text}"</#if><#rt/>
<#if parameters.icon?if_exists != ""> pluginParam2="${parameters.icon}"</#if><#rt/>
>
<script type="text/javascript">
    function ${parameters.id}_plugin_click(${parameters.dataVar})
    {