// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowDrawService.java

package com.sinitek.sirm.busin.workflow.service.drawprocess;

import com.sinitek.sirm.busin.workflow.entity.drawprocess.*;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public interface IWorkflowDrawService
{

    public abstract int saveFlowNode(IWorkflowFlowNode iworkflowflownode);

    public abstract int saveFlowPaths(IWorkflowFlowPaths iworkflowflowpaths);

    public abstract int saveFlowProps(IWorkflowFlowProps iworkflowflowprops);

    public abstract int saveFlowDots(IWorkflowFlowDots iworkflowflowdots);

    public abstract IWorkflowFlowNode getFlowNode(int i);

    public abstract IWorkflowFlowPaths getFlowPaths(int i);

    public abstract IWorkflowFlowProps getFlowProps(int i);

    public abstract IWorkflowFlowDots getFlowDots(int i);

    public abstract void deleteFlowNode(IWorkflowFlowNode iworkflowflownode);

    public abstract void deleteFlowPaths(IWorkflowFlowPaths iworkflowflowpaths);

    public abstract void deleteFlowProps(IWorkflowFlowProps iworkflowflowprops);

    public abstract void deleteFlowDots(IWorkflowFlowDots iworkflowflowdots);

    public abstract void deleteHistoryData(int i);

    public abstract Map getFlowDataMap(int i);

    public abstract void saveFlowData(int i, Map map)
        throws JSONException;

    public abstract void addFlowProp(String s, String s1, String s2, int i, String s3);

    public abstract List findFlowNode(int i);

    public abstract List findFlowPaths(int i);

    public abstract List findFlowPaths(int i, int j, int k);

    public abstract List findFlowProps(int i, String s);

    public abstract List findFlowProps(int i, String s, String s1);

    public abstract IWorkflowFlowProps findFlowProps(int i, String s, String s1, String s2);

    public abstract Map getFlowPropsMap(int i, String s);

    public abstract List findFlowDots(int i);

    public abstract Boolean checkIsDrawFlow(int i);
}
