// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttachmentUitl.java

package com.sinitek.sirm.common.web;

import com.sinitek.base.metadb.IStreamValue;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.hibernate.proxy.FileStreamValue;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.attachment.AttachmentException;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.utils.IOUtil;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.directwebremoting.io.FileTransfer;

public class AttachmentUitl
{

    public AttachmentUitl()
    {
    }

    public static String upload(Map params, Map fileMap, HttpServletRequest request)
    {
        List _attachmentList = uploadAttachment("BBBB", Integer.valueOf(1), fileMap);
        CommonServiceFactory.getAttachmentService().saveAttachmentList(1, "BBBB", _attachmentList);
        CommonServiceFactory.getAttachmentService().removeAttachmentList((String)params.get("removeid"), "BBBB", 1);
        return "\u4E0A\u4F20\u6210\u529F";
    }

    public static List uploadAttachment(String entityName, Integer type, Map fileMap)
    {
        if(fileMap.size() < 1)
            return null;
        List _attachmentList = new ArrayList();
        List files = (List)fileMap.get("files");
        if(files != null && files.size() > 0)
        {
            for(int i = 0; i < files.size(); i++)
                try
                {
                    IAttachment _attachment = (IAttachment)(new MetaDBTemplate()).createNewMetaObject("ATTACHMENT");
                    FileTransfer file = (FileTransfer)files.get(i);
                    File cpFile = IOUtil.copyToTempDir(file.getInputStream());
                    IStreamValue _streamValue = new FileStreamValue(cpFile, "ATTACHMENT");
                    String _paths[] = file.getFilename().split("\\\\");
                    String _name = _paths[_paths.length - 1];
                    int _index = _name.lastIndexOf(".");
                    String _suffix = _index >= 0 ? _name.substring(_index + 1, _name.length()) : "";
                    _name = _index >= 0 ? _name.substring(0, _index) : _name;
                    _attachment.setName(_name);
                    _attachment.setFileType(_suffix);
                    _attachment.setContent(_streamValue);
                    _attachment.setType(Integer.valueOf(type != null ? type.intValue() : 2));
                    _attachment.setStoreType(Integer.valueOf(0));
                    _attachment.setConvertStatus(Integer.valueOf(0));
                    _attachment.setContentSize(Double.valueOf(file.getSize()));
                    _attachment.setSourceEntity(entityName);
                    _attachmentList.add(_attachment);
                }
                catch(IOException e)
                {
                    LOGGER.error("uploadAttachment failed!", e);
                    throw new AttachmentException(e.getMessage(), "01");
                }

            files.clear();
        }
        return _attachmentList;
    }

    public static Object downAttachmentInfoData(Map param, HttpServletRequest request)
    {
        String entityName = (String)param.get("_down_Attachment_SourceEntity");
        int sourseId = Integer.parseInt((String)param.get("_down_Attachment_SourceId"));
        return CommonServiceFactory.getAttachmentService().findAttachmentInfoList(entityName, sourseId).getResult();
    }

    public static Object removeData(Map param, HttpServletRequest request)
    {
        String __down_Objid = (String)param.get("__down_Objid");
        CommonServiceFactory.getAttachmentService().removeAttachment(Integer.parseInt(__down_Objid));
        return "\u5220\u9664\u6210\u529F";
    }

    public static List uploadFileList(String filepaths[], String entityName, Integer type)
    {
        List _attachmentList = new ArrayList();
        if(filepaths != null && filepaths.length > 0)
        {
            for(int i = 0; i < filepaths.length; i++)
            {
                IAttachment _attachment = (IAttachment)(new MetaDBTemplate()).createNewMetaObject("ATTACHMENT");
                File file = new File(filepaths[i]);
                if(!file.exists())
                    throw new AttachmentException("\u76EE\u5F55\u6570\u636E\u4E0D\u5B58\u5728", "0101");
                IStreamValue _streamValue = new FileStreamValue(file, "ATTACHMENT");
                String _paths[] = filepaths[i].split("\\\\");
                String _name = _paths[_paths.length - 1];
                int _index = _name.lastIndexOf(".");
                String _suffix = _index >= 0 ? _name.substring(_index + 1, _name.length()) : "";
                _name = _index >= 0 ? _name.substring(0, _index) : _name;
                _attachment.setName(_name);
                _attachment.setFileType(_suffix);
                _attachment.setContent(_streamValue);
                _attachment.setType(type);
                _attachment.setStoreType(Integer.valueOf(0));
                _attachment.setConvertStatus(Integer.valueOf(0));
                _attachment.setContentSize(Double.valueOf(file.length()));
                _attachment.setSourceEntity(entityName);
                _attachmentList.add(_attachment);
            }

        }
        return _attachmentList;
    }

    public static IAttachment uploadFile(InputStream stream, String name, String entityName, Integer entityId, Integer type)
    {
        IAttachment _attachment = (IAttachment)(new MetaDBTemplate()).createNewMetaObject("ATTACHMENT");
        File file = IOUtil.copyToTempDir(stream);
        IStreamValue _streamValue = new FileStreamValue(file, "ATTACHMENT");
        String _name = name;
        int _index = _name.lastIndexOf(".");
        String _suffix = _index >= 0 ? _name.substring(_index + 1, _name.length()) : "";
        _name = _index >= 0 ? _name.substring(0, _index) : _name;
        _attachment.setName(_name);
        _attachment.setFileType(_suffix);
        _attachment.setContent(_streamValue);
        _attachment.setType(type);
        _attachment.setStoreType(Integer.valueOf(0));
        _attachment.setConvertStatus(Integer.valueOf(0));
        _attachment.setContentSize(Double.valueOf(file.length()));
        _attachment.setSourceEntity(entityName);
        _attachment.setSourceId(entityId);
        return _attachment;
    }

    public static List findAttachments(String sourceentity, int sourceid)
    {
        return CommonServiceFactory.getAttachmentService().findAttachmentList(sourceentity, sourceid);
    }

    static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/web/AttachmentUitl);

}
