// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerializableJavaBean.java

package com.sinitek.base.control;


// Referenced classes of package com.sinitek.base.control:
//            ISerializableObject, SerializeHelper

public class SerializableJavaBean
    implements ISerializableObject
{

    public SerializableJavaBean()
    {
    }

    public byte[] toBytes()
    {
        return SerializeHelper.serializeJavaBean(this);
    }

    public void fromBytes(byte bytes[])
    {
        SerializeHelper.deserializeJavaBean(bytes, this);
    }
}
