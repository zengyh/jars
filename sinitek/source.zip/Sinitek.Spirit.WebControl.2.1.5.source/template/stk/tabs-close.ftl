</UL>
</div>
<script type="text/javascript">
    $("#${parameters.id?html}_wrap").append($("#${parameters.id?html}").find("div[class='stk-ui-tabs-con'][parent='${parameters.id}']"));
    var param = {};
    <#if parameters.tabWidth?if_exists != "">param.tabWidth=${parameters.tabWidth?html};</#if><#rt/>
    $( "#${parameters.id?html}_wrap" ).stktabs(param);
    if(typeof(${parameters.id}_bindTabEvent) == 'function')
    {
        ${parameters.id}_bindTabEvent();
    }
    <#if parameters.selected?if_exists != "">
    $( "#${parameters.id?html}_wrap" ).stktabsselect(${parameters.selected});
    </#if>
    $("#${parameters.id?html}").find("li").css("overflow-x","hidden");
    $.each( $( "#${parameters.id?html}_wrap" ).find( "div[cn='tablefix']" ), function ( i, n )
    {
        var tableId = $( n ).attr( "id" ).replaceAll( "_wrap", "" );
        $( "#" + tableId ).stk_adjust();
    } );
</script>