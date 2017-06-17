// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynStatusClient.java

package com.sinitek.base.synserver.client;

import com.sinitek.base.synserver.SynException;
import com.sinitek.base.synserver.server.ClientBean;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.client:
//            ServerStatusBean, SynClientException

public class SynStatusClient
{

    public SynStatusClient()
    {
    }

    public static ServerStatusBean check(String serverIp, int statusPort)
        throws SynException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ServerStatusBean bean = new ServerStatusBean();
        Socket socket = null;
        InputStream is = null;
        try
        {
            ServerStatusBean serverstatusbean;
            try
            {
                socket = new Socket();
                socket.connect(new InetSocketAddress(serverIp, statusPort));
                is = socket.getInputStream();
                byte head[] = read(is, 20);
                bean.setCheckDate(sdf.parse(new String(head, 0, 14)));
                int bodyCount = Integer.parseInt((new String(head, 14, 6)).trim()) / 37;
                List beans = new ArrayList();
                for(int i = 0; i < bodyCount; i++)
                    beans.add(getBean(read(is, 37)));

                bean.setClients(beans);
                serverstatusbean = bean;
            }
            catch(Exception ex)
            {
                throw new SynClientException("0011", ex);
            }
            return serverstatusbean;
        }
        finally
        {
            if(is != null)
                try
                {
                    is.close();
                }
                catch(IOException e)
                {
                    LOGGER.warn("\u5173\u95EDSocket\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
            if(socket != null)
                try
                {
                    socket.close();
                }
                catch(IOException e)
                {
                    LOGGER.warn("\u5173\u95EDSocket\u8FDE\u63A5\u5931\u8D25", e);
                }
        }
    }

    private static byte[] read(InputStream is, int length)
        throws IOException
    {
        byte ret[] = new byte[length];
        int count = 0;
        for(count += is.read(ret); count < length; count += is.read(ret, count, length - count));
        return ret;
    }

    private static ClientBean getBean(byte data[])
    {
        ClientBean cb = new ClientBean();
        cb.setClientIp((new String(data, 0, 15)).trim());
        int port = data[15];
        port <<= 8;
        port += data[16];
        cb.setClientPort(port);
        cb.setConnectTime(new Date(getLong(data, 17)));
        cb.setLastCheckTime(new Date(getLong(data, 25)));
        cb.setNextCheckDelay(getInt(data, 33));
        return cb;
    }

    private static long getLong(byte data[], int pos)
    {
        long ret = 0L;
        for(int i = 0; i < 8; i++)
        {
            ret <<= 8;
            byte _b = data[pos + i];
            int _t = 255;
            _t &= _b;
            ret += _t;
        }

        return ret;
    }

    private static int getInt(byte data[], int pos)
    {
        int ret = 0;
        for(int i = 0; i < 4; i++)
        {
            ret <<= 8;
            byte _b = data[pos + i];
            int _t = 255;
            _t &= _b;
            ret += _t;
        }

        return ret;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/client/SynStatusClient);

}
