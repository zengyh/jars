// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelectOrgUtil.java

package com.sinitek.sirm.org.utils;

import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.spirit.webcontrol.form.RequestData;
import java.util.*;

public class SelectOrgUtil
{

    public SelectOrgUtil()
    {
    }

    public static List parseOrgObjects(Map stringMap, String prefix, String hiddenName)
    {
        List result = new ArrayList();
        if(!"".equals(prefix))
        {
            String value = hiddenName == null || "".equals(hiddenName) ? null : (String)stringMap.get(hiddenName);
            if(value == null || "".equals(value))
                value = (String)stringMap.get((new StringBuilder()).append("_").append(prefix).append("_candidateHidden").toString());
            if(value != null && !"".equals(value))
            {
                String item[] = value.split(",");
                String arr$[] = item;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String org = arr$[i$];
                    String orgitem[] = org.split(":");
                    OrgObject orgObject = new OrgObject();
                    orgObject.setOrgId(orgitem[0]);
                    orgObject.setOrgName(orgitem[1]);
                    orgObject.setOrgType(Integer.valueOf(orgitem[2]).intValue());
                    result.add(orgObject);
                }

            }
        }
        return result;
    }

    public static OrgObject parseOrgObject(Map stringMap, String prefix, String hiddenName)
    {
        OrgObject orgObject = null;
        if(!"".equals(prefix))
        {
            String value = hiddenName == null || "".equals(hiddenName) ? null : (String)stringMap.get(hiddenName);
            if(value == null || "".equals(value))
                value = (String)stringMap.get((new StringBuilder()).append("_").append(prefix).append("_candidateHidden").toString());
            if(value != null && !"".equals(value))
            {
                String item[] = value.split(",");
                if(item.length > 0)
                {
                    String org = item[0];
                    String orgitem[] = org.split(":");
                    orgObject = new OrgObject();
                    orgObject.setOrgId(orgitem[0]);
                    orgObject.setOrgName(orgitem[1]);
                    orgObject.setOrgType(Integer.valueOf(orgitem[2]).intValue());
                }
            }
        }
        return orgObject;
    }

    public static OrgObject parseOrgObject(RequestData params, String prefix, String hiddenName)
    {
        OrgObject orgObject = null;
        if(!"".equals(prefix))
        {
            String value = hiddenName == null || "".equals(hiddenName) ? null : params.get(hiddenName);
            if(value == null || "".equals(value))
                value = params.get((new StringBuilder()).append("_").append(prefix).append("_candidateHidden").toString());
            if(value != null && !"".equals(value))
            {
                String item[] = value.split(",");
                if(item.length > 0)
                {
                    String org = item[0];
                    String orgitem[] = org.split(":");
                    orgObject = new OrgObject();
                    orgObject.setOrgId(orgitem[0]);
                    orgObject.setOrgName(orgitem[1]);
                    orgObject.setOrgType(Integer.valueOf(orgitem[2]).intValue());
                }
            }
        }
        return orgObject;
    }
}
