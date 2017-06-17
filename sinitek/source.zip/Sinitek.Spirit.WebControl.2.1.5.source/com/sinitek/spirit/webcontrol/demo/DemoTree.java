// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTree.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.tree.ITreeAware;
import com.sinitek.spirit.webcontrol.tree.TreeNode;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DemoTree
    implements ITreeAware
{

    public DemoTree()
    {
    }

    public void genTree(TreeNode root, Map map, HttpServletRequest request)
    {
        TreeNode level1 = new TreeNode("1", "\u5C421");
        TreeNode level2 = new TreeNode("2", "\u5C422");
        TreeNode level3 = new TreeNode("3", "\u5C423");
        level1.setGenChildren(Boolean.valueOf(true));
        level2.setGenChildren(Boolean.valueOf(true));
        level3.setGenChildren(Boolean.valueOf(true));
        root.addChildren(level1);
        root.addChildren(level2);
        root.addChildren(level3);
    }

    public void genChildren(TreeNode parent, Map map, HttpServletRequest request)
    {
        String parentId = parent.getId();
        for(int i = 0; i < 10; i++)
            parent.addChildren(new TreeNode((new StringBuilder()).append(parentId).append(i).toString(), (new StringBuilder()).append("\u5C42").append(parentId).append(i).toString(), (new StringBuilder()).append("").append(parentId).append(i).append(".jsp").toString()));

        TreeNode child = new TreeNode((new StringBuilder()).append(parentId).append("99").toString(), (new StringBuilder()).append("\u5C42").append(parentId).append("99").toString(), (new StringBuilder()).append("").append(parentId).append(99).append(".jsp").toString());
        child.setGenChildren(Boolean.valueOf(true));
        parent.addChildren(child);
    }
}
