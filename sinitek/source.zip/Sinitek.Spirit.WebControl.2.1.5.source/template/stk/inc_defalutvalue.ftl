<#if parameters.sDefaultValue?if_exists != "">
<script type="text/javascript">
    $(document).ready(function(){
        var ${parameters.id?html}_defaultValue;
        if(${parameters.id?html}_defaultValue == undefined)
        {
            ${parameters.id?html}_defaultValue  = ${parameters.sDefaultValue};
            $("#${parameters.id?html}").stk_val(${parameters.id?html}_defaultValue);
        }
    })
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