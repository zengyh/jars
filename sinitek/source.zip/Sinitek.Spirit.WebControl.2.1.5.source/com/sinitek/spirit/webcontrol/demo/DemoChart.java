// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoChart.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.spirit.webcontrol.chart.IChartAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class DemoChart
    implements IChartAware
{

    public DemoChart()
    {
    }

    public Object getChartResult(Map options, Map params, HttpServletRequest request)
    {
        Object result = null;
        if("line".equals(options.get("name")))
            result = getLineResult(options, params, request);
        else
        if("area".equals(options.get("name")))
            result = getAreaResult(options, params, request);
        else
        if("area_percent".equals(options.get("name")))
            result = getAreaPercentResult(options, params, request);
        else
        if("bar".equals(options.get("name")))
            result = getBarResult(options, params, request);
        else
        if("column".equals(options.get("name")))
            result = getColumnResult(options, params, request);
        else
        if("column_rotation".equals(options.get("name")))
            result = getColumnRotationResult(options, params, request);
        else
        if("pie".equals(options.get("name")))
            result = getPieResult(options, params, request);
        else
        if("pie_donut".equals(options.get("name")))
            result = getPieDonutResult(options, params, request);
        else
        if("line_icon".equals(options.get("name")))
            result = getLineIconResult(options, params, request);
        else
        if("scatter".equals(options.get("name")))
            result = getScatterResult(options, params, request);
        return result;
    }

    Object getLineResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "\u4E0A\u6D77");
        map1.put("data", new Double[] {
            Double.valueOf(7D), Double.valueOf(6.9000000000000004D), Double.valueOf(9.5D), Double.valueOf(14.5D), Double.valueOf(18.199999999999999D), Double.valueOf(25.5D), Double.valueOf(30.199999999999999D), Double.valueOf(32.5D), Double.valueOf(29.300000000000001D), Double.valueOf(20.300000000000001D), 
            Double.valueOf(13.9D), Double.valueOf(9.5999999999999996D)
        });
        Map map2 = new HashMap();
        map2.put("name", "\u5317\u4EAC");
        map2.put("data", new Double[] {
            Double.valueOf(-7D), Double.valueOf(-6.9000000000000004D), Double.valueOf(3.5D), Double.valueOf(8.5D), Double.valueOf(12.199999999999999D), Double.valueOf(20.5D), Double.valueOf(28.199999999999999D), Double.valueOf(30.5D), Double.valueOf(24.300000000000001D), Double.valueOf(12.199999999999999D), 
            Double.valueOf(4.9000000000000004D), Double.valueOf(0.59999999999999998D)
        });
        data.add(map1);
        data.add(map2);
        result.put("data", data);
        return result;
    }

    Object getLineIconResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "\u4E0A\u6D77");
        JSONObject sun = JSONObject.fromObject((new StringBuilder()).append("{y: 32.5,marker: {symbol: 'url(").append(request.getContextPath()).append("/framework/css/common/images/icon/sun.png)'}}").toString());
        map1.put("data", ((Object) (new Object[] {
            Double.valueOf(7D), Double.valueOf(6.9000000000000004D), Double.valueOf(9.5D), Double.valueOf(14.5D), Double.valueOf(18.199999999999999D), Double.valueOf(25.5D), Double.valueOf(30.199999999999999D), sun, Double.valueOf(29.300000000000001D), Double.valueOf(20.300000000000001D), 
            Double.valueOf(13.9D), Double.valueOf(9.5999999999999996D)
        })));
        Map map2 = new HashMap();
        map2.put("name", "\u5317\u4EAC");
        JSONObject snow = JSONObject.fromObject((new StringBuilder()).append("{y: -7.0,marker: {symbol: 'url(").append(request.getContextPath()).append("/framework/css/common/images/icon/snow.png)'}}").toString());
        map2.put("data", ((Object) (new Object[] {
            snow, Double.valueOf(-6.9000000000000004D), Double.valueOf(3.5D), Double.valueOf(8.5D), Double.valueOf(12.199999999999999D), Double.valueOf(20.5D), Double.valueOf(28.199999999999999D), Double.valueOf(30.5D), Double.valueOf(24.300000000000001D), Double.valueOf(12.199999999999999D), 
            Double.valueOf(4.9000000000000004D), Double.valueOf(0.59999999999999998D)
        })));
        data.add(map1);
        data.add(map2);
        result.put("data", data);
        return result;
    }

    Object getAreaResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "\u7F8E\u56FD");
        map1.put("data", new Integer[] {
            null, null, null, null, null, Integer.valueOf(6), Integer.valueOf(11), Integer.valueOf(32), Integer.valueOf(110), Integer.valueOf(235), 
            Integer.valueOf(369), Integer.valueOf(640), Integer.valueOf(1005), Integer.valueOf(1436), Integer.valueOf(2063), Integer.valueOf(3057), Integer.valueOf(4618), Integer.valueOf(6444), Integer.valueOf(9822), Integer.valueOf(15468), 
            Integer.valueOf(20434), Integer.valueOf(24126), Integer.valueOf(27387), Integer.valueOf(29459), Integer.valueOf(31056), Integer.valueOf(31982), Integer.valueOf(32040), Integer.valueOf(31233), Integer.valueOf(29224), Integer.valueOf(27342), 
            Integer.valueOf(26662), Integer.valueOf(26956), Integer.valueOf(27912), Integer.valueOf(28999), Integer.valueOf(28965), Integer.valueOf(27826), Integer.valueOf(25579), Integer.valueOf(25722), Integer.valueOf(24826), Integer.valueOf(24605), 
            Integer.valueOf(24304), Integer.valueOf(23464), Integer.valueOf(23708), Integer.valueOf(24099), Integer.valueOf(24357), Integer.valueOf(24237), Integer.valueOf(24401), Integer.valueOf(24344), Integer.valueOf(23586), Integer.valueOf(22380), 
            Integer.valueOf(21004), Integer.valueOf(17287), Integer.valueOf(14747), Integer.valueOf(13076), Integer.valueOf(12555), Integer.valueOf(12144), Integer.valueOf(11009), Integer.valueOf(10950), Integer.valueOf(10871), Integer.valueOf(10824), 
            Integer.valueOf(10577), Integer.valueOf(10527), Integer.valueOf(10475), Integer.valueOf(10421), Integer.valueOf(10358), Integer.valueOf(10295), Integer.valueOf(10104)
        });
        Map map2 = new HashMap();
        map2.put("name", "\u4FC4\u7F57\u65AF");
        map2.put("data", new Integer[] {
            null, null, null, null, null, null, null, null, null, null, 
            Integer.valueOf(5), Integer.valueOf(25), Integer.valueOf(50), Integer.valueOf(120), Integer.valueOf(150), Integer.valueOf(200), Integer.valueOf(426), Integer.valueOf(660), Integer.valueOf(869), Integer.valueOf(1060), 
            Integer.valueOf(1605), Integer.valueOf(2471), Integer.valueOf(3322), Integer.valueOf(4238), Integer.valueOf(5221), Integer.valueOf(6129), Integer.valueOf(7089), Integer.valueOf(8339), Integer.valueOf(9399), Integer.valueOf(10538), 
            Integer.valueOf(11643), Integer.valueOf(13092), Integer.valueOf(14478), Integer.valueOf(15915), Integer.valueOf(17385), Integer.valueOf(19055), Integer.valueOf(21205), Integer.valueOf(23044), Integer.valueOf(25393), Integer.valueOf(27935), 
            Integer.valueOf(30062), Integer.valueOf(32049), Integer.valueOf(33952), Integer.valueOf(35804), Integer.valueOf(37431), Integer.valueOf(39197), Integer.valueOf(45000), Integer.valueOf(43000), Integer.valueOf(41000), Integer.valueOf(39000), 
            Integer.valueOf(37000), Integer.valueOf(35000), Integer.valueOf(33000), Integer.valueOf(31000), Integer.valueOf(29000), Integer.valueOf(27000), Integer.valueOf(25000), Integer.valueOf(24000), Integer.valueOf(23000), Integer.valueOf(22000), 
            Integer.valueOf(21000), Integer.valueOf(20000), Integer.valueOf(19000), Integer.valueOf(18000), Integer.valueOf(18000), Integer.valueOf(17000), Integer.valueOf(16000)
        });
        data.add(map1);
        data.add(map2);
        result.put("data", data);
        return result;
    }

    Object getAreaPercentResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "\u4E9A\u6D32");
        map1.put("data", new Integer[] {
            Integer.valueOf(502), Integer.valueOf(635), Integer.valueOf(809), Integer.valueOf(947), Integer.valueOf(1402), Integer.valueOf(3634), Integer.valueOf(5268)
        });
        Map map2 = new HashMap();
        map2.put("name", "\u975E\u6D32");
        map2.put("data", new Integer[] {
            Integer.valueOf(106), Integer.valueOf(107), Integer.valueOf(111), Integer.valueOf(133), Integer.valueOf(221), Integer.valueOf(767), Integer.valueOf(1766)
        });
        Map map3 = new HashMap();
        map3.put("name", "\u6B27\u6D32");
        map3.put("data", new Integer[] {
            Integer.valueOf(163), Integer.valueOf(203), Integer.valueOf(276), Integer.valueOf(408), Integer.valueOf(547), Integer.valueOf(729), Integer.valueOf(628)
        });
        Map map4 = new HashMap();
        map4.put("name", "\u7F8E\u6D32");
        map4.put("data", new Integer[] {
            Integer.valueOf(18), Integer.valueOf(31), Integer.valueOf(54), Integer.valueOf(156), Integer.valueOf(339), Integer.valueOf(818), Integer.valueOf(1201)
        });
        Map map5 = new HashMap();
        map5.put("name", "\u5927\u6D0B\u6D32");
        map5.put("data", new Integer[] {
            Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(6), Integer.valueOf(13), Integer.valueOf(30), Integer.valueOf(46)
        });
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);
        result.put("data", data);
        result.put("categories", new String[] {
            "1750", "1800", "1850", "1900", "1950", "1999", "2050"
        });
        return result;
    }

    Object getBarResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "1800\u5E74");
        map1.put("data", new Integer[] {
            Integer.valueOf(107), Integer.valueOf(31), Integer.valueOf(635), Integer.valueOf(203), Integer.valueOf(2)
        });
        Map map2 = new HashMap();
        map2.put("name", "1900\u5E74");
        map2.put("data", new Integer[] {
            Integer.valueOf(133), Integer.valueOf(156), Integer.valueOf(497), Integer.valueOf(408), Integer.valueOf(6)
        });
        Map map3 = new HashMap();
        map3.put("name", "2008\u5E74");
        map3.put("data", new Integer[] {
            Integer.valueOf(973), Integer.valueOf(194), Integer.valueOf(4054), Integer.valueOf(732), Integer.valueOf(34)
        });
        data.add(map1);
        data.add(map2);
        data.add(map3);
        result.put("data", data);
        return result;
    }

    Object getColumnResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List data = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "\u4E1C\u4EAC");
        map1.put("data", new Double[] {
            Double.valueOf(49.899999999999999D), Double.valueOf(71.5D), Double.valueOf(106.40000000000001D), Double.valueOf(129.19999999999999D), Double.valueOf(144D), Double.valueOf(176D), Double.valueOf(135.59999999999999D), Double.valueOf(148.5D), Double.valueOf(216.40000000000001D), Double.valueOf(194.09999999999999D), 
            Double.valueOf(95.599999999999994D), Double.valueOf(54.399999999999999D)
        });
        Map map2 = new HashMap();
        map2.put("name", "\u7EBD\u7EA6");
        map2.put("data", new Double[] {
            Double.valueOf(83.599999999999994D), Double.valueOf(78.799999999999997D), Double.valueOf(98.5D), Double.valueOf(93.400000000000006D), Double.valueOf(106D), Double.valueOf(84.5D), Double.valueOf(105D), Double.valueOf(104.3D), Double.valueOf(91.200000000000003D), Double.valueOf(83.5D), 
            Double.valueOf(106.59999999999999D), Double.valueOf(92.299999999999997D)
        });
        Map map3 = new HashMap();
        map3.put("name", "\u4F26\u6566");
        map3.put("data", new Double[] {
            Double.valueOf(48.899999999999999D), Double.valueOf(38.799999999999997D), Double.valueOf(39.299999999999997D), Double.valueOf(41.399999999999999D), Double.valueOf(47D), Double.valueOf(48.299999999999997D), Double.valueOf(59D), Double.valueOf(59.600000000000001D), Double.valueOf(52.399999999999999D), Double.valueOf(65.200000000000003D), 
            Double.valueOf(59.299999999999997D), Double.valueOf(51.200000000000003D)
        });
        data.add(map1);
        data.add(map2);
        data.add(map3);
        result.put("data", data);
        return result;
    }

    Object getColumnRotationResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        result.put("data", new Double[] {
            Double.valueOf(49.899999999999999D), Double.valueOf(71.5D), Double.valueOf(106.40000000000001D), Double.valueOf(129.19999999999999D), Double.valueOf(144D), Double.valueOf(176D), Double.valueOf(135.59999999999999D), Double.valueOf(148.5D), Double.valueOf(216.40000000000001D), Double.valueOf(194.09999999999999D), 
            Double.valueOf(95.599999999999994D), Double.valueOf(54.399999999999999D)
        });
        return result;
    }

    Object getPieResult(Map options, Map params, HttpServletRequest request)
    {
        List result = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "Firefox");
        map1.put("y", Double.valueOf(21.710000000000001D));
        Map map2 = new HashMap();
        map2.put("name", "IE");
        map2.put("y", Double.valueOf(54.270000000000003D));
        Map map3 = new HashMap();
        map3.put("name", "Safari");
        map3.put("y", Double.valueOf(7.2800000000000002D));
        Map map4 = new HashMap();
        map4.put("name", "Opera");
        map4.put("y", Double.valueOf(3.2999999999999998D));
        Map map5 = new HashMap();
        map5.put("name", "Others");
        map5.put("y", Double.valueOf(0.90000000000000002D));
        Map map6 = new HashMap();
        map6.put("name", "Chrome");
        map6.put("y", Double.valueOf(12.52D));
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        return result;
    }

    Object getPieDonutResult(Map options, Map params, HttpServletRequest request)
    {
        Map result = new HashMap();
        List result2008 = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "Firefox");
        map1.put("y", Double.valueOf(21.710000000000001D));
        map1.put("color", "#4572A7");
        Map map2 = new HashMap();
        map2.put("name", "IE");
        map2.put("y", Double.valueOf(54.270000000000003D));
        map2.put("color", "#AA4643");
        Map map3 = new HashMap();
        map3.put("name", "Safari");
        map3.put("y", Double.valueOf(7.2800000000000002D));
        map3.put("color", "#89A54E");
        Map map4 = new HashMap();
        map4.put("name", "Opera");
        map4.put("y", Double.valueOf(3.2999999999999998D));
        map4.put("color", "#80699B");
        Map map5 = new HashMap();
        map5.put("name", "Others");
        map5.put("y", Double.valueOf(0.90000000000000002D));
        map5.put("color", "#3D96AE");
        Map map6 = new HashMap();
        map6.put("name", "Chrome");
        map6.put("y", Double.valueOf(12.52D));
        map6.put("color", "#DB843D");
        result2008.add(map1);
        result2008.add(map2);
        result2008.add(map3);
        result2008.add(map4);
        result2008.add(map5);
        result2008.add(map6);
        List result2010 = new ArrayList();
        Map map7 = new HashMap();
        map7.put("name", "Firefox");
        map7.put("y", Double.valueOf(31.710000000000001D));
        map7.put("color", "#4572A7");
        Map map8 = new HashMap();
        map8.put("name", "IE");
        map8.put("y", Double.valueOf(46.270000000000003D));
        map8.put("color", "#AA4643");
        Map map9 = new HashMap();
        map9.put("name", "Safari");
        map9.put("y", Double.valueOf(5.2800000000000002D));
        map9.put("color", "#89A54E");
        Map map10 = new HashMap();
        map10.put("name", "Opera");
        map10.put("y", Double.valueOf(0.29999999999999999D));
        map10.put("color", "#80699B");
        Map map11 = new HashMap();
        map11.put("name", "Others");
        map11.put("y", Double.valueOf(0.90000000000000002D));
        map11.put("color", "#3D96AE");
        Map map12 = new HashMap();
        map12.put("name", "Chrome");
        map12.put("y", Double.valueOf(15.52D));
        map12.put("color", "#DB843D");
        result2010.add(map7);
        result2010.add(map8);
        result2010.add(map9);
        result2010.add(map10);
        result2010.add(map11);
        result2010.add(map12);
        result.put("data2008", result2008);
        result.put("data2010", result2010);
        return result;
    }

    Object getScatterResult(Map options, Map params, HttpServletRequest request)
    {
        return null;
    }
}
