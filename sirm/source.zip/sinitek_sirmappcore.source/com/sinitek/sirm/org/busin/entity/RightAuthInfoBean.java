// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RightAuthInfoBean.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.spirit.right.core.IRightAuthInfo;

public class RightAuthInfoBean
    implements IRightAuthInfo
{

    public RightAuthInfoBean()
    {
    }

    public void setAuthOrgObjectpId(String authOrgObjectpId)
    {
        this.authOrgObjectpId = authOrgObjectpId;
    }

    public void setAuthOrgExpression(String authOrgExpression)
    {
        this.authOrgExpression = authOrgExpression;
    }

    public void setObjectKey(String objectKey)
    {
        this.objectKey = objectKey;
    }

    public void setRightDefineKey(String rightDefineKey)
    {
        this.rightDefineKey = rightDefineKey;
    }

    public void setRejectFlag(Boolean rejectFlag)
    {
        this.rejectFlag = rejectFlag;
    }

    public void setAuthRightType(String authRightType)
    {
        this.authRightType = authRightType;
    }

    public String getAuthOrgObjectId()
    {
        return authOrgObjectpId;
    }

    public String getAuthOrgExpression()
    {
        return authOrgExpression;
    }

    public String getObjectKey()
    {
        return objectKey;
    }

    public String getRightDefineKey()
    {
        return rightDefineKey;
    }

    public Boolean getRejectFlag()
    {
        return rejectFlag;
    }

    public String getAuthRightType()
    {
        return authRightType;
    }

    private String authOrgObjectpId;
    private String authOrgExpression;
    private String objectKey;
    private String rightDefineKey;
    private Boolean rejectFlag;
    private String authRightType;
}
