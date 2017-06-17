<div class='stk_multiselect_boxdiv' id="${parameters.id?html}_multiVisibleDiv" <#if (parameters.width?exists)>style="width:${parameters.width};"</#if>>
    <div style="height:25px;margin-top:5px">
        <div style="text-align:left;display:inline;height:25px;<#if (parameters.cssStyle?exists)>${parameters.cssStyle}</#if>">
            <input type="checkbox" id="${parameters.id}_cb_all"<#rt/><#if parameters.disabled?default(false)> disabled="disabled"<#rt/></#if>>全部
        </div>
    </div>
    <div id="${parameters.id?html}_multiSelDiv" style="overflow-y:auto;<#rt/>
<#if parameters.height?if_exists != "">
height:${parameters.height?html};<#rt/>
</#if>
<#if parameters.width?if_exists != "">
width:${parameters.width?html};<#rt/>
</#if>
">
        <#assign itemCount = 0/>
        <#if parameters.list?exists && parameters.list?size gt 0>
            <@s.iterator value="parameters.list">
                <#assign itemCount = itemCount + 1/>

                <#if parameters.listKey?if_exists != "">
                    <#assign itemKey = stack.findValue(parameters.listKey)/>
                <#else>
                    <#assign itemKey = stack.findValue('top')/>
                </#if>
                <#if parameters.listValue?if_exists != "">
                    <#assign itemValue = stack.findString(parameters.listValue)/>
                <#else>
                    <#assign itemValue = stack.findString('top')/>
                </#if>
                <#assign itemKeyStr=itemKey.toString()/>
        <div title='${itemValue?html}' alt='${itemValue?html}' style='text-align:left;float:left;display:inline;word-break: break-all;overflow:hidden;height:<#if (parameters.columnHeight?exists)>${parameters.columnHeight?html}<#else>23px</#if>;<#if (parameters.columnWidth?exists)>width:${parameters.columnWidth?html};</#if>' id="${parameters.name?html}_${itemCount}_div">
            <input type="checkbox" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}_${itemCount}"<#rt/>
<#if tag.contains(parameters.nameValue, itemKey)>
 checked="checked"<#rt/>
</#if>
 showName="${itemValue?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.title?if_exists != "">
 title="${parameters.title?html}"<#rt/>
</#if>
/><label for="${parameters.name?html}_${itemCount}" class="checkboxLabel">${itemValue?html}</label>
        </div>
    </@s.iterator>
        <#else>
            <div style='<#if (parameters.cssStyle?exists)>${parameters.cssStyle}</#if>'>&nbsp;</div>
        </#if>
    </div>
</div>
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
><div style="display:inline-block;*display:inline;*zoom:1;float:none;" class="stk-date-search" id="${parameters.id?html}" <#if (parameters.columnWidth?exists)>columnWidth="${parameters.columnWidth?html}"</#if> name="${parameters.name?html}" type='multiselect'>
<div class="stk-date-aside">
        <div class="ui-icon ui-icon-triangle-1-s" onclick="${parameters.id}_toggle();"></div>
    </div>
<input class="stk-input stk-date-search-input" type="text" id="${parameters.id?html}_showValue" disabled="disabled"<#rt/>
 style="<#rt/>
<#if parameters.cssStyle?if_exists != "">
 "${parameters.cssStyle?html};<#rt/></#if>
 <#if parameters.textWidth?if_exists != "">
 width:${parameters.textWidth?html};<#rt/></#if>
"<#rt/>>
<script type="text/javascript">
    $("#${parameters.id?html}_selBtn").stk_button({
        icons: {
            primary: "ui-icon-search"
        },
        text: false
    });
    stk.checkBox.buildSelectAll("${parameters.id}_cb_all","${parameters.name?html}");
    $("#${parameters.id?html}_multiVisibleDiv").hover(function(){},function(){
        ${parameters.id?html}_hasSelect();
    });
    function ${parameters.id}_resetPosition()
    {
        var positionX = new Number("${parameters.positionX}");
        var positionY = new Number("${parameters.positionY}");
        $('#${parameters.id}_multiVisibleDiv').show();
        $("#${parameters.id}_multiVisibleDiv").css("left", $("#${parameters.id}_wrap").position().left + positionX);
    <#if parameters.direction=="bottom">
        $("#${parameters.id}_multiVisibleDiv").css("top", $("#${parameters.id}_wrap").position().top + positionY);
    </#if>
    <#if parameters.direction=="top">
        $("#${parameters.id}_multiVisibleDiv").css("top", $("#${parameters.id}_wrap").position().top - $("#${parameters.id}_multiVisibleDiv").height() + $("#${parameters.id}_showValue").height() + positionY);
    </#if>
    }
    function ${parameters.id}_toggle()
    {
        if($('#${parameters.id}_multiVisibleDiv').css("display") == "none")
        {
            ${parameters.id}_resetPosition();
        }
        else
        {
            $('#${parameters.id}_multiVisibleDiv').hide();
        }
    }
    <#if parameters.interactAction?if_exists != "">
    function ${parameters.id?html}_callInteract(callBack)
    {
        SpiritInteractAction.getMultiSelectData("${parameters.interactAction?html}",$("#${parameters.id?html}").stk_val(),{
            callback:function(result)
            {
                $("#${parameters.interactId?html}").stk_resetOptionsList(result);
                if(typeof(callBack) == 'function') callBack();
            }
        });
    }
    </#if>
    function ${parameters.id?html}_hasSelect()
    {
        var selectValue = stk.checkBox.getText("${parameters.name?html}");
        $("#${parameters.id?html}_showValue").val(selectValue);
        $('#${parameters.id?html}_multiVisibleDiv').hide();
        <#if parameters.interactAction?if_exists != "">
        ${parameters.id?html}_callInteract();
        </#if>
    }
    $('#multiVisibleDiv').bgiframe();
</script>
</div>