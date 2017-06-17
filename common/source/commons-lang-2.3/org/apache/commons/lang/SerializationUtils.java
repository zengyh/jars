// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerializationUtils.java

package org.apache.commons.lang;

import java.io.*;

// Referenced classes of package org.apache.commons.lang:
//            SerializationException

public class SerializationUtils
{

    public SerializationUtils()
    {
    }

    public static Object clone(Serializable object)
    {
        return deserialize(serialize(object));
    }

    public static void serialize(Serializable obj, OutputStream outputStream)
    {
        if(outputStream == null)
            throw new IllegalArgumentException("The OutputStream must not be null");
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);
        }
        catch(IOException ex)
        {
            throw new SerializationException(ex);
        }
        finally
        {
            try
            {
                if(out != null)
                    out.close();
            }
            catch(IOException ex) { }
        }
    }

    public static byte[] serialize(Serializable obj)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(obj, ((OutputStream) (baos)));
        return baos.toByteArray();
    }

    public static Object deserialize(InputStream inputStream)
    {
        if(inputStream == null)
            throw new IllegalArgumentException("The InputStream must not be null");
        ObjectInputStream in = null;
        try
        {
            in = new ObjectInputStream(inputStream);
            Object obj = in.readObject();
            return obj;
        }
        catch(ClassNotFoundException ex)
        {
            throw new SerializationException(ex);
        }
        catch(IOException ex)
        {
            throw new SerializationException(ex);
        }
        finally
        {
            try
            {
                if(in != null)
                    in.close();
            }
            catch(IOException ex) { }
        }
    }

    public static Object deserialize(byte objectData[])
    {
        if(objectData == null)
        {
            throw new IllegalArgumentException("The byte[] must not be null");
        } else
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
            return deserialize(((InputStream) (bais)));
        }
    }
}
