
<span <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
<#include "inc_field_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
>
    <div class="stk-button stk-ext-button<#if parameters.feature?exists && parameters.feature?index_of("noBg") != -1> nobg noborder</#if>"
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
><#if parameters.icon?if_exists != ""><span class="stk-button-icon ${parameters.icon}"></span></#if><#if parameters.text?if_exists != ""><span name="text">${parameters.text}</span></#if><b id="${parameters.id?html}_menuButton" onClick="$.stopBubble(event);" class="stk-button-icon stk-button-icon-bi1 ui-icon-triangle-b"></b>
</div>
<script type="text/javascript">
    var ${parameters.id?html}_hasInit = null;
    $(document).ready(function(){
        $('#${parameters.targetMenu?html}').hide();
        if(${parameters.id?html}_hasInit == null)
        {
            $('#${parameters.id?html}_menuButton').click(function() {
                $('#${parameters.id?html}_menuButton').tipExtraCancel();
                $('#${parameters.id?html}_menuButton').tipExtra1({
                    html : $('#${parameters.targetMenu?html}').clone().show()
                });
            });
        <#if parameters.clickToShow?exists && parameters.clickToShow == "true">
            $('#${parameters.id?html}').click(function() {
                $('#${parameters.id?html}_menuButton').tipExtraCancel();
                $('#${parameters.id?html}_menuButton').tipExtra1({
                    html : $('#${parameters.targetMenu?html}').clone().show()
                });
            });
        </#if>

            ${parameters.id?html}_hasInit = true;
        }
    });
</script>