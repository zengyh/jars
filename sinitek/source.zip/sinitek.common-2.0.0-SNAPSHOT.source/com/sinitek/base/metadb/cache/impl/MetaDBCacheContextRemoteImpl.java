// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCacheContextRemoteImpl.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.MetaObjectUpdateBean;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.config.IMetaDBSysLoader;
import com.sinitek.base.synserver.DataPackUtil;
import com.sinitek.base.synserver.IDataPack;
import com.sinitek.base.synserver.client.ISynEventListener;
import com.sinitek.base.synserver.client.SynClient;
import java.io.ByteArrayOutputStream;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaDBCacheContextLocalImpl, EntityCacheContainer, MetaObjectIdCache

public class MetaDBCacheContextRemoteImpl extends MetaDBCacheContextLocalImpl
    implements ISynEventListener
{

    public MetaDBCacheContextRemoteImpl(String server, int port, IMetaDBSysLoader loader)
    {
        client = new SynClient(server, port, this);
        client.setListener(this);
        this.loader = loader;
        client.connect();
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
            client.sendData(DataPackUtil.craeteAppPack("datacommit", baos.toByteArray()));
    }

    public void onInit()
    {
    }

    public void notifyReload()
    {
        super.notifyReload();
        client.sendData(DataPackUtil.craeteAppPack("reloadall", new byte[0]));
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
            client.sendData(DataPackUtil.craeteAppPack("reloadent", baos.toByteArray()));
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
                if(loader.isEntityExists(entityName))
                {
                    IEntity entity = loader.getEntity(entityName);
                    ObjectOperateType operType = (ObjectOperateType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/cache/ObjectOperateType.getName(), opType);
                    IMetaObjectImpl mo;
                    if(operType == ObjectOperateType.DELETE)
                        mo = entityCacheContainer.getIdCache(entity).getCurretObject(new Integer(objId));
                    else
                        mo = entityCacheContainer.getIdCache(entity).doExchangeObject(new Integer(objId));
                    MetaObjectUpdateBean updateBean = new MetaObjectUpdateBean(mo, operType, entity, objId);
                    commitDatas.add(updateBean);
                } else
                {
                    LOGGER.debug((new StringBuilder()).append("\u5B9E\u4F53[").append(entityName).append("]\u4E0D\u5B58\u5728\uFF0C\u5FFD\u7565").toString());
                }
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
