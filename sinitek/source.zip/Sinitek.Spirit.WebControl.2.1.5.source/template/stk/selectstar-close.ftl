</span>

<script type="text/javascript">
    var ${parameters.id}_hasInit;
    var _${parameters.id};
    $(document).ready(function(){
        if(${parameters.id}_hasInit == undefined)
        {
            ${parameters.id}_initStar();
            ${parameters.id}_hasInit = true;
        }
    });

    function ${parameters.id}_initStar(curStarts)
    {
        $("#${parameters.id?html}").empty();
        if(curStarts == undefined)
        {
            <#if parameters.sDefaultValue?if_exists != ""> curStarts = ${parameters.oDefaultValue?html}</#if>
            if(curStarts == undefined) curStarts = 0;
        }
        var config = {
            <#if parameters.name?if_exists != "">
            name:"${parameters.name?html}",
            <#else>
            name:"${parameters.id?html}",
            </#if>
            <#if parameters.maxStar?if_exists != ""> maxStarts:${parameters.maxStar?html},</#if>
            <#if parameters.title?if_exists != ""> titles:${parameters.title?html},</#if>
            curStarts:curStarts,
            id:"${parameters.id?html}_keyId"
        };
        $("#${parameters.id?html}").startsScore(config);
    }
</script>