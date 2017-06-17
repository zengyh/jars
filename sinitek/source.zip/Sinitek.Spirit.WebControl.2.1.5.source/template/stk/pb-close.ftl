</span>
</span>
<input type="hidden"<#rt/>
<#include "inc_component_property.ftl" />/>
<script type="text/javascript">
    $(document).ready(function ()
    {
        var value = 0;
    <#if parameters.sDefaultValue?if_exists != "">value = <@s.property value="parameters.oDefaultValue"/>;</#if>
        $("#${parameters.id?html}_bar").progressBar(value, {showText:${parameters.showText?html},max:${parameters.max?html},textFormat:'${parameters.textFormat?html}',
        callback: function (data)
        {
            $("#${parameters.id?html}").val(data.running_value);
            if(window['${parameters.id?html}_callback']) window['${parameters.id?html}_callback'](data);
        }});
    })
</script>