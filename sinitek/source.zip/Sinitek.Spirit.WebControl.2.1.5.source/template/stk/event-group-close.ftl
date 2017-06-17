
    }
    if(!stk.eventMap.hasBind("${parameters.targetId}","${parameters.eventName}_group"))
    {
        $("#${parameters.targetId}").find("input").bind("${parameters.eventName}", function() { ${parameters.targetId}_${parameters.eventName}(this) });
        stk.eventMap.add("${parameters.targetId}","${parameters.eventName}_group");
    };
</script>
</div>