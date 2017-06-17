// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynReceiveWebService.java

package com.sinitek.sirm.common.support.datasyn;

import com.sinitek.sirm.common.utils.ObjectUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.common.support.datasyn:
//            DataSynHeader, IDataReceiver, DataSynServiceFactory

public class DataSynReceiveWebService
{

    public DataSynReceiveWebService()
    {
    }

    public void receiveData(String header, String serializeData)
        throws Exception
    {
        DataSynHeader _header = (DataSynHeader)ObjectUtils.deserializeObjectFromJson(header, com/sinitek/sirm/common/support/datasyn/DataSynHeader);
        String servicename = _header.getServiceName();
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u63A5\u6536\u6570\u636E[").append(servicename).append(".").append(_header.getOperation()).append("]").toString());
        LOGGER.debug((new StringBuilder()).append("datasrc.id:").append(_header.getDatasrc()).toString());
        LOGGER.debug((new StringBuilder()).append("serializedata:\n").append(serializeData).toString());
        IDataReceiver dataReceiver = DataSynServiceFactory.getDataReceiver(_header.getServiceName());
        if(dataReceiver != null)
            dataReceiver.receiveData(_header, serializeData);
        else
            LOGGER.warn((new StringBuilder()).append("\u6CA1\u6709\u627E\u5230\u670D\u52A1[").append(_header.getServiceName()).append("]").toString());
        LOGGER.debug((new StringBuilder()).append("\u63A5\u6536\u6570\u636E\u5B8C\u6BD5[").append(servicename).append(".").append(_header.getOperation()).append("]").toString());
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/datasyn/DataSynReceiveWebService);

}
