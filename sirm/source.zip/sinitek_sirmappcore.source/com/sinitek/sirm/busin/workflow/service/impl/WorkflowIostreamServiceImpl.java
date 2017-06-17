// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowIostreamServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.impl;

import com.sinitek.sirm.busin.workflow.service.IWorkflowIostreamService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

public class WorkflowIostreamServiceImpl extends MetaDBContextSupport
    implements IWorkflowIostreamService
{

    public WorkflowIostreamServiceImpl()
    {
    }

    public Map TxttoMap(String path)
    {
        try
        {
            File file = new File(path);
            if(file.exists())
            {
                InputStream fin = new FileInputStream(path);
                return InputStreamToMap(fin);
            }
        }
        catch(IOException e)
        {
            System.out.println("\u8BFB\u53D6\u5931\u8D25\uFF01");
        }
        catch(Throwable e)
        {
            System.out.println("\u53D1\u751F\u672A\u77E5\u9519\u8BEF\uFF01");
        }
        return new HashMap();
    }

    public Map InputStreamToMap(InputStream fin)
    {
        Map map;
        BufferedReader br;
        map = new HashMap();
        br = null;
        if(fin == null)
            break MISSING_BLOCK_LABEL_7963;
        boolean flag;
        br = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        flag = true;
_L1:
        String r;
        if(!flag)
            break MISSING_BLOCK_LABEL_7963;
        r = br.readLine();
        if(r == null)
            return map;
        if(r.startsWith("<processcode>") && r.endsWith("</processcode>"))
        {
            String value = r.substring(r.indexOf("<processcode>") + 13, r.length() - 14);
            map.put("processcode", value);
            System.out.println(value);
        } else
        if(r.startsWith("<processtype>") && r.endsWith("</processtype>"))
        {
            String value = r.substring(r.indexOf("<processtype>") + 13, r.length() - 14);
            map.put("processtype", value);
            System.out.println(value);
        } else
        {
            if(!r.startsWith("<phoneshow>") || !r.endsWith("</phoneshow>"))
                break MISSING_BLOCK_LABEL_254;
            String value = r.substring(r.indexOf("<phoneshow>") + 11, r.length() - 12);
            map.put("phoneshow", value);
            System.out.println(value);
        }
          goto _L1
        List steplist;
        boolean flag1;
        if(!r.startsWith("<steplist>"))
            break MISSING_BLOCK_LABEL_7814;
        steplist = new ArrayList();
        System.out.println("steplist:");
        flag1 = true;
_L3:
        while(flag1) 
        {
            r = br.readLine();
            if(!r.startsWith("</steplist>"))
                continue; /* Loop/switch isn't completed */
            map.put("steplist", steplist);
            flag1 = false;
        }
          goto _L1
        if(!r.startsWith("<Map>")) goto _L3; else goto _L2
_L2:
        Map steplistmap;
        boolean flag2;
        steplistmap = new HashMap();
        System.out.println("  steplistmap:");
        flag2 = true;
_L7:
        if(!flag2) goto _L3; else goto _L4
_L4:
        r = br.readLine();
        if(!r.startsWith("</Map>")) goto _L6; else goto _L5
_L5:
        steplist.add(steplistmap);
        flag2 = false;
          goto _L7
_L6:
        if(!r.startsWith("<pointtype>") || !r.endsWith("</pointtype>")) goto _L9; else goto _L8
_L8:
        String value = r.substring(r.indexOf("<pointtype>") + 11, r.length() - 12);
        steplistmap.put("pointtype", value);
        System.out.println((new StringBuilder()).append("    ").append(value).toString());
          goto _L7
_L9:
        if(!r.startsWith("<objid>") || !r.endsWith("</objid>")) goto _L11; else goto _L10
_L10:
        String value = r.substring(r.indexOf("<objid>") + 7, r.length() - 8);
        steplistmap.put("objid", value);
        System.out.println((new StringBuilder()).append("    ").append(value).toString());
          goto _L7
_L11:
        if(!r.startsWith("<sort>") || !r.endsWith("</sort>")) goto _L13; else goto _L12
_L12:
        String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
        steplistmap.put("sort", value);
        System.out.println((new StringBuilder()).append("    ").append(value).toString());
          goto _L7
_L13:
        if(!r.startsWith("<condition>") || !r.endsWith("</condition>")) goto _L15; else goto _L14
_L14:
        String value = r.substring(r.indexOf("<condition>") + 11, r.length() - 12);
        steplistmap.put("condition", value);
        System.out.println((new StringBuilder()).append("    ").append(value).toString());
          goto _L7
_L15:
        if(!r.startsWith("<steptypemap>")) goto _L17; else goto _L16
_L16:
        Map steptypemap = new HashMap();
        System.out.println("    steptypemap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                steptypemap.put("sort", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                steptypemap.put("name", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                steptypemap.put("value", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                steptypemap.put("valueads", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                steptypemap.put("type", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                steptypemap.put("key", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("</steptypemap>"))
                steplistmap.put("steptypemap", steptypemap);
            i++;
        }
          goto _L7
_L17:
        if(!r.startsWith("<conditionmap>")) goto _L19; else goto _L18
_L18:
        Map conditionmap = new HashMap();
        System.out.println("    conditionmap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                conditionmap.put("sort", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                conditionmap.put("name", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                conditionmap.put("value", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                conditionmap.put("valueads", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                conditionmap.put("type", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                conditionmap.put("key", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("</conditionmap>"))
                steplistmap.put("conditionmap", conditionmap);
            i++;
        }
          goto _L7
_L19:
        if(!r.startsWith("<pointtypemap>")) goto _L21; else goto _L20
_L20:
        Map pointtypemap = new HashMap();
        System.out.println("    pointtypemap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                pointtypemap.put("sort", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                pointtypemap.put("name", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                pointtypemap.put("value", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                pointtypemap.put("valueads", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                pointtypemap.put("type", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                pointtypemap.put("key", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("</pointtypemap>"))
                steplistmap.put("pointtypemap", pointtypemap);
            i++;
        }
          goto _L7
_L21:
        if(!r.startsWith("<conditionmap>")) goto _L23; else goto _L22
_L22:
        Map conditionmap = new HashMap();
        System.out.println("    conditionmap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                conditionmap.put("sort", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                conditionmap.put("name", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                conditionmap.put("value", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                conditionmap.put("valueads", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                conditionmap.put("type", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                conditionmap.put("key", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("</conditionmap>"))
                steplistmap.put("conditionmap", conditionmap);
            i++;
        }
          goto _L7
_L23:
        if(!r.startsWith("<ownerlist>")) goto _L25; else goto _L24
_L24:
        List ownerlist;
        boolean flag3;
        ownerlist = new ArrayList();
        System.out.println("    ownerlist:");
        flag3 = true;
_L27:
        while(flag3) 
        {
            r = br.readLine();
            if(!r.startsWith("</ownerlist>"))
                continue; /* Loop/switch isn't completed */
            steplistmap.put("ownerlist", ownerlist);
            flag3 = false;
        }
          goto _L7
        if(!r.startsWith("<Map>")) goto _L27; else goto _L26
_L26:
        Map ownerlistmap;
        boolean flag4;
        ownerlistmap = new HashMap();
        System.out.println("    ownerlistmap:");
        flag4 = true;
_L29:
        while(flag4) 
        {
            r = br.readLine();
            if(r.startsWith("</Map>"))
            {
                ownerlist.add(ownerlistmap);
                flag4 = false;
            } else
            if(r.startsWith("<orgtype>") && r.endsWith("</orgtype>"))
            {
                String value = r.substring(r.indexOf("<orgtype>") + 9, r.length() - 10);
                ownerlistmap.put("orgtype", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<ownergoto>") && r.endsWith("</ownergoto>"))
            {
                String value = r.substring(r.indexOf("<ownergoto>") + 11, r.length() - 12);
                ownerlistmap.put("ownergoto", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<orgid>") && r.endsWith("</orgid>"))
            {
                String value = r.substring(r.indexOf("<orgid>") + 7, r.length() - 8);
                ownerlistmap.put("orgid", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                ownerlistmap.put("value", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            {
                if(!r.startsWith("<ownergotoid>") || !r.endsWith("</ownergotoid>"))
                    continue; /* Loop/switch isn't completed */
                String value = r.substring(r.indexOf("<ownergotoid>") + 13, r.length() - 14);
                ownerlistmap.put("ownergotoid", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            }
        }
          goto _L27
        if(!r.startsWith("<orgtypemap>")) goto _L29; else goto _L28
_L28:
        Map orgtypemap = new HashMap();
        System.out.println("      orgtypemap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                orgtypemap.put("sort", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                orgtypemap.put("name", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                orgtypemap.put("value", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                orgtypemap.put("valueads", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                orgtypemap.put("type", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                orgtypemap.put("key", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("</orgtypemap>"))
                ownerlistmap.put("orgtypemap", orgtypemap);
            i++;
        }
          goto _L29
_L25:
        if(!r.startsWith("<ownerlinklist>")) goto _L31; else goto _L30
_L30:
        List ownerlinklist;
        ownerlinklist = new ArrayList();
        System.out.println("    ownerlinklist:");
        flag3 = true;
_L33:
        while(flag3) 
        {
            r = br.readLine();
            if(!r.startsWith("</ownerlinklist>"))
                continue; /* Loop/switch isn't completed */
            steplistmap.put("ownerlinklist", ownerlinklist);
            flag3 = false;
        }
          goto _L7
        if(!r.startsWith("<Map>")) goto _L33; else goto _L32
_L32:
        Map ownerlinklistmap = new HashMap();
        System.out.println("    ownerlinklistmap:");
        flag4 = true;
        while(flag4) 
        {
            r = br.readLine();
            if(r.startsWith("</Map>"))
            {
                ownerlinklist.add(ownerlinklistmap);
                flag4 = false;
            } else
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                ownerlinklistmap.put("sort", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<linkleaf>") && r.endsWith("</linkleaf>"))
            {
                String value = r.substring(r.indexOf("<linkleaf>") + 10, r.length() - 11);
                ownerlinklistmap.put("linkleaf", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<ownerender>") && r.endsWith("</ownerender>"))
            {
                String value = r.substring(r.indexOf("<ownerender>") + 12, r.length() - 13);
                ownerlinklistmap.put("ownerender", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<ownerstarter>") && r.endsWith("</ownerstarter>"))
            {
                String value = r.substring(r.indexOf("<ownerstarter>") + 14, r.length() - 15);
                ownerlinklistmap.put("ownerstarter", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            } else
            if(r.startsWith("<linkroot>") && r.endsWith("</linkroot>"))
            {
                String value = r.substring(r.indexOf("<linkroot>") + 10, r.length() - 11);
                ownerlinklistmap.put("linkroot", value);
                System.out.println((new StringBuilder()).append("      ").append(value).toString());
            }
        }
          goto _L33
_L31:
        List linklist;
        if(!r.startsWith("<linklist>"))
            break MISSING_BLOCK_LABEL_7200;
        linklist = new ArrayList();
        System.out.println("    linklist:");
        flag3 = true;
_L35:
        while(flag3) 
        {
            r = br.readLine();
            if(!r.startsWith("</linklist>"))
                continue; /* Loop/switch isn't completed */
            steplistmap.put("linklist", linklist);
            flag3 = false;
        }
          goto _L7
        if(!r.startsWith("<Map>")) goto _L35; else goto _L34
_L34:
        Map linklistmap;
        linklistmap = new HashMap();
        System.out.println("    linklistmap:");
        flag4 = true;
_L39:
        if(!flag4) goto _L35; else goto _L36
_L36:
        r = br.readLine();
        if(!r.startsWith("</Map>")) goto _L38; else goto _L37
_L37:
        linklist.add(linklistmap);
        flag4 = false;
          goto _L39
_L38:
        if(!r.startsWith("<sort>") || !r.endsWith("</sort>")) goto _L41; else goto _L40
_L40:
        String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
        linklistmap.put("sort", value);
        System.out.println((new StringBuilder()).append("      ").append(value).toString());
          goto _L39
_L41:
        if(!r.startsWith("<iflist>")) goto _L43; else goto _L42
_L42:
        List iflist;
        boolean flag5;
        iflist = new ArrayList();
        System.out.println("      iflist:");
        flag5 = true;
_L45:
        while(flag5) 
        {
            r = br.readLine();
            if(!r.startsWith("</iflist>"))
                continue; /* Loop/switch isn't completed */
            linklistmap.put("iflist", iflist);
            flag5 = false;
        }
          goto _L39
        if(!r.startsWith("<Map>")) goto _L45; else goto _L44
_L44:
        Map iflistmap;
        boolean flag6;
        iflistmap = new HashMap();
        System.out.println("        iflistmap:");
        flag6 = true;
_L47:
        while(flag6) 
        {
            r = br.readLine();
            if(r.startsWith("</Map>"))
            {
                iflist.add(iflistmap);
                flag6 = false;
            } else
            if(r.startsWith("<ifand>") && r.endsWith("</ifand>"))
            {
                String value = r.substring(r.indexOf("<ifand>") + 7, r.length() - 8);
                iflistmap.put("ifand", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            } else
            if(r.startsWith("<iftype>") && r.endsWith("</iftype>"))
            {
                String value = r.substring(r.indexOf("<iftype>") + 8, r.length() - 9);
                iflistmap.put("iftype", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            } else
            {
                if(!r.startsWith("<ifads>") || !r.endsWith("</ifads>"))
                    continue; /* Loop/switch isn't completed */
                String value = r.substring(r.indexOf("<ifads>") + 7, r.length() - 8);
                iflistmap.put("ifads", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            }
        }
          goto _L45
        if(!r.startsWith("<iftypemap>")) goto _L47; else goto _L46
_L46:
        Map iftypemap = new HashMap();
        System.out.println("      iftypemap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                iftypemap.put("sort", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                iftypemap.put("name", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                iftypemap.put("value", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                iftypemap.put("valueads", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                iftypemap.put("type", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                iftypemap.put("key", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("</iftypemap>"))
                iflistmap.put("iftypemap", iftypemap);
            i++;
        }
          goto _L47
_L43:
        if(r.startsWith("<aftstepid>") && r.endsWith("</aftstepid>"))
        {
            String value = r.substring(r.indexOf("<aftstepid>") + 11, r.length() - 12);
            linklistmap.put("aftstepid", value);
            System.out.println((new StringBuilder()).append("      ").append(value).toString());
        } else
        {
            if(!r.startsWith("<prestepid>") || !r.endsWith("</prestepid>"))
                continue; /* Loop/switch isn't completed */
            String value = r.substring(r.indexOf("<prestepid>") + 11, r.length() - 12);
            linklistmap.put("prestepid", value);
            System.out.println((new StringBuilder()).append("      ").append(value).toString());
        }
          goto _L39
        if(!r.startsWith("<dolist>")) goto _L39; else goto _L48
_L48:
        List dolist;
        dolist = new ArrayList();
        System.out.println("      dolist:");
        flag5 = true;
_L50:
        while(flag5) 
        {
            r = br.readLine();
            if(!r.startsWith("</dolist>"))
                continue; /* Loop/switch isn't completed */
            linklistmap.put("dolist", dolist);
            flag5 = false;
        }
          goto _L39
          goto _L35
        if(!r.startsWith("<Map>")) goto _L50; else goto _L49
_L49:
        Map dolistmap;
        dolistmap = new HashMap();
        System.out.println("        dolistmap:");
        flag6 = true;
_L52:
        while(flag6) 
        {
            r = br.readLine();
            if(r.startsWith("</Map>"))
            {
                dolist.add(dolistmap);
                flag6 = false;
            } else
            if(r.startsWith("<doads>") && r.endsWith("</doads>"))
            {
                String value = r.substring(r.indexOf("<doads>") + 7, r.length() - 8);
                dolistmap.put("doads", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            } else
            if(r.startsWith("<domark>") && r.endsWith("</domark>"))
            {
                String value = r.substring(r.indexOf("<domark>") + 8, r.length() - 9);
                dolistmap.put("domark", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            } else
            {
                if(!r.startsWith("<dotype>") || !r.endsWith("</dotype>"))
                    continue; /* Loop/switch isn't completed */
                String value = r.substring(r.indexOf("<dotype>") + 8, r.length() - 9);
                dolistmap.put("dotype", value);
                System.out.println((new StringBuilder()).append("          ").append(value).toString());
            }
        }
          goto _L50
        if(!r.startsWith("<dotypemap>")) goto _L52; else goto _L51
_L51:
        Map dotypemap = new HashMap();
        System.out.println("      dotypemap:");
        int i = 0;
        while(i < 7) 
        {
            r = br.readLine();
            if(r.startsWith("<sort>") && r.endsWith("</sort>"))
            {
                String value = r.substring(r.indexOf("<sort>") + 6, r.length() - 7);
                dotypemap.put("sort", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<name>") && r.endsWith("</name>"))
            {
                String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
                dotypemap.put("name", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<value>") && r.endsWith("</value>"))
            {
                String value = r.substring(r.indexOf("<value>") + 7, r.length() - 8);
                dotypemap.put("value", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<valueads>") && r.endsWith("</valueads>"))
            {
                String value = r.substring(r.indexOf("<valueads>") + 10, r.length() - 11);
                dotypemap.put("valueads", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<type>") && r.endsWith("</type>"))
            {
                String value = r.substring(r.indexOf("<type>") + 6, r.length() - 7);
                dotypemap.put("type", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("<key>") && r.endsWith("</key>"))
            {
                String value = r.substring(r.indexOf("<key>") + 5, r.length() - 6);
                dotypemap.put("key", value);
                System.out.println((new StringBuilder()).append("        ").append(value).toString());
            } else
            if(r.startsWith("</dotypemap>"))
                dolistmap.put("dotypemap", dotypemap);
            i++;
        }
          goto _L52
        if(r.startsWith("<showurl>"))
        {
            String value = r.substring(r.indexOf("<showurl>") + 9, r.length() - 10);
            steplistmap.put("showurl", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<steptypeads>"))
        {
            String value = r.substring(r.indexOf("<steptypeads>") + 13, r.length() - 14);
            steplistmap.put("steptypeads", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<actionurl>"))
        {
            String value = r.substring(r.indexOf("<actionurl>") + 11, r.length() - 12);
            steplistmap.put("actionurl", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<name>"))
        {
            String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
            steplistmap.put("name", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<steptype>"))
        {
            String value = r.substring(r.indexOf("<steptype>") + 10, r.length() - 11);
            steplistmap.put("steptype", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<urlname>"))
        {
            String value = r.substring(r.indexOf("<urlname>") + 9, r.length() - 10);
            steplistmap.put("urlname", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<stepspecial>"))
        {
            String value = r.substring(r.indexOf("<stepspecial>") + 13, r.length() - 14);
            steplistmap.put("stepspecial", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        } else
        if(r.startsWith("<phoneshow>"))
        {
            String value = r.substring(r.indexOf("<phoneshow>") + 11, r.length() - 12);
            steplistmap.put("phoneshow", value);
            System.out.println((new StringBuilder()).append("    ").append(value).toString());
        }
          goto _L7
        if(r.startsWith("<name>") && r.endsWith("</name>"))
        {
            String value = r.substring(r.indexOf("<name>") + 6, r.length() - 7);
            map.put("name", value);
            System.out.println(value);
        } else
        if(r.startsWith("<processbrief>") && r.endsWith("</processbrief>"))
        {
            String value = r.substring(r.indexOf("<processbrief>") + 14, r.length() - 15);
            map.put("processbrief", value);
            System.out.println(value);
        }
          goto _L1
        Exception e;
        e;
        LOGGER.error("\u6570\u636E\u8BFB\u53D6\u5931\u8D25", e);
        return map;
    }

    public void MaptoTxt(Map map, String path)
    {
        StringBuffer sb = new StringBuffer();
        if(map.get("processcode") != null)
            sb.append("<processcode>").append(map.get("processcode").toString()).append("</processcode>").append("\r\n");
        if(map.get("processtype") != null)
            sb.append("<processtype>").append(map.get("processtype").toString()).append("</processtype>").append("\r\n");
        if(map.get("phoneshow") != null)
            sb.append("<phoneshow>").append(map.get("phoneshow").toString()).append("</phoneshow>").append("\r\n");
        if(map.get("steplist") != null)
        {
            sb.append("<steplist>").append("\r\n");
            List steplist = (List)map.get("steplist");
            for(Iterator i$ = steplist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
            {
                Map steplistmap = (Map)i$.next();
                sb.append("<Map>").append("\r\n");
                if(steplistmap.get("pointtype") != null)
                    sb.append("<pointtype>").append(steplistmap.get("pointtype").toString()).append("</pointtype>").append("\r\n");
                if(steplistmap.get("objid") != null)
                    sb.append("<objid>").append(steplistmap.get("objid").toString()).append("</objid>").append("\r\n");
                if(steplistmap.get("sort") != null)
                    sb.append("<sort>").append(steplistmap.get("sort").toString()).append("</sort>").append("\r\n");
                if(steplistmap.get("condition") != null)
                    sb.append("<condition>").append(steplistmap.get("condition").toString()).append("</condition>").append("\r\n");
                if(steplistmap.get("steptypemap") != null)
                {
                    sb.append("<steptypemap>").append("\r\n");
                    Map steptypemap = (Map)steplistmap.get("steptypemap");
                    if(steptypemap.get("sort") != null)
                        sb.append("<sort>").append(steptypemap.get("sort").toString()).append("</sort>").append("\r\n");
                    if(steptypemap.get("name") != null)
                        sb.append("<name>").append(steptypemap.get("name").toString()).append("</name>").append("\r\n");
                    if(steptypemap.get("value") != null)
                        sb.append("<value>").append(steptypemap.get("value").toString()).append("</value>").append("\r\n");
                    if(steptypemap.get("valueads") != null)
                        sb.append("<valueads>").append(steptypemap.get("valueads").toString()).append("</valueads>").append("\r\n");
                    if(steptypemap.get("type") != null)
                        sb.append("<type>").append(steptypemap.get("type").toString()).append("</type>").append("\r\n");
                    if(steptypemap.get("key") != null)
                        sb.append("<key>").append(steptypemap.get("key").toString()).append("</key>").append("\r\n");
                    sb.append("</steptypemap>").append("\r\n");
                }
                if(steplistmap.get("pointtypemap") != null)
                {
                    sb.append("<pointtypemap>").append("\r\n");
                    Map pointtypemap = (Map)steplistmap.get("pointtypemap");
                    if(pointtypemap.get("sort") != null)
                        sb.append("<sort>").append(pointtypemap.get("sort").toString()).append("</sort>").append("\r\n");
                    if(pointtypemap.get("name") != null)
                        sb.append("<name>").append(pointtypemap.get("name").toString()).append("</name>").append("\r\n");
                    if(pointtypemap.get("value") != null)
                        sb.append("<value>").append(pointtypemap.get("value").toString()).append("</value>").append("\r\n");
                    if(pointtypemap.get("valueads") != null)
                        sb.append("<valueads>").append(pointtypemap.get("valueads").toString()).append("</valueads>").append("\r\n");
                    if(pointtypemap.get("type") != null)
                        sb.append("<type>").append(pointtypemap.get("type").toString()).append("</type>").append("\r\n");
                    if(pointtypemap.get("key") != null)
                        sb.append("<key>").append(pointtypemap.get("key").toString()).append("</key>").append("\r\n");
                    sb.append("</pointtypemap>").append("\r\n");
                }
                if(steplistmap.get("conditionmap") != null)
                {
                    sb.append("<conditionmap>").append("\r\n");
                    Map conditionmap = (Map)steplistmap.get("conditionmap");
                    if(conditionmap.get("sort") != null)
                        sb.append("<sort>").append(conditionmap.get("sort").toString()).append("</sort>").append("\r\n");
                    if(conditionmap.get("name") != null)
                        sb.append("<name>").append(conditionmap.get("name").toString()).append("</name>").append("\r\n");
                    if(conditionmap.get("value") != null)
                        sb.append("<value>").append(conditionmap.get("value").toString()).append("</value>").append("\r\n");
                    if(conditionmap.get("valueads") != null)
                        sb.append("<valueads>").append(conditionmap.get("valueads").toString()).append("</valueads>").append("\r\n");
                    if(conditionmap.get("type") != null)
                        sb.append("<type>").append(conditionmap.get("type").toString()).append("</type>").append("\r\n");
                    if(conditionmap.get("key") != null)
                        sb.append("<key>").append(conditionmap.get("key").toString()).append("</key>").append("\r\n");
                    sb.append("</conditionmap>").append("\r\n");
                }
                if(steplistmap.get("ownerlist") != null)
                {
                    sb.append("<ownerlist>").append("\r\n");
                    List ownerlist = (List)steplistmap.get("ownerlist");
                    for(Iterator i$ = ownerlist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
                    {
                        Map ownerlistmap = (Map)i$.next();
                        sb.append("<Map>").append("\r\n");
                        if(ownerlistmap.get("orgtype") != null)
                            sb.append("<orgtype>").append(ownerlistmap.get("orgtype")).append("</orgtype>").append("\r\n");
                        if(ownerlistmap.get("ownergoto") != null)
                            sb.append("<ownergoto>").append(ownerlistmap.get("ownergoto")).append("</ownergoto>").append("\r\n");
                        if(ownerlistmap.get("orgid") != null)
                            sb.append("<orgid>").append(ownerlistmap.get("orgid")).append("</orgid>").append("\r\n");
                        if(ownerlistmap.get("value") != null)
                            sb.append("<value>").append(ownerlistmap.get("value")).append("</value>").append("\r\n");
                        if(ownerlistmap.get("ownergotoid") != null)
                            sb.append("<ownergotoid>").append(ownerlistmap.get("ownergotoid")).append("</ownergotoid>").append("\r\n");
                        if(ownerlistmap.get("orgtypemap") == null)
                            continue;
                        sb.append("<orgtypemap>").append("\r\n");
                        Map orgtypemap = (Map)ownerlistmap.get("orgtypemap");
                        if(orgtypemap.get("sort") != null)
                            sb.append("<sort>").append(orgtypemap.get("sort").toString()).append("</sort>").append("\r\n");
                        if(orgtypemap.get("name") != null)
                            sb.append("<name>").append(orgtypemap.get("name").toString()).append("</name>").append("\r\n");
                        if(orgtypemap.get("value") != null)
                            sb.append("<value>").append(orgtypemap.get("value").toString()).append("</value>").append("\r\n");
                        if(orgtypemap.get("valueads") != null)
                            sb.append("<valueads>").append(orgtypemap.get("valueads").toString()).append("</valueads>").append("\r\n");
                        if(orgtypemap.get("type") != null)
                            sb.append("<type>").append(orgtypemap.get("type").toString()).append("</type>").append("\r\n");
                        if(orgtypemap.get("key") != null)
                            sb.append("<key>").append(orgtypemap.get("key").toString()).append("</key>").append("\r\n");
                        sb.append("</orgtypemap>").append("\r\n");
                    }

                    sb.append("</ownerlist>").append("\r\n");
                }
                if(steplistmap.get("ownerlinklist") != null)
                {
                    sb.append("<ownerlinklist>").append("\r\n");
                    List ownerlinklist = (List)steplistmap.get("ownerlinklist");
                    for(Iterator i$ = ownerlinklist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
                    {
                        Map ownerlinklistmap = (Map)i$.next();
                        sb.append("<Map>").append("\r\n");
                        if(ownerlinklistmap.get("sort") != null)
                            sb.append("<sort>").append(ownerlinklistmap.get("sort").toString()).append("</sort>").append("\r\n");
                        if(ownerlinklistmap.get("linkleaf") != null)
                            sb.append("<linkleaf>").append(ownerlinklistmap.get("linkleaf").toString()).append("</linkleaf>").append("\r\n");
                        if(ownerlinklistmap.get("ownerender") != null)
                            sb.append("<ownerender>").append(ownerlinklistmap.get("ownerender").toString()).append("</ownerender>").append("\r\n");
                        if(ownerlinklistmap.get("ownerstarter") != null)
                            sb.append("<ownerstarter>").append(ownerlinklistmap.get("ownerstarter").toString()).append("</ownerstarter>").append("\r\n");
                        if(ownerlinklistmap.get("linkroot") != null)
                            sb.append("<linkroot>").append(ownerlinklistmap.get("linkroot").toString()).append("</linkroot>").append("\r\n");
                    }

                    sb.append("</ownerlinklist>").append("\r\n");
                }
                if(steplistmap.get("linklist") != null)
                {
                    sb.append("<linklist>").append("\r\n");
                    List linklist = (List)steplistmap.get("linklist");
                    for(Iterator i$ = linklist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
                    {
                        Map linklistmap = (Map)i$.next();
                        sb.append("<Map>").append("\r\n");
                        if(linklistmap.get("sort") != null)
                            sb.append("<sort>").append(linklistmap.get("sort").toString()).append("</sort>").append("\r\n");
                        if(linklistmap.get("iflist") != null)
                        {
                            List iflist = (List)linklistmap.get("iflist");
                            sb.append("<iflist>").append("\r\n");
                            for(Iterator i$ = iflist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
                            {
                                Map iflistmap = (Map)i$.next();
                                sb.append("<Map>").append("\r\n");
                                if(iflistmap.get("ifand") != null)
                                    sb.append("<ifand>").append(iflistmap.get("ifand").toString()).append("</ifand>").append("\r\n");
                                if(iflistmap.get("iftype") != null)
                                    sb.append("<iftype>").append(iflistmap.get("iftype").toString()).append("</iftype>").append("\r\n");
                                if(iflistmap.get("ifads") != null)
                                    sb.append("<ifads>").append(iflistmap.get("ifads").toString()).append("</ifads>").append("\r\n");
                                if(iflistmap.get("iftypemap") == null)
                                    continue;
                                sb.append("<iftypemap>").append("\r\n");
                                Map iftypemap = (Map)iflistmap.get("iftypemap");
                                if(iftypemap.get("sort") != null)
                                    sb.append("<sort>").append(iftypemap.get("sort").toString()).append("</sort>").append("\r\n");
                                if(iftypemap.get("name") != null)
                                    sb.append("<name>").append(iftypemap.get("name").toString()).append("</name>").append("\r\n");
                                if(iftypemap.get("value") != null)
                                    sb.append("<value>").append(iftypemap.get("value").toString()).append("</value>").append("\r\n");
                                if(iftypemap.get("valueads") != null)
                                    sb.append("<valueads>").append(iftypemap.get("valueads").toString()).append("</valueads>").append("\r\n");
                                if(iftypemap.get("type") != null)
                                    sb.append("<type>").append(iftypemap.get("type").toString()).append("</type>").append("\r\n");
                                if(iftypemap.get("key") != null)
                                    sb.append("<key>").append(iftypemap.get("key").toString()).append("</key>").append("\r\n");
                                sb.append("</iftypemap>").append("\r\n");
                            }

                            sb.append("</iflist>").append("\r\n");
                        }
                        if(linklistmap.get("aftstepid") != null)
                            sb.append("<aftstepid>").append(linklistmap.get("aftstepid").toString()).append("</aftstepid>").append("\r\n");
                        if(linklistmap.get("prestepid") != null)
                            sb.append("<prestepid>").append(linklistmap.get("prestepid").toString()).append("</prestepid>").append("\r\n");
                        if(linklistmap.get("dolist") == null)
                            continue;
                        sb.append("<dolist>").append("\r\n");
                        List dolist = (List)linklistmap.get("dolist");
                        for(Iterator i$ = dolist.iterator(); i$.hasNext(); sb.append("</Map>").append("\r\n"))
                        {
                            Map dolistmap = (Map)i$.next();
                            sb.append("<Map>").append("\r\n");
                            if(dolistmap.get("doads") != null)
                                sb.append("<doads>").append(dolistmap.get("doads").toString()).append("</doads>").append("\r\n");
                            if(dolistmap.get("domark") != null)
                                sb.append("<domark>").append(dolistmap.get("domark").toString()).append("</domark>").append("\r\n");
                            if(dolistmap.get("dotype") != null)
                                sb.append("<dotype>").append(dolistmap.get("dotype").toString()).append("</dotype>").append("\r\n");
                            if(dolistmap.get("dotypemap") == null)
                                continue;
                            sb.append("<dotypemap>").append("\r\n");
                            Map dotypemap = (Map)dolistmap.get("dotypemap");
                            if(dotypemap.get("sort") != null)
                                sb.append("<sort>").append(dotypemap.get("sort").toString()).append("</sort>").append("\r\n");
                            if(dotypemap.get("name") != null)
                                sb.append("<name>").append(dotypemap.get("name").toString()).append("</name>").append("\r\n");
                            if(dotypemap.get("value") != null)
                                sb.append("<value>").append(dotypemap.get("value").toString()).append("</value>").append("\r\n");
                            if(dotypemap.get("valueads") != null)
                                sb.append("<valueads>").append(dotypemap.get("valueads").toString()).append("</valueads>").append("\r\n");
                            if(dotypemap.get("type") != null)
                                sb.append("<type>").append(dotypemap.get("type").toString()).append("</type>").append("\r\n");
                            if(dotypemap.get("key") != null)
                                sb.append("<key>").append(dotypemap.get("key").toString()).append("</key>").append("\r\n");
                            sb.append("</dotypemap>").append("\r\n");
                        }

                        sb.append("</dolist>").append("\r\n");
                    }

                    sb.append("</linklist>").append("\r\n");
                }
                if(steplistmap.get("showurl") != null)
                    sb.append("<showurl>").append(steplistmap.get("showurl").toString()).append("</showurl>").append("\r\n");
                if(steplistmap.get("steptypeads") != null)
                    sb.append("<steptypeads>").append(steplistmap.get("steptypeads").toString()).append("</steptypeads>").append("\r\n");
                if(steplistmap.get("actionurl") != null)
                    sb.append("<actionurl>").append(steplistmap.get("actionurl").toString()).append("</actionurl>").append("\r\n");
                if(steplistmap.get("name") != null)
                    sb.append("<name>").append(steplistmap.get("name").toString()).append("</name>").append("\r\n");
                if(steplistmap.get("steptype") != null)
                    sb.append("<steptype>").append(steplistmap.get("steptype").toString()).append("</steptype>").append("\r\n");
                if(steplistmap.get("urlname") != null)
                    sb.append("<urlname>").append(steplistmap.get("urlname").toString()).append("</urlname>").append("\r\n");
                if(steplistmap.get("stepspecial") != null)
                    sb.append("<stepspecial>").append(steplistmap.get("stepspecial").toString()).append("</stepspecial>").append("\r\n");
                if(steplistmap.get("phoneshow") != null)
                    sb.append("<phoneshow>").append(steplistmap.get("phoneshow").toString()).append("</phoneshow>").append("\r\n");
            }

            sb.append("</steplist>").append("\r\n");
        }
        if(map.get("name") != null)
            sb.append("<name>").append(map.get("name").toString()).append("</name>").append("\r\n");
        if(map.get("processbrief") != null)
            sb.append("<processbrief>").append(map.get("processbrief").toString()).append("</processbrief>");
        try
        {
            File file = new File(path);
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
            fos.close();
        }
        catch(Exception e)
        {
            LOGGER.error("", e);
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/impl/WorkflowIostreamServiceImpl);

}
