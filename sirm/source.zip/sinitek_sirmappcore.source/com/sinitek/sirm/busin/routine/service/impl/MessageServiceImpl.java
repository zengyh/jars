// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.IMessageService;
import com.sinitek.sirm.common.utils.ListComparator;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.enumerate.OrgType;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;

public class MessageServiceImpl extends AbstractMetaDBContextSupport
    implements IMessageService
{

    public MessageServiceImpl()
    {
    }

    public IMessageTemplate getMessageTemplateByCode(String code)
    {
        String sql = "code=:code";
        Map param = new HashMap();
        param.put("code", code);
        List list = getMetaDBContext().query("MESSAGETEMPLATE", sql, param);
        return list.size() <= 0 ? null : (IMessageTemplate)list.get(0);
    }

    public IMessageTemplate getMessageTemplateById(Integer objid)
    {
        if(objid != null)
            return (IMessageTemplate)getMetaDBContext().get(com/sinitek/sirm/busin/routine/entity/IMessageTemplate, objid.intValue());
        else
            return null;
    }

    public List findMessageReceiversByTemplateId(int templateId)
    {
        String sql = "templateId=:templateId";
        Map param = new HashMap();
        param.put("templateId", Integer.valueOf(templateId));
        return getMetaDBContext().query("MESSAGERECEIVER", sql, param);
    }

    public List findMessageReceiversByFlag(String orgid)
    {
        String sql = "usertype=:usertype and orgid=:orgid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(0));
        param.put("orgid", orgid);
        param.put("orgtype", Integer.valueOf(8));
        return getMetaDBContext().query("MESSAGERECEIVER", sql, param);
    }

    public List findCancelMessageReceiversByOrgid(String orgid)
    {
        String sql = "usertype=:usertype and orgid=:orgid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(2));
        param.put("orgid", orgid);
        param.put("orgtype", Integer.valueOf(8));
        return getMetaDBContext().query("MESSAGERECEIVER", sql, param);
    }

    public List findMessageReceiversBytemplateid(String templateid)
    {
        String sql = "usertype=:usertype and templateid=:templateid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(0));
        param.put("templateid", templateid);
        param.put("orgtype", Integer.valueOf(8));
        return getMetaDBContext().query("MESSAGERECEIVER", sql, param);
    }

    public List findCancelMessageReceiversBytemplateid(String templateid)
    {
        String sql = "usertype=:usertype and templateid=:templateid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(2));
        param.put("templateid", templateid);
        param.put("orgtype", Integer.valueOf(8));
        return getMetaDBContext().query("MESSAGERECEIVER", sql, param);
    }

    public IMetaDBQuery findMessageReceiversBy(String code, String name)
    {
        return findMessageReceiversBy(code, name, null);
    }

    public IMetaDBQuery findMessageReceiversBy(String code, String name, Integer category)
    {
        IMetaDBQuery _IMetaDBQuery = null;
        StringBuffer sql = new StringBuffer();
        Map _param = new HashMap();
        sql.append("select * from Sirm_MessageTemplate where 1=1 ");
        if(!"".equals(code) && null != code)
        {
            sql.append(" and code like :code");
            _param.put("code", (new StringBuilder()).append("%").append(code).append("%").toString());
        }
        if(!"".equals(name) && null != name)
        {
            sql.append(" and name like :name");
            _param.put("name", (new StringBuilder()).append("%").append(name).append("%").toString());
        }
        if(category != null)
            sql.append(" and catagory  = ").append(category);
        _IMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        _IMetaDBQuery.setParameters(_param);
        return _IMetaDBQuery;
    }

    public Integer saveMessageTemplate(IMessageTemplate template)
    {
        IMessageTemplate obj = null;
        if(template.getId() != 0)
            try
            {
                obj = (IMessageTemplate)getMetaDBContext().get("MESSAGETEMPLATE", template.getId());
                if(obj != null)
                {
                    obj.setContent(template.getContent());
                    obj.setName(template.getName());
                    obj.setSmsContent(template.getSmsContent());
                    obj.setCode(template.getCode());
                    obj.setSendMode(template.getSendMode());
                    obj.setTitle(template.getTitle());
                    obj.setRemindContent(template.getRemindContent());
                    obj.setForceflag(template.getForceflag());
                    obj.setProcesstype(template.getProcesstype());
                    obj.setRemark(template.getRemark());
                    obj.setTemplatetype(template.getTemplatetype());
                    obj.setMobileContent(template.getMobileContent());
                    obj.setCatagory(template.getCatagory());
                    obj.save();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        else
            template.save();
        return Integer.valueOf(template.getId());
    }

    public List findTemplateById(String objid)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Sirm_MessageTemplate where objid=");
        sql.append(objid);
        return getMetaDBContext().createSqlQuery(sql.toString()).getResult();
    }

    public Map findTemplateByFlag()
    {
        Map linkedHashMap = new LinkedHashMap();
        Map params = new HashMap();
        params.put("forceflag", "0");
        List list = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageTemplate, "forceflag=:forceflag ", params);
        Collections.sort(list, new ListComparator("name", "asc"));
        IMessageTemplate obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); linkedHashMap.put(Integer.valueOf(obj.getId()), obj.getName()))
            obj = (IMessageTemplate)i$.next();

        return linkedHashMap;
    }

    public void removeTemplateById(int objid)
    {
        IMessageTemplate obj = null;
        try
        {
            obj = (IMessageTemplate)getMetaDBContext().get("MESSAGETEMPLATE", objid);
            if(obj != null)
                obj.remove();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveMesssageReceiver(List list)
    {
        IMessageReceiver obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); obj.save())
            obj = (IMessageReceiver)i$.next();

    }

    public void removeMesssageReceiverByTemplateId(String id)
    {
        Map params = new HashMap();
        params.put("templateId", id);
        params.put("usertype", Integer.valueOf(1));
        List receiversList = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageReceiver, "templateId=:templateId and usertype=:usertype", params);
        IMessageReceiver obj;
        for(Iterator i$ = receiversList.iterator(); i$.hasNext(); obj.remove())
            obj = (IMessageReceiver)i$.next();

    }

    public void removeMesssageReceiverByUserid(String id)
    {
        String sql = "usertype=:usertype and orgid=:orgid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(0));
        param.put("orgid", id);
        param.put("orgtype", Integer.valueOf(8));
        List receiversList = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageReceiver, sql, param);
        IMessageReceiver obj;
        for(Iterator i$ = receiversList.iterator(); i$.hasNext(); obj.remove())
            obj = (IMessageReceiver)i$.next();

    }

    public void removeCancelMesssageReceiverByOrgid(String orgid)
    {
        String sql = "usertype=:usertype and orgid=:orgid and orgtype=:orgtype";
        Map param = new HashMap();
        param.put("usertype", Integer.valueOf(2));
        param.put("orgid", orgid);
        param.put("orgtype", Integer.valueOf(8));
        List receiversList = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageReceiver, sql, param);
        IMessageReceiver obj;
        for(Iterator i$ = receiversList.iterator(); i$.hasNext(); obj.remove())
            obj = (IMessageReceiver)i$.next();

    }

    public List findMesssageReceiverByTemplateId(String id)
    {
        Map params = new HashMap();
        params.put("templateId", id);
        params.put("usertype", Integer.valueOf(1));
        List receiversList = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageReceiver, "templateId=:templateId and usertype=:usertype", params);
        return receiversList;
    }

    public Map findAlltemp()
    {
        Map map = new HashMap();
        Map params = new HashMap();
        List list = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageTemplate, "1=1 ", params);
        IMessageTemplate obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); map.put(obj.getCode(), obj.getName()))
            obj = (IMessageTemplate)i$.next();

        return map;
    }

    public Map findTemplateByProcessType(int processtype)
    {
        StringBuffer sb = new StringBuffer();
        Map map = new HashMap();
        Map params = new HashMap();
        params.put("processtype", Integer.valueOf(processtype));
        sb.append("SELECT * FROM sirm_messagetemplate sm WHERE 1=1 AND nvl(sm.processtype,0) = :processtype");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameters(params);
        List list = _query.getResult();
        Map obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); map.put(StringUtil.safeToString(obj.get("code"), ""), StringUtil.safeToString(obj.get("name"), "")))
            obj = (Map)i$.next();

        return map;
    }

    public void saveMessageTemplateReal(IMessageTemplateRela real)
    {
        real.save();
    }

    public void delMessageTemplateRealBySource(String sourceid, String sourceentity)
    {
        Map params = new HashMap();
        params.put("sourceId", sourceid);
        params.put("sourceEntity", sourceentity);
        List list = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageTemplateRela, "sourceEntity=:sourceEntity and sourceId=:sourceId", params);
        IMessageTemplateRela obj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); obj.remove())
            obj = (IMessageTemplateRela)i$.next();

    }

    public List findMessageTemplateRelasBySource(Integer sourceid, String sourceentity, Integer relatype)
    {
        Map params = new HashMap();
        params.put("sourceid", sourceid);
        params.put("sourceentity", sourceentity);
        String sql = " sourceid=:sourceid and sourceentity=:sourceentity";
        if(null != relatype)
        {
            sql = (new StringBuilder()).append(sql).append(" and relatype=:relatype").toString();
            params.put("relatype", relatype);
        }
        List list = getMetaDBContext().query(com/sinitek/sirm/busin/routine/entity/IMessageTemplateRela, sql, params);
        return list;
    }

    public String getContent(String cont, Map map)
    {
        return StringUtil.replaceVariables(cont, map);
    }

    public static String getName(String val, Map map)
    {
        String b = val.substring(2, val.length() - 1);
        if(!"".equals(map.get(b)) && null != map.get(b))
            return (String)map.get(b);
        else
            return "";
    }

    public List findMessageReceiverByTemplateCode(String code)
    {
        List messageReceiverList = new ArrayList();
        if(code != null && !"".equals(code))
        {
            IMessageTemplate messageTemplate = getMessageTemplateByCode(code);
            if(messageTemplate != null)
            {
                List receiverList = findMesssageReceiverByTemplateId(String.valueOf(messageTemplate.getId()));
                IOrgService orgService = OrgServiceFactory.getOrgService();
                IMessageReceiver messageReceiver = null;
                List orglist = new ArrayList();
                for(Iterator i$ = receiverList.iterator(); i$.hasNext();)
                {
                    IMessageReceiver receiver = (IMessageReceiver)i$.next();
                    if(receiver.getOrgType().intValue() == OrgType.EMPLOYEE.getEnumItemValue())
                    {
                        if(!orglist.contains(receiver.getOrgId()))
                        {
                            messageReceiverList.add(receiver);
                            orglist.add(receiver.getOrgId());
                        }
                    } else
                    {
                        List employeeList = orgService.findEmployeesByOrgId(receiver.getOrgId());
                        Iterator i$ = employeeList.iterator();
                        while(i$.hasNext()) 
                        {
                            Employee employee = (Employee)i$.next();
                            if(!orglist.contains(employee.getId()))
                            {
                                messageReceiver = new MessageReceiverImpl();
                                messageReceiver.setOrgId(employee.getId());
                                messageReceiver.setOrgType(Integer.valueOf(8));
                                messageReceiver.setTemplateId(receiver.getTemplateId());
                                messageReceiver.setUserType(Integer.valueOf(8));
                                messageReceiverList.add(messageReceiver);
                                orglist.add(receiver.getOrgId());
                            }
                        }
                    }
                }

            }
        }
        return messageReceiverList;
    }
}
