                    chart: {
<#if parameters.inverted?if_exists != "">inverted:${parameters.inverted},</#if><#rt>
<#if parameters.margin?if_exists != "">margin:${parameters.margin},</#if><#rt>
<#if parameters.marginTop?if_exists != "">marginTop:${parameters.marginTop},</#if><#rt>
<#if parameters.marginRight?if_exists != "">marginRight:${parameters.marginRight},</#if><#rt>
<#if parameters.marginBottom?if_exists != "">marginBottom:${parameters.marginBottom},</#if><#rt>
<#if parameters.marginLeft?if_exists != "">marginLeft:${parameters.marginLeft},</#if><#rt>
<#if parameters.spacingTop?if_exists != "">spacingTop:${parameters.spacingTop},</#if><#rt>
<#if parameters.spacingRight?if_exists != "">spacingRight:${parameters.spacingRight},</#if><#rt>
<#if parameters.spacingBottom?if_exists != "">spacingBottom:${parameters.spacingBottom},</#if><#rt>
<#if parameters.spacingLeft?if_exists != "">spacingLeft:${parameters.spacingLeft},</#if><#rt>
                        <#if parameters.type != "stock">type: '${parameters.type}',</#if>
                        renderTo: '${parameters.id}'
                     },
                    credits:{
                        enabled:false
                    }
                });
                if(typeof(callback) == 'function')
                {
                    callback(${parameters.dataVar});
                }
            <#if parameters.clazz?if_exists != "">}
        });</#if>
    }
    var ${parameters.id}_hasInit;
    <#if parameters.allowInit == "true">
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            ${parameters.id}_init();
            ${parameters.id}_hasInit = true;
        }
    });
    </#if>
</script>
</div>
