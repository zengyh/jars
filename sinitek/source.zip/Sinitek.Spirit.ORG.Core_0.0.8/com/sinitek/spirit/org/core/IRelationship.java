// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRelationship.java

package com.sinitek.spirit.org.core;


// Referenced classes of package com.sinitek.spirit.org.core:
//            IOrgObject

public interface IRelationship
{
    public static final class RelationshipType extends Enum
    {

        public static RelationshipType[] values()
        {
            return (RelationshipType[])$VALUES.clone();
        }

        public static RelationshipType valueOf(String name)
        {
            return (RelationshipType)Enum.valueOf(com/sinitek/spirit/org/core/IRelationship$RelationshipType, name);
        }

        public static final RelationshipType UNDERLINE;
        public static final RelationshipType SUPERVISION;
        private static final RelationshipType $VALUES[];

        static 
        {
            UNDERLINE = new RelationshipType("UNDERLINE", 0);
            SUPERVISION = new RelationshipType("SUPERVISION", 1);
            $VALUES = (new RelationshipType[] {
                UNDERLINE, SUPERVISION
            });
        }

        private RelationshipType(String s, int i)
        {
            super(s, i);
        }
    }


    public abstract String getType();

    public abstract IOrgObject getFromObject();

    public abstract IOrgObject getToObject();
}
