// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerializeHelper.java

package com.sinitek.base.control;

import com.sinitek.base.common.BaseException;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;

// Referenced classes of package com.sinitek.base.control:
//            HandleCoreException, ISerializableObject

public class SerializeHelper
{

    public SerializeHelper()
    {
    }

    public static void serialize(Object input, OutputStream outputStream, String objName)
        throws IOException
    {
        if(input == null)
            return;
        byte data[] = new byte[0];
        if(input instanceof String)
            try
            {
                data = input.toString().getBytes("utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
        else
        if((input instanceof Integer) || (input instanceof Long) || (input instanceof Float) || (input instanceof Short) || (input instanceof Double))
        {
            Number value = (Number)input;
            try
            {
                data = value.toString().getBytes("utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
        } else
        if(input instanceof Boolean)
        {
            Boolean b = (Boolean)input;
            byte value = (byte)(b.booleanValue() ? 49 : 48);
            data = (new byte[] {
                value
            });
        } else
        if(input instanceof Date)
        {
            Date date = (Date)input;
            Long value = new Long(date.getTime());
            data = value.toString().getBytes("utf-8");
        } else
        if(input instanceof Collection)
        {
            Collection col = (Collection)input;
            data = serializeCollection(col);
        } else
        if(input instanceof Map)
        {
            Map map = (Map)input;
            data = serializeMap(map);
        } else
        if(input instanceof ISerializableObject)
        {
            ISerializableObject so = (ISerializableObject)input;
            data = so.toBytes();
        }
        DecimalFormat df = new DecimalFormat("00000000");
        String length = df.format(data.length);
        outputStream.write(length.getBytes());
        outputStream.write(objName.getBytes());
        outputStream.write(124);
        outputStream.write(input.getClass().getName().getBytes());
        outputStream.write(59);
        outputStream.write(data);
    }

    public static java.util.Map.Entry deserialize(byte input[])
    {
        String className;
        int pos;
        java.util.Map.Entry ret;
        className = null;
        StringBuffer objName = new StringBuffer();
        pos = 0;
        int i = 8;
        do
        {
            if(i >= input.length)
                break;
            if(input[i] == 59)
            {
                pos = i;
                String _temp[] = (new String(input, 8, pos - 8)).split("\\|");
                objName.append(_temp[0]);
                className = _temp[1];
                break;
            }
            i++;
        } while(true);
        if(className == null)
            throw new HandleCoreException("0002");
        pos++;
        ret = new java.util.Map.Entry(objName) {

            public Object getKey()
            {
                return objName.toString();
            }

            public Object getValue()
            {
                return value;
            }

            public Object setValue(Object value)
            {
                Object _ret = this.value;
                this.value = value;
                return _ret;
            }

            public Object value;
            final StringBuffer val$objName;

            
            {
                objName = stringbuffer;
                super();
            }
        };
        if(java/lang/String.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(data);
            return ret;
        }
        if(java/lang/Double.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Double.valueOf(data));
            return ret;
        }
        if(java/lang/Float.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Float.valueOf(data));
            return ret;
        }
        if(java/lang/Integer.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Integer.valueOf(data));
            return ret;
        }
        if(java/lang/Long.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Long.valueOf(data));
            return ret;
        }
        if(java/lang/Short.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Short.valueOf(data));
            return ret;
        }
        if(java/util/Date.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(new Date(Long.parseLong(data)));
            return ret;
        }
        if(java/lang/Boolean.getName().equals(className))
        {
            String data;
            try
            {
                data = new String(input, pos, input.length - pos, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {
                throw new HandleCoreException("0001", e);
            }
            ret.setValue(Boolean.valueOf(!"0".equals(data)));
            return ret;
        }
        Class cls;
        byte temp[];
        cls = Class.forName(className);
        temp = new byte[input.length - pos];
        System.arraycopy(input, pos, temp, 0, input.length - pos);
        if(!java/util/Collection.isAssignableFrom(cls))
            break MISSING_BLOCK_LABEL_682;
        ret.setValue(deserializeCollection(temp, cls));
        return ret;
        if(!java/util/Map.isAssignableFrom(cls))
            break MISSING_BLOCK_LABEL_711;
        ret.setValue(deserializeMap(temp, cls));
        return ret;
        if(!com/sinitek/base/control/ISerializableObject.isAssignableFrom(cls))
            break MISSING_BLOCK_LABEL_781;
        ISerializableObject obj = (ISerializableObject)cls.newInstance();
        obj.fromBytes(temp);
        ret.setValue(obj);
        return ret;
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new HandleCoreException("0005", e, new Object[] {
            className
        });
        throw new HandleCoreException("0004", new Object[] {
            className
        });
        ClassNotFoundException ex;
        ex;
        throw new HandleCoreException("0003", ex, new Object[] {
            className
        });
    }

    public static byte[] serializeJavaBean(Object bean)
    {
        ByteArrayOutputStream baos;
        Map map = PropertyUtils.describe(bean);
        baos = new ByteArrayOutputStream();
        Iterator iter = map.entrySet().iterator();
        do
        {
            if(!iter.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            if(!"class".equals(entry.getKey()) && entry.getValue() != null)
                serialize(entry.getValue(), baos, entry.getKey().toString());
        } while(true);
        return baos.toByteArray();
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new HandleCoreException("0006", e, new Object[] {
            bean.getClass().getName()
        });
    }

    public static void deserializeJavaBean(byte data[], Object bean)
    {
        List propDatas = devideData(data);
        Iterator iter = propDatas.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            byte propData[] = (byte[])(byte[])iter.next();
            java.util.Map.Entry entry = deserialize(propData);
            String propName = entry.getKey().toString();
            if(PropertyUtils.isWriteable(bean, propName))
                try
                {
                    PropertyUtils.setProperty(bean, propName, entry.getValue());
                }
                catch(Exception e)
                {
                    throw new HandleCoreException("0007", e, new Object[] {
                        bean.getClass().getName()
                    });
                }
        } while(true);
    }

    public static List devideData(byte data[])
    {
        int dataStartPos = 0;
        List propDatas = new ArrayList();
        int dataPos;
        for(; dataStartPos < data.length - 1; dataStartPos = dataPos)
        {
            int length = Integer.parseInt(new String(data, dataStartPos, 8));
            dataPos = dataStartPos;
            do
            {
                if(dataPos >= data.length)
                    break;
                if(data[dataPos] == 59)
                {
                    dataPos++;
                    break;
                }
                dataPos++;
            } while(true);
            dataPos += length;
            byte propData[] = new byte[dataPos - dataStartPos];
            System.arraycopy(data, dataStartPos, propData, 0, propData.length);
            propDatas.add(propData);
        }

        return propDatas;
    }

    private static byte[] serializeCollection(Collection collection)
        throws IOException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for(Iterator iter = collection.iterator(); iter.hasNext(); serialize(iter.next(), os, ""));
        return os.toByteArray();
    }

    private static byte[] serializeMap(Map map)
        throws IOException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        java.util.Map.Entry entry;
        for(Iterator iter = map.entrySet().iterator(); iter.hasNext(); serialize(entry.getValue(), os, ""))
        {
            entry = (java.util.Map.Entry)iter.next();
            serialize(entry.getKey(), os, "");
        }

        return os.toByteArray();
    }

    private static Collection deserializeCollection(byte data[], Class collectionClz)
    {
        Collection collection;
        try
        {
            collection = (Collection)collectionClz.newInstance();
        }
        catch(Exception e)
        {
            throw new HandleCoreException("0008", e, new Object[] {
                collectionClz.getName()
            });
        }
        List datas = devideData(data);
        for(Iterator iter = datas.iterator(); iter.hasNext(); collection.add(deserialize((byte[])(byte[])iter.next()).getValue()));
        return collection;
    }

    private static Map deserializeMap(byte data[], Class collectionClz)
    {
        Map map;
        try
        {
            map = (Map)collectionClz.newInstance();
        }
        catch(Exception e)
        {
            throw new HandleCoreException("0009", e, new Object[] {
                collectionClz.getName()
            });
        }
        List datas = devideData(data);
        Object key;
        Object value;
        for(Iterator iter = datas.iterator(); iter.hasNext(); map.put(key, value))
        {
            key = deserialize((byte[])(byte[])iter.next()).getValue();
            value = deserialize((byte[])(byte[])iter.next()).getValue();
        }

        return map;
    }
}
