// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractStreamValue.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.*;
import java.io.*;
import java.sql.SQLException;

public abstract class AbstractStreamValue
    implements IStreamValue, Serializable
{
    protected static class StatusOutputStream extends OutputStream
    {

        public void write(int b)
            throws IOException
        {
            origOs.write(b);
        }

        public void close()
            throws IOException
        {
            super.close();
            streamValue.resetStatus();
        }

        private OutputStream origOs;
        private AbstractStreamValue streamValue;

        public StatusOutputStream(OutputStream origOs, AbstractStreamValue streamValue)
        {
            this.origOs = origOs;
            this.streamValue = streamValue;
        }
    }


    protected AbstractStreamValue(String entityName)
    {
        this.entityName = entityName;
    }

    public abstract InputStream getOrigInputStream()
        throws IOException, SQLException;

    public InputStream getInputStream()
        throws IOException
    {
        OutputStream os;
        InputStream is;
        Exception exception;
        initFlag = readFile != null;
        createReadFile();
        if(!initFlag)
        {
            os = null;
            is = null;
            try
            {
                is = getOrigInputStream();
                if(is != null)
                {
                    os = new FileOutputStream(readFile);
                    byte buffer[] = new byte[512];
                    for(int length = is.read(buffer); length > 0; length = is.read(buffer))
                        os.write(buffer, 0, length);

                    os.flush();
                }
                initFlag = true;
            }
            catch(IOException ioe)
            {
                throw ioe;
            }
            catch(Exception ex)
            {
                throw new MetaDBCoreException("0012", ex);
            }
            finally
            {
                if(is == null) goto _L0; else goto _L0
            }
            if(is != null)
                is.close();
            if(os != null)
                os.close();
        }
        break MISSING_BLOCK_LABEL_152;
        is.close();
        if(os != null)
            os.close();
        throw exception;
        return new FileInputStream(readFile);
    }

    public synchronized OutputStream getOutputStream()
        throws IOException
    {
        createReadFile();
        if(openFlag)
        {
            throw new MetaDBCoreException("0013", new Object[] {
                readFile.getPath()
            });
        } else
        {
            FileOutputStream fos = new FileOutputStream(readFile);
            openFlag = true;
            return new StatusOutputStream(fos, this);
        }
    }

    protected void resetStatus()
    {
        openFlag = false;
    }

    private void createReadFile()
    {
        if(readFile == null)
        {
            MetaDBContextFactory factory = MetaDBContextFactory.getInstance();
            com.sinitek.base.metadb.IEntity entity = factory.getEntity(entityName);
            readFile = factory.createTempFile(entity);
        }
    }

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        entityName = (String)in.readObject();
        createReadFile();
        FileOutputStream fos = new FileOutputStream(readFile);
        fos.write((byte[])(byte[])in.readObject());
        fos.close();
        initFlag = true;
    }

    private void writeObject(ObjectOutputStream out)
        throws IOException
    {
        out.writeObject(entityName);
        InputStream is = getInputStream();
        byte buffer[] = new byte[512];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(int length = is.read(buffer); length > 0; length = is.read(buffer))
            baos.write(buffer, 0, length);

        out.writeObject(baos.toByteArray());
        is.close();
    }

    public InputStream getData()
        throws IOException
    {
        if(readFile == null)
            return getInputStream();
        if(openFlag)
            throw new MetaDBCoreException("0014", new Object[] {
                readFile.getPath()
            });
        else
            return new FileInputStream(readFile);
    }

    public long getDataSize()
        throws IOException
    {
        if(readFile == null)
        {
            InputStream is = getInputStream();
            is.close();
        }
        if(openFlag)
            throw new MetaDBCoreException("0014", new Object[] {
                readFile.getPath()
            });
        else
            return readFile.length();
    }

    protected String entityName;
    protected boolean initFlag;
    protected boolean openFlag;
    protected transient File readFile;
}
