// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassUtils.java

package org.apache.commons.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

// Referenced classes of package org.apache.commons.lang:
//            NullArgumentException, ArrayUtils, StringUtils

public class ClassUtils
{

    public ClassUtils()
    {
    }

    public static String getShortClassName(Object object, String valueIfNull)
    {
        if(object == null)
            return valueIfNull;
        else
            return getShortClassName(object.getClass().getName());
    }

    public static String getShortClassName(Class cls)
    {
        if(cls == null)
            return "";
        else
            return getShortClassName(cls.getName());
    }

    public static String getShortClassName(String className)
    {
        if(className == null)
            return "";
        if(className.length() == 0)
            return "";
        char chars[] = className.toCharArray();
        int lastDot = 0;
        for(int i = 0; i < chars.length; i++)
            if(chars[i] == '.')
                lastDot = i + 1;
            else
            if(chars[i] == '$')
                chars[i] = '.';

        return new String(chars, lastDot, chars.length - lastDot);
    }

    public static String getPackageName(Object object, String valueIfNull)
    {
        if(object == null)
            return valueIfNull;
        else
            return getPackageName(object.getClass().getName());
    }

    public static String getPackageName(Class cls)
    {
        if(cls == null)
            return "";
        else
            return getPackageName(cls.getName());
    }

    public static String getPackageName(String className)
    {
        if(className == null)
            return "";
        int i = className.lastIndexOf('.');
        if(i == -1)
            return "";
        else
            return className.substring(0, i);
    }

    public static List getAllSuperclasses(Class cls)
    {
        if(cls == null)
            return null;
        List classes = new ArrayList();
        for(Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass())
            classes.add(superclass);

        return classes;
    }

    public static List getAllInterfaces(Class cls)
    {
        if(cls == null)
            return null;
        List list = new ArrayList();
        for(; cls != null; cls = cls.getSuperclass())
        {
            Class interfaces[] = cls.getInterfaces();
            for(int i = 0; i < interfaces.length; i++)
            {
                if(!list.contains(interfaces[i]))
                    list.add(interfaces[i]);
                List superInterfaces = getAllInterfaces(interfaces[i]);
                for(Iterator it = superInterfaces.iterator(); it.hasNext();)
                {
                    Class intface = (Class)it.next();
                    if(!list.contains(intface))
                        list.add(intface);
                }

            }

        }

        return list;
    }

    public static List convertClassNamesToClasses(List classNames)
    {
        if(classNames == null)
            return null;
        List classes = new ArrayList(classNames.size());
        for(Iterator it = classNames.iterator(); it.hasNext();)
        {
            String className = (String)it.next();
            try
            {
                classes.add(Class.forName(className));
            }
            catch(Exception ex)
            {
                classes.add(null);
            }
        }

        return classes;
    }

    public static List convertClassesToClassNames(List classes)
    {
        if(classes == null)
            return null;
        List classNames = new ArrayList(classes.size());
        for(Iterator it = classes.iterator(); it.hasNext();)
        {
            Class cls = (Class)it.next();
            if(cls == null)
                classNames.add(null);
            else
                classNames.add(cls.getName());
        }

        return classNames;
    }

    public static boolean isAssignable(Class classArray[], Class toClassArray[])
    {
        if(!ArrayUtils.isSameLength(classArray, toClassArray))
            return false;
        if(classArray == null)
            classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
        if(toClassArray == null)
            toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
        for(int i = 0; i < classArray.length; i++)
            if(!isAssignable(classArray[i], toClassArray[i]))
                return false;

        return true;
    }

    public static boolean isAssignable(Class cls, Class toClass)
    {
        if(toClass == null)
            return false;
        if(cls == null)
            return !toClass.isPrimitive();
        if(cls.equals(toClass))
            return true;
        if(cls.isPrimitive())
        {
            if(!toClass.isPrimitive())
                return false;
            if(Integer.TYPE.equals(cls))
                return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if(Long.TYPE.equals(cls))
                return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if(Boolean.TYPE.equals(cls))
                return false;
            if(Double.TYPE.equals(cls))
                return false;
            if(Float.TYPE.equals(cls))
                return Double.TYPE.equals(toClass);
            if(Character.TYPE.equals(cls))
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if(Short.TYPE.equals(cls))
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if(Byte.TYPE.equals(cls))
                return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            else
                return false;
        } else
        {
            return toClass.isAssignableFrom(cls);
        }
    }

    public static Class primitiveToWrapper(Class cls)
    {
        Class convertedClass = cls;
        if(cls != null && cls.isPrimitive())
            convertedClass = (Class)primitiveWrapperMap.get(cls);
        return convertedClass;
    }

    public static Class[] primitivesToWrappers(Class classes[])
    {
        if(classes == null)
            return null;
        if(classes.length == 0)
            return classes;
        Class convertedClasses[] = new Class[classes.length];
        for(int i = 0; i < classes.length; i++)
            convertedClasses[i] = primitiveToWrapper(classes[i]);

        return convertedClasses;
    }

    public static boolean isInnerClass(Class cls)
    {
        if(cls == null)
            return false;
        else
            return cls.getName().indexOf('$') >= 0;
    }

    public static Class getClass(ClassLoader classLoader, String className, boolean initialize)
        throws ClassNotFoundException
    {
        Class clazz;
        if(abbreviationMap.containsKey(className))
        {
            String clsName = "[" + abbreviationMap.get(className);
            clazz = Class.forName(clsName, initialize, classLoader).getComponentType();
        } else
        {
            clazz = Class.forName(toProperClassName(className), initialize, classLoader);
        }
        return clazz;
    }

    public static Class getClass(ClassLoader classLoader, String className)
        throws ClassNotFoundException
    {
        return getClass(classLoader, className, true);
    }

    public static Class getClass(String className)
        throws ClassNotFoundException
    {
        return getClass(className, true);
    }

    public static Class getClass(String className, boolean initialize)
        throws ClassNotFoundException
    {
        ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = contextCL != null ? contextCL : (org.apache.commons.lang.ClassUtils.class).getClassLoader();
        return getClass(loader, className, initialize);
    }

    public static Method getPublicMethod(Class cls, String methodName, Class parameterTypes[])
        throws SecurityException, NoSuchMethodException
    {
        Method declaredMethod = cls.getMethod(methodName, parameterTypes);
        if(Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers()))
            return declaredMethod;
        List candidateClasses = new ArrayList();
        candidateClasses.addAll(getAllInterfaces(cls));
        candidateClasses.addAll(getAllSuperclasses(cls));
        Iterator it = candidateClasses.iterator();
        while(it.hasNext()) 
        {
            Class candidateClass = (Class)it.next();
            if(!Modifier.isPublic(candidateClass.getModifiers()))
                continue;
            Method candidateMethod;
            try
            {
                candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
            }
            catch(NoSuchMethodException ex)
            {
                continue;
            }
            if(Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers()))
                return candidateMethod;
        }
        throw new NoSuchMethodException("Can't find a public method for " + methodName + " " + ArrayUtils.toString(parameterTypes));
    }

    private static String toProperClassName(String className)
    {
        className = StringUtils.deleteWhitespace(className);
        if(className == null)
            throw new NullArgumentException("className");
        if(className.endsWith("[]"))
        {
            StringBuffer classNameBuffer = new StringBuffer();
            for(; className.endsWith("[]"); classNameBuffer.append("["))
                className = className.substring(0, className.length() - 2);

            String abbreviation = (String)abbreviationMap.get(className);
            if(abbreviation != null)
                classNameBuffer.append(abbreviation);
            else
                classNameBuffer.append("L").append(className).append(";");
            className = classNameBuffer.toString();
        }
        return className;
    }

    public static final char PACKAGE_SEPARATOR_CHAR = 46;
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final char INNER_CLASS_SEPARATOR_CHAR = 36;
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
    private static Map primitiveWrapperMap;
    private static Map abbreviationMap;

    static 
    {
        primitiveWrapperMap = new HashMap();
        primitiveWrapperMap.put(Boolean.TYPE, java.lang.Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, java.lang.Byte.class);
        primitiveWrapperMap.put(Character.TYPE, java.lang.Character.class);
        primitiveWrapperMap.put(Short.TYPE, java.lang.Short.class);
        primitiveWrapperMap.put(Integer.TYPE, java.lang.Integer.class);
        primitiveWrapperMap.put(Long.TYPE, java.lang.Long.class);
        primitiveWrapperMap.put(Double.TYPE, java.lang.Double.class);
        primitiveWrapperMap.put(Float.TYPE, java.lang.Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        abbreviationMap = new HashMap();
        abbreviationMap.put("int", "I");
        abbreviationMap.put("boolean", "Z");
        abbreviationMap.put("float", "F");
        abbreviationMap.put("long", "J");
        abbreviationMap.put("short", "S");
        abbreviationMap.put("byte", "B");
        abbreviationMap.put("double", "D");
        abbreviationMap.put("char", "C");
    }
}
