<LI class="stk-ui-tabs-item"><A href="#${parameters.id?html}">${parameters.title?html}</A></LI>
<div class="stk-ui-tabs-con" name="tab" <#rt/>
 id="${parameters.id?html}" parent="${parameters.parentId?html}"
<#include "inc_box_wrap.ftl" />
 style="padding-top:5px;<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
<#if parameters.height?if_exists != "">min-height:${parameters.height?html};_height:${parameters.height?html};</#if><#rt/>
<#rt/>
<#if parameters.align?if_exists != "">text-align:${parameters.align?html};</#if><#rt/>
"<#rt/>
>
    <div class="stk_box_layout_${parameters.layout?html}">
<#if parameters.hidden?if_exists != "" && parameters.hidden=="true">
<script type="text/javascript">
    $("#${parameters.parentId?html}").stk_hide("${parameters.id?html}");
</script>
</#if>
