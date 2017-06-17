// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowPublicString.java

package com.sinitek.sirm.busin.workflow.support;

import com.sinitek.sirm.busin.workflow.service.IWorkflowBaseService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

public class WorkflowPublicString
{

    public WorkflowPublicString()
    {
        systemname = "\u7CFB\u7EDF";
        judgebyself = "\u5904\u7406\u4EBA\u4E3A\u53D1\u8D77\u4EBA";
        judgebysystem = "\u7CFB\u7EDF\u81EA\u52A8\u901A\u8FC7";
        firsttime = "1000-01-01 00:00:00";
        lasttime = "3000-01-01 00:00:00";
        namemaxlength = 61;
        opinionmaxlength = 40;
        movenullstring = "\u65E0\u6570\u636E";
        movenullint = 0;
        breifmaxlength = 61;
        templateaddword = "\u9A73\u56DE\u540E\u63D0\u4EA4";
        shownullopinion = "";
        stepbriefmaxlenth = 9;
        paramaxlength = 121;
        recoverstepname = "\u64A4\u56DE";
        opinionsavemaxlength = 660;
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        Map map = baseService.findParaMapByNameKey("OpinionShowLength", 1);
        opinionmaxlength = NumberTool.safeToInteger(map.get("value"), Integer.valueOf(-1)).intValue();
        if(opinionmaxlength < 0)
            opinionmaxlength = 40;
    }

    public int getOpinionSaveMaxLength()
    {
        return opinionsavemaxlength;
    }

    public int getParaMaxLength()
    {
        return paramaxlength;
    }

    public int getStepBriefMaxLength()
    {
        return stepbriefmaxlenth;
    }

    public String getShowNullOpinion()
    {
        return shownullopinion;
    }

    public String getTemplateAddWord()
    {
        return templateaddword;
    }

    public String getSystemName()
    {
        return systemname;
    }

    public String getJudgeBySelf()
    {
        return judgebyself;
    }

    public String getJudgeBySystem()
    {
        return judgebysystem;
    }

    public String getFirstTime()
    {
        return firsttime;
    }

    public String getLastTime()
    {
        return lasttime;
    }

    public int getNameMaxLength()
    {
        return namemaxlength;
    }

    public int getOpinionMaxLength()
    {
        return opinionmaxlength;
    }

    public String getMoveNullString()
    {
        return movenullstring;
    }

    public int getMoveNullInt()
    {
        return movenullint;
    }

    public int getBreifMaxLength()
    {
        return breifmaxlength;
    }

    public String getRecoverStepName()
    {
        return recoverstepname;
    }

    private String systemname;
    private String judgebyself;
    private String judgebysystem;
    private String firsttime;
    private String lasttime;
    private int namemaxlength;
    private int opinionmaxlength;
    private String movenullstring;
    private int movenullint;
    private int breifmaxlength;
    private String templateaddword;
    private String shownullopinion;
    private int stepbriefmaxlenth;
    private int paramaxlength;
    private String recoverstepname;
    private int opinionsavemaxlength;
    public static final String AGENT_STEP_TYPE = "StepType";
    public static final String AGENT_STEP_SPECIAL2 = "StepSpecial_2";
}
