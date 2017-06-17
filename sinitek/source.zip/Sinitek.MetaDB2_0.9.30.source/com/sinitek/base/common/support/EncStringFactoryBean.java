// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EncStringFactoryBean.java

package com.sinitek.base.common.support;

import com.sinitek.base.common.SinitekDES;
import org.springframework.beans.factory.FactoryBean;

public class EncStringFactoryBean
    implements FactoryBean
{

    public EncStringFactoryBean()
    {
    }

    public Object getObject()
        throws Exception
    {
        return SinitekDES.decrypt(encText);
    }

    public Class getObjectType()
    {
        return java/lang/String;
    }

    public boolean isSingleton()
    {
        return true;
    }

    public String getEncText()
    {
        return encText;
    }

    public void setEncText(String encText)
    {
        this.encText = encText;
    }

    private String encText;
}
