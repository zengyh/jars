// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SendMessageServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.ISendMessageService;
import com.sinitek.sirm.common.message.entity.ISirmSendMessage;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SendMessageServiceImpl extends AbstractMetaDBContextSupport
    implements ISendMessageService
{

    public SendMessageServiceImpl()
    {
    }

    public void saveSendMessage(ISendMessage sendMessage, List sendMessageContentList, List sendMessageDetailList, List _receivemesssageList)
    {
        IOrgService orgService = OrgServiceFactory.getOrgService();
        if(sendMessage != null)
        {
            if(sendMessage.getEditFlag() == null)
                sendMessage.setEditFlag(Integer.valueOf(1));
            sendMessage.save();
        }
        if(sendMessageContentList != null && sendMessageContentList.size() > 0)
        {
            for(int i = 0; i < sendMessageContentList.size(); i++)
            {
                ISendMessageContent _obj = (ISendMessageContent)sendMessageContentList.get(i);
                _obj.setSendMessageId(Integer.valueOf(sendMessage.getId()));
                _obj.save();
            }

        }
        if(sendMessageDetailList != null && sendMessageDetailList.size() > 0)
        {
            for(int i = 0; i < sendMessageDetailList.size(); i++)
            {
                ISendMessageDetail _messageDetail = (ISendMessageDetail)sendMessageDetailList.get(i);
                _messageDetail.setSendMessageId(Integer.valueOf(sendMessage.getId()));
                if(_messageDetail.getReceiverType() == null)
                    _messageDetail.setReceiverType(Integer.valueOf(1));
                _messageDetail.save();
            }

        }
        if(_receivemesssageList != null && _receivemesssageList.size() > 0)
        {
            for(int i = 0; i < _receivemesssageList.size(); i++)
            {
                IReceiveMessage _receivemessage = (IReceiveMessage)_receivemesssageList.get(i);
                Employee employee = orgService.getEmployeeById((new StringBuilder()).append(_receivemessage.getReceiver()).append("").toString());
                if(employee == null || employee.getInservice() == 0)
                    continue;
                _receivemessage.setSendMessageId(Integer.valueOf(sendMessage.getId()));
                if(_receivemessage.getReceiverType() == null)
                    _receivemessage.setReceiverType(Integer.valueOf(1));
                _receivemessage.save();
            }

        }
    }

    public IMetaDBQuery listSendMessage(String userid, String begindate, String enddate, String empid, String titileandcontent)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select s.objid ,s.sender,s.title,s.sendtime,s.sendstatus,s.content as content,s.sendmode,\ns.receiver,s.definetime,s.timingflag,s.editflag \nfrom RT_SendMessage s where 1=1 and (s.deleteflag !=1 or s.deleteflag is null) ");
        if(userid != null && !userid.equals(""))
            QueryUtils.build("=", "s.sender", Integer.valueOf(Integer.parseInt(userid)), _builder, _param);
        if(begindate != null && !begindate.equals(""))
            QueryUtils.build(">=", "sendtime", TimeUtil.formatDate(begindate, "yyyy-MM-dd"), _builder, _param);
        if(enddate != null && !enddate.equals(""))
            QueryUtils.build("<=", "sendtime", TimeUtil.formatDate(getFutureDay(enddate, "yyyy-MM-dd", 1), "yyyy-MM-dd"), _builder, _param);
        if(titileandcontent != null && !titileandcontent.equals(""))
            QueryUtils.buildLikeAllowNull("title", titileandcontent, _builder, _param);
        if(empid != null && !empid.equals(""))
        {
            _builder.append(" and objid in(select sendmessageid from RT_SendMessageDetail where 1=1");
            QueryUtils.build("=", "receiver", Integer.valueOf(Integer.parseInt(empid)), _builder, _param);
            _builder.append(")");
        }
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        _metaDBQuery.setParameters(_param);
        _metaDBQuery.setDefaultOrderBy(" s.sendtime desc");
        return _metaDBQuery;
    }

    public static String getFutureDay(String appDate, String format, int days)
    {
        String future = "";
        try
        {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(5, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        }
        catch(Exception e) { }
        return future;
    }

    public ISendMessage findSendMessageById(Integer objid)
    {
        return (ISendMessage)getMetaDBContext().get("SENDMESSAGE", objid.intValue());
    }

    public void delSendMessageById(String objid)
    {
        ISendMessage obj = (ISendMessage)getMetaDBContext().get("SENDMESSAGE", Integer.parseInt(objid));
        if(obj != null)
        {
            obj.setDeleteFlag(Integer.valueOf(1));
            obj.save();
        }
    }

    public List findReceiversByOrgId(String orgid)
    {
        List employeeList = new ArrayList();
        StringBuilder sql = new StringBuilder("select receiver from RT_SendMessage group by Receiver where 1=1 ");
        Map map = new HashMap();
        if(orgid != null && !"".equals(orgid))
        {
            sql.append(" and sender=:sender");
            map.put("sender", orgid);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        IOrgService orgService = OrgServiceFactory.getOrgService();
        Iterator i$ = iMetaDBQuery.getResult().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map empMap = (Map)i$.next();
            Object receiver = empMap.get("receiver");
            if(receiver != null && !"".equals(receiver))
            {
                Employee employee = orgService.getEmployeeById(String.valueOf(receiver));
                if(employee != null)
                    employeeList.add(employee);
            }
        } while(true);
        return employeeList;
    }

    public int removeSendMessage(String userId)
    {
        String sql = "update RT_SendMessage s set s.deleteflag = 1 where s.sender=:orgId ";
        ISQLUpdater sqlUpdate = getMetaDBContext().createSqlUpdater(sql);
        sqlUpdate.setParameter("orgId", userId);
        int result = sqlUpdate.executeUpdate();
        return result;
    }

    public IMetaDBQuery findSendMessage(Map map)
    {
        StringBuilder sql = new StringBuilder();
        sql.append(" select sm.objid,sm.type,smd.sendtime,sm.senderid,sm.title,sm.content, ");
        sql.append(" smd.empid as reciverid,smd.address ,smd.status");
        sql.append(" from sirm_sendmessage sm inner join sirm_sendmessagedetail smd on sm.objid = smd.sendmessageid ");
        Map params = new HashMap();
        String type = StringUtil.safeToString(map.get("type"), "");
        if(!StringUtil.isStrTrimEmpty(type))
        {
            sql.append(" and sm.type=:type");
            params.put("type", type);
        }
        Date starttime = (Date)map.get("starttime");
        if(starttime != null)
        {
            sql.append(" and sm.sendTime >=:starttime");
            params.put("starttime", starttime);
        }
        Date endtime = (Date)map.get("endtime");
        if(endtime != null)
        {
            sql.append(" and sm.sendTime <=:endtime");
            params.put("endtime", endtime);
        }
        String title = StringUtil.safeToString(map.get("title"), "");
        if(StringUtils.isNotBlank(title))
        {
            sql.append(" and (sm.title like :title");
            params.put("title", (new StringBuilder()).append("%").append(title).append("%").toString());
            sql.append(" or to_char(sm.content) like :content)");
            params.put("content", (new StringBuilder()).append("%").append(title).append("%").toString());
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        LOGGER.info((new StringBuilder()).append("\u67E5\u8BE2\u7CFB\u7EDF\u6D88\u606Fsql\uFF1A").append(sql.toString()).toString());
        query.setParameters(params);
        query.setDefaultOrderBy("sm.sendTime desc");
        return query;
    }

    public List findSender()
    {
        StringBuilder sql = new StringBuilder(" select orgid,orgname from sprt_orgobject where orgid in (select distinct senderid from sirm_sendmessage) order by orgname ");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        return query.getResult();
    }

    public List findReceiver()
    {
        StringBuilder sql = new StringBuilder("select orgid,orgname from sprt_orgobject where orgid in (select distinct empid from sirm_sendmessagedetail) order by orgname");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        return query.getResult();
    }

    public IMetaDBQuery findQuartzMessageList(Map param)
    {
        Map sqlparam = new HashMap();
        String sql = "select * from rt_sendmessage where sendstatus=0 and timingflag=1 ";
        if(param.get("time") != null)
        {
            sql = (new StringBuilder()).append(sql).append(" and to_char(sendtime,'yyyy-MM-dd hh24:mi')<=:time ").toString();
            sqlparam.put("time", param.get("time"));
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameters(sqlparam);
        query.setDefaultOrderBy("sendtime desc");
        return query;
    }

    public ISirmSendMessage getSirmSendMessageById(Integer objid)
    {
        return (ISirmSendMessage)getMetaDBContext().get("SirmSendMessage", objid.intValue());
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/service/impl/SendMessageServiceImpl);

}
