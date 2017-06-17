</label>
</span>
<script type="text/javascript">
    var ${parameters.id}_hasInit;
    if (${parameters.id}_hasInit == undefined) {
        SpiritCommonAction.getNowDate({
            callback:function(result) {
                $('#${parameters.id}').calendarClock({
                    dateTimeFormat:"${parameters.format}",
                    date:result
                });
            }
        });
        ${parameters.id}_hasInit = true;
    }
</script>