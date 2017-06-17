// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSession.java

package com.sinitek.spirit.um.client;

import com.sinitek.spirit.um.*;
import java.util.*;

public interface UserSession
{

    public abstract Date getCreationTime()
        throws InvalidSessionException;

    public abstract Date getLastAccessedTime()
        throws InvalidSessionException;

    public abstract int getMaxInactiveInterval()
        throws InvalidSessionException;

    public abstract void setMaxInactiveInterval(int i)
        throws InvalidSessionException;

    public abstract void invalidate();

    public abstract boolean isValid();

    public abstract String getUserId()
        throws InvalidSessionException;

    public abstract void setUserSecurityQuestions(Map map)
        throws InvalidSessionException;

    public abstract String createUser(String s, String s1, boolean flag, Map map, Map map1, Set set)
        throws InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException;

    public abstract void deleteUser(String s)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract void setUserName(String s, String s1)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, UserNameConflictException;

    public abstract void setUserPassword(String s, String s1)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract void setUserProperty(String s, String s1, String s2)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract void setUserProperties(String s, Map map)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract void grantRole(String s, String s1)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract void grantRoles(String s, Set set)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract void revokeRole(String s, String s1)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract void revokeRoles(String s, Set set)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException;

    public abstract void lockUser(String s)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract void unlockUser(String s)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract int invalidateUserSessions(String s)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException;

    public abstract UserSession switchUser(String s)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, LockedUserException;
}
