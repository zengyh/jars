// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITreeAware.java

package com.sinitek.spirit.webcontrol.tree;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.sinitek.spirit.webcontrol.tree:
//            TreeNode

public interface ITreeAware
{

    public abstract void genTree(TreeNode treenode, Map map, HttpServletRequest httpservletrequest)
        throws Exception;

    public abstract void genChildren(TreeNode treenode, Map map, HttpServletRequest httpservletrequest)
        throws Exception;
}
