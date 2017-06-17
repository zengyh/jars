// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListField.java

package com.sinitek.spirit.webcontrol.tag.ui;

import com.opensymphony.xwork2.util.ValueStack;
import java.lang.reflect.Array;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.util.ContainUtil;
import org.apache.struts2.util.MakeIterator;

// Referenced classes of package com.sinitek.spirit.webcontrol.tag.ui:
//            Field

public abstract class ListField extends Field
{

    ListField(ValueStack stack, HttpServletRequest request, HttpServletResponse response)
    {
        super(stack, request, response);
        throwExceptionOnNullValueAttribute = false;
    }

    public void evaluateExtraParams()
    {
        super.evaluateExtraParams();
        Object value = null;
        if(list == null)
            list = parameters.get("list");
        if(list instanceof String)
            value = findValue((String)list);
        else
        if(list instanceof Collection)
            value = list;
        else
        if(MakeIterator.isIterable(list))
            value = MakeIterator.convert(list);
        if(value == null)
            if(throwExceptionOnNullValueAttribute)
                value = findValue(list != null ? list.toString() : (String)list, "list", (new StringBuilder()).append("The requested list key '").append(list).append("' could not be resolved as a collection/array/map/enumeration/iterator type. ").append("Example: people or people.{name}").toString());
            else
                value = findValue(list != null ? list.toString() : (String)list);
        if(value == null || "".equals(value))
            value = Collections.EMPTY_LIST;
        if(value instanceof Collection)
            addParameter("list", value);
        else
            addParameter("list", MakeIterator.convert(value));
        if(value instanceof Collection)
            addParameter("listSize", Integer.valueOf(((Collection)value).size()));
        else
        if(value instanceof Map)
            addParameter("listSize", Integer.valueOf(((Map)value).size()));
        else
        if(value != null && value.getClass().isArray())
            addParameter("listSize", Integer.valueOf(Array.getLength(value)));
        if(listKey != null)
            addParameter("listKey", listKey);
        else
        if(value instanceof Map)
            addParameter("listKey", "key");
        if(listValue != null)
        {
            if(altSyntax() && listValue.startsWith("%{") && listValue.endsWith("}"))
                listValue = listValue.substring(2, listValue.length() - 1);
            addParameter("listValue", listValue);
            addProperty("jsMap", jsMap);
        } else
        if(value instanceof Map)
            addParameter("listValue", "value");
    }

    public boolean contains(Object obj1, Object obj2)
    {
        return ContainUtil.contains(obj1, obj2);
    }

    protected Class getValueClassType()
    {
        return null;
    }

    public void setList(Object list)
    {
        this.list = list;
    }

    public void setListKey(String listKey)
    {
        this.listKey = listKey;
    }

    public void setListValue(String listValue)
    {
        this.listValue = listValue;
    }

    public void setThrowExceptionOnNullValueAttribute(boolean throwExceptionOnNullValueAttribute)
    {
        this.throwExceptionOnNullValueAttribute = throwExceptionOnNullValueAttribute;
    }

    public void setJsMap(String jsMap)
    {
        this.jsMap = jsMap;
    }

    private Object list;
    private String listKey;
    private String listValue;
    private String jsMap;
    private boolean throwExceptionOnNullValueAttribute;
}
