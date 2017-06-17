// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileStreamType.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.hibernate.proxy.FileStreamValue;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

// Referenced classes of package com.sinitek.base.metadb.hibernate.usertype:
//            AbstractStreamUserType

public class FileStreamType extends AbstractStreamUserType
{

    public FileStreamType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            12
        });
    }

    public Class returnedClass()
    {
        return com/sinitek/base/metadb/hibernate/proxy/FileStreamValue;
    }

    public Object nullSafeGet(ResultSet rs, String names[], Object owner)
        throws HibernateException, SQLException
    {
        String _dbFileName = rs.getString(names[0]);
        if(_dbFileName != null && _dbFileName.trim().length() != 0)
        {
            _dbFileName = _dbFileName.trim();
            String fileName = (new StringBuilder()).append(filePath).append(File.separator).append(_dbFileName).toString();
            File file = new File(fileName);
            if(file.exists())
            {
                saveFile = file;
                saveFileName = _dbFileName;
                return new FileStreamValue(file, entityName);
            } else
            {
                throw new HibernateException((new StringBuilder()).append("\u6587\u4EF6[").append(fileName).append("]\u4E0D\u5B58\u5728").toString());
            }
        } else
        {
            return new FileStreamValue(null, entityName);
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        FileStreamValue fsv;
        OutputStream os;
        fsv = (FileStreamValue)value;
        InputStream data = null;
        os = null;
        try
        {
            InputStream data = fsv.getData();
            if(data != null)
            {
                if(saveFile == null)
                {
                    saveFileName = genFileName(entityName);
                    saveFile = new File((new StringBuilder()).append(filePath).append(File.separator).append(saveFileName).toString());
                    if(!saveFile.exists())
                        saveFile.createNewFile();
                }
                os = new FileOutputStream(saveFile);
                byte buffer[] = new byte[512];
                long totalLength = 0L;
                for(int length = data.read(buffer); length > 0; length = data.read(buffer))
                {
                    totalLength += length;
                    os.write(buffer, 0, length);
                }

                os.flush();
                if(totalLength != 0L)
                {
                    st.setString(index, saveFileName);
                } else
                {
                    saveFile.delete();
                    st.setNull(index, 12);
                }
            } else
            {
                st.setNull(index, 12);
            }
        }
        catch(IOException e)
        {
            throw new HibernateException("\u5904\u7406\u6587\u4EF6\u6570\u636E\u5931\u8D25", e);
        }
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                MetaDBLoggerFactory.LOGGER_CORE.warn("\u5173\u95ED\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_296;
        Exception exception;
        exception;
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                MetaDBLoggerFactory.LOGGER_CORE.warn("\u5173\u95ED\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        throw exception;
    }

    public void setParameterValues(Properties parameters)
    {
        super.setParameterValues(parameters);
        filePath = parameters.getProperty("metadb.file.path");
    }

    private String genFileName(String entityName)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        DecimalFormat df = new DecimalFormat("00");
        Random ran = new Random(System.currentTimeMillis());
        return (new StringBuilder()).append(entityName).append(sdf.format(new Date())).append(df.format(ran.nextInt(100))).append(".data").toString();
    }

    private String filePath;
    private File saveFile;
    private String saveFileName;
}
