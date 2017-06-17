// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.setting.service.ISettingService;
import com.sinitek.sirm.common.utils.EncryptUtil;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.ObjectUtils;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.org.busin.entity.Department;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.entity.IOrgLogonLog;
import com.sinitek.sirm.org.busin.entity.IOrgProperty;
import com.sinitek.sirm.org.busin.entity.IOrgTeamIndustryRela;
import com.sinitek.sirm.org.busin.entity.IOrgUserExtendInfo;
import com.sinitek.sirm.org.busin.entity.IQualifyInfo;
import com.sinitek.sirm.org.busin.entity.OrgInfo;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.entity.OrgUserExtendInfoImpl;
import com.sinitek.sirm.org.busin.entity.Position;
import com.sinitek.sirm.org.busin.entity.RightAuthInfoBean;
import com.sinitek.sirm.org.busin.entity.Role;
import com.sinitek.sirm.org.busin.entity.Team;
import com.sinitek.sirm.org.busin.enumerate.OrgType;
import com.sinitek.sirm.org.busin.service.IOrgPropertyService;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.IPasswordService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.sirm.org.utils.OrgUtils;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.IEmployee;
import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.org.core.IOrgObject;
import com.sinitek.spirit.org.core.IOrgUpdater;
import com.sinitek.spirit.org.core.IRelationship;
import com.sinitek.spirit.org.core.IUnit;
import com.sinitek.spirit.org.server.entity.IOrgRelationInfo;
import com.sinitek.spirit.org.server.entity.ObjectType;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.IRightUpdater;
import com.sinitek.spirit.um.AuthenticationFailedException;
import com.sinitek.spirit.um.InsufficientPrivilegesException;
import com.sinitek.spirit.um.InvalidSessionException;
import com.sinitek.spirit.um.LockedUserException;
import com.sinitek.spirit.um.NoSuchUserException;
import com.sinitek.spirit.um.UserNameConflictException;
import com.sinitek.spirit.um.client.UMClient;
import com.sinitek.spirit.um.client.UMFactory;
import com.sinitek.spirit.um.client.UserSession;
import com.sinitek.spirit.um.server.UserDatabase;
import com.sinitek.spirit.um.server.userdb.UserDatabaseFactory;
import com.sinitek.spirit.um.server.userdb.UserInfo;
import com.sinitek.spirit.um.server.userdb.UserProperty;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class OrgServiceImpl extends MetaDBContextSupport
    implements IOrgService
{

    public OrgServiceImpl()
    {
    }

    public List findAllEmployees()
    {
        List result = findAllEmployees(null);
        return result;
    }

    public List findAllEmployees(Map params)
    {
        List result = new ArrayList();
        List mapList = findAllEmployeesOfMap(params);
        Employee _employee;
        for(Iterator i$ = mapList.iterator(); i$.hasNext(); result.add(_employee))
        {
            Map _map = (Map)i$.next();
            _employee = OrgUtils.getEmployeeByMap(_map);
        }

        return result;
    }

    public List findAllEmployeesOfMap(Map params)
    {
        List result = null;
        Map map = new HashMap();
        StringBuilder sql = (new StringBuilder()).append("select u.userid,u.username,o.inservice,o.orgid as objid,o.orgid,o.orgname as displayname,e.sex,e.email,e.tel,\n").append("e.job,e.mobilephone,e.tel2,e.familytelephone,e.mobilephone2,e.familytelephone2,e.otherphone,\n").append("e.otherphone2,e.bp,e.office,e.fax,e.fax2,e.familyfax,e.familyfax2,e.companyaddress,\n").append("e.familyaddress,e.companyzip,e.familyzip,e.otheraddress,e.otherzip,e.email1,e.email2,e.homepage,\n").append("e.qq,e.msn,e.where1,e.addressbook,e.introduction,e.namepy,e.rzrq,e.lzrq \n").append("from um_userinfo u,sprt_orgobject o,org_userextendinfo e \n").append("where u.userid=o.userid and u.userid=e.userid and u.username not like '$%' \n");
        if(params != null)
        {
            if(params.get("jointname") != null && !"".equals(params.get("jointname")))
            {
                String jointName = StringUtil.safeToString(params.get("jointname"), "");
                sql.append((new StringBuilder()).append("and (u.username like '%").append(jointName).append("%'  or o.orgname like '%").append(jointName).append("%' or e.namepy like '%").append(jointName).append("%' ) ").toString());
            }
            if(params.get("city") != null && !"".equals(params.get("city")))
                sql.append((new StringBuilder()).append(" and e.where1 ='").append(params.get("city")).append("' ").toString());
            if(params.get("username") != null && !"".equals(params.get("username")))
                sql.append((new StringBuilder()).append(" and u.username like '%").append(params.get("username")).append("%'").toString());
            if(params.get("empname") != null && !"".equals(params.get("empname")))
                sql.append((new StringBuilder()).append(" and (o.orgname like '%").append(params.get("empname")).append("%' or e.namepy like '%").append(params.get("empname")).append("%' )").toString());
            if(params.get("jqEmpName") != null && !"".equals(params.get("jqEmpName")))
            {
                sql.append(" and o.orgname =:orgname ");
                map.put("orgname", params.get("jqEmpName"));
            }
            int inservice = 2;
            if(params.get("inservice") != null && !"".equals(params.get("inservice")))
            {
                sql.append((new StringBuilder()).append(" and o.inservice =").append(params.get("inservice")).toString());
                inservice = NumberTool.safeToInteger(params.get("inservice"), Integer.valueOf(1)).intValue();
            }
            if(params.get("unUser") != null && ((Boolean)params.get("unUser")).booleanValue())
            {
                IOrgFinder _finder = OrgFactory.getOrgFinder();
                String _rootid = _finder.getRoot().getId();
                if(_rootid != null && !_rootid.equals(""))
                    sql.append((new StringBuilder()).append(" and e.userid not in (select userid from sprt_orgobject  where orgid in(select distinct toobjectid from sprt_orgrela \n where fromobjectid in (select r.toobjectid from sprt_orgobject o right join\n (select * from sprt_orgrela\u3000CONNECT BY PRIOR toobjectid = fromobjectid and relationtype='UNDERLINE'\n START WITH fromobjectid=").append(_rootid).append(") r on o.orgid=r.toobjectid where unittype in('UNIT','POSITION')\n").append(" ) and relationtype ='SUPERVISION'))").toString());
            }
            if(params.get("strutureid") != null && !"".equals(params.get("strutureid")))
            {
                List s2 = new ArrayList();
                String _strutureItem[] = params.get("strutureid").toString().split(",");
                String arr$[] = _strutureItem;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String _str = arr$[i$];
                    String _unitid[] = _str.split(":");
                    s2.addAll(findUserIdsByUnitId(_unitid[0], inservice));
                }

                StringBuilder userids = new StringBuilder();
                String userid;
                for(Iterator i$ = s2.iterator(); i$.hasNext(); userids.append(userid))
                {
                    userid = (String)i$.next();
                    if(!"".equals(userids.toString()))
                        userids.append(",");
                }

                if(!"".equals(userids.toString()))
                {
                    String str[] = userids.toString().split(",");
                    QueryUtils.buildIn("e.userid", str, sql, map);
                } else
                {
                    sql.append(" and 1=0");
                }
            }
            String userids = StringUtil.safeToString(params.get("userids"), "");
            String orgids = StringUtil.safeToString(params.get("orgids"), "");
            String webserusername = StringUtil.safeToString(params.get("webserusername"), "");
            if(!userids.isEmpty() && userids.split(",").length > 0)
            {
                String str[] = userids.split(",");
                QueryUtils.buildIn("e.userid", str, sql, map);
            } else
            if(!orgids.isEmpty() && orgids.split(",").length > 0)
            {
                String str[] = orgids.split(",");
                QueryUtils.buildIn("o.orgid", str, sql, map);
            } else
            if(!webserusername.isEmpty() && webserusername.split(",").length > 0)
            {
                String str[] = webserusername.split(",");
                QueryUtils.buildIn("u.username", str, sql, map);
            }
        }
        IMetaDBQuery _metaDbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(map.size() > 0)
            _metaDbQuery.setParameters(map);
        result = _metaDbQuery.getResult();
        String sqlstr = "select userid,name,value from um_userproperty";
        _metaDbQuery = getMetaDBContext().createSqlQuery(sqlstr);
        List list = _metaDbQuery.getResult();
        Map groupMap = ObjectUtils.groupList("userid", list);
        for(Iterator i$ = result.iterator(); i$.hasNext();)
        {
            Map infoData = (Map)i$.next();
            String userid = StringUtil.safeToString(infoData.get("userid"), "");
            List tempList = (List)groupMap.get(userid);
            if(null != tempList && tempList.size() > 0)
            {
                Iterator i$ = tempList.iterator();
                while(i$.hasNext()) 
                {
                    Map propertyMap = (Map)i$.next();
                    String key = StringUtil.safeToString(propertyMap.get("name"), "");
                    String value = StringUtil.safeToString(propertyMap.get("value"), "");
                    if(StringUtils.isNotBlank(key))
                        infoData.put(key, value);
                }
            }
        }

        return result;
    }

    public List findAllEmployeesOfParam(Map params)
    {
        List result = null;
        Map map = new HashMap();
        StringBuilder sql = (new StringBuilder()).append("select o.orgid as objid,o.orgid,o.orgname as displayname,e.namepy\n").append("from um_userinfo u,sprt_orgobject o,org_userextendinfo e \n").append("where u.userid=o.userid and u.userid=e.userid and u.username not like '$%' \n");
        if(params != null)
        {
            if(params.get("jointname") != null && !"".equals(params.get("jointname")))
            {
                String jointName = StringUtil.safeToString(params.get("jointname"), "");
                sql.append((new StringBuilder()).append("and (u.username like '%").append(jointName).append("%'  or o.orgname like '%").append(jointName).append("%' or e.namepy like '%").append(jointName).append("%' ) ").toString());
            }
            if(params.get("city") != null && !"".equals(params.get("city")))
                sql.append((new StringBuilder()).append(" and e.where1 ='").append(params.get("city")).append("' ").toString());
            if(params.get("username") != null && !"".equals(params.get("username")))
                sql.append((new StringBuilder()).append(" and u.username like '%").append(params.get("username")).append("%'").toString());
            if(params.get("empname") != null && !"".equals(params.get("empname")))
                sql.append((new StringBuilder()).append(" and (o.orgname like '%").append(params.get("empname")).append("%' or e.namepy like '%").append(params.get("empname")).append("%' )").toString());
            if(params.get("jqEmpName") != null && !"".equals(params.get("jqEmpName")))
            {
                sql.append(" and o.orgname =:orgname ");
                map.put("orgname", params.get("jqEmpName"));
            }
            int inservice = 2;
            if(params.get("inservice") != null && !"".equals(params.get("inservice")))
            {
                sql.append((new StringBuilder()).append(" and o.inservice =").append(params.get("inservice")).toString());
                inservice = NumberTool.safeToInteger(params.get("inservice"), Integer.valueOf(1)).intValue();
            }
            if(params.get("unUser") != null && ((Boolean)params.get("unUser")).booleanValue())
            {
                IOrgFinder _finder = OrgFactory.getOrgFinder();
                String _rootid = _finder.getRoot().getId();
                if(_rootid != null && !_rootid.equals(""))
                    sql.append((new StringBuilder()).append(" and e.userid not in (select userid from sprt_orgobject  where orgid in(select distinct toobjectid from sprt_orgrela \n where fromobjectid in (select r.toobjectid from sprt_orgobject o right join\n (select * from sprt_orgrela\u3000CONNECT BY PRIOR toobjectid = fromobjectid and relationtype='UNDERLINE'\n START WITH fromobjectid=").append(_rootid).append(") r on o.orgid=r.toobjectid where unittype in('UNIT','POSITION')\n").append(" ) and relationtype ='SUPERVISION'))").toString());
            }
            if(params.get("strutureid") != null && !"".equals(params.get("strutureid")))
            {
                List s2 = new ArrayList();
                String _strutureItem[] = params.get("strutureid").toString().split(",");
                String arr$[] = _strutureItem;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String _str = arr$[i$];
                    String _unitid[] = _str.split(":");
                    s2.addAll(findUserIdsByUnitId(_unitid[0], inservice));
                }

                StringBuilder userids = new StringBuilder();
                String userid;
                for(Iterator i$ = s2.iterator(); i$.hasNext(); userids.append(userid))
                {
                    userid = (String)i$.next();
                    if(!"".equals(userids.toString()))
                        userids.append(",");
                }

                if(!"".equals(userids.toString()))
                {
                    String str[] = userids.toString().split(",");
                    QueryUtils.buildIn("e.userid", str, sql, map);
                } else
                {
                    sql.append(" and 1=0");
                }
            }
        }
        IMetaDBQuery _metaDbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(map.size() > 0)
            _metaDbQuery.setParameters(map);
        result = _metaDbQuery.getResult();
        return result;
    }

    public Employee getEmployeeByUserId(String userid)
    {
        UMClient _umclient = UMFactory.getUMClient();
        Map _userprops = new HashMap();
        Employee _employee = new Employee();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        try
        {
            String _username = _umclient.getUserName(userid);
            _userprops = _umclient.getUserProperties(userid);
            _employee.setUserId(userid);
            _employee.setUserName(_username);
            if(!userid.equals(""))
            {
                IEmployee e = _finder.getEmployeeByUserId(userid);
                if(e != null)
                {
                    _employee.setId(e.getId());
                    _employee.setEmpName(e.getName());
                    _employee.setInservice(e.isInservice().booleanValue() ? 1 : 0);
                }
            }
            IOrgUserExtendInfo extendInfo = getOrgUserExtendInfoByUserId(userid);
            String key;
            String value;
            for(Iterator i$ = _userprops.keySet().iterator(); i$.hasNext(); _employee.setOtherProperty(key, value))
            {
                key = (String)i$.next();
                value = StringUtil.safeToString(_userprops.get(key), "");
                value = "null".equals(value) ? "" : value;
            }

            OrgUtils.copyUserProperties(_employee, extendInfo, false);
        }
        catch(NoSuchUserException e1)
        {
            LOGGER.error("findAllEmployees failed", e1);
            return null;
        }
        return _employee;
    }

    public Employee getEmployeeByUserName(String username)
    {
        UMClient _umclient = UMFactory.getUMClient();
        Employee _employee = new Employee();
        String userid = _umclient.getUserByName(username);
        if(userid != null && !userid.equals(""))
            _employee = getEmployeeByUserId(userid);
        return _employee;
    }

    public List getEmployeeByEmpName(String empName)
    {
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        List result = new ArrayList();
        List _userlist = _finder.findEmployeesByName(empName, true);
        Iterator i$ = _userlist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IEmployee _org = (IEmployee)i$.next();
            Employee employee = getEmployeeById(_org.getId());
            if(employee != null)
                result.add(employee);
        } while(true);
        return result;
    }

    public List getEmployeeInserviceByEmpName(String empName)
    {
        List result = new ArrayList();
        Iterator i$ = getEmployeeByEmpName(empName).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public void insertEmployee(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        UMClient _umclient = UMFactory.getUMClient();
        UserSession _userSession = _umclient.getSession();
        if(_userSession != null)
        {
            IEmployee _employee = _updater.addEmployee(employee.getEmpName(), "000", "", Boolean.valueOf(employee.getInservice() == 1));
            employee.setId(_employee.getId());
            employee.addUserOtherProperties();
            Map _param = employee.loadOtherProperties();
            String userid = _userSession.createUser(employee.getUserName(), employee.getPassword(), false, _param, null, null);
            employee.setUserId(userid);
            IOrgUserExtendInfo extendInfo = new OrgUserExtendInfoImpl();
            OrgUtils.copyUserProperties(employee, extendInfo, true);
            saveOrgUserExtendInfo(extendInfo);
            _updater.updateEmployee(_employee, employee.getEmpName(), employee.getUserId(), "", Boolean.valueOf(employee.getInservice() == 1));
            _updater.flush();
        }
    }

    public void updateEmployee(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        UMClient _umclient = UMFactory.getUMClient();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        UserSession _userSession = _umclient.getSession();
        if(_userSession != null)
        {
            String _username = _umclient.getUserName(employee.getUserId());
            if(!_username.equals(employee.getUserName()))
                _userSession.setUserName(employee.getUserId(), employee.getUserName());
            IOrgUserExtendInfo extendInfo = getOrgUserExtendInfoByUserId(employee.getUserId());
            OrgUtils.copyUserProperties(employee, extendInfo, true);
            saveOrgUserExtendInfo(extendInfo);
            employee.addUserOtherProperties();
            Map _param = employee.loadOtherProperties();
            _userSession.setUserProperties(employee.getUserId(), _param);
            IEmployee _employee = null;
            if(employee.getId() != null)
            {
                _employee = (IEmployee)_finder.getOrgObject(employee.getId());
                _updater.updateEmployee(_employee, employee.getEmpName(), employee.getUserId(), "", Boolean.valueOf(employee.getInservice() == 1));
                _updater.flush();
            }
        }
    }

    public void updatePassword(String userid, String password)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        UMClient _umclient = UMFactory.getUMClient();
        UserSession _userSession = _umclient.getSession();
        _userSession.setUserPassword(userid, password);
        Employee employee = getEmployeeByUserId(userid);
        try
        {
            employee.setPasswordUpdateTime(TimeUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            updateEmployee(employee);
            _userSession.unlockUser(employee.getUserId());
            IOrgLogonLog logonLog = OrgServiceFactory.getPasswordService().getLastPswLog(employee.getId());
            if(null != logonLog)
            {
                logonLog.setFailedCount(Integer.valueOf(0));
                logonLog.setLogDate(new Date());
                OrgServiceFactory.getPasswordService().savePswLog(logonLog);
            }
        }
        catch(Exception e)
        {
            LOGGER.error("password update error", e);
        }
    }

    public List findUserIdsByUnitId(String unitid)
    {
        return findUserIdsByUnitId(unitid, 1);
    }

    public List findUserIdsByUnitId(String unitid, int inservicestatus)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(unitid != null && !unitid.equals(""))
        {
            List employeeList = new ArrayList();
            IUnit _unit = (IUnit)_finder.getOrgObject(unitid);
            if(com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeByUnitId(unitid, inservicestatus);
            else
            if(com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeByPosId(unitid, inservicestatus);
            else
            if(com.sinitek.spirit.org.core.IUnit.UnitType.TEAM.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeByTeamId(unitid, inservicestatus);
            Employee employee;
            for(Iterator i$ = employeeList.iterator(); i$.hasNext(); result.add(employee.getUserId()))
                employee = (Employee)i$.next();

        }
        return result;
    }

    public List findUserIdsInserviceByUnitId(String unitid)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(unitid != null && !unitid.equals(""))
        {
            List employeeList = new ArrayList();
            IUnit _unit = (IUnit)_finder.getOrgObject(unitid);
            if(com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeInserviceByUnitId(unitid);
            else
            if(com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeInserviceByPosId(unitid);
            else
            if(com.sinitek.spirit.org.core.IUnit.UnitType.TEAM.toString().equals(_unit.getUnitType()))
                employeeList = findEmployeeInserviceByTeamId(unitid);
            Employee employee;
            for(Iterator i$ = employeeList.iterator(); i$.hasNext(); result.add(employee.getUserId()))
                employee = (Employee)i$.next();

        }
        return result;
    }

    public List findEmpsByUnitId(String unitid)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(unitid != null && !unitid.equals(""))
        {
            IUnit _unit = (IUnit)_finder.getOrgObject(unitid);
            if(_unit != null)
                if(com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString().equals(_unit.getUnitType()))
                    result = findEmployeeByUnitId(unitid);
                else
                if(com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString().equals(_unit.getUnitType()))
                    result = findEmployeeByPosId(unitid);
                else
                if(com.sinitek.spirit.org.core.IUnit.UnitType.TEAM.toString().equals(_unit.getUnitType()))
                    result = findEmployeeByTeamId(unitid);
                else
                if("ROLE".equals(_unit.getUnitType()))
                    result = findEmployeeByRoleId(unitid);
        }
        return result;
    }

    public List findEmpsInserviceByUnitId(String unitid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmpsByUnitId(unitid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public void deleteUserByUserId(String id)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        UMClient _umclient = UMFactory.getUMClient();
        UserSession _userSession = _umclient.getSession();
        Employee _employee = getEmployeeByUserId(id);
        if(_employee.getId() != null && _employee.getId() != "")
        {
            IOrgUpdater _updater = OrgFactory.getOrgUpdater();
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            _updater.deleteObject(_finder.getOrgObject(_employee.getId()), true);
        }
        _userSession.deleteUser(id);
    }

    public Employee getEmployeeById(String orgid)
    {
        Employee _employee = null;
        if(orgid == null || orgid.equals(""))
            return _employee;
        if("0".equals(orgid))
        {
            _employee = new Employee();
            _employee.setId(orgid);
            _employee.setEmpName("\u7CFB\u7EDF");
            return _employee;
        }
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        try
        {
            IOrgObject _object = _finder.getOrgObject(orgid);
            if(_object != null && (_object instanceof IEmployee))
            {
                IEmployee _iEmployee = (IEmployee)_object;
                String userid = _iEmployee.getUserId();
                _employee = getEmployeeByUserId(userid);
            }
        }
        catch(Throwable e)
        {
            LOGGER.warn("getemployeebyid failed.", e);
            return _employee;
        }
        return _employee;
    }

    public Position getPositionById(String orgid)
    {
        Position _position = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(orgid);
        if(_object != null && (_object instanceof IUnit))
        {
            _position = new Position();
            IUnit _unit = (IUnit)_object;
            _position.setOrgid(_unit.getId());
            _position.setName(_unit.getName());
            IOrgObject _businUnit = _finder.findOrgObjectByPath(_unit, "UP(orgtype='POSITION';relationshiptype='SUPBUSIN')");
            if(_businUnit != null)
                _position.setSupBusinId(_businUnit.getId());
            IOrgObject _executeUnit = _finder.findOrgObjectByPath(_unit, "UP(orgtype='POSITION';relationshiptype='SUPEXECUTE')");
            if(_executeUnit != null)
                _position.setSupExecuteId(_executeUnit.getId());
            _position.setDescription(_unit.getDescription());
        }
        return _position;
    }

    public Team getTeamById(String orgid)
    {
        Team _team = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(orgid);
        if(_object != null && (_object instanceof IUnit))
        {
            _team = new Team();
            IUnit _unit = (IUnit)_object;
            _team.setTeamId(_unit.getId());
            _team.setTeamName(_unit.getName());
            _team.setTeamDescription(_unit.getDescription());
            IOrgProperty orgProperty = OrgServiceFactory.getOrgPropertyService().getOrgPropertyByOrgId(orgid);
            _team.setResearchFlag(orgProperty != null && orgProperty.getF1().booleanValue());
        }
        return _team;
    }

    public List findTeamerById(String teamid)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(teamid);
        if(_object != null)
        {
            List _userlist = _finder.findOrgObjectsByPath(_object, "DOWN(orgtype='EMPLOYEE'; level=all;relationshiptype='TEAMER')");
            Iterator i$ = _userlist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IOrgObject _org = (IOrgObject)i$.next();
                Employee employee = getEmployeeById(_org.getId());
                if(employee != null && employee.getInservice() != 0 && !employee.getUserName().startsWith("$"))
                    result.add(employee);
            } while(true);
        }
        return result;
    }

    public List findTeamerInserviceById(String teamid)
    {
        List result = new ArrayList();
        Iterator i$ = findTeamerById(teamid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public Department getDepartmentById(String orgid)
    {
        Department _department = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(orgid);
        if(_object != null && (_object instanceof IUnit))
        {
            _department = new Department();
            IUnit _unit = (IUnit)_object;
            _department.setOrgid(_unit.getId());
            _department.setName(_unit.getName());
            _department.setDescription(_unit.getDescription());
        }
        return _department;
    }

    public void deleteDepartmentById(String orgid)
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        IOrgObject _object = _finder.getOrgObject(orgid);
        List allChildrenList = new ArrayList();
        if(_object != null)
        {
            allChildrenList.add(_object);
            allChildrenList.addAll(_finder.findOrgObjectsByPath(_object, "DOWN(orgtype='UNIT'; level=all)"));
            allChildrenList.addAll(_finder.findOrgObjectsByPath(_object, "DOWN(orgtype='POSITION'; level=all;relationshiptype in ['SUPERVISION','UNDERLINE'])"));
            for(Iterator i$ = allChildrenList.iterator(); i$.hasNext(); _updater.flush())
            {
                IOrgObject _orgObject = (IOrgObject)i$.next();
                _updater.deleteObject(_orgObject, true);
                RightAuthInfoBean _info = new RightAuthInfoBean();
                _info.setAuthOrgObjectpId(_orgObject.getId());
                _rightUpdater.deleteRightAuth(_info);
                _rightUpdater.deleteRightDefine(_orgObject.getId());
            }

            IUnit _unit;
            if(_object instanceof IUnit)
                _unit = (IUnit)_object;
        }
    }

    public List findEmployeeByPosId(String postid)
    {
        return findEmployeeByPosId(postid, 1);
    }

    public List findEmployeeByPosId(String postid, int inservicestatus)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IUnit _unit = (IUnit)_finder.getOrgObject(postid);
        if(_unit != null)
        {
            List _userlist = _finder.findOrgObjectsByPath(_unit, "DOWN(orgtype='EMPLOYEE'; level=all)");
            Iterator i$ = _userlist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IOrgObject _org = (IOrgObject)i$.next();
                Employee employee = getEmployeeById(_org.getId());
                if(employee != null && (inservicestatus == 2 || inservicestatus == employee.getInservice()) && !employee.getUserName().startsWith("$"))
                    result.add(employee);
            } while(true);
        }
        return result;
    }

    public List findEmployeeInserviceByPosId(String postid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeeByPosId(postid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findEmployeeByUnitId(String unitid)
    {
        return findEmployeeByUnitId(unitid, 1);
    }

    public List findEmployeeByUnitId(String unitid, int inservicestatus)
    {
        List result = new ArrayList();
        StringBuilder orgids = new StringBuilder();
        List orgIdList = findEmpIdsByUnitId(unitid, inservicestatus);
        if(orgIdList != null && orgIdList.size() > 0)
        {
            for(Iterator i$ = orgIdList.iterator(); i$.hasNext();)
            {
                String id = (String)i$.next();
                if(StringUtils.isNotBlank(id))
                    orgids.append(",").append(id);
                else
                    orgids.append(id);
            }

            Map param = new HashMap();
            param.put("orgids", orgids.toString());
            result = findAllEmployees(param);
        }
        return result;
    }

    private List findEmpIdsByUnitId(String unitid)
    {
        return findEmpIdsByUnitId(unitid, 1);
    }

    private List findEmpIdsByUnitId(String unitid, int inservicestatus)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select orgid from sprt_orgobject t where t.objecttype=1");
        if(inservicestatus != 2)
            sql.append((new StringBuilder()).append("and t.inservice=").append(inservicestatus).toString());
        sql.append("and t.userid not in( \n").append(" select userid from um_userinfo where username like '$%' ) and t.orgid in(select distinct toobjectid from sprt_orgrela \n").append(" where fromobjectid in (select r.toobjectid from sprt_orgobject o right join (select * from sprt_orgrela \n").append(" CONNECT BY PRIOR toobjectid = fromobjectid and relationtype='UNDERLINE' START WITH fromobjectid=:orgid) r on o.orgid=r.toobjectid \n").append(" where unittype in('UNIT','POSITION'))and relationtype ='SUPERVISION') ");
        Map param = new HashMap();
        param.put("orgid", unitid);
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        List list = query.getResult();
        List result = new ArrayList();
        Iterator i$ = list.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map map = (Map)i$.next();
            String orgid = StringUtil.safeToString(map.get("orgid"), "");
            if(StringUtils.isNotBlank(orgid))
                result.add(orgid);
        } while(true);
        return result;
    }

    public List findEmployeeInserviceByUnitId(String unitid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeeByUnitId(unitid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findPositionsByUnitId(String unitid)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IUnit _unit = (IUnit)_finder.getOrgObject(unitid);
        if(_unit != null)
        {
            List _orglist = _finder.findOrgObjectsByPath(_unit, "DOWN(orgtype='POSITION'; level=all;relationshiptype in ['SUPERVISION','UNDERLINE'])");
            Position position;
            for(Iterator i$ = _orglist.iterator(); i$.hasNext(); result.add(position))
            {
                IOrgObject _org = (IOrgObject)i$.next();
                position = getPositionById(_org.getId());
            }

        }
        return result;
    }

    public List findUnitByParentId(String unitid)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IUnit _unit = (IUnit)_finder.getOrgObject(unitid);
        if(_unit != null)
        {
            List _orglist = _finder.findOrgObjectsByPath(_unit, "DOWN(orgtype='UNIT')");
            Department department;
            for(Iterator i$ = _orglist.iterator(); i$.hasNext(); result.add(department))
            {
                IOrgObject _org = (IOrgObject)i$.next();
                department = getDepartmentById(_org.getId());
            }

        }
        return result;
    }

    public OrgObject getOrgObjectById(String id)
    {
        OrgObject result = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(StringUtils.isNotBlank(id))
            try
            {
                IOrgObject _object = _finder.getOrgObject(id);
                if(_object != null)
                {
                    int type = 0;
                    boolean isEmployee = _object instanceof IEmployee;
                    if(!isEmployee)
                    {
                        IUnit _unit = (IUnit)_object;
                        type = OrgType.getOrgTypeName(_unit.getUnitType()).getEnumItemValue();
                    } else
                    {
                        type = OrgType.EMPLOYEE.getEnumItemValue();
                    }
                    result = new OrgObject(_object.getId(), type);
                    result.setOrgName(_object.getName());
                }
            }
            catch(Exception ex)
            {
                LOGGER.warn((new StringBuilder()).append("getOrgObjectById failed.[orgid:").append(id).append("]").toString(), ex);
                return result;
            }
        return result;
    }

    public OrgInfo findOrgInfoByEmpId(String id)
    {
        OrgInfo orgInfo = null;
        Map result = findlistById(id);
        if(!result.keySet().isEmpty())
        {
            orgInfo = new OrgInfo();
            orgInfo.setPositionname((String)result.get("positionname"));
            orgInfo.setRolename((String)result.get("rolename"));
            orgInfo.setTeamname((String)result.get("teamname"));
            orgInfo.setUnitname((String)result.get("unitname"));
        }
        return orgInfo;
    }

    private Map findlistById(String id)
    {
        Map _result = new HashMap();
        StringBuilder positionname = new StringBuilder();
        StringBuilder unitname = new StringBuilder();
        StringBuilder rolename = new StringBuilder();
        StringBuilder teamname = new StringBuilder();
        if(StringUtils.isNotBlank(id))
        {
            Map params = new HashMap();
            String sql = "select *\u3000from sprt_orgobject where orgid in (select distinct fromobjectid from sprt_orgrela where toobjectid=:id and (relationtype='SUPERVISION' or relationtype='UNDERLINE' or relationtype='SUPERROLE' or relationtype='SUPERTEAM')) ";
            IMetaDBQuery _metaDbQuery = getMetaDBContext().createSqlQuery(sql);
            if(!"".equals(id))
            {
                params.put("id", id);
                _metaDbQuery.setParameters(params);
            }
            List _list = _metaDbQuery.getResult();
            Iterator i$ = _list.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _org = (Map)i$.next();
                String _orgtype = _org.get("unittype").toString();
                String _orgname = _org.get("orgname").toString();
                if(_orgtype.equals("POSITION"))
                {
                    if("".equals(positionname.toString()))
                    {
                        positionname.append(_orgname);
                    } else
                    {
                        positionname.append(",");
                        positionname.append(_orgname);
                    }
                    Map postUnit = findlistById(_org.get("orgid").toString());
                    if(postUnit.size() > 0)
                        if("".equals(unitname.toString()))
                        {
                            unitname.append((String)postUnit.get("unitname"));
                        } else
                        {
                            unitname.append(",");
                            unitname.append((String)postUnit.get("unitname"));
                        }
                } else
                if(_orgtype.equals("UNIT"))
                {
                    if("".equals(unitname.toString()))
                    {
                        unitname.append(_orgname);
                    } else
                    {
                        unitname.append(",");
                        unitname.append(_orgname);
                    }
                } else
                if(_orgtype.equals("ROLE"))
                {
                    if("".equals(rolename.toString()))
                    {
                        rolename.append(_orgname);
                    } else
                    {
                        rolename.append(",");
                        rolename.append(_orgname);
                    }
                } else
                if(_orgtype.equals("TEAM"))
                    if("".equals(teamname.toString()))
                    {
                        teamname.append(_orgname);
                    } else
                    {
                        teamname.append(",");
                        teamname.append(_orgname);
                    }
            } while(true);
        }
        _result.put("positionname", positionname.toString());
        _result.put("unitname", unitname.toString());
        _result.put("rolename", rolename.toString());
        _result.put("teamname", teamname.toString());
        return _result;
    }

    public List findUnallocatedEmployee()
    {
        List _emplist = findAllEmployees();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        String _rootid = _finder.getRoot().getId();
        if(_rootid != null && !_rootid.equals(""))
        {
            List _orglist = findEmployeeByUnitId(_rootid);
label0:
            for(int i = 0; i < _orglist.size(); i++)
            {
                Employee employee = (Employee)_orglist.get(i);
                int j = 0;
                do
                {
                    if(j >= _emplist.size())
                        continue label0;
                    if(((Employee)_emplist.get(j)).getId().equals(employee.getId()))
                    {
                        _emplist.remove(_emplist.get(j));
                        continue label0;
                    }
                    j++;
                } while(true);
            }

        }
        return _emplist;
    }

    public List findUnallocatedEmployeeInservice()
    {
        List result = new ArrayList();
        Iterator i$ = findUnallocatedEmployee().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findPositionsByEmpId(String id)
    {
        List _poslist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _empOrg = _finder.getOrgObject(id);
        if(_empOrg != null)
        {
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='POSITION';relationshiptype in ['SUPERVISION','UNDERLINE'])");
            Position _position;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _poslist.add(_position))
            {
                IOrgObject _obj = (IOrgObject)i$.next();
                _position = new Position();
                _position.setOrgid(_obj.getId());
                _position.setName(_obj.getName());
                _position.setDescription(_obj.getDescription());
            }

        }
        return _poslist;
    }

    public List findEmployeesByOrgTypeOrgId(int orgtype, String orgid)
    {
        List _emplist = new ArrayList();
        if(orgtype == 0 || orgtype == OrgType.EMPLOYEE.getEnumItemValue())
        {
            Employee employee = getEmployeeById(orgid);
            if(employee != null)
                _emplist.add(employee);
        } else
        if(orgtype == OrgType.POSITION.getEnumItemValue())
            _emplist = findEmployeeByPosId(orgid);
        else
        if(orgtype == OrgType.DEPT.getEnumItemValue())
            _emplist = findEmployeeByUnitId(orgid);
        else
        if(orgtype == OrgType.TEAM.getEnumItemValue())
            _emplist = findEmployeeByTeamId(orgid);
        else
        if(orgtype == OrgType.ROLE.getEnumItemValue())
            _emplist = findEmployeeByRoleId(orgid);
        return _emplist;
    }

    public List findEmployeesInserviceByOrgTypeOrgId(int orgtype, String orgid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeesByOrgTypeOrgId(orgtype, orgid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findEmployeesByOrgId(String orgid)
    {
        List _emplist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(orgid);
        boolean isEmployee = _object instanceof IEmployee;
        if(!isEmployee)
        {
            boolean isUnit = _object instanceof IUnit;
            if(isUnit)
            {
                IUnit _unit = (IUnit)_object;
                if(_unit.getUnitType().equals(OrgType.DEPT.getEnumItemInfo()))
                    _emplist.addAll(findEmployeeByUnitId(orgid));
                else
                if(_unit.getUnitType().equals(OrgType.POSITION.getEnumItemInfo()))
                    _emplist.addAll(findEmployeeByPosId(orgid));
                else
                if(_unit.getUnitType().equals(OrgType.TEAM.getEnumItemInfo()))
                    _emplist.addAll(findEmployeeByTeamId(orgid));
            }
        } else
        {
            Employee employee = getEmployeeById(orgid);
            if(employee != null && employee.getInservice() != 0)
                _emplist.add(employee);
        }
        return _emplist;
    }

    public List findEmployeesInserviceByOrgId(String orgid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeesByOrgId(orgid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findEmployeeByTeamId(String teamid)
    {
        return findEmployeeByTeamId(teamid, 1);
    }

    public List findEmployeeByTeamId(String teamid, int inservicestatus)
    {
        List _emplist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _teamOrg = _finder.getOrgObject(teamid);
        if(_teamOrg != null)
        {
            List _userlist = _finder.findOrgObjectsByPath(_teamOrg, "DOWN(orgtype='EMPLOYEE';relationshiptype='SUPERTEAM';level=all)");
            Iterator i$ = _userlist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IOrgObject _org = (IOrgObject)i$.next();
                Employee employee = getEmployeeById(_org.getId());
                if(employee != null && (inservicestatus == 2 || inservicestatus == employee.getInservice()) && !employee.getUserName().startsWith("$"))
                    _emplist.add(employee);
            } while(true);
        }
        return _emplist;
    }

    public List findEmployeeInserviceByTeamId(String teamid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeeByTeamId(teamid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findUnitsByEmpId(String id)
    {
        List _unitlist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(id != null && !id.equals(""))
        {
            List postlist = findPositionsByEmpId(id);
            Iterator i$ = postlist.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Position _position = (Position)i$.next();
                IOrgObject _empOrg = _finder.getOrgObject(_position.getOrgid());
                if(_empOrg != null)
                {
                    List _objlist = _finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='UNIT')");
                    Iterator i$ = _objlist.iterator();
                    while(i$.hasNext()) 
                    {
                        IOrgObject _obj = (IOrgObject)i$.next();
                        Department unit = new Department();
                        unit.setOrgid(_obj.getId());
                        unit.setName(_obj.getName());
                        unit.setDescription(_obj.getDescription());
                        _unitlist.add(unit);
                    }
                }
            } while(true);
        }
        return _unitlist;
    }

    public List findTeamsByEmpId(String id)
    {
        List _teamlist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgPropertyService orgPropertyService = OrgServiceFactory.getOrgPropertyService();
        if(id != null && !id.equals(""))
        {
            IOrgObject _empOrg = _finder.getOrgObject(id);
            if(_empOrg != null && !"".equals(_empOrg.getId()))
            {
                List _objlist = _finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='TEAM')");
                Team team;
                for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _teamlist.add(team))
                {
                    IOrgObject _obj = (IOrgObject)i$.next();
                    team = new Team();
                    team.setTeamId(_obj.getId());
                    team.setTeamName(_obj.getName());
                    team.setTeamDescription(_obj.getDescription());
                    IOrgProperty orgProperty = orgPropertyService.getOrgPropertyByOrgId(_obj.getId());
                    team.setResearchFlag(orgProperty != null && orgProperty.getF1().booleanValue());
                }

            }
        }
        return _teamlist;
    }

    public Department getParentDepartmentById(String id)
    {
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        Department department = null;
        if(id != null && !"".equals(id))
        {
            IOrgObject _org = _finder.getOrgObject(id);
            if(_org != null)
            {
                IOrgObject _parentOrg = _finder.findOrgObjectByPath(_org, "UP(orgtype='UNIT')");
                if(_parentOrg != null)
                {
                    department = new Department();
                    department.setOrgid(_parentOrg.getId());
                    department.setName(_parentOrg.getName());
                    department.setDescription(_parentOrg.getDescription());
                }
            }
        }
        return department;
    }

    public Department getDepartmentByPositionId(String positionId)
    {
        Department department = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(positionId != null && !positionId.equals(""))
        {
            IOrgObject _posOrg = _finder.getOrgObject(positionId);
            if(_posOrg != null)
            {
                IOrgObject _obj = _finder.findOrgObjectByPath(_posOrg, "UP(orgtype='UNIT')");
                if(_obj != null)
                {
                    department = new Department();
                    department.setOrgid(_obj.getId());
                    department.setName(_obj.getName());
                    department.setDescription(_obj.getDescription());
                }
            }
        }
        return department;
    }

    public List findOnlineUsers()
    {
        UMClient _umclient = UMFactory.getUMClient();
        Set _userids = _umclient.getOnlineUsers();
        List _empList = new ArrayList();
        if(null != _userids && _userids.size() > 0)
        {
            Iterator i$ = _userids.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                String _userid = (String)i$.next();
                Employee employee = getEmployeeByUserId(_userid);
                if(!employee.getUserName().startsWith("$"))
                    _empList.add(employee);
            } while(true);
        }
        return _empList;
    }

    public List findAllRole()
    {
        List _roleList = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _empOrg = _finder.getRoot();
        if(_empOrg != null && !"".equals(_empOrg.getId()))
        {
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, "DOWN(orgtype='ROLE')");
            Role role;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _roleList.add(role))
            {
                IOrgObject _obj = (IOrgObject)i$.next();
                role = new Role();
                role.setObjid(_obj.getId());
                role.setName(_obj.getName());
                role.setDescription(_obj.getDescription());
            }

        }
        return _roleList;
    }

    public Role getRoleById(String orgid)
    {
        Role role = null;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(orgid != null && !"".equals(orgid))
        {
            IOrgObject _object = _finder.getOrgObject(orgid);
            if(_object != null && (_object instanceof IUnit))
            {
                role = new Role();
                IUnit _unit = (IUnit)_object;
                role.setObjid(_unit.getId());
                role.setName(_unit.getName());
                role.setDescription(_unit.getDescription());
            }
        }
        return role;
    }

    public List findEmployeeByRoleId(String roleid)
    {
        List _emplist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(roleid != null && !"".equals(roleid))
        {
            IOrgObject _teamOrg = _finder.getOrgObject(String.valueOf(roleid));
            if(_teamOrg != null)
            {
                List _userlist = _finder.findOrgObjectsByPath(_teamOrg, "DOWN(orgtype='EMPLOYEE'; level=all)");
                Iterator i$ = _userlist.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    IOrgObject _org = (IOrgObject)i$.next();
                    Employee employee = getEmployeeById(_org.getId());
                    if(employee != null && employee.getInservice() != 0 && !employee.getUserName().startsWith("$"))
                        _emplist.add(employee);
                } while(true);
            }
        }
        return _emplist;
    }

    public List findEmployeeInserviceByRoleId(String roleid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmployeeByRoleId(roleid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findRoleByEmpId(String empid)
    {
        List roleList = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _empOrg = _finder.getOrgObject(empid);
        if(_empOrg != null)
        {
            List _rolelist = _finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='ROLE')");
            Role role;
            for(Iterator i$ = _rolelist.iterator(); i$.hasNext(); roleList.add(role))
            {
                IOrgObject object = (IOrgObject)i$.next();
                role = new Role();
                role.setObjid(object.getId());
                role.setName(object.getName());
                role.setDescription(object.getDescription());
            }

        }
        return roleList;
    }

    public List findAllQualifyInfoByEmpId(String empid)
    {
        String hql = " OrgId =:OrgId";
        Map map = new HashMap();
        map.put("OrgId", empid);
        IMetaDBQuery _query = getMetaDBContext().createQuery("QUALIFYINFO", hql);
        _query.setParameters(map);
        _query.setOrderBy("endtime");
        return _query.getResult();
    }

    public IQualifyInfo getQualifyInfoBySrcid(String srcid, String datasrc)
    {
        if(StringUtil.safeToString(srcid, "").isEmpty() || StringUtil.safeToString(datasrc, "").isEmpty())
            return null;
        StringBuffer hql = new StringBuffer(" 1=1 ");
        Map map = new HashMap();
        if(!StringUtil.safeToString(srcid, "").isEmpty())
        {
            hql.append(" and origid =:srcid");
            map.put("srcid", srcid);
        }
        if(!StringUtil.safeToString(datasrc, "").isEmpty())
        {
            hql.append(" and datasrc =:datasrc");
            map.put("datasrc", datasrc);
        }
        IMetaDBQuery _query = getMetaDBContext().createQuery("QUALIFYINFO", hql.toString());
        if(map.size() > 0)
            _query.setParameters(map);
        _query.setOrderBy("endtime");
        List qualifyInfos = _query.getResult();
        if(qualifyInfos.size() > 0)
            return (IQualifyInfo)qualifyInfos.get(0);
        else
            return null;
    }

    public void deleteQualifyInfo(int qualifyId)
    {
        IQualifyInfo _functionInfo = (IQualifyInfo)getMetaDBContext().get("QUALIFYINFO", qualifyId);
        if(_functionInfo != null)
            _functionInfo.delete();
    }

    public void deleteQualifyByOrgId(String orgId)
    {
        List infoList = findAllQualifyInfoByEmpId(orgId);
        IQualifyInfo info;
        for(Iterator i$ = infoList.iterator(); i$.hasNext(); info.delete())
            info = (IQualifyInfo)i$.next();

    }

    public void saveQualify(IQualifyInfo info)
    {
        info.save();
    }

    public List findAllOrgByEmpId(String empid)
    {
        List _result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _empOrg = _finder.getOrgObject(empid);
        if(_empOrg != null)
        {
            List _objlist = new ArrayList();
            _objlist.add(_empOrg);
            _objlist.addAll(_finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='ROLE')"));
            _objlist.addAll(_finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='TEAM')"));
            List _parentPost = _finder.findOrgObjectsByPath(_empOrg, "UP(orgtype='POSITION';relationshiptype in ['SUPERVISION','UNDERLINE'])");
            _objlist.addAll(_parentPost);
            OrgObject _object;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _result.add(_object))
            {
                IOrgObject _obj = (IOrgObject)i$.next();
                _object = new OrgObject();
                _object.setOrgId(_obj.getId());
                _object.setOrgName(_obj.getName());
                if(_obj instanceof IUnit)
                {
                    String _type = ((IUnit)_obj).getUnitType();
                    if("POSITION".equals(_type))
                    {
                        _object.setOrgType(2);
                        continue;
                    }
                    if("TEAM".equals(_type))
                        _object.setOrgType(4);
                } else
                {
                    _object.setOrgType(8);
                }
            }

        }
        return _result;
    }

    public List findAllPosParentByEmpId(String empid, String positionType)
    {
        List _result = new ArrayList();
        if(!"".equals(empid))
        {
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            IOrgObject _empOrg = _finder.getOrgObject(empid);
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, (new StringBuilder()).append("UP(orgtype='POSITION'):UP(orgtype='POSITION';relationshiptype='").append(positionType).append("')").toString());
            IOrgObject orgObject;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _result.addAll(findEmployeeByPosId(orgObject.getId())))
                orgObject = (IOrgObject)i$.next();

        }
        return _result;
    }

    public List findAllPosParentInserviceByEmpId(String empid, String positionType)
    {
        List result = new ArrayList();
        Iterator i$ = findAllPosParentByEmpId(empid, positionType).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findPosParentByEmpId(String empid, String positionType)
    {
        List _result = new ArrayList();
        if(!"".equals(empid))
        {
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            IOrgObject _empOrg = _finder.getOrgObject(empid);
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, (new StringBuilder()).append("UP(orgtype='POSITION';relationshiptype='").append(positionType).append("')").toString());
            OrgObject object;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _result.add(object))
            {
                IOrgObject orgObject = (IOrgObject)i$.next();
                object = new OrgObject();
                object.setOrgName(orgObject.getName());
                object.setOrgId(orgObject.getId());
                object.setOrgType(OrgType.POSITION.getEnumItemValue());
            }

        }
        return _result;
    }

    public List findAllPosChildrenByEmpId(String empid, String positionType)
    {
        List _result = new ArrayList();
        if(!"".equals(empid))
        {
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            IOrgObject _empOrg = _finder.getOrgObject(empid);
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, (new StringBuilder()).append("UP(orgtype='POSITION'):DOWN(orgtype='POSITION';relationshiptype='").append(positionType).append("')").toString());
            IOrgObject orgObject;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); _result.addAll(findEmployeeByPosId(orgObject.getId())))
                orgObject = (IOrgObject)i$.next();

        }
        return _result;
    }

    public List findAllPosChildrenByEmpIdInservice(String empid, String positionType)
    {
        List result = new ArrayList();
        Iterator i$ = findAllPosChildrenByEmpId(empid, positionType).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public boolean checkPosParentByEmpId(String empid, String positionType)
    {
        boolean _result = false;
        if(!"".equals(empid))
        {
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            IOrgObject _empOrg = _finder.getOrgObject(empid);
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, (new StringBuilder()).append("UP(orgtype='POSITION'):UP(orgtype='POSITION';relationshiptype='").append(positionType).append("')").toString());
            if(_objlist.size() > 0)
                _result = true;
        }
        return _result;
    }

    public Department addDepartment(Department department, IUnit _parentUnit)
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        if(department != null && _parentUnit != null)
        {
            IUnit _orgUnit = _updater.addUnit(department.getName(), com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString(), department.getDescription());
            _updater.addRelationShip(_parentUnit, _orgUnit, com.sinitek.spirit.org.core.IRelationship.RelationshipType.UNDERLINE.toString());
            _updater.flush();
            department.setOrgid(_orgUnit.getId());
        }
        return department;
    }

    public Department updateDepartment(Department department, IUnit _parentUnit)
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(department != null && _parentUnit != null)
        {
            IUnit _unit = (IUnit)_finder.getOrgObject(department.getOrgid());
            IUnit _newUnit = _updater.updateUnit(_unit, department.getName(), com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString(), department.getDescription());
            _updater.flush();
        }
        return department;
    }

    public List findParentOrgById(String id)
    {
        List _result = new ArrayList();
        List _orglist = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        if(id != null && !"".equals(id))
        {
            IOrgObject _object = _finder.getOrgObject(id);
            if(_object != null)
            {
                _orglist.add(_object);
                boolean isEmployee = _object instanceof IEmployee;
                if(!isEmployee)
                {
                    boolean isUnit = _object instanceof IUnit;
                    if(isUnit)
                    {
                        IUnit _unit = (IUnit)_object;
                        if(_unit.getUnitType().equals(OrgType.DEPT.getEnumItemInfo()) || _unit.getUnitType().equals(OrgType.POSITION.getEnumItemInfo()))
                            _orglist.addAll(_finder.findOrgObjectsByPath(_object, "up(orgtype='UNIT';level=all)"));
                    }
                } else
                {
                    _orglist.addAll(_finder.findOrgObjectsByPath(_object, "up(orgtype in ['TEAM','ROLE','POSITION'])"));
                    _orglist.addAll(_finder.findOrgObjectsByPath(_object, "UP():up(orgtype='UNIT';level=all)"));
                }
                OrgObject orgObject;
                for(Iterator i$ = _orglist.iterator(); i$.hasNext(); _result.add(orgObject))
                {
                    IOrgObject iOrgObject = (IOrgObject)i$.next();
                    orgObject = new OrgObject();
                    orgObject.setOrgId(iOrgObject.getId());
                    orgObject.setOrgName(iOrgObject.getName());
                    int orgtype = 8;
                    if(iOrgObject instanceof IUnit)
                    {
                        IUnit _unit = (IUnit)iOrgObject;
                        orgtype = OrgType.getOrgTypeName(_unit.getUnitType()).getEnumItemValue();
                    }
                    orgObject.setOrgType(orgtype);
                }

            }
        }
        return _result;
    }

    public String getPwdByEmpUserId(String userid)
    {
        String pwd = "";
        if(!"".equals(userid))
        {
            String sql = "select password from um_userinfo where userid=:userid";
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql);
            iMetaDBQuery.setParameter("userid", userid);
            List result = iMetaDBQuery.getResult();
            if(result.size() > 0)
            {
                Map map = (Map)result.get(0);
                pwd = (String)map.get("password");
            }
        }
        return pwd;
    }

    public List getTeamerByScode(String industrycode)
    {
        List result = new ArrayList();
        List relaList = findIndustryRelaByIndustryCode(industrycode);
        IOrgTeamIndustryRela rela;
        for(Iterator i$ = relaList.iterator(); i$.hasNext(); result.addAll(findTeamerById(rela.getTeamId())))
            rela = (IOrgTeamIndustryRela)i$.next();

        return result;
    }

    public List getTeamerInserviceByScode(String industrycode)
    {
        List result = new ArrayList();
        Iterator i$ = getTeamerByScode(industrycode).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findIndustryRelaByTeamId(String teamid)
    {
        List result = new ArrayList();
        if(teamid != null && !"".equals(teamid))
        {
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGTEAMINDUSTRYRELA", " teamid=:teamid");
            iMetaDBQuery.setParameter("teamid", teamid);
            result.addAll(iMetaDBQuery.getResult());
        }
        return result;
    }

    public List findIndustryRelaByIndustryCode(String industryCode)
    {
        List result = new ArrayList();
        if(industryCode != null && !"".equals(industryCode))
        {
            StringBuilder sql = new StringBuilder(" 1=1 ");
            Map map = new HashMap();
            sql.append(" and :industrycode like industryCode || '%'");
            map.put("industrycode", industryCode);
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGTEAMINDUSTRYRELA", sql.toString());
            iMetaDBQuery.setParameters(map);
            result.addAll(iMetaDBQuery.getResult());
        }
        return result;
    }

    public void saveIndustryRela(IOrgTeamIndustryRela industryRela)
    {
        industryRela.save();
    }

    public void delIndustryRelaByTeamId(String teamid)
    {
        List relaList = findIndustryRelaByTeamId(teamid);
        IOrgTeamIndustryRela rela;
        for(Iterator i$ = relaList.iterator(); i$.hasNext(); rela.delete())
            rela = (IOrgTeamIndustryRela)i$.next();

    }

    public List findEmpsByTeamer(String orgid)
    {
        List result = new ArrayList();
        StringBuilder sql = new StringBuilder();
        if(orgid != null && !"".equals(orgid))
        {
            sql.append("select toobjectid from sprt_orgrela where relationtype='SUPERTEAM' and fromobjectid in(select fromobjectid from sprt_orgrela where relationtype='TEAMER'");
            sql.append(" and toobjectid=:toobjectid");
            sql.append(") group by toobjectid");
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
            iMetaDBQuery.setParameter("toobjectid", orgid);
            List list = iMetaDBQuery.getResult();
            Iterator i$ = list.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _org = (Map)i$.next();
                Employee employee = getEmployeeById(String.valueOf(_org.get("toobjectid")));
                if(employee != null && employee.getInservice() != 0 && !employee.getUserName().startsWith("$"))
                    result.add(employee);
            } while(true);
        }
        return result;
    }

    public List findEmpsInserviceByTeamer(String orgid)
    {
        List result = new ArrayList();
        Iterator i$ = findEmpsByTeamer(orgid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public boolean checkEmpAndTeamer(String orgid, String teamerid)
    {
label0:
        {
            if(teamerid == null || "".equals(teamerid) || orgid == null || "".equals(orgid))
                break label0;
            List result = findEmpsByTeamer(teamerid);
            Iterator i$ = result.iterator();
            Employee employee;
            do
            {
                if(!i$.hasNext())
                    break label0;
                employee = (Employee)i$.next();
            } while(!employee.getId().equals(orgid));
            return true;
        }
        return false;
    }

    public boolean checkEmpAndTeamer(String orgid, String teamerid, String teamid)
    {
label0:
        {
            List teamerList;
            boolean isok;
            boolean teamerok;
label1:
            {
                if(teamid == null || "".equals(teamid))
                    break label0;
                List employeeList = findEmployeeByTeamId(teamid);
                teamerList = findTeamerById(teamid);
                isok = false;
                teamerok = false;
                if(orgid == null || "".equals(orgid))
                    break label1;
                Iterator i$ = employeeList.iterator();
                Employee employee;
                do
                {
                    if(!i$.hasNext())
                        break label1;
                    employee = (Employee)i$.next();
                } while(!orgid.equals(employee.getId()));
                isok = true;
            }
label2:
            {
                if(teamerid == null || "".equals(teamerid))
                    break label2;
                Iterator i$ = teamerList.iterator();
                Employee employee;
                do
                {
                    if(!i$.hasNext())
                        break label2;
                    employee = (Employee)i$.next();
                } while(!teamerid.equals(employee.getId()));
                teamerok = true;
            }
            return isok && teamerok;
        }
        return checkEmpAndTeamer(orgid, teamerid);
    }

    public List findResearchEmpsByOrgId(String orgid)
    {
        List employeeList = new ArrayList();
        if(orgid != null && !"".equals(orgid))
        {
            List teamList = findTeamsByEmpId(orgid);
            Iterator i$ = teamList.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Team team = (Team)i$.next();
                if(team.isResearchFlag())
                    employeeList.addAll(findEmployeeByTeamId(String.valueOf(team.getTeamId())));
            } while(true);
        }
        return employeeList;
    }

    public List findResearchEmpsInserviceByOrgId(String orgid)
    {
        List result = new ArrayList();
        Iterator i$ = findResearchEmpsByOrgId(orgid).iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _org = (Employee)i$.next();
            if(_org.getInservice() == 1)
                result.add(_org);
        } while(true);
        return result;
    }

    public List findOrgObjectsByOrgIds(List orgIds)
    {
        List list = new ArrayList();
        if(orgIds.size() > 0)
        {
            StringBuilder sql = new StringBuilder("select * from sprt_orgobject where 1=1 ");
            Map param = new HashMap();
            if(orgIds != null && orgIds.size() > 0)
                QueryUtils.buildIn("orgid", orgIds.toArray(), sql, param);
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
            iMetaDBQuery.setParameters(param);
            List result = iMetaDBQuery.getResult();
            OrgObject orgObject;
            for(Iterator i$ = result.iterator(); i$.hasNext(); list.add(orgObject))
            {
                Map map = (Map)i$.next();
                orgObject = new OrgObject();
                orgObject.setOrgId(StringUtil.safeToString(map.get("orgid"), ""));
                orgObject.setOrgName(StringUtil.safeToString(map.get("orgname"), ""));
                int orgtype = 8;
                String unittype = StringUtil.safeToString(map.get("unittype"), "");
                if(unittype != null && !"".equals(unittype))
                    orgtype = OrgType.getOrgTypeName(unittype).getEnumItemValue();
                orgObject.setOrgType(orgtype);
            }

        }
        return list;
    }

    public List getOrgParentIdByOrgId(String orgId)
    {
        String sql = "select ore.fromobjectid from SPRT_ORGRELA ore where ore.toobjectid = :toobjectid and relationtype not in ('SUPEXECUTE','SUPBUSIN')";
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameter("toobjectid", orgId);
        return query.getResult();
    }

    public List findAllOrgParentIdByOrgId(String orgId)
    {
        List result = new ArrayList();
        String sql = "select ore.fromobjectid from SPRT_ORGRELA ore where ore.toobjectid = :toobjectid and relationtype not in ('SUPEXECUTE','SUPBUSIN')";
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameter("toobjectid", orgId);
        int lev = 0;
        for(List list = query.getResult(); list.size() > 0;)
        {
            Map m;
            for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(m))
            {
                m = (Map)i$.next();
                m.put("lev", Integer.valueOf(lev));
            }

            Set orgids = new HashSet();
            Map m;
            for(Iterator i$ = list.iterator(); i$.hasNext(); orgids.add((String)m.get("fromobjectid")))
                m = (Map)i$.next();

            sql = (new StringBuilder()).append("select ore.fromobjectid from SPRT_ORGRELA ore where ore.toobjectid in ('").append(org.apache.commons.lang.StringUtils.join(orgids, "','")).append("') and relationtype not in ('SUPEXECUTE','SUPBUSIN')").toString();
            query = getMetaDBContext().createSqlQuery(sql);
            list = query.getResult();
            lev++;
        }

        return result;
    }

    public List getRelationtypeByOrgId(String orgId)
    {
        String sql = "select ore.relationtype from SPRT_ORGRELA ore where ore.toobjectid = :toobjectid";
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameter("toobjectid", orgId);
        LOGGER.debug(sql);
        return query.getResult();
    }

    public List findTeamsByParent(String partentTeamId)
    {
        List teams = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject orgObject = _finder.getOrgObject(partentTeamId);
        List orgObjects = _finder.findOrgObjectsByPath(orgObject, "DOWN(orgtype='TEAM')");
        Iterator i$ = orgObjects.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IOrgObject _object = (IOrgObject)i$.next();
            Team _team = new Team();
            if(_object instanceof IUnit)
            {
                IUnit _unit = (IUnit)_object;
                if(_unit != null)
                {
                    _team.setTeamId(_unit.getId());
                    _team.setTeamName(_unit.getName());
                    _team.setTeamDescription(_unit.getDescription());
                    IOrgProperty orgProperty = OrgServiceFactory.getOrgPropertyService().getOrgPropertyByOrgId(_object.getId());
                    _team.setResearchFlag(orgProperty != null && orgProperty.getF1().booleanValue());
                    teams.add(_team);
                }
            }
        } while(true);
        return teams;
    }

    public Map getUserinfoByUsernameWithJDBC(String username)
    {
        String sql = (new StringBuilder()).append("select * from um_userinfo where username = '").append(username).append("'").toString();
        List userinfos = getMetaDBContext().createSqlQuery(sql).getResult();
        if(userinfos.size() > 0)
            return (Map)userinfos.get(0);
        else
            return null;
    }

    public Map getUserinfoByJDBC(String userid)
    {
        String sql = (new StringBuilder()).append("select * from um_userinfo where userid = '").append(userid).append("'").toString();
        List userinfos = getMetaDBContext().createSqlQuery(sql).getResult();
        if(userinfos.size() > 0)
            return (Map)userinfos.get(0);
        else
            return null;
    }

    public Map getOrgObjectByJDBC(String orgid)
    {
        String sql = (new StringBuilder()).append("select * from sprt_orgobject where orgid = '").append(orgid).append("'").toString();
        List userinfos = getMetaDBContext().createSqlQuery(sql).getResult();
        if(userinfos.size() > 0)
            return (Map)userinfos.get(0);
        else
            return null;
    }

    public Employee getEmployeeByOrgidWithoutSession(String orgid)
    {
        Map orgmap;
        orgmap = getOrgObjectByJDBC(orgid);
        if(null == orgmap || orgmap.size() <= 0)
            return null;
        try
        {
            UserDatabase userDatabase = UserDatabaseFactory.getUserDatabase();
            String userid = StringUtil.safeToString(orgmap.get("userid"), "");
            Map username = getUserinfoByJDBC(userid);
            Map _userprops = userDatabase.getUserProperties(userid, userDatabase.getUserPropertyNames(userid));
            Employee _employee = new Employee();
            _employee.setId(orgid);
            _employee.setUserId(userid);
            _employee.setUserName(StringUtil.safeToString(orgmap.get("orgname"), ""));
            _employee.setEmpName(StringUtil.safeToString(username.get("username"), ""));
            _employee.setInservice(Boolean.getBoolean(StringUtil.safeToString(username.get("inservice"), "false")) ? 1 : 0);
            _employee.setEmail((String)_userprops.get("email"));
            _employee.setTel((String)_userprops.get("tel"));
            int sex = _userprops.get("sex") != null ? NumberTool.convertMapKeyToInt(_userprops, "sex").intValue() : -1;
            _employee.setSex(sex);
            _employee.setJob((String)_userprops.get("job"));
            _employee.setMobilePhone((String)_userprops.get("mobilephone"));
            _employee.setTel2((String)_userprops.get("tel2"));
            _employee.setFamilytelephone((String)_userprops.get("familytelephone"));
            _employee.setMobilephone2((String)_userprops.get("mobilephone2"));
            _employee.setFamilytelephone2((String)_userprops.get("familytelephone2"));
            _employee.setOtherphone((String)_userprops.get("otherphone"));
            _employee.setOtherphone2((String)_userprops.get("otherphone2"));
            _employee.setBp((String)_userprops.get("bp"));
            _employee.setOffice((String)_userprops.get("office"));
            _employee.setFax((String)_userprops.get("fax"));
            _employee.setFax2((String)_userprops.get("fax2"));
            _employee.setFamilyfax((String)_userprops.get("familyfax"));
            _employee.setFamilyfax2((String)_userprops.get("familyfax2"));
            _employee.setCompanyaddress((String)_userprops.get("companyaddress"));
            _employee.setFamilyaddress((String)_userprops.get("familyaddress"));
            _employee.setCompanyzip((String)_userprops.get("companyzip"));
            _employee.setFamilyzip((String)_userprops.get("familyzip"));
            _employee.setOtheraddress((String)_userprops.get("otheraddress"));
            _employee.setOtherzip((String)_userprops.get("otherzip"));
            _employee.setEmail1((String)_userprops.get("email1"));
            _employee.setEmail2((String)_userprops.get("email2"));
            _employee.setHomepage((String)_userprops.get("homepage"));
            _employee.setQq((String)_userprops.get("qq"));
            _employee.setMsn((String)_userprops.get("msn"));
            _employee.setWhere((String)_userprops.get("where"));
            _employee.setAddressbook((String)_userprops.get("addressbook"));
            _employee.setIntroduction((String)_userprops.get("introduction"));
            _employee.setPasswordUpdateTime((String)_userprops.get("passwordupdatetime"));
            _employee.setUserLockTime((String)_userprops.get("userlocktime"));
            return _employee;
        }
        catch(Exception e)
        {
            LOGGER.error("\u83B7\u53D6\u7528\u6237\u4FE1\u606F\u5931\u8D25\uFF01", e);
        }
        return null;
    }

    public void insertEmployeeWithoutSession(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
    {
        IMetaDBContext metaDBContext = getMetaDBContext();
        com.sinitek.spirit.org.server.entity.IOrgObject orgObject = (com.sinitek.spirit.org.server.entity.IOrgObject)metaDBContext.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgObject);
        orgObject.setOrgId(employee.getId());
        orgObject.setOrgName(employee.getEmpName());
        orgObject.setObjectType(ObjectType.EMPLOYEE);
        orgObject.setUserId(employee.getUserId());
        orgObject.setInservice(Boolean.valueOf(employee.getInservice() == 1));
        orgObject.save();
        ISetting setting = CommonServiceFactory.getSettingService().getSetting("ORG", "PASSWORD");
        UserInfo userInfo = (UserInfo)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserInfo);
        userInfo.setUserId(employee.getUserId());
        userInfo.setUserName(employee.getUserName());
        if(null == setting || StringUtil.safeToString(setting.getValue(), "").isEmpty())
            userInfo.setPassword("111");
        else
            userInfo.setPassword((new EncryptUtil()).encodeMD5(setting.getValue()));
        userInfo.setLockFlag(Boolean.valueOf(false));
        userInfo.save();
        Class cls = employee.getClass();
        Field field[] = cls.getDeclaredFields();
        Field arr$[] = field;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            Field fd = arr$[i$];
            fd.setAccessible(true);
            String val = "";
            try
            {
                Object object = fd.get(employee);
                if(null != object)
                    val = object.toString();
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            UserProperty emailproperty = (UserProperty)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserProperty);
            emailproperty.setUserId(employee.getUserId());
            emailproperty.setName(fd.getName());
            emailproperty.setValue(val);
            emailproperty.save();
        }

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

    }

    public void updateEmployeeWithoutSession(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
    {
        IMetaDBContext metaDBContext = getMetaDBContext();
        com.sinitek.spirit.org.server.entity.IOrgObject orgObject = (com.sinitek.spirit.org.server.entity.IOrgObject)metaDBContext.get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", employee.getId());
        if(null == orgObject)
            orgObject = (com.sinitek.spirit.org.server.entity.IOrgObject)metaDBContext.createNewMetaObject(com/sinitek/spirit/org/server/entity/IOrgObject);
        orgObject.setOrgId(employee.getId());
        orgObject.setOrgName(employee.getEmpName());
        orgObject.setObjectType(ObjectType.EMPLOYEE);
        orgObject.setUserId(employee.getUserId());
        orgObject.setInservice(Boolean.valueOf(employee.getInservice() == 1));
        orgObject.save();
        ISetting setting = CommonServiceFactory.getSettingService().getSetting("ORG", "PASSWORD");
        UserInfo userInfo = (UserInfo)metaDBContext.get(com/sinitek/spirit/um/server/userdb/UserInfo, "userid", employee.getUserId());
        if(null == userInfo)
            userInfo = (UserInfo)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserInfo);
        userInfo.setUserId(employee.getUserId());
        userInfo.setUserName(employee.getUserName());
        if(StringUtil.safeToString(employee.getPassword(), "").isEmpty())
            if(null == setting || StringUtil.safeToString(setting.getValue(), "").isEmpty())
                userInfo.setPassword((new EncryptUtil()).encodeMD5("111"));
            else
                userInfo.setPassword((new EncryptUtil()).encodeMD5(setting.getValue()));
        userInfo.setLockFlag(userInfo.getLockFlag());
        userInfo.save();
        Class cls = employee.getClass();
        Field field[] = cls.getDeclaredFields();
        Field arr$[] = field;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            Field fd = arr$[i$];
            fd.setAccessible(true);
            String val = "";
            try
            {
                Object object = fd.get(employee);
                if(null != object)
                    val = object.toString();
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            UserProperty emailproperty = (UserProperty)metaDBContext.get(com/sinitek/spirit/um/server/userdb/UserProperty, new String[] {
                "userid", "name"
            }, new Object[] {
                employee.getUserId(), fd.getName()
            });
            if(null == emailproperty)
                emailproperty = (UserProperty)metaDBContext.createNewMetaObject(com/sinitek/spirit/um/server/userdb/UserProperty);
            emailproperty.setUserId(employee.getUserId());
            emailproperty.setName(fd.getName());
            emailproperty.setValue(val);
            emailproperty.save();
        }

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

    }

    public void unlockUserWithoutSession(String userid)
    {
        IMetaDBContext metaDBContext = getMetaDBContext();
        UserInfo userInfo = (UserInfo)metaDBContext.get(com/sinitek/spirit/um/server/userdb/UserInfo, "userid", userid);
        userInfo.setLockFlag(Boolean.valueOf(false));
        userInfo.save();
    }

    public List searchOrgByName(String searchStr)
    {
        StringBuilder sql = new StringBuilder();
        Map params = new HashMap();
        sql.append("select orgid, orgname from sprt_orgobject o\n");
        sql.append("where 1=1 \n");
        if(StringUtils.isNotBlank(searchStr))
        {
            sql.append("and (\n");
            sql.append(" upper(orgname) like :searchstr \n");
            sql.append(" or userid in (select userid from um_userinfo where upper(username) like :searchstr)\n");
            sql.append(")\n");
            params.put("searchstr", (new StringBuilder()).append("%").append(searchStr.toUpperCase()).append("%").toString());
        }
        IMetaDBQuery mq = getMetaDBContext().createSqlQuery(sql.toString());
        mq.setDefaultOrderBy("orgname");
        mq.setParameters(params);
        return mq.getResult();
    }

    public List findTeamsByTeamerId(String orgId)
    {
        List chargeTeams = new ArrayList();
        List teams = findTeamsByEmpId(orgId);
        for(Iterator i$ = teams.iterator(); i$.hasNext();)
        {
            Team team = (Team)i$.next();
            List captaions = findTeamerById((new StringBuilder()).append(team.getTeamId()).append("").toString());
            Iterator i$ = captaions.iterator();
            while(i$.hasNext()) 
            {
                Employee employee = (Employee)i$.next();
                if(employee.getId().equals(orgId))
                    chargeTeams.add(team);
            }
        }

        return chargeTeams;
    }

    public List findTeammatesByTeamInfo(Map searchCondition)
    {
        StringBuffer sql = new StringBuffer("select distinct team.*,userinfo.*  from (select a.orgid as teamid, a.orgname          from sprt_orgobject a         where a.unittype = 'TEAM'           and exists (select 1                  from sprt_orgrela b                 where b.fromobjectid = a.orgid                   and b.relationtype = 'TEAMER'                    and b.toobjectid = :teamer)) team,       sprt_orgrela relation,       (select c.orgid as userid, c.orgname as userorgname, d.username          from sprt_orgobject c, um_userinfo d         where c.userid = d.userid) userinfo where team.teamid = relation.fromobjectid   and relation.toobjectid = userinfo.userid");
        Map paraMap = new HashMap();
        if(searchCondition.get("teamer") == null)
            return new ArrayList();
        paraMap.put("teamer", searchCondition.get("teamer"));
        String username = (String)searchCondition.get("username");
        if(username != null && !"".equals(username))
        {
            sql.append(" and ( userinfo.username like :username   or userinfo.userorgname like :username )");
            paraMap.put("username", (new StringBuilder()).append("%").append(username).append("%").toString());
        }
        IMetaDBQuery query = super.getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(paraMap);
        return query.getResult();
    }

    public void deleteSprtObjectRealByObjid(String orgid)
    {
        if(orgid != null && !"".equals(orgid))
        {
            String sql = "delete from sprt_orgrela r where r.objid in (select a.objid from sprt_orgrela a where a.toobjectid = :orgid)";
            ISQLUpdater _IsqlUpdater = getMetaDBContext().createSqlUpdater(sql);
            _IsqlUpdater.setParameter("orgid", orgid);
            _IsqlUpdater.executeUpdate();
        }
    }

    public List getOrgParentByOrgId(String orgId)
    {
        String sql = "select * from SPRT_ORGRELA ore where ore.toobjectid = :toobjectid order by objid";
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        query.setParameter("toobjectid", orgId);
        return query.getResult();
    }

    public Map getUserinfo(String userid)
    {
        String sql = (new StringBuilder()).append("select * from um_userinfo where userid = '").append(userid).append("'").toString();
        List userinfos = getMetaDBContext().createSqlQuery(sql).getResult();
        if(userinfos.size() > 0)
            return (Map)userinfos.get(0);
        else
            return null;
    }

    public void deleteEmployeeByUnitIdAndEmpId(String unitId, String empId)
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        List unitPositList = findPositionsByUnitId(unitId);
        List empPositList = findPositionsByEmpId(empId);
        List _poslist = new ArrayList();
        for(Iterator i$ = empPositList.iterator(); i$.hasNext();)
        {
            Position empPosit = (Position)i$.next();
            if(empPosit != null)
            {
                Iterator i$ = unitPositList.iterator();
                while(i$.hasNext()) 
                {
                    Position unitPosit = (Position)i$.next();
                    if(unitPosit != null && (empPosit.getOrgid() != null && empPosit.getOrgid().equals(unitPosit.getOrgid())))
                        _poslist.add(unitPosit);
                }
            }
        }

        IEmployee _empUnit = (IEmployee)_finder.getOrgObject(empId);
        Iterator i$ = _poslist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Position _pos = (Position)i$.next();
            IUnit _parentUnit = (IUnit)_finder.getOrgObject(_pos.getOrgid());
            if(_empUnit != null && _parentUnit != null)
                try
                {
                    _updater.deleteRelationship(_parentUnit, _empUnit, com.sinitek.spirit.org.core.IRelationship.RelationshipType.SUPERVISION.toString());
                }
                catch(Exception e)
                {
                    LOGGER.error((new StringBuilder()).append(unitId).append("\u90E8\u95E8\u4E0B\u6CA1\u6709").append(empId).append("\u7528\u6237").toString(), e);
                }
        } while(true);
        _updater.flush();
    }

    public List getByEmpName(String empname)
    {
        IMetaDBQuery query = getMetaDBContext().createSqlQuery("select objid, empname from employee where empname = :empname");
        query.setParameter("empname", empname);
        return query.getResult();
    }

    public List getOrgRelationInfo(String fromObjectId, String toObjectId, String type)
    {
        StringBuilder _sql = new StringBuilder();
        Map _param = new HashMap();
        _sql.append(" 1=1");
        if(StringUtils.isNotBlank(type))
        {
            _sql.append(" and relationtype = :type \n");
            _param.put("type", type);
        }
        if(StringUtils.isNotBlank(toObjectId))
        {
            _sql.append(" and toobjectid = :toobjectid \n");
            _param.put("toobjectid", toObjectId);
        }
        if(StringUtils.isNotBlank(fromObjectId))
        {
            _sql.append(" and fromobjectid = :fromobjectid \n");
            _param.put("fromobjectid", fromObjectId);
        }
        IMetaDBQuery _query = getMetaDBContext().createQuery("ORGRELATIONINFO", _sql.toString());
        _query.setParameters(_param);
        return _query.getResult();
    }

    public void saveOrgRelationInfo(IOrgRelationInfo orgRelationInfo)
    {
        if(orgRelationInfo != null)
            super.save(orgRelationInfo);
    }

    public List findUnitsByUnitId(String unitId)
    {
        List result = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IUnit _unit = (IUnit)_finder.getOrgObject(unitId);
        if(_unit != null)
        {
            List _orglist = _finder.findOrgObjectsByPath(_unit, "DOWN(orgtype='UNIT';level=all)");
            Department department;
            for(Iterator i$ = _orglist.iterator(); i$.hasNext(); result.add(department))
            {
                IOrgObject _org = (IOrgObject)i$.next();
                department = getDepartmentById(_org.getId());
            }

        }
        return result;
    }

    public List findAllPositions()
    {
        List result = new ArrayList();
        try
        {
            List deptList = findAllDepartments();
            Iterator i$ = deptList.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Department dept = (Department)i$.next();
                if(dept != null)
                    result.addAll(findPositionsByUnitId(dept.getOrgid()));
            } while(true);
            result.addAll(findPositionsByUnitId("99999"));
        }
        catch(Exception e)
        {
            return result;
        }
        return result;
    }

    public Employee getEmployeeByProperty(String propertyName, String propertyValue)
    {
        Employee employee = null;
        if(StringUtils.isBlank(propertyValue))
            return employee;
        Map param = new HashMap();
        param.put(propertyName, propertyValue);
        List list = findAllEmployees(param);
        if(list != null && list.size() > 0)
            employee = (Employee)list.get(0);
        return employee;
    }

    public IOrgUserExtendInfo getOrgUserExtendInfoByUserId(String userId)
    {
        Map param = new HashMap();
        param.put("userid", userId);
        List list = getMetaDBContext().query("USEREXTENDINFO", "userid=:userid", param);
        if(list != null && list.size() > 0)
            return (IOrgUserExtendInfo)list.get(0);
        else
            return null;
    }

    public void saveOrgUserExtendInfo(IOrgUserExtendInfo orgUserExtendInfo)
    {
        save(orgUserExtendInfo);
    }

    public List findAllDepartments()
    {
        List result = findUnitsByUnitId("99999");
        return result;
    }

    public List findAllTeams()
    {
        List teams = new ArrayList();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _empOrg = _finder.getRoot();
        if(_empOrg != null && !"".equals(_empOrg.getId()))
        {
            List _objlist = _finder.findOrgObjectsByPath(_empOrg, "DOWN(orgtype='TEAM')");
            Team team;
            for(Iterator i$ = _objlist.iterator(); i$.hasNext(); teams.add(team))
            {
                IOrgObject _obj = (IOrgObject)i$.next();
                team = new Team();
                team.setTeamId(_obj.getId());
                team.setTeamName(_obj.getName());
                team.setTeamDescription(_obj.getDescription());
            }

        }
        return teams;
    }

    static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/service/impl/OrgServiceImpl);

}
