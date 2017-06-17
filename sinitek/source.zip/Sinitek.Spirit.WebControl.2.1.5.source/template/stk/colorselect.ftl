<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_textfield_wrap.ftl" />
<#if parameters.type?if_exists != ""> inputType="${parameters.type?html}"</#if><#rt/>
<#if parameters.format?if_exists != ""> dateFormat="${parameters.format}"</#if><#rt/>
 style="<#include "inc_component_style.ftl" />"<#rt/>
>
    <input class="input_color" type="text" readonly="readonly"<#rt/>
<#if parameters.sDefaultValue?if_exists != ""></#if>
<#include "inc_textfield_property.ftl" />
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
<#rt/>
/>
<script type="text/javascript">
    var ${parameters.id}_hasInit;
    var _${parameters.id};
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            var ${parameters.id?html}_defaultValue = $( "#${parameters.id?html}_defaultValue_div" ).text();
            if(${parameters.id?html}_defaultValue)
                $( "#${parameters.id?html}" ).val( ${parameters.id?html}_defaultValue );
            $("#${parameters.id}").colorSelect({
                simple:${parameters.simple?html}
            });
            ${parameters.id}_hasInit = true;
        }
    });
</script>