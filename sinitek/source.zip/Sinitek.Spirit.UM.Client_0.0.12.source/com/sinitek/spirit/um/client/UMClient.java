// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UMClient.java

package com.sinitek.spirit.um.client;

import com.sinitek.spirit.um.*;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.sinitek.spirit.um.client:
//            UserSession

public interface UMClient
{

    public abstract UserSession getSession();

    public abstract UserSession openSession(String s, String s1)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException;

    public abstract UserSession openSession(String s, String s1, int i)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException;

    public abstract String createUser(String s, String s1, Map map, Map map1)
        throws UserNameConflictException;

    public abstract Set getAllUsers();

    public abstract boolean hasUser(String s);

    public abstract String getUserByName(String s);

    public abstract Set getUsersByNamesLike(String s);

    public abstract Set getUsersByProperty(String s, String s1, boolean flag);

    public abstract Set getUsersByProperties(Map map, boolean flag);

    public abstract Set getUsersByRole(String s);

    public abstract String getUserName(String s)
        throws NoSuchUserException;

    public abstract String getUserProperty(String s, String s1)
        throws NoSuchUserException;

    public abstract Set getUserPropertyNames(String s)
        throws NoSuchUserException;

    public abstract Map getUserProperties(String s)
        throws NoSuchUserException;

    public abstract Map getUserProperties(String s, Set set)
        throws NoSuchUserException;

    public abstract boolean isUserInRole(String s, String s1)
        throws NoSuchUserException;

    public abstract Set getUserRoles(String s)
        throws NoSuchUserException;

    public abstract Set getUserSecurityQuestions(String s)
        throws NoSuchUserException;

    public abstract void resetUserPassword(String s, Map map, String s1)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException;

    public abstract boolean isUserLocked(String s)
        throws NoSuchUserException;

    public abstract Set getOnlineUsers();
}
