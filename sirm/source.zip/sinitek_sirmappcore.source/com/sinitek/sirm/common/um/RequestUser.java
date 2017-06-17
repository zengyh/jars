// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestUser.java

package com.sinitek.sirm.common.um;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class RequestUser
{

    public RequestUser()
    {
        userId = null;
        name = null;
        userProperties = new HashMap();
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDisplayName()
    {
        String _displayname = (String)userProperties.get("displayname");
        return StringUtils.isNotBlank(_displayname) ? _displayname : name;
    }

    public Map getUserProperties()
    {
        return userProperties;
    }

    public void setUserProperties(Map userProperties)
    {
        this.userProperties = userProperties;
    }

    public String getOrgId()
    {
        return userProperties.containsKey("orgid") ? (String)userProperties.get("orgid") : null;
    }

    public static final String PROP_DISPLAYNAME = "displayname";
    private String userId;
    private String name;
    private Map userProperties;
}
