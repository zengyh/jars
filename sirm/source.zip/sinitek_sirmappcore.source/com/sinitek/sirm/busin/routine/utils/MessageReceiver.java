// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageReceiver.java

package com.sinitek.sirm.busin.routine.utils;


public class MessageReceiver
{

    public MessageReceiver()
    {
        email = null;
        mobile = null;
        empId = null;
        receivetype = null;
        publicmessage = null;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmpId()
    {
        return empId;
    }

    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getReceivetype()
    {
        return receivetype;
    }

    public void setReceivetype(String receivetype)
    {
        this.receivetype = receivetype;
    }

    public String getPublicmessage()
    {
        return publicmessage;
    }

    public void setPublicmessage(String publicmessage)
    {
        this.publicmessage = publicmessage;
    }

    private String email;
    private String mobile;
    private String empId;
    private String receivetype;
    private String publicmessage;
}
