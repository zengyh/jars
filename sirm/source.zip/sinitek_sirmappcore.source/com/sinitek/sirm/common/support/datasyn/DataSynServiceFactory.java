// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynServiceFactory.java

package com.sinitek.sirm.common.support.datasyn;

import com.sinitek.sirm.common.spring.SpringFactory;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.support.datasyn:
//            IDataSerializer, IDataReceiver

public class DataSynServiceFactory
{

    public DataSynServiceFactory()
    {
    }

    public static IDataSerializer getDataSerializer(String name)
    {
        IDataSerializer result = null;
        String beanname = (new StringBuilder()).append("DataSyn").append(name).toString();
        LOGGER.debug((new StringBuilder()).append("getDataSerializer[").append(beanname).append("]").toString());
        return (IDataSerializer)SpringFactory.getBean(beanname, com/sinitek/sirm/common/support/datasyn/IDataSerializer);
    }

    public static IDataReceiver getDataReceiver(String name)
    {
        IDataReceiver result = null;
        String beanname = (new StringBuilder()).append("DataSyn").append(name).toString();
        LOGGER.debug((new StringBuilder()).append("getDataReceiver[").append(beanname).append("]").toString());
        return (IDataReceiver)SpringFactory.getBean(beanname, com/sinitek/sirm/common/support/datasyn/IDataReceiver);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/datasyn/DataSynServiceFactory);

}
