// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUIConfigCatalog.java

package com.sinitek.sirm.common.um.enums;


public final class UserUIConfigCatalog extends Enum
{

    public static UserUIConfigCatalog[] values()
    {
        return (UserUIConfigCatalog[])$VALUES.clone();
    }

    public static UserUIConfigCatalog valueOf(String name)
    {
        return (UserUIConfigCatalog)Enum.valueOf(com/sinitek/sirm/common/um/enums/UserUIConfigCatalog, name);
    }

    private UserUIConfigCatalog(String s, int i)
    {
        super(s, i);
    }

    public static final UserUIConfigCatalog TABLE_PAGE_SIZE;
    public static final UserUIConfigCatalog HOME_PAGE;
    public static final UserUIConfigCatalog THEME;
    public static final UserUIConfigCatalog I18N;
    private static final UserUIConfigCatalog $VALUES[];

    static 
    {
        TABLE_PAGE_SIZE = new UserUIConfigCatalog("TABLE_PAGE_SIZE", 0);
        HOME_PAGE = new UserUIConfigCatalog("HOME_PAGE", 1);
        THEME = new UserUIConfigCatalog("THEME", 2);
        I18N = new UserUIConfigCatalog("I18N", 3);
        $VALUES = (new UserUIConfigCatalog[] {
            TABLE_PAGE_SIZE, HOME_PAGE, THEME, I18N
        });
    }
}
