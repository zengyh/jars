// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextInfoImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.control.SerializableJavaBean;
import java.util.List;

// Referenced classes of package com.sinitek.base.metadb:
//            IMetaDBContextInfo

public class MetaDBContextInfoImpl extends SerializableJavaBean
    implements IMetaDBContextInfo
{

    public MetaDBContextInfoImpl()
    {
    }

    public int getMaxContextCount()
    {
        return maxContextCount;
    }

    public int getIdelContextCount()
    {
        return idelContextCount;
    }

    public int getUseCount(int contextId)
    {
        if(contextId >= useCountList.size())
            return 0;
        else
            return ((Integer)useCountList.get(contextId - 1)).intValue();
    }

    public List getUseCountList()
    {
        return useCountList;
    }

    public void setUseCountList(List useCountList)
    {
        this.useCountList = useCountList;
    }

    public void setIdelContextCount(int idelContextCount)
    {
        this.idelContextCount = idelContextCount;
    }

    public void setMaxContextCount(int maxContextCount)
    {
        this.maxContextCount = maxContextCount;
    }

    private int maxContextCount;
    private int idelContextCount;
    private List useCountList;
}
