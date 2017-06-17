<script type="text/javascript">
    var ${parameters.formId}_checkers;
    if(${parameters.formId}_checkers == undefined)
    {
        ${parameters.formId}_checkers = $("div[formId='${parameters.formId}'][type='checkers']");
        $.each($(${parameters.formId}_checkers).find("span[type='checker']"), function(i, n)
        {
            if($(n).attr("validateOnBlur") == "true" || ($(n).attr("validateOnBlur") != "false" && $(${parameters.formId}_checkers).attr("validateOnBlur") == "true") && $(${parameters.formId}_checkers).attr("msgTarget") != "alert")
            {
               stk.form.setCheckerOnBlur('${parameters.formId}',$(n).attr("target"));
            }
            if($(n).attr("allowBlank") == "false")
            {
                var target = stk.form.getCheckTarget("${parameters.formId}",$(n).attr("target"));
                if(target != undefined)
                {
                    var current = target;
                    for( var i = 0;i < 7; i++)
                    {
                        current = $(current).parent();
                        try
                        {
                        if(current && $(current).attr("cn") == "boxcell")
                        {
                            var labelObj = $(current).children("table").find("td").eq(0);
                            var label = $(labelObj).html();
                            if(label.substr(label.length-1,1) == ":" || label.substr(label.length-1,1) == "£º")
                            {
                                label = label.substring(0,label.length-1) + "<span style='color:red;font-weight:bold;'>*</span>" + label.substr(label.length-1,1);
                            }
                            else
                                label += "<span style='color:red;font-weight:bold;'>*</span>";
                            $(labelObj).html(label);
                            break;
                        }
                        }catch(e){break;}
                    }
                }
            }
        });
    }
</script>
</div>