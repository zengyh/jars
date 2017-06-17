// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttachmentMO.java

package com.sinitek.sirm.common.attachment.entity;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.hibernate.proxy.*;
import com.sinitek.sirm.common.exception.SirmAppException;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.IOUtil;
import com.sinitek.sirm.common.utils.TimeUtil;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

// Referenced classes of package com.sinitek.sirm.common.attachment.entity:
//            IAttachment

public class AttachmentMO
    implements IAttachment
{

    public AttachmentMO(IAttachment orig)
    {
        this.orig = null;
        storeHome = null;
        storeType = null;
        this.orig = orig;
        loadAttachmentStoreHome();
        loadAttachmentStoreTye();
    }

    public String getSourceEntity()
    {
        return orig.getSourceEntity();
    }

    public void setSourceEntity(String value)
    {
        orig.setSourceEntity(value);
    }

    public Integer getType()
    {
        return orig.getType();
    }

    public void setType(Integer value)
    {
        orig.setType(value);
    }

    public Integer getSourceId()
    {
        return orig.getSourceId();
    }

    public void setSourceId(Integer value)
    {
        orig.setSourceId(value);
    }

    public String getName()
    {
        return orig.getName();
    }

    public void setName(String value)
    {
        orig.setName(value);
    }

    public Double getContentSize()
    {
        return orig.getContentSize();
    }

    public void setContentSize(Double value)
    {
        orig.setContentSize(value);
    }

    public Integer getPageCount()
    {
        return orig.getPageCount();
    }

    public void setPageCount(Integer value)
    {
        orig.setPageCount(value);
    }

    public Integer getConvertStatus()
    {
        return orig.getConvertStatus();
    }

    public void setConvertStatus(Integer value)
    {
        orig.setConvertStatus(value);
    }

    public String getDigest()
    {
        return orig.getDigest();
    }

    public void setDigest(String value)
    {
        orig.setDigest(value);
    }

    public Integer getStoreType()
    {
        return orig.getStoreType();
    }

    public void setStoreType(Integer value)
    {
        orig.setStoreType(value);
    }

    public String getStorePath()
    {
        return orig.getStorePath();
    }

    public void setStorePath(String value)
    {
        orig.setStorePath(value);
    }

    public String getFileType()
    {
        return orig.getFileType();
    }

    public void setFileType(String value)
    {
        orig.setFileType(value);
    }

    public IStreamValue getContent()
    {
        IStreamValue result = null;
        if(orig.getStoreType() != null && orig.getStoreType().intValue() == 0)
            result = orig.getContent();
        else
            result = getFileContentStream();
        return result;
    }

    public void setContent(IStreamValue value)
    {
        orig.setContent(value);
    }

    public Integer getConvertResult()
    {
        return orig.getConvertResult();
    }

    public void setConvertResult(Integer value)
    {
        orig.setConvertResult(value);
    }

    public Integer getSendFlag()
    {
        return orig.getSendFlag();
    }

    public void setSendFlag(Integer value)
    {
        orig.setSendFlag(value);
    }

    public Integer getConvertFlag()
    {
        return orig.getConvertFlag();
    }

    public void setConvertFlag(Integer value)
    {
        orig.setConvertFlag(value);
    }

    public Integer getConvertId()
    {
        return orig.getConvertId();
    }

    public void setConvertId(Integer value)
    {
        orig.setConvertId(value);
    }

    public Object putValue(Object o, Object o1)
    {
        return orig.putValue(o, o1);
    }

    public Object getValue(Object o)
    {
        return orig.getValue(o);
    }

    public boolean isDirtyProperty(String s)
    {
        return orig.isDirtyProperty(s);
    }

    public int getDirtyPropertyCount()
    {
        return orig.getDirtyPropertyCount();
    }

    public boolean isPersistStatus()
    {
        return orig.isPersistStatus();
    }

    public void setPersistStatus(boolean b)
    {
        orig.setPersistStatus(b);
    }

    public Object getOrigValue(IProperty iProperty)
    {
        return orig.getOrigValue(iProperty);
    }

    public void setEnumValue(String s, Object o)
    {
        orig.setEnumValue(s, o);
    }

    public void setEntityValue(String s, Object o)
    {
        orig.setEntityValue(s, o);
    }

    public void setEntityOrigDBValue(String s, Object o)
    {
        orig.setEntityOrigDBValue(s, o);
    }

    public void setCreateTimeStamp(Date date)
    {
        orig.setCreateTimeStamp(date);
    }

    public void setId(int i)
    {
        orig.setId(i);
    }

    public void setUpdateTimeStamp(Date date)
    {
        orig.setUpdateTimeStamp(date);
    }

    public void setLastProxy(LastIdProxy lastIdProxy)
    {
        orig.setLastProxy(lastIdProxy);
    }

    public void setVersion(int i)
    {
        orig.setVersion(i);
    }

    public int getId()
    {
        return orig.getId();
    }

    public Date getUpdateTimestamp()
    {
        return orig.getUpdateTimestamp();
    }

    public Date getCreateTimestamp()
    {
        return orig.getCreateTimestamp();
    }

    public int getLastId()
        throws MetaDBException
    {
        return orig.getLastId();
    }

    public IMetaObject getLastObject()
        throws MetaDBException
    {
        return orig.getLastObject();
    }

    public Map getEntityOrigValues()
    {
        return orig.getEntityOrigValues();
    }

    public void save()
        throws MetaDBException
    {
        BlobStreamValue bsv;
        if("file".equals(storeType))
        {
            setStoreFilePath();
            break MISSING_BLOCK_LABEL_90;
        }
        bsv = null;
        InputStream fin = null;
        try
        {
            InputStream fin = orig.getContent().getInputStream();
            bsv = new BlobStreamValue(Hibernate.createBlob(fin), orig.getEntityName());
        }
        catch(Exception ex)
        {
            LOGGER.warn("create BlobStreamValue failed.", ex);
        }
        break MISSING_BLOCK_LABEL_80;
        Exception exception;
        exception;
        throw exception;
        orig.setContent(bsv);
        orig.save();
        return;
    }

    public void remove()
        throws MetaDBException
    {
        if("file".equals(storeType))
        {
            File storeFile = getStoreFile(false);
            if(storeFile != null)
                storeFile.delete();
        }
        orig.remove();
    }

    public void delete()
        throws MetaDBException
    {
        if("file".equals(storeType))
        {
            File storeFile = getStoreFile(false);
            if(storeFile != null)
                storeFile.delete();
        }
        orig.delete();
    }

    public int getVersion()
    {
        return orig.getVersion();
    }

    public IEntity getEntity()
    {
        return orig.getEntity();
    }

    public String getEntityName()
    {
        return orig.getEntityName();
    }

    public List getRelaObjects(IEntity iEntity)
        throws MetaDBException
    {
        return orig.getRelaObjects(iEntity);
    }

    public IMetaObjectPropertyUpdateInfo getUpdateInfo()
    {
        return orig.getUpdateInfo();
    }

    public int size()
    {
        return orig.size();
    }

    public boolean isEmpty()
    {
        return orig.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return orig.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return orig.containsValue(value);
    }

    public Object get(Object key)
    {
        return orig.get(key);
    }

    public Object put(Object key, Object value)
    {
        return orig.put(key, value);
    }

    public Object remove(Object key)
    {
        return orig.remove(key);
    }

    public void putAll(Map m)
    {
        orig.putAll(m);
    }

    public void clear()
    {
        orig.clear();
    }

    public Set keySet()
    {
        return orig.keySet();
    }

    public Collection values()
    {
        return orig.values();
    }

    public Set entrySet()
    {
        return orig.entrySet();
    }

    public Object clone()
    {
        return orig.clone();
    }

    private void loadAttachmentStoreTye()
    {
        String storeType = SettingUtils.getStringValue("COMMON", "ATTACH_STORETYPE");
        boolean filestored = "file".equalsIgnoreCase(storeType);
        if(!filestored)
            storeType = "db";
        this.storeType = storeType.toLowerCase();
        if((new Integer(1)).equals(orig.getStoreType()) && StringUtils.isNotBlank(orig.getStorePath()))
            this.storeType = "file";
    }

    private void loadAttachmentStoreHome()
    {
        String storeHome = SettingUtils.getStringValue("COMMON", "ATTACH_STOREHOME");
        if(storeHome == null)
            LOGGER.warn("[COMMON,ATTACH_STOREHOME] is not configed");
        this.storeHome = storeHome;
    }

    private void setStoreFilePath()
    {
        if(storeHome == null)
            throw new SirmAppException("[COMMON,ATTACH_STOREHOME] is not configed", "0001");
        File storeFile = getStoreFile(true);
        IStreamValue sv = orig.getContent();
        try
        {
            if(sv != null && sv.getInputStream().available() > 0)
            {
                File storeDir = storeFile.getParentFile();
                if(!storeDir.exists())
                    storeDir.mkdirs();
                storeFile = IOUtil.copyFile(storeFile.getAbsolutePath(), sv.getInputStream());
                orig.setStorePath(storeFile.toURI().toString());
                orig.setStoreType(Integer.valueOf(1));
            }
            orig.setContent(new BlobStreamValue(null, orig.getEntityName()));
        }
        catch(IOException ex)
        {
            LOGGER.error(getClass(), ex);
            throw new SirmAppException((new StringBuilder()).append("save attachment to [ATTACH_STOREHOME] failed.[").append(storeFile.getAbsolutePath()).append("]").toString(), "0001");
        }
    }

    private File getStoreFile(boolean create)
    {
        File result = null;
        try
        {
            if(StringUtils.isNotBlank(orig.getStorePath()))
            {
                if(orig.getStorePath().indexOf(":/") > 0)
                {
                    result = new File(new URI(orig.getStorePath()));
                } else
                {
                    String storepath = IOUtil.combinePath(new String[] {
                        storeHome, orig.getStorePath()
                    });
                    result = new File(storepath);
                }
            } else
            if(create)
            {
                String date = (new SimpleDateFormat("yyyyMM")).format(new Date());
                String filename = TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss");
                if(StringUtils.isNotBlank(orig.getFileType()))
                    filename = (new StringBuilder()).append(filename).append(".").append(orig.getFileType()).toString();
                else
                    filename = (new StringBuilder()).append(filename).append(".dat").toString();
                String storepath = IOUtil.combinePath(new String[] {
                    storeHome, orig.getSourceEntity().toLowerCase(), date, filename
                });
                result = new File(storepath);
            }
        }
        catch(Exception ex)
        {
            LOGGER.error("getstorefile failed.", ex);
        }
        return result;
    }

    private IStreamValue getFileContentStream()
    {
        IStreamValue result = null;
        String storePath = StringUtils.replace(orig.getStorePath(), "\\", "/");
        if(StringUtils.isNotBlank(storePath))
        {
            File file = null;
            if(storePath.startsWith("/"))
            {
                if("\\".equals(File.separator))
                {
                    String path = IOUtil.combinePath(new String[] {
                        StringUtils.replace(storeHome, "\\", "/"), storePath
                    });
                    path = StringUtils.replace(path, "\\", "/");
                    file = new File(path);
                } else
                {
                    file = new File(storePath);
                }
            } else
            if(storePath.startsWith("file:"))
            {
                try
                {
                    URI uri = new URI(storePath);
                    file = new File(uri);
                }
                catch(URISyntaxException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                String path = null;
                if("\\".equals(File.separator))
                {
                    if(storePath.indexOf(":/") < 0)
                    {
                        path = IOUtil.combinePath(new String[] {
                            StringUtils.replace(storeHome, "\\", "/"), storePath
                        });
                        path = StringUtils.replace(path, "\\", "/");
                    } else
                    {
                        path = storePath;
                    }
                } else
                {
                    path = IOUtil.combinePath(new String[] {
                        StringUtils.replace(storeHome, "\\", "/"), storePath
                    });
                }
                file = new File(path);
            }
            if(file != null && file.exists())
            {
                result = new FileStreamValue(file, orig.getEntityName());
            } else
            {
                java.sql.Blob blob = Hibernate.createBlob(new byte[0]);
                result = new BlobStreamValue(blob, orig.getEntityName());
            }
        }
        return result;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/attachment/entity/AttachmentMO);
    private IAttachment orig;
    private String storeHome;
    private String storeType;

}
