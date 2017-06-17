// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KeyReaderAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.IMessageTemplate;
import com.sinitek.sirm.busin.routine.entity.IRT_KeyReader;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.busin.routine.utils.MessageContext;
import com.sinitek.sirm.busin.routine.utils.MessageReceiver;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.ListComparator;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class KeyReaderAction
    implements ITableAware, ICheckBoxPluginAware
{

    public KeyReaderAction()
    {
    }

    public Object getResult(Map stringStringMap, HttpServletRequest request)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        List keyReaderList = new ArrayList();
        int directoryid = NumberTool.convertMapKeyToInt(stringStringMap, "directoryid").intValue();
        int documentid = NumberTool.convertMapKeyToInt(stringStringMap, "documentid").intValue();
        keyReaderList = documentAuthService.findKeyReaderByDocumentId(documentid).getResult();
        String order = (String)stringStringMap.get("order2");
        if(order != null && !"".equals(order))
        {
            String name = order.substring(0, order.indexOf(":"));
            Collections.sort(keyReaderList, new ListComparator(name, order.substring(order.indexOf(":") + 1)));
        } else
        {
            Collections.sort(keyReaderList, new ListComparator("readerName", "asc"));
        }
        return keyReaderList;
    }

    public String setDefaultOrderBy(Map stringStringMap, HttpServletRequest request)
        throws Exception
    {
        return null;
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest request)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRT_KeyReader keyReader;
        for(Iterator i$ = maps.iterator(); i$.hasNext(); documentAuthService.delDocumentKeyReader(keyReader))
        {
            Map objids = (Map)i$.next();
            int objid = NumberTool.convertMapKeyToInt(objids, "objid").intValue();
            keyReader = documentAuthService.getKeyReaderById(objid);
        }

        return "";
    }

    public Object submitRelpy(Map stringStringMap, HttpServletRequest request)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService;
        String reply;
        IRT_KeyReader keyReader;
        Map result;
        documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        reply = (String)stringStringMap.get("reply");
        int documentid = NumberTool.convertMapKeyToInt(stringStringMap, "documentid").intValue();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        keyReader = documentAuthService.getKeyReaderByAuthIdAndReaderId(documentid, orgid);
        result = new HashMap();
        if(keyReader == null)
        {
            result.put("info", "\u60A8\u4E0D\u662F\u6307\u5B9A\u7684\u91CD\u70B9\u9605\u8BFB\u4EBA,\u4E0D\u80FD\u63D0\u4EA4\u56DE\u590D");
            return result;
        }
        Map result;
        try
        {
            keyReader.setReply(reply != null ? reply : "");
            documentAuthService.saveKeyReader(keyReader);
            result.put("info", "\u63D0\u4EA4\u6210\u529F");
            return result;
        }
        catch(Exception e)
        {
            result = new HashMap();
        }
        result.put("info", "\u63D0\u4EA4\u5931\u8D25");
        return result;
    }

    public static void sendRemindMessage(String code, Map remindInfo)
    {
        String recieverNames = String.valueOf(remindInfo.get("recieverNames"));
        RequestUser user = RequestContext.getCurrentUser();
        IMessageService _msgService = CommonServiceFactory.getMessageService();
        IMessageTemplate messageTemplate = _msgService.getMessageTemplateByCode(code);
        MessageContext context = new MessageContext();
        if(messageTemplate != null)
        {
            context.setCode(code);
            List receivers = new ArrayList();
            String arr$[] = recieverNames.split(",");
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String author = arr$[i$];
                if(author.split(":").length < 2)
                    continue;
                Employee employee = OrgServiceFactory.getOrgService().getEmployeeById(author.split(":")[0]);
                if(employee != null)
                {
                    MessageReceiver mr = new MessageReceiver();
                    mr.setMobile(employee.getMobilePhone());
                    mr.setEmail(employee.getEmail());
                    mr.setEmpId(employee.getId());
                    receivers.add(mr);
                }
                context.setReceivers(receivers);
                context.setSendId(user.getOrgId());
            }

            context.addParam("title", remindInfo.get("title"));
            context.addParam("context", remindInfo.get("context"));
            CommonServiceFactory.getMessageEngineService().sendMessage(context);
            LOGGER.info("\u53D1\u9001\u5B8C\u6210");
        }
    }

    private static final Logger LOGGER = Logger.getLogger("KeyReaderAction");

}
