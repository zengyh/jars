// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploaderFileList.java

package com.sinitek.sirm.framework.utils.io;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.hibernate.proxy.FileStreamValue;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javolution.util.FastList;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.framework.utils.io:
//            UploaderFile, Uploader

public class UploaderFileList extends FastList
{

    public UploaderFileList(Collection values)
    {
        super(values);
    }

    public UploaderFileList()
    {
    }

    public UploaderFileList(String id)
    {
        super(id);
    }

    public UploaderFileList(int capacity)
    {
        super(capacity);
    }

    public List toAttachmentList(String entityName, Integer type, boolean deleteTmpFile)
    {
        List _attachmentList;
        Iterator i$;
        _attachmentList = new FastList();
        i$ = iterator();
_L2:
        UploaderFile file;
        InputStream _in;
        if(!i$.hasNext())
            break; /* Loop/switch isn't completed */
        file = (UploaderFile)i$.next();
        _in = null;
        IAttachment _attachment = (IAttachment)(new MetaDBTemplate()).createNewMetaObject("ATTACHMENT");
        com.sinitek.base.metadb.IStreamValue _streamValue = new FileStreamValue(file, "ATTACHMENT");
        _attachment.setName(file.getUploadBaseName());
        _attachment.setFileType(file.getExtension());
        _attachment.setContent(_streamValue);
        _attachment.setType(type);
        _attachment.setStoreType(Integer.valueOf(0));
        _attachment.setConvertStatus(Integer.valueOf(0));
        _attachment.setContentSize(Double.valueOf(Double.parseDouble((new StringBuilder()).append(file.length()).append("").toString())));
        _attachment.setSourceEntity(entityName);
        _attachmentList.add(_attachment);
        if(_in != null)
            try
            {
                _in.close();
            }
            catch(IOException e)
            {
                LOGGER.error("close inputstream failed.", e);
            }
        if(true) goto _L2; else goto _L1
        Exception exception;
        exception;
        if(_in != null)
            try
            {
                _in.close();
            }
            catch(IOException e)
            {
                LOGGER.error("close inputstream failed.", e);
            }
        throw exception;
_L1:
        if(deleteTmpFile)
        {
            Uploader.addAll(this);
            Uploader.removeAll();
        }
        return _attachmentList;
    }

    public List toAttachmentList(String entityName, Integer type)
    {
        return toAttachmentList(entityName, type, true);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/framework/utils/io/UploaderFileList);

}
