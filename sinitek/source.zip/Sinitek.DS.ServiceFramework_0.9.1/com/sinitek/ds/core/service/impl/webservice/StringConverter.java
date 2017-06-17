// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringConverter.java

package com.sinitek.ds.core.service.impl.webservice;

import com.sinitek.ds.core.service.*;
import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.*;

// Referenced classes of package com.sinitek.ds.core.service.impl.webservice:
//            ServiceWebServiceClientException

public class StringConverter
{

    public StringConverter()
    {
    }

    public static String toString(IServiceRequest request)
    {
        StringBuffer ret = new StringBuffer();
        ret.append("00");
        String functionCode = request.getFunctionCode();
        dealFunctionCode(ret, functionCode);
        ret.append("00000000");
        ret.append("      ");
        int bodyLength = genString(ret, request);
        String strBodyLength = (new DecimalFormat("000000")).format(bodyLength);
        ret.replace(26, 32, strBodyLength);
        return ret.toString();
    }

    public static String toString(String functionCode, IServiceResponse response)
    {
        StringBuffer ret = new StringBuffer();
        ret.append("1");
        boolean isMultiPack = response.getRecordCount() > 0;
        ret.append(isMultiPack ? "1" : "0");
        dealFunctionCode(ret, functionCode);
        String returnCode = response.getReturnCode();
        if(getStringLength(returnCode) != 8)
            throw new ServiceWebServiceClientException("0004", new Object[] {
                returnCode
            });
        ret.append(returnCode);
        ret.append("      ");
        String retMsg = response.getReturnMessage();
        String typeStr = getValueType(retMsg);
        int bodyLength = appendItem(ret, typeStr, "resp_returnmsg", retMsg) + 9;
        bodyLength += genString(ret, response);
        if(isMultiPack)
        {
            ret.append("000000012MULTISTART|0");
            bodyLength += "000000012MULTISTART|0".length();
            for(int i = 0; i < response.getRecordCount(); i++)
            {
                if(i != 0)
                {
                    ret.append("000000010MULTISEP|0");
                    bodyLength += "000000010MULTISEP|0".length();
                }
                IParamObject po = (IParamObject)response.next();
                bodyLength += genString(ret, po);
            }

            ret.append("000000010MULTIEND|0");
            bodyLength += "000000010MULTIEND|0".length();
        }
        String strBodyLength = (new DecimalFormat("000000")).format(bodyLength);
        ret.replace(26, 32, strBodyLength);
        return ret.toString();
    }

    public static IServiceRequest getRequestFromString(String requestString)
    {
        byte reqData[] = strToBytes(requestString);
        if(reqData.length <= 32)
            throw new ServiceWebServiceClientException("0005", new Object[] {
                new Integer(reqData.length)
            });
        if(!"0".equals(getString(reqData, 0, 1)))
            throw new ServiceWebServiceClientException("0008");
        String functionCode = getString(reqData, 2, 16).trim();
        int bodyLength = Integer.parseInt(getString(reqData, 26, 6).trim());
        if(bodyLength + 32 != reqData.length)
            throw new ServiceWebServiceClientException("0009", new Object[] {
                new Integer(bodyLength + 32), new Integer(reqData.length)
            });
        SimpleServiceRequest request = new SimpleServiceRequest(functionCode);
        for(int pos = 32; pos < reqData.length;)
        {
            String dataType = getString(reqData, pos, 1);
            pos++;
            int dataLength = Integer.parseInt(getString(reqData, pos, 8));
            pos += 8;
            String dataStr = getString(reqData, pos, dataLength);
            pos += dataLength;
            int sepIndex = dataStr.indexOf('|');
            if(sepIndex < 0)
                throw new ServiceWebServiceClientException("0010", new Object[] {
                    dataStr
                });
            String itemName = dataStr.substring(0, sepIndex);
            String strItemValue = dataStr.substring(sepIndex + 1);
            request.addParam(itemName, getObject(dataType, strItemValue));
        }

        return request;
    }

    public static IServiceResponse getResponseFromString(String responseString)
    {
        byte respData[] = strToBytes(responseString);
        if(respData.length < 32)
            throw new ServiceWebServiceClientException("0005", new Object[] {
                new Integer(respData.length)
            });
        if(!"1".equals(getString(respData, 0, 1)))
            throw new ServiceWebServiceClientException("0011");
        String returnCode = getString(respData, 18, 8);
        int bodyLength = Integer.parseInt(getString(respData, 26, 6).trim());
        if(bodyLength + 32 != respData.length)
            throw new ServiceWebServiceClientException("0009", new Object[] {
                new Integer(bodyLength + 32), new Integer(respData.length)
            });
        SimpleServiceResponse ret;
        if("00000000".equals(returnCode))
            ret = SimpleServiceResponse.createSuccessResponse("");
        else
            ret = SimpleServiceResponse.createFailResponse(returnCode, "");
        SimpleParamObject container = ret;
        int pos = 32;
        boolean retMsgFlag = false;
        do
        {
            if(pos >= respData.length)
                break;
            String dataType = getString(respData, pos, 1);
            pos++;
            boolean sysFlag = "0".equals(dataType);
            int dataLength = Integer.parseInt(getString(respData, pos, 8));
            pos += 8;
            String dataStr = getString(respData, pos, dataLength);
            pos += dataLength;
            int sepIndex = dataStr.indexOf('|');
            if(sepIndex < 0)
                throw new ServiceWebServiceClientException("0010", new Object[] {
                    dataStr
                });
            String itemName = dataStr.substring(0, sepIndex);
            if(sysFlag)
            {
                if("MULTISTART".equalsIgnoreCase(itemName))
                {
                    container = new SimpleParamObject();
                    ret.addRecord(container);
                    continue;
                }
                if("MULTISEP".equalsIgnoreCase(itemName))
                {
                    container = new SimpleParamObject();
                    ret.addRecord(container);
                    continue;
                }
                if(!"MULTIEND".equalsIgnoreCase(itemName))
                    throw new ServiceWebServiceClientException("", new Object[] {
                        itemName
                    });
                break;
            }
            String strItemValue = dataStr.substring(sepIndex + 1);
            if("resp_returnmsg".equalsIgnoreCase(itemName) && !retMsgFlag)
            {
                ret.setReturnMessage(strItemValue);
                retMsgFlag = true;
            } else
            {
                container.addParam(itemName, getObject(dataType, strItemValue));
            }
        } while(true);
        return ret;
    }

    private static int genString(StringBuffer out, IParamObject paramObj)
    {
        int totalLength = 0;
        for(Iterator iter = paramObj.getValueMap().entrySet().iterator(); iter.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            String typeStr = getValueType(entry.getValue());
            String nameStr = entry.getKey().toString();
            String valueStr = getValueStr(entry.getValue());
            int length = appendItem(out, typeStr, nameStr, valueStr);
            totalLength += 9 + length;
        }

        return totalLength;
    }

    private static int appendItem(StringBuffer out, String typeStr, String nameStr, String valueStr)
    {
        int length = getStringLength(nameStr) + 1 + getStringLength(valueStr);
        String lengthStr = (new DecimalFormat("00000000")).format(length);
        out.append(typeStr).append(lengthStr).append(nameStr).append("|").append(valueStr);
        return length;
    }

    private static String getValueType(Object value)
    {
        if(value == null)
            return "N";
        if(value instanceof String)
            return "1";
        if(value instanceof Integer)
            return "2";
        if(value instanceof Float)
            return "3";
        if(value instanceof Double)
            return "4";
        if(value instanceof Boolean)
            return "5";
        if(value instanceof Date)
            return "6";
        else
            throw new ServiceWebServiceClientException("0001", new Object[] {
                value.getClass().getName()
            });
    }

    private static String getValueStr(Object value)
    {
        if(value == null)
            return "";
        if(value instanceof String)
            return value.toString();
        if(value instanceof Integer)
            return value.toString();
        if(value instanceof Float)
            return value.toString();
        if(value instanceof Double)
            return value.toString();
        if(value instanceof Boolean)
        {
            Boolean bv = (Boolean)value;
            return bv.booleanValue() ? "1" : "0";
        }
        if(value instanceof Date)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            return sdf.format(value);
        } else
        {
            throw new ServiceWebServiceClientException("0001", new Object[] {
                value.getClass().getName()
            });
        }
    }

    private static int getStringLength(String str)
    {
        return strToBytes(str).length;
    }

    private static byte[] strToBytes(String input)
    {
        try
        {
            return input.getBytes("GBK");
        }
        catch(UnsupportedEncodingException e)
        {
            throw new ServiceWebServiceClientException("0002", e, new Object[] {
                "GBK"
            });
        }
    }

    private static String getString(byte buffer[], int offset, int length)
    {
        try
        {
            return new String(buffer, offset, length, "GBK");
        }
        catch(UnsupportedEncodingException e)
        {
            throw new ServiceWebServiceClientException("0002", e, new Object[] {
                "GBK"
            });
        }
    }

    private static void dealFunctionCode(StringBuffer out, String functionCode)
    {
        int functionCodeLen = getStringLength(functionCode);
        if(functionCodeLen > 16)
            throw new ServiceWebServiceClientException("0003", new Object[] {
                functionCode
            });
        out.append(functionCode);
        for(int i = functionCodeLen; i < 16; i++)
            out.append(" ");

    }

    private static Object getObject(String objType, String objValue)
    {
        if("N".equalsIgnoreCase(objType))
            return null;
        if("1".equals(objType))
            return objValue;
        if("2".equals(objType))
            return Integer.valueOf(objValue);
        if("3".equals(objType))
            return Float.valueOf(objValue);
        if("4".equals(objType))
            return Double.valueOf(objValue);
        if("5".equals(objType))
            return Boolean.valueOf("1".equals(objValue));
        if("6".equals(objType))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            try
            {
                return sdf.parse(objValue);
            }
            catch(ParseException e)
            {
                throw new ServiceWebServiceClientException("0006", new Object[] {
                    objValue
                });
            }
        } else
        {
            throw new ServiceWebServiceClientException("0007", new Object[] {
                objType
            });
        }
    }

    private static final String ENCODING = "GBK";
    private static final String RETURNMESSAGE_PARAM_NAME = "resp_returnmsg";
    private static final String MULTI_START = "000000012MULTISTART|0";
    private static final String MULTI_SEP = "000000010MULTISEP|0";
    private static final String MULTI_END = "000000010MULTIEND|0";
}
