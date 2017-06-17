// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgMobileServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.common.pinyin.PinYinHelper;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.ListComparator;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.entity.OrgInfo;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.IOrgFinder;
import com.sinitek.spirit.org.core.IUnit;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;
import org.apache.log4j.Logger;

public class OrgMobileServiceImpl extends MetaDBContextSupport
    implements IOrgMobileService
{

    public OrgMobileServiceImpl()
    {
    }

    public List findAllEmployees()
    {
        return findAllEmployees(null);
    }

    public List findAllEmployees(Map params)
    {
        List result = new ArrayList();
        Map map = new HashMap();
        StringBuilder sql = (new StringBuilder()).append("select e.sex,e.email,e.tel,e.job,e.mobilephone,e.tel2,e.familytelephone,e.mobilephone2,e.familytelephone2,e.otherphone,\n").append("e.otherphone2,e.bp,e.office,e.fax,e.fax2,e.familyfax,e.familyfax2,e.companyaddress,\n").append("e.familyaddress,e.companyzip,e.familyzip,e.otheraddress,e.otherzip,e.email1,e.email2,e.homepage,\n").append("e.qq,e.msn,e.where1,e.addressbook,e.introduction,t1.ydsbh1,t1.ydsbh2,t1.ydsbh3,\n").append("t1.username,t1.userid,t1.ydsbh1,t1.ydsbh2,t1.ydsbh3,\n").append("o.inservice,o.orgid,o.orgname,t1.password from (select userid,max(username) username,max(password) password,\n").append("max(case name when 'ydsbh1' then value end) as ydsbh1 ,\n").append("max(case name when 'ydsbh2' then value end) as ydsbh2 ,\n").append("max(case name when 'ydsbh3' then value end) as ydsbh3 \n").append(" from (\n").append("select u.username,u.userid,p.name,p.value,u.password \n").append("from um_userproperty p,um_userinfo u \n").append("where u.userid=p.userid\n").append(") group by userid) t1,sprt_orgobject o,org_userextendinfo e where o.userid=t1.userid and o.userid=e.userid and t1.username not like '$%'");
        if(params != null)
        {
            if(params.get("username") != null)
                sql.append((new StringBuilder()).append(" and t1.username like '%").append(params.get("username")).append("%'").toString());
            if(params.get("empname") != null)
                sql.append((new StringBuilder()).append(" and o.orgname like '%").append(params.get("empname")).append("%'").toString());
            if(params.get("inservice") != null)
                sql.append((new StringBuilder()).append(" and o.inservice =").append(params.get("inservice")).toString());
            if(null != params.get("unUser") && ((Boolean)params.get("unUser")).booleanValue())
            {
                IOrgFinder _finder = OrgFactory.getOrgFinder();
                String _rootid = _finder.getRoot().getId();
                if(_rootid != null && !_rootid.equals(""))
                    sql.append((new StringBuilder()).append(" and t1.userid not in (select userid from sprt_orgobject  where orgid in(select distinct toobjectid from sprt_orgrela \n where fromobjectid in (select r.toobjectid from sprt_orgobject o right join\n (select * from sprt_orgrela\u3000CONNECT BY PRIOR toobjectid = fromobjectid and relationtype='UNDERLINE'\n START WITH fromobjectid=").append(_rootid).append(") r on o.orgid=r.toobjectid where unittype in('UNIT','POSITION')\n").append(" ) and relationtype ='SUPERVISION'))").toString());
            }
            if(params.get("strutureid") != null)
            {
                List s2 = new ArrayList();
                String _strutureItem[] = params.get("strutureid").toString().split(",");
                String arr$[] = _strutureItem;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String _str = arr$[i$];
                    String _unitid[] = _str.split(":");
                    s2.addAll(OrgServiceFactory.getOrgService().findUserIdsByUnitId(_unitid[0]));
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
                    QueryUtils.buildIn("t1.userid", str, sql, map);
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
                QueryUtils.buildIn("t1.userid", str, sql, map);
            } else
            if(!orgids.isEmpty() && orgids.split(",").length > 0)
            {
                String str[] = orgids.split(",");
                QueryUtils.buildIn("o.orgid", str, sql, map);
            } else
            if(!webserusername.isEmpty() && webserusername.split(",").length > 0)
            {
                String str[] = webserusername.split(",");
                QueryUtils.buildIn("t1.username", str, sql, map);
            }
        }
        IMetaDBQuery _metaDbQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(map.size() > 0)
            _metaDbQuery.setParameters(map);
        Employee _employee;
        for(Iterator i$ = _metaDbQuery.getResult().iterator(); i$.hasNext(); result.add(_employee))
        {
            Map _map = (Map)i$.next();
            _employee = new Employee();
            _employee.setEmail(_map.get("email") != null ? _map.get("email").toString() : "");
            _employee.setEmpName(_map.get("orgname") != null ? _map.get("orgname").toString() : "");
            _employee.setId(_map.get("orgid") != null ? _map.get("orgid").toString() : "");
            _employee.setInservice(_map.get("inservice") != null ? Integer.parseInt(_map.get("inservice").toString()) : 0);
            _employee.setJob(_map.get("job") != null ? _map.get("job").toString() : "");
            _employee.setMobilePhone(_map.get("mobilephone") != null ? _map.get("mobilephone").toString() : "");
            _employee.setSex(_map.get("sex") != null ? Integer.parseInt(_map.get("sex").toString()) : -1);
            _employee.setTel(_map.get("tel") != null ? _map.get("tel").toString() : "");
            _employee.setUserId(_map.get("userid") != null ? _map.get("userid").toString() : "");
            _employee.setUserName(_map.get("username") != null ? _map.get("username").toString() : "");
            _employee.setTel2(_map.get("tel2") != null ? _map.get("tel2").toString() : "");
            _employee.setFamilytelephone(_map.get("familytelephone") != null ? _map.get("familytelephone").toString() : "");
            _employee.setMobilephone2(_map.get("mobilephone2") != null ? _map.get("mobilephone2").toString() : "");
            _employee.setFamilytelephone2(_map.get("familytelephone2") != null ? _map.get("familytelephone2").toString() : "");
            _employee.setOtherphone(_map.get("otherphone") != null ? _map.get("otherphone").toString() : "");
            _employee.setOtherphone2(_map.get("otherphone2") != null ? _map.get("otherphone2").toString() : "");
            _employee.setBp(_map.get("bp") != null ? _map.get("bp").toString() : "");
            _employee.setOffice(_map.get("office") != null ? _map.get("office").toString() : "");
            _employee.setFax(_map.get("fax") != null ? _map.get("fax").toString() : "");
            _employee.setFax2(_map.get("fax2") != null ? _map.get("fax2").toString() : "");
            _employee.setFamilyfax(_map.get("familyfax") != null ? _map.get("familyfax").toString() : "");
            _employee.setFamilyfax2(_map.get("familyfax2") != null ? _map.get("familyfax2").toString() : "");
            _employee.setCompanyaddress(_map.get("companyaddress") != null ? _map.get("companyaddress").toString() : "");
            _employee.setFamilyaddress(_map.get("familyaddress") != null ? _map.get("familyaddress").toString() : "");
            _employee.setCompanyzip(_map.get("companyzip") != null ? _map.get("companyzip").toString() : "");
            _employee.setFamilyzip(_map.get("familyzip") != null ? _map.get("familyzip").toString() : "");
            _employee.setOtheraddress(_map.get("otheraddress") != null ? _map.get("otheraddress").toString() : "");
            _employee.setOtherzip(_map.get("otherzip") != null ? _map.get("otherzip").toString() : "");
            _employee.setEmail1(_map.get("email1") != null ? _map.get("email1").toString() : "");
            _employee.setEmail2(_map.get("email2") != null ? _map.get("email2").toString() : "");
            _employee.setHomepage(_map.get("homepage") != null ? _map.get("homepage").toString() : "");
            _employee.setQq(_map.get("qq") != null ? _map.get("qq").toString() : "");
            _employee.setMsn(_map.get("msn") != null ? _map.get("msn").toString() : "");
            _employee.setWhere(_map.get("where1") != null ? _map.get("where1").toString() : "");
            _employee.setAddressbook(_map.get("addressbook") != null ? _map.get("addressbook").toString() : "");
            _employee.setIntroduction(_map.get("introduction") != null ? _map.get("introduction").toString() : "");
            String ydsbh1 = _map.get("ydsbh1") != null ? _map.get("ydsbh1").toString() : "";
            String ydsbh2 = _map.get("ydsbh2") != null ? _map.get("ydsbh2").toString() : "";
            String ydsbh3 = _map.get("ydsbh3") != null ? _map.get("ydsbh3").toString() : "";
            _employee.setOtherProperty("ydsbh1", ydsbh1);
            _employee.setOtherProperty("ydsbh2", ydsbh2);
            _employee.setOtherProperty("ydsbh3", ydsbh3);
            StringBuilder ydsbh = new StringBuilder("");
            if(null != ydsbh1 && !"".equals(ydsbh1))
                ydsbh.append(ydsbh1);
            if(null != ydsbh2 && !"".equals(ydsbh2))
                if("".equals(ydsbh.toString()))
                    ydsbh.append(ydsbh2);
                else
                    ydsbh.append((new StringBuilder()).append(",").append(ydsbh2).toString());
            if(null != ydsbh3 && !"".equals(ydsbh3))
                if("".equals(ydsbh.toString()))
                    ydsbh.append(ydsbh3);
                else
                    ydsbh.append((new StringBuilder()).append(",").append(ydsbh3).toString());
            _employee.setOtherProperty("ydsbh", ydsbh.toString());
        }

        return result;
    }

    public List getuserlist(Map condition)
    {
        IOrgService orgService = OrgServiceFactory.getOrgService();
        String orgids = StringUtil.safeToString(null == condition ? "" : condition.get("orgids"), "");
        String userids = StringUtil.safeToString(null == condition ? "" : condition.get("userids"), "");
        String username = StringUtil.safeToString(null == condition ? "" : condition.get("username"), "");
        Map params = new HashMap();
        params.put("webserusername", username);
        params.put("userids", userids);
        params.put("orgids", orgids);
        params.put("unUser", Boolean.valueOf(false));
        params.put("inservice", Integer.valueOf(1));
        List list = new ArrayList();
        try
        {
            list = findAllEmployees(params);
        }
        catch(Exception e)
        {
            LOGGER.error("\u67E5\u8BE2\u7528\u6237\u63A5\u53E3\u51FA\u9519", e);
        }
        LOGGER.info((new StringBuilder()).append("\u67E5\u8BE2\u7528\u6237\u6570\u91CF\u4E3A").append(list.size()).toString());
        List listuser = new ArrayList();
        PinYinHelper pinYinHelper = PinYinHelper.getInstance();
        for(Iterator i$ = list.iterator(); i$.hasNext();)
        {
            Employee emp = (Employee)i$.next();
            try
            {
                Map map = new HashMap();
                map.put("orgid", emp.getId());
                map.put("userid", emp.getId());
                map.put("username", emp.getUserName());
                map.put("orgname", emp.getEmpName());
                map.put("ydsbh", StringUtil.safeToString(emp.getOtherProperty("ydsbh"), ""));
                if(!StringUtil.safeToString(emp.getEmpName(), "").isEmpty())
                    map.put("pinyin", pinYinHelper.getSimplePinYin(emp.getEmpName()));
                map.put("sex", Integer.valueOf(emp.getSex()));
                String ml = "";
                String mail = StringUtil.safeToString(emp.getEmail(), "");
                if(!mail.isEmpty())
                    ml = mail;
                String mail1 = StringUtil.safeToString(emp.getEmail1(), "");
                if(!mail1.isEmpty())
                    ml = (new StringBuilder()).append(ml).append(ml.isEmpty() ? "" : ",").append(mail1).toString();
                String mail2 = StringUtil.safeToString(emp.getEmail2(), "");
                if(!mail2.isEmpty())
                    ml = (new StringBuilder()).append(ml).append(ml.isEmpty() ? "" : ",").append(mail2).toString();
                map.put("email", ml);
                String tl = "";
                String tel = StringUtil.safeToString(emp.getTel(), "");
                String tel2 = StringUtil.safeToString(emp.getTel2(), "");
                if(!tel.isEmpty())
                    tl = tel;
                if(!tel2.isEmpty())
                    tl = (new StringBuilder()).append(tl).append(tl.isEmpty() ? "" : ",").append(tel2).toString();
                map.put("telphone", tl);
                String mb = "";
                String mobilephone = StringUtil.safeToString(emp.getMobilePhone(), "");
                if(!mobilephone.isEmpty())
                    mb = (new StringBuilder()).append(mb.isEmpty() ? "" : ",").append(mobilephone).toString();
                String mobilephone2 = StringUtil.safeToString(emp.getMobilephone2(), "");
                if(!mobilephone2.isEmpty())
                    mb = (new StringBuilder()).append(mb).append(mb.isEmpty() ? "" : ",").append(mobilephone2).toString();
                map.put("mobilephone", mb);
                String fx = "";
                String fax = StringUtil.safeToString(emp.getFax(), "");
                if(!fax.isEmpty())
                    fx = (new StringBuilder()).append(fx).append(fax).toString();
                String fax2 = StringUtil.safeToString(emp.getFax2(), "");
                if(!fax2.isEmpty())
                    fx = (new StringBuilder()).append(fx).append(fx.isEmpty() ? "" : ",").append(fax2).toString();
                map.put("fax", fx);
                map.put("password", StringUtil.safeToString(orgService.getPwdByEmpUserId(emp.getUserId()), ""));
                OrgInfo orgInfo = orgService.findOrgInfoByEmpId(emp.getId());
                if(orgInfo != null)
                {
                    String positionname[] = "".equals(orgInfo.getPositionname()) ? null : orgInfo.getPositionname().split(",");
                    String unitname[] = "".equals(orgInfo.getUnitname()) ? null : orgInfo.getUnitname().split(",");
                    StringBuilder postunit = new StringBuilder();
                    if(positionname != null && unitname != null)
                    {
                        int postlength = positionname.length <= unitname.length ? positionname.length : unitname.length;
                        for(int i = 0; i < postlength; i++)
                        {
                            if(!"".equals(postunit.toString()))
                                postunit.append(",");
                            postunit.append((new StringBuilder()).append(unitname[i]).append("/").append(positionname[i]).toString());
                        }

                    }
                    map.put("department", postunit.toString());
                }
                listuser.add(map);
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append("\u89E3\u6790\u7528\u6237").append(emp.getUserId()).append("\u65F6\u51FA\u9519").toString(), e);
            }
        }

        Collections.sort(listuser, new ListComparator("pinyin", "asc"));
        return listuser;
    }

    public int updateEmployeeYdsbh(Map map)
    {
        String orgid = StringUtil.safeToString(map.get("orgid"), "");
        String ydsbh = StringUtil.safeToString(map.get("ydsbh"), "");
        int flag = 0;
        if(!"".equals(orgid) && !"".equals(ydsbh))
        {
            Employee employee = OrgServiceFactory.getOrgService().getEmployeeById(orgid);
            if(employee != null)
            {
                String querySql = (new StringBuilder()).append("select * from um_userproperty where name='ydsbh1' and userid='").append(employee.getUserId()).append("' ").toString();
                IMetaDBQuery query = getMetaDBContext().createSqlQuery(querySql);
                List list = null;
                if(query != null)
                    list = query.getResult();
                if(list != null && list.size() > 0)
                {
                    String sql = (new StringBuilder()).append("update um_userproperty set value='").append(ydsbh).append("' where name='ydsbh1' and userid='").append(employee.getUserId()).append("' ").toString();
                    ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sql);
                    updater.executeUpdate();
                    flag = 1;
                } else
                {
                    String insertSql = (new StringBuilder()).append("insert into um_userproperty values((select max(objid)+1 from um_userproperty),'").append(employee.getUserId()).append("','ydsbh1','").append(ydsbh).append("',sysdate,sysdate,0,'USERPROPERTY')").toString();
                    ISQLUpdater insert = getMetaDBContext().createSqlUpdater(insertSql);
                    insert.executeUpdate();
                    String uSql = "update METADB_IDGENERATOR set CURRENTVALUE = (select max(objid) from um_userproperty) where ENTITYNAME = 'USERPROPERTY' ";
                    ISQLUpdater isqlUpdater = getMetaDBContext().createSqlUpdater(uSql);
                    isqlUpdater.executeUpdate();
                    flag = 1;
                }
                if(flag == 1)
                {
                    IMetaDBCacheContext context = MetaDBContextFactory.getInstance().getCacheContext();
                    context.notifyReload(new String[] {
                        "USERPROPERTY"
                    });
                }
            }
        }
        return flag;
    }

    static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/service/impl/OrgMobileServiceImpl);

}
