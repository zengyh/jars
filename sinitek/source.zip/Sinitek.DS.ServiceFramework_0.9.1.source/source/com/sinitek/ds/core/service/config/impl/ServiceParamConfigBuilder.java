// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceParamConfigBuilder.java

package com.sinitek.ds.core.service.config.impl;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.framework.dict.DictionaryContext;
import com.sinitek.ds.core.service.ServiceException;
import com.sinitek.ds.core.service.ServiceRuntimeException;
import com.sinitek.ds.core.service.config.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

public class ServiceParamConfigBuilder
{
    public static class EnumParamConfig extends AbstractParamConfig
    {

        public String getEnumName()
        {
            return enumName;
        }

        public boolean isIntType()
        {
            return intType;
        }

        protected void checkValue(Object value)
            throws ServiceException
        {
            try
            {
                if(intType)
                {
                    int iValue = ((Integer)value).intValue();
                    EnumItemContext.getInstance().getEnumItem(enumName, iValue);
                } else
                {
                    EnumItemContext.getInstance().getEnumItem(enumName, (String)value);
                }
            }
            catch(Exception ex)
            {
                throw new ServiceRuntimeException("0009", ex, new Object[] {
                    value, enumName
                });
            }
        }

        public ServiceParamType getParamType()
        {
            return ServiceParamType.ENUM;
        }

        private String enumName;
        private boolean intType;

        protected EnumParamConfig(String paramName, String enumName, boolean intType)
        {
            super(paramName);
            this.enumName = enumName;
            this.intType = intType;
        }
    }

    public static class DictParamConfig extends AbstractParamConfig
    {

        public int getKey()
        {
            return key;
        }

        protected void checkValue(Object value)
            throws ServiceException
        {
            String szValue = (String)value;
            try
            {
                DictionaryContext.getInstance().getDictionaryItem(key, szValue);
            }
            catch(Exception e)
            {
                throw new ServiceRuntimeException("0008", e, new Object[] {
                    szValue, new Integer(key)
                });
            }
        }

        public ServiceParamType getParamType()
        {
            return ServiceParamType.DICT;
        }

        private int key;

        protected DictParamConfig(String paramName, int key)
        {
            super(paramName);
            this.key = key;
        }
    }

    private static class DateTimeParamConfig extends AbstractParamConfig
    {

        protected void checkValue(Object value)
            throws ServiceException
        {
            String szValue = (String)value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try
            {
                sdf.parse(szValue);
            }
            catch(ParseException e)
            {
                throw new ServiceRuntimeException("0007", e, new Object[] {
                    szValue, pattern
                });
            }
        }

        public ServiceParamType getParamType()
        {
            return type;
        }

        private ServiceParamType type;
        private String pattern;

        protected DateTimeParamConfig(String paramName, ServiceParamType type, String pattern)
        {
            super(paramName);
            this.type = type;
            this.pattern = pattern;
        }
    }

    private static class BooleanParamConfig extends AbstractParamConfig
    {

        protected void checkValue(Object obj)
            throws ServiceException
        {
        }

        public ServiceParamType getParamType()
        {
            return ServiceParamType.BOOLEAN;
        }

        protected BooleanParamConfig(String paramName)
        {
            super(paramName);
        }
    }

    private static class NumericParamConfig extends AbstractParamConfig
    {

        protected void checkValue(Object value)
            throws ServiceException
        {
            Number num = (Number)value;
            if(maxValue != null && maxValue.doubleValue() < num.doubleValue())
                throw new ServiceRuntimeException("0005", new Object[] {
                    paramName, maxValue, num
                });
            if(minValue != null && minValue.doubleValue() > num.doubleValue())
                throw new ServiceRuntimeException("0006", new Object[] {
                    paramName, minValue, num
                });
            else
                return;
        }

        public ServiceParamType getParamType()
        {
            return type;
        }

        private Number maxValue;
        private Number minValue;
        private ServiceParamType type;



        protected NumericParamConfig(String paramName, ServiceParamType type)
        {
            super(paramName);
            this.type = type;
        }
    }

    private static class StringParamConfig extends AbstractParamConfig
    {

        protected void checkValue(Object value)
            throws ServiceException
        {
            String szValue = (String)value;
            if(maxLength != null && maxLength.intValue() > 0 && szValue.length() > maxLength.intValue())
                throw new ServiceRuntimeException("0002", new Object[] {
                    paramName, maxLength, new Integer(szValue.length())
                });
            if(minLength != null && minLength.intValue() > 0 && szValue.length() < minLength.intValue())
                throw new ServiceRuntimeException("0003", new Object[] {
                    minLength, new Integer(szValue.length())
                });
            else
                return;
        }

        public ServiceParamType getParamType()
        {
            return ServiceParamType.STRING;
        }

        private Integer maxLength;
        private Integer minLength;



        protected StringParamConfig(String paramName)
        {
            super(paramName);
        }
    }

    private static abstract class AbstractParamConfig
        implements IServiceParamConfig
    {

        public String getParamName()
        {
            return paramName;
        }

        public boolean isNotNull()
        {
            return notNull;
        }

        public void setNotNull(boolean notNull)
        {
            this.notNull = notNull;
        }

        public boolean isKeyFlag()
        {
            return keyFlag;
        }

        public void setKeyFlag(boolean keyFlag)
        {
            this.keyFlag = keyFlag;
        }

        public String getComment()
        {
            return comment;
        }

        public void setComment(String comment)
        {
            this.comment = comment;
        }

        public String getInfo()
        {
            return info;
        }

        public void setInfo(String info)
        {
            this.info = info;
        }

        public final void checkParamValue(Object value)
            throws ServiceException
        {
            if(notNull && (value == null || (value instanceof String) && StringUtils.isBlank(value.toString())))
                throw new ServiceRuntimeException("0001", new Object[] {
                    paramName
                });
            if(value != null && (value instanceof String) && StringUtils.isNotBlank(value.toString()))
            {
                checkType(value);
                checkValue(value);
            }
        }

        protected void checkType(Object value)
            throws ServiceException
        {
            ServiceParamType type = getParamType();
            Object errorParams[] = {
                paramName, type.getEnumItemName(), value.getClass().getName()
            };
            if(type.equals(ServiceParamType.STRING) && !(value instanceof String))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.BOOLEAN) && !(value instanceof Boolean))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.DATE) && !(value instanceof String))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.DATETIME) && !(value instanceof String))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.DICT) && !(value instanceof String))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.ENUM) && !(value instanceof String) && !(value instanceof Integer))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.TIME) && !(value instanceof String))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.FLOAT) && !(value instanceof Float))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.DOUBLE) && !(value instanceof Double))
                throw new ServiceRuntimeException("0004", errorParams);
            if(type.equals(ServiceParamType.INTEGER) && !(value instanceof Integer))
                throw new ServiceRuntimeException("0004", errorParams);
            else
                return;
        }

        protected abstract void checkValue(Object obj)
            throws ServiceException;

        protected String paramName;
        protected boolean notNull;
        protected String info;
        protected String comment;
        protected boolean keyFlag;

        protected AbstractParamConfig(String paramName)
        {
            this.paramName = paramName;
        }
    }


    public ServiceParamConfigBuilder()
    {
    }

    public static IServiceParamConfig createParamConfig(Element paramElement)
    {
        String paramName = paramElement.getAttribute("name");
        boolean notNull = "true".equalsIgnoreCase(paramElement.getAttribute("isNotNull"));
        boolean keyFlag = "true".equalsIgnoreCase(paramElement.getAttribute("keyFlag"));
        AbstractParamConfig paramConfig;
        if(paramElement.getNodeName().equals("string"))
        {
            StringParamConfig ret = new StringParamConfig(paramName);
            ret.setNotNull(notNull);
            ret.setKeyFlag(keyFlag);
            String szMaxLength = paramElement.getAttribute("maxLength");
            if(StringUtils.isNotBlank(szMaxLength))
                ret.maxLength = new Integer(szMaxLength);
            String szMinLength = paramElement.getAttribute("minLength");
            if(StringUtils.isNotBlank(szMinLength))
                ret.minLength = new Integer(szMinLength);
            paramConfig = ret;
        } else
        if(paramElement.getNodeName().equals("integer"))
        {
            NumericParamConfig ret = new NumericParamConfig(paramName, ServiceParamType.INTEGER);
            ret.setNotNull(notNull);
            ret.setKeyFlag(keyFlag);
            String szMaxValue = paramElement.getAttribute("maxValue");
            if(StringUtils.isNotBlank(szMaxValue))
                ret.maxValue = new Integer(szMaxValue);
            String szMinValue = paramElement.getAttribute("minValue");
            if(StringUtils.isNotBlank(szMinValue))
                ret.minValue = new Integer(szMinValue);
            paramConfig = ret;
        } else
        if(paramElement.getNodeName().equals("float"))
        {
            NumericParamConfig ret = new NumericParamConfig(paramName, ServiceParamType.FLOAT);
            ret.setNotNull(notNull);
            ret.setKeyFlag(keyFlag);
            String szMaxValue = paramElement.getAttribute("maxValue");
            if(StringUtils.isNotBlank(szMaxValue))
                ret.maxValue = new Float(szMaxValue);
            String szMinValue = paramElement.getAttribute("minValue");
            if(StringUtils.isNotBlank(szMinValue))
                ret.minValue = new Float(szMinValue);
            paramConfig = ret;
        } else
        if(paramElement.getNodeName().equals("double"))
        {
            NumericParamConfig ret = new NumericParamConfig(paramName, ServiceParamType.DOUBLE);
            ret.setNotNull(notNull);
            ret.setKeyFlag(keyFlag);
            String szMaxValue = paramElement.getAttribute("maxValue");
            if(StringUtils.isNotBlank(szMaxValue))
                ret.maxValue = new Double(szMaxValue);
            String szMinValue = paramElement.getAttribute("minValue");
            if(StringUtils.isNotBlank(szMinValue))
                ret.minValue = new Double(szMinValue);
            paramConfig = ret;
        } else
        if(paramElement.getNodeName().equals("boolean"))
            paramConfig = new BooleanParamConfig(paramName);
        else
        if(paramElement.getNodeName().equals("date"))
            paramConfig = new DateTimeParamConfig(paramName, ServiceParamType.DATE, "yyyyMMdd");
        else
        if(paramElement.getNodeName().equals("time"))
            paramConfig = new DateTimeParamConfig(paramName, ServiceParamType.TIME, "HHmmss");
        else
        if(paramElement.getNodeName().equals("datetime"))
            paramConfig = new DateTimeParamConfig(paramName, ServiceParamType.DATETIME, "yyyyMMddHHmmss");
        else
        if(paramElement.getNodeName().equals("enum"))
        {
            String enumName = paramElement.getAttribute("enumName");
            boolean intValue = "integer".equalsIgnoreCase(paramElement.getAttribute("valueType"));
            paramConfig = new EnumParamConfig(paramName, enumName, intValue);
        } else
        if(paramElement.getNodeName().equals("dict"))
        {
            int key = Integer.parseInt(paramElement.getAttribute("key"));
            paramConfig = new DictParamConfig(paramName, key);
        } else
        {
            throw new ServiceConfigException("0003", new Object[] {
                paramElement.getNodeName()
            });
        }
        paramConfig.setComment(paramElement.getAttribute("comment"));
        paramConfig.setInfo(paramElement.getAttribute("info"));
        paramConfig.setNotNull(notNull);
        paramConfig.setKeyFlag(keyFlag);
        return paramConfig;
    }
}
