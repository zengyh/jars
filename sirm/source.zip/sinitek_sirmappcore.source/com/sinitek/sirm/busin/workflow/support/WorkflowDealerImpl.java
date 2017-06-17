// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDealerImpl.java

package com.sinitek.sirm.busin.workflow.support;

import java.util.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.support:
//            IWorkflowDealer

public class WorkflowDealerImpl
    implements IWorkflowDealer
{

    public WorkflowDealerImpl()
    {
    }

    public List dealer(Map map, Map returnmap)
    {
        List _list = new ArrayList();
        Map _map = new HashMap();
        _map.put("orgid", "999000640");
        _map.put("orgtype", Integer.valueOf(8));
        _list.add(_map);
        return _list;
    }
}
