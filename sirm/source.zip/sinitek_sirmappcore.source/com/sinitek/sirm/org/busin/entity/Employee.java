// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Employee.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.spirit.um.client.impl.UMClientImpl;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;

public class Employee
    implements Serializable
{

    public Employee()
    {
        id = null;
        inservice = 0;
        otherProperties = null;
    }

    public String getOtherProperty(String propertyname)
    {
        return (String)loadOtherProperties().get(propertyname);
    }

    public String getOtherProperty(String propertyname, String dv)
    {
        return StringUtil.safeToString(loadOtherProperties().get(propertyname), dv);
    }

    public void setOtherProperty(String propertyname, String propertyvalue)
    {
        loadOtherProperties().put(propertyname, propertyvalue);
    }

    public void setOtherProperties(Map otherProperties)
    {
        this.otherProperties = otherProperties;
    }

    public Map loadOtherProperties()
    {
        if(otherProperties == null)
        {
            Map properties = new HashMap();
            if(StringUtils.isNotBlank(getUserId()))
                try
                {
                    properties = UMClientImpl.getInstance().getUserProperties(getUserId());
                    Map mapping = getPropertiesMapping();
                    String name;
                    for(Iterator i$ = mapping.keySet().iterator(); i$.hasNext(); properties.remove(name))
                        name = (String)i$.next();

                }
                catch(Exception e) { }
            otherProperties = properties;
        }
        return otherProperties;
    }

    public Map getAllUserProperties()
    {
        Map param = new HashMap();
        param.putAll(loadOtherProperties());
        Map mapping = getPropertiesMapping();
        String name;
        String value;
        for(Iterator i$ = mapping.keySet().iterator(); i$.hasNext(); param.put(name, value))
        {
            name = (String)i$.next();
            PropertyDescriptor pd = (PropertyDescriptor)mapping.get(name);
            value = "";
            if(pd == null)
                continue;
            try
            {
                value = StringUtil.safeToString(pd.getReadMethod().invoke(this, new Object[0]), "");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(value == null || value.equals("null"))
                value = "";
        }

        return param;
    }

    public static Employee instanceFromMap(Map data)
    {
        Employee result = new Employee();
        if(data != null)
        {
            Map pdMapping = getPropertiesMapping();
            Iterator it = data.keySet().iterator();
            do
            {
                if(!it.hasNext())
                    break;
                String name = (String)it.next();
                Object value = data.get(name);
                PropertyDescriptor pd = (PropertyDescriptor)pdMapping.get(name);
                if(pd != null)
                    try
                    {
                        BeanUtils.setProperty(result, pd.getName(), value != null ? value : "");
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                else
                if(value != null)
                    result.setOtherProperty(name, String.valueOf(value));
            } while(true);
        }
        return result;
    }

    private static Map getEmployeePropertyDescriptors()
    {
        Map pdMapping = new CaseInsensitiveMap();
        PropertyDescriptor pds[] = PropertyUtils.getPropertyDescriptors(com/sinitek/sirm/org/busin/entity/Employee);
        if(pds != null)
        {
            PropertyDescriptor arr$[] = pds;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                PropertyDescriptor pd = arr$[i$];
                pdMapping.put(pd.getName(), pd);
            }

        }
        return pdMapping;
    }

    public void addUserOtherProperties()
    {
        setOtherProperty("orgid", StringUtil.safeToString(id, ""));
        setOtherProperty("displayname", StringUtil.safeToString(empName, ""));
    }

    private static Map getPropertiesMapping()
    {
        Map pdMapping = getEmployeePropertyDescriptors();
        Map param = new HashMap();
        param.put("orgid", pdMapping.get("id"));
        param.put("objid", pdMapping.get("id"));
        param.put("displayname", pdMapping.get("empName"));
        param.put("sex", pdMapping.get("sex"));
        param.put("mobilephone", pdMapping.get("mobilePhone"));
        param.put("job", pdMapping.get("job"));
        param.put("email", pdMapping.get("email"));
        param.put("tel", pdMapping.get("tel"));
        param.put("qualifyno", pdMapping.get("qualifyno"));
        param.put("qualifytype", pdMapping.get("qualifytype"));
        param.put("inservice", pdMapping.get("inservice"));
        param.put("userid", pdMapping.get("userId"));
        param.put("username", pdMapping.get("userName"));
        param.put("password", pdMapping.get("password"));
        param.put("tel2", pdMapping.get("tel2"));
        param.put("familytelephone", pdMapping.get("familytelephone"));
        param.put("mobilephone2", pdMapping.get("mobilephone2"));
        param.put("familytelephone2", pdMapping.get("familytelephone2"));
        param.put("otherphone", pdMapping.get("otherphone"));
        param.put("otherphone2", pdMapping.get("otherphone2"));
        param.put("bp", pdMapping.get("bp"));
        param.put("office", pdMapping.get("office"));
        param.put("fax", pdMapping.get("fax"));
        param.put("fax2", pdMapping.get("fax2"));
        param.put("familyfax", pdMapping.get("familyfax"));
        param.put("familyfax2", pdMapping.get("familyfax2"));
        param.put("companyaddress", pdMapping.get("companyaddress"));
        param.put("familyaddress", pdMapping.get("familyaddress"));
        param.put("companyzip", pdMapping.get("companyzip"));
        param.put("familyzip", pdMapping.get("familyzip"));
        param.put("otheraddress", pdMapping.get("otheraddress"));
        param.put("otherzip", pdMapping.get("otherzip"));
        param.put("email1", pdMapping.get("email1"));
        param.put("email2", pdMapping.get("email2"));
        param.put("homepage", pdMapping.get("homepage"));
        param.put("qq", pdMapping.get("qq"));
        param.put("msn", pdMapping.get("msn"));
        param.put("where1", pdMapping.get("where"));
        param.put("addressbook", pdMapping.get("addressbook"));
        param.put("introduction", pdMapping.get("introduction"));
        param.put("passwordupdatetime", pdMapping.get("passwordUpdateTime"));
        param.put("userlocktime", pdMapping.get("userLockTime"));
        param.put("namepy", pdMapping.get("namePy"));
        param.put("rzrq", pdMapping.get("rzrq"));
        param.put("lzrq", pdMapping.get("lzrq"));
        return param;
    }

    public String getTel2()
    {
        return tel2;
    }

    public void setTel2(String tel2)
    {
        this.tel2 = tel2;
    }

    public String getFamilytelephone()
    {
        return familytelephone;
    }

    public void setFamilytelephone(String familytelephone)
    {
        this.familytelephone = familytelephone;
    }

    public String getMobilephone2()
    {
        return mobilephone2;
    }

    public void setMobilephone2(String mobilephone2)
    {
        this.mobilephone2 = mobilephone2;
    }

    public String getFamilytelephone2()
    {
        return familytelephone2;
    }

    public void setFamilytelephone2(String familytelephone2)
    {
        this.familytelephone2 = familytelephone2;
    }

    public String getOtherphone()
    {
        return otherphone;
    }

    public void setOtherphone(String otherphone)
    {
        this.otherphone = otherphone;
    }

    public String getOtherphone2()
    {
        return otherphone2;
    }

    public void setOtherphone2(String otherphone2)
    {
        this.otherphone2 = otherphone2;
    }

    public String getBp()
    {
        return bp;
    }

    public void setBp(String bp)
    {
        this.bp = bp;
    }

    public String getNamePy()
    {
        return namePy;
    }

    public void setNamePy(String namePy)
    {
        this.namePy = namePy;
    }

    public String getOffice()
    {
        return office;
    }

    public void setOffice(String office)
    {
        this.office = office;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getFax2()
    {
        return fax2;
    }

    public void setFax2(String fax2)
    {
        this.fax2 = fax2;
    }

    public String getFamilyfax()
    {
        return familyfax;
    }

    public void setFamilyfax(String familyfax)
    {
        this.familyfax = familyfax;
    }

    public String getFamilyfax2()
    {
        return familyfax2;
    }

    public void setFamilyfax2(String familyfax2)
    {
        this.familyfax2 = familyfax2;
    }

    public String getCompanyaddress()
    {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress)
    {
        this.companyaddress = companyaddress;
    }

    public String getFamilyaddress()
    {
        return familyaddress;
    }

    public void setFamilyaddress(String familyaddress)
    {
        this.familyaddress = familyaddress;
    }

    public String getCompanyzip()
    {
        return companyzip;
    }

    public void setCompanyzip(String companyzip)
    {
        this.companyzip = companyzip;
    }

    public String getFamilyzip()
    {
        return familyzip;
    }

    public void setFamilyzip(String familyzip)
    {
        this.familyzip = familyzip;
    }

    public String getOtheraddress()
    {
        return otheraddress;
    }

    public void setOtheraddress(String otheraddress)
    {
        this.otheraddress = otheraddress;
    }

    public String getOtherzip()
    {
        return otherzip;
    }

    public void setOtherzip(String otherzip)
    {
        this.otherzip = otherzip;
    }

    public String getEmail1()
    {
        return email1;
    }

    public void setEmail1(String email1)
    {
        this.email1 = email1;
    }

    public String getEmail2()
    {
        return email2;
    }

    public void setEmail2(String email2)
    {
        this.email2 = email2;
    }

    public String getHomepage()
    {
        return homepage;
    }

    public void setHomepage(String homepage)
    {
        this.homepage = homepage;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getMsn()
    {
        return msn;
    }

    public void setMsn(String msn)
    {
        this.msn = msn;
    }

    public String getWhere()
    {
        return where;
    }

    public void setWhere(String where)
    {
        this.where = where;
    }

    public String getAddressbook()
    {
        return addressbook;
    }

    public void setAddressbook(String addressbook)
    {
        this.addressbook = addressbook;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getEmpName()
    {
        return empName;
    }

    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public int getInservice()
    {
        return inservice;
    }

    public void setInservice(int inservice)
    {
        this.inservice = inservice;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getPostid()
    {
        return postid;
    }

    public void setPostid(String postid)
    {
        this.postid = postid;
    }

    public String getQualifyno()
    {
        return qualifyno;
    }

    public void setQualifyno(String qualifyno)
    {
        this.qualifyno = qualifyno;
    }

    public int getQualifytype()
    {
        return qualifytype;
    }

    public void setQualifytype(int qualifytype)
    {
        this.qualifytype = qualifytype;
    }

    public String getPasswordUpdateTime()
    {
        return passwordUpdateTime;
    }

    public void setPasswordUpdateTime(String passwordUpdateTime)
    {
        this.passwordUpdateTime = passwordUpdateTime;
    }

    public String getUserLockTime()
    {
        return userLockTime;
    }

    public String getRzrq()
    {
        return rzrq;
    }

    public void setRzrq(String rzrq)
    {
        this.rzrq = rzrq;
    }

    public String getLzrq()
    {
        return lzrq;
    }

    public void setLzrq(String lzrq)
    {
        this.lzrq = lzrq;
    }

    public void setUserLockTime(String userLockTime)
    {
        this.userLockTime = userLockTime;
    }

    private String id;
    private String userName;
    private String password;
    private String userId;
    private String empName;
    private int inservice;
    private String email;
    private int sex;
    private String tel;
    private String job;
    private String mobilePhone;
    private String postid;
    private String qualifyno;
    private int qualifytype;
    private String tel2;
    private String familytelephone;
    private String mobilephone2;
    private String familytelephone2;
    private String otherphone;
    private String otherphone2;
    private String bp;
    private String office;
    private String fax;
    private String fax2;
    private String familyfax;
    private String familyfax2;
    private String companyaddress;
    private String familyaddress;
    private String companyzip;
    private String familyzip;
    private String otheraddress;
    private String otherzip;
    private String email1;
    private String email2;
    private String homepage;
    private String qq;
    private String msn;
    private String where;
    private String addressbook;
    private String introduction;
    private String passwordUpdateTime;
    private String userLockTime;
    private Map otherProperties;
    private String namePy;
    private String rzrq;
    private String lzrq;
}
