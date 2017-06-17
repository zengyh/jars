// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSynchronousServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.org.busin.entity.IUserSynchronous;
import com.sinitek.sirm.org.busin.service.IUserSynchronousService;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.org.busin.service.impl:
//            OrgServiceImpl

public class UserSynchronousServiceImpl extends MetaDBContextSupport
    implements IUserSynchronousService
{

    public UserSynchronousServiceImpl()
    {
    }

    public void saveUserSynchronous(IUserSynchronous userSynchronous)
    {
        userSynchronous.save();
    }

    public void delUserSynchronous(int userid)
    {
        IUserSynchronous userSynchronous = getUserSynchronous(userid);
        userSynchronous.delete();
    }

    public IUserSynchronous getUserSynchronous(int userid)
    {
        return (IUserSynchronous)getMetaDBContext().get("USERSYNCHRONOUS", userid);
    }

    public IUserSynchronous getUserSynchronousByUserName(String username)
    {
        List userSynchronousList = new ArrayList();
        String _hql = "username=:username";
        IMetaDBQuery _relaQuery = getMetaDBContext().createQuery("USERSYNCHRONOUS", _hql);
        _relaQuery.setParameter("username", username);
        if(_relaQuery != null)
            userSynchronousList = _relaQuery.getResult();
        return userSynchronousList.size() <= 0 ? null : (IUserSynchronous)userSynchronousList.get(0);
    }

    public List findAllIUserSynchronous()
    {
        List userSynchronousList = new ArrayList();
        IMetaDBQuery _relaQuery = getMetaDBContext().createQuery("USERSYNCHRONOUS", "1=1");
        if(_relaQuery != null)
            userSynchronousList = _relaQuery.getResult();
        return userSynchronousList;
    }

    public List findFailureAllIUserSynchronous()
    {
        List userSynchronousList = new ArrayList();
        String _hql = "Issuccess=:Issuccess";
        IMetaDBQuery _relaQuery = getMetaDBContext().createQuery("USERSYNCHRONOUS", _hql);
        _relaQuery.setParameter("Issuccess", Integer.valueOf(1));
        if(_relaQuery != null)
            userSynchronousList = _relaQuery.getResult();
        return userSynchronousList;
    }

    static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/service/impl/OrgServiceImpl);

}
