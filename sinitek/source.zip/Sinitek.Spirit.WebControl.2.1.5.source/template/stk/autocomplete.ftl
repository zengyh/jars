<#include "textfield.ftl" />
<input type='hidden' id="${parameters.id}_keyId" name="${parameters.id}_keyId">
<script type="text/javascript">
    $(document).ready(function(){
        if(window["${parameters.id}_ac_hasInit"] == undefined)
        {
            var callback;
            if(typeof(${parameters.id}_dataselect) == 'function') callback = ${parameters.id}_dataselect;
            <#if parameters.interactFunction?if_exists != "">
                callback = ${parameters.interactFunction},
            </#if>
            $("#${parameters.id}").stk_autocomplete({
                clazz:'${parameters.clazz}',
                callback:function(data){if(typeof(callback)=='function')callback(data,"${parameters.componentName}")},
                <#if parameters.delay?if_exists != "">
                delay:${parameters.delay},
                </#if>
                <#if parameters.jsParam?if_exists != "">
                extraParams:${parameters.jsParam},
                </#if>
                minChars:${parameters.minChars},
                showId:${parameters.showId},
                scrollHeight:${parameters.scrollHeight},
                maxNum:${parameters.maxNum},
                width:${parameters.selectWidth},
                showOnFocus:${parameters.showOnFocus},
                keyId:"${parameters.id}_keyId"
            });
            window["${parameters.id}_ac_hasInit"] = true;
        }
    });

</script>