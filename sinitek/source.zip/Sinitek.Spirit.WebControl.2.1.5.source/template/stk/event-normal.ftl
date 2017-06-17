
<div style="display:none;" <#rt/>
type="event" evt="${parameters.eventName}" targetId="${parameters.targetId}"<#rt/>
>
<script type="text/javascript">
    function ${parameters.targetId}_${parameters.eventName}(obj,p1,p2)
    {
        <#if parameters.valVar?if_exists != "">
        var ${parameters.valVar?html} = $(obj).stk_val();
        </#if>
        <#if parameters.checkedVar?if_exists != "">
        var ${parameters.checkedVar?html} = $(obj).stk_checked();
        </#if>
        <#if parameters.textVar?if_exists != "">
        var ${parameters.textVar?html} = $(obj).stk_text();
        </#if>
        <#if parameters.keyCodeVar?if_exists != "">
        var ${parameters.keyCodeVar?html} = p1.keyCode;
        </#if>
        <#if parameters.mouseXVar?if_exists != "">
        var ${parameters.mouseXVar?html} = clientX;
        </#if>
        <#if parameters.mouseYVar?if_exists != "">
        var ${parameters.mouseYVar?html} = clientY;
        </#if>