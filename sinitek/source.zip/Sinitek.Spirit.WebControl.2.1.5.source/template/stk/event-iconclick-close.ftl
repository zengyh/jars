
    }
    if(!stk.eventMap.hasBind("${parameters.targetId}","${parameters.eventName}"))
    {
        $("#${parameters.targetId}_icon").bind("click", function() { ${parameters.targetId}_${parameters.eventName}($("#${parameters.targetId}")) });
        stk.eventMap.add("${parameters.targetId}","${parameters.eventName}");
    };
</script>
</div>