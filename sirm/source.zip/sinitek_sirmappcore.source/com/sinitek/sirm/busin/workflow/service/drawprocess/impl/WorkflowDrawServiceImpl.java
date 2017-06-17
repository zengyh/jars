// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowDrawServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.drawprocess.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.workflow.entity.drawprocess.*;
import com.sinitek.sirm.busin.workflow.service.drawprocess.IWorkflowDrawService;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.web.workflow.util.JsonUtil;
import java.io.PrintStream;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.*;

public class WorkflowDrawServiceImpl extends MetaDBContextSupport
    implements IWorkflowDrawService
{

    public WorkflowDrawServiceImpl()
    {
    }

    public int saveFlowNode(IWorkflowFlowNode flowNode)
    {
        flowNode.save();
        return flowNode.getId();
    }

    public int saveFlowPaths(IWorkflowFlowPaths flowPaths)
    {
        flowPaths.save();
        return flowPaths.getId();
    }

    public int saveFlowProps(IWorkflowFlowProps flowProps)
    {
        flowProps.save();
        return flowProps.getId();
    }

    public int saveFlowDots(IWorkflowFlowDots flowDots)
    {
        flowDots.save();
        return flowDots.getId();
    }

    public IWorkflowFlowNode getFlowNode(int nodeid)
    {
        IWorkflowFlowNode _t = (IWorkflowFlowNode)getMetaDBContext().get(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowNode, nodeid);
        return ((IWorkflowFlowNode) (_t != null ? _t : new WorkflowFlowNodeImpl()));
    }

    public IWorkflowFlowPaths getFlowPaths(int pathid)
    {
        IWorkflowFlowPaths _t = (IWorkflowFlowPaths)getMetaDBContext().get(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowPaths, pathid);
        return ((IWorkflowFlowPaths) (_t != null ? _t : new WorkflowFlowPathsImpl()));
    }

    public IWorkflowFlowProps getFlowProps(int propsid)
    {
        IWorkflowFlowProps _t = (IWorkflowFlowProps)getMetaDBContext().get(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowProps, propsid);
        return ((IWorkflowFlowProps) (_t != null ? _t : new WorkflowFlowPropsImpl()));
    }

    public IWorkflowFlowDots getFlowDots(int dotid)
    {
        IWorkflowFlowDots _t = (IWorkflowFlowDots)getMetaDBContext().get(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowDots, dotid);
        return ((IWorkflowFlowDots) (_t != null ? _t : new WorkflowFlowDotsImpl()));
    }

    public void deleteFlowNode(IWorkflowFlowNode flowNode)
    {
        if(flowNode != null)
            flowNode.delete();
    }

    public void deleteFlowPaths(IWorkflowFlowPaths flowPaths)
    {
        if(flowPaths != null)
            flowPaths.delete();
    }

    public void deleteFlowProps(IWorkflowFlowProps flowProps)
    {
        if(flowProps != null)
            flowProps.delete();
    }

    public void deleteFlowDots(IWorkflowFlowDots flowDots)
    {
        if(flowDots != null)
            flowDots.delete();
    }

    public void deleteHistoryData(int processid)
    {
        List nodelist = findFlowNode(processid);
        IWorkflowFlowNode _node;
        for(Iterator i$ = nodelist.iterator(); i$.hasNext(); deleteFlowNode(_node))
        {
            _node = (IWorkflowFlowNode)i$.next();
            List _propslist = findFlowProps(_node.getId(), _node.getEntityName());
            IWorkflowFlowProps _prop;
            for(Iterator i$ = _propslist.iterator(); i$.hasNext(); deleteFlowProps(_prop))
                _prop = (IWorkflowFlowProps)i$.next();

        }

        List _pathlist = findFlowPaths(processid);
        IWorkflowFlowPaths _path;
        for(Iterator i$ = _pathlist.iterator(); i$.hasNext(); deleteFlowPaths(_path))
        {
            _path = (IWorkflowFlowPaths)i$.next();
            List _dotslist = findFlowDots(_path.getId());
            IWorkflowFlowDots _dot;
            for(Iterator i$ = _dotslist.iterator(); i$.hasNext(); deleteFlowDots(_dot))
                _dot = (IWorkflowFlowDots)i$.next();

            List _propslist = findFlowProps(_path.getId(), _path.getEntityName());
            IWorkflowFlowProps _prop;
            for(Iterator i$ = _propslist.iterator(); i$.hasNext(); deleteFlowProps(_prop))
                _prop = (IWorkflowFlowProps)i$.next();

        }

    }

    public Map getFlowDataMap(int processid)
    {
        Map _myflow = new HashMap();
        int num = 1;
        Map _parammap = new HashMap();
        Map status = new HashMap();
        Map paths = new HashMap();
        List _nodelist = findFlowNode(processid);
        for(int i = 0; i < _nodelist.size(); i++)
        {
            IWorkflowFlowNode _node = (IWorkflowFlowNode)_nodelist.get(i);
            try
            {
                Map rect = new HashMap();
                rect.put("type", _node.getType());
                Map _tp = new HashMap();
                _tp.put("text", _node.getText());
                rect.put("text", _tp);
                rect.put("attr", JsonUtil.jsonToMap(new JSONObject(_node.getAttr())));
                Map _propsmap = getFlowPropsMap(_node.getId(), _node.getEntityName());
                Map props = new HashMap();
                for(Iterator i$ = _propsmap.keySet().iterator(); i$.hasNext();)
                {
                    String _name = (String)i$.next();
                    Map _valuemap = (Map)_propsmap.get(_name);
                    Iterator i$ = _valuemap.keySet().iterator();
                    while(i$.hasNext()) 
                    {
                        String _key = (String)i$.next();
                        _tp = (Map)props.get(_name);
                        if(_tp == null)
                            _tp = new HashMap();
                        _tp.put(_key, StringUtils.isBlank((String)_valuemap.get(_key)) ? "" : ((Object) ((String)_valuemap.get(_key))));
                        props.put(_name, _tp);
                    }
                }

                rect.put("props", props);
                status.put((new StringBuilder()).append("rect").append(num).toString(), rect);
                _parammap.put((new StringBuilder()).append(_node.getId()).append("_rect").toString(), (new StringBuilder()).append("rect").append(num).toString());
                num++;
            }
            catch(JSONException e)
            {
                LOGGER.error("\u6570\u636E\u8F6C\u6362\u5931\u8D25", e);
            }
        }

        List _pathlist = findFlowPaths(processid);
        for(int i = 0; i < _pathlist.size(); i++)
        {
            IWorkflowFlowPaths _paths = (IWorkflowFlowPaths)_pathlist.get(i);
            try
            {
                Map path = new HashMap();
                path.put("type", _paths.getType());
                path.put("from", MapUtils.getString(_parammap, (new StringBuilder()).append(_paths.getFromid()).append("_rect").toString(), ""));
                path.put("to", MapUtils.getString(_parammap, (new StringBuilder()).append(_paths.getToid()).append("_rect").toString(), ""));
                Map _propsmap = getFlowPropsMap(_paths.getId(), _paths.getEntityName());
                Map props = new HashMap();
                for(Iterator i$ = _propsmap.keySet().iterator(); i$.hasNext();)
                {
                    String _name = (String)i$.next();
                    Map _valuemap = (Map)_propsmap.get(_name);
                    Iterator i$ = _valuemap.keySet().iterator();
                    while(i$.hasNext()) 
                    {
                        String _key = (String)i$.next();
                        Map _tp = (Map)props.get(_name);
                        if(_tp == null)
                            _tp = new HashMap();
                        _tp.put(_key, StringUtils.isBlank((String)_valuemap.get(_key)) ? "" : ((Object) ((String)_valuemap.get(_key))));
                        props.put(_name, _tp);
                    }
                }

                path.put("props", props);
                List _dotslist = findFlowDots(_paths.getId());
                List dots = new LinkedList();
                IWorkflowFlowDots _dot;
                for(Iterator i$ = _dotslist.iterator(); i$.hasNext(); dots.add(JsonUtil.jsonToMap(new JSONObject(_dot.getPos()))))
                    _dot = (IWorkflowFlowDots)i$.next();

                path.put("dots", dots);
                paths.put((new StringBuilder()).append("path").append(num).toString(), path);
                num++;
            }
            catch(JSONException e)
            {
                LOGGER.error("\u6570\u636E\u8F6C\u6362\u5931\u8D25", e);
            }
        }

        _myflow.put("states", status);
        _myflow.put("paths", paths);
        return _myflow;
    }

    public void saveFlowData(int processid, Map myflow)
        throws JSONException
    {
        JSONObject _flowarray = new JSONObject(myflow);
        JSONObject states = _flowarray.getJSONObject("states");
        JSONObject paths = _flowarray.getJSONObject("paths");
        deleteHistoryData(processid);
        Map _parammap = new HashMap();
        if(states.length() > 0)
        {
            System.out.println((new StringBuilder()).append("_states.length() = ").append(states.length()).toString());
            for(Iterator _it = states.sortedKeys(); _it.hasNext();)
            {
                String _rect = (String)_it.next();
                System.out.println((new StringBuilder()).append("\u8282\u70B9\uFF1A").append(_rect).toString());
                JSONObject _detail = states.getJSONObject(_rect);
                String _type = _detail.optString("type", "");
                String _attr = _detail.optString("attr", "{}");
                String _text = _detail.getJSONObject("text").optString("text", "");
                IWorkflowFlowNode _node = getFlowNode(0);
                _node.setProcessid(Integer.valueOf(processid));
                _node.setType(_type);
                _node.setAttr(_attr);
                _node.setText(_text);
                int _nodeid = saveFlowNode(_node);
                _parammap.put((new StringBuilder()).append(_rect).append("_nodeid").toString(), Integer.valueOf(_nodeid));
                JSONObject _props = _detail.getJSONObject("props");
                Iterator _itprops = _props.keys();
                while(_itprops.hasNext()) 
                {
                    String _name = (String)_itprops.next();
                    JSONObject _valuesmap = _props.getJSONObject(_name);
                    System.out.println((new StringBuilder()).append("      ").append(_name).append(":").append(_valuesmap.toString()).toString());
                    if("conditionowner".equals(_name) || "stepcondition".equals(_name) || "steplinkdo".equals(_name))
                    {
                        String _value = _valuesmap.optString("value", "");
                        if(StringUtils.isNotBlank(_value))
                        {
                            String _owners[] = _value.split(",");
                            addFlowProp((new StringBuilder()).append(_name).append("size").toString(), "value", StringUtil.safeToString(Integer.valueOf(_owners.length), "0"), _nodeid, "WFFLOWNODE");
                            int s = 1;
                            while(s <= _owners.length) 
                            {
                                addFlowProp((new StringBuilder()).append(_name).append(s).toString(), "value", _owners[s - 1], _nodeid, "WFFLOWNODE");
                                s++;
                            }
                        } else
                        {
                            addFlowProp((new StringBuilder()).append(_name).append("size").toString(), "value", "0", _nodeid, "WFFLOWNODE");
                            addFlowProp(_name, "value", _value, _nodeid, "WFFLOWNODE");
                        }
                    } else
                    if("messagenotice".equals(_name))
                    {
                        Iterator _itvalues = _valuesmap.keys();
                        while(_itvalues.hasNext()) 
                        {
                            String _key = (String)_itvalues.next();
                            String _value = _valuesmap.optString(_key, "");
                            if("noticeowners".equals(_key))
                            {
                                if(StringUtils.isNotBlank(_value))
                                {
                                    String _owners[] = _value.split(",");
                                    addFlowProp(_name, (new StringBuilder()).append(_key).append("size").toString(), StringUtil.safeToString(Integer.valueOf(_owners.length), "0"), _nodeid, "WFFLOWNODE");
                                    int s = 1;
                                    while(s <= _owners.length) 
                                    {
                                        addFlowProp(_name, (new StringBuilder()).append(_key).append(s).toString(), _owners[s - 1], _nodeid, "WFFLOWNODE");
                                        s++;
                                    }
                                } else
                                {
                                    addFlowProp(_name, _key, _value, _nodeid, "WFFLOWNODE");
                                }
                            } else
                            {
                                addFlowProp(_name, _key, _value, _nodeid, "WFFLOWNODE");
                            }
                        }
                    } else
                    {
                        Iterator _itvalues = _valuesmap.keys();
                        while(_itvalues.hasNext()) 
                        {
                            String _key = (String)_itvalues.next();
                            String _value = _valuesmap.optString(_key, "");
                            addFlowProp(_name, _key, _value, _nodeid, "WFFLOWNODE");
                        }
                    }
                }
            }

        }
        if(paths.length() > 0)
        {
            System.out.println((new StringBuilder()).append("paths.length() = ").append(paths.length()).toString());
            Iterator _it = paths.sortedKeys();
            do
            {
                if(!_it.hasNext())
                    break;
                String _patha = (String)_it.next();
                System.out.println((new StringBuilder()).append("\u8DEF\u5F84\uFF1A").append(_patha).toString());
                JSONObject _detail = paths.getJSONObject(_patha);
                String _type = _detail.optString("type", "");
                String _from = _detail.optString("from", "");
                String _to = _detail.optString("to", "");
                int _fromid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append(_from).append("_nodeid").toString(), 0);
                int _toid = MapUtils.getIntValue(_parammap, (new StringBuilder()).append(_to).append("_nodeid").toString(), 0);
                IWorkflowFlowPaths _path = getFlowPaths(0);
                _path.setProcessid(Integer.valueOf(processid));
                _path.setType(_type);
                _path.setFromid(Integer.valueOf(_fromid));
                _path.setToid(Integer.valueOf(_toid));
                int _pathid = saveFlowPaths(_path);
                JSONObject _props = _detail.getJSONObject("props");
                for(Iterator _itprops = _props.keys(); _itprops.hasNext();)
                {
                    String _name = (String)_itprops.next();
                    JSONObject _valuesmap = _props.getJSONObject(_name);
                    System.out.println((new StringBuilder()).append("      ").append(_name).append(":").append(_valuesmap.toString()).toString());
                    Iterator _itvalues = _valuesmap.keys();
                    while(_itvalues.hasNext()) 
                    {
                        String _key = (String)_itvalues.next();
                        String _value = _valuesmap.optString(_key, "");
                        addFlowProp(_name, _key, _value, _pathid, "WFFLOWPATHS");
                    }
                }

                JSONArray _dotlist = _detail.getJSONArray("dots");
                if(_dotlist.length() > 0)
                {
                    System.out.println((new StringBuilder()).append("    \u70B9\uFF1A").append(_dotlist.length()).toString());
                    int i = 0;
                    while(i < _dotlist.length()) 
                    {
                        JSONObject _dots = _dotlist.getJSONObject(i);
                        String _pos = _dots.toString();
                        IWorkflowFlowDots _dot = getFlowDots(0);
                        _dot.setPathid(Integer.valueOf(_pathid));
                        _dot.setPos(_pos);
                        _dot.setSort(Integer.valueOf(i + 1));
                        saveFlowDots(_dot);
                        i++;
                    }
                }
            } while(true);
        }
    }

    public void addFlowProp(String name, String key, String value, int sourceid, String sourcename)
    {
        IWorkflowFlowProps _prop = getFlowProps(0);
        _prop.setName(name);
        _prop.setKey(key);
        _prop.setValue(value);
        _prop.setSort(Integer.valueOf(0));
        _prop.setStatus(Integer.valueOf(0));
        _prop.setSourceid(Integer.valueOf(sourceid));
        _prop.setSourcename(sourcename);
        saveFlowProps(_prop);
    }

    public List findFlowNode(int processid)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(processid > 0)
        {
            sql.append("processid=:processid");
            _param.put("processid", Integer.valueOf(processid));
        } else
        {
            sql.append("1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowNode, sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public List findFlowPaths(int processid)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(processid > 0)
        {
            sql.append("processid=:processid");
            _param.put("processid", Integer.valueOf(processid));
        } else
        {
            sql.append("1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowPaths, sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public List findFlowPaths(int processid, int nodeid, int type)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(processid > 0)
        {
            sql.append("processid=:processid");
            _param.put("processid", Integer.valueOf(processid));
        } else
        {
            sql.append("1=2");
        }
        if(nodeid > 0)
            if(type == 1)
            {
                sql.append(" and toid=:nodeid");
                _param.put("nodeid", Integer.valueOf(nodeid));
            } else
            if(type == 0)
            {
                sql.append(" and fromid=:nodeid");
                _param.put("nodeid", Integer.valueOf(nodeid));
            }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowPaths, sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public List findFlowProps(int sourceid, String sourcename)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(sourceid > 0 && StringUtils.isNotBlank(sourcename))
        {
            sql.append("sourceid=:sourceid and sourcename=:sourcename");
            _param.put("sourceid", Integer.valueOf(sourceid));
            _param.put("sourcename", sourcename);
        } else
        {
            sql.append("1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowProps, sql.toString());
        query.setParameters(_param);
        query.setDefaultOrderBy(" sort asc");
        return query.getResult();
    }

    public List findFlowProps(int sourceid, String sourcename, String name)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(sourceid > 0 && StringUtils.isNotBlank(sourcename))
        {
            sql.append("sourceid=:sourceid and sourcename=:sourcename \n");
            _param.put("sourceid", Integer.valueOf(sourceid));
            _param.put("sourcename", sourcename);
        } else
        {
            sql.append("1=2");
        }
        if(StringUtils.isNotBlank(name))
        {
            sql.append(" and name=:name \n");
            _param.put("name", name);
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowProps, sql.toString());
        query.setParameters(_param);
        query.setDefaultOrderBy(" sort asc");
        return query.getResult();
    }

    public IWorkflowFlowProps findFlowProps(int sourceid, String sourcename, String name, String key)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(sourceid > 0 && StringUtils.isNotBlank(sourcename))
        {
            sql.append("sourceid=:sourceid and sourcename=:sourcename \n");
            _param.put("sourceid", Integer.valueOf(sourceid));
            _param.put("sourcename", sourcename);
        } else
        {
            sql.append("1=2");
        }
        if(StringUtils.isNotBlank(name))
        {
            sql.append(" and name=:name \n");
            _param.put("name", name);
        }
        if(StringUtils.isNotBlank(key))
        {
            sql.append(" and key=:key \n");
            _param.put("key", key);
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowProps, sql.toString());
        query.setParameters(_param);
        query.setDefaultOrderBy(" sort asc");
        if(query.getResultCount() > 0)
            query.getResult().get(0);
        return new WorkflowFlowPropsImpl();
    }

    public Map getFlowPropsMap(int sourceid, String sourcename)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        sql.append("select name, key, value \n");
        sql.append("from WF_FLOWPROPS \n");
        sql.append("where 1=1 and status=0 \n");
        if(sourceid > 0 && StringUtils.isNotBlank(sourcename))
        {
            sql.append(" and sourceid=:sourceid and sourcename=:sourcename \n");
            _param.put("sourceid", Integer.valueOf(sourceid));
            _param.put("sourcename", sourcename);
        } else
        {
            sql.append(" and 1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(_param);
        query.setDefaultOrderBy(" sort asc");
        List _list = query.getResult();
        return changePropsToMap(_list);
    }

    private Map changePropsToMap(List list)
    {
        Map _res = new HashMap();
        Map _t = new HashMap();
        Iterator i$ = list.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map _map = (Map)i$.next();
            String _name = MapUtils.getString(_map, "name", "");
            String _key = MapUtils.getString(_map, "key", "");
            String _value = MapUtils.getString(_map, "value", "");
            Map _map2 = (Map)_t.get(_name);
            if(_map2 == null)
                _map2 = new HashMap();
            _map2.put(_key, _value);
            _t.put(_name, _map2);
            if(!_name.startsWith("conditionowner") && !_name.startsWith("stepcondition") && !_name.startsWith("steplinkdo") && !_name.startsWith("messagenotice"))
                _res.put(_name, _map2);
        } while(true);
        if(_t.containsKey("conditionownersize"))
        {
            int conditionownersize = MapUtils.getIntValue((Map)_t.get("conditionownersize"), "value", 0);
            String conditionowner = "";
            for(int i = 1; i <= conditionownersize; i++)
                conditionowner = (new StringBuilder()).append(conditionowner).append(MapUtils.getString((Map)_t.get((new StringBuilder()).append("conditionowner").append(i).toString()), "value", "")).append(",").toString();

            if(conditionowner.endsWith(","))
                conditionowner = conditionowner.substring(0, conditionowner.length() - 1);
            Map _map2 = new HashMap();
            _map2.put("value", conditionowner);
            _res.put("conditionowner", _map2);
        }
        if(_t.containsKey("stepconditionsize"))
        {
            int stepconditionsize = MapUtils.getIntValue((Map)_t.get("stepconditionsize"), "value", 0);
            String stepcondition = "";
            for(int i = 1; i <= stepconditionsize; i++)
                stepcondition = (new StringBuilder()).append(stepcondition).append(MapUtils.getString((Map)_t.get((new StringBuilder()).append("stepcondition").append(i).toString()), "value", "")).append(",").toString();

            if(stepcondition.endsWith(","))
                stepcondition = stepcondition.substring(0, stepcondition.length() - 1);
            Map _map2 = new HashMap();
            _map2.put("value", stepcondition);
            _res.put("stepcondition", _map2);
        }
        if(_t.containsKey("steplinkdosize"))
        {
            int steplinkdosize = MapUtils.getIntValue((Map)_t.get("steplinkdosize"), "value", 0);
            String steplinkdo = "";
            for(int i = 1; i <= steplinkdosize; i++)
                steplinkdo = (new StringBuilder()).append(steplinkdo).append(MapUtils.getString((Map)_t.get((new StringBuilder()).append("steplinkdo").append(i).toString()), "value", "")).append(",").toString();

            if(steplinkdo.endsWith(","))
                steplinkdo = steplinkdo.substring(0, steplinkdo.length() - 1);
            Map _map2 = new HashMap();
            _map2.put("value", steplinkdo);
            _res.put("steplinkdo", _map2);
        }
        if(_t.containsKey("messagenotice"))
        {
            Map messagenoticeMap = (Map)_t.get("messagenotice");
            Map _map2 = new HashMap();
            Iterator i$ = messagenoticeMap.keySet().iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                String _mkey = (String)i$.next();
                if(!_mkey.startsWith("noticeowners"))
                    _map2.put(_mkey, messagenoticeMap.get(_mkey));
            } while(true);
            int noticeownerssize = MapUtils.getIntValue(messagenoticeMap, "noticeownerssize", 0);
            String noticeowners = "";
            for(int i = 1; i <= noticeownerssize; i++)
                noticeowners = (new StringBuilder()).append(noticeowners).append(MapUtils.getString(messagenoticeMap, (new StringBuilder()).append("noticeowners").append(i).toString(), "")).append(",").toString();

            if(noticeowners.endsWith(","))
                noticeowners = noticeowners.substring(0, noticeowners.length() - 1);
            _map2.put("noticeowners", noticeowners);
            _res.put("messagenotice", _map2);
        }
        return _res;
    }

    public List findFlowDots(int pathid)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        if(pathid > 0)
        {
            sql.append("pathid=:pathid");
            _param.put("pathid", Integer.valueOf(pathid));
        } else
        {
            sql.append("1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/workflow/entity/drawprocess/IWorkflowFlowDots, sql.toString());
        query.setParameters(_param);
        query.setDefaultOrderBy(" sort asc");
        return query.getResult();
    }

    public Boolean checkIsDrawFlow(int processid)
    {
        StringBuilder sql = new StringBuilder();
        Map _param = new HashMap();
        sql.append("select objid from WF_FLOWNODE where ");
        if(processid > 0)
        {
            sql.append("processid=:processid");
            _param.put("processid", Integer.valueOf(processid));
        } else
        {
            sql.append("1=2");
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(_param);
        return Boolean.valueOf(query.getResult().size() > 0);
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/workflow/service/drawprocess/impl/WorkflowDrawServiceImpl);

}
