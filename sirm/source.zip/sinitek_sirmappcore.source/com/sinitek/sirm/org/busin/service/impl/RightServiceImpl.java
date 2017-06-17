// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RightServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.enumsupport.AbstractEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.Department;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.entity.Position;
import com.sinitek.sirm.org.busin.enumerate.OrgType;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.IEmployee;
import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.org.core.IOrgObject;
import com.sinitek.spirit.org.core.IUnit;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.AuthType;
import com.sinitek.spirit.right.core.IRightAuthInfo;
import com.sinitek.spirit.right.core.IRightType;
import com.sinitek.spirit.right.core.IRightUpdater;
import com.sinitek.spirit.right.server.entity.IRightAuth;
import com.sinitek.spirit.right.server.entity.RightAuthImpl;
import com.sinitek.spirit.um.NoSuchUserException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class RightServiceImpl extends AbstractMetaDBContextSupport
    implements IRightService
{
    private static class RightAuthInfo
        implements IRightAuthInfo
    {

        public String getRightDefineKey()
        {
            return rightDefineKey;
        }

        public String getAuthOrgObjectId()
        {
            return authOrgObjectId;
        }

        public String getAuthOrgExpression()
        {
            return authOrgExpression;
        }

        public String getObjectKey()
        {
            return objectKey;
        }

        public Boolean getRejectFlag()
        {
            return rejectFlag;
        }

        public String getAuthRightType()
        {
            return authRightType;
        }

        private String rightDefineKey;
        private String authOrgObjectId;
        private String authOrgExpression;
        private String objectKey;
        private Boolean rejectFlag;
        private String authRightType;

        private RightAuthInfo(String rightDefineKey, String authOrgObjectId, String authOrgExpression, String objectKey, Boolean rejectFlag, String authRightType)
        {
            this.rightDefineKey = null;
            this.authOrgObjectId = null;
            this.authOrgExpression = null;
            this.objectKey = null;
            this.rejectFlag = null;
            this.authRightType = null;
            this.rightDefineKey = rightDefineKey;
            this.authOrgObjectId = authOrgObjectId;
            this.authOrgExpression = authOrgExpression;
            this.objectKey = objectKey;
            this.rejectFlag = rejectFlag;
            this.authRightType = authRightType;
        }

    }

    public static class RightType extends AbstractEnumItem
        implements IRightType
    {

        public RightType(String enumItemName)
        {
            super(enumItemName, 1, null, null);
        }
    }


    public RightServiceImpl()
    {
    }

    public boolean checkRight(int userid, String orgstr)
        throws NoSuchUserException
    {
        boolean result = false;
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgObject _object = _finder.getOrgObject(orgstr);
        IOrgService _service = OrgServiceFactory.getOrgService();
        List _emplist = new ArrayList();
        boolean isEmployee = _object instanceof IEmployee;
        if(!isEmployee)
        {
            boolean isUnit = _object instanceof IUnit;
            if(isUnit)
            {
                IUnit _unit = (IUnit)_object;
                if(_unit.getUnitType().equals(OrgType.DEPT.getEnumItemInfo()))
                    _emplist.addAll(_service.findEmployeeByUnitId(orgstr));
                else
                if(_unit.getUnitType().equals(OrgType.POSITION.getEnumItemInfo()))
                    _emplist.addAll(_service.findEmployeeByPosId(orgstr));
                else
                if(_unit.getUnitType().equals(OrgType.TEAM.getEnumItemInfo()))
                    _emplist.addAll(_service.findEmployeeByTeamId(orgstr));
            }
        } else
        {
            _emplist.add(_service.getEmployeeById(orgstr));
        }
        Iterator i$ = _emplist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Employee _emp = (Employee)i$.next();
            if(!_emp.getId().equals(String.valueOf(userid)))
                continue;
            result = true;
            break;
        } while(true);
        return result;
    }

    public void addRightAuth(OrgObject orgObj, String rightDefineKey, boolean rejectFlag, String rightTypes[])
    {
        addRightAuth(orgObj, "NULL", rightDefineKey, rejectFlag, rightTypes);
    }

    public void addRightAuth(OrgObject orgObj, String rightObjId, String rightDefineKey, boolean rejectFlag, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(!_rightUpdater.hasRightDefine(rightDefineKey))
            _rightUpdater.createRightDefine(rightDefineKey, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(rightDefineKey).toString(), AuthType.OBJECT_KEY, null);
        String orgId = orgObj.getOrgId();
        String arr$[] = rightTypes;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String rightType = arr$[i$];
            _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, orgId, null, rightObjId, null, rightType));
        }

        IRightType _rightTypes[] = new IRightType[rightTypes.length];
        int i = 0;
        for(int length = rightTypes.length; i < length; i++)
            _rightTypes[i] = new RightType(rightTypes[i]);

        String _typestr = null;
        _rightUpdater.addRightAuth(orgId, _typestr, rightObjId, rightDefineKey, rejectFlag, _rightTypes);
    }

    public void addRightAuth(String orgId, String rightObjId, String rightDefineKey, boolean rejectFlag, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(!_rightUpdater.hasRightDefine(rightDefineKey))
            _rightUpdater.createRightDefine(rightDefineKey, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(rightDefineKey).toString(), AuthType.OBJECT_KEY, null);
        IRightType _rightTypes[] = new IRightType[rightTypes.length];
        int i = 0;
        for(int length = rightTypes.length; i < length; i++)
            _rightTypes[i] = new RightType(rightTypes[i]);

        _rightUpdater.addRightAuth(orgId, null, rightObjId, rightDefineKey, rejectFlag, _rightTypes);
    }

    public void deleteRightAuth(OrgObject orgObj, String rightDefineKey, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(rightTypes != null)
        {
            String arr$[] = rightTypes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String rightType = arr$[i$];
                _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, orgObj.getOrgId(), null, null, null, rightType));
            }

        } else
        {
            _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, orgObj.getOrgId(), null, null, null, null));
        }
    }

    public void deleteRightAuth(OrgObject orgObj, String objectKey, String rightDefineKey, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(rightTypes != null)
        {
            String arr$[] = rightTypes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String rightType = arr$[i$];
                _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, orgObj.getOrgId(), null, objectKey, null, rightType));
            }

        } else
        {
            _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, orgObj.getOrgId(), null, objectKey, null, null));
        }
    }

    public void deleteRightAuth(String objectKey, String rightDefineKey, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(rightTypes != null)
        {
            String arr$[] = rightTypes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String rightType = arr$[i$];
                _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, null, null, objectKey, null, rightType));
            }

        } else
        {
            _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, null, null, objectKey, null, null));
        }
    }

    public void deleteRightAuth(String rightDefineKey, String rightTypes[])
    {
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        if(rightTypes != null)
        {
            String arr$[] = rightTypes;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String rightType = arr$[i$];
                _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, null, null, null, null, rightType));
            }

        } else
        {
            _rightUpdater.deleteRightAuth(new RightAuthInfo(rightDefineKey, null, null, null, null, null));
        }
    }

    public boolean checkRight(OrgObject orgObj, String rightDefineKey, String rightType)
    {
        return checkRight(orgObj.getOrgId(), rightDefineKey, rightType);
    }

    public boolean checkRight(String orgid, String rightDefineKey, String rightType)
    {
        boolean _result = false;
        List lists = findAuthedObjects(orgid, rightDefineKey, rightType);
        if(lists.size() > 0)
            _result = true;
        return _result;
    }

    public boolean checkOrgRight(OrgObject orgObj, String rightObjId, String rightDefineKey, String rightType)
    {
        return checkOrgRight(orgObj.getOrgId(), rightObjId, rightDefineKey, rightType);
    }

    public boolean checkOrgRight(String orgid, String rightObjId, String rightDefineKey, String rightType)
    {
        boolean _result = false;
        if(StringUtils.isNotBlank(rightObjId))
        {
            List lists = findAuthedObjects(orgid, rightDefineKey, rightType);
            _result = lists.contains(rightObjId);
        }
        return _result;
    }

    public boolean checkRight(OrgObject orgObj, String rightObjId, String rightDefineKey, String rightType)
    {
        return checkRight(orgObj.getOrgId(), rightObjId, rightDefineKey, rightType);
    }

    public boolean checkRight(String orgid, String rightObjId, String rightDefineKey, String rightType)
    {
        boolean _result = false;
        if(StringUtils.isNotBlank(rightObjId))
        {
            List lists = findAuthedObjects(orgid, rightDefineKey, rightType);
            _result = lists.contains(rightObjId);
        }
        return _result;
    }

    public List findAuthOrgObjects(String rightObjId, String rightDefineKey, String rightType)
    {
        IOrgService _orgservice = OrgServiceFactory.getOrgService();
        List _result = new ArrayList();
        Set _orgIds = new HashSet();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        List _rightAuthInfos = _rightUpdater.getRightAuthInfos(rightDefineKey, rightObjId);
        Iterator i$ = _rightAuthInfos.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IRightAuthInfo _auth = (IRightAuthInfo)i$.next();
            if(!_orgIds.contains(_auth.getAuthOrgObjectId()) && _auth.getAuthRightType().equals(rightType))
            {
                OrgObject _object = _orgservice.getOrgObjectById(_auth.getAuthOrgObjectId());
                if(_object != null)
                {
                    _result.add(_object);
                    _orgIds.add(_object.getOrgId());
                }
            }
        } while(true);
        return _result;
    }

    public List findEnableAuthOrgObjects(String rightObjId, String rightDefineKey, String rightType)
    {
        IOrgService _orgservice = OrgServiceFactory.getOrgService();
        Set _orgIds = new HashSet();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        List orgs = new ArrayList();
        List _rightAuthInfos = _rightUpdater.getRightAuthInfos(rightDefineKey, rightObjId);
        Iterator i$ = _rightAuthInfos.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IRightAuthInfo _auth = (IRightAuthInfo)i$.next();
            if(!_auth.getRejectFlag().booleanValue() && rightType.equals(_auth.getAuthRightType()) && !_orgIds.contains(_auth.getAuthOrgObjectId()))
            {
                _orgIds.add(_auth.getAuthOrgObjectId());
                OrgObject oo = _orgservice.getOrgObjectById(_auth.getAuthOrgObjectId());
                if(oo != null)
                    orgs.add(oo);
            }
        } while(true);
        return orgs;
    }

    public List findAuthedObjects(String orgId, String rightDefineKey, String rightType)
    {
        List _result = new ArrayList();
        List rightAuthList = findEnableAuthedObjects(orgId, rightDefineKey, rightType, null);
        IRightAuth rightAuth;
        for(Iterator i$ = rightAuthList.iterator(); i$.hasNext(); _result.add(rightAuth.getObjectKey()))
            rightAuth = (IRightAuth)i$.next();

        return _result;
    }

    public List findAuthedObjects(String orgId, String rightDefineKey, String rightType[])
    {
        List _result = new ArrayList();
        List rightAuthList = findEnableAuthedObjects(orgId, rightDefineKey, rightType, null);
        IRightAuth rightAuth;
        for(Iterator i$ = rightAuthList.iterator(); i$.hasNext(); _result.add(rightAuth.getObjectKey()))
            rightAuth = (IRightAuth)i$.next();

        return _result;
    }

    public List findEnableAuthedObjects(String orgId, String rightDefineKey, String rightType, List outlist)
    {
        List _result = new ArrayList();
        Map allAuthMap = getAllAuthMap(orgId, rightDefineKey, new String[] {
            rightType
        }, 3, true);
        String key;
        for(Iterator i$ = allAuthMap.keySet().iterator(); i$.hasNext(); _result.add(allAuthMap.get(key)))
            key = (String)i$.next();

        if(outlist != null)
        {
            Map selfAuthMap = getAllAuthMap(orgId, rightDefineKey, new String[] {
                rightType
            }, 1, false);
            String key;
            for(Iterator i$ = selfAuthMap.keySet().iterator(); i$.hasNext(); outlist.add(selfAuthMap.get(key)))
                key = (String)i$.next();

        }
        return _result;
    }

    private Map getAllAuthMap(String orgId, String rightDefineKey, String rightType[], int type, boolean excludereject)
    {
        Map parentAuth = new HashMap();
        List list = new ArrayList();
        if((type & 1) == 1)
        {
            Map map = new HashMap();
            map.put("fromobjectid", orgId);
            map.put("lev", Integer.valueOf(-1));
            list.add(map);
        }
        if((type & 2) == 2)
            list.addAll(OrgServiceFactory.getOrgService().findAllOrgParentIdByOrgId(orgId));
        Iterator i$;
        if(list.size() > 0)
        {
            Map fromobjectidlevMap = new HashMap();
            String fromobjectid;
            int lev;
            for(i$ = list.iterator(); i$.hasNext(); fromobjectidlevMap.put(fromobjectid, Integer.valueOf(lev)))
            {
                Map map = (Map)i$.next();
                fromobjectid = String.valueOf(map.get("fromobjectid"));
                lev = Integer.parseInt(String.valueOf(map.get("lev")));
            }

            Set orgids = new HashSet();
            Map m;
            for(Iterator i$ = list.iterator(); i$.hasNext(); orgids.add((String)m.get("fromobjectid")))
                m = (Map)i$.next();

            String _sql2 = (new StringBuilder()).append("select * from sprt_rightauth where rightdefinekey=:rightdefinekey and righttype in ('").append(org.apache.commons.lang.StringUtils.join(rightType, "','")).append("')").append("and authorgid in ('").append(org.apache.commons.lang.StringUtils.join(orgids, "','")).append("')").toString();
            IMetaDBQuery _query = getMetaDBContext().createSqlQuery(_sql2);
            _query.setParameter("rightdefinekey", rightDefineKey);
            _query.setParameter("righttype", rightType);
            List list2 = _query.getResult();
            Iterator i$ = list2.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map map = (Map)i$.next();
                String authorgid = String.valueOf(map.get("authorgid"));
                String objectkey = String.valueOf(map.get("objectkey"));
                String rejectflag = String.valueOf(map.get("rejectflag"));
                int lev = ((Integer)fromobjectidlevMap.get(authorgid)).intValue();
                if(parentAuth.get(objectkey) == null)
                {
                    parentAuth.put(objectkey, map);
                } else
                {
                    Map map2 = (Map)parentAuth.get(objectkey);
                    int lev2 = ((Integer)fromobjectidlevMap.get(String.valueOf(map2.get("authorgid")))).intValue();
                    if(lev < lev2)
                        parentAuth.put(objectkey, map);
                    else
                    if(lev == lev2 && "1".equals(rejectflag))
                        parentAuth.put(objectkey, map);
                }
            } while(true);
        }
        Map result = new HashMap();
        orgids = parentAuth.keySet().iterator();
        do
        {
            if(!orgids.hasNext())
                break;
            String key = (String)orgids.next();
            Map map = (Map)parentAuth.get(key);
            String authorgid = String.valueOf(map.get("authorgid"));
            String objectkey = String.valueOf(map.get("objectkey"));
            String rejectflag = String.valueOf(map.get("rejectflag"));
            IRightAuth rightAuth = new RightAuthImpl();
            rightAuth.setAuthOrgId(authorgid);
            rightAuth.setObjectKey(objectkey);
            rightAuth.setRejectFlag(Boolean.valueOf("0".equals(rejectflag)));
            if(excludereject)
            {
                if("0".equals(rejectflag))
                    result.put(key, rightAuth);
            } else
            {
                result.put(key, rightAuth);
            }
        } while(true);
        return result;
    }

    public List findEnableAuthedObjects(String orgId, String rightDefineKey, String rightType[], List outlist)
    {
        List _result = new ArrayList();
        Map allAuthMap = getAllAuthMap(orgId, rightDefineKey, rightType, 3, true);
        String key;
        for(Iterator i$ = allAuthMap.keySet().iterator(); i$.hasNext(); _result.add(allAuthMap.get(key)))
            key = (String)i$.next();

        if(outlist != null)
        {
            Map selfAuthMap = getAllAuthMap(orgId, rightDefineKey, rightType, 1, false);
            String key;
            for(Iterator i$ = selfAuthMap.keySet().iterator(); i$.hasNext(); outlist.add(selfAuthMap.get(key)))
                key = (String)i$.next();

        }
        return _result;
    }

    public void recoverOrgAuth1(String orgId, String rightType, String rightDefineKey)
    {
        Map selflist = getAllAuthMap(orgId, rightDefineKey, new String[] {
            rightType
        }, 1, false);
        Map allParentAuthMap = getAllAuthMap(orgId, rightDefineKey, new String[] {
            rightType
        }, 2, true);
        OrgObject orgObject = new OrgObject(orgId, 0);
        Iterator i$ = allParentAuthMap.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            String key = (String)i$.next();
            if(selflist.get(key) != null && ((IRightAuth)selflist.get(key)).getRejectFlag() == ((IRightAuth)allParentAuthMap.get(key)).getRejectFlag())
                OrgServiceFactory.getRightService().deleteRightAuth(orgObject, key, rightDefineKey, new String[] {
                    rightType
                });
        } while(true);
        i$ = selflist.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            String key = (String)i$.next();
            if(allParentAuthMap.get(key) == null && !((IRightAuth)selflist.get(key)).getRejectFlag().booleanValue())
                OrgServiceFactory.getRightService().deleteRightAuth(orgObject, key, rightDefineKey, new String[] {
                    rightType
                });
        } while(true);
    }

    public IMetaDBQuery findAuthOrg(String rightDefineKey, int menuId)
    {
        return findAuthOrg(rightDefineKey, menuId, null);
    }

    public IMetaDBQuery findAuthOrg(String rightDefineKey, int menuId, String orgId)
    {
        StringBuilder sql = new StringBuilder();
        Map params = new HashMap();
        sql.append("select a.AUTHORGID orgid, o.orgname, o.unittype, a.rejectflag from SPRT_RIGHTAUTH a\n");
        sql.append("  join sprt_orgobject o on a.authorgid = o.orgid\n");
        sql.append("where objectkey = :menuid");
        params.put("menuid", Integer.valueOf(menuId));
        if(StringUtils.isNotBlank(orgId))
        {
            sql.append(" and a.AUTHORGID = :orgid");
            params.put("orgid", orgId);
        }
        if(StringUtils.isNotBlank(rightDefineKey))
        {
            sql.append(" and rightdefinekey = :rightdefinekey");
            params.put("rightdefinekey", rightDefineKey);
        }
        IMetaDBQuery mq = getMetaDBContext().createSqlQuery(sql.toString());
        mq.setParameters(params);
        return mq;
    }

    public List findAllAuthOrgObjects(String rightObjId, String rightDefineKey, String rightType)
    {
        List result = new ArrayList();
        Map map = new HashMap();
        IOrgService orgService = OrgServiceFactory.getOrgService();
        List orgObjects = findEnableAuthOrgObjects(rightObjId, rightDefineKey, rightType);
        if(StringUtil.isEmpty(orgObjects))
            return result;
        List departments = null;
        List positions = null;
        List employees = null;
        Iterator i$ = orgObjects.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            OrgObject orgObject = (OrgObject)i$.next();
            if(orgObject != null)
            {
                int orgType = orgObject.getOrgType();
                String orgId = orgObject.getOrgId();
                String unittype = "";
                if(orgType == 1)
                {
                    unittype = "UNIT";
                    departments = orgService.findUnitByParentId(orgId);
                    Department department;
                    Map objMap;
                    for(Iterator i$ = departments.iterator(); i$.hasNext(); map.put(department.getOrgid(), objMap))
                    {
                        department = (Department)i$.next();
                        boolean authFlag = checkOrgRight(department.getOrgid(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(department.getOrgid());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "UNIT");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                    positions = orgService.findPositionsByUnitId(orgId);
                    Position position;
                    Map objMap;
                    for(Iterator i$ = positions.iterator(); i$.hasNext(); map.put(position.getOrgid(), objMap))
                    {
                        position = (Position)i$.next();
                        boolean authFlag = checkOrgRight(position.getOrgid(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(position.getOrgid());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "POSITION");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                    employees = orgService.findEmployeeByUnitId(orgId);
                    Employee employee;
                    Map objMap;
                    for(Iterator i$ = employees.iterator(); i$.hasNext(); map.put(employee.getId(), objMap))
                    {
                        employee = (Employee)i$.next();
                        boolean authFlag = checkOrgRight(employee.getId(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(employee.getId());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                } else
                if(orgType == 2)
                {
                    unittype = "POSITION";
                    employees = orgService.findEmployeeByPosId(orgId);
                    Employee employee;
                    Map objMap;
                    for(Iterator i$ = employees.iterator(); i$.hasNext(); map.put(employee.getId(), objMap))
                    {
                        employee = (Employee)i$.next();
                        boolean authFlag = checkOrgRight(employee.getId(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(employee.getId());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                } else
                if(orgType == 4)
                {
                    unittype = "TEAM";
                    employees = orgService.findEmployeeByTeamId(orgId);
                    Employee employee;
                    Map objMap;
                    for(Iterator i$ = employees.iterator(); i$.hasNext(); map.put(employee.getId(), objMap))
                    {
                        employee = (Employee)i$.next();
                        boolean authFlag = checkOrgRight(employee.getId(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(employee.getId());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                } else
                if(orgType == 16)
                {
                    unittype = "ROLE";
                    employees = orgService.findEmployeeByRoleId(orgId);
                    Employee employee;
                    Map objMap;
                    for(Iterator i$ = employees.iterator(); i$.hasNext(); map.put(employee.getId(), objMap))
                    {
                        employee = (Employee)i$.next();
                        boolean authFlag = checkOrgRight(employee.getId(), rightObjId, rightDefineKey, rightType);
                        OrgObject object = orgService.getOrgObjectById(employee.getId());
                        objMap = new HashMap();
                        objMap.put("orgid", object.getOrgId());
                        objMap.put("orgname", object.getOrgName());
                        objMap.put("unittype", "");
                        String rejectflag = "";
                        if(authFlag)
                            rejectflag = "0";
                        else
                            rejectflag = "1";
                        objMap.put("rejectflag", rejectflag);
                    }

                } else
                if(orgType == 8)
                    unittype = "";
                Map orgMap = new HashMap();
                orgMap.put("orgid", orgObject.getOrgId());
                orgMap.put("orgname", orgObject.getOrgName());
                orgMap.put("unittype", unittype);
                orgMap.put("rejectflag", "0");
                map.put(orgId, orgMap);
            }
        } while(true);
        String key;
        for(i$ = map.keySet().iterator(); i$.hasNext(); result.add(map.get(key)))
            key = (String)i$.next();

        return result;
    }

    public IMetaDBQuery findAuthOrg(String orgIds, String rightDefineKey, String rightType)
    {
        StringBuilder sql = new StringBuilder();
        Map params = new HashMap();
        sql.append("select distinct a.AUTHORGID orgid, o.orgname, o.unittype, a.rejectflag from SPRT_RIGHTAUTH a\n");
        sql.append("  join sprt_orgobject o on a.authorgid = o.orgid\n");
        sql.append("where a.rightType = :rightType");
        params.put("rightType", rightType);
        if(StringUtils.isNotBlank(orgIds))
            sql.append(" and a.AUTHORGID in(").append(orgIds).append(")");
        if(StringUtils.isNotBlank(rightDefineKey))
        {
            sql.append(" and rightdefinekey = :rightdefinekey");
            params.put("rightdefinekey", rightDefineKey);
        }
        IMetaDBQuery mq = getMetaDBContext().createSqlQuery(sql.toString());
        mq.setParameters(params);
        return mq;
    }
}
