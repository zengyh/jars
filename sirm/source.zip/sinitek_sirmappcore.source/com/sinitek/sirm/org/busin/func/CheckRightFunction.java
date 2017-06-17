// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckRightFunction.java

package com.sinitek.sirm.org.busin.func;

import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.enumerate.OrgType;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.IRightChecker;
import com.sinitek.spirit.right.core.righttype.AccessRightType;
import com.sinitek.spirit.web.sysmenu.ISysMenu;

public class CheckRightFunction
{

    public CheckRightFunction()
    {
    }

    public static boolean checkRight(String userid, ISysMenu menu)
    {
        boolean result = false;
        IRightChecker _check = RightFactory.getRightChecker();
        result = _check.checkRight(userid, menu, AccessRightType.ACCESS);
        return result;
    }

    public static boolean checkRoleFuncRight(String roleid, String rightObjId)
    {
        boolean result = false;
        IRightService svc = OrgServiceFactory.getRightService();
        result = svc.checkRight(new OrgObject(roleid, OrgType.ROLE.getEnumItemValue()), rightObjId, "FUNCTIONINFO", "ACCESS");
        return result;
    }
}
