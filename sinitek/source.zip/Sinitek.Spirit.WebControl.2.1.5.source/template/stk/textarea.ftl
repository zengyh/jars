
<span <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
<#include "inc_textfield_wrap.ftl" />
<#if parameters.rows?if_exists != ""> rows="${parameters.rows?html}"</#if><#rt/>
<#if parameters.cols?if_exists != ""> cols="${parameters.cols?html}"</#if><#rt/>
  style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
"<#rt/>
>
    <textarea onfocus="stk.stopSubmit = true" onblur="stk.stopSubmit = false"  class="stk-textarea" cols="100" <#rt/>
<#include "inc_component_property.ftl" />
<#include "inc_field_property.ftl" />
<#include "inc_textfield_property.ftl" />
 rows="${parameters.rows?default("")?html}"<#rt/>
 style="<#rt/>
<#if parameters.width?if_exists != "">width:${parameters.width?html};</#if><#rt/>
 "<#rt/>
><#if parameters.sDefaultValue?if_exists != ""><@s.property value="parameters.oDefaultValue"/></#if></textarea>