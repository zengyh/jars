// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallSerivceAction.java

package com.sinitek.ds.core.service.webtest;

import com.sinitek.ds.core.service.IServiceContext;
import com.sinitek.ds.core.service.SimpleServiceRequest;
import com.sinitek.ds.core.service.config.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

// Referenced classes of package com.sinitek.ds.core.service.webtest:
//            CallServiceForm, ParamContainer, CallParamStoreHelper, CallParamInfo

public class CallSerivceAction extends DispatchAction
{

    public CallSerivceAction()
    {
    }

    public ActionForward prepareData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        CallServiceForm csForm = (CallServiceForm)form;
        Map configMap = (Map)request.getSession().getAttribute("config");
        IServiceConfigBean configBean = (IServiceConfigBean)configMap.get(csForm.getServiceName());
        if(configBean == null)
        {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.invalidservicecode");
            errors.add("org.apache.struts.action.GLOBAL_MESSAGE", msg);
            addErrors(request, errors);
            return mapping.findForward("main");
        } else
        {
            csForm.setSaveParam(true);
            request.setAttribute("history", CallParamStoreHelper.getStoredParamMap(request, csForm.getServiceName()));
            request.setAttribute("configBean", configBean);
            request.setAttribute("serviceCode", csForm.getServiceName());
            request.setAttribute("newInput", Boolean.TRUE);
            return mapping.findForward("inputData");
        }
    }

    public ActionForward deleteData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        CallServiceForm csForm = (CallServiceForm)form;
        CallParamStoreHelper.deleteSaveFile(request, csForm.getServiceName(), csForm.getCurrentFileName());
        return prepareData(mapping, form, request, response);
    }

    public ActionForward loadData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        CallServiceForm csForm = (CallServiceForm)form;
        Map configMap = (Map)request.getSession().getAttribute("config");
        IServiceConfigBean configBean = (IServiceConfigBean)configMap.get(csForm.getServiceName());
        if(configBean == null)
        {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.invalidservicecode");
            errors.add("org.apache.struts.action.GLOBAL_MESSAGE", msg);
            addErrors(request, errors);
            return mapping.findForward("main");
        } else
        {
            csForm.setSaveParam(true);
            request.setAttribute("history", CallParamStoreHelper.getStoredParamMap(request, csForm.getServiceName()));
            CallParamInfo info = CallParamStoreHelper.loadCurrent(request, csForm.getServiceName(), csForm.getCurrentFileName());
            csForm.setCallParams(info.getParamMap());
            csForm.setNullMarkParam(info.getNullMarkMap());
            request.setAttribute("hisInfo", info);
            request.setAttribute("configBean", configBean);
            request.setAttribute("serviceCode", csForm.getServiceName());
            request.setAttribute("newInput", Boolean.FALSE);
            return mapping.findForward("inputData");
        }
    }

    public ActionForward call(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        CallServiceForm csForm = (CallServiceForm)form;
        Map configMap = (Map)request.getSession().getAttribute("config");
        IServiceConfigBean configBean = (IServiceConfigBean)configMap.get(csForm.getServiceName());
        Map callParam = transformCallMap(csForm.getCallParams(), configBean);
        IServiceContext context = (IServiceContext)request.getSession().getAttribute("context");
        SimpleServiceRequest serviceReq = new SimpleServiceRequest(csForm.getServiceName());
        for(Iterator iter = callParam.entrySet().iterator(); iter.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            if(csForm.getNullMarkParam().containsKey(entry.getKey()))
                serviceReq.addParam((String)entry.getKey(), null);
            else
                serviceReq.addParam((String)entry.getKey(), entry.getValue());
        }

        ParamContainer container = new ParamContainer();
        container.setInfo(csForm.getInfo());
        container.setNullMarkMap(csForm.getNullMarkParam());
        container.setParamMap(csForm.getCallParams());
        if(csForm.isNewInput() && csForm.isSaveParam())
            CallParamStoreHelper.saveParamMap(request, csForm.getServiceName(), container);
        if(!csForm.isNewInput())
        {
            CallParamInfo info = CallParamStoreHelper.loadCurrent(request, csForm.getServiceName(), csForm.getCurrentFileName());
            if(info != null && (!info.getParamMap().equals(csForm.getCallParams()) || !csForm.getInfo().equals(info.getInfo()) || !csForm.getNullMarkParam().equals(info.getNullMarkMap())))
                if(csForm.isUpdateCurrent())
                    CallParamStoreHelper.saveParamMap(request, csForm.getServiceName(), container, csForm.getCurrentFileName());
                else
                    CallParamStoreHelper.saveParamMap(request, csForm.getServiceName(), container);
        }
        com.sinitek.ds.core.service.IServiceResponse serviceResp = context.handleService(serviceReq);
        request.setAttribute("resp", serviceResp);
        request.setAttribute("serviceName", csForm.getServiceName());
        return mapping.findForward("success");
    }

    private Map transformCallMap(Map inputMap, IServiceConfigBean configBean)
    {
        Map paramMap = new HashMap();
        IServiceParamConfig paramConfigs[] = configBean.getParamConfigs();
        for(int i = 0; i < paramConfigs.length; i++)
        {
            IServiceParamConfig paramConfig = paramConfigs[i];
            paramMap.put(paramConfig.getParamName(), paramConfig);
        }

        Map ret = new HashMap(inputMap.size());
        Iterator iter = inputMap.entrySet().iterator();
        do
        {
            if(!iter.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            String key = (String)entry.getKey();
            IServiceParamConfig paramConfig = (IServiceParamConfig)paramMap.get(key);
            String value = entry.getValue().toString();
            if(paramConfig != null && value.length() != 0)
            {
                if(paramConfig.getParamType() == ServiceParamType.BOOLEAN)
                {
                    if(value.equals("1"))
                        value = "true";
                    else
                    if(value.equals("0"))
                        value = "false";
                    ret.put(key, Boolean.valueOf(value));
                }
                if(paramConfig.getParamType() == ServiceParamType.DATE)
                    ret.put(key, value);
                if(paramConfig.getParamType() == ServiceParamType.DATETIME)
                    ret.put(key, value);
                if(paramConfig.getParamType() == ServiceParamType.DICT)
                    ret.put(key, value);
                if(paramConfig.getParamType() == ServiceParamType.DOUBLE)
                    ret.put(key, Double.valueOf(value));
                if(paramConfig.getParamType() == ServiceParamType.ENUM)
                    ret.put(key, value);
                if(paramConfig.getParamType() == ServiceParamType.FLOAT)
                    ret.put(key, Float.valueOf(value));
                if(paramConfig.getParamType() == ServiceParamType.INTEGER)
                    ret.put(key, Integer.valueOf(value));
                if(paramConfig.getParamType() == ServiceParamType.STRING)
                    ret.put(key, value);
                if(paramConfig.getParamType() == ServiceParamType.TIME)
                    ret.put(key, value);
            }
        } while(true);
        return ret;
    }
}
