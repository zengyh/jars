
<div <#rt/>
<#include "inc_component_wrap.ftl" />
 style="display:inline-block;*display:inline;*zoom:1;z-index:9999999;background-color:white;position:absolute;<#rt/>
 <#include "inc_component_style.ftl" />"<#rt/>
>
    <div class='stk-input-search-extra-st1' id="${parameters.id?html}" style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html}</#if><#rt/>
"><#rt/>
        <div class="stk-child-bar" onclick="$('#${parameters.id?html}').stk_hide();">
        <b class="stk-button-icon stk-child-close" style="right:15px"></b>
        </div>
        <div name="tab" class="stk-child-nav"></div>
        <div name="content" style="width:100%;<#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};overflow-y:auto;overflow-x:hidden;</#if><#rt/>
"><#rt/></div>
        <div name="buttonGroup" style="text-align:center;width:100%"></div>
    </div>
<script type="text/javascript">
    var ${parameters.id}_hasInit;
    function ${parameters.id}_resetPosition(target)
    {
        <#if parameters.target?if_exists != "">
            var positionX = new Number("${parameters.positionX}");
            var positionY = new Number("${parameters.positionY}");
            var obj = $("#${parameters.target}");
            if($("#${parameters.target}_wrap").length > 0 && $("#${parameters.target}_wrap").attr("cn")=='window'){
                obj = $("#${parameters.target}_wrap").parent().parent();
            }
            if(target) obj = target;
            $("#${parameters.id}_wrap").css("left",$(obj).position().left+positionX);
            <#if parameters.direction=="bottom">
            $("#${parameters.id}_wrap").css("top",$(obj).position().top+positionY);
            <#else>
            $("#${parameters.id}_wrap").css("top",$(obj).position().top-$("#${parameters.id}_wrap").height()+$(obj).height()+positionY);
            </#if>
        </#if>
    }
    <#if parameters.interactFunction?if_exists != "">
    var ${parameters.id}_interactFunction = function(para1,para2) {
        ${parameters.interactFunction}(para1,para2)
    };
    </#if>
    <#if parameters.allowInit == "true">
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            ${parameters.id}_resetPosition();
            ${parameters.id}_init();
            ${parameters.id}_hasInit = true;
        }
    });
    </#if>
    function ${parameters.id}_init(callBack)
    {
        $("#${parameters.id}_wrap").bgiframe();
        var options = {
            id:'${parameters.id}',
            clazz:'${parameters.clazz}',
            multiple:'${parameters.multiple}',
            selectAll:${parameters.selectAll},
            cellWidth:'${parameters.cellWidth}'
        };
        var param = {};
    <#if parameters.jsParam?if_exists != "">
        if (typeof(${parameters.jsParam}) == "function")
            param = ${parameters.jsParam}();
    </#if>
        var defaultValue;
    <#if parameters.sDefaultValue?if_exists != "">
        defaultValue =  ${parameters.sDefaultValue?html};
    </#if>
        SpiritSelectorAction.genLetterSelector(options, param, {
            callback:function(result) {
                stk.letterSelector.init("${parameters.id}", result, options);
                $("#${parameters.id?html}").stk_val(defaultValue);
                if (typeof(callBack) == "function")
                    callBack();
            }
        });
    }
</script>