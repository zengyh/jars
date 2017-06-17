// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowRuleService.java

package com.sinitek.sirm.busin.workflow.service.rule;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.IWorkflowProcessRule;
import com.sinitek.sirm.busin.workflow.entity.IWorkflowProcessRuleInfo;
import java.util.Map;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.rule:
//            ProcessRuleQuery, ProcessRuleInfoQuery

public interface IWorkflowRuleService
{

    public abstract IMetaDBQuery findProcessRule(ProcessRuleQuery processrulequery);

    public abstract IMetaDBQuery findProcessRuleInfo(ProcessRuleInfoQuery processruleinfoquery);

    public abstract IWorkflowProcessRule loadProcessRule(int i);

    public abstract void saveProcessRule(IWorkflowProcessRule iworkflowprocessrule);

    public abstract IWorkflowProcessRuleInfo loadProcessRuleInfo(int i);

    public abstract void saveProcessRuleInfo(IWorkflowProcessRuleInfo iworkflowprocessruleinfo);

    public abstract Map pretreatedSubProcessStep(Map map);
}
