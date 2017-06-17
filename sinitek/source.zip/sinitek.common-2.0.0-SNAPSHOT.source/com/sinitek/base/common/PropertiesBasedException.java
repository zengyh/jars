// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertiesBasedException.java

package com.sinitek.base.common;

import java.util.Properties;

// Referenced classes of package com.sinitek.base.common:
//            BaseException, PropertiesHelper

public abstract class PropertiesBasedException extends BaseException
{

    public PropertiesBasedException(String errorCode)
    {
        super(errorCode);
    }

    public PropertiesBasedException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public PropertiesBasedException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode);
        this.errorMsgParams = errorMsgParams;
    }

    public PropertiesBasedException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause);
        this.errorMsgParams = errorMsgParams;
    }

    public String getErrorMsg()
    {
        String targetStr = PropertiesHelper.getProperties(getPropertieFile()).getProperty(getCode());
        if(targetStr == null)
            return getCode();
        if(errorMsgParams != null && errorMsgParams.length > 0)
        {
            for(int i = 0; i < errorMsgParams.length; i++)
            {
                Object temp = errorMsgParams[i];
                if(temp != null)
                {
                    String str = temp.toString();
                    str = str.replaceAll("\\\\", "\\\\\\\\");
                    str = str.replaceAll("\\$", "\\\\\\$");
                    targetStr = targetStr.replaceAll((new StringBuilder()).append("\\{").append(i).append("\\}").toString(), str);
                }
            }

        }
        return targetStr;
    }

    public String getMessage()
    {
        return toString();
    }

    public String getLocalizedMessage()
    {
        return getMessage();
    }

    public abstract String getPropertieFile();

    protected Object errorMsgParams[];
}
