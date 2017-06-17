// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractConnectorServlet.java

package com.sinitek.sirm.web.routine.document;

import com.sinitek.sirm.common.web.SirmAction;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elfinder.servlets.ConnectorException;
import org.elfinder.servlets.commands.*;
import org.elfinder.servlets.config.AbstractConnectorConfig;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractConnectorServlet extends SirmAction
{

    public AbstractConnectorServlet()
    {
    }

    protected abstract AbstractConnectorConfig prepareConfig(HttpServletRequest httpservletrequest)
        throws Exception;

    public String connector()
    {
        if(logger.isDebugEnabled())
            logger.debug((new StringBuilder()).append("processing request: ").append(request.getRequestURI()).append(request.getQueryString()).toString());
        response.setCharacterEncoding("UTF-8");
        parseRequest(request, response);
        json = new JSONObject();
        try
        {
            AbstractConnectorConfig config = prepareConfig(request);
            if(config == null)
                throw new Exception("Configuration problem");
            AbstractCommand command = prepareCommand((String)requestParams.get("cmd"), request, response, config);
            try
            {
                command.execute();
            }
            catch(ConnectorException e)
            {
                logger.warn("command returned an error", e);
                putResponse("error", e.getMessage());
            }
            if(command.mustRunInit())
                try
                {
                    command.initCommand();
                }
                catch(ConnectorException e)
                {
                    logger.warn("command returned an error", e);
                    putResponse("error", e.getMessage());
                }
            if(!command.isResponseOutputDone())
            {
                output(response, command.isResponseTextHtml(), json, command.getResponseWriter());
                command.setResponseOutputDone(true);
            }
        }
        catch(Exception e)
        {
            logger.error("Unknown error", e);
            putResponse("error", "Unknown error");
            try
            {
                output(response, false, json, response.getWriter());
            }
            catch(Exception ee)
            {
                logger.error("", ee);
            }
        }
        if(listFileStreams != null)
        {
            for(Iterator i$ = listFileStreams.iterator(); i$.hasNext();)
            {
                ByteArrayOutputStream os = (ByteArrayOutputStream)i$.next();
                try
                {
                    os.close();
                }
                catch(Exception e) { }
            }

        }
        return null;
    }

    protected static void output(HttpServletResponse response, boolean isResponseTextHtml, JSONObject json, PrintWriter responseWriter)
    {
        if(isResponseTextHtml)
            response.setContentType("text/html; charset=UTF-8");
        else
            response.setContentType("application/json; charset=UTF-8");
        try
        {
            json.write(responseWriter);
        }
        catch(Exception e)
        {
            logger.error("", e);
        }
        AbstractCommand.closeWriter(responseWriter);
    }

    protected void parseRequest(HttpServletRequest request, HttpServletResponse response)
    {
        requestParams = new HashMap();
        listFiles = new ArrayList();
        listFileStreams = new ArrayList();
        if(ServletFileUpload.isMultipartContent(request))
        {
            try
            {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(request);
                do
                {
                    if(!iter.hasNext())
                        break;
                    FileItemStream item = iter.next();
                    String name = item.getFieldName();
                    InputStream stream = item.openStream();
                    if(item.isFormField())
                    {
                        requestParams.put(name, Streams.asString(stream));
                    } else
                    {
                        String fileName = item.getName();
                        if(fileName != null && !"".equals(fileName.trim()))
                        {
                            listFiles.add(item);
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            IOUtils.copy(stream, os);
                            listFileStreams.add(os);
                        }
                    }
                } while(true);
            }
            catch(Exception e)
            {
                logger.error("Unexpected error parsing multipart content", e);
            }
        } else
        {
            for(Iterator i$ = request.getParameterMap().keySet().iterator(); i$.hasNext();)
            {
                Object mapKey = i$.next();
                String mapKeyString = (String)mapKey;
                if(mapKeyString.endsWith("[]"))
                {
                    String values[] = request.getParameterValues(mapKeyString);
                    List listeValues = new ArrayList();
                    String arr$[] = values;
                    int len$ = arr$.length;
                    for(int i$ = 0; i$ < len$; i$++)
                    {
                        String value = arr$[i$];
                        listeValues.add(value);
                    }

                    requestParams.put(mapKeyString, listeValues);
                } else
                {
                    String value = request.getParameter(mapKeyString);
                    requestParams.put(mapKeyString, value);
                }
            }

        }
    }

    protected AbstractCommand prepareCommand(String commandStr, HttpServletRequest request, HttpServletResponse response, AbstractConnectorConfig config)
    {
        if(commandStr != null)
            commandStr = commandStr.trim();
        if(commandStr == null && "POST".equals(request.getMethod()))
            putResponse("error", "Data exceeds the maximum allowed size");
        if(!config.isCommandAllowed(commandStr))
            putResponse("error", "Permission denied");
        AbstractCommand command = null;
        if(commandStr != null)
        {
            command = instanciateCommand(commandStr);
            if(command == null)
                putResponse("error", "Unknown command");
        } else
        {
            String current = (String)request.getParameterMap().get("current");
            if(current != null)
                command = new OpenCommand();
            else
                command = new ContentCommand();
        }
        command.setRequest(request);
        command.setResponse(response);
        command.setJson(json);
        command.setRequestParameters(requestParams);
        command.setListFiles(listFiles);
        command.setListFileStreams(listFileStreams);
        command.setConfig(config);
        command.init();
        return command;
    }

    protected AbstractCommand instanciateCommand(String commandName)
    {
        AbstractCommand instance = null;
        try
        {
            Class clazz = getCommandClass(commandName);
            if(clazz != null)
            {
                instance = (AbstractCommand)clazz.newInstance();
                if(instance == null)
                    throw new Exception((new StringBuilder()).append("Command not found : ").append(commandName).toString());
            }
        }
        catch(Exception e)
        {
            logger.error("Could not instanciate connector configuration", e);
        }
        return instance;
    }

    protected Class getCommandClass(String commandName)
    {
        Class clazz = getCommandClassOverride(commandName);
        if(clazz == null)
            clazz = getCommandClassDefault(commandName);
        return clazz;
    }

    protected Class getCommandClassDefault(String commandName)
    {
        String className = (new StringBuilder()).append("com.sinitek.sirm.web.routine.document.").append(StringUtils.capitalize(commandName)).append("Command").toString();
        Class clazz = null;
        try
        {
            clazz = Class.forName(className);
        }
        catch(ClassNotFoundException e) { }
        return clazz;
    }

    protected Class getCommandClassOverride(String commandName)
    {
        String className = (new StringBuilder()).append("com.sinitek.sirm.web.routine.document.").append(StringUtils.capitalize(commandName)).append("CommandOverride").toString();
        Class clazz = null;
        try
        {
            clazz = Class.forName(className);
        }
        catch(ClassNotFoundException e) { }
        return clazz;
    }

    protected void putResponse(String param, Object value)
    {
        try
        {
            json.put(param, value);
        }
        catch(JSONException e)
        {
            logger.error("json write error", e);
        }
    }

    private static Logger logger = Logger.getLogger(com/sinitek/sirm/web/routine/document/AbstractConnectorServlet);
    private JSONObject json;
    private Map requestParams;
    private List listFiles;
    private List listFileStreams;

}
