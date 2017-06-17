</tr>
</table>
	</div>
	<div class="stk-con" style="width:100%;min-height:16px;<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if>">
    	<table id="${parameters.id?html}_Con" class="fixHeadTable" style="table-layout:fixed;width:${parameters.tableWidth};">
        <tbody dataType="right">
        </tbody>
        </table>
        <div class="stk-scrollX" style="width:100%">
        <div id="${parameters.id?html}_scrollX" class="stk-scrollBar" style="width:500px;">
            <div class="scrLine"></div>
        </div>
        </div>
        <div class="stk-scrollY" style="<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if>">
        <div id="${parameters.id?html}_scrollY" class="stk-scrollBar">
        	<div class="scrLine" style="<#if parameters.height?if_exists != "">height:${parameters.height?html};</#if>"></div>
       	</div>
        </div>
    </div>
</div>
</div>
<#include "inc_table-close.ftl" />