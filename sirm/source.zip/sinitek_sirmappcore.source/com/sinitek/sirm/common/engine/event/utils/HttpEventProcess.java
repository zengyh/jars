// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpEventProcess.java

package com.sinitek.sirm.common.engine.event.utils;

import com.sinitek.sirm.common.utils.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.json.JSONObject;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            SimpleEventObject, IEventProcess, AbstractEventObject

public class HttpEventProcess
    implements IEventProcess
{

    public HttpEventProcess(String location)
    {
        this.location = null;
        this.location = location;
    }

    public void process(SimpleEventObject e)
    {
        logger.debug("-- HttpAction --");
        Map eoMap = e.getAllData();
        Map _requestData = new HashMap();
        JSONObject json = new JSONObject(eoMap);
        json.remove("class");
        _requestData.put("json", json.toString());
        InputStream is = HttpUtils.requestData(location, _requestData);
        try
        {
            is.close();
        }
        catch(IOException ex)
        {
            logger.error("request data failed.", ex);
        }
        logger.debug("-- HttpAction End --");
    }

    public String getLocation()
    {
        return location;
    }

    public volatile void process(AbstractEventObject x0)
    {
        process((SimpleEventObject)x0);
    }

    private static Logger logger = Logger.getLogger(com/sinitek/sirm/common/engine/event/utils/HttpEventProcess);
    private String location;

}
