// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentAttachmentAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.service.BaseRoutineServiceFactory;
import com.sinitek.sirm.busin.routine.service.IRTDocumentsService;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class DocumentAttachmentAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DocumentAttachmentAction()
    {
    }

    public Object getResult(Map stringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        String type = (String)stringMap.get("type");
        int documentid = NumberTool.convertMapKeyToInt(stringMap, "documentid").intValue();
        int groupid = NumberTool.convertMapKeyToInt(stringMap, "groupid").intValue();
        List attachmentList = new ArrayList();
        if("All".equals(type))
        {
            if(documentid != 0 && groupid != 0)
                attachmentList = documentsService.findAttachmentsById(documentid, groupid).getResult();
        } else
        {
            attachmentList = documentsService.findAllAttachmentsById(documentid).getResult();
        }
        return attachmentList;
    }

    public String setDefaultOrderBy(Map stringStringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        return null;
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest httpServletRequest)
        throws Exception
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        IAttachmentService attachmentService = CommonServiceFactory.getAttachmentService();
        Iterator i$ = maps.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map attachment = (Map)i$.next();
            int objid = NumberTool.convertMapKeyToInt(attachment, "objid").intValue();
            int attachmentid = NumberTool.convertMapKeyToInt(attachment, "attachmentid").intValue();
            if(objid != 0)
            {
                documentsService.removeDocumentAttachmentById(objid);
                if(attachmentid != 0)
                    attachmentService.removeAttachment(attachmentid);
            }
        } while(true);
        return "";
    }

    public String getVersion(Map map, HttpServletRequest httpServletRequest)
    {
        String version = (String)map.get("documentversion");
        if(!"".equals(version) && version != null)
            return (new StringBuilder()).append("V").append(version).toString();
        else
            return "";
    }
}
