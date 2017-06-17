<#if parameters.areaEdit == "true">
<div class='stk_multiselect_boxdiv' id="${parameters.id?html}_editorDiv">
<textarea  id="${parameters.id}_editor"<#rt/>
 rows="${parameters.areaRows?html}" cols="" <#rt/>
 style="width:${parameters.areaWidth?html};<#rt/>
 "<#rt/>
></textarea>
<div width="100%" style="text-align:center;"><button onclick="$('#${parameters.id}').val($('#${parameters.id}_editor').val());$('#${parameters.id?html}_editorDiv').hide();" type="button" class="stk-button stk-button-st-3"><span>确认</span></button>&nbsp;&nbsp;<button onclick="$('#${parameters.id}_editor').val($('#${parameters.id}').val());$('#${parameters.id?html}_editorDiv').hide();" type="button" class="stk-button stk-button-st-3"><span>取消</span></button></div>
</div>
</#if>
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_textfield_wrap.ftl" />
<#if parameters.areaEdit=="true"> areaEdit="${parameters.areaEdit?html}"</#if><#rt/>
<#if parameters.type?if_exists != ""> inputType="${parameters.type?html}"</#if><#rt/>
 style="
<#include "inc_component_style.ftl" /><#rt/>
">
<#if parameters.icon?if_exists != ""><div class='stk-date-search' id='${parameters.id}_subWrap' style='display:inline-block;*display:inline;*zoom:1;float:none'><div class="stk-date-aside">
<div class="ui-icon ${parameters.icon}" id="${parameters.id}_icon"></div>
</div></#if>
<#if parameters.areaEdit=="true">
    <textarea rows="1"<#rt/>
<#else>
    <input<#rt/>
</#if><#rt/>
<#if parameters.type!="file"> class="stk-input"</#if> type="${parameters.type?html}"<#rt/>
<#include "inc_textfield_property.ftl" />
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
 style="font-family:'宋体';<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.textAlign?if_exists != "">text-align:${parameters.textAlign?html};</#if><#rt/>
<#if parameters.type=="file">height:18px;</#if><#rt/>
"<#rt/>
<#if parameters.areaEdit=="true">
></textarea><#rt/>
<#else>
/><#rt/>
</#if><#rt/>
<#if parameters.icon?if_exists != ""></div></#if>
<script type="text/javascript">
    var ${parameters.id}_hasInit;
<#if parameters.areaEdit == "true">
    function ${parameters.id}_resetPosition()
    {
        $("#${parameters.id}_editorDiv").css("left",$("#${parameters.id}_wrap").position().left);
        $("#${parameters.id}_editorDiv").css("top",$("#${parameters.id}_wrap").position().top);
    }
</#if>
    $(document).ready(function(){
        if(window["${parameters.id}_hasInit"] == undefined)
        {
        <#if parameters.icon?if_exists != "">
            $("#${parameters.id}").addClass("stk-date-search-input");
        </#if>
            <#if parameters.areaEdit == "true">
            if(!stk.eventMap.hasBind("${parameters.id}","iconclick"))
            {
                $("#${parameters.id}_icon").bind("click", function() {
                    ${parameters.id}_resetPosition();
                    $('#${parameters.id}_editor').val($('#${parameters.id}').val());
                    $('#${parameters.id?html}_editorDiv').show();
                });
                stk.eventMap.add("${parameters.id}","iconclick");
            }
            </#if>
            <#if parameters.passwordStrength == "true">
                $("#${parameters.id}_ps").addClass("is0");
                $("#${parameters.id}").passwordStrength({targetDiv:'#${parameters.id}_ps'});
            </#if>
            window["${parameters.id}_hasInit"] = true;
        }
    });
</script>