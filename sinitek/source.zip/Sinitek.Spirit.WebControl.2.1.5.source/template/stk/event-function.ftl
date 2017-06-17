
<div style="display:none;" <#rt/>
type="event" evt="${parameters.eventName}" targetId="${parameters.targetId}"<#rt/>
>
<script type="text/javascript">
    function ${parameters.targetId}_${parameters.eventName}(obj,cn)
    {
        <#if parameters.dataVar?if_exists != "">
        var ${parameters.dataVar?html} = obj;
        </#if>