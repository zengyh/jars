// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDealerRecursive.java

package com.sinitek.sirm.busin.workflow.support.dealer;

import com.sinitek.sirm.busin.workflow.support.IWorkflowDealer;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class WorkflowDealerRecursive
    implements IWorkflowDealer
{

    public WorkflowDealerRecursive()
    {
    }

    public List dealer(Map map, Map returnmap)
    {
        List stepownerlist = (List)map.get("stepownerlist");
        int wsrecursivenum = NumberTool.convertMapKeyToInt(map, "wsrecursivenum", Integer.valueOf(0)).intValue();
        if(wsrecursivenum < 1)
        {
            wsrecursivenum = 1;
            returnmap.put("wsrecursivenum", Integer.valueOf(wsrecursivenum));
        }
        int wsrecursiveownernum = NumberTool.convertMapKeyToInt(map, (new StringBuilder()).append("wsrecursive").append(wsrecursivenum).append("ownernum").toString(), Integer.valueOf(0)).intValue();
        List resultlist = new ArrayList();
        for(int i = 1; i <= wsrecursiveownernum; i++)
        {
            Map ownermap = new HashMap();
            String wsrecursiveowner = StringUtil.safeToString(map.get((new StringBuilder()).append("wsrecursive").append(wsrecursivenum).append("owner").append(i).toString()), "");
            String owenrArr[] = wsrecursiveowner.split(",");
            if(owenrArr.length >= 3)
            {
                ownermap.put("orgid", owenrArr[0]);
                ownermap.put("orgtype", owenrArr[1]);
                ownermap.put("value", owenrArr[2]);
                resultlist.add(ownermap);
            }
        }

        if(resultlist.size() == 0)
            return stepownerlist;
        else
            return resultlist;
    }
}
