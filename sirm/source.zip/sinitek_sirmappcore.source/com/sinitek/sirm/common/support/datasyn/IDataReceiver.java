// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataReceiver.java

package com.sinitek.sirm.common.support.datasyn;


// Referenced classes of package com.sinitek.sirm.common.support.datasyn:
//            DataSynHeader

public interface IDataReceiver
{

    public abstract Object deSerializeData(String s);

    public abstract void receiveData(DataSynHeader datasynheader, String s);
}
