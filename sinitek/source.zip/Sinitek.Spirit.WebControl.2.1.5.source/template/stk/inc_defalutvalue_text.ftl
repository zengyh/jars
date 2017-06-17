<#if parameters.sDefaultValue?if_exists != "">
<div id="${parameters.id?html}_defaultValue_div" style="display:none"><@s.property value="parameters.oDefaultValue"/><#t/></div>
<script type="text/javascript">
    var ${parameters.id?html}_defaultValue;
    if(${parameters.id?html}_defaultValue == undefined)
    {
        ${parameters.id?html}_defaultValue  = $("#${parameters.id?html}_defaultValue_div").text();
        $("#${parameters.id?html}").stk_val(${parameters.id?html}_defaultValue).trigger("keyup");
    }
</script>
</#if>
<#if parameters.emptyText?if_exists != "">
<script type="text/javascript">
    var ${parameters.id?html}_emptyText;
    if(${parameters.id?html}_emptyText == undefined)
    {
        ${parameters.id?html}_emptyText  = "${parameters.emptyText?html}";
        $("#${parameters.id?html}").stk_emptyText(${parameters.id?html}_emptyText);
    }
</script>
</#if>
<#if parameters.selectOnFocus == "true">
<script type="text/javascript">
    $("#${parameters.id?html}").bind("focus",function() { $("#${parameters.id?html}").select() })
</script>
</#if>