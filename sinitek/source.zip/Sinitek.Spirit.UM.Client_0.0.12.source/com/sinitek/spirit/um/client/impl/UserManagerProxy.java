// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserManagerProxy.java

package com.sinitek.spirit.um.client.impl;

import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.client.ServiceContextClientFactory;
import com.sinitek.spirit.um.*;
import java.io.IOError;
import java.util.*;
import org.json.*;

public class UserManagerProxy
    implements UserManager
{
    private class InvocationException extends Exception
    {

        public String getExceptionName()
        {
            return exceptionName;
        }

        public String getExceptionMessage()
        {
            return exceptionMessage;
        }

        private String exceptionName;
        private String exceptionMessage;
        final UserManagerProxy this$0;

        public InvocationException(String exceptionName, String exceptionMessage)
        {
            this$0 = UserManagerProxy.this;
            super();
            this.exceptionName = exceptionName;
            this.exceptionMessage = exceptionMessage;
        }
    }


    public UserManagerProxy()
    {
    }

    private JSONArray invokeMethod(String method, JSONObject params)
        throws InvocationException
    {
        IServiceResponse response;
        SimpleServiceRequest request = new SimpleServiceRequest("sprtum_invoke");
        request.addParam("method", method);
        if(params != null)
            request.addParam("params", params.toString());
        IServiceContext serviceContext = ServiceContextClientFactory.getServiceContext();
        response = serviceContext.handleService(request);
        if(!response.isSuccess())
            break MISSING_BLOCK_LABEL_99;
        try
        {
            if(response.getString("return") != null)
                return new JSONArray(response.getString("return"));
        }
        catch(JSONException jsone)
        {
            throw new IOError(jsone);
        }
        return null;
        try
        {
            JSONObject jo = new JSONObject(response.getReturnMessage());
            String exceptionName = jo.getString("exception");
            String exceptionMessage = jo.isNull("message") ? null : jo.getString("message");
            if(exceptionName.endsWith(ILLEGAL_ARGUMENT))
                throw new IllegalArgumentException(exceptionMessage);
            if(exceptionName.endsWith(NULL_POINTER))
                throw new NullPointerException(exceptionMessage);
            if(exceptionName.endsWith(ILLEGAL_STATE))
                throw new IllegalStateException(exceptionMessage);
            else
                throw new InvocationException(exceptionName, exceptionMessage);
        }
        catch(JSONException jsone)
        {
            throw new IOError(new Throwable(response.getReturnMessage()));
        }
    }

    public String openSession(String userName, String password, boolean beginAccess, int keepSignedInPeriod)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException
    {
        JSONObject jo = new JSONObject();
        jo.put("userName", userName);
        jo.put("password", password);
        jo.put("beginAccess", beginAccess);
        jo.put("keepSignedInPeriod", keepSignedInPeriod);
        try
        {
            JSONArray ja = invokeMethod("openSession", jo);
            return ja.getString(0);
        }
        catch(InvocationException ie)
        {
            String exception = ie.getExceptionName();
            String message = ie.getExceptionMessage();
            if(exception.endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(message);
            if(exception.endsWith(LOCKED_USER))
                throw new LockedUserException(message);
            if(exception.endsWith(AUTHENTICATION_FAILED))
                throw new AuthenticationFailedException(message);
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String getNewSession(String oldSessionId, boolean beginAccess)
    {
        JSONObject jo = new JSONObject();
        jo.put("oldSessionId", oldSessionId);
        jo.put("beginAccess", beginAccess);
        try
        {
            JSONArray ja = invokeMethod("getNewSession", jo);
            return ja.isNull(0) ? null : ja.getString(0);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public boolean isSessionValid(String sessionId)
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("isSessionValid", jo);
            return ja.getBoolean(0);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String getUser(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("getUser", jo);
            return ja.getString(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(INVALID_SESSION))
                throw new InvalidSessionException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public void invalidateSession(String sessionId)
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            try
            {
                invokeMethod("invalidateSession", jo);
            }
            catch(InvocationException ie)
            {
                throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void beginSessionAccess(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            try
            {
                invokeMethod("beginSessionAccess", jo);
            }
            catch(InvocationException ie)
            {
                if(ie.getExceptionName().endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(ie.getExceptionMessage());
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void endSessionAccess(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            try
            {
                invokeMethod("endSessionAccess", jo);
            }
            catch(InvocationException ie)
            {
                if(ie.getExceptionName().endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(ie.getExceptionMessage());
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public long getSessionCreationTime(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("getSessionCreationTime", jo);
            return ja.getLong(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(INVALID_SESSION))
                throw new InvalidSessionException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public long getSessionLastAccessedTime(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("getSessionLastAccessedTime", jo);
            return ja.getLong(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(INVALID_SESSION))
                throw new InvalidSessionException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public long getSessionThisAccessedTime(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("getSessionThisAccessedTime", jo);
            return ja.getLong(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(INVALID_SESSION))
                throw new InvalidSessionException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public int getSessionMaxInactiveInterval(String sessionId)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        try
        {
            JSONArray ja = invokeMethod("getSessionMaxInactiveInterval", jo);
            return ja.getInt(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(INVALID_SESSION))
                throw new InvalidSessionException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public void setSessionMaxInactiveInterval(String sessionId, int interval)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("interval", interval);
            try
            {
                invokeMethod("setSessionMaxInactiveInterval", jo);
            }
            catch(InvocationException ie)
            {
                if(ie.getExceptionName().endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(ie.getExceptionMessage());
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public Set getOnlineUsers()
    {
        try
        {
            JSONArray ja = invokeMethod("getOnlineUsers", null);
            Set onlineUsers = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                onlineUsers.add(ja.getString(i));

            return onlineUsers;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
    }

    public boolean isUserOnline(String userId)
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("isUserOnline", jo);
            return ja.getBoolean(0);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public boolean hasUser(String userId)
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("hasUser", jo);
            return ja.getBoolean(0);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String getUserName(String userId)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("getUserName", jo);
            return ja.getString(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String getUserProperty(String userId, String propertyName)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        jo.put("propertyName", propertyName);
        try
        {
            JSONArray ja = invokeMethod("getUserProperty", jo);
            return ja.isNull(0) ? null : ja.getString(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Map getUserProperties(String userId, Set propertyNames)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        if(propertyNames != null)
            jo.put("propertyNames", propertyNames);
        try
        {
            JSONArray ja = invokeMethod("getUserProperties", jo);
            JSONObject propertiesJO = ja.getJSONObject(0);
            Iterator iterator = propertiesJO.keys();
            Map properties = new HashMap();
            String key;
            for(; iterator.hasNext(); properties.put(key, propertiesJO.getString(key)))
                key = (String)iterator.next();

            return properties;
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUserPropertyNames(String userId)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("getUserPropertyNames", jo);
            Set propertyNames = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                propertyNames.add(ja.getString(i));

            return propertyNames;
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getAllUsers()
    {
        try
        {
            JSONArray ja = invokeMethod("getAllUsers", null);
            Set allUsers = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                allUsers.add(ja.getString(i));

            return allUsers;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
    }

    public String getUserByName(String userName)
    {
        JSONObject jo = new JSONObject();
        jo.put("userName", userName);
        try
        {
            JSONArray ja = invokeMethod("getUserByName", jo);
            return ja.isNull(0) ? null : ja.getString(0);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUsersByNamesLike(String userName)
    {
        JSONObject jo = new JSONObject();
        jo.put("userName", userName);
        try
        {
            JSONArray ja = invokeMethod("getUsersByNamesLike", jo);
            Set users = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                users.add(ja.getString(i));

            return users;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUsersByProperty(String propertyName, String propertyValue, boolean fuzzy)
    {
        JSONObject jo = new JSONObject();
        jo.put("propertyName", propertyName);
        jo.put("propertyValue", propertyValue);
        jo.put("fuzzy", fuzzy);
        try
        {
            JSONArray ja = invokeMethod("getUsersByProperty", jo);
            Set users = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                users.add(ja.getString(i));

            return users;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUsersByProperties(Map properties, boolean fuzzy)
    {
        JSONObject jo = new JSONObject();
        jo.put("properties", properties);
        jo.put("fuzzy", fuzzy);
        try
        {
            JSONArray ja = invokeMethod("getUsersByProperties", jo);
            Set users = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                users.add(ja.getString(i));

            return users;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUsersByRole(String role)
    {
        JSONObject jo = new JSONObject();
        jo.put("role", role);
        try
        {
            JSONArray ja = invokeMethod("getUsersByRole", jo);
            Set users = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                users.add(ja.getString(i));

            return users;
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUserSecurityQuestions(String userName)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userName", userName);
        try
        {
            JSONArray ja = invokeMethod("getUserSecurityQuestions", jo);
            Set securityQuestions = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                securityQuestions.add(ja.getString(i));

            return securityQuestions;
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public boolean isUserInRole(String userId, String role)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        jo.put("role", role);
        try
        {
            JSONArray ja = invokeMethod("isUserInRole", jo);
            return ja.getBoolean(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public Set getUserRoles(String userId)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("getUserRoles", jo);
            Set roles = new HashSet();
            for(int i = 0; i < ja.length(); i++)
                roles.add(ja.getString(i));

            return roles;
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String createUser(String sessionId, String userName, String password, boolean lock, Map properties, Map securityQuestions, Set roles)
        throws InvalidSessionException, InsufficientPrivilegesException, UserNameConflictException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        jo.put("userName", userName);
        jo.put("password", password);
        jo.put("lock", lock);
        if(properties != null)
            jo.put("properties", properties);
        if(securityQuestions != null)
            jo.put("securityQuestions", securityQuestions);
        if(roles != null)
            jo.put("roles", roles);
        try
        {
            JSONArray ja = invokeMethod("createUser", jo);
            return ja.getString(0);
        }
        catch(InvocationException ie)
        {
            String exception = ie.getExceptionName();
            String message = ie.getExceptionMessage();
            if(exception.endsWith(INVALID_SESSION))
                throw new InvalidSessionException(message);
            if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                throw new InsufficientPrivilegesException(message);
            if(exception.endsWith(USER_NAME_CONFLICT))
                throw new UserNameConflictException(message);
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public void deleteUser(String sessionId, String userId)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            try
            {
                invokeMethod("deleteUser", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void setUserName(String sessionId, String userId, String userName)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, UserNameConflictException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("userName", userName);
            try
            {
                invokeMethod("setUserName", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(USER_NAME_CONFLICT))
                    throw new UserNameConflictException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void setUserPassword(String sessionId, String userId, String password)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("password", password);
            try
            {
                invokeMethod("setUserPassword", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void resetUserPassword(String userName, Map securityAnswers, String newPassword)
        throws NoSuchUserException, LockedUserException, AuthenticationFailedException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("userName", userName);
            jo.put("securityAnswers", securityAnswers);
            jo.put("newPassword", newPassword);
            try
            {
                invokeMethod("resetUserPassword", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(LOCKED_USER))
                    throw new LockedUserException(message);
                if(exception.endsWith(AUTHENTICATION_FAILED))
                    throw new AuthenticationFailedException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void setUserSecurityQuestions(String sessionId, Map securityQuestions)
        throws InvalidSessionException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("securityQuestions", securityQuestions);
            try
            {
                invokeMethod("setUserSecurityQuestions", jo);
            }
            catch(InvocationException ie)
            {
                if(ie.getExceptionName().endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(ie.getExceptionMessage());
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void setUserProperty(String sessionId, String userId, String propertyName, String propertyValue)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("propertyName", propertyName);
            jo.put("propertyValue", propertyValue);
            try
            {
                invokeMethod("setUserProperty", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void setUserProperties(String sessionId, String userId, Map properties)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("properties", properties);
            try
            {
                invokeMethod("setUserProperties", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void grantRole(String sessionId, String userId, String role)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("role", role);
            try
            {
                invokeMethod("grantRole", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void grantRoles(String sessionId, String userId, Set roles)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("roles", roles);
            try
            {
                invokeMethod("grantRoles", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void revokeRole(String sessionId, String userId, String role)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("role", role);
            try
            {
                invokeMethod("revokeRole", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void revokeRoles(String sessionId, String userId, Set roles)
        throws InvalidSessionException, NoSuchUserException, InsufficientPrivilegesException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            jo.put("roles", roles);
            try
            {
                invokeMethod("revokeRoles", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void lockUser(String sessionId, String userId)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            try
            {
                invokeMethod("lockUser", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public void unlockUser(String sessionId, String userId)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("sessionId", sessionId);
            jo.put("userId", userId);
            try
            {
                invokeMethod("unlockUser", jo);
            }
            catch(InvocationException ie)
            {
                String exception = ie.getExceptionName();
                String message = ie.getExceptionMessage();
                if(exception.endsWith(INVALID_SESSION))
                    throw new InvalidSessionException(message);
                if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                    throw new InsufficientPrivilegesException(message);
                if(exception.endsWith(NO_SUCH_USER))
                    throw new NoSuchUserException(message);
                else
                    throw new IOError(ie);
            }
        }
        catch(JSONException jsone)
        {
            throw new UnknownError();
        }
    }

    public boolean isUserLocked(String userId)
        throws NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("isUserLocked", jo);
            return ja.getBoolean(0);
        }
        catch(InvocationException ie)
        {
            if(ie.getExceptionName().endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(ie.getExceptionMessage());
            else
                throw new IOError(ie);
        }
        catch(Exception e)
        {
            throw new IOError(e);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public int invalidateUserSessions(String sessionId, String userId)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        jo.put("userId", userId);
        try
        {
            JSONArray ja = invokeMethod("invalidateUserSessions", jo);
            return ja.getInt(0);
        }
        catch(InvocationException ie)
        {
            String exception = ie.getExceptionName();
            String message = ie.getExceptionMessage();
            if(exception.endsWith(INVALID_SESSION))
                throw new InvalidSessionException(message);
            if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                throw new InsufficientPrivilegesException(message);
            if(exception.endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(message);
            else
                throw new IOError(ie);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    public String switchUser(String sessionId, String userId, boolean beginAccess)
        throws InvalidSessionException, InsufficientPrivilegesException, NoSuchUserException, LockedUserException
    {
        JSONObject jo = new JSONObject();
        jo.put("sessionId", sessionId);
        jo.put("userId", userId);
        jo.put("beginAccess", beginAccess);
        try
        {
            JSONArray ja = invokeMethod("switchUser", jo);
            return ja.getString(0);
        }
        catch(InvocationException ie)
        {
            String exception = ie.getExceptionName();
            String message = ie.getExceptionMessage();
            if(exception.endsWith(INVALID_SESSION))
                throw new InvalidSessionException(message);
            if(exception.endsWith(INSUFFICIENT_PRIVILEGES))
                throw new InsufficientPrivilegesException(message);
            if(exception.endsWith(NO_SUCH_USER))
                throw new NoSuchUserException(message);
            if(exception.endsWith(LOCKED_USER))
                throw new LockedUserException(message);
            else
                throw new IOError(ie);
        }
        JSONException jsone;
        jsone;
        throw new UnknownError();
    }

    private static final String AUTHENTICATION_FAILED = com/sinitek/spirit/um/AuthenticationFailedException.getSimpleName();
    private static final String INSUFFICIENT_PRIVILEGES = com/sinitek/spirit/um/InsufficientPrivilegesException.getSimpleName();
    private static final String INVALID_SESSION = com/sinitek/spirit/um/InvalidSessionException.getSimpleName();
    private static final String LOCKED_USER = com/sinitek/spirit/um/LockedUserException.getSimpleName();
    private static final String NO_SUCH_USER = com/sinitek/spirit/um/NoSuchUserException.getSimpleName();
    private static final String USER_NAME_CONFLICT = com/sinitek/spirit/um/UserNameConflictException.getSimpleName();
    private static final String ILLEGAL_ARGUMENT = java/lang/IllegalArgumentException.getSimpleName();
    private static final String NULL_POINTER = java/lang/NullPointerException.getSimpleName();
    private static final String ILLEGAL_STATE = java/lang/IllegalStateException.getSimpleName();

}
