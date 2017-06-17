// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgDataSynService.java

package com.sinitek.sirm.org.busin.support;

import com.sinitek.base.datasource.DataSourceFactory;
import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.ds.core.service.SimpleServiceRequest;
import com.sinitek.sirm.common.support.datasyn.DataSynHeader;
import com.sinitek.sirm.common.support.datasyn.DataSynServiceSupport;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.spirit.org.client.*;
import com.sinitek.spirit.org.core.*;
import com.sinitek.spirit.um.UserNameConflictException;
import java.io.*;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

// Referenced classes of package com.sinitek.sirm.org.busin.support:
//            OrgRelation, UnitObject, OrgProperty, OrgRelaOrder

public class OrgDataSynService extends DataSynServiceSupport
{

    public OrgDataSynService()
    {
    }

    public String serializeData(Object data)
    {
        if(null == data)
            return null;
        try
        {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(data);
            byte bts[] = bo.toByteArray();
            return (new BASE64Encoder()).encode(bts);
        }
        catch(Exception e)
        {
            LOGGER.error("\u5E8F\u5217\u5316\u5BF9\u8C61\u5931\u8D25", e);
        }
        return null;
    }

    public Object deSerializeData(String serialstr)
    {
        if(StringUtil.safeToString(serialstr, "").isEmpty())
            return null;
        byte objBytes[];
        objBytes = (new BASE64Decoder()).decodeBuffer(serialstr);
        if(objBytes == null || objBytes.length == 0)
            return null;
        try
        {
            ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            return oi.readObject();
        }
        catch(Exception e)
        {
            LOGGER.error("\u53CD\u5E8F\u5217\u5316\u5BF9\u8C61\u5931\u8D25", e);
        }
        return null;
    }

    public void saveEmployee(DataSynHeader header, Object data)
        throws Exception
    {
        Employee employee = (Employee)data;
        LOGGER.info((new StringBuilder()).append("[saveEmployee]employee.id:").append(employee.getId()).toString());
        IOrgService orgService = OrgServiceFactory.getOrgService();
        Employee local = orgService.getEmployeeByOrgidWithoutSession(employee.getId());
        if(local == null)
        {
            Map usermap = orgService.getUserinfoByJDBC(employee.getUserId());
            Map orgmap = orgService.getOrgObjectByJDBC(employee.getId());
            Map umap = orgService.getUserinfoByUsernameWithJDBC(employee.getEmpName());
            if(null != orgmap && orgmap.size() > 0 || null != usermap && usermap.size() > 0 || null != umap && umap.size() > 0)
            {
                LOGGER.error((new StringBuilder()).append("\u7EC4\u7EC7\u673A\u6784id\u4E3A \u3010").append(employee.getId()).append("\u3011 \u5DF2\u5B58\u5728\uFF0C\u8BF7\u6838\u5B9E\u6570\u636E\u662F\u5426\u6B63\u786E\uFF01").toString());
                throw new UserNameConflictException();
            }
            local = new Employee();
            BeanUtils.copyProperties(local, employee);
            try
            {
                orgService.insertEmployeeWithoutSession(local);
            }
            catch(Exception e)
            {
                LOGGER.error("[insert]\u7528\u6237\u4FE1\u606F\u540C\u6B65\u5931\u8D25\uFF01", e);
                throw new Exception(e);
            }
            try
            {
                markSynData("sprt_orgobject", header.getDatasrc(), employee.getId(), (new StringBuilder()).append("origid='").append(local.getId()).append("'").toString());
                markSynData("um_userinfo", header.getDatasrc(), employee.getUserId(), (new StringBuilder()).append("userid='").append(local.getUserId()).append("'").toString());
                LOGGER.info("\u7528\u6237\u5DF2\u540C\u6B65\u5B8C\u6210\u3010insert\u3011");
            }
            catch(Exception e)
            {
                LOGGER.error("\u4FEE\u6539\u6570\u636E\u540C\u6B65\u72B6\u6001 datasrc \u3001 orgid \u51FA\u9519\uFF01", e);
                throw new Exception(e);
            }
        } else
        {
            String localId = local.getId();
            String localUserId = local.getUserId();
            BeanUtils.copyProperties(local, employee);
            local.setId(localId);
            local.setUserId(localUserId);
            try
            {
                orgService.updateEmployeeWithoutSession(local);
            }
            catch(Exception e)
            {
                LOGGER.error("[update]\u7528\u6237\u4FE1\u606F\u540C\u6B65\u5931\u8D25\uFF01", e);
                throw new Exception(e);
            }
            try
            {
                markSynData("sprt_orgobject", header.getDatasrc(), employee.getId(), (new StringBuilder()).append("origid='").append(local.getId()).append("'").toString());
                markSynData("um_userinfo", header.getDatasrc(), employee.getUserId(), (new StringBuilder()).append("userid='").append(local.getUserId()).append("'").toString());
                LOGGER.info("\u7528\u6237\u5DF2\u540C\u6B65\u5B8C\u6210\u3010update\u3011");
            }
            catch(Exception e)
            {
                LOGGER.error("\u4FEE\u6539\u6570\u636E\u540C\u6B65\u72B6\u6001 datasrc \u3001 orgid \u51FA\u9519\uFF01", e);
                throw new Exception(e);
            }
        }
    }

    public void deleteQualifyByOrgid(DataSynHeader header, Object data)
        throws Exception
    {
        String orgid = (String)data;
        if(orgid.isEmpty())
        {
            LOGGER.error("data \u6570\u636E\u4E0D\u80FD\u4E3A\u7A7A");
            throw new Exception("\u7EC4\u7EC7\u673A\u6784id\u4E0D\u80FD\u4E3A\u7A7A\uFF01");
        } else
        {
            OrgServiceFactory.getOrgService().deleteQualifyByOrgId(orgid);
            LOGGER.info("\u7528\u6237\u8BC1\u4E66\u5DF2\u5220\u9664\u6210\u529F\uFF01");
            return;
        }
    }

    public void saveQualify(DataSynHeader header, Object data)
        throws Exception
    {
        IOrgService orgService = OrgServiceFactory.getOrgService();
        IQualifyInfo qualifyInfo = (IQualifyInfo)data;
        if(null == qualifyInfo)
            throw new Exception("\u53CD\u5E8F\u5217\u5316data -> IQualifyInfo \u5931\u8D25 ");
        try
        {
            IQualifyInfo local = new QualifyInfoImpl();
            local.setBrief(qualifyInfo.getBrief());
            local.setEndtime(qualifyInfo.getEndtime());
            local.setFailureDate(qualifyInfo.getFailureDate());
            local.setIssuingDate(qualifyInfo.getIssuingDate());
            local.setIssuingUnit(qualifyInfo.getIssuingUnit());
            local.setLev(qualifyInfo.getLev());
            local.setOrgId(qualifyInfo.getOrgId());
            local.setOrigid(qualifyInfo.getOrigid());
            local.setQualifyno(qualifyInfo.getQualifyno());
            local.setQualifyType(qualifyInfo.getQualifyType());
            local.setDataSrc(header.getDatasrc());
            orgService.saveQualify(local);
            LOGGER.info("\u8D44\u683C\u8BC1\u4E66\u4FDD\u5B58\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u8BC1\u4E66\u6570\u636E\u4FDD\u5B58\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void deleteEmpRelationships(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            OrgRelation relation = (OrgRelation)data;
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
            String type = StringUtil.safeToString(relation.getType(), "");
            if(type.isEmpty())
                throw new Exception("\u4EBA\u5458\u5173\u7CFB\u7C7B\u578B\u4E3A\u7A7A\uFF01type=");
            if(type.equalsIgnoreCase("allemp"))
            {
                IOrgObject _emp = _finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(null, _emp, "SUPERROLE");
                _updater.deleteRelationship(null, _emp, "SUPERTEAM");
                _updater.deleteRelationship(null, _emp, com.sinitek.spirit.org.core.IRelationship.RelationshipType.SUPERVISION.toString());
            }
            if(type.equalsIgnoreCase("SUPEXECUTE"))
            {
                IUnit _orgUnit = (IUnit)_finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(null, _orgUnit, "SUPEXECUTE");
            }
            if(type.equalsIgnoreCase("SUPBUSIN"))
            {
                IUnit _orgUnit = (IUnit)_finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(null, _orgUnit, "SUPBUSIN");
            }
            if(type.equalsIgnoreCase("TEAMER"))
            {
                IUnit parent = (IUnit)_finder.getOrgObject(relation.getUnit());
                IOrgObject _orgUnit = _finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(parent, _orgUnit, "TEAMER");
            }
            if(type.equalsIgnoreCase("SUPERTEAM"))
            {
                IUnit parent = (IUnit)_finder.getOrgObject(relation.getUnit());
                IOrgObject _orgUnit = _finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(parent, _orgUnit, "SUPERTEAM");
            }
            if(type.equalsIgnoreCase("SUPERPOST"))
            {
                IUnit parent = (IUnit)_finder.getOrgObject(relation.getUnit());
                IOrgObject _orgUnit = _finder.getOrgObject(relation.getObj());
                _updater.deleteRelationship(parent, _orgUnit, com.sinitek.spirit.org.core.IRelationship.RelationshipType.SUPERVISION.toString());
            }
            _updater.flush();
            LOGGER.info("\u7EC4\u7EC7\u673A\u6784\u5173\u7CFB\u5220\u9664\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u7EC4\u7EC7\u673A\u6784\u5173\u7CFB\u5220\u9664\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void saveOrgObject(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            UnitObject unit = (UnitObject)data;
            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            if(null == unit)
                throw new Exception(" saveOrgObject \u540C\u6B65\u7EC4\u7EC7\u673A\u6784\u4FE1\u606F\u4E3A\u7A7A");
            IUnit _unit = (IUnit)_finder.getOrgObject(unit.getId());
            if(null == _unit)
            {
                UnitImpl ut = new UnitImpl();
                ut.setUnitType(unit.getUnitType());
                ut.setId(unit.getId());
                ut.setDescription(unit.getDescription());
                ut.setName(unit.getName());
                AbstractOrgObject orgObject = ut;
                StringBuilder operData = new StringBuilder("INSERT|");
                operData.append("OBJ").append("|").append(orgObject.getId()).append("|").append(orgObject.toDataString()).append("|").append(0).append(";");
                String oper_data = operData.deleteCharAt(operData.length() - 1).toString();
                SimpleServiceRequest request = new SimpleServiceRequest("sprtorg_06");
                request.addParam("data", oper_data);
                CallServiceUtil.callService(request);
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

                LOGGER.info("\u7EC4\u7EC7\u673A\u6784\u6570\u636E\u6DFB\u52A0\u6210\u529F");
            } else
            {
                _updater.updateUnit(_unit, unit.getName(), com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString(), unit.getDescription());
                LOGGER.info("\u7EC4\u7EC7\u673A\u6784\u6570\u636E\u4FEE\u6539\u6210\u529F");
            }
            _updater.flush();
        }
        catch(Exception e)
        {
            LOGGER.error("\u7EC4\u7EC7\u673A\u6784\u6570\u636E\u540C\u6B65\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void deleteOrgObject(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            List _ids = (List)data;
            IOrgObject _object;
            for(Iterator i$ = _ids.iterator(); i$.hasNext(); LOGGER.info((new StringBuilder()).append("\u540C\u6B65\u5220\u9664\u7EC4\u7EC7\u673A\u6784\u201C").append(_object.getName()).append("\u201D\u6210\u529F\uFF01").toString()))
            {
                String _id = (String)i$.next();
                IOrgService orgService = OrgServiceFactory.getOrgService();
                IOrgUpdater _updater = OrgFactory.getOrgUpdater();
                IOrgFinder finder = OrgFactory.getOrgFinder();
                _object = finder.getOrgObject(_id);
                if(null != _object)
                {
                    _updater.deleteObject(_object, true);
                    orgService.delIndustryRelaByTeamId(_id);
                }
                _updater.flush();
            }

            LOGGER.info("\u540C\u6B65\u5220\u9664\u7EC4\u7EC7\u673A\u6784\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u540C\u6B65\u5220\u9664\u7EC4\u7EC7\u673A\u6784\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void saveOrgProperty(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            OrgProperty unit = (OrgProperty)data;
            IOrgPropertyService orgPropertyService = OrgServiceFactory.getOrgPropertyService();
            IOrgProperty orgProperty = orgPropertyService.getOrgPropertyByOrgId(unit.getOrgid());
            if(orgProperty == null)
                orgProperty = new OrgPropertyImpl();
            orgProperty.setOrgId(unit.getOrgid());
            orgProperty.setF1(Boolean.valueOf(unit.isFlag()));
            orgPropertyService.saveOrgProperty(orgProperty);
            LOGGER.info("\u540C\u6B65\u7EC4\u7EC7\u673A\u6784\u5C5E\u6027\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u540C\u6B65\u7EC4\u7EC7\u673A\u6784\u5C5E\u6027\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void saveRelationShipOrder(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            OrgRelaOrder orgRelaOrder = (OrgRelaOrder)data;
            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
            IOrgFinder finder = OrgFactory.getOrgFinder();
            IUnit parent = (IUnit)finder.getOrgObject(orgRelaOrder.getParent());
            IUnit unit = (IUnit)finder.getOrgObject(orgRelaOrder.getUnit());
            IUnit orgobject = null;
            if(!StringUtil.safeToString(orgRelaOrder.getOrgObject(), "").isEmpty())
                orgobject = (IUnit)finder.getOrgObject(orgRelaOrder.getOrgObject());
            _updater.changeRelationShipOrder(parent, unit, orgRelaOrder.getType(), orgobject, orgRelaOrder.isFlag());
            _updater.flush();
            LOGGER.info("\u540C\u6B65\u7EC4\u7EC7\u673A\u6784\u6392\u5E8F\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u540C\u6B65\u7EC4\u7EC7\u673A\u6784\u6392\u5E8F\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void addOrgRela(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            IOrgFinder finder = OrgFactory.getOrgFinder();
            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
            OrgRelation relation = (OrgRelation)data;
            String type = StringUtil.safeToString(relation.getType(), "");
            if(type.isEmpty())
                throw new Exception("\u4EBA\u5458\u5173\u7CFB\u7C7B\u578B\u4E3A\u7A7A\uFF01type=");
            IOrgObject _emp = finder.getOrgObject(relation.getObj());
            IUnit org = (IUnit)finder.getOrgObject(relation.getUnit());
            if(type.equalsIgnoreCase("role") && org.getUnitType().equals("ROLE"))
                _updater.addRelationShip(org, _emp, "SUPERROLE");
            if(type.equalsIgnoreCase("position") && org.getUnitType().equals(com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString()))
                _updater.addRelationShip(org, _emp, com.sinitek.spirit.org.core.IRelationship.RelationshipType.SUPERVISION.toString());
            if(type.equalsIgnoreCase("team") && org.getUnitType().equals(com.sinitek.spirit.org.core.IUnit.UnitType.TEAM.toString()))
                _updater.addRelationShip(org, _emp, "SUPERTEAM");
            if(type.equalsIgnoreCase("SUPEXECUTE"))
            {
                IUnit _orgUnit = (IUnit)finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, _orgUnit, "SUPEXECUTE");
            }
            if(type.equalsIgnoreCase("SUPBUSIN"))
            {
                IUnit _orgUnit = (IUnit)finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, _orgUnit, "SUPBUSIN");
            }
            if(type.equalsIgnoreCase("SUPPOST"))
            {
                IUnit _orgUnit = (IUnit)finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, _orgUnit, com.sinitek.spirit.org.core.IRelationship.RelationshipType.UNDERLINE.toString());
            }
            if(type.equalsIgnoreCase("SUPTEAM"))
            {
                IOrgObject emp = finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, emp, "SUPERTEAM");
            }
            if(type.equalsIgnoreCase("SUPTEAMORG"))
            {
                IOrgObject emp = finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, emp, com.sinitek.spirit.org.core.IRelationship.RelationshipType.UNDERLINE.toString());
            }
            if(type.equalsIgnoreCase("TEAMER"))
                _updater.addRelationShip(org, _emp, "TEAMER");
            if(type.equalsIgnoreCase("SUPDEPT"))
            {
                IUnit _orgUnit = (IUnit)finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, _orgUnit, com.sinitek.spirit.org.core.IRelationship.RelationshipType.UNDERLINE.toString());
            }
            if(type.equalsIgnoreCase("SUPROLEORG"))
            {
                IOrgObject emp = finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, emp, com.sinitek.spirit.org.core.IRelationship.RelationshipType.UNDERLINE.toString());
            }
            if(type.equalsIgnoreCase("SUPROLE"))
            {
                IOrgObject emp = finder.getOrgObject(relation.getObj());
                _updater.addRelationShip(org, emp, "SUPERROLE");
            }
            _updater.flush();
            LOGGER.info("\u7EC4\u7EC7\u673A\u6784\u5173\u7CFB\u6DFB\u52A0\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u7EC4\u7EC7\u673A\u6784\u5173\u7CFB\u6DFB\u52A0\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void updateInservice(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            String orgid = (String)data;
            IOrgService orgService = OrgServiceFactory.getOrgService();
            Employee employee = orgService.getEmployeeByOrgidWithoutSession(orgid);
            employee.setInservice(0);
            orgService.updateEmployeeWithoutSession(employee);
            LOGGER.info("\u540C\u6B65\u79BB\u804C\u4FE1\u606F\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u91CD\u7F6E\u5BC6\u7801\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void resetPassword(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            String orgid = (String)data;
            IOrgService orgService = OrgServiceFactory.getOrgService();
            Employee employee = orgService.getEmployeeByOrgidWithoutSession(orgid);
            employee.setPassword("");
            orgService.updateEmployeeWithoutSession(employee);
            LOGGER.info("\u91CD\u7F6E\u5BC6\u7801\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u91CD\u7F6E\u5BC6\u7801\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    public void unlockUser(DataSynHeader header, Object data)
        throws Exception
    {
        try
        {
            String userid = (String)data;
            IOrgService orgService = OrgServiceFactory.getOrgService();
            orgService.unlockUserWithoutSession(userid);
            LOGGER.info("\u7528\u6237\u89E3\u9501\u6210\u529F\uFF01");
        }
        catch(Exception e)
        {
            LOGGER.error("\u91CD\u7F6E\u5BC6\u7801\u5931\u8D25\uFF01", e);
            throw new Exception(e);
        }
    }

    private void markSynData(String tableName, String datasrc, String origid, String wheresql)
    {
        String sql = (new StringBuilder()).append("update ").append(tableName).append(" \nset datasrc=?,origid=?").append(" \nwhere ").append(wheresql).toString();
        Object params[] = {
            datasrc, origid
        };
        javax.sql.DataSource ds = DataSourceFactory.getInstance().getDataSouce("siniteksirm");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.update(sql, params);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/support/OrgDataSynService);

}
