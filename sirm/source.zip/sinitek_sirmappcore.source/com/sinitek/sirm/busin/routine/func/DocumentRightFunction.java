// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentRightFunction.java

package com.sinitek.sirm.busin.routine.func;

import com.sinitek.sirm.busin.routine.entity.IRTDirectory;
import com.sinitek.sirm.busin.routine.entity.IRTDocuments;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.right.server.entity.IRightAuth;
import java.util.*;

public class DocumentRightFunction
{

    public DocumentRightFunction()
    {
    }

    public static boolean checkDocumentRight(String orgid, int documentid, String rightauth)
    {
        boolean result;
label0:
        {
            IRTDocumentAuthService irtDocumentAuthService;
            IRTDocuments rtDocument;
label1:
            {
                result = false;
                irtDocumentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
                if(orgid == null || "".equals(orgid) || documentid == 0)
                    break label0;
                List ids = new ArrayList();
                rtDocument = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
                if(rtDocument != null && orgid.equals(rtDocument.getDocumenter()))
                    return true;
                if(rightauth == null || "".equals(rightauth) || result)
                    break label0;
                List list = OrgServiceFactory.getRightService().findEnableAuthedObjects(orgid, "RTDOCUMENTS", rightauth, new ArrayList());
                if(list == null || list.size() <= 0)
                    break label1;
                Iterator i$ = list.iterator();
                IRightAuth iRightAuth;
                do
                {
                    if(!i$.hasNext())
                        break label1;
                    iRightAuth = (IRightAuth)i$.next();
                } while(!iRightAuth.getObjectKey().equals(StringUtil.safeToString(Integer.valueOf(rtDocument.getId()), "")) || !irtDocumentAuthService.checkAuth(iRightAuth.getAuthOrgId(), iRightAuth.getObjectKey(), "RTDOCUMENTS"));
                return true;
            }
            List list1 = OrgServiceFactory.getRightService().findEnableAuthedObjects(orgid, "RTDIRECTORY", rightauth, new ArrayList());
            if(list1 == null || list1.size() <= 0)
                break label0;
            Iterator i$ = list1.iterator();
            IRightAuth iRightAuth;
            do
            {
                if(!i$.hasNext())
                    break label0;
                iRightAuth = (IRightAuth)i$.next();
            } while(!iRightAuth.getObjectKey().equals(StringUtil.safeToString(rtDocument.getDirectoryId(), "")) || !irtDocumentAuthService.checkAuth(iRightAuth.getAuthOrgId(), iRightAuth.getObjectKey(), "RTDIRECTORY"));
            return true;
        }
        return result;
    }

    public static boolean checkDirectoryRight(String orgid, int directoryid, String rightauth)
    {
        boolean result;
label0:
        {
            result = false;
            IRTDocumentAuthService irtDocumentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
            if(orgid == null || "".equals(orgid) || directoryid == 0)
                break label0;
            List ids = new ArrayList();
            IRTDirectory irtDirectory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(directoryid);
            if(irtDirectory != null)
            {
                if(irtDirectory.getDirectoryer().equals(orgid))
                    return true;
            } else
            {
                return false;
            }
            if(rightauth == null || "".equals(rightauth))
                break label0;
            List list = OrgServiceFactory.getRightService().findEnableAuthedObjects(orgid, "RTDIRECTORY", rightauth, new ArrayList());
            if(list == null || list.size() <= 0)
                break label0;
            Iterator i$ = list.iterator();
            IRightAuth iRightAuth;
            do
            {
                if(!i$.hasNext())
                    break label0;
                iRightAuth = (IRightAuth)i$.next();
            } while(!iRightAuth.getObjectKey().equals(StringUtil.safeToString(Integer.valueOf(directoryid), "")) || !irtDocumentAuthService.checkAuth(iRightAuth.getAuthOrgId(), iRightAuth.getObjectKey(), "RTDIRECTORY"));
            return true;
        }
        return result;
    }

    public static boolean checkDirectoryRight(String orgid, int directoryid, String rightauths[])
    {
        String arr$[] = rightauths;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String rightauth = arr$[i$];
            boolean result = checkDirectoryRight(orgid, directoryid, rightauth);
            if(result)
                return true;
        }

        return false;
    }

    public static boolean checkDocumentRight(String orgid, int documentid, String rightauths[])
    {
        String arr$[] = rightauths;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String righauth = arr$[i$];
            boolean result = checkDocumentRight(orgid, documentid, righauth);
            if(result)
                return true;
        }

        return false;
    }
}
