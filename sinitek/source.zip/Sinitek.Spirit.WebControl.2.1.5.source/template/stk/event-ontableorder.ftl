
<div style="display:none;" <#rt/>
type="event" evt="${parameters.eventName}" targetId="${parameters.targetId}"<#rt/>
>
<script type="text/javascript">
    function ${parameters.targetId}_${parameters.eventName}(_property,_order)
    {
        <#if parameters.propertyVar?if_exists != "">
        var ${parameters.propertyVar?html} = _property;
        </#if><#rt/>
        <#if parameters.orderTypeVar?if_exists != "">
        var ${parameters.orderTypeVar?html} = _order;
        </#if><#rt/>