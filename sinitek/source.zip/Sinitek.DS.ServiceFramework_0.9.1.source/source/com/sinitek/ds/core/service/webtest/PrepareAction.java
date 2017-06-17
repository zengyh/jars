// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrepareAction.java

package com.sinitek.ds.core.service.webtest;

import com.sinitek.ds.core.service.client.ejb.EjbServiceContextProxy;
import com.sinitek.ds.core.service.config.ServiceConfigReader;
import com.sinitek.ds.core.service.impl.local.ServiceContextFactory;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

// Referenced classes of package com.sinitek.ds.core.service.webtest:
//            PrepareForm

public class PrepareAction extends DispatchAction
{

    public PrepareAction()
    {
    }

    public ActionForward selectCallType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        PrepareForm prepareForm = (PrepareForm)form;
        if("ejb".equalsIgnoreCase(prepareForm.getCallType()))
        {
            String serverType = prepareForm.getServerType();
            Properties properties = new Properties();
            if(serverType.equalsIgnoreCase("weblogic"))
                properties.setProperty("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            else
            if(serverType.equalsIgnoreCase("websphere"))
                properties.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
            com.sinitek.ds.core.service.IServiceContext proxyContext = new EjbServiceContextProxy(prepareForm.getJndi(), prepareForm.getRemoteUrl(), properties, 20, false);
            request.getSession().setAttribute("context", proxyContext);
            File webInfoDir = new File(request.getSession().getServletContext().getRealPath("/WEB-INF/"));
            File configs[] = webInfoDir.listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name)
                {
                    return name.toLowerCase().endsWith(".xml") && !name.toLowerCase().startsWith("web.") && !name.toLowerCase().startsWith("struts");
                }

                final PrepareAction this$0;

            
            {
                this$0 = PrepareAction.this;
                super();
            }
            }
);
            List fileNames = new ArrayList(configs.length);
            for(int i = 0; i < configs.length; i++)
            {
                File config = configs[i];
                fileNames.add(config.getName());
            }

            Collections.sort(fileNames, String.CASE_INSENSITIVE_ORDER);
            request.setAttribute("fileNames", fileNames);
            return mapping.findForward("selectConfigFile");
        } else
        {
            request.getSession().setAttribute("context", ServiceContextFactory.getInstance().getContext());
            request.getSession().setAttribute("config", readConfigFile(Thread.currentThread().getContextClassLoader().getResource("servicecoreconfig.xml")));
            return mapping.findForward("success");
        }
    }

    public ActionForward selectConfigFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        PrepareForm pForm = (PrepareForm)form;
        String fileName = pForm.getConfigFile();
        String realPath = request.getSession().getServletContext().getRealPath((new StringBuilder()).append("/WEB-INF/").append(fileName).toString());
        File temp = new File(realPath);
        URL url = temp.toURL();
        request.getSession().setAttribute("config", readConfigFile(url));
        return mapping.findForward("success");
    }

    private Map readConfigFile(URL url)
    {
        Map map = ServiceConfigReader.readServiceConfig(url);
        Map ret = new TreeMap();
        String key;
        for(Iterator iter = map.keySet().iterator(); iter.hasNext(); ret.put(key, map.get(key)))
            key = (String)iter.next();

        return ret;
    }
}
