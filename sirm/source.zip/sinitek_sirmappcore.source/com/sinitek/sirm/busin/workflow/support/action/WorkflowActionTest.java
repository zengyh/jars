// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionTest.java

package com.sinitek.sirm.busin.workflow.support.action;

import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.support.IWorkflowAction;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.tool.workflow.special.IWorkflowMobileService;
import java.io.PrintStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;

public class WorkflowActionTest
    implements IWorkflowAction
{

    public WorkflowActionTest()
    {
    }

    public void action(Map map, Map returnmap)
    {
        IWorkflowWebService workweb = WorkflowServiceFactory.getWorkflowWebService();
        int exampleid = NumberTool.convertMapKeyToInt(map, "exampleid", Integer.valueOf(0)).intValue();
        int status = workweb.getProcessStatus(exampleid);
        System.out.println((new StringBuilder()).append("\u5F53\u524D\u6D41\u7A0B\u72B6\u6001\u4E3A\uFF1A").append(status).toString());
    }

    public Object test(Map map, HttpServletRequest r)
    {
        int _type = MapUtils.getIntValue(map, "type", 0);
        IWorkflowMobileService _workmobile = WorkflowServiceFactory.getWorkflowMobileService();
        Map _map = new HashMap();
        int num = _workmobile.getUndoExampleTaskNum(RequestContext.getCurrentUser().getOrgId());
        _map.put("sourceid", Integer.valueOf(1));
        _map.put("sourcename", "TEST-2014-06-23");
        List list3 = _workmobile.getExampleInfo(_map);
        if(_type == 3)
            return list3;
        _map = new HashMap();
        if(_type == 0)
        {
            List list = _workmobile.getUndoExampleTask(RequestContext.getCurrentUser().getOrgId(), _map);
            return list;
        }
        if(_type == 8)
        {
            List list2 = _workmobile.getDoneExampleTask(RequestContext.getCurrentUser().getOrgId(), _map);
            return list2;
        }
        if(_type == 96)
        {
            List list = WorkflowServiceFactory.getWorkflowBaseService().getUrlExistList("\u5BA1\u6279\u62A5\u544A", null, null, 1);
            return list;
        }
        if(_type == 99)
        {
            List list = WorkflowServiceFactory.getWorkflowBaseService().getUrlExistList("", "/rschreport/innerreport/approveinnerreport020101.jsp", "/rschreport/innerreport/viewinnerreport.action", 1);
            return list;
        }
        if(_type == 97)
        {
            List list = WorkflowServiceFactory.getWorkflowBaseService().getListExistList("LinkIfType", "\u62A5\u544A\u6761\u4EF6", null, 1);
            return list;
        }
        if(_type == 98)
        {
            List list = WorkflowServiceFactory.getWorkflowBaseService().getListExistList("LinkIfType", "", "com.sinitek.sirm.busin.workflow.support.WorkflowConditionImpl", 1);
            return list;
        } else
        {
            return Integer.valueOf(num);
        }
    }
}
