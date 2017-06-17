// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSchemeServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.google.common.base.Splitter;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;

public class UserSchemeServiceImpl extends MetaDBContextSupport
    implements IUserSchemeService
{

    public UserSchemeServiceImpl()
    {
    }

    public List findUserScheme(IUserScheme userScheme)
    {
        List result = new ArrayList();
        StringBuilder sql = new StringBuilder("1=1 ");
        Map map = new HashMap();
        if(userScheme != null)
        {
            if(null != userScheme.getCode() && !"".equals(userScheme.getCode()))
                QueryUtils.buildLike("code", userScheme.getCode(), sql, map);
            if(null != userScheme.getRange())
            {
                sql.append(" and range=:range");
                map.put("range", userScheme.getRange());
            }
            if(null != userScheme.getStatus())
            {
                sql.append(" and status=:status");
                map.put("status", userScheme.getStatus());
            }
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("USERSCHEME", sql.toString());
        if(map.size() > 0)
            iMetaDBQuery.setParameters(map);
        result = iMetaDBQuery.getResult();
        return result;
    }

    public IUserScheme getUserScheme(int schemeId)
    {
        IUserScheme userScheme = null;
        if(schemeId != 0)
            userScheme = (IUserScheme)getMetaDBContext().get("USERSCHEME", schemeId);
        return userScheme;
    }

    public void saveUserScheme(IUserScheme userScheme)
    {
        userScheme.save();
    }

    public void delUserScheme(int objid)
    {
        if(objid != 0)
        {
            IUserScheme userScheme = getUserScheme(objid);
            if(userScheme != null)
                userScheme.delete();
        }
    }

    public IUserScheme getUserSchemeByCode(String code)
    {
        IUserScheme userScheme = null;
        if(null != code && !"".equals(code))
        {
            Map map = new HashMap();
            map.put("code", code);
            List list = getMetaDBContext().query("USERSCHEME", " code=:code", map);
            if(list.size() > 0)
                userScheme = (IUserScheme)list.get(0);
        }
        return userScheme;
    }

    public List findUserSchemeRelaBySchemeId(int schemeId)
    {
        StringBuilder sql = new StringBuilder("1=1 ");
        Map map = new HashMap();
        if(schemeId != 0)
        {
            sql.append(" and schemeId=:schemeId");
            map.put("schemeId", Integer.valueOf(schemeId));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("USERSCHEMERELA", sql.toString());
        if(map.size() > 0)
            iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public List findUserSchemeRelaBySchemeCode(String schemeCode)
    {
        List result = new ArrayList();
        if(null != schemeCode && !"".equals(schemeCode))
        {
            StringBuilder sql = new StringBuilder("select * from um_userschemerela where schemeid=(select objid from um_userscheme where code=:schemeCode)");
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
            iMetaDBQuery.setParameter("schemeCode", schemeCode);
            List list = iMetaDBQuery.getResult();
            IUserSchemeRela rela;
            for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(rela))
            {
                Map map = (Map)i$.next();
                rela = new UserSchemeRelaImpl();
                rela.setPath(String.valueOf(map.get("path")));
                rela.setSchemeId(NumberTool.convertMapKeyToInt(map, "SchemeId"));
            }

        }
        return result;
    }

    public List findUserSchemeRelaByPath(String path)
    {
        StringBuilder sql = new StringBuilder("1=1 ");
        Map map = new HashMap();
        if(path != null && !"".equals(path))
        {
            QueryUtils.buildLike("path", path, sql, map);
            map.put("path", path);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("USERSCHEMERELA", sql.toString());
        if(map.size() > 0)
            iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public void saveUserSchemeRela(IUserSchemeRela userSchemeRela)
    {
        userSchemeRela.save();
    }

    public void delUserSchemeRela(IUserSchemeRela userSchemeRela)
    {
        userSchemeRela.remove();
    }

    public IUserSchemeRela getUserSchemeRelaById(int objid)
    {
        IUserSchemeRela rela = null;
        if(objid != 0)
            rela = (IUserSchemeRela)getMetaDBContext().get("USERSCHEMERELA", objid);
        return rela;
    }

    public List findEmployeeBySchemeCode(String schemeCode)
    {
        List result = new ArrayList();
        if(schemeCode != null && !"".equals(schemeCode))
        {
            IOrgService orgService = OrgServiceFactory.getOrgService();
            IUserScheme userScheme = getUserSchemeByCode(schemeCode);
            if(userScheme != null)
            {
                StringBuilder orgids = new StringBuilder();
                if(userScheme.getDepartmentIds() != null && !"".equals(userScheme.getDepartmentIds()))
                {
                    if(!"".equals(orgids.toString()))
                        orgids.append(",");
                    orgids.append(userScheme.getDepartmentIds());
                }
                if(userScheme.getPostIds() != null && !"".equals(userScheme.getPostIds()))
                {
                    if(!"".equals(orgids.toString()))
                        orgids.append(",");
                    orgids.append(userScheme.getPostIds());
                }
                if(userScheme.getRoleIds() != null && !"".equals(userScheme.getRoleIds()))
                {
                    if(!"".equals(orgids.toString()))
                        orgids.append(",");
                    orgids.append(userScheme.getRoleIds());
                }
                if(userScheme.getTeamIds() != null && !"".equals(userScheme.getTeamIds()))
                {
                    if(!"".equals(orgids.toString()))
                        orgids.append(",");
                    orgids.append(userScheme.getTeamIds());
                }
                String roleIds[] = orgids.toString().split(",");
                List emplist = new ArrayList();
                String arr$[] = roleIds;
                int len$ = arr$.length;
label0:
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String roleId = arr$[i$];
                    List employeeList = orgService.findEmployeesByOrgId(roleId);
                    Iterator i$ = employeeList.iterator();
                    do
                    {
                        if(!i$.hasNext())
                            continue label0;
                        Employee employee = (Employee)i$.next();
                        if(!emplist.contains(employee.getId()))
                        {
                            emplist.add(employee.getId());
                            result.add(employee);
                        }
                    } while(true);
                }

            }
        }
        return result;
    }

    public List findDepartmentsBySchemeCode(String schemeCode)
    {
        List result = new ArrayList();
        if(schemeCode != null && !"".equals(schemeCode))
        {
            IOrgService orgService = OrgServiceFactory.getOrgService();
            IUserScheme userScheme = getUserSchemeByCode(schemeCode);
            if(userScheme != null)
            {
                StringBuilder orgids = new StringBuilder();
                if(userScheme.getDepartmentIds() != null && !"".equals(userScheme.getDepartmentIds()))
                {
                    if(!"".equals(orgids.toString()))
                        orgids.append(",");
                    orgids.append(userScheme.getDepartmentIds());
                }
                Iterator it = Splitter.on(",").trimResults().omitEmptyStrings().split(orgids.toString()).iterator();
                do
                {
                    if(!it.hasNext())
                        break;
                    String id = (String)it.next();
                    com.sinitek.sirm.org.busin.entity.Department dept = orgService.getDepartmentById(id);
                    if(dept != null)
                        result.add(dept);
                } while(true);
            }
        }
        return result;
    }
}
