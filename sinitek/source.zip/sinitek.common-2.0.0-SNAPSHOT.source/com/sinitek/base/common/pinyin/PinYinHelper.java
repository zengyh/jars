// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PinYinHelper.java

package com.sinitek.base.common.pinyin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class PinYinHelper
{

    public PinYinHelper()
    {
        hypy = new HashMap();
    }

    public static synchronized PinYinHelper getInstance()
    {
        if(instance == null)
        {
            instance = new PinYinHelper();
            instance.loadPinYin();
        }
        return instance;
    }

    public String getFullPinYin(String sentence)
    {
        char charset[] = sentence.toCharArray();
        int length = charset.length;
        StringBuffer sb = new StringBuffer(length);
        for(int i = 0; i < length; i++)
        {
            char c = charset[i];
            if(hypy.get(String.valueOf(c)) != null)
            {
                ArrayList list = (ArrayList)hypy.get(String.valueOf(c));
                sb.append(list.get(0));
            } else
            {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public String getSimplePinYin(String sentence)
    {
        char charset[] = sentence.toCharArray();
        int length = charset.length;
        StringBuffer sb = new StringBuffer(length);
        for(int i = 0; i < length; i++)
        {
            char c = charset[i];
            if(hypy.get(String.valueOf(c)) != null)
            {
                ArrayList list = (ArrayList)hypy.get(String.valueOf(c));
                String s = (String)list.get(0);
                sb.append(s.charAt(0));
            } else
            {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public List traslatePinYins(String sentence)
    {
        List _result = new ArrayList();
        char charset[] = sentence.toCharArray();
        if(charset.length > 0)
        {
            List _pinyins = (List)hypy.get(String.valueOf(charset[0]));
            if(_pinyins == null)
            {
                String _str = getSimplePinYin(sentence);
                _result.add(_str);
                return _result;
            }
            String _item;
            for(Iterator it = _pinyins.iterator(); it.hasNext(); _result.add(_item.substring(0, 1)))
                _item = (String)it.next();

            if(charset.length > 1)
            {
                String _str = getSimplePinYin(sentence.substring(1));
                for(int i = 0; i < _result.size(); i++)
                    _result.set(i, (new StringBuilder()).append(_result.get(i)).append(_str).toString());

            }
            return _result;
        } else
        {
            return _result;
        }
    }

    public void loadPinYin()
    {
        try
        {
            java.io.InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/base/common/pinyin/pinyin.txt");
            InputStreamReader rdr = new InputStreamReader(in);
            BufferedReader brdr = new BufferedReader(rdr);
            do
            {
                String s;
                if((s = brdr.readLine()) == null)
                    break;
                String items[] = s.split(":|,");
                int length = items.length;
                if(length > 1)
                {
                    ArrayList list = new ArrayList(length - 1);
                    for(int i = 1; i < length; i++)
                    {
                        String value = items[i].trim();
                        if(value.compareTo("") != 0)
                            list.add(value);
                    }

                    hypy.put(items[0].trim(), list);
                }
            } while(true);
            brdr.close();
            rdr.close();
        }
        catch(Exception ex)
        {
            throw new RuntimeException("\u8BFB\u53D6\u62FC\u97F3\u6570\u636E\u5B57\u5178\u6587\u4EF6\u5931\u8D25", ex);
        }
    }

    private HashMap hypy;
    private static PinYinHelper instance;
}
