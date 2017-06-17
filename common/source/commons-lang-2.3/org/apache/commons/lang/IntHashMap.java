// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntHashMap.java

package org.apache.commons.lang;


class IntHashMap
{
    private static class Entry
    {

        int hash;
        int key;
        Object value;
        Entry next;

        protected Entry(int hash, int key, Object value, Entry next)
        {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    public IntHashMap()
    {
        this(20, 0.75F);
    }

    public IntHashMap(int initialCapacity)
    {
        this(initialCapacity, 0.75F);
    }

    public IntHashMap(int initialCapacity, float loadFactor)
    {
        if(initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        if(loadFactor <= 0.0F)
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        if(initialCapacity == 0)
            initialCapacity = 1;
        this.loadFactor = loadFactor;
        table = new Entry[initialCapacity];
        threshold = (int)((float)initialCapacity * loadFactor);
    }

    public int size()
    {
        return count;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public boolean contains(Object value)
    {
        if(value == null)
            throw new NullPointerException();
        Entry tab[] = table;
        for(int i = tab.length; i-- > 0;)
        {
            for(Entry e = tab[i]; e != null; e = e.next)
                if(e.value.equals(value))
                    return true;

        }

        return false;
    }

    public boolean containsValue(Object value)
    {
        return contains(value);
    }

    public boolean containsKey(int key)
    {
        Entry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for(Entry e = tab[index]; e != null; e = e.next)
            if(e.hash == hash)
                return true;

        return false;
    }

    public Object get(int key)
    {
        Entry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for(Entry e = tab[index]; e != null; e = e.next)
            if(e.hash == hash)
                return e.value;

        return null;
    }

    protected void rehash()
    {
        int oldCapacity = table.length;
        Entry oldMap[] = table;
        int newCapacity = oldCapacity * 2 + 1;
        Entry newMap[] = new Entry[newCapacity];
        threshold = (int)((float)newCapacity * loadFactor);
        table = newMap;
        for(int i = oldCapacity; i-- > 0;)
        {
            for(Entry old = oldMap[i]; old != null;)
            {
                Entry e = old;
                old = old.next;
                int index = (e.hash & 0x7fffffff) % newCapacity;
                e.next = newMap[index];
                newMap[index] = e;
            }

        }

    }

    public Object put(int key, Object value)
    {
        Entry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for(Entry e = tab[index]; e != null; e = e.next)
            if(e.hash == hash)
            {
                Object old = e.value;
                e.value = value;
                return old;
            }

        if(count >= threshold)
        {
            rehash();
            tab = table;
            index = (hash & 0x7fffffff) % tab.length;
        }
        Entry e = new Entry(hash, key, value, tab[index]);
        tab[index] = e;
        count++;
        return null;
    }

    public Object remove(int key)
    {
        Entry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        Entry e = tab[index];
        Entry prev = null;
        for(; e != null; e = e.next)
        {
            if(e.hash == hash)
            {
                if(prev != null)
                    prev.next = e.next;
                else
                    tab[index] = e.next;
                count--;
                Object oldValue = e.value;
                e.value = null;
                return oldValue;
            }
            prev = e;
        }

        return null;
    }

    public synchronized void clear()
    {
        Entry tab[] = table;
        for(int index = tab.length; --index >= 0;)
            tab[index] = null;

        count = 0;
    }

    private transient Entry table[];
    private transient int count;
    private int threshold;
    private float loadFactor;
}
