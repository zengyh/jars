// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBMongoFactory.java

package com.sinitek.base.mongodb;

import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MetaDBMongoFactory
{

    private MetaDBMongoFactory(String host, String port, String db, String user, String password, String readPreference)
    {
        if(StringUtils.isBlank(host))
            host = "127.0.0.1";
        if(StringUtils.isBlank(port))
            port = "27017";
        if(StringUtils.isBlank(db))
            db = "sinitek";
        try
        {
            MongoClient client;
            if(host.contains(";"))
            {
                List addresses = new ArrayList();
                String hosts[] = host.split(";");
                String ports[] = port.split(";");
                for(int i = 0; i < hosts.length; i++)
                {
                    ServerAddress address = new ServerAddress(hosts[i], (new Integer(ports[i])).intValue());
                    addresses.add(address);
                }

                client = new MongoClient(addresses);
            } else
            {
                client = new MongoClient(host, (new Integer(port)).intValue());
            }
            mongoDB = client.getDB(db);
            MongoDbFactory factory;
            if(StringUtils.isBlank(user) || StringUtils.isBlank(password))
            {
                factory = new SimpleMongoDbFactory(client, db);
            } else
            {
                factory = new SimpleMongoDbFactory(client, db, new UserCredentials(user, password));
                boolean auth = mongoDB.authenticate(user, password.toCharArray());
                if(!auth)
                    throw new RuntimeException("mongodb\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF!");
            }
        }
        catch(Exception e)
        {
            LOG.error("\u8FDE\u63A5mongodb\u5931\u8D25", e);
            throw new RuntimeException("\u8FDE\u63A5mongodb\u5931\u8D25", e);
        }
    }

    public static synchronized MetaDBMongoFactory getInstance()
    {
        if(instance == null)
            throw new RuntimeException("\u8BF7\u5148\u521D\u59CB\u5316MetaDBMongoFactory");
        else
            return instance;
    }

    public static synchronized MetaDBMongoFactory init(String host, String port, String db, String user, String password, String readPreference)
    {
        if(instance == null)
            instance = new MetaDBMongoFactory(host, port, db, user, password, readPreference);
        return instance;
    }

    public DB getMongoDB()
    {
        return mongoDB;
    }

    public MongoTemplate getMongoTemplate()
    {
        return mongoTemplate;
    }

    public static synchronized MetaDBMongoFactory changeDB(String host, String port, String db, String user, String password, String readPreference)
    {
        instance = new MetaDBMongoFactory(host, port, db, user, password, readPreference);
        return instance;
    }

    public static boolean hasInit()
    {
        return instance != null;
    }

    private static final Logger LOG = Logger.getLogger(com/sinitek/base/mongodb/MetaDBMongoFactory);
    private static MetaDBMongoFactory instance;
    private MongoTemplate mongoTemplate;
    private DB mongoDB;

}
