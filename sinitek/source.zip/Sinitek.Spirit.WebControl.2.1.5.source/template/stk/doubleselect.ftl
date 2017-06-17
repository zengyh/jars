<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_select_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />"
>
   <div id="${parameters.id}"></div>

<script type="text/javascript">
    var ${parameters.id}_listCache;
    function ${parameters.id}_init(list,data,callback)
    {
        var setting = {};
            setting.selectCfg1 = {
<#if parameters.title?if_exists != "">title:'${parameters.title}',</#if><#rt>
<#if parameters.name?if_exists != "">name:'${parameters.name}_l',</#if><#rt>
<#if parameters.width?if_exists != "">width:${parameters.width},</#if><#rt>
id:'${parameters.id}_l'
            };
            setting.selectCfg2 = {
<#if parameters.selectTitle?if_exists != "">title:'${parameters.selectTitle}',</#if><#rt>
<#if parameters.name?if_exists != "">name:'${parameters.name}_r',</#if><#rt>
<#if parameters.selectWidth?if_exists != "">width:${parameters.selectWidth},</#if><#rt>
id:'${parameters.id}_r'
            };
            <#if parameters.height?if_exists != "">setting.selectHeight = ${parameters.height}</#if><#rt>

            setting.data = [
            <@s.iterator value="parameters.list">
                <#if parameters.listKey?if_exists != "">
                    <#assign itemKey = stack.findValue(parameters.listKey)/>
                <#else>
                    <#assign itemKey = stack.findValue('top')/>
                </#if>
                <#assign itemKeyStr = itemKey.toString() />
                <#if parameters.listValue?if_exists != "">
                    <#assign itemValue = stack.findString(parameters.listValue)/>
                <#else>
                    <#assign itemValue = stack.findString('top')/>
                </#if>
                {name:'${itemValue?html}',value:'${itemKeyStr?html}'},<#lt/>
            </@s.iterator>
                {}];

            if(setting.data.length > 1)
            {
                var tmp = [];
                for(var i = 0; i < setting.data.length-1; i++) tmp.push(setting.data[i]);
                setting.data = tmp;
            }

            <#if parameters.sDefaultValue?exists>
                setting.selData = ${parameters.sDefaultValue?html};
            </#if>
        if(${parameters.id}_listCache) setting.data = ${parameters.id}_listCache;
        if(list && typeof(list) == typeof({})) {setting.data = list;${parameters.id}_listCache = list;}
        if(data && typeof(data) == typeof([])) setting.selData = data;

        $('#${parameters.id}').empty();
        $('#${parameters.id}').selectMultiple(setting);
        $('#${parameters.id}_r').attr("multiValue","1");
        $('#${parameters.id}_l').attr("multiValue","1");
        if(typeof(callback) == 'function') callback();
    }
    var ${parameters.id}_hasInit;
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            ${parameters.id}_init();

            ${parameters.id}_hasInit = true;
        }
    });
</script>
