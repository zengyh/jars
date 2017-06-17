
</div>
<div class="stk-page-bar-st1" id="${parameters.id?html}_pageInfo" style="display:none;text-align:center;"></div><#rt/>
<div class="stk-ui-bar stk-table-opt-bar" style="display:none;"><ul id="${parameters.id?html}_bottomBar" class="btn-box"></ul></div>
<#if parameters.allowInit?exists && parameters.allowInit=="true"><div name='__wait' style="text-align:center;"><div class='stk_icon_wait'></div></div></#if><#rt/>
</div>
<script type="text/javascript">
    var _${parameters.id};
    function ${parameters.id}_tableInit()
    {
        _${parameters.id} = {};
        _${parameters.id}.id = "${parameters.id}";
        _${parameters.id}.tableConfig = stk.table.getTableConfig("${parameters.id}");
        _${parameters.id}.columnConfig = stk.table.getColumnConfig("${parameters.id}");
        _${parameters.id}.orderOrd = {};
        var allowInit = _${parameters.id}.tableConfig.allowInit;
        stk.table.initEmptyBody(_${parameters.id});
        if(allowInit == "true") ${parameters.id}_query();
    }

    if(_${parameters.id} == undefined) ${parameters.id}_tableInit();

    function ${parameters.id}_init(func){
        stk.table.beforeInit(_${parameters.id});
        stk.table.init(_${parameters.id},function(result){
            _${parameters.id}.data = result.data;
            _${parameters.id}.htmlData = result.htmlData;
            _${parameters.id}.pageInfo = result.pageInfo;
            _${parameters.id}.resultType = result.resultType;
            stk.table.config["${parameters.id}"] = _${parameters.id};
            ${parameters.id}_collect();
            stk.table.afterInit(_${parameters.id});
            if(typeof(func) == 'function') func();
        });
    }

    function ${parameters.id}_jump(targetPage,allPage,func){
        if(typeof(targetPage) == "object") {
            targetPage = $(targetPage).val();
            if(!StringUtils.isAllNumber(targetPage) || targetPage == "0") targetPage = 1;
            if(new Number(targetPage) > allPage) targetPage = allPage;
        }
        stk.table.beforeInit(_${parameters.id},true);
        stk.table.jump(_${parameters.id},targetPage,function(result){
            _${parameters.id}.data = result.data;
            _${parameters.id}.htmlData = result.htmlData;
            _${parameters.id}.pageInfo = result.pageInfo;
            _${parameters.id}.resultType = result.resultType;
            stk.table.config["${parameters.id}"] = _${parameters.id};
            ${parameters.id}_collect();
            stk.table.afterInit(_${parameters.id},true);
            if(typeof(func) == 'function') func();
        });
    }

    function ${parameters.id}_query(func){
        stk.table.beforeInit(_${parameters.id});
        stk.table.query(_${parameters.id},function(result){
            _${parameters.id}.data = result.data;
            _${parameters.id}.htmlData = result.htmlData;
            _${parameters.id}.resultType = result.resultType;
            _${parameters.id}.pageInfo = result.pageInfo;
            stk.table.config["${parameters.id}"] = _${parameters.id};
            ${parameters.id}_collect();
            stk.table.afterInit(_${parameters.id});
            if(typeof(func) == 'function') func();
        });
    }

    function ${parameters.id}_setSort(td){
        stk.table.setSort(td,function(type,property,orderType){
            if(type=="normal")
            {
                if(orderType != undefined)
                {
                    _${parameters.id}.orderOrd = {};
                    _${parameters.id}.orderOrd[property] = orderType+"_0";
                }
                else
                {
                    _${parameters.id}.orderOrd = {};
                }
                if(typeof(${parameters.id}_tableorder) == 'function')
                    ${parameters.id}_tableorder(property,orderType);
            }
            else
            {
                if(orderType != undefined)
                {
                    if(_${parameters.id}.orderOrd[property] == undefined || _${parameters.id}.orderOrd[property] == null)
                    {
                        var num = 0;
                        for (var key in _${parameters.id}.orderOrd)
                        {
                            if(_${parameters.id}.orderOrd[key] != undefined)
                                num ++;
                        }
                        _${parameters.id}.orderOrd[property] = orderType+"_" + num;
                    }
                    else
                    {
                        var num = _${parameters.id}.orderOrd[property].split("_")[1];
                        _${parameters.id}.orderOrd[property] = orderType+"_"+num;
                    }
                }
                else
                {
                    _${parameters.id}.orderOrd[property] = undefined;
                }
                if(typeof(${parameters.id}_tableorder) == 'function')
                {
                    var _property = [];
                    var _orderType = [];
                    if(_${parameters.id}.orderOrd)
                    {
                        for (var key in _${parameters.id}.orderOrd)
                        {
                            if(_${parameters.id}.orderOrd[key] != undefined)
                            {
                                var num = _${parameters.id}.orderOrd[key].split("_")[1];
                                var sort = _${parameters.id}.orderOrd[key].split("_")[0];
                                if(num && sort)
                                {
                                    _property[new Number(num)] = key;
                                    _orderType[new Number(num)] = sort;
                                }
                            }
                        }
                    }
                    ${parameters.id}_tableorder(_property,_orderType);
                }
            }
            if(_${parameters.id}.resultType == 'metadb')
                ${parameters.id}_query();
        });
    }

    function ${parameters.id}_collect(){
        stk.table.genTableCollect(_${parameters.id},function(result){
            _${parameters.id}.collectData = result.data;
        });
    }

    function ${parameters.id}_getCollectData(callBack){
        if (jQuery.isFunction(callBack)) callBack(_${parameters.id}.collectData);
        return _${parameters.id}.collectData;
    }

    function ${parameters.id}_resetPageSize(pageSize){
        if(typeof(pageSize)=='object') pageSize = $(pageSize).val();
        $("#${parameters.id}_wrap").attr("pageSize",pageSize);
        _${parameters.id}.tableConfig = stk.table.getTableConfig("${parameters.id}");
        <#if parameters.allowConfigPageSize=="true">
            stk.table.savePageSize("${parameters.id}",pageSize);
        </#if>
        ${parameters.id}_query();
    }
    stk.table.addExport("${parameters.id?html}");
    stk.table.addDetail("${parameters.id?html}");
</script>