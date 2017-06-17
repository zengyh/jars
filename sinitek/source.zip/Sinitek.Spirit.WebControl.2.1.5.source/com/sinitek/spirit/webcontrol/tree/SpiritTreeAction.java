// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritTreeAction.java

package com.sinitek.spirit.webcontrol.tree;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.webcontrol.tree:
//            ITreeAware, TreeNode

public class SpiritTreeAction
{

    public SpiritTreeAction()
    {
    }

    public TreeNode genTree(Map config, Map map, HttpServletRequest request)
        throws Exception
    {
        String className = (String)config.get("clazz");
        try
        {
            ITreeAware action = (ITreeAware)Class.forName(className).newInstance();
            TreeNode root = new TreeNode("root", (String)config.get("rootText"));
            root.setIcon((String)config.get("rootIcon"));
            action.genTree(root, map, request);
            return root;
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    public TreeNode genChildren(Map config, String parentId, Map map, HttpServletRequest request)
        throws Exception
    {
        String className = (String)config.get("clazz");
        try
        {
            ITreeAware action = (ITreeAware)Class.forName(className).newInstance();
            TreeNode parend = new TreeNode(parentId);
            action.genChildren(parend, map, request);
            return parend;
        }
        catch(Exception e)
        {
            LOG.error("\u8C03\u7528\u5BF9\u8C61\u5931\u8D25", e);
            throw e;
        }
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/spirit/webcontrol/tree/SpiritTreeAction);

}
