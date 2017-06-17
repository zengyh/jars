// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowClaimService.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.List;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ClaimSubject, ExampleAskForQuery, ExampleTaskQuery, AskForStart, 
//            AskForSubmit, AskForMain

public interface IWorkflowClaimService
{

    public abstract ClaimSubject getClaimSubject(int i);

    public abstract ClaimSubject getClaimSubjectBySyscode(String s);

    public abstract ClaimSubject getClaimSubjectByExampleId(int i);

    public abstract ClaimSubject getClaimSubjectByExampleOwnerId(int i);

    public abstract void saveClaimSubject(ClaimSubject claimsubject);

    public abstract IMetaDBQuery findExampleAskFors(ExampleAskForQuery exampleaskforquery);

    public abstract IMetaDBQuery findExampleTasks(ExampleTaskQuery exampletaskquery);

    public abstract int getExampleTaskCount(int i);

    public abstract List getHomePageTasks(int i);

    public abstract void startAskFor(AskForStart askforstart);

    public abstract void submitAskFor(AskForSubmit askforsubmit);

    public abstract AskForSubmit loadAskForSubmit(int i);

    public abstract List findAskForSubmits(int i);

    public abstract AskForMain loadAskForMain(int i);
}
