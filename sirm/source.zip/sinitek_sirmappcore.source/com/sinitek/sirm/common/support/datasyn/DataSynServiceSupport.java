// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynServiceSupport.java

package com.sinitek.sirm.common.support.datasyn;

import com.sinitek.sirm.common.utils.ObjectUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.support.datasyn:
//            IDataSerializer, IDataReceiver, DataSynHeader

public abstract class DataSynServiceSupport
    implements IDataSerializer, IDataReceiver
{

    public DataSynServiceSupport()
    {
    }

    public String serializeData(Object data)
    {
        return ObjectUtils.serializeObjectAsJson(data);
    }

    public void receiveData(DataSynHeader header, String serializeData)
    {
        String operation = header.getOperation();
        Object data = deSerializeData(serializeData);
        try
        {
            MethodUtils.invokeMethod(this, operation, new Object[] {
                header, data
            });
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("invoke failed.[serviceName:").append(header.getServiceName()).append(",operation:").append(operation).append("]").toString(), e);
            throw new RuntimeException(e);
        }
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/datasyn/DataSynServiceSupport);

}
