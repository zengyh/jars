// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploaderFile.java

package com.sinitek.sirm.framework.utils.io;

import java.io.File;
import java.net.URI;
import org.apache.commons.io.FilenameUtils;

public class UploaderFile extends File
{

    public UploaderFile(String pathname, String uploadName, String id)
    {
        super(pathname);
        this.uploadName = uploadName;
        this.id = id;
    }

    public UploaderFile(String parent, String child, String uploadName, String id)
    {
        super(parent, child);
        this.uploadName = uploadName;
        this.id = id;
    }

    public UploaderFile(File parent, String child, String uploadName, String id)
    {
        super(parent, child);
        this.uploadName = uploadName;
        this.id = id;
    }

    public UploaderFile(URI uri, String uploadName, String id)
    {
        super(uri);
        this.uploadName = uploadName;
        this.id = id;
    }

    public String getUploadName()
    {
        return uploadName;
    }

    public String getExtension()
    {
        return FilenameUtils.getExtension(getName());
    }

    public String getUploadBaseName()
    {
        return FilenameUtils.getBaseName(getUploadName());
    }

    public String getBaseName()
    {
        return FilenameUtils.getBaseName(getName());
    }

    public String getId()
    {
        return id;
    }

    protected String uploadName;
    protected String id;
}
