// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReceiveMessageServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.IReceiveMessage;
import com.sinitek.sirm.busin.routine.entity.ISendMessage;
import com.sinitek.sirm.busin.routine.service.IReceiveMessageService;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReceiveMessageServiceImpl extends AbstractMetaDBContextSupport
    implements IReceiveMessageService
{

    public ReceiveMessageServiceImpl()
    {
    }

    public IReceiveMessage findReceiveMessageById(int objid)
    {
        return (IReceiveMessage)getMetaDBContext().get("RECEIVEMESSAGE", objid);
    }

    public void deleteReceive(int objid)
    {
        IReceiveMessage receiveMessage = (IReceiveMessage)getMetaDBContext().get("RECEIVEMESSAGE", objid);
        receiveMessage.setDeleteFlag(Integer.valueOf(1));
        receiveMessage.save();
    }

    public List findReceiveMessage(String sendmessageid, String receiverid)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select * from rt_receivemessage where 1=1");
        if(sendmessageid != null && !sendmessageid.equals(""))
            QueryUtils.build("=", "sendmessageid", Integer.valueOf(Integer.parseInt(sendmessageid)), _builder, _param);
        if(receiverid != null && !receiverid.equals(""))
            QueryUtils.build("=", "receiver", Integer.valueOf(Integer.parseInt(receiverid)), _builder, _param);
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        if(_param != null)
            _metaDBQuery.setParameters(_param);
        return _metaDBQuery.getResult();
    }

    public void saveupdateReceiveMessage(IReceiveMessage receiveMessage)
    {
        if(receiveMessage != null)
        {
            if(receiveMessage.getReceiverType() == null)
                receiveMessage.setReceiverType(Integer.valueOf(1));
            receiveMessage.save();
        }
    }

    public ISendMessage getSendMessageById(int objid)
    {
        ISendMessage sendMessage = (ISendMessage)getMetaDBContext().get("SENDMESSAGE", objid);
        return sendMessage;
    }

    public IMetaDBQuery listReceiveMessage(String userid, String begindate, String enddate, String empid, String titileandcontent)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select s.objid ,s.sender,s.title,to_char(s.content) as content,cast(s.sendtime AS TIMESTAMP) as senddate,r.status from RT_SendMessage s left join rt_receivemessage r on s.objid = r.sendmessageid where 1=1  and (r.deleteflag !=1 or  r.deleteflag is null) and s.sendmode like '1%' ");
        if(empid != null && !empid.equals(""))
            QueryUtils.build("=", "s.sender", Integer.valueOf(Integer.parseInt(empid)), _builder, _param);
        if(begindate != null && !begindate.equals(""))
            QueryUtils.build(">=", "sendtime", TimeUtil.formatDate(begindate, "yyyy-MM-dd"), _builder, _param);
        if(enddate != null && !enddate.equals(""))
            QueryUtils.build("<=", "sendtime", TimeUtil.formatDate(getFutureDay(enddate, "yyyy-MM-dd", 1), "yyyy-MM-dd"), _builder, _param);
        if(titileandcontent != null && !titileandcontent.equals(""))
            QueryUtils.buildLikeAllowNull("title", titileandcontent, _builder, _param);
        if(userid != null && !userid.equals(""))
            QueryUtils.build("=", "r.receiver", Integer.valueOf(Integer.parseInt(userid)), _builder, _param);
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        if(_param != null)
            _metaDBQuery.setParameters(_param);
        _metaDBQuery.setOrderBy(" senddate desc");
        return _metaDBQuery;
    }

    public IMetaDBQuery listReceiveMessage(String userid, String begindate, String enddate, String empid, String titileandcontent, int status)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select s.objid ,s.sender,s.title,s.content,s.sendtime as senddate,r.status,s.replymessageid from RT_SendMessage s left join rt_receivemessage r on s.objid = r.sendmessageid where 1=1  and (r.deleteflag !=1 or  r.deleteflag is null) and s.sendmode like '1%' ");
        if(empid != null && !empid.equals(""))
            QueryUtils.build("=", "s.sender", Integer.valueOf(Integer.parseInt(empid)), _builder, _param);
        if(begindate != null && !begindate.equals(""))
            QueryUtils.build(">=", "sendtime", TimeUtil.formatDate(begindate, "yyyy-MM-dd"), _builder, _param);
        if(enddate != null && !enddate.equals(""))
            QueryUtils.build("<=", "sendtime", TimeUtil.formatDate(getFutureDay(enddate, "yyyy-MM-dd", 1), "yyyy-MM-dd"), _builder, _param);
        if(titileandcontent != null && !titileandcontent.equals(""))
            QueryUtils.buildLikeAllowNull("title", titileandcontent, _builder, _param);
        if(userid != null && !userid.equals(""))
            QueryUtils.build("=", "r.receiver", Integer.valueOf(Integer.parseInt(userid)), _builder, _param);
        if(status != -1)
        {
            _builder.append(" and r.status=:status ");
            _param.put("status", Integer.valueOf(status));
        }
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        _metaDBQuery.setParameters(_param);
        _metaDBQuery.setDefaultOrderBy(" s.sendtime desc");
        return _metaDBQuery;
    }

    public Integer findReceiveMessageByReceiverId(String receiverid)
    {
        String sql = "select   count(1) c\n  from RT_SendMessage s\n  left join rt_receivemessage r\n    on s.objid = r.sendmessageid\n where 1 = 1\n   and (r.deleteflag != 1 or r.deleteflag is null) \n   and s.sendmode like '1%' \n   and r.receiver = :RECEIVERID \nand r.status='0'";
        Map m = new HashMap();
        m.put("RECEIVERID", Integer.valueOf(Integer.parseInt(receiverid)));
        IMetaDBQuery mq = getMetaDBContext().createSqlQuery(sql);
        mq.setParameters(m);
        List list = mq.getResult();
        return Integer.valueOf(list.size() <= 0 ? 0 : NumberTool.convertMapKeyToInt((Map)list.get(0), "c", Integer.valueOf(0)).intValue());
    }

    public List findNewReceiveMessage(String userid)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append("select s.objid ,s.sender,s.title,s.content,cast(s.sendtime AS TIMESTAMP) as senddate,r.status from RT_SendMessage s left join rt_receivemessage r ").append(" on s.objid = r.sendmessageid where r.status = 0 and (r.deleteflag is null or r.deleteflag=0) and s.sendmode like '1%' ");
        if(userid != null && !userid.equals(""))
            QueryUtils.build("=", "r.receiver", Integer.valueOf(Integer.parseInt(userid)), _builder, _param);
        _builder.append(" order by senddate desc ");
        String _sql = (new StringBuilder()).append(" select * from ( ").append(_builder.toString()).append(") where rownum <=6").toString();
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_sql);
        if(_param != null)
            _metaDBQuery.setParameters(_param);
        List rsList = _metaDBQuery.getResult();
        Calendar c = Calendar.getInstance();
        Iterator i$ = rsList.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map temp = (Map)i$.next();
            temp.put("title", filter((new StringBuilder()).append(temp.get("title")).append("").toString()));
            temp.put("content", filter((new StringBuilder()).append(temp.get("content")).append("").toString()));
            Date sendTime = (Date)temp.get("senddate");
            c.setTime(sendTime);
            int hour = c.get(11);
            String shour = hour >= 10 ? (new StringBuilder()).append("").append(hour).toString() : (new StringBuilder()).append("0").append(hour).toString();
            int minite = c.get(12);
            String sminite = minite >= 10 ? (new StringBuilder()).append("").append(minite).toString() : (new StringBuilder()).append("0").append(minite).toString();
            String time = (new StringBuilder()).append(shour).append(":").append(sminite).append(" ").toString();
            long betweenDay = TimeUtil.getBetweenDays(sendTime, new Date());
            if(betweenDay == 0L)
                temp.put("senddate2", (new StringBuilder()).append("\u4ECA\u5929").append(time).toString());
            else
            if(betweenDay == 1L)
                temp.put("senddate2", (new StringBuilder()).append("\u6628\u5929").append(time).toString());
            else
            if(betweenDay < 7L)
            {
                int day = c.get(7);
                if(day == 1)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u65E5").append(time).toString());
                else
                if(day == 2)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u4E00").append(time).toString());
                else
                if(day == 3)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u4E8C").append(time).toString());
                else
                if(day == 4)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u4E09").append(time).toString());
                else
                if(day == 5)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u56DB").append(time).toString());
                else
                if(day == 6)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u4E94").append(time).toString());
                else
                if(day == 7)
                    temp.put("senddate2", (new StringBuilder()).append("\u5468\u516D").append(time).toString());
            } else
            {
                temp.put("senddate2", TimeUtil.formatDate(sendTime, "yy-MM-dd"));
            }
        } while(true);
        return rsList;
    }

    private String filter(String message)
    {
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        for(int i = 0; i < content.length; i++)
            switch(content[i])
            {
            case 60: // '<'
                result.append("&lt;");
                break;

            case 62: // '>'
                result.append("&gt;");
                break;

            case 38: // '&'
                result.append("&amp;");
                break;

            case 34: // '"'
                result.append("&quot;");
                break;

            case 39: // '\''
                result.append("&quot;");
                break;

            default:
                result.append(content[i]);
                break;
            }

        return result.toString();
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

    public int removeReceiveMessage(String userid)
    {
        String sql = "update rt_receivemessage r set r.deleteflag = 1 where r.receiver=:orgId";
        ISQLUpdater sqlUpdate = getMetaDBContext().createSqlUpdater(sql);
        sqlUpdate.setParameter("orgId", userid);
        int result = sqlUpdate.executeUpdate();
        return result;
    }

    public List listReceiveMessage(String orgId)
    {
        String sql = " select * from rt_receivemessage r  inner join RT_SendMessage s on r.sendmessageid = s.objid  where r.receiver=:orgId and (r.deleteflag !=1 or  r.deleteflag is null)  order by 1 desc";
        Map param = new HashMap();
        param.put("orgId", orgId);
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameters(param);
        return query.getResult();
    }
}
