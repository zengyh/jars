<div id="${parameters.id}" formId="${parameters.formId}" type="checkers" style="display:none;visibility:hidden;"<#rt/>
<#if parameters.msgTarget?if_exists != ""> msgTarget="${parameters.msgTarget}"</#if><#rt/>
<#if parameters.validateOnBlur?if_exists != ""> validateOnBlur="${parameters.validateOnBlur}"</#if><#rt/>
<#if parameters.alertOnFalse?if_exists != ""> alertOnFalse="${parameters.alertOnFalse}"</#if><#rt/>
<#if parameters.oneByOne?if_exists != ""> oneByOne="${parameters.oneByOne}"</#if><#rt/>>