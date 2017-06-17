// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgUtils.java

package com.sinitek.sirm.org.utils;

import com.sinitek.sirm.common.support.datasyn.DataSynUtil;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.org.client.OrgFactory;
import com.sinitek.spirit.org.core.*;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.IRightUpdater;
import com.sinitek.spirit.um.NoSuchUserException;
import com.sinitek.spirit.um.client.UMClient;
import com.sinitek.spirit.um.client.UMFactory;
import com.sinitek.spirit.um.server.UserDatabase;
import com.sinitek.spirit.um.server.userdb.UserDatabaseFactory;
import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.IOUtil;

public class OrgUtils
{

    public OrgUtils()
    {
    }

    public static Map getMapByEmp(Employee employee)
    {
        Map param = new HashMap();
        if(employee != null)
            param = employee.getAllUserProperties();
        return param;
    }

    public static void copyUserProperties(Employee employee, IOrgUserExtendInfo extendInfo, boolean flag)
    {
        if(employee != null && extendInfo != null)
            if(flag)
            {
                extendInfo.setSex(NumberTool.safeToInteger(Integer.valueOf(employee.getSex()), Integer.valueOf(0)));
                extendInfo.setTel(StringUtil.safeToString(employee.getTel(), ""));
                extendInfo.setTel2(StringUtil.safeToString(employee.getTel2(), ""));
                extendInfo.setJob(StringUtil.safeToString(employee.getJob(), ""));
                extendInfo.setEmail(StringUtil.safeToString(employee.getEmail(), ""));
                extendInfo.setUserid(StringUtil.safeToString(employee.getUserId(), ""));
                extendInfo.setMobilephone2(StringUtil.safeToString(employee.getMobilephone2(), ""));
                extendInfo.setFamilytelephone(StringUtil.safeToString(employee.getFamilytelephone(), ""));
                extendInfo.setFamilytelephone2(StringUtil.safeToString(employee.getFamilytelephone2(), ""));
                extendInfo.setOtherphone(StringUtil.safeToString(employee.getOtherphone(), ""));
                extendInfo.setOtherphone2(StringUtil.safeToString(employee.getOtherphone2(), ""));
                extendInfo.setBp(StringUtil.safeToString(employee.getBp(), ""));
                extendInfo.setOffice(StringUtil.safeToString(employee.getOffice(), ""));
                extendInfo.setFax(StringUtil.safeToString(employee.getFax(), ""));
                extendInfo.setFax2(StringUtil.safeToString(employee.getFax2(), ""));
                extendInfo.setFamilyfax(StringUtil.safeToString(employee.getFamilyfax(), ""));
                extendInfo.setFamilyfax2(StringUtil.safeToString(employee.getFamilyfax2(), ""));
                extendInfo.setCompanyaddress(StringUtil.safeToString(employee.getCompanyaddress(), ""));
                extendInfo.setCompanyzip(StringUtil.safeToString(employee.getCompanyzip(), ""));
                extendInfo.setFamilyaddress(StringUtil.safeToString(employee.getFamilyaddress(), ""));
                extendInfo.setFamilyzip(StringUtil.safeToString(employee.getFamilyzip(), ""));
                extendInfo.setOtheraddress(StringUtil.safeToString(employee.getOtheraddress(), ""));
                extendInfo.setOtherzip(StringUtil.safeToString(employee.getOtherzip(), ""));
                extendInfo.setEmail1(StringUtil.safeToString(employee.getEmail1(), ""));
                extendInfo.setEmail2(StringUtil.safeToString(employee.getEmail2(), ""));
                extendInfo.setHomepage(StringUtil.safeToString(employee.getHomepage(), ""));
                extendInfo.setWhere1(NumberTool.safeToInteger(employee.getWhere(), Integer.valueOf(0)));
                extendInfo.setQq(StringUtil.safeToString(employee.getQq(), ""));
                extendInfo.setMsn(StringUtil.safeToString(employee.getMsn(), ""));
                extendInfo.setAddressbook(StringUtil.safeToString(employee.getAddressbook(), ""));
                extendInfo.setIntroduction(StringUtil.safeToString(employee.getIntroduction(), ""));
                extendInfo.setPasswordUpdateTime(StringUtil.safeToString(employee.getPasswordUpdateTime(), ""));
                extendInfo.setUserLockTime(StringUtil.safeToString(employee.getUserLockTime(), ""));
                extendInfo.setPostid(StringUtil.safeToString(employee.getPostid(), ""));
                extendInfo.setQualifyno(StringUtil.safeToString(employee.getQualifyno(), ""));
                extendInfo.setQualifytype(NumberTool.safeToInteger(Integer.valueOf(employee.getQualifytype()), Integer.valueOf(0)));
                extendInfo.setNamePy(StringUtil.safeToString(employee.getNamePy(), ""));
                extendInfo.setMobilePhone(StringUtil.safeToString(employee.getMobilePhone(), ""));
                extendInfo.setLzrq(StringUtil.safeToString(employee.getLzrq(), ""));
                extendInfo.setRzrq(StringUtil.safeToString(employee.getRzrq(), ""));
            } else
            {
                employee.setSex(NumberTool.safeToInteger(extendInfo.getSex(), Integer.valueOf(0)).intValue());
                employee.setTel(StringUtil.safeToString(extendInfo.getTel(), ""));
                employee.setTel2(StringUtil.safeToString(extendInfo.getTel2(), ""));
                employee.setJob(StringUtil.safeToString(extendInfo.getJob(), ""));
                employee.setEmail(StringUtil.safeToString(extendInfo.getEmail(), ""));
                employee.setUserId(StringUtil.safeToString(extendInfo.getUserid(), ""));
                employee.setMobilephone2(StringUtil.safeToString(extendInfo.getMobilephone2(), ""));
                employee.setFamilytelephone(StringUtil.safeToString(extendInfo.getFamilytelephone(), ""));
                employee.setFamilytelephone2(StringUtil.safeToString(extendInfo.getFamilytelephone2(), ""));
                employee.setOtherphone(StringUtil.safeToString(extendInfo.getOtherphone(), ""));
                employee.setOtherphone2(StringUtil.safeToString(extendInfo.getOtherphone2(), ""));
                employee.setBp(StringUtil.safeToString(extendInfo.getBp(), ""));
                employee.setOffice(StringUtil.safeToString(extendInfo.getOffice(), ""));
                employee.setFax(StringUtil.safeToString(extendInfo.getFax(), ""));
                employee.setFax2(StringUtil.safeToString(extendInfo.getFax2(), ""));
                employee.setFamilyfax(StringUtil.safeToString(extendInfo.getFamilyfax(), ""));
                employee.setFamilyfax2(StringUtil.safeToString(extendInfo.getFamilyfax2(), ""));
                employee.setCompanyaddress(StringUtil.safeToString(extendInfo.getCompanyaddress(), ""));
                employee.setCompanyzip(StringUtil.safeToString(extendInfo.getCompanyzip(), ""));
                employee.setFamilyaddress(StringUtil.safeToString(extendInfo.getFamilyaddress(), ""));
                employee.setFamilyzip(StringUtil.safeToString(extendInfo.getFamilyzip(), ""));
                employee.setOtheraddress(StringUtil.safeToString(extendInfo.getOtheraddress(), ""));
                employee.setOtherzip(StringUtil.safeToString(extendInfo.getOtherzip(), ""));
                employee.setEmail1(StringUtil.safeToString(extendInfo.getEmail1(), ""));
                employee.setEmail2(StringUtil.safeToString(extendInfo.getEmail2(), ""));
                employee.setHomepage(StringUtil.safeToString(extendInfo.getHomepage(), ""));
                employee.setWhere(StringUtil.safeToString(extendInfo.getWhere1(), ""));
                employee.setQq(StringUtil.safeToString(extendInfo.getQq(), ""));
                employee.setMsn(StringUtil.safeToString(extendInfo.getMsn(), ""));
                employee.setAddressbook(StringUtil.safeToString(extendInfo.getAddressbook(), ""));
                employee.setIntroduction(StringUtil.safeToString(extendInfo.getIntroduction(), ""));
                employee.setPasswordUpdateTime(StringUtil.safeToString(extendInfo.getPasswordUpdateTime(), ""));
                employee.setUserLockTime(StringUtil.safeToString(extendInfo.getUserLockTime(), ""));
                employee.setPostid(StringUtil.safeToString(extendInfo.getPostid(), ""));
                employee.setQualifyno(StringUtil.safeToString(extendInfo.getQualifyno(), ""));
                employee.setQualifytype(NumberTool.safeToInteger(extendInfo.getQualifytype(), Integer.valueOf(0)).intValue());
                employee.setNamePy(StringUtil.safeToString(extendInfo.getNamePy(), ""));
                employee.setMobilePhone(StringUtil.safeToString(extendInfo.getMobilePhone(), ""));
                employee.setLzrq(StringUtil.safeToString(extendInfo.getLzrq(), ""));
                employee.setRzrq(StringUtil.safeToString(extendInfo.getRzrq(), ""));
            }
    }

    public static Employee getEmployeeByMap(Map _map)
    {
        Employee result = Employee.instanceFromMap(_map);
        return result;
    }

    public static boolean searchRepeatName(IOrgObject _object, String _objname, String _objid, String type)
    {
label0:
        {
            IOrgFinder _finder = OrgFactory.getOrgFinder();
            if(_object == null)
                break label0;
            List _orglist = _finder.findOrgObjectsByPath(_object, (new StringBuilder()).append("DOWN(orgname='").append(_objname).append("'").append("".equals(type) ? "" : (new StringBuilder()).append(";orgtype='").append(type).append("'").toString()).append(")").toString());
            if(_orglist == null || _orglist.size() <= 0 || _objid != null && !"".equals(_objid))
                break label0;
            if(_orglist.size() > 0)
                return true;
            Iterator i$ = _orglist.iterator();
            IOrgObject _org;
            do
            {
                if(!i$.hasNext())
                    break label0;
                _org = (IOrgObject)i$.next();
            } while(_objid.equals(_org.getId()));
            return true;
        }
        return false;
    }

    public static void lockUser(String username)
        throws NoSuchUserException
    {
        UMClient client = UMFactory.getUMClient();
        String userid = client.getUserByName(username);
        UserDatabaseFactory.getUserDatabase().lockUser(userid);
    }

    public static void deleteOrgStructure(String orgid)
        throws Exception
    {
        IOrgUpdater _updater = OrgFactory.getOrgUpdater();
        IOrgFinder _finder = OrgFactory.getOrgFinder();
        IOrgService orgService = OrgServiceFactory.getOrgService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        String _id = orgid;
        IOrgObject _object = _finder.getOrgObject(_id);
        String _objectType = ((IUnit)_object).getUnitType();
        if(_objectType.equals(com.sinitek.spirit.org.core.IUnit.UnitType.TEAM.toString()))
        {
            List list = new ArrayList();
            _updater.deleteObject(_object, true);
            orgService.delIndustryRelaByTeamId(_id);
            list.add(_id);
            _updater.flush();
            DataSynUtil.sendData("OrgService", "deleteOrgObject", list);
        } else
        if(_objectType.equals(com.sinitek.spirit.org.core.IUnit.UnitType.POSITION.toString()))
        {
            List list = new ArrayList();
            _updater.deleteObject(_object, true);
            list.add(_id);
            if(_object != null)
            {
                RightAuthInfoBean _info = new RightAuthInfoBean();
                _info.setAuthOrgObjectpId(_object.getId());
                _rightUpdater.deleteRightAuth(_info);
            }
            _updater.flush();
            DataSynUtil.sendData("OrgService", "deleteOrgObject", list);
        } else
        if(_objectType.equals(com.sinitek.spirit.org.core.IUnit.UnitType.UNIT.toString()))
        {
            orgService.deleteDepartmentById(_id);
            List list = new ArrayList();
            orgService.deleteDepartmentById(_id);
            list.add(_id);
            DataSynUtil.sendData("OrgService", "deleteOrgObject", list);
        }
    }

    public static String renamePhotoFile(Employee employee, String filefullname)
    {
        String abdirpath;
        String path;
        FileInputStream inputStream;
        FileOutputStream outputStream;
        if(null == filefullname || filefullname.isEmpty())
            return "";
        String prjdir = com/sinitek/sirm/org/utils/OrgUtils.getResource("/").getPath();
        File classdir = new File(prjdir);
        abdirpath = classdir.getParentFile().getParentFile().getAbsolutePath();
        path = "";
        inputStream = null;
        outputStream = null;
        File file;
        String s;
        file = new File((new StringBuilder()).append(abdirpath).append("/").append(filefullname).toString());
        if(file.exists())
            break MISSING_BLOCK_LABEL_143;
        s = "";
        try
        {
            if(null != outputStream)
                outputStream.close();
            if(null != inputStream)
                inputStream.close();
        }
        catch(Exception e)
        {
            LOGGER.error("\u5173\u95ED\u9519\u8BEF", e);
        }
        return s;
        inputStream = new FileInputStream(file);
        file.delete();
        int a = filefullname.lastIndexOf(File.separator);
        String filedir = file.getParent();
        String newurl = (new StringBuilder()).append(filedir).append("/").append(employee.getId()).append(filefullname.substring(filefullname.lastIndexOf("."))).toString();
        File newfile = new File(newurl);
        outputStream = new FileOutputStream(newfile);
        IOUtil.copyCompletely(inputStream, outputStream);
        path = newfile.getPath().substring(abdirpath.length());
        Exception e;
        try
        {
            if(null != outputStream)
                outputStream.close();
            if(null != inputStream)
                inputStream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception e)
        {
            LOGGER.error("\u5173\u95ED\u9519\u8BEF", e);
        }
        break MISSING_BLOCK_LABEL_401;
        e;
        LOGGER.error("\u56FE\u7247\u5904\u7406\u9519\u8BEF", e);
        e.printStackTrace();
        try
        {
            if(null != outputStream)
                outputStream.close();
            if(null != inputStream)
                inputStream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception e)
        {
            LOGGER.error("\u5173\u95ED\u9519\u8BEF", e);
        }
        break MISSING_BLOCK_LABEL_401;
        Exception exception;
        exception;
        try
        {
            if(null != outputStream)
                outputStream.close();
            if(null != inputStream)
                inputStream.close();
        }
        catch(Exception e)
        {
            LOGGER.error("\u5173\u95ED\u9519\u8BEF", e);
        }
        throw exception;
        return path;
    }

    public static String getEmpName(String empname, int inservice)
    {
        if(empname != null)
            empname = empname.replace("(\u79BB\u804C)", "");
        if(inservice == 0)
            empname = (new StringBuilder()).append(empname).append("(\u79BB\u804C)").toString();
        return empname;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/utils/OrgUtils);

}
