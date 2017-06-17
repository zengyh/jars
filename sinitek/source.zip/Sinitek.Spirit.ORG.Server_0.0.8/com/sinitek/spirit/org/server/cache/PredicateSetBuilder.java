// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PredicateSetBuilder.java

package com.sinitek.spirit.org.server.cache;

import java.util.*;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            Predicate, PredicateSet

final class PredicateSetBuilder
{
    private final class PredicateSetImpl
        implements PredicateSet
    {

        public Set getPredicates()
        {
            return predicates;
        }

        private String buildStringValue(Set predicates)
        {
            StringBuilder sb = new StringBuilder("(");
            boolean first = true;
            for(Iterator i$ = predicates.iterator(); i$.hasNext();)
            {
                Predicate predicate = (Predicate)i$.next();
                if(first)
                {
                    sb.append(predicate.toString());
                    first = false;
                } else
                {
                    sb.append("; ").append(predicate.toString());
                }
            }

            sb.append(")");
            return sb.toString();
        }

        public String toString()
        {
            return stringValue;
        }

        public int hashCode()
        {
            return stringValue.hashCode();
        }

        public boolean equals(Object obj)
        {
            if(obj == null)
                return false;
            if(this == obj)
                return true;
            if(obj instanceof PredicateSet)
            {
                PredicateSet predicateSet = (PredicateSet)obj;
                return stringValue.equals(predicateSet.toString());
            } else
            {
                return false;
            }
        }

        private final Set predicates;
        private final String stringValue;
        final PredicateSetBuilder this$0;

        public PredicateSetImpl(Collection predicates)
        {
            this$0 = PredicateSetBuilder.this;
            super();
            this.predicates = Collections.unmodifiableSet(new TreeSet(predicates));
            stringValue = buildStringValue(this.predicates);
        }
    }


    public PredicateSetBuilder()
    {
    }

    public PredicateSetBuilder add(Predicate predicate)
    {
        if(predicate != null)
            predicates.add(predicate);
        return this;
    }

    public PredicateSet build()
    {
        return new PredicateSetImpl(predicates);
    }

    private final Collection predicates = new LinkedList();
}
