// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyflowXmlUtil.java

package com.sinitek.sirm.web.workflow.util;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.busin.workflow.service.drawprocess.IWorkflowDrawService;
import com.sinitek.sirm.common.utils.*;
import java.io.*;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.*;
import org.json.JSONException;

public class MyflowXmlUtil
{

    public MyflowXmlUtil()
    {
    }

    public static String createXml(int processid)
        throws IOException, JSONException
    {
        IWorkflowBaseService _workbase = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowDrawService _workdraw = WorkflowServiceFactory.getWorkflowDrawService();
        IWorkflowAdvService _workadv = WorkflowServiceFactory.getWorkflowAdvService();
        Map _paramap = _workbase.getSingleParaMap();
        Document doc = DocumentHelper.createDocument();
        Element myflow = doc.addElement("process");
        IWorkflowProcess _process = _workbase.loadProcess(processid);
        int _processtype = _process.getProcessType().intValue();
        myflow.addElement("name").setText(_process.getName());
        myflow.addElement("processcode").setText(StringUtils.defaultIfEmpty(_process.getProcessCode(), ""));
        myflow.addElement("processtype").setText(String.valueOf(_process.getProcessType()));
        myflow.addElement("processbrief").setText(StringUtils.defaultIfEmpty(_process.getProcessBrief(), ""));
        Map _drawflowdata = new HashMap();
        if(!_workdraw.checkIsDrawFlow(processid).booleanValue())
        {
            LOGGER.info("\u65E7\u6D41\u7A0B\u56FE\u5F62\u5316\u64CD\u4F5C\uFF1A");
            _drawflowdata = _workadv.changeProcess(processid);
        } else
        {
            LOGGER.info("\u65B0\u6D41\u7A0B\u56FE\u5F62\u5316\u64CD\u4F5C\uFF1A");
            _drawflowdata = _workdraw.getFlowDataMap(processid);
        }
        Element processlist = myflow.addElement("processlist");
        Element states = myflow.addElement("states");
        Element paths = myflow.addElement("paths");
        Map _states = (Map)_drawflowdata.get("states");
label0:
        do
        {
            String _type;
            Element props;
            Map _propsmap;
            Iterator i$;
label1:
            do
            {
                for(Iterator i$ = _states.keySet().iterator(); i$.hasNext();)
                {
                    String _o = (String)i$.next();
                    Element rect = states.addElement("rect");
                    rect.addAttribute("id", _o.substring(4));
                    Map _rectmap = (Map)_states.get(_o);
                    _type = MapUtils.getString(_rectmap, "type", "");
                    String _text = MapUtils.getString((Map)_rectmap.get("text"), "text", "");
                    rect.addElement("type").addText(_type);
                    rect.addElement("text").addElement("text").setText(_text);
                    Map _attrmap = MapUtils.getMap(_rectmap, "attr");
                    Element attr = rect.addElement("attr");
                    attr.addElement("height").setText(MapUtils.getString(_attrmap, "height", "0"));
                    attr.addElement("width").setText(MapUtils.getString(_attrmap, "width", "0"));
                    attr.addElement("x").setText(MapUtils.getString(_attrmap, "x", "0"));
                    attr.addElement("y").setText(MapUtils.getString(_attrmap, "y", "0"));
                    props = rect.addElement("props");
                    _propsmap = (Map)_rectmap.get("props");
                    if("start".equals(_type) || "end".equals(_type))
                    {
                        i$ = _propsmap.keySet().iterator();
                        while(i$.hasNext()) 
                        {
                            String _name = (String)i$.next();
                            if("steppredo".equals(_name) || "stepaftdo".equals(_name))
                            {
                                String _steppredo = MapUtils.getString((Map)_propsmap.get(_name), "value", "");
                                props.addElement(_name).addElement("value").setText(String.valueOf(_steppredo));
                                if(StringUtils.isNotBlank(_steppredo))
                                {
                                    String _linkdoArr[] = _steppredo.split(",");
                                    int j = 0;
                                    while(j < _linkdoArr.length) 
                                    {
                                        if(_linkdoArr[j].length() > 0)
                                        {
                                            int _dotype = NumberTool.safeToInteger(_linkdoArr[j], Integer.valueOf(0)).intValue();
                                            if(_dotype > 0)
                                            {
                                                int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("SpecialTask").append(_dotype).toString(), 0);
                                                if(_value == 0)
                                                    _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("SpecialTask").append(_dotype).toString(), 0);
                                                processlist2Element(_workbase.loadProcessList(_value), processlist);
                                            }
                                        }
                                        j++;
                                    }
                                }
                            } else
                            {
                                Element prop = props.addElement(_name);
                                Map _valuemap = (Map)_propsmap.get(_name);
                                Iterator i$ = _valuemap.keySet().iterator();
                                while(i$.hasNext()) 
                                {
                                    String _key = (String)i$.next();
                                    prop.addElement(_key).setText((String)_valuemap.get(_key));
                                }
                            }
                        }
                    } else
                    {
                        if(!"submit".equals(_type) && !"judge".equals(_type))
                            continue label1;
                        i$ = _propsmap.keySet().iterator();
                        while(i$.hasNext()) 
                        {
                            String _name = (String)i$.next();
                            if("pointtypeid".equals(_name))
                            {
                                int _pointtypeid = MapUtils.getIntValue((Map)_propsmap.get("pointtypeid"), "value", 0);
                                props.addElement("pointtypeid").addElement("value").setText(String.valueOf(_pointtypeid));
                                if(_pointtypeid != 0)
                                {
                                    int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("StepStage").append(_pointtypeid).toString(), 0);
                                    processlist2Element(_workbase.loadProcessList(_value), processlist);
                                }
                            } else
                            if("processurl".equals(_name))
                            {
                                int _processurl = MapUtils.getIntValue((Map)_propsmap.get("processurl"), "value", 0);
                                String _actionurl = "";
                                String _showurl = "";
                                String _urlname = "";
                                IWorkflowProcessUrl _url = _workbase.loadProcessUrl(_processurl);
                                if(_url.size() > 0)
                                {
                                    _actionurl = StringUtil.safeToString(_url.getActionUrl(), "");
                                    _showurl = StringUtil.safeToString(_url.getShowUrl(), "");
                                    _urlname = StringUtil.safeToString(_url.getName(), "");
                                }
                                props.addElement("actionurl").addElement("value").setText(_actionurl);
                                props.addElement("showurl").addElement("value").setText(_showurl);
                                props.addElement("urlname").addElement("value").setText(_urlname);
                            } else
                            if("submit".equals(_type) && "submitowner".equals(_name))
                            {
                                String _steptypeads = "";
                                int _judgetype = MapUtils.getIntValue((Map)_propsmap.get("submitowner"), "value", 0);
                                _steptypeads = (new StringBuilder()).append(_steptypeads).append(_judgetype).toString();
                                props.addElement("submitowner").addElement("value").setText(_steptypeads);
                            } else
                            if("judge".equals(_type) && "judgetype".equals(_name))
                            {
                                String _judgetypearr[] = MapUtils.getString((Map)_propsmap.get("judgetype"), "value", "").split(":");
                                props.addElement("judgetype").addElement("value").setText(MapUtils.getString((Map)_propsmap.get("judgetype"), "value", ""));
                                int _judgetype = NumberTool.safeToInteger(_judgetypearr[0], Integer.valueOf(0)).intValue();
                                if(_judgetype != 2 && _judgetype == 100)
                                {
                                    int _resultdeal = NumberTool.safeToInteger(_judgetypearr[3], Integer.valueOf(0)).intValue();
                                    if(_resultdeal != 0)
                                    {
                                        int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("ResultDeal").append(_resultdeal).toString(), 0);
                                        if(_value == 0)
                                            _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("ResultDeal").append(_resultdeal).toString(), 0);
                                        processlist2Element(_workbase.loadProcessList(_value), processlist);
                                    }
                                }
                            } else
                            if("stepdealer".equals(_name))
                            {
                                int _stepdealerid = MapUtils.getIntValue((Map)_propsmap.get("stepdealer"), "value", 0);
                                props.addElement("stepdealer").addElement("value").setText(String.valueOf(_stepdealerid));
                                if(_stepdealerid != 0)
                                {
                                    int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("StepDealer").append(_stepdealerid).toString(), 0);
                                    if(_value == 0)
                                        _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("StepDealer").append(_stepdealerid).toString(), 0);
                                    processlist2Element(_workbase.loadProcessList(_value), processlist);
                                }
                            } else
                            if("messagenotice".equals(_name))
                            {
                                String _messagenotice = MapUtils.getString((Map)_propsmap.get("messagenotice"), "value", "");
                                String _noticeowner = MapUtils.getString((Map)_propsmap.get("messagenotice"), "noticeowners", "");
                                Element _emsgnotice = props.addElement("messagenotice");
                                _emsgnotice.addElement("value").setText(_messagenotice);
                                _emsgnotice.addElement("noticeowners").setText(_noticeowner);
                                String _specialnoticer = MapUtils.getString((Map)_propsmap.get("messagenotice"), "specialnoticer", "");
                                _emsgnotice.addElement("specialnoticer").setText(_specialnoticer);
                                if(NumberTool.safeToInteger(_specialnoticer, Integer.valueOf(0)).intValue() > 0)
                                {
                                    int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("StepDealer").append(_specialnoticer).toString(), 0);
                                    if(_value == 0)
                                        _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("StepDealer").append(_specialnoticer).toString(), 0);
                                    processlist2Element(_workbase.loadProcessList(_value), processlist);
                                }
                            } else
                            if("steppredo".equals(_name) || "stepaftdo".equals(_name))
                            {
                                String _steppredo = MapUtils.getString((Map)_propsmap.get(_name), "value", "");
                                props.addElement(_name).addElement("value").setText(String.valueOf(_steppredo));
                                if(StringUtils.isNotBlank(_steppredo))
                                {
                                    String _linkdoArr[] = _steppredo.split(",");
                                    int j = 0;
                                    while(j < _linkdoArr.length) 
                                    {
                                        if(_linkdoArr[j].length() > 0)
                                        {
                                            int _dotype = NumberTool.safeToInteger(_linkdoArr[j], Integer.valueOf(0)).intValue();
                                            if(_dotype > 0)
                                            {
                                                int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("SpecialTask").append(_dotype).toString(), 0);
                                                if(_value == 0)
                                                    _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("SpecialTask").append(_dotype).toString(), 0);
                                                processlist2Element(_workbase.loadProcessList(_value), processlist);
                                            }
                                        }
                                        j++;
                                    }
                                }
                            } else
                            {
                                Element prop = props.addElement(_name);
                                Map _valuemap = (Map)_propsmap.get(_name);
                                Iterator i$ = _valuemap.keySet().iterator();
                                while(i$.hasNext()) 
                                {
                                    String _key = (String)i$.next();
                                    prop.addElement(_key).setText((String)_valuemap.get(_key));
                                }
                            }
                        }
                    }
                }

                break label0;
            } while(!"state".equals(_type));
            i$ = _propsmap.keySet().iterator();
            while(i$.hasNext()) 
            {
                String _name = (String)i$.next();
                if("stepcondition".equals(_name))
                {
                    String _stepcondition = MapUtils.getString((Map)_propsmap.get("stepcondition"), "value", "");
                    props.addElement("stepcondition").addElement("value").setText(_stepcondition);
                    if(StringUtils.isNotBlank(_stepcondition))
                    {
                        String _linkifArr[] = _stepcondition.split(",");
                        int j = 0;
                        while(j < _linkifArr.length) 
                        {
                            String _linkifArrArr[] = _linkifArr[j].split(":");
                            if(_linkifArrArr.length == 3)
                            {
                                int _iftype = NumberTool.safeToInteger(_linkifArrArr[0], Integer.valueOf(0)).intValue();
                                if(_iftype >= 0x186a0)
                                {
                                    int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("LinkIfType").append(_iftype).toString(), 0);
                                    if(_value == 0)
                                        _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("LinkIfType").append(_iftype).toString(), 0);
                                    processlist2Element(_workbase.loadProcessList(_value), processlist);
                                }
                            }
                            j++;
                        }
                    }
                } else
                if("steplinkdo".equals(_name))
                {
                    String _steplinkdo = MapUtils.getString((Map)_propsmap.get("steplinkdo"), "value", "");
                    props.addElement("steplinkdo").addElement("value").setText(_steplinkdo);
                    if(StringUtils.isNotBlank(_steplinkdo))
                    {
                        String _linkdoArr[] = _steplinkdo.split(",");
                        int j = 0;
                        while(j < _linkdoArr.length) 
                        {
                            if(_linkdoArr[j].length() > 0)
                            {
                                int _dotype = NumberTool.safeToInteger(_linkdoArr[j], Integer.valueOf(0)).intValue();
                                if(_dotype > 0)
                                {
                                    int _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append(_processtype).append("SpecialTask").append(_dotype).toString(), 0);
                                    if(_value == 0)
                                        _value = MapUtils.getIntValue(_paramap, (new StringBuilder()).append("SpecialTask").append(_dotype).toString(), 0);
                                    processlist2Element(_workbase.loadProcessList(_value), processlist);
                                }
                            }
                            j++;
                        }
                    }
                } else
                {
                    Element prop = props.addElement(_name);
                    Map _valuemap = (Map)_propsmap.get(_name);
                    Iterator i$ = _valuemap.keySet().iterator();
                    while(i$.hasNext()) 
                    {
                        String _key = (String)i$.next();
                        prop.addElement(_key).setText((String)_valuemap.get(_key));
                    }
                }
            }
        } while(true);
        Map _paths = (Map)_drawflowdata.get("paths");
        for(Iterator i$ = _paths.keySet().iterator(); i$.hasNext();)
        {
            String _o = (String)i$.next();
            Element path = paths.addElement("path");
            path.addAttribute("id", _o.substring(4));
            Map _pathmap = (Map)_paths.get(_o);
            String _type = MapUtils.getString(_pathmap, "type", "");
            String _from = MapUtils.getString(_pathmap, "from", "");
            String _to = MapUtils.getString(_pathmap, "to", "");
            path.addElement("type").addText(_type);
            path.addElement("from").setText(_from);
            path.addElement("to").addText(_to);
            Element props = path.addElement("props");
            Map _propsmap = (Map)_pathmap.get("props");
            for(Iterator i$ = _propsmap.keySet().iterator(); i$.hasNext();)
            {
                String _name = (String)i$.next();
                Element prop = props.addElement(_name);
                Map _valuemap = (Map)_propsmap.get(_name);
                Iterator i$ = _valuemap.keySet().iterator();
                while(i$.hasNext()) 
                {
                    String _key = (String)i$.next();
                    prop.addElement(_key).setText((String)_valuemap.get(_key));
                }
            }

            List _dotslist = (LinkedList)_pathmap.get("dots");
            Element dots = path.addElement("dots");
            Iterator i$ = _dotslist.iterator();
            while(i$.hasNext()) 
            {
                Map _dot = (Map)i$.next();
                Element dot = dots.addElement("dot");
                dot.addElement("x").setText(MapUtils.getString(_dot, "x", "0"));
                dot.addElement("y").setText(MapUtils.getString(_dot, "y", "0"));
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File file = IOUtil.createTempFile("xml");
        XMLWriter writer = null;
        writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(doc);
        return file.getPath();
    }

    private static void processlist2Element(IWorkflowProcessList processlist, Element list)
    {
        if(processlist.size() > 0)
        {
            for(Iterator i$ = list.elements().iterator(); i$.hasNext();)
            {
                Element _elmap = (Element)i$.next();
                Map _map = ElementsToMap(_elmap.elements());
                _map.remove("sort");
                String _name = MapUtils.getString(_map, "name", "");
                String _value = MapUtils.getString(_map, "value", "");
                String _valueads = MapUtils.getString(_map, "valueads", "");
                int _type = MapUtils.getIntValue(_map, "type", 0);
                int _key = MapUtils.getIntValue(_map, "key", 0);
                if(_name.equals(processlist.getName()) && _value.equals(processlist.getValue()) && _valueads.equals(processlist.getValueAds()) && _type == processlist.getType().intValue() && _key == processlist.getKey().intValue())
                    return;
            }

            Element _listmap = list.addElement("listmap");
            _listmap.addElement("name").setText(StringUtil.safeToString(processlist.getName(), ""));
            _listmap.addElement("value").setText(StringUtil.safeToString(processlist.getValue(), ""));
            _listmap.addElement("valueads").setText(StringUtil.safeToString(processlist.getValueAds(), ""));
            _listmap.addElement("key").setText(StringUtil.safeToString(processlist.getKey(), ""));
            _listmap.addElement("type").setText(StringUtil.safeToString(processlist.getType(), ""));
            _listmap.addElement("sort").setText(StringUtil.safeToString(processlist.getSort(), ""));
        }
    }

    public static Map parseXml(String fileName)
        throws DocumentException
    {
        Map _res = new HashMap();
        Document document = (new SAXReader()).read(new File(fileName));
        Element root = document.getRootElement();
        Element _tempElement = null;
        _tempElement = root.element("name");
        if(_tempElement != null)
            _res.put("name", _tempElement.getText());
        _tempElement = root.element("processcode");
        if(_tempElement != null)
            _res.put("processcode", _tempElement.getText());
        _tempElement = root.element("processtype");
        if(_tempElement != null)
            _res.put("processtype", _tempElement.getText());
        _tempElement = root.element("processbrief");
        if(_tempElement != null)
            _res.put("processbrief", _tempElement.getText());
        _tempElement = root.element("processlist");
        if(_tempElement != null)
        {
            List _list = new LinkedList();
            Element element;
            for(Iterator it = _tempElement.elementIterator(); it.hasNext(); _list.add(ElementsToMap(element.elements())))
                element = (Element)it.next();

            _res.put("processlist", _list);
        }
        _tempElement = root.element("states");
        if(_tempElement != null)
        {
            Iterator it = _tempElement.elementIterator();
            Map _statemap = new HashMap();
            Element rectelement;
            for(; it.hasNext(); _statemap.put((new StringBuilder()).append(rectelement.getName()).append(rectelement.attributeValue("id")).toString(), ElementsToMap(rectelement.elements())))
                rectelement = (Element)it.next();

            _res.put("states", _statemap);
        }
        _tempElement = root.element("paths");
        if(_tempElement != null)
        {
            Iterator it = _tempElement.elementIterator();
            Map _pathmap = new HashMap();
            Element element;
            for(; it.hasNext(); _pathmap.put((new StringBuilder()).append(element.getName()).append(element.attributeValue("id")).toString(), ElementsToMap(element.elements())))
                element = (Element)it.next();

            _res.put("paths", _pathmap);
        }
        return _res;
    }

    public static Map ElementToMap(Element ele)
    {
        Map _map = new HashMap();
        List elementList = ele.elements();
        if(elementList.size() == 0)
            _map.put(ele.getName(), ele.getText());
        else
        if(checkElementIsList(ele).booleanValue())
            _map.put(ele.getName(), ElementsToList(elementList));
        else
            _map.put(ele.getName(), ElementsToMap(elementList));
        return _map;
    }

    public static Map ElementsToMap(List elementList)
    {
        Map _map = new HashMap();
        for(Iterator i$ = elementList.iterator(); i$.hasNext();)
        {
            Element ele = (Element)i$.next();
            List _elementList = ele.elements();
            if(_elementList.size() == 0)
            {
                if("dots".equals(ele.getName()))
                    _map.put(ele.getName(), ElementsToList(_elementList));
                else
                    _map.put(ele.getName(), ele.getText());
            } else
            if("dots".equals(ele.getName()))
                _map.put(ele.getName(), ElementsToList(_elementList));
            else
                _map.put(ele.getName(), ElementsToMap(_elementList));
        }

        return _map;
    }

    public static List ElementsToList(List elementList)
    {
        List _list = new LinkedList();
        Map _map;
        for(Iterator i$ = elementList.iterator(); i$.hasNext(); _list.add(_map))
        {
            Element ele = (Element)i$.next();
            _map = new HashMap();
            List _elementList = ele.elements();
            if(_elementList.size() == 0)
                _map.put(ele.getName(), ele.getText());
            else
                _map = ElementsToMap(_elementList);
        }

        return _list;
    }

    private static Boolean checkElementIsList(Element element)
    {
        Boolean _flag = Boolean.valueOf(false);
        Map _namemap = new HashMap();
        List _elementlist = element.elements();
        Element _child;
        for(Iterator i$ = _elementlist.iterator(); i$.hasNext(); _namemap.put(_child.getName(), "1"))
            _child = (Element)i$.next();

        if(_elementlist.size() > 1 && _namemap.size() == 1)
            _flag = Boolean.valueOf(true);
        return _flag;
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/web/workflow/util/MyflowXmlUtil);

}
