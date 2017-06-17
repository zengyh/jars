
</span>
<#if parameters.checkDate == "true">
<script type="text/javascript">
    if(!stk.eventMap.hasBind("${parameters.id}","checkDate"))
    {
        $("#${parameters.id}").bind("blur", function() {
            $('#${parameters.id}').removeClass("ui-state-error");
            if(StringUtils.isNotBlank($('#${parameters.id}').stk_val()))
            {
                var startDate,startFormat,endDate,endFormat;
                <#if parameters.startDate?if_exists != "">startDate="${parameters.startDate}";startFormat = $("#${parameters.startDate}_wrap").attr("dateFormat");startDate = $("#${parameters.startDate}").stk_val();</#if>
                <#if parameters.endDate?if_exists != "">endDate="${parameters.endDate}";endFormat = $("#${parameters.endDate}_wrap").attr("dateFormat");endDate = $("#${parameters.endDate}").stk_val();</#if>
                SpiritCommonAction.checkDate("${parameters.format}",$('#${parameters.id}').stk_val(),startFormat,startDate,endFormat,endDate, {
                    callback:function(result) {
                        if(StringUtils.isNotBlank(result.error))
                        {
                            stk.error(result.error,{"确认":function(){$("#__alertDiv").dialog("close");window['${parameters.id}_errorData']=$('#${parameters.id}').stk_val();$('#${parameters.id}').stk_val("").focus();}});
                            $('#${parameters.id}').addClass("ui-state-error");
                        }
                        else if(StringUtils.isNotBlank(result.value))
                            $('#${parameters.id}').stk_val(result.value);
                    },
                    async:false
                });
            }
        });
        stk.eventMap.add("${parameters.id}","checkDate");
    }
</script>
</#if>
<#include "inc_defalutvalue.ftl" />