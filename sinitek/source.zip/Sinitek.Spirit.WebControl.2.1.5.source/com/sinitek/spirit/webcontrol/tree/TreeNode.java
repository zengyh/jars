// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeNode.java

package com.sinitek.spirit.webcontrol.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode
{

    public void addChildren(TreeNode tn)
    {
        if(children == null)
            children = new ArrayList();
        children.add(tn);
    }

    public TreeNode()
    {
        children = null;
    }

    public TreeNode(String id)
    {
        children = null;
        this.id = id;
    }

    public TreeNode(String id, String text)
    {
        children = null;
        this.id = id;
        this.text = text;
    }

    public TreeNode(String id, String text, String href)
    {
        children = null;
        this.id = id;
        this.text = text;
        this.href = href;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public List getChildren()
    {
        return children;
    }

    public void setChildren(List children)
    {
        this.children = children;
    }

    public Boolean isOpen()
    {
        return isOpen;
    }

    public void setOpen(Boolean open)
    {
        isOpen = open;
    }

    public Boolean getGenChildren()
    {
        return genChildren;
    }

    public void setGenChildren(Boolean genChildren)
    {
        this.genChildren = genChildren;
        if(genChildren.booleanValue())
            children = new ArrayList();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    private String id;
    private String icon;
    private Boolean genChildren;
    private String text;
    private String href;
    private List children;
    private Boolean isOpen;
}
