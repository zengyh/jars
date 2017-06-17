
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_textfield_wrap.ftl" />
<#if parameters.type?if_exists != ""> inputType="${parameters.type?html}"</#if><#rt/>
<#if parameters.format?if_exists != ""> dateFormat="${parameters.format}"</#if><#rt/>
 style="<#include "inc_component_style.ftl" />"<#rt/>
><div class="stk-date-search" id="${parameters.id}_subWrap" style="display:inline-block;*display:inline;*zoom:1;float:none">
    <input class="stk-input" type="_${parameters.type?html}"<#rt/>
<#include "inc_textfield_property.ftl" />
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
 style="font-size: 12px;<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
"<#rt/>
/></div>
<script type="text/javascript">
    var ${parameters.id}_hasInit;
    var _${parameters.id};
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            $("#${parameters.id}").addClass("stk-date-search-input").before("<div class='stk-date-aside'><div id='${parameters.id}_icon' onclick='${parameters.id}_refreshDate()' class='stk-ui-icon stk-date-aside-icon'></div></div>");
            <#if parameters.disabled?default(false)> $("#${parameters.id}").stk_disabled(true);</#if><#rt/>
            ${parameters.id}_initDate();
            ${parameters.id}_hasInit = true;
        }
    });

    function ${parameters.id}_refreshDate()
    {
        _${parameters.id}.redraw();
    }

    function ${parameters.id}_beforePopup()
    {
        if(window['${parameters.id}_errorData'])
        {
            $('#${parameters.id}').stk_val(window['${parameters.id}_errorData']);

        }
        window['${parameters.id}_errorData'] = undefined;
    }

    function ${parameters.id}_afterHidePopup()
    {
        <#if parameters.type == 'datetime'>
            $("#${parameters.id}").blur();
        </#if>
    } 

    function ${parameters.id}_initDate()
    {
        var config = {
        <#if parameters.type == 'datetime'>
            <#if parameters.showSecond == 'true'>
                dateFormat:'%Y-%m-%d %H:%M:%S',
            <#else>
                dateFormat:'%Y-%m-%d %H:%M',
            </#if>
        <#else>
            dateFormat: "%Y-%m-%d",
        </#if>
            inputField: "${parameters.id}",
            trigger: "${parameters.id}_icon",
        <#if parameters.type == 'datetime'>
            showTime: 24,
        </#if>
            fdow:${parameters.firstDay},
            minuteStep:${parameters.minuteStep},
            onSelect: function() {
                if(typeof(${parameters.id}_onselect) == 'function') ${parameters.id}_onselect();
                <#if parameters.type != 'datetime'>
                this.hide();
                </#if>
                $("#${parameters.id}").blur();
            },
            <#if parameters.type == 'datetime'>
            onTimeChange  : function(cal) {
                var h = cal.getHours(), m = cal.getMinutes();
                // zero-pad them
                if (h < 10) h = "0" + h;
                if (m < 10) m = "0" + m;
                var time = $("#${parameters.id}").stk_val();
                if(time.length == "19")
                {
                    <#if parameters.showSecond == 'true'>
                        time = time.substr(0,11) + h+":"+m+":00";
                        <#else>
                        time = time.substr(0,11) + h+":"+m;
                    </#if>
                    $("#${parameters.id}").stk_val(time);
                }
                else if ( time.length == "16" )
                {
                    time = time.substr( 0, 11 ) + h + ":" + m;
                    $( "#${parameters.id}" ).stk_val( time );
                }
            },
            </#if>
            titleFormat: "%Y %B"
        };
        if ( jQuery.browser.msie && jQuery.browser.version < 7 )
        {
            config.onFocus = function ( ca )
            {
                if ( ca && ca.els.main && !ca.bgiframe )
                {
                    $( ca.els.main ).bgiframe();
                    ca.bgiframe = true;
                }
            }
        }
        _${parameters.id} = Calendar.setup(config);
    }
</script>