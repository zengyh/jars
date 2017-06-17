<#include "inc_table.ftl" />
<div class="stk-fixed-th-box" style="padding-left:${parameters.fixWidth?html};" id="${parameters.id?html}" <#if parameters.name?if_exists != ""> name="${parameters.name?html}"</#if>>
<div class="stk-fixed-left" style="width:${parameters.fixWidth?html};">
	<div class="stk-hTL">
		<table id="${parameters.id?html}_HL" style="table-layout:fixed;width:100%" class="fixHeadTable">
            <tr></tr>
	    </table>
	</div>
	<div class="stk-hL" style="<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if>">
    	<table id="${parameters.id?html}_ConFix" class="fixHeadTable" style="table-layout:fixed;width:100%">
        <tbody dataType="left">
        </tbody>
        </table>
    </div>
</div>
<div class="stk-fixed-right" style="width:100%">
	<div class="stk-hT" style="width:100%">
		<table id="${parameters.id?html}_HT" class="fixHeadTable" style="table-layout:fixed;width:${parameters.tableWidth};">
	    <tr>
