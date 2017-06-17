
</div>
<script type="text/javascript">
    $(document).ready(function(){
    <#if parameters.position == "top">
        <#if parameters.hidden != "true">$("#${parameters.tableId?html}_topBar").parent().show();</#if>
        $("#${parameters.id?html}_wrap").find('li').prependTo($("#${parameters.tableId?html}_topBar"));
    </#if>
    <#if parameters.position == "bottom">
        <#if parameters.hidden != "true">$("#${parameters.tableId?html}_bottomBar").parent().show();</#if>
        $("#${parameters.id?html}_wrap").find('li').prependTo($("#${parameters.tableId?html}_bottomBar"));
    </#if>
        $("#${parameters.id?html}_wrap").remove();
    });
</script>