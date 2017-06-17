// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageEngineServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.IMessageReceiver;
import com.sinitek.sirm.busin.routine.entity.IMessageTemplate;
import com.sinitek.sirm.busin.routine.service.IMessageEngineService;
import com.sinitek.sirm.busin.routine.service.IMessageService;
import com.sinitek.sirm.busin.routine.utils.*;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.message.entity.*;
import com.sinitek.sirm.common.message.enumerate.SirmMessageDetailStatus;
import com.sinitek.sirm.common.message.enumerate.SirmMessageStatus;
import com.sinitek.sirm.common.message.service.ISirmMessageService;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import org.apache.log4j.Logger;

public class MessageEngineServiceImpl extends AbstractMetaDBContextSupport
    implements IMessageEngineService
{

    public MessageEngineServiceImpl()
    {
    }

    public void sendMessage(MessageContext context)
    {
        asynSendMessage(context);
    }

    public void asynSendMessage(MessageContext context)
    {
        IMessageService _msgsvc = CommonServiceFactory.getMessageService();
        IMessageTemplate _template = _msgsvc.getMessageTemplateByCode(context.getCode());
        IOrgService _service = OrgServiceFactory.getOrgService();
        if(null != _template)
        {
            List mrlist = new ArrayList();
            List receiverList;
            if(context.getReceivers() != null)
            {
                receiverList = context.getReceivers();
                Employee receiverEmployee = null;
                MessageReceiver user;
                for(Iterator i$ = receiverList.iterator(); i$.hasNext(); mrlist.add(user))
                {
                    user = (MessageReceiver)i$.next();
                    receiverEmployee = _service.getEmployeeById(user.getEmpId());
                    if(receiverEmployee != null)
                    {
                        user.setEmpId(user.getEmpId());
                        user.setEmail(receiverEmployee.getEmail());
                        user.setMobile(receiverEmployee.getMobilePhone());
                    }
                }

            } else
            {
                LOGGER.info((new StringBuilder("\u6D88\u606F\u53D1\u9001\u4EBA\u5458\u5217\u8868\u4E3A\u7A7A")).toString());
            }
            receiverList = _msgsvc.findMesssageReceiverByTemplateId((new StringBuilder()).append(_template.getId()).append("").toString());
            List emplist = new ArrayList();
            IMessageReceiver objlist;
            for(Iterator i$ = receiverList.iterator(); i$.hasNext(); emplist.addAll(_service.findEmployeesByOrgTypeOrgId(objlist.getOrgType().intValue(), objlist.getOrgId())))
                objlist = (IMessageReceiver)i$.next();

            MessageReceiver user = new MessageReceiver();
            Iterator i$ = emplist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Employee empobj = (Employee)i$.next();
                if(empobj != null)
                {
                    user = new MessageReceiver();
                    user.setEmail(empobj.getEmail());
                    user.setMobile(empobj.getMobilePhone());
                    user.setEmpId(empobj.getId());
                    mrlist.add(user);
                }
            } while(true);
            List reempids = new ArrayList();
            if(0 == _template.getForceflag().intValue())
            {
                List relist = _msgsvc.findCancelMessageReceiversBytemplateid((new StringBuilder()).append(_template.getId()).append("").toString());
                IMessageReceiver reobj;
                for(Iterator i$ = relist.iterator(); i$.hasNext(); reempids.add(reobj.getOrgId()))
                    reobj = (IMessageReceiver)i$.next();

                List delmrlist = new ArrayList();
                Iterator i$ = mrlist.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    MessageReceiver mrobj = (MessageReceiver)i$.next();
                    if(reempids.contains(mrobj.getEmpId()))
                        delmrlist.add(mrobj);
                } while(true);
                mrlist.removeAll(delmrlist);
            }
            mrlist = removeDuplicate(mrlist);
            MessageSendContext sc = new MessageSendContext();
            sc.setTitle(_template.getTitle());
            sc.setContent(_template.getContent());
            sc.setTemplatetypeid(NumberTool.safeToInteger(context.getParam("templatetypeid"), Integer.valueOf(0)).intValue());
            sc.setTemplatetype(_template.getTemplatetype() == null ? -1 : _template.getTemplatetype().intValue());
            sc.setExampleownerid(NumberTool.safeToInteger(context.getParam("exampleownerid"), Integer.valueOf(0)).intValue());
            sc.setReceivers(mrlist);
            sc.setAttachments(context.getAttachments());
            sc.setParams(context.getParams());
            sc.setMailFrom(context.getMailFrom());
            sc.setOperateurl((String)context.getParam("operateurl"));
            int modes[] = {
                1, 2, 4, 8
            };
            IMessageSender messageSender = null;
            int arr$[] = modes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                int mode = arr$[i$];
                boolean isok = (_template.getSendMode().intValue() & mode) == mode;
                if(isok)
                    try
                    {
                        if(mode == 1)
                            sc.setContent(_template.getContent());
                        else
                        if(mode == 2)
                            sc.setContent(_template.getSmsContent());
                        else
                        if(mode == 4)
                            sc.setContent(_template.getRemindContent());
                        else
                        if(mode == 8)
                            sc.setContent(_template.getMobileContent());
                        getSirmMessageBySendContext(sc, mode, SirmMessageStatus.INIT.getEnumItemValue(), SirmMessageDetailStatus.SENDING.getEnumItemValue(), 0, TimeUtil.getSysDateAsDate());
                        LOGGER.info((new StringBuilder("\u6D88\u606F\u53D1\u9001\u6210\u529F")).toString());
                    }
                    catch(Exception ex)
                    {
                        getSirmMessageBySendContext(sc, mode, SirmMessageStatus.FAIL.getEnumItemValue(), SirmMessageDetailStatus.FAIL.getEnumItemValue(), 0, TimeUtil.getSysDateAsDate());
                        LOGGER.info((new StringBuilder("\u6D88\u606F\u53D1\u9001\u5931\u8D25")).toString());
                        if(ex instanceof RuntimeException)
                            throw (RuntimeException)ex;
                        else
                            throw new RuntimeException(ex);
                    }
            }

        } else
        {
            LOGGER.info((new StringBuilder("\u6D88\u606F\u6A21\u677F\u3010")).append(context.getCode()).append("\u3011\u4E0D\u5B58\u5728").toString());
        }
    }

    public void synSendMessage(MessageContext context)
    {
        IMessageService _msgsvc = CommonServiceFactory.getMessageService();
        IMessageTemplate _template = _msgsvc.getMessageTemplateByCode(context.getCode());
        IOrgService _service = OrgServiceFactory.getOrgService();
        if(null != _template)
        {
            List mrlist = new ArrayList();
            List receiverList;
            if(context.getReceivers() != null)
            {
                receiverList = context.getReceivers();
                Employee receiverEmployee = null;
                MessageReceiver user;
                for(Iterator i$ = receiverList.iterator(); i$.hasNext(); mrlist.add(user))
                {
                    user = (MessageReceiver)i$.next();
                    receiverEmployee = _service.getEmployeeById(user.getEmpId());
                    if(receiverEmployee != null)
                    {
                        user.setEmpId(user.getEmpId());
                        user.setEmail(receiverEmployee.getEmail());
                        user.setMobile(receiverEmployee.getMobilePhone());
                    }
                }

            }
            receiverList = _msgsvc.findMesssageReceiverByTemplateId((new StringBuilder()).append(_template.getId()).append("").toString());
            List emplist = new ArrayList();
            IMessageReceiver objlist;
            for(Iterator i$ = receiverList.iterator(); i$.hasNext(); emplist.addAll(_service.findEmployeesByOrgTypeOrgId(objlist.getOrgType().intValue(), objlist.getOrgId())))
                objlist = (IMessageReceiver)i$.next();

            MessageReceiver user = new MessageReceiver();
            Iterator i$ = emplist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Employee empobj = (Employee)i$.next();
                if(empobj != null)
                {
                    user = new MessageReceiver();
                    user.setEmail(empobj.getEmail());
                    user.setMobile(empobj.getMobilePhone());
                    user.setEmpId(empobj.getId());
                    mrlist.add(user);
                }
            } while(true);
            List reempids = new ArrayList();
            if(0 == _template.getForceflag().intValue())
            {
                List relist = _msgsvc.findCancelMessageReceiversBytemplateid((new StringBuilder()).append(_template.getId()).append("").toString());
                IMessageReceiver reobj;
                for(Iterator i$ = relist.iterator(); i$.hasNext(); reempids.add(reobj.getOrgId()))
                    reobj = (IMessageReceiver)i$.next();

                List delmrlist = new ArrayList();
                Iterator i$ = mrlist.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    MessageReceiver mrobj = (MessageReceiver)i$.next();
                    if(reempids.contains(mrobj.getEmpId()))
                        delmrlist.add(mrobj);
                } while(true);
                mrlist.removeAll(delmrlist);
            }
            mrlist = removeDuplicate(mrlist);
            MessageSendContext sc = new MessageSendContext();
            sc.setTitle(_template.getTitle());
            sc.setContent(_template.getContent());
            sc.setTemplatetypeid(NumberTool.safeToInteger(context.getParam("templatetypeid"), Integer.valueOf(0)).intValue());
            sc.setTemplatetype(_template.getTemplatetype() == null ? -1 : _template.getTemplatetype().intValue());
            sc.setExampleownerid(NumberTool.safeToInteger(context.getParam("exampleownerid"), Integer.valueOf(0)).intValue());
            sc.setReceivers(mrlist);
            sc.setAttachments(context.getAttachments());
            sc.setParams(context.getParams());
            sc.setMailFrom(context.getMailFrom());
            sc.setOperateurl((String)context.getParam("operateurl"));
            int modes[] = {
                1, 2, 4, 8
            };
            IMessageSender messageSender = null;
            int arr$[] = modes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                int mode = arr$[i$];
                boolean isok = (_template.getSendMode().intValue() & mode) == mode;
                if(isok)
                    try
                    {
                        if(mode == 1)
                        {
                            sc.setContent(_template.getContent());
                            messageSender = MessageUtils.getMessageEmailSender();
                        } else
                        if(mode == 2)
                        {
                            sc.setContent(_template.getSmsContent());
                            String SMSSupportFlag = SettingUtils.getStringValue("COMMON", "SMS_SUPPORTFLAG");
                            if("1".equals(SMSSupportFlag))
                                messageSender = MessageUtils.getMessageSMSSender();
                        } else
                        if(mode == 4)
                        {
                            sc.setContent(_template.getRemindContent());
                            messageSender = MessageUtils.getMessageRemindeSender();
                        } else
                        if(mode == 8)
                        {
                            sc.setContent(_template.getMobileContent());
                            messageSender = MessageUtils.getMessageMobileSender();
                        }
                        if(messageSender != null)
                            messageSender.send(sc);
                        getSirmMessageBySendContext(sc, mode, SirmMessageStatus.SUCCESS.getEnumItemValue(), SirmMessageDetailStatus.SUCCESS.getEnumItemValue(), 0, TimeUtil.getSysDateAsDate());
                        LOGGER.info((new StringBuilder("\u6D88\u606F\u53D1\u9001\u6210\u529F")).toString());
                    }
                    catch(Exception ex)
                    {
                        getSirmMessageBySendContext(sc, mode, SirmMessageStatus.FAIL.getEnumItemValue(), SirmMessageDetailStatus.FAIL.getEnumItemValue(), 0, TimeUtil.getSysDateAsDate());
                        LOGGER.info((new StringBuilder("\u6D88\u606F\u53D1\u9001\u5931\u8D25")).toString());
                        if(ex instanceof RuntimeException)
                            throw (RuntimeException)ex;
                        else
                            throw new RuntimeException(ex);
                    }
            }

        } else
        {
            LOGGER.info((new StringBuilder("\u6D88\u606F\u6A21\u677F\u3010")).append(context.getCode()).append("\u3011\u4E0D\u5B58\u5728").toString());
        }
    }

    public List removeDuplicate(List list)
    {
        List lists = new ArrayList();
        Map map = new HashMap();
        for(Iterator i$ = list.iterator(); i$.hasNext(); lists.add(map))
        {
            MessageReceiver obj = (MessageReceiver)i$.next();
            map = new HashMap();
            map.put("email", StringUtil.safeToString(obj.getEmail(), ""));
            map.put("empid", StringUtil.safeToString(obj.getEmpId(), ""));
            map.put("mobile", StringUtil.safeToString(obj.getMobile(), ""));
        }

        HashSet h = new HashSet(lists);
        lists.clear();
        lists.addAll(h);
        MessageReceiver mr = new MessageReceiver();
        list.clear();
        for(Iterator i$ = lists.iterator(); i$.hasNext(); list.add(mr))
        {
            Map obj2 = (Map)i$.next();
            mr = new MessageReceiver();
            mr.setEmpId(StringUtil.safeToString(obj2.get("empid"), ""));
            mr.setEmail(StringUtil.safeToString(obj2.get("email"), ""));
            mr.setMobile(StringUtil.safeToString(obj2.get("mobile"), ""));
        }

        return list;
    }

    public ISirmSendMessage getSirmMessageBySendContext(MessageSendContext sc, int type, int status, int detailStatus, int isTime, Date sendTime)
    {
        ISirmMessageService sirmMessageService = CommonServiceFactory.getSirmMessageService();
        ISirmSendMessage sirmSendMessage = null;
        ISirmSendMessageDetail sirmSendMessageDetail = null;
        String url = SettingUtils.getStringValue("COMMON", "HOST_ADDRESS");
        if(sc != null)
        {
            String content = StringUtil.replaceVariables(sc.getContent(), sc.getParams());
            String title = StringUtil.replaceVariables(sc.getTitle(), sc.getParams());
            int exampleownerid = sc.getExampleownerid();
            int templatetypeid = sc.getTemplatetypeid();
            int _templatetypeid = -1;
            if(templatetypeid != 0)
                _templatetypeid = templatetypeid;
            else
                _templatetypeid = sc.getTemplatetype();
            List messageReceiverList = sc.getReceivers();
            String operatrurl = sc.getOperateurl();
            if(operatrurl != null)
                operatrurl = operatrurl.replace("\\", "/");
            if(sc.getDirectloginflag() == 1 && operatrurl != null && type == 1)
            {
                if(!operatrurl.startsWith("http://") && !operatrurl.startsWith("https://"))
                {
                    Iterator i$ = messageReceiverList.iterator();
                    do
                    {
                        if(!i$.hasNext())
                            break;
                        MessageReceiver messageReceiver = (MessageReceiver)i$.next();
                        String cont = content;
                        Employee employee = OrgServiceFactory.getOrgService().getEmployeeById(StringUtil.safeToString(messageReceiver.getEmpId(), ""));
                        String _username = "";
                        String _operatrurl = HttpUtils.getJsUrl(operatrurl);
                        if(employee != null)
                        {
                            _username = (new StringBuilder()).append("username=").append(employee.getUserName()).toString();
                            _username = EncryptUtil.encryptDES(_username, "SIRM2012");
                            cont = (new StringBuilder()).append(cont).append("\n<a href='").append(SettingUtils.getStringValue("COMMON", "HOST_ADDRESS")).append(HttpUtils.getJsUrl("/service/logon.action?url=")).append(_operatrurl.replaceAll("&", "%26")).append("&parameter=").append(_username).append("'>\u70B9\u51FB\u524D\u5F80</a>").toString();
                        }
                        sirmSendMessage = new SirmSendMessageImpl();
                        sirmSendMessage.setContent(cont);
                        sirmSendMessage.setTitle(title);
                        sirmSendMessage.setIsTime(Integer.valueOf(isTime));
                        sirmSendMessage.setType(Integer.valueOf(type));
                        sirmSendMessage.setTemplatetypeid(Integer.valueOf(_templatetypeid));
                        sirmSendMessage.setExampleownerid(Integer.valueOf(exampleownerid));
                        sirmSendMessage.setStatus(Integer.valueOf(status));
                        sirmSendMessage.setSenderId(sc.getSendId() != null ? sc.getSendId() : "0");
                        sirmSendMessage.setSendTime(sendTime);
                        sirmMessageService.saveSirmSendMessage(sirmSendMessage);
                        sirmSendMessageDetail = new SirmSendMessageDetailImpl();
                        if(sirmSendMessage.getId() != 0)
                        {
                            String address = "";
                            switch(type)
                            {
                            case 1: // '\001'
                                address = messageReceiver.getEmail() != null && !"null".equals(messageReceiver.getEmail()) ? messageReceiver.getEmail() : "";
                                break;

                            case 2: // '\002'
                                address = messageReceiver.getMobile() != null && !"null".equals(messageReceiver.getMobile()) ? messageReceiver.getMobile() : "";
                                break;

                            case 4: // '\004'
                                address = messageReceiver.getEmpId() != null && !"null".equals(messageReceiver.getEmpId()) ? messageReceiver.getEmpId() : "";
                                break;

                            case 8: // '\b'
                                address = messageReceiver.getEmpId() != null && !"null".equals(messageReceiver.getEmpId()) ? messageReceiver.getEmpId() : "";
                                break;
                            }
                            sirmSendMessageDetail.setEmpid(messageReceiver.getEmpId());
                            sirmSendMessageDetail.setAddress(address);
                            sirmSendMessageDetail.setSendMessageId(Integer.valueOf(sirmSendMessage.getId()));
                            sirmSendMessageDetail.setSendTime(sendTime);
                            sirmSendMessageDetail.setStatus(Integer.valueOf(detailStatus));
                            sirmMessageService.saveSirmSendMessageDetail(sirmSendMessageDetail);
                        }
                    } while(true);
                }
            } else
            {
                sirmSendMessage = new SirmSendMessageImpl();
                sirmSendMessage.setContent(content);
                sirmSendMessage.setTitle(title);
                sirmSendMessage.setIsTime(Integer.valueOf(isTime));
                sirmSendMessage.setType(Integer.valueOf(type));
                sirmSendMessage.setTemplatetypeid(Integer.valueOf(_templatetypeid));
                sirmSendMessage.setExampleownerid(Integer.valueOf(exampleownerid));
                sirmSendMessage.setStatus(Integer.valueOf(status));
                sirmSendMessage.setSenderId(sc.getSendId() != null ? sc.getSendId() : "0");
                sirmSendMessage.setSendTime(sendTime);
                sirmMessageService.saveSirmSendMessage(sirmSendMessage);
                Iterator i$ = messageReceiverList.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    MessageReceiver messageReceiver = (MessageReceiver)i$.next();
                    sirmSendMessageDetail = new SirmSendMessageDetailImpl();
                    if(sirmSendMessage.getId() != 0)
                    {
                        String address = "";
                        switch(type)
                        {
                        case 1: // '\001'
                            address = messageReceiver.getEmail() != null && !"null".equals(messageReceiver.getEmail()) ? messageReceiver.getEmail() : "";
                            break;

                        case 2: // '\002'
                            address = messageReceiver.getMobile() != null && !"null".equals(messageReceiver.getMobile()) ? messageReceiver.getMobile() : "";
                            break;

                        case 4: // '\004'
                            address = messageReceiver.getEmpId() != null && !"null".equals(messageReceiver.getEmpId()) ? messageReceiver.getEmpId() : "";
                            break;

                        case 8: // '\b'
                            address = messageReceiver.getEmpId() != null && !"null".equals(messageReceiver.getEmpId()) ? messageReceiver.getEmpId() : "";
                            break;
                        }
                        sirmSendMessageDetail.setEmpid(messageReceiver.getEmpId());
                        sirmSendMessageDetail.setAddress(address);
                        sirmSendMessageDetail.setSendMessageId(Integer.valueOf(sirmSendMessage.getId()));
                        sirmSendMessageDetail.setSendTime(sendTime);
                        sirmSendMessageDetail.setStatus(Integer.valueOf(detailStatus));
                        sirmMessageService.saveSirmSendMessageDetail(sirmSendMessageDetail);
                    }
                } while(true);
            }
        }
        return sirmSendMessage;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/service/impl/MessageEngineServiceImpl);

}
