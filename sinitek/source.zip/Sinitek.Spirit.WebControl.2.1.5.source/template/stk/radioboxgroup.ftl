
<div <#rt/>
<#include "inc_component_wrap.ftl" />
<#include "inc_field_wrap.ftl" />
 style="<#include "inc_component_style.ftl" />
 <#if (parameters.width?exists)>width:${parameters.width};</#if><#if (parameters.height?exists)>height:${parameters.height};</#if>"
><div id="${parameters.id?html}" name="${parameters.name?html}" style="overflow:hidden;zoom:1;">
    <#assign itemCount = 0/>
    <#if parameters.list?exists>
        <@s.iterator value="parameters.list">
            <#assign itemCount = itemCount + 1/>
            <#if parameters.listKey?exists>
                <#assign itemKey = stack.findValue(parameters.listKey)/>
            <#else>
                <#assign itemKey = stack.findValue('top')/>
            </#if>
            <#if parameters.listValue?exists>
                <#assign itemValue = stack.findString(parameters.listValue)/>
            <#else>
                <#assign itemValue = stack.findString('top')/>
            </#if>
    <#assign itemKeyStr=itemKey.toString() />
    <div style='float:left;text-align:left;<#if (parameters.columnWidth?exists)>width:${parameters.columnWidth};</#if><#if (parameters.columnHeight?exists)>height:${parameters.columnHeight};</#if><#if (parameters.cssStyle?exists)>${parameters.cssStyle}</#if>'>
    <input type="radio" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}-${itemCount}"<#rt/>
            <#if tag.contains(parameters.checkedValue, itemKey)>
     checked="checked"<#rt/>
            </#if>
            <#if parameters.disabled?default(false)>
     disabled="disabled"<#rt/>
            </#if>
            <#if parameters.title?if_exists != "">
     title="${parameters.title?html}"<#rt/>
            </#if>
    /><label for="${parameters.name?html}-${itemCount}" class="checkboxLabel">${itemValue?html}</label>
    </div>
        </@s.iterator>
    <#else>
      &nbsp;
    </#if>
</div>