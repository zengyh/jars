
<li <#rt/>
<#include "inc_component_wrap_noclass.ftl" />
 style="cursor:pointer;<#include "inc_component_style.ftl" />"
>
<a
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
 class="ext stk-button" href="#"><#if parameters.icon?if_exists != ""><b class="stk-button-icon ${parameters.icon}"></b></#if><#if parameters.text?if_exists != ""><span name="text">${parameters.text}</span></#if><b class="b-down stk-button"><b id="${parameters.id?html}_menuButton" onclick="$.stopBubble(event);"  class="stk-button-icon  stk-button-icon-bi1 ui-icon-triangle-1-s"></b></b></a><b class="split"></b>

<script type="text/javascript">
    var ${parameters.id?html}_hasInit;
    $(document).ready(function(){
        if(${parameters.id?html}_hasInit == undefined)
        {
            $('#${parameters.targetMenu?html}').appendTo($(document).find("body")).hide();
            $('#${parameters.id?html}_menuButton').click(function() {
                $('#${parameters.id?html}_menuButton').tipExtraCancel();
                $('#${parameters.id?html}_menuButton').tipExtra1({
                    html : $('#${parameters.targetMenu?html}').clone().show()
                });
            });
        <#if parameters.clickToShow?exists && parameters.clickToShow == "true">
            $('#${parameters.id?html}').click(function(e) {
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