// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentAuthAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.sirm.org.busin.service.impl.RightServiceImpl;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.sirm.web.routine:
//            KeyReaderAction

public class DocumentAuthAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DocumentAuthAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest httpServletRequest)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        String rightkey = String.valueOf(map.get("entity"));
        if(!"".equals(rightkey))
        {
            IRTDocumentAuth documentAuth;
            for(Iterator i$ = maps.iterator(); i$.hasNext(); documentAuthService.delDocumentAuth(documentAuth))
            {
                Map objids = (Map)i$.next();
                int objid = NumberTool.convertMapKeyToInt(objids, "objid").intValue();
                documentAuth = (IRTDocumentAuth)documentAuthService.getDocumentAuth(objid);
                List list = new ArrayList();
                if(objids.get("READ") != null && !"".equals(objids.get("READ")) || objids.get("read") != null && !"".equals(objids.get("read")))
                    list.add("READ");
                if(objids.get("WRITE") != null && !"".equals(objids.get("WRITE")) || objids.get("write") != null && !"".equals(objids.get("write")))
                    list.add("WRITE");
                if(objids.get("DOWNLOAD") != null && !"".equals(objids.get("DOWNLOAD")) || objids.get("download") != null && !"".equals(objids.get("download")))
                    list.add("DOWNLOAD");
                String strings[] = new String[list.size()];
                for(int i = 0; i < list.size(); i++)
                    strings[i] = String.valueOf(list.get(i));

                rightService.deleteRightAuth(new OrgObject(documentAuth.getSharinger(), 8), String.valueOf(documentAuth.getAuthid()), rightkey, new String[] {
                    "READ", "WRITE", "DOWNLOAD"
                });
            }

        }
        return "";
    }

    public Object getResult(Map stringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        List rtdocumentList = new ArrayList();
        int directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        int documentid = NumberTool.convertMapKeyToInt(stringMap, "documentid").intValue();
        if(stringMap.get("directoryid") != null && directoryid != 0)
            rtdocumentList = documentAuthService.findAuthByDiretoryId(directoryid);
        else
        if(stringMap.get("documentid") != null && documentid != 0)
            rtdocumentList = documentAuthService.findAuthByDocumentId(documentid);
        String order = (String)stringMap.get("order");
        if(order != null && !"".equals(order))
        {
            String name = order.substring(0, order.indexOf(":"));
            Collections.sort(rtdocumentList, new ListComparator(name, order.substring(order.indexOf(":") + 1)));
        } else
        {
            Collections.sort(rtdocumentList, new ListComparator("sharingername", "asc"));
        }
        return rtdocumentList;
    }

    public String setDefaultOrderBy(Map stringStringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        return null;
    }

    public String saveAuth(Map stringMap, HttpServletRequest httpServletRequest)
    {
        String _result = "\u4FDD\u5B58\u6210\u529F";
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        int directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        int documentid = NumberTool.convertMapKeyToInt(stringMap, "documentid").intValue();
        String authorgids = (String)stringMap.get("authorgids");
        String starttimeStr = (String)stringMap.get("starttime");
        String starttime = null == starttimeStr || "".equals(starttimeStr) ? null : (new StringBuilder()).append(starttimeStr).append(" 00:00:00").toString();
        String endtimeStr = (String)stringMap.get("endtime");
        String endtime = null == endtimeStr || "".equals(endtimeStr) ? null : (new StringBuilder()).append(endtimeStr).append(" 23:59:59").toString();
        String entity = (String)stringMap.get("entity");
        String auths[] = ((String)stringMap.get("auths")).split(",");
        IRightType _rightTypes[] = new IRightType[auths.length];
        int i = 0;
        for(int length = auths.length; i < length; i++)
            _rightTypes[i] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType(auths[i]);

        IRightService rightService = OrgServiceFactory.getRightService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        int id = 0;
        if("RTDOCUMENTS".equals(entity))
            id = documentid;
        else
            id = directoryid;
        if(!"".equals(authorgids))
        {
            String orgids[] = authorgids.contains(",") ? authorgids.split(",") : (new String[] {
                authorgids
            });
            String arr$[] = orgids;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String org = arr$[i$];
                String orgobject[] = org.split(":");
                IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
                documentAuth.setAuthid(Integer.valueOf(id));
                documentAuth.setBegintime(TimeUtil.formatDate(starttime, "yyyy-MM-dd HH:mm:ss"));
                documentAuth.setEndtime(TimeUtil.formatDate(endtime, "yyyy-MM-dd HH:mm:ss"));
                documentAuth.setOrgid(orgid);
                documentAuth.setSharinger(orgobject[0]);
                documentAuth.setAuthEntity(entity);
                documentAuthService.saveDocumentAuth(documentAuth);
                if(!_rightUpdater.hasRightDefine(entity))
                    _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
                _rightUpdater.addRightAuth(orgobject[0], String.valueOf(documentAuth.getId()), String.valueOf(id), entity, false, _rightTypes);
            }

            String keyreaders = (String)stringMap.get("keyreaders");
            if(keyreaders != null && !"".equals(keyreaders))
            {
                String readerOrgids[] = keyreaders.contains(",") ? keyreaders.split(",") : (new String[] {
                    keyreaders
                });
                String arr$[] = readerOrgids;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String org = arr$[i$];
                    String orgobject[] = org.split(":");
                    IRT_KeyReader keyReader = documentAuthService.getKeyReaderByAuthIdAndReaderId(id, orgobject[0]);
                    if(keyReader == null)
                        keyReader = new RT_KeyReaderImpl();
                    keyReader.setAuthId(Integer.valueOf(id));
                    keyReader.setOrgid(orgid);
                    keyReader.setReaderId(orgobject[0]);
                    keyReader.setAuthEntity(entity);
                    documentAuthService.saveKeyReader(keyReader);
                    IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
                    documentAuth.setAuthid(Integer.valueOf(id));
                    documentAuth.setBegintime(null);
                    documentAuth.setEndtime(null);
                    documentAuth.setOrgid(orgid);
                    documentAuth.setSharinger(orgobject[0]);
                    documentAuth.setAuthEntity(entity);
                    documentAuthService.saveCompanyDocumentAuth(documentAuth);
                    IRightType _keyrightTypes[] = new IRightType[1];
                    _keyrightTypes[0] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("READ");
                    if(!_rightUpdater.hasRightDefine(entity))
                        _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
                    _rightUpdater.addRightAuth(orgobject[0], String.valueOf(documentAuth.getId()), String.valueOf(id), entity, false, _keyrightTypes);
                }

                IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
                IRTDocuments document = documentsService.getDocumentById(documentid);
                Map remindInfo = new HashMap();
                String title = (new StringBuilder()).append("\u8BF7\u67E5\u770B\u5171\u4EAB\u6587\u4EF6\u300A").append(document.getDocumentName()).append("\u300B").toString();
                String context = (new StringBuilder()).append(RequestContext.getCurrentUser().getDisplayName()).append("\u7ED9\u4F60\u5171\u4EAB\u4E86\u4E00\u4E2A\u6587\u4EF6\u300A").append(document.getDocumentName()).append("\u300B,").append("\u8BF7\u5728\u4E2A\u4EBA\u6587\u6863\u9875\u9762\u4E2D\u7684\u5171\u4EAB\u6587\u4EF6\u67E5\u770B\uFF0C\u5E76\u56DE\u590D\u3002").toString();
                remindInfo.put("recieverNames", keyreaders);
                remindInfo.put("title", title);
                remindInfo.put("context", context);
                KeyReaderAction.sendRemindMessage("RT_KEYREADER_REMIND1", remindInfo);
            }
        } else
        {
            _result = "\u4FDD\u5B58\u5931\u8D25";
        }
        return _result;
    }

    public String getTime(Map stringMap, HttpServletRequest httpServletRequest)
    {
        String _result = "";
        Object starttime = stringMap.get("begintime");
        Object endtime = stringMap.get("endtime");
        if(starttime == null && endtime == null)
            _result = "\u6C38\u4E45\u6709\u6548";
        else
            _result = (new StringBuilder()).append(TimeUtil.formatDateToString(String.valueOf(starttime), "yyyy-MM-dd")).append("-").append(TimeUtil.formatDateToString(String.valueOf(endtime), "yyyy-MM-dd")).toString();
        return _result;
    }
}
