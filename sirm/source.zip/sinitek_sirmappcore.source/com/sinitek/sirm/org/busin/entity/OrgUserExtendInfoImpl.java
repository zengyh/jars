// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgUserExtendInfoImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgUserExtendInfo

public class OrgUserExtendInfoImpl extends MetaObjectImpl
    implements IOrgUserExtendInfo
{

    public OrgUserExtendInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("USEREXTENDINFO"));
    }

    public OrgUserExtendInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getLzrq()
    {
        return (String)get("Lzrq");
    }

    public void setLzrq(String value)
    {
        put("Lzrq", value);
    }

    public Integer getSex()
    {
        return (Integer)get("Sex");
    }

    public void setSex(Integer value)
    {
        put("Sex", value);
    }

    public String getTel()
    {
        return (String)get("Tel");
    }

    public void setTel(String value)
    {
        put("Tel", value);
    }

    public String getTel2()
    {
        return (String)get("Tel2");
    }

    public void setTel2(String value)
    {
        put("Tel2", value);
    }

    public String getJob()
    {
        return (String)get("Job");
    }

    public void setJob(String value)
    {
        put("Job", value);
    }

    public String getEmail()
    {
        return (String)get("Email");
    }

    public void setEmail(String value)
    {
        put("Email", value);
    }

    public String getUserid()
    {
        return (String)get("Userid");
    }

    public void setUserid(String value)
    {
        put("Userid", value);
    }

    public String getMobilephone2()
    {
        return (String)get("Mobilephone2");
    }

    public void setMobilephone2(String value)
    {
        put("Mobilephone2", value);
    }

    public String getFamilytelephone()
    {
        return (String)get("Familytelephone");
    }

    public void setFamilytelephone(String value)
    {
        put("Familytelephone", value);
    }

    public String getFamilytelephone2()
    {
        return (String)get("Familytelephone2");
    }

    public void setFamilytelephone2(String value)
    {
        put("Familytelephone2", value);
    }

    public String getOtherphone()
    {
        return (String)get("Otherphone");
    }

    public void setOtherphone(String value)
    {
        put("Otherphone", value);
    }

    public String getOtherphone2()
    {
        return (String)get("Otherphone2");
    }

    public void setOtherphone2(String value)
    {
        put("Otherphone2", value);
    }

    public String getBp()
    {
        return (String)get("Bp");
    }

    public void setBp(String value)
    {
        put("Bp", value);
    }

    public String getOffice()
    {
        return (String)get("Office");
    }

    public void setOffice(String value)
    {
        put("Office", value);
    }

    public String getFax()
    {
        return (String)get("Fax");
    }

    public void setFax(String value)
    {
        put("Fax", value);
    }

    public String getFax2()
    {
        return (String)get("Fax2");
    }

    public void setFax2(String value)
    {
        put("Fax2", value);
    }

    public String getFamilyfax()
    {
        return (String)get("Familyfax");
    }

    public void setFamilyfax(String value)
    {
        put("Familyfax", value);
    }

    public String getFamilyfax2()
    {
        return (String)get("Familyfax2");
    }

    public void setFamilyfax2(String value)
    {
        put("Familyfax2", value);
    }

    public String getCompanyaddress()
    {
        return (String)get("Companyaddress");
    }

    public void setCompanyaddress(String value)
    {
        put("Companyaddress", value);
    }

    public String getCompanyzip()
    {
        return (String)get("Companyzip");
    }

    public void setCompanyzip(String value)
    {
        put("Companyzip", value);
    }

    public String getFamilyaddress()
    {
        return (String)get("Familyaddress");
    }

    public void setFamilyaddress(String value)
    {
        put("Familyaddress", value);
    }

    public String getFamilyzip()
    {
        return (String)get("Familyzip");
    }

    public void setFamilyzip(String value)
    {
        put("Familyzip", value);
    }

    public String getOtheraddress()
    {
        return (String)get("Otheraddress");
    }

    public void setOtheraddress(String value)
    {
        put("Otheraddress", value);
    }

    public String getOtherzip()
    {
        return (String)get("Otherzip");
    }

    public void setOtherzip(String value)
    {
        put("Otherzip", value);
    }

    public String getEmail1()
    {
        return (String)get("Email1");
    }

    public void setEmail1(String value)
    {
        put("Email1", value);
    }

    public String getEmail2()
    {
        return (String)get("Email2");
    }

    public void setEmail2(String value)
    {
        put("Email2", value);
    }

    public String getHomepage()
    {
        return (String)get("Homepage");
    }

    public void setHomepage(String value)
    {
        put("Homepage", value);
    }

    public Integer getWhere1()
    {
        return (Integer)get("Where1");
    }

    public void setWhere1(Integer value)
    {
        put("Where1", value);
    }

    public String getQq()
    {
        return (String)get("Qq");
    }

    public void setQq(String value)
    {
        put("Qq", value);
    }

    public String getMsn()
    {
        return (String)get("Msn");
    }

    public void setMsn(String value)
    {
        put("Msn", value);
    }

    public String getAddressbook()
    {
        return (String)get("Addressbook");
    }

    public void setAddressbook(String value)
    {
        put("Addressbook", value);
    }

    public String getIntroduction()
    {
        return (String)get("Introduction");
    }

    public void setIntroduction(String value)
    {
        put("Introduction", value);
    }

    public String getPasswordUpdateTime()
    {
        return (String)get("PasswordUpdateTime");
    }

    public void setPasswordUpdateTime(String value)
    {
        put("PasswordUpdateTime", value);
    }

    public String getUserLockTime()
    {
        return (String)get("UserLockTime");
    }

    public void setUserLockTime(String value)
    {
        put("UserLockTime", value);
    }

    public String getPostid()
    {
        return (String)get("Postid");
    }

    public void setPostid(String value)
    {
        put("Postid", value);
    }

    public String getQualifyno()
    {
        return (String)get("Qualifyno");
    }

    public void setQualifyno(String value)
    {
        put("Qualifyno", value);
    }

    public Integer getQualifytype()
    {
        return (Integer)get("Qualifytype");
    }

    public void setQualifytype(Integer value)
    {
        put("Qualifytype", value);
    }

    public String getNamePy()
    {
        return (String)get("NamePy");
    }

    public void setNamePy(String value)
    {
        put("NamePy", value);
    }

    public String getMobilePhone()
    {
        return (String)get("MobilePhone");
    }

    public void setMobilePhone(String value)
    {
        put("MobilePhone", value);
    }

    public String getRzrq()
    {
        return (String)get("Rzrq");
    }

    public void setRzrq(String value)
    {
        put("Rzrq", value);
    }
}
