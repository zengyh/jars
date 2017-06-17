
<div style="display:none;" <#rt/>
type="event" evt="${parameters.eventName}" targetId="${parameters.targetId}"<#rt/>
>
<script type="text/javascript">
    function ${parameters.targetId}_onselect(obj)
    {
        <#if parameters.valVar?if_exists != "">
        var ${parameters.valVar?html} = obj;
        </#if>