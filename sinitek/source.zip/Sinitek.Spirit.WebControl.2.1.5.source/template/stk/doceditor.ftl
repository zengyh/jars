
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#if parameters.type?if_exists != ""> inputType="${parameters.type?html}"</#if><#rt/>
 style="<#include "inc_component_style.ftl" />"<#rt/>
>
    <textarea id="${parameters.id?html}" style="<#if parameters.width?if_exists != ""> width:${parameters.width?html};</#if><#if parameters.height?if_exists != ""> height:${parameters.height?html};</#if>visibility:hidden;">
    </textarea>
    <script type="text/javascript">
      var ${parameters.id?html}_hasInit;
      $(document).ready(function(){
          if (${parameters.id?html}_hasInit == undefined)
          {
              ${parameters.id?html}_hasInit = true;
              KE.init({
                  <#if parameters.readOnly?exists && parameters.readOnly=='true'>
                  afterCreate : function(id) {
                                      KE.toolbar.disable(id, []);
                                      KE.readonly(id);
                                      KE.g[id].newTextarea.disabled = true;
                                  },
                  </#if><#rt/>
                  <#if parameters.items?if_exists != "">
                  items:[${parameters.items}],
                  </#if><#rt/>
                  cssPath : '${parameters.contextPath}/framework/kindeditor/skins/default.css',
                  <#if parameters.imageUploadJson?if_exists != "">imageUploadJson : '${parameters.contextPath}${parameters.imageUploadJson?html}',</#if>
                  id : '${parameters.id?html}'
              });
              KE.create('${parameters.id?html}');
              KE.html('${parameters.id?html}',"");
        <#if parameters.sDefaultValue?if_exists != "">
            ${parameters.id?html}_defaultValue  = ${parameters.sDefaultValue?html};
            $("#${parameters.id?html}").stk_val(${parameters.id?html}_defaultValue);
        </#if>
          }
      });
    </script>