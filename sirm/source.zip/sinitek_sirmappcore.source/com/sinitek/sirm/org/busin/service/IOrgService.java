// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.spirit.org.core.IUnit;
import com.sinitek.spirit.org.server.entity.IOrgRelationInfo;
import com.sinitek.spirit.um.*;
import java.util.List;
import java.util.Map;

public interface IOrgService
{

    public abstract List findAllEmployees();

    public abstract List findAllEmployees(Map map);

    public abstract List findAllEmployeesOfMap(Map map);

    public abstract List findAllEmployeesOfParam(Map map);

    public abstract Employee getEmployeeByUserId(String s);

    public abstract Employee getEmployeeByUserName(String s);

    public abstract List getEmployeeByEmpName(String s);

    public abstract List getEmployeeInserviceByEmpName(String s);

    public abstract void insertEmployee(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException;

    public abstract void updateEmployee(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException;

    public abstract void updatePassword(String s, String s1)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract List findUserIdsByUnitId(String s);

    public abstract List findUserIdsInserviceByUnitId(String s);

    public abstract List findEmpsByUnitId(String s);

    public abstract List findEmpsInserviceByUnitId(String s);

    public abstract void deleteUserByUserId(String s)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract Employee getEmployeeById(String s);

    public abstract Position getPositionById(String s);

    public abstract Team getTeamById(String s);

    public abstract List findTeamerById(String s);

    public abstract List findTeamerInserviceById(String s);

    public abstract Department getDepartmentById(String s);

    public abstract void deleteDepartmentById(String s);

    public abstract List findEmployeeByPosId(String s);

    public abstract List findEmployeeInserviceByPosId(String s);

    public abstract List findEmployeeByUnitId(String s);

    public abstract List findEmployeeInserviceByUnitId(String s);

    public abstract List findPositionsByUnitId(String s);

    public abstract List findUnitByParentId(String s);

    public abstract OrgObject getOrgObjectById(String s);

    public abstract OrgInfo findOrgInfoByEmpId(String s);

    public abstract List findUnallocatedEmployee();

    public abstract List findUnallocatedEmployeeInservice();

    public abstract List findPositionsByEmpId(String s);

    public abstract List findEmployeesByOrgTypeOrgId(int i, String s);

    public abstract List findEmployeesInserviceByOrgTypeOrgId(int i, String s);

    public abstract List findEmployeesByOrgId(String s);

    public abstract List findEmployeesInserviceByOrgId(String s);

    public abstract List findEmployeeByTeamId(String s);

    public abstract List findEmployeeInserviceByTeamId(String s);

    public abstract List findUnitsByEmpId(String s);

    public abstract List findTeamsByEmpId(String s);

    public abstract Department getParentDepartmentById(String s);

    public abstract Department getDepartmentByPositionId(String s);

    public abstract List findOnlineUsers();

    public abstract List findAllRole();

    public abstract Role getRoleById(String s);

    public abstract List findEmployeeByRoleId(String s);

    public abstract List findEmployeeInserviceByRoleId(String s);

    public abstract List findRoleByEmpId(String s);

    public abstract List findAllQualifyInfoByEmpId(String s);

    public abstract IQualifyInfo getQualifyInfoBySrcid(String s, String s1);

    public abstract void deleteQualifyInfo(int i);

    public abstract void deleteQualifyByOrgId(String s);

    public abstract void saveQualify(IQualifyInfo iqualifyinfo);

    public abstract List findAllOrgByEmpId(String s);

    public abstract List findAllPosParentByEmpId(String s, String s1);

    public abstract List findAllPosParentInserviceByEmpId(String s, String s1);

    public abstract List findPosParentByEmpId(String s, String s1);

    public abstract List findAllPosChildrenByEmpId(String s, String s1);

    public abstract List findAllPosChildrenByEmpIdInservice(String s, String s1);

    public abstract boolean checkPosParentByEmpId(String s, String s1);

    public abstract Department addDepartment(Department department, IUnit iunit);

    public abstract Department updateDepartment(Department department, IUnit iunit);

    public abstract List findParentOrgById(String s);

    public abstract String getPwdByEmpUserId(String s);

    public abstract List getTeamerByScode(String s);

    public abstract List getTeamerInserviceByScode(String s);

    public abstract List findIndustryRelaByTeamId(String s);

    public abstract List findIndustryRelaByIndustryCode(String s);

    public abstract void saveIndustryRela(IOrgTeamIndustryRela iorgteamindustryrela);

    public abstract void delIndustryRelaByTeamId(String s);

    public abstract List findEmpsByTeamer(String s);

    public abstract List findEmpsInserviceByTeamer(String s);

    public abstract boolean checkEmpAndTeamer(String s, String s1);

    public abstract boolean checkEmpAndTeamer(String s, String s1, String s2);

    public abstract List findResearchEmpsByOrgId(String s);

    public abstract List findResearchEmpsInserviceByOrgId(String s);

    public abstract List findOrgObjectsByOrgIds(List list);

    public abstract List getOrgParentIdByOrgId(String s);

    public abstract List findAllOrgParentIdByOrgId(String s);

    public abstract List getRelationtypeByOrgId(String s);

    public abstract List findTeamsByParent(String s);

    public abstract Map getUserinfoByUsernameWithJDBC(String s);

    public abstract Map getUserinfoByJDBC(String s);

    public abstract Map getOrgObjectByJDBC(String s);

    public abstract Employee getEmployeeByOrgidWithoutSession(String s);

    public abstract void insertEmployeeWithoutSession(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException;

    public abstract void updateEmployeeWithoutSession(Employee employee)
        throws NoSuchUserException, AuthenticationFailedException, LockedUserException, InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException;

    public abstract void unlockUserWithoutSession(String s);

    public abstract List searchOrgByName(String s);

    public abstract List getOrgParentByOrgId(String s);

    public abstract List findTeamsByTeamerId(String s);

    public abstract List findTeammatesByTeamInfo(Map map);

    public abstract void deleteSprtObjectRealByObjid(String s);

    public abstract Map getUserinfo(String s);

    public abstract void deleteEmployeeByUnitIdAndEmpId(String s, String s1);

    public abstract List getByEmpName(String s);

    public abstract List getOrgRelationInfo(String s, String s1, String s2);

    public abstract void saveOrgRelationInfo(IOrgRelationInfo iorgrelationinfo);

    public abstract List findUnitsByUnitId(String s);

    public abstract List findAllPositions();

    public abstract Employee getEmployeeByProperty(String s, String s1);

    public abstract IOrgUserExtendInfo getOrgUserExtendInfoByUserId(String s);

    public abstract void saveOrgUserExtendInfo(IOrgUserExtendInfo iorguserextendinfo);

    public abstract List findAllDepartments();

    public abstract List findAllTeams();
}
