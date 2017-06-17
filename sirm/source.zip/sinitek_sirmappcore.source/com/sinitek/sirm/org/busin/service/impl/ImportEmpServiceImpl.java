// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImportEmpServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.IServiceResponse;
import com.sinitek.ds.core.service.SimpleServiceRequest;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.utils.EncryptUtil;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.sirm.org.utils.OrgUtils;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.org.core.IUnit;
import com.sinitek.spirit.org.server.entity.*;
import com.sinitek.spirit.right.client.CallServiceUtil;
import com.sinitek.spirit.um.server.userdb.UserInfo;
import com.sinitek.spirit.um.server.userdb.UserProperty;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ImportEmpServiceImpl extends MetaDBContextSupport
    implements IImportEmpService
{

    public ImportEmpServiceImpl()
    {
    }

    public String saveEmps(List emps)
    {
        IMetaDBContext metaDBContext;
        IOrgFinder _finder;
        metaDBContext = getMetaDBContext();
        _finder = OrgFactory.getOrgFinder();
        IOrgService _orgsvc;
        ISetting setting;
        Iterator i$;
        _orgsvc = OrgServiceFactory.getOrgService();
        setting = CommonServiceFactory.getSettingService().getSetting("ORG", "PASSWORD");
        i$ = emps.iterator();
_L1:
        Employee emp;
        String pstid;
        String deptid;
        if(!i$.hasNext())
            break MISSING_BLOCK_LABEL_696;
        emp = (Employee)i$.next();
        pstid = StringUtil.safeToString(emp.getPostid(), "");
        deptid = StringUtil.safeToString(emp.getOtherProperty("deptid"), "");
        List orgidlist = metaDBContext.createSqlQuery("select seq_orgid.nextval as orgid from dual").getResult();
        if(orgidlist.size() <= 0)
        {
            LOGGER.error("\u83B7\u53D6\u5E8Fseq_orgid\u5217\u5931\u8D25");
            return "\u7528\u6237\u4FE1\u606F\u4FDD\u5B58\u5931\u8D25";
        }
        List useridlist;
        String orgid;
        String userid;
        IOrgObject orgObject;
        UserInfo userInfo;
        UserProperty property;
        UserProperty displaynameproperty;
        UserProperty orgidproperty;
        IOrgUserExtendInfo extendInfo;
        try
        {
            useridlist = metaDBContext.createSqlQuery("select seq_userid.nextval as userid from dual").getResult();
            if(useridlist.size() <= 0)
            {
                LOGGER.error("\u83B7\u53D6\u5E8Fseq_userid\u5217\u5931\u8D25");
                return "\u7528\u6237\u4FE1\u606F\u4FDD\u5B58\u5931\u8D25";
            }
        }
        catch(Exception e)
        {
            metaDBContext.rollback();
            LOGGER.error("\u7528\u6237\u4FDD\u5B58\u5931\u8D25", e);
            return "\u7528\u6237\u4FDD\u5B58\u5931\u8D25";
        }
        orgid = getOrgId("emp");
        userid = StringUtil.safeToString(((Map)useridlist.get(0)).get("userid"), "");
        orgObject = (IOrgObject)metaDBContext.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgObject);
        orgObject.setOrgId(orgid);
        orgObject.setOrgName(emp.getEmpName());
        orgObject.setObjectType(ObjectType.EMPLOYEE);
        orgObject.setUserId(userid);
        orgObject.setInservice(Boolean.valueOf(true));
        orgObject.save();
        userInfo = (UserInfo)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserInfo);
        userInfo.setUserId(userid);
        userInfo.setUserName(emp.getUserName());
        userInfo.setPassword((new EncryptUtil()).encodeMD5(setting.getValue()));
        userInfo.setLockFlag(Boolean.valueOf(false));
        userInfo.save();
        property = (UserProperty)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserProperty);
        property.setUserId(userid);
        property.setName("email");
        property.setValue(emp.getEmail());
        property.save();
        displaynameproperty = (UserProperty)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserProperty);
        displaynameproperty.setUserId(userid);
        displaynameproperty.setName("displayname");
        displaynameproperty.setValue(emp.getEmpName());
        displaynameproperty.save();
        orgidproperty = (UserProperty)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserProperty);
        orgidproperty.setUserId(userid);
        orgidproperty.setName("orgid");
        orgidproperty.setValue(orgid);
        orgidproperty.save();
        if(!deptid.isEmpty())
        {
            IUnit dept = (IUnit)_finder.getOrgObject(deptid);
            if(dept != null)
            {
                IOrgRelationInfo orgRelation = (IOrgRelationInfo)metaDBContext.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgRelationInfo);
                orgRelation.setToObjectId(orgid);
                orgRelation.setFromObjectId(deptid);
                orgRelation.setRelationType("SUPERVISION");
                orgRelation.setOrderCode(Integer.valueOf(0));
                orgRelation.save();
            }
        }
        if(!pstid.isEmpty())
        {
            IUnit _post = (IUnit)_finder.getOrgObject(pstid);
            if(_post != null)
            {
                IOrgRelationInfo orgRelation = (IOrgRelationInfo)metaDBContext.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgRelationInfo);
                orgRelation.setToObjectId(orgid);
                orgRelation.setFromObjectId(pstid);
                orgRelation.setRelationType("SUPERVISION");
                orgRelation.setOrderCode(Integer.valueOf(0));
                orgRelation.save();
            }
        }
        extendInfo = new OrgUserExtendInfoImpl();
        OrgUtils.copyUserProperties(emp, extendInfo, true);
        extendInfo.setUserid(userid);
        _orgsvc.saveOrgUserExtendInfo(extendInfo);
          goto _L1
        metaDBContext.commit();
        IMetaDBCacheContext context = MetaDBContextFactory.getInstance().getCacheContext();
        String _entitynames[] = {
            "ORGRELATIONINFO", "ORGOBJECT"
        };
        String arr$[] = _entitynames;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String e = arr$[i$];
            if(StringUtils.isNotBlank(e))
                context.notifyReload(new String[] {
                    e
                });
        }

        return "\u7528\u6237\u4FDD\u5B58\u6210\u529F";
    }

    public boolean checkEmpName(String username)
    {
        List list = getMetaDBContext().createSqlQuery((new StringBuilder()).append("select t.* from um_userinfo t where t.username = '").append(username).append("'").toString()).getResult();
        return list.size() > 0;
    }

    private String getOrgId(String typeStr)
    {
        SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_01");
        request.addParam("orgtype", typeStr);
        IServiceResponse response = CallServiceUtil.callService(request);
        return response.getString("orgid");
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/service/impl/ImportEmpServiceImpl);

}
