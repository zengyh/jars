
    }
    function ${parameters.targetId}_bindTabEvent()
    {
        if(!stk.eventMap.hasBind("${parameters.targetId}","${parameters.eventName}"))
        {
            $( "#${parameters.targetId}_wrap" ).bind( "${parameters.eventName}", function(event, id, title) {
                var param = {};
                param.id = id.replace("#","");
                param.title = title;
                ${parameters.targetId}_${parameters.eventName}(param);
            });
            stk.eventMap.add("${parameters.targetId}","${parameters.eventName}");
        };
    }
</script>
</div>