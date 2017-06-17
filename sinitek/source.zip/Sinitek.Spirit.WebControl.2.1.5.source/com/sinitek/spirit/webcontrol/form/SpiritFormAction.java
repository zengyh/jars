// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritFormAction.java

package com.sinitek.spirit.webcontrol.form;

import com.sinitek.spirit.webcontrol.utils.FormatUtils;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.io.FileTransfer;

// Referenced classes of package com.sinitek.spirit.webcontrol.form:
//            RequestData

public class SpiritFormAction
{

    public SpiritFormAction()
    {
    }

    public Object callSubmit(String clazz, String methodName, Map map, HttpServletRequest request)
        throws Exception
    {
        try
        {
            Class argsClass[] = {
                java/util/Map, javax/servlet/http/HttpServletRequest
            };
            Class arrayArgsClass[] = {
                com/sinitek/spirit/webcontrol/form/RequestData, javax/servlet/http/HttpServletRequest
            };
            Class _class = Class.forName(clazz);
            Map checkResult = checkMode(_class, methodName, argsClass, arrayArgsClass);
            Method method = (Method)checkResult.get("method");
            Boolean isRequestData = (Boolean)checkResult.get("isRequestData");
            return methodInvoke(_class, method, isRequestData, map, request, null);
        }
        catch(Exception e)
        {
            LOG.error("\u8868\u5355\u63D0\u4EA4\u5931\u8D25", e);
            throw e;
        }
    }

    public Object callFileSubmit(String clazz, String methodName, Map map, String fileKeys[], FileTransfer files[], HttpServletRequest request)
        throws Exception
    {
        try
        {
            Map filesMap = new HashMap();
            for(int i = 0; i < files.length; i++)
            {
                String key = fileKeys[i];
                List fileList = (List)filesMap.get(key);
                if(fileList == null)
                    fileList = new ArrayList();
                fileList.add(files[i]);
                filesMap.put(key, fileList);
            }

            Class argsClass[] = {
                java/util/Map, java/util/Map, javax/servlet/http/HttpServletRequest
            };
            Class arrayArgsClass[] = {
                com/sinitek/spirit/webcontrol/form/RequestData, java/util/Map, javax/servlet/http/HttpServletRequest
            };
            Class _class = Class.forName(clazz);
            Map checkResult = checkMode(_class, methodName, argsClass, arrayArgsClass);
            Method method = (Method)checkResult.get("method");
            Boolean isRequestData = (Boolean)checkResult.get("isRequestData");
            return methodInvoke(_class, method, isRequestData, map, request, filesMap);
        }
        catch(Exception e)
        {
            LOG.error("\u8868\u5355\u63D0\u4EA4\u5931\u8D25", e);
            throw e;
        }
    }

    private Object methodInvoke(Class _class, Method method, Boolean isRequestData, Map map, HttpServletRequest request, Map filesMap)
        throws InvocationTargetException, IllegalAccessException, InstantiationException
    {
        RequestData rd = convertMap(map);
        boolean isVoid = "void".equalsIgnoreCase(method.getReturnType().getName());
        if(Modifier.isStatic(method.getModifiers()))
        {
            if(isRequestData.booleanValue())
            {
                if(filesMap == null)
                {
                    if(isVoid)
                        method.invoke(null, new Object[] {
                            rd, request
                        });
                    else
                        return method.invoke(null, new Object[] {
                            rd, request
                        });
                } else
                if(isVoid)
                    method.invoke(null, new Object[] {
                        rd, filesMap, request
                    });
                else
                    return method.invoke(null, new Object[] {
                        rd, filesMap, request
                    });
            } else
            if(filesMap == null)
            {
                if(isVoid)
                    method.invoke(null, new Object[] {
                        map, request
                    });
                else
                    return method.invoke(null, new Object[] {
                        map, request
                    });
            } else
            if(isVoid)
                method.invoke(null, new Object[] {
                    map, filesMap, request
                });
            else
                return method.invoke(null, new Object[] {
                    map, filesMap, request
                });
        } else
        if(isRequestData.booleanValue())
        {
            if(filesMap == null)
            {
                if(isVoid)
                    method.invoke(_class.newInstance(), new Object[] {
                        rd, request
                    });
                else
                    return method.invoke(_class.newInstance(), new Object[] {
                        rd, request
                    });
            } else
            if(isVoid)
                method.invoke(_class.newInstance(), new Object[] {
                    rd, filesMap, request
                });
            else
                return method.invoke(_class.newInstance(), new Object[] {
                    rd, filesMap, request
                });
        } else
        if(filesMap == null)
        {
            if(isVoid)
                method.invoke(_class.newInstance(), new Object[] {
                    map, request
                });
            else
                return method.invoke(_class.newInstance(), new Object[] {
                    map, request
                });
        } else
        if(isVoid)
            method.invoke(_class.newInstance(), new Object[] {
                map, filesMap, request
            });
        else
            return method.invoke(_class.newInstance(), new Object[] {
                map, filesMap, request
            });
        return null;
    }

    private Map checkMode(Class _class, String methodName, Class argsClass[], Class arrayArgsClass[])
    {
        Boolean isRequestData = Boolean.valueOf(false);
        Map result = new HashMap();
        Method method = null;
        try
        {
            method = _class.getMethod(methodName, arrayArgsClass);
            isRequestData = Boolean.valueOf(true);
        }
        catch(Exception ignored) { }
        if(!isRequestData.booleanValue())
            try
            {
                method = _class.getMethod(methodName, argsClass);
            }
            catch(Exception ignored) { }
        if(method == null)
        {
            throw new RuntimeException((new StringBuilder()).append("\u4E0D\u5B58\u5728\u65B9\u6CD5\u6216\u4F20\u5165\u53C2\u6570\u4E0D\u5339\u914D:").append(method).toString());
        } else
        {
            result.put("isRequestData", isRequestData);
            result.put("method", method);
            return result;
        }
    }

    private RequestData convertMap(Map map)
    {
        RequestData rd = new RequestData();
        String htmlConvertIgnore = (String)map.get("__htmlConvertIgnore");
        String htmlConvertIgnoreArray[] = new String[0];
        if(StringUtils.isNotBlank(htmlConvertIgnore))
            htmlConvertIgnoreArray = htmlConvertIgnore.split(",");
        Iterator i$ = map.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            String key = (String)i$.next();
            String value = (String)map.get(key);
            boolean needConvert = true;
            String arr$[] = htmlConvertIgnoreArray;
            int len$ = arr$.length;
            int i$ = 0;
            do
            {
                if(i$ >= len$)
                    break;
                String s = arr$[i$];
                if(StringUtils.isNotBlank(s) && s.equals(key))
                {
                    needConvert = false;
                    break;
                }
                i$++;
            } while(true);
            if(needConvert && StringUtils.isNotBlank(value))
                map.put(key, FormatUtils.toSafeHtml(value));
        } while(true);
        map.remove("__htmlConvertIgnore");
        rd.setParam(map);
        return rd;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/form/SpiritFormAction);

}
