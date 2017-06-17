// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JsonUtil.java

package com.sinitek.sirm.web.workflow.util;

import java.util.*;
import org.json.*;

public class JsonUtil
{

    public JsonUtil()
    {
    }

    public static Map jsonToMap(JSONObject jsonObj)
        throws JSONException
    {
        Map jsonMap = new HashMap();
        for(Iterator jsonKeys = jsonObj.keys(); jsonKeys.hasNext();)
        {
            String jsonKey = (String)jsonKeys.next();
            Object jsonValObj = jsonObj.get(jsonKey);
            if(jsonValObj instanceof JSONArray)
                jsonMap.put(jsonKey, jsonToList((JSONArray)jsonValObj));
            else
            if(jsonValObj instanceof JSONObject)
                jsonMap.put(jsonKey, jsonToMap((JSONObject)jsonValObj));
            else
                jsonMap.put(jsonKey, jsonValObj);
        }

        return jsonMap;
    }

    public static List jsonToList(JSONArray jsonArr)
        throws JSONException
    {
        List jsonToMapList = new ArrayList();
        for(int i = 0; i < jsonArr.length(); i++)
        {
            Object object = jsonArr.get(i);
            if(object instanceof JSONArray)
            {
                jsonToMapList.add(jsonToList((JSONArray)object));
                continue;
            }
            if(object instanceof JSONObject)
                jsonToMapList.add(jsonToMap((JSONObject)object));
            else
                jsonToMapList.add(object);
        }

        return jsonToMapList;
    }
}
