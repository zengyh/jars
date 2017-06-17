// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCacheContextNewSyncRemoteImpl.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.MetaObjectUpdateBean;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.config.IMetaDBSysLoader;
import com.sinitek.base.metadb.syncenter.client.ISynClientListener;
import com.sinitek.base.metadb.syncenter.client.SynClient;
import com.sinitek.base.metadb.syncenter.common.BaseDataPack;
import com.sinitek.base.metadb.syncenter.common.IDataPack;
import java.io.ByteArrayOutputStream;
import java.util.*;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaDBCacheContextLocalImpl, EntityCacheContainer, MetaObjectIdCache

public class MetaDBCacheContextNewSyncRemoteImpl extends MetaDBCacheContextLocalImpl
    implements ISynClientListener
{

    public MetaDBCacheContextNewSyncRemoteImpl(Properties syncProperties, IMetaDBSysLoader loader)
    {
        client = new SynClient(syncProperties, this, loader.getDataSource());
        this.loader = loader;
    }

    public void notifyAfterCommit(List commitDatas)
    {
        super.notifyAfterCommit(commitDatas);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator iter = commitDatas.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            MetaObjectUpdateBean bean = (MetaObjectUpdateBean)iter.next();
            if(bean.getUpdateObject().getEntity().isIdCacheSupport())
            {
                byte entityName[] = bean.getUpdateObject().getEntityName().getBytes();
                baos.write(entityName, 0, entityName.length);
                baos.write(32);
                byte objId[] = String.valueOf(bean.getUpdateObject().getId()).getBytes();
                baos.write(objId, 0, objId.length);
                baos.write(32);
                byte opType[] = bean.getUpdateType().getEnumItemName().getBytes();
                baos.write(opType, 0, opType.length);
                baos.write(59);
            }
        } while(true);
        if(baos.size() > 0)
            client.sendData(BaseDataPack.craeteAppPack("datacommit", baos.toByteArray(), client.getAppKey()));
    }

    public void onInit()
    {
    }

    public void notifyReload()
    {
        super.notifyReload();
        client.sendData(BaseDataPack.craeteAppPack("reloadall", new byte[0], client.getAppKey()));
    }

    public void notifyReload(String entityNames[])
    {
        super.notifyReload(entityNames);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(int i = 0; i < entityNames.length; i++)
        {
            IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityNames[i]);
            if(entity.isIdCacheSupport())
            {
                byte entityName[] = entity.getEntityName().getBytes();
                baos.write(entityName, 0, entityName.length);
                baos.write(59);
            }
        }

        if(baos.size() > 0)
            client.sendData(BaseDataPack.craeteAppPack("reloadent", baos.toByteArray(), client.getAppKey()));
    }

    public void onRecieveData(IDataPack data)
    {
        if(data.getApplicationCode().equalsIgnoreCase("datacommit"))
        {
            List commitDatas = new ArrayList();
            int pos = 0;
            int lastPos = pos;
            for(byte bodyPack[] = data.getBodyPack(); pos < bodyPack.length; pos++)
            {
                if(bodyPack[pos] != 59)
                    continue;
                String temp = new String(bodyPack, lastPos, pos - lastPos);
                lastPos = pos + 1;
                String datas[] = temp.split(" ");
                String entityName = datas[0];
                int objId = Integer.parseInt(datas[1]);
                String opType = datas[2];
                IEntity entity = loader.getEntity(entityName);
                ObjectOperateType operType = (ObjectOperateType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/cache/ObjectOperateType.getName(), opType);
                IMetaObjectImpl mo;
                if(operType == ObjectOperateType.DELETE)
                    mo = entityCacheContainer.getIdCache(entity).getCurretObject(new Integer(objId));
                else
                    mo = entityCacheContainer.getIdCache(entity).doExchangeObject(new Integer(objId));
                MetaObjectUpdateBean updateBean = new MetaObjectUpdateBean(mo, operType, entity, objId);
                commitDatas.add(updateBean);
            }

            super.notifyAfterCommit(commitDatas);
        } else
        if("reloadent".equalsIgnoreCase(data.getApplicationCode()))
        {
            String _temp = new String(data.getBodyPack());
            super.notifyReload(_temp.split("[;]"));
        } else
        if("reloadall".equalsIgnoreCase(data.getApplicationCode()))
            super.notifyReload();
    }

    private SynClient client;
    private IMetaDBSysLoader loader;
}
