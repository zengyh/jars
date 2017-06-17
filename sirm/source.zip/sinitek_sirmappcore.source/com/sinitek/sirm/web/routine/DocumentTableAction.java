// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentTableAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.IRTDocuments;
import com.sinitek.sirm.busin.routine.func.DocumentRightFunction;
import com.sinitek.sirm.busin.routine.service.BaseRoutineServiceFactory;
import com.sinitek.sirm.busin.routine.service.IRTDocumentsService;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class DocumentTableAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DocumentTableAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest httpServletRequest)
        throws Exception
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        String type = String.valueOf(map.get("type"));
        if(!"null".equals(type) && "1".equals(type))
        {
            String ids = "";
            String tempid = "";
            Iterator i$ = maps.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _map = (Map)i$.next();
                tempid = StringUtil.safeToString(_map.get("objid"), "");
                if(!"".equals(tempid))
                {
                    if(!"".equals(ids))
                        ids = (new StringBuilder()).append(ids).append("-").toString();
                    ids = (new StringBuilder()).append(ids).append(tempid).toString();
                }
            } while(true);
            return ids;
        }
        IRTDocuments documents;
        for(Iterator i$ = maps.iterator(); i$.hasNext(); documentsService.saveDocument(documents))
        {
            Map objids = (Map)i$.next();
            int objid = NumberTool.convertMapKeyToInt(objids, "objid").intValue();
            documents = documentsService.getDocumentById(objid);
            documents.setStatus(Integer.valueOf(0));
        }

        return "";
    }

    public Object getResult(Map stringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        int directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        String companytype = (String)stringMap.get("companytype");
        IOrgService orgService = OrgServiceFactory.getOrgService();
        String documenter = RequestContext.getCurrentUser().getOrgId();
        List alllist = BaseRoutineServiceFactory.getDocumentsService().findAllDocuments(1, directoryid, "".equals(companytype) || !"true".equals(companytype) ? documenter : null);
        List documentslist = new ArrayList();
        Iterator i$;
        if(!"".equals(companytype) && "true".equals(companytype))
        {
            i$ = alllist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map documentMap = (Map)i$.next();
                int documentid = NumberTool.convertMapKeyToInt(documentMap, "objid").intValue();
                if(DocumentRightFunction.checkDocumentRight(documenter, documentid, "READ"))
                    documentslist.add(documentMap);
            } while(true);
        } else
        {
            documentslist = alllist;
        }
        i$ = documentslist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map map = (Map)i$.next();
            Employee employee = orgService.getEmployeeById(String.valueOf(map.get("Documenter")));
            if(employee != null)
                map.put("DocumenterName", employee.getEmpName());
        } while(true);
        String order = (String)stringMap.get("order");
        if(order != null && !"".equals(order))
        {
            String name = order.substring(0, order.indexOf(":"));
            Collections.sort(documentslist, new ListComparator(name, order.substring(order.indexOf(":") + 1)));
        } else
        {
            Collections.sort(documentslist, new ListComparator("documentname", "asc"));
        }
        return documentslist;
    }

    public String setDefaultOrderBy(Map stringStringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        return null;
    }
}
