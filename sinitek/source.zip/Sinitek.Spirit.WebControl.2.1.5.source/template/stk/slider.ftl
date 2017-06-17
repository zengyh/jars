
<div <#rt/>
<#include "inc_component_wrap.ftl" />
 style="display:inline-block;*display:inline;*zoom:1;<#rt/>
<#include "inc_component_style.ftl" />"<#rt/>
>
    <div style="<#if parameters.orientation=="horizontal">width<#else>height</#if>:${parameters._size?html}"
 id="${parameters.id?html}"></div><input type="hidden" id="${parameters.id?html}_value">

<script type="text/javascript">
    $(document).ready(function(){
        if(window["${parameters.id}_hasInit"] == undefined)
        {
            ${parameters.id}_init();
            window["${parameters.id}_hasInit"] = true;
        }
    });

    function ${parameters.id}_init(val)
    {
        var config = {};
    <#if parameters.disabled?default(false)>
        config.disabled =${parameters.disabled};
    </#if>
    <#if parameters.max?if_exists != "">
        config.max =${parameters.max?html};
    </#if>
    <#if parameters.min?if_exists != "">
        config.min =${parameters.min?html};
    </#if>
    <#if parameters.orientation?if_exists != "">
        config.orientation = "${parameters.orientation?html}";
    </#if>
    <#if parameters.range?if_exists != "">
        config.range =${parameters.range?html};
    </#if>
    <#if parameters.step?if_exists != "">
        config.step =${parameters.step?html};
    </#if>
    <#if parameters.sDefaultValue?if_exists != "" && parameters.defaultValueType == "array">
        config.values = ${parameters.sDefaultValue?html};
    </#if>
    <#if parameters.sDefaultValue?if_exists != "" && parameters.defaultValueType != "array">
        config.value = ${parameters.sDefaultValue?html};
    </#if>
        if(val){
            if(val.value) config.value = val.value;
            if(val.values) config.values = val.values;
        }
        config.change = function(event,ui)
        {
            var val = $("#${parameters.id}").slider("value");
            if(!val) {val = $("#${parameters.id}").slider("values");if(typeof(val) == typeof([])) $("#${parameters.id}_value").val(val.join(","))}
            else {$("#${parameters.id}_value").val(val)}

            if(typeof(${parameters.id}_sliderchange) == 'function') ${parameters.id}_sliderchange(val)
        };
        if(typeof(${parameters.id}_sliderchange) == 'function'){
            config.slide = function(event,ui)
            {
                ${parameters.id}_sliderchange(ui)
            }
        }
        $("#${parameters.id}").empty();
        $("#${parameters.id}").slider(config);
    }
</script>