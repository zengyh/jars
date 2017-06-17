
<span <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
<#include "inc_field_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
>
    <button type="${parameters.type}" <#if parameters.icon?if_exists != "">icon="${parameters.icon}"</#if><#rt/>
    <#if parameters.nameValue?if_exists != "">
     value="<@s.property value="parameters.nameValue"/>"<#rt/>
    </#if>
    <#if parameters.cssClass?if_exists != "">
     class="${parameters.cssClass?html}"<#rt/>
    </#if>
    <#if parameters.cssStyle?if_exists != "">
     style="${parameters.cssStyle?html}"<#rt/>
    </#if>
    <#include "inc_component_property.ftl" />
    <#include "inc_field_property.ftl" />
    ><#if parameters.text?if_exists != "">${parameters.text?html}</#if></button>
<script type="text/javascript">
    $("#${parameters.id}").stk_button({
    <#if parameters.icon?if_exists != "">
        icons: {
            primary: "${parameters.icon}"
        },
    </#if>
        text: <#if parameters.text?if_exists != "">"${parameters.text}"
    <#else >false
    </#if>
    });
    <#if parameters.disabled?default(false)>$("#${parameters.id?html}").stk_disabled(true);</#if><#rt/>
</script>