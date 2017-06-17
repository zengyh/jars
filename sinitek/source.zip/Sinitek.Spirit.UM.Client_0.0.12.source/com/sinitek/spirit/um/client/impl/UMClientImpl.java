// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UMClientImpl.java

package com.sinitek.spirit.um.client.impl;

import com.sinitek.spirit.um.*;
import com.sinitek.spirit.um.client.UMClient;
import com.sinitek.spirit.um.client.UserSession;
import com.sinitek.util.ThreadLocalRegister;
import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Referenced classes of package com.sinitek.spirit.um.client.impl:
//            UserManagerProxy, RequestListener

public class UMClientImpl
    implements UMClient, RequestListener
{
    private class UserSessionImpl
        implements UserSession
    {

        public Date getCreationTime()
            throws InvalidSessionException
        {
            return new Date(userManager.getSessionCreationTime(sessionId));
        }

        public Date getLastAccessedTime()
            throws InvalidSessionException
        {
            return new Date(userManager.getSessionLastAccessedTime(sessionId));
        }

        public int getMaxInactiveInterval()
            throws InvalidSessionException
        {
            return userManager.getSessionMaxInactiveInterval(sessionId);
        }

        public void setMaxInactiveInterval(int interval)
            throws InvalidSessionException
        {
            userManager.setSessionMaxInactiveInterval(sessionId, interval);
            if(interval == 0)
                setRequestedSessionId(getRequest(), getResponse(), null, false);
        }

        public void invalidate()
        {
            userManager.invalidateSession(sessionId);
            setRequestedSessionId(getRequest(), getResponse(), null, false);
        }

        public boolean isValid()
        {
            return userManager.isSessionValid(sessionId);
        }

        public String getUserId()
            throws InvalidSessionException
        {
            return userManager.getUser(sessionId);
        }

        public void setUserSecurityQuestions(Map securityQuestions)
            throws InvalidSessionException
        {
            userManager.setUserSecurityQuestions(sessionId, securityQuestions);
        }

        public String createUser(String userName, String password, boolean lock, Map properties, Map securityQuestions, Set roles)
            throws InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
        {
            return userManager.createUser(sessionId, userName, password, lock, properties, securityQuestions, roles);
        }

        public void deleteUser(String userId)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.deleteUser(sessionId, userId);
        }

        public void setUserName(String userId, String userName)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, UserNameConflictException
        {
            userManager.setUserName(sessionId, userId, userName);
        }

        public void setUserPassword(String userId, String password)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.setUserPassword(sessionId, userId, password);
        }

        public void setUserProperty(String userId, String propertyName, String propertyValue)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.setUserProperty(sessionId, userId, propertyName, propertyValue);
        }

        public void setUserProperties(String userId, Map properties)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.setUserProperties(sessionId, userId, properties);
        }

        public void grantRole(String userId, String role)
            throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
        {
            userManager.grantRole(sessionId, userId, role);
        }

        public void grantRoles(String userId, Set roles)
            throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
        {
            userManager.grantRoles(sessionId, userId, roles);
        }

        public void revokeRole(String userId, String role)
            throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
        {
            userManager.revokeRole(sessionId, userId, role);
        }

        public void revokeRoles(String userId, Set roles)
            throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
        {
            userManager.revokeRoles(sessionId, userId, roles);
        }

        public void lockUser(String userId)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.lockUser(sessionId, userId);
        }

        public void unlockUser(String userId)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            userManager.unlockUser(sessionId, userId);
        }

        public int invalidateUserSessions(String userId)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
        {
            return userManager.invalidateUserSessions(sessionId, userId);
        }

        public UserSession switchUser(String userId)
            throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, LockedUserException
        {
            String newSessionId = userManager.switchUser(sessionId, userId, true);
            setRequestedSessionId(getRequest(), getResponse(), newSessionId, false);
            return new UserSessionImpl(newSessionId);
        }

        private String sessionId;
        final UMClientImpl this$0;

        public UserSessionImpl(String sessionId)
        {
            this$0 = UMClientImpl.this;
            super();
            this.sessionId = sessionId;
        }
    }


    private UMClientImpl()
    {
    }

    public static UMClientImpl getInstance()
    {
        return INSTANCE;
    }

    public void requestComingIn(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        ThreadLocalRegister.init();
        setRequest((HttpServletRequest)request);
        setResponse((HttpServletResponse)response);
        String requestedSessionId = getRequestedSessionId((HttpServletRequest)request);
        if(requestedSessionId != null)
            try
            {
                userManager.beginSessionAccess(requestedSessionId);
            }
            catch(InvalidSessionException ise)
            {
                String newSessionId = userManager.getNewSession(requestedSessionId, true);
                setRequestedSessionId((HttpServletRequest)request, (HttpServletResponse)response, newSessionId, true);
            }
    }

    public void requestGoingOut(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        String requestedSessionId = getRequestedSessionId((HttpServletRequest)request);
        if(requestedSessionId != null)
            try
            {
                userManager.endSessionAccess(requestedSessionId);
            }
            catch(InvalidSessionException ise) { }
        ThreadLocalRegister.destroy();
    }

    public UserSession getSession()
    {
        HttpServletRequest request = getRequest();
        String requestedSessionId = getRequestedSessionId(request);
        if(requestedSessionId != null)
        {
            if(userManager.isSessionValid(requestedSessionId))
                return new UserSessionImpl(requestedSessionId);
            String newSessionId = userManager.getNewSession(requestedSessionId, true);
            setRequestedSessionId(request, getResponse(), newSessionId, true);
            if(newSessionId != null)
                return new UserSessionImpl(newSessionId);
        }
        return null;
    }

    public UserSession openSession(String userName, String password)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException
    {
        return openSession(userName, password, 0);
    }

    public UserSession openSession(String userName, String password, int keepSignedInPeriod)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException
    {
        String requestedSessionId = getRequestedSessionId(getRequest());
        if(requestedSessionId != null)
            userManager.invalidateSession(requestedSessionId);
        String sessionId = userManager.openSession(userName, password, true, keepSignedInPeriod);
        setRequestedSessionId(getRequest(), getResponse(), sessionId, keepSignedInPeriod > 0);
        return new UserSessionImpl(sessionId);
    }

    public String createUser(String userName, String password, Map properties, Map securityQuestions)
        throws UserNameConflictException
    {
        try
        {
            return userManager.createUser(null, userName, password, false, properties, securityQuestions, null);
        }
        catch(UMException ume)
        {
            if(ume instanceof UserNameConflictException)
                throw (UserNameConflictException)ume;
            else
                throw new UnsupportedOperationException();
        }
    }

    public Set getAllUsers()
    {
        return userManager.getAllUsers();
    }

    public boolean hasUser(String userId)
    {
        return userManager.hasUser(userId);
    }

    public String getUserByName(String userName)
    {
        return userManager.getUserByName(userName);
    }

    public Set getUsersByNamesLike(String userName)
    {
        return userManager.getUsersByNamesLike(userName);
    }

    public Set getUsersByProperty(String propertyName, String propertyValue, boolean fuzzy)
    {
        return userManager.getUsersByProperty(propertyName, propertyValue, fuzzy);
    }

    public Set getUsersByProperties(Map properties, boolean fuzzy)
    {
        return userManager.getUsersByProperties(properties, fuzzy);
    }

    public Set getUsersByRole(String role)
    {
        return userManager.getUsersByRole(role);
    }

    public String getUserName(String userId)
        throws NoSuchUserException
    {
        return userManager.getUserName(userId);
    }

    public String getUserProperty(String userId, String propertyName)
        throws NoSuchUserException
    {
        return userManager.getUserProperty(userId, propertyName);
    }

    public Map getUserProperties(String userId)
        throws NoSuchUserException
    {
        return getUserProperties(userId, null);
    }

    public Map getUserProperties(String userId, Set propertyNames)
        throws NoSuchUserException
    {
        return userManager.getUserProperties(userId, propertyNames);
    }

    public Set getUserPropertyNames(String userId)
        throws NoSuchUserException
    {
        return userManager.getUserPropertyNames(userId);
    }

    public boolean isUserInRole(String userId, String role)
        throws NoSuchUserException
    {
        return userManager.isUserInRole(userId, role);
    }

    public Set getUserRoles(String userId)
        throws NoSuchUserException
    {
        return userManager.getUserRoles(userId);
    }

    public Set getUserSecurityQuestions(String userName)
        throws NoSuchUserException
    {
        return userManager.getUserSecurityQuestions(userName);
    }

    public void resetUserPassword(String userName, Map securityAnswers, String newPassword)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException
    {
        userManager.resetUserPassword(userName, securityAnswers, newPassword);
    }

    public boolean isUserLocked(String userId)
        throws NoSuchUserException
    {
        return userManager.isUserLocked(userId);
    }

    public Set getOnlineUsers()
    {
        return userManager.getOnlineUsers();
    }

    private void setRequest(HttpServletRequest request)
    {
        ThreadLocalRegister.setAttribute("request", request);
    }

    private HttpServletRequest getRequest()
    {
        return (HttpServletRequest)ThreadLocalRegister.getAttribute("request");
    }

    private void setResponse(HttpServletResponse response)
    {
        ThreadLocalRegister.setAttribute("response", response);
    }

    private HttpServletResponse getResponse()
    {
        return (HttpServletResponse)ThreadLocalRegister.getAttribute("response");
    }

    private Cookie createSessionCookie(String sessionId, boolean persistent)
    {
        Cookie cookie = new Cookie("USESSIONID", sessionId);
        if(persistent)
            cookie.setMaxAge(0x7fffffff);
        else
            cookie.setMaxAge(-1);
        cookie.setPath("/");
        return cookie;
    }

    private Cookie getSessionCookie(HttpServletRequest request)
    {
        Cookie cookies[] = request.getCookies();
        if(cookies == null)
            return null;
        Cookie arr$[] = cookies;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            Cookie cookie = arr$[i$];
            if("USESSIONID".equals(cookie.getName()))
                return cookie;
        }

        return null;
    }

    private void setSessionCookie(HttpServletResponse response, Cookie sessionCookie)
    {
        if(!response.isCommitted())
            response.addCookie(sessionCookie);
    }

    private String getRequestedSessionId(HttpServletRequest request)
    {
        String sessionId = (String)ThreadLocalRegister.getAttribute("sessionId");
        if(sessionId == null)
        {
            Cookie sessionCookie = getSessionCookie(request);
            if(sessionCookie != null)
            {
                sessionId = sessionCookie.getValue();
                ThreadLocalRegister.setAttribute("sessionId", sessionId);
            }
        }
        return sessionId;
    }

    private void setRequestedSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId, boolean persistent)
    {
        if(sessionId != null)
        {
            ThreadLocalRegister.setAttribute("sessionId", sessionId);
            setSessionCookie(response, createSessionCookie(sessionId, persistent));
        } else
        {
            ThreadLocalRegister.removeAttribute("sessionId");
            deleteSessionCookie(request, response);
        }
    }

    private void deleteSessionCookie(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie sessionCookie = getSessionCookie(request);
        if(sessionCookie != null && !response.isCommitted())
        {
            sessionCookie.setMaxAge(0);
            response.addCookie(sessionCookie);
        }
    }

    private static final UMClientImpl INSTANCE = new UMClientImpl();
    private static final String COOKIE_NAME = "USESSIONID";
    private static final String COOKIE_PATH = "/";
    private static final String ATTRIBUTE_REQUEST = "request";
    private static final String ATTRIBUTE_RESPONSE = "response";
    private static final String ATTRIBUTE_SESSIONID = "sessionId";
    private final UserManager userManager = new UserManagerProxy();





}
