
<div style="display:none;" <#rt/>
type="event" evt="${parameters.eventName}" targetId="${parameters.targetId}"<#rt/>
>
<script type="text/javascript">
    function ${parameters.targetId}_${parameters.eventName}(obj)
    {
        <#if parameters.tabVar?if_exists != "">
        var ${parameters.tabVar?html} = obj;
        </#if>