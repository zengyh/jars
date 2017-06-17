// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailLogonServiceImpl.java

package com.sinitek.sirm.common.maillogon.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.maillogon.entity.EmailLogonImpl;
import com.sinitek.sirm.common.maillogon.entity.IEmailLogon;
import com.sinitek.sirm.common.maillogon.service.IEmailLogonService;
import java.util.*;
import org.apache.log4j.Logger;

public class EmailLogonServiceImpl extends AbstractMetaDBContextSupport
    implements IEmailLogonService
{

    public EmailLogonServiceImpl()
    {
    }

    public IEmailLogon getEmailLogon(int objid)
    {
        IEmailLogon emaillogon = (IEmailLogon)getMetaDBContext().get("SIRMEMAILLOGON", objid);
        return ((IEmailLogon) (emaillogon != null ? emaillogon : new EmailLogonImpl()));
    }

    public int saveEmailLogon(IEmailLogon emailLogon)
    {
        emailLogon.save();
        return emailLogon.getId();
    }

    public void deleteEmailLogon(IEmailLogon emailLogon)
    {
        if(emailLogon != null)
            emailLogon.remove();
    }

    public IEmailLogon getEmailLogon(String uuid, String username)
    {
        String _sql = " uuid=:uuid and username=:username";
        Map _map = new HashMap();
        _map.put("uuid", uuid);
        _map.put("username", username);
        List list = getMetaDBContext().query("SIRMEMAILLOGON", _sql, _map);
        if(list.size() > 0)
        {
            LOGGER.info((new StringBuilder()).append("\u901A\u8FC7uuid\u548Cusername\u67E5\u8BE2\u90AE\u4EF6\u767B\u9646\u4FE1\u606F\u7684\u6761\u6570\uFF1A").append(list.size()).toString());
            return (IEmailLogon)list.get(0);
        } else
        {
            return null;
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/maillogon/service/impl/EmailLogonServiceImpl);

}
