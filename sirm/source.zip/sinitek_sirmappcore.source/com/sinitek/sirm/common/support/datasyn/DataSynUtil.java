// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSynUtil.java

package com.sinitek.sirm.common.support.datasyn;

import com.sinitek.base.datasource.DataSourceFactory;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.ObjectUtils;
import com.sinitek.sirm.common.web.RequestContext;
import java.rmi.RemoteException;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package com.sinitek.sirm.common.support.datasyn:
//            DataSynResult, DataSynHeader, IDataSerializer, DataSynServiceFactory

public class DataSynUtil
{

    public DataSynUtil()
    {
    }

    public static DataSynResult sendData(String servicename, String operation, Object data)
    {
        DataSynResult result = new DataSynResult();
        boolean datasynEnabled = BooleanUtils.toBoolean(SettingUtils.getLocalSetting("datasyn.enabled"));
        String datasynId = StringUtils.trim(SettingUtils.getLocalSetting("datasyn.id"));
        if(datasynEnabled)
        {
            DataSynHeader header = new DataSynHeader();
            header.setDatasrc(datasynId);
            String operatorId = null;
            RequestUser requestUser = RequestContext.getCurrentUser();
            if(requestUser != null)
                operatorId = requestUser.getOrgId();
            header.setOperartorId(operatorId);
            header.setServiceName(servicename);
            header.setOperation(operation);
            LOGGER.debug((new StringBuilder()).append("sendData.servicename:").append(servicename).append(",operation:").append(operation).append(",operatorId:").append(operatorId).toString());
            String strTargets = SettingUtils.getLocalSetting("datasyn.targets");
            String endpoints[] = strTargets.split(",");
            String arr$[] = endpoints;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String endpoint = arr$[i$];
                String _endpoint = StringUtils.trimToNull(endpoint);
                if(_endpoint != null)
                {
                    LOGGER.debug((new StringBuilder()).append("sendData to ").append(_endpoint).toString());
                    try
                    {
                        callWebservice(_endpoint, header, data);
                    }
                    catch(Exception ex)
                    {
                        result.setSuccess(false);
                        LOGGER.error("sendData failed", ex);
                    }
                }
            }

        } else
        {
            result.setSuccess(true);
        }
        return result;
    }

    private static void callWebservice(String endpoint, DataSynHeader header, Object data)
        throws RemoteException, ServiceException
    {
        Service service = new Service();
        Call call = (Call)service.createCall();
        call.setTargetEndpointAddress(endpoint);
        call.setOperationName("receiveData");
        call.addParameter("serialheader", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("serializeData", XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnType(XMLType.XSD_ANYTYPE);
        String strHeader = ObjectUtils.serializeObjectAsJson(header);
        IDataSerializer dataSerializer = DataSynServiceFactory.getDataSerializer(header.getServiceName());
        String strData = dataSerializer == null ? ObjectUtils.serializeObjectAsJson(data) : dataSerializer.serializeData(data);
        call.invoke(new Object[] {
            strHeader, strData
        });
    }

    public static void markSynData(String tableName, String datasrc, String origid, String wheresql)
    {
        String sql = (new StringBuilder()).append("update ").append(tableName).append(" \nset datasrc=?,origid=?").append(" \nwhere ").append(wheresql).toString();
        Object params[] = {
            datasrc, origid
        };
        javax.sql.DataSource ds = DataSourceFactory.getInstance().getDataSouce("siniteksirm");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.update(sql, params);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/datasyn/DataSynUtil);

}
