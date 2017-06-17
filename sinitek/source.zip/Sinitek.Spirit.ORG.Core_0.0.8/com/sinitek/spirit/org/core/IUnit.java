// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUnit.java

package com.sinitek.spirit.org.core;


// Referenced classes of package com.sinitek.spirit.org.core:
//            IOrgObject

public interface IUnit
    extends IOrgObject
{
    public static final class UnitType extends Enum
    {

        public static UnitType[] values()
        {
            return (UnitType[])$VALUES.clone();
        }

        public static UnitType valueOf(String name)
        {
            return (UnitType)Enum.valueOf(com/sinitek/spirit/org/core/IUnit$UnitType, name);
        }

        public static final UnitType UNIT;
        public static final UnitType TEAM;
        public static final UnitType POSITION;
        private static final UnitType $VALUES[];

        static 
        {
            UNIT = new UnitType("UNIT", 0);
            TEAM = new UnitType("TEAM", 1);
            POSITION = new UnitType("POSITION", 2);
            $VALUES = (new UnitType[] {
                UNIT, TEAM, POSITION
            });
        }

        private UnitType(String s, int i)
        {
            super(s, i);
        }
    }


    public abstract String getUnitType();
}
