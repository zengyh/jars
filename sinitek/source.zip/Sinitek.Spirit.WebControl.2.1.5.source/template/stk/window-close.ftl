
<#include "inc_box_div-close.ftl" />
</div>
<script type="text/javascript">
    <#--$("#${parameters.id}").width($("#${parameters.id}_wrap").width()-28);-->
    <#--$("#${parameters.id}").height($("#${parameters.id}_wrap").height()+5);-->
    <#if parameters.autoOpen?exists && parameters.autoOpen == "true">
    $("#${parameters.id}").stk_show();
    </#if>
</script>