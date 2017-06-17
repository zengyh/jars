<div <#rt/>
<#include "inc_component_wrap.ftl" />
 style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if><#rt/>
<#include "inc_component_style.ftl" />
"<#rt/>
>
<div id="${parameters.id}" class='stk_tree' style="width:${parameters.width};<#rt/>
<#if parameters.height?if_exists != ""> height:${parameters.height}<#rt/></#if>
"></div><#rt/>
<script type="text/javascript">
    var ${parameters.id}_hasLoad;
    var ${parameters.id}_config = {
        <#if parameters.clazz?if_exists != "">
        clazz:'${parameters.clazz}',
        </#if>
        <#if parameters.rootText?if_exists != "">
        rootText:'${parameters.rootText}',
        </#if>
        <#if parameters.rootIcon?if_exists != "">
        rootIcon:'${parameters.rootIcon}',
        </#if>
        <#if parameters.rootExpanded?if_exists != "">
        rootExpanded:${parameters.rootExpanded},
        </#if>
        <#if parameters.hrefTarget?if_exists != "">
        hrefTarget:'${parameters.hrefTarget}',
        </#if>
        <#if parameters.lines?if_exists != "">
        lines:${parameters.lines},
        </#if>
        <#if parameters.disabled?if_exists != "">
        disabled:${parameters.disabled},
        </#if>
        <#if parameters.hidden?if_exists != "">
        hidden:${parameters.hidden},
        </#if>
        <#if parameters.singleExpand?if_exists != "">
        singleExpand:${parameters.singleExpand},
        </#if>
        id:"root",
        end:false
    };
    function ${parameters.id}_buildTree(nodeId)
    {
        var data =new Array();
        <#if parameters.jsList?if_exists != "">
            data = ${parameters.jsList};
        </#if>

        var param = {};
        <#if parameters.jsParam?if_exists != "">
            if(typeof(${parameters.jsParam}) == "function")
                param = ${parameters.jsParam}();
        </#if>

        <#if parameters.clazz?if_exists != "">
        SpiritTreeAction.genTree(${parameters.id}_config,param,{
            callback:function(result) {
                data = result.children;
                if(!data) data = {};
        </#if>

        stk.tree.build("${parameters.id}", data, ${parameters.id}_config<#if parameters.jsParam?if_exists != ""> ,"${parameters.jsParam}"</#if><#rt/>);
        if(StringUtils.isNotBlank(nodeId)){
            var node = $('#${parameters.id}').find("div[nodeId='" + nodeId + "']")[0];
            $(node).removeClass("stk_tree_icon_open");
            $(node).addClass("stk_tree_icon_close");
            stk.tree.changeNode(node, true, '${parameters.id}');
        }
    <#if parameters.clazz?if_exists != "">
            }
        });
        </#if>
    }
    if(${parameters.id}_hasLoad == undefined)
    {
        ${parameters.id}_buildTree();
        ${parameters.id}_hasLoad = true;
    }
</script>