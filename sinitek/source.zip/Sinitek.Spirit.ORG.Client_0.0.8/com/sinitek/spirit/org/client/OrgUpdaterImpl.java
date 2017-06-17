// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgUpdaterImpl.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.spirit.org.client:
//            OrgClientException, UnitImpl, EmployeeImpl, RelationshipImpl, 
//            AbstractOrgObject, OrgObjectService

public class OrgUpdaterImpl
    implements IOrgUpdater
{
    private static class OrgOperationBean
    {

        public Object getTargetOrgObject()
        {
            return targetOrgObject;
        }

        public void setTargetOrgObject(Object targetOrgObject)
        {
            this.targetOrgObject = targetOrgObject;
        }

        public OPERATIONTYPE getOperationType()
        {
            return operationType;
        }

        public void setOperationType(OPERATIONTYPE operationType)
        {
            this.operationType = operationType;
        }

        private Object targetOrgObject;
        private OPERATIONTYPE operationType;

        private OrgOperationBean(Object targetOrgObject, OPERATIONTYPE operationType)
        {
            this.targetOrgObject = targetOrgObject;
            this.operationType = operationType;
        }

    }


    public OrgUpdaterImpl()
    {
        newOrgObjectMap = new HashMap();
        deleteObjectMap = new HashMap();
        updateList = new ArrayList();
        newRelationMap = new HashMap();
        deleteRelationMap = new HashMap();
    }

    public IUnit addUnit(String unitName, String unitType, String unitDesc)
    {
        String orgId;
        UnitImpl unit;
        if(StringUtils.isBlank(unitName))
            throw new OrgClientException("0001");
        if(StringUtils.isBlank(unitType))
            throw new OrgClientException("0002");
        orgId = OrgObjectService.getOrgId(unitType);
        unit = new UnitImpl();
        unit.setUnitType(unitType);
        unit.setId(orgId);
        unit.setDescription(unitDesc);
        unit.setName(unitName);
        lock.lock();
        newOrgObjectMap.put(orgId, unit);
        updateList.add(new OrgOperationBean(unit, OrgOperationBean.OPERATIONTYPE.INSERT));
        lock.unlock();
        break MISSING_BLOCK_LABEL_146;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        LOGGER.debug((new StringBuilder()).append("ID\u4E3A[").append(unit.getId()).append("]\u7684Unit\u5DF2\u52A0\u5165\u65B0\u589E\u961F\u5217").toString());
        return unit;
    }

    public IUnit updateUnit(IUnit unit, String unitName, String unitType, String unitDesc)
        throws OrgException
    {
        lock.lock();
        UnitImpl unitimpl;
        UnitImpl targetUnit;
        if(newOrgObjectMap.containsKey(unit.getId()))
        {
            targetUnit = (UnitImpl)newOrgObjectMap.get(unit.getId());
        } else
        {
            if(deleteObjectMap.containsKey(unit.getId()))
                throw new OrgClientException("0004", new Object[] {
                    unit.getId(), unit.getName()
                });
            targetUnit = OrgObjectService.getUnitById(unit.getId());
            updateList.add(new OrgOperationBean(targetUnit, OrgOperationBean.OPERATIONTYPE.UPDATE));
        }
        if(StringUtils.isNotBlank(unitName))
            targetUnit.setName(unitName);
        if(StringUtils.isNotBlank(unitType))
            targetUnit.setUnitType(unitType);
        targetUnit.setDescription(unitDesc);
        LOGGER.debug((new StringBuilder()).append("ID\u4E3A[").append(targetUnit.getId()).append("]\u7684Unit\u5DF2\u52A0\u5165\u66F4\u65B0\u961F\u5217").toString());
        unitimpl = targetUnit;
        lock.unlock();
        return unitimpl;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public IEmployee addEmployee(String empName, String userId, String empDesc, Boolean isInservice)
    {
        EmployeeImpl emp;
        if(StringUtils.isBlank(empName))
            throw new OrgClientException("0011");
        emp = new EmployeeImpl();
        emp.setId(OrgObjectService.getOrgId("emp"));
        emp.setName(empName);
        emp.setUserId(userId);
        emp.setDescription(empDesc);
        emp.setInservice(isInservice);
        lock.lock();
        newOrgObjectMap.put(emp.getId(), emp);
        updateList.add(new OrgOperationBean(emp, OrgOperationBean.OPERATIONTYPE.INSERT));
        lock.unlock();
        break MISSING_BLOCK_LABEL_136;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        LOGGER.debug((new StringBuilder()).append("ID\u4E3A[").append(emp.getId()).append("]\u7684Employee\u5DF2\u52A0\u5165\u65B0\u589E\u961F\u5217").toString());
        return emp;
    }

    public IEmployee updateEmployee(IEmployee employee, String empName, String userId, String empDesc, Boolean isInservice)
        throws OrgException
    {
        lock.lock();
        EmployeeImpl employeeimpl;
        EmployeeImpl ret = null;
        if(newOrgObjectMap.containsKey(employee.getId()))
        {
            ret = (EmployeeImpl)newOrgObjectMap.get(employee.getId());
        } else
        {
            if(deleteObjectMap.containsKey(employee.getId()))
                throw new OrgClientException("0005", new Object[] {
                    employee.getId()
                });
            ret = OrgObjectService.getEmployeeById(employee.getId());
            updateList.add(new OrgOperationBean(ret, OrgOperationBean.OPERATIONTYPE.UPDATE));
        }
        if(StringUtils.isNotBlank(empName))
            ret.setName(empName);
        if(StringUtils.isNotBlank(userId))
            ret.setUserId(userId);
        ret.setDescription(empDesc);
        ret.setInservice(isInservice);
        LOGGER.debug((new StringBuilder()).append("ID\u4E3A[").append(ret.getId()).append("]\u7684Employee\u5DF2\u52A0\u5165\u66F4\u65B0\u961F\u5217").toString());
        employeeimpl = ret;
        lock.unlock();
        return employeeimpl;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public IRelationship addRelationShip(IUnit fromObject, IOrgObject toObject, String relationshipType)
        throws OrgException
    {
        return addRelationShip(fromObject, toObject, relationshipType, null, false);
    }

    public IRelationship addRelationShip(IUnit fromObject, IOrgObject toObject, String relationshipType, IOrgObject referenceObject, boolean prior)
        throws OrgException
    {
        if(fromObject == null)
            throw new OrgClientException("0021");
        if(toObject == null)
            throw new OrgClientException("0022");
        if(fromObject.equals(toObject))
            throw new OrgClientException("0023", new Object[] {
                fromObject.getId()
            });
        relationshipType = StringUtils.trimToNull(relationshipType);
        if(relationshipType == null)
            throw new OrgClientException("0026");
        lock.lock();
        String relationKey;
        IRelationship irelationship;
        relationKey = (new StringBuilder()).append(fromObject.getId()).append("_").append(toObject.getId()).append("_").append(relationshipType.toUpperCase()).toString();
        if(!newRelationMap.containsKey(relationKey))
            break MISSING_BLOCK_LABEL_176;
        irelationship = (IRelationship)newRelationMap.get(relationKey);
        lock.unlock();
        return irelationship;
        if(!OrgObjectService.checkOrgRelationshipExists(fromObject.getId(), toObject.getId(), relationshipType) || deleteRelationMap.containsKey(relationKey))
            break MISSING_BLOCK_LABEL_248;
        irelationship = (IRelationship)OrgObjectService.queryRelations(fromObject.getId(), toObject.getId(), relationshipType).get(0);
        lock.unlock();
        return irelationship;
        Iterator i$;
        if(!newOrgObjectMap.containsKey(fromObject.getId()) && (!OrgObjectService.checkOrgObjectExists(fromObject.getId()) || deleteObjectMap.containsKey(fromObject.getId())))
            throw new OrgClientException("0007", new Object[] {
                fromObject.getId()
            });
        if(!newOrgObjectMap.containsKey(toObject.getId()) && (!OrgObjectService.checkOrgObjectExists(toObject.getId()) || deleteObjectMap.containsKey(toObject.getId())))
            throw new OrgClientException("0007", new Object[] {
                toObject.getId()
            });
        if(referenceObject != null)
        {
            if(!newOrgObjectMap.containsKey(referenceObject.getId()) && (!OrgObjectService.checkOrgObjectExists(referenceObject.getId()) || deleteObjectMap.containsKey(referenceObject.getId())))
                throw new OrgClientException("0007", new Object[] {
                    referenceObject.getId()
                });
            String referenceRelationKey = (new StringBuilder()).append(fromObject.getId()).append("_").append(referenceObject.getId()).append("_").append(relationshipType.toUpperCase()).toString();
            if(!newRelationMap.containsKey(referenceRelationKey) && (!OrgObjectService.checkOrgRelationshipExists(fromObject.getId(), referenceObject.getId(), relationshipType) || deleteRelationMap.containsKey(referenceRelationKey)))
                throw new OrgClientException("0027", new Object[] {
                    fromObject.getId(), fromObject.getName(), referenceObject.getId(), referenceObject.getName(), relationshipType
                });
        }
        IRelationship removedRelation = (IRelationship)deleteRelationMap.remove(relationKey);
        if(removedRelation == null)
            break MISSING_BLOCK_LABEL_744;
        OrgOperationBean _targetBean = null;
        i$ = updateList.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            OrgOperationBean bean = (OrgOperationBean)i$.next();
            if(!bean.getTargetOrgObject().equals(removedRelation))
                continue;
            _targetBean = bean;
            break;
        } while(true);
        if(_targetBean != null)
            updateList.remove(_targetBean);
        i$ = changeRelationShipOrder(fromObject, toObject, relationshipType, referenceObject, prior);
        lock.unlock();
        return i$;
        RelationshipImpl relationship = new RelationshipImpl();
        relationship.setFromObject(fromObject);
        relationship.setToObject(toObject);
        relationship.setType(relationshipType);
        relationship.setReferenceObject(referenceObject);
        relationship.setPrior(prior);
        newRelationMap.put(relationKey, relationship);
        updateList.add(new OrgOperationBean(relationship, OrgOperationBean.OPERATIONTYPE.INSERT));
        LOGGER.debug((new StringBuilder()).append("\u4ECE[").append(fromObject.getId()).append("]\u6307\u5411[").append(toObject.getId()).append("]\u7684\u7C7B\u578B\u4E3A[").append(relationshipType).append("]\u7684\u5173\u8054\u5173\u7CFB\u5DF2\u52A0\u5165\u65B0\u589E\u961F\u5217").toString());
        i$ = relationship;
        lock.unlock();
        return i$;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public IRelationship changeRelationShipOrder(IUnit fromObject, IOrgObject toObject, String relationshipType, IOrgObject referenceObject, boolean prior)
        throws OrgException
    {
        if(fromObject == null)
            throw new OrgClientException("0021");
        if(toObject == null)
            throw new OrgClientException("0022");
        if(fromObject.equals(toObject))
            throw new OrgClientException("0023", new Object[] {
                fromObject.getId()
            });
        relationshipType = StringUtils.trimToNull(relationshipType);
        if(relationshipType == null)
            throw new OrgClientException("0026");
        lock.lock();
        RelationshipImpl relationshipimpl;
        if(referenceObject != null)
        {
            if(!newOrgObjectMap.containsKey(referenceObject.getId()) && (!OrgObjectService.checkOrgObjectExists(referenceObject.getId()) || deleteObjectMap.containsKey(referenceObject.getId())))
                throw new OrgClientException("0007", new Object[] {
                    referenceObject.getId()
                });
            String referenceRelationKey = (new StringBuilder()).append(fromObject.getId()).append("_").append(referenceObject.getId()).append("_").append(relationshipType.toUpperCase()).toString();
            if(!newRelationMap.containsKey(referenceRelationKey) && (!OrgObjectService.checkOrgRelationshipExists(fromObject.getId(), referenceObject.getId(), relationshipType) || deleteRelationMap.containsKey(referenceRelationKey)))
                throw new OrgClientException("0027", new Object[] {
                    fromObject.getId(), fromObject.getName(), referenceObject.getId(), referenceObject.getName(), relationshipType
                });
        }
        RelationshipImpl ret = null;
        String relationKey = (new StringBuilder()).append(fromObject.getId()).append("_").append(toObject.getId()).append("_").append(relationshipType.toUpperCase()).toString();
        if(newRelationMap.containsKey(relationKey))
        {
            ret = (RelationshipImpl)newRelationMap.get(relationKey);
        } else
        {
            if(deleteRelationMap.containsKey(relationKey))
                throw new OrgClientException("0009", new Object[] {
                    fromObject.getId(), fromObject.getName(), toObject.getId(), toObject.getName(), relationKey
                });
            List relationships = OrgObjectService.queryRelations(fromObject.getId(), toObject.getId(), relationshipType);
            if(!relationships.isEmpty())
            {
                ret = (RelationshipImpl)relationships.get(0);
                updateList.add(new OrgOperationBean(ret, OrgOperationBean.OPERATIONTYPE.UPDATE));
            } else
            {
                throw new OrgClientException("0025", new Object[] {
                    fromObject.getId(), fromObject.getName(), toObject.getId(), toObject.getName(), relationshipType
                });
            }
        }
        ret.setReferenceObject(referenceObject);
        ret.setPrior(prior);
        LOGGER.debug((new StringBuilder()).append("\u4ECE[").append(fromObject.getId()).append("]\u6307\u5411[").append(toObject.getId()).append("]\u7684\u7C7B\u578B\u4E3A[").append(relationshipType).append("]\u7684\u5173\u8054\u5173\u7CFB\u5DF2\u52A0\u5165\u66F4\u65B0\u961F\u5217").toString());
        relationshipimpl = ret;
        lock.unlock();
        return relationshipimpl;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void deleteObject(IOrgObject orgObject, boolean checkRelationship)
        throws OrgException
    {
        lock.lock();
        if(deleteObjectMap.containsKey(orgObject.getId()))
        {
            LOGGER.info((new StringBuilder()).append("\u7EC4\u7EC7\u7ED3\u6784\u5BF9\u8C61[").append(orgObject.getId()).append("][").append(orgObject.getName()).append("]\u5DF2\u7ECF\u5728\u5220\u9664\u961F\u5217\u4E2D\uFF0C\u672C\u6B21\u64CD\u4F5C\u5FFD\u7565").toString());
        } else
        {
            Iterator i$ = newRelationMap.entrySet().iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
                String keyElements[] = ((String)entry.getKey()).split("_");
                if(keyElements[0].equalsIgnoreCase(orgObject.getId()) || keyElements[1].equalsIgnoreCase(orgObject.getId()))
                {
                    if(!checkRelationship)
                        throw new OrgClientException("0024", new Object[] {
                            orgObject.getId(), orgObject.getName()
                        });
                    IRelationship _rela = (IRelationship)entry.getValue();
                    deleteRelationship(_rela.getFromObject(), _rela.getToObject(), _rela.getType());
                }
            } while(true);
            i$ = OrgObjectService.getRelationshipsByOrgId(orgObject.getId()).iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IRelationship relationship = (IRelationship)i$.next();
                String relationKey = (new StringBuilder()).append(relationship.getFromObject().getId()).append("_").append(relationship.getToObject().getId()).append("_").append(relationship.getType().toUpperCase()).toString();
                if(!deleteRelationMap.containsKey(relationKey))
                {
                    if(!checkRelationship)
                        throw new OrgClientException("0024", new Object[] {
                            orgObject.getId(), orgObject.getName()
                        });
                    deleteRelationship(relationship.getFromObject(), relationship.getToObject(), relationship.getType());
                }
            } while(true);
            if(newOrgObjectMap.remove(orgObject.getId()) != null)
            {
                OrgOperationBean _targetBean = null;
                Iterator i$ = updateList.iterator();
                do
                {
                    if(!i$.hasNext())
                        break;
                    OrgOperationBean bean = (OrgOperationBean)i$.next();
                    if(!bean.getTargetOrgObject().equals(orgObject))
                        continue;
                    _targetBean = bean;
                    break;
                } while(true);
                if(_targetBean != null)
                    updateList.remove(_targetBean);
            } else
            if(OrgObjectService.checkOrgObjectExists(orgObject.getId()))
            {
                deleteObjectMap.put(orgObject.getId(), orgObject);
                updateList.add(new OrgOperationBean(orgObject, OrgOperationBean.OPERATIONTYPE.DELETE));
                LOGGER.debug((new StringBuilder()).append("ID\u4E3A[").append(orgObject.getId()).append("]\u7684\u7EC4\u7EC7\u7ED3\u6784\u5BF9\u8C61\u5DF2\u52A0\u5165\u5220\u9664\u961F\u5217").toString());
            } else
            {
                throw new OrgClientException("0007", new Object[] {
                    orgObject.getId()
                });
            }
        }
        lock.unlock();
        break MISSING_BLOCK_LABEL_649;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public int deleteRelationship(IOrgObject fromObject, IOrgObject toObject, String relationshipType)
        throws OrgException
    {
        lock.lock();
        String relationKey;
        int i;
        relationshipType = StringUtils.trimToNull(relationshipType);
        if(fromObject == null || toObject == null || relationshipType == null)
            break MISSING_BLOCK_LABEL_505;
        relationKey = (new StringBuilder()).append(fromObject.getId()).append("_").append(toObject.getId()).append("_").append(relationshipType.toUpperCase()).toString();
        if(!deleteRelationMap.containsKey(relationKey))
            break MISSING_BLOCK_LABEL_188;
        LOGGER.info((new StringBuilder()).append("\u4ECE[").append(fromObject.getId()).append("][").append(fromObject.getName()).append("]\u6307\u5411[").append(toObject.getId()).append("][").append(toObject.getName()).append("]\u7C7B\u578B\u4E3A[").append(relationshipType).append("]\u7684\u5173\u8054\u5173\u7CFB\u5DF2\u7ECF\u5728\u5220\u9664\u961F\u5217\u4E2D\u3002\u672C\u6B21\u64CD\u4F5C\u5FFD\u7565\u3002").toString());
        i = 0;
        lock.unlock();
        return i;
        int j;
        IRelationship removeRelation = (IRelationship)newRelationMap.remove(relationKey);
        if(removeRelation == null)
            break MISSING_BLOCK_LABEL_300;
        OrgOperationBean _targetBean = null;
        Iterator i$ = updateList.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            OrgOperationBean bean = (OrgOperationBean)i$.next();
            if(!bean.getTargetOrgObject().equals(removeRelation))
                continue;
            _targetBean = bean;
            break;
        } while(true);
        if(_targetBean != null)
            updateList.remove(_targetBean);
        j = 1;
        lock.unlock();
        return j;
        int k;
        List relationships = OrgObjectService.queryRelations(fromObject.getId(), toObject.getId(), relationshipType);
        if(relationships.isEmpty())
            break MISSING_BLOCK_LABEL_451;
        RelationshipImpl relationship = (RelationshipImpl)relationships.get(0);
        LOGGER.debug((new StringBuilder()).append("\u4ECE[").append(fromObject.getId()).append("]\u6307\u5411[").append(toObject.getId()).append("]\u7684\u7C7B\u578B\u4E3A[").append(relationshipType).append("]\u7684\u5173\u8054\u5173\u7CFB\u5DF2\u52A0\u5165\u5220\u9664\u961F\u5217").toString());
        deleteRelationMap.put(relationKey, relationship);
        updateList.add(new OrgOperationBean(relationship, OrgOperationBean.OPERATIONTYPE.DELETE));
        k = 1;
        lock.unlock();
        return k;
        throw new OrgClientException("0025", new Object[] {
            fromObject.getId(), fromObject.getName(), toObject.getId(), toObject.getName(), relationshipType
        });
        String fromObjId = fromObject != null ? fromObject.getId() : null;
        String toObjId = toObject != null ? toObject.getId() : null;
        int count = 0;
        Iterator i$ = newRelationMap.values().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IRelationship relationship = (IRelationship)i$.next();
            if((fromObjId == null || relationship.getFromObject().getId().equalsIgnoreCase(fromObjId)) && (toObjId == null || relationship.getToObject().getId().equalsIgnoreCase(toObjId)) && (relationshipType == null || relationship.getType().equalsIgnoreCase(relationshipType)))
                count += deleteRelationship(relationship.getFromObject(), relationship.getToObject(), relationship.getType());
        } while(true);
        List relationships = OrgObjectService.queryRelations(fromObjId, toObjId, relationshipType);
        Iterator i$ = relationships.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            RelationshipImpl relationship = (RelationshipImpl)i$.next();
            String relationKey = (new StringBuilder()).append(relationship.getFromObject().getId()).append("_").append(relationship.getToObject().getId()).append("_").append(relationship.getType().toUpperCase()).toString();
            if(!deleteRelationMap.containsKey(relationKey))
                count += deleteRelationship(relationship.getFromObject(), relationship.getToObject(), relationship.getType());
        } while(true);
        i$ = count;
        lock.unlock();
        return i$;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void flush()
        throws OrgException
    {
        List operationList;
        operationList = new ArrayList();
        lock.lock();
        if(!updateList.isEmpty())
            break MISSING_BLOCK_LABEL_47;
        LOGGER.info("\u6CA1\u6709\u9700\u8981\u63D0\u4EA4\u7684\u66F4\u65B0\u6570\u636E");
        lock.unlock();
        return;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u63D0\u4EA4[").append(updateList.size()).append("]\u6761\u6570\u636E").toString());
        operationList.addAll(updateList);
        updateList.clear();
        newOrgObjectMap.clear();
        newRelationMap.clear();
        deleteObjectMap.clear();
        deleteRelationMap.clear();
        lock.unlock();
        break MISSING_BLOCK_LABEL_174;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        StringBuilder operData = new StringBuilder();
        for(Iterator i$ = operationList.iterator(); i$.hasNext(); operData.append(";"))
        {
            OrgOperationBean bean = (OrgOperationBean)i$.next();
            operData.append(bean.getOperationType().toString()).append("|");
            Object targetObj = bean.getTargetOrgObject();
            if(targetObj instanceof AbstractOrgObject)
            {
                AbstractOrgObject orgObject = (AbstractOrgObject)targetObj;
                operData.append("OBJ").append("|").append(orgObject.getId()).append("|").append(orgObject.toDataString()).append("|").append(orgObject.getVersionId());
                continue;
            }
            if(targetObj instanceof RelationshipImpl)
            {
                RelationshipImpl relationship = (RelationshipImpl)targetObj;
                operData.append("RELA").append("|").append(relationship.getObjId()).append("|").append(relationship.toDataString()).append("|").append(relationship.getVersionId());
            }
        }

        String data = operData.deleteCharAt(operData.length() - 1).toString();
        OrgObjectService.commitChanges(data);
        return;
    }

    private Map newOrgObjectMap;
    private Map deleteObjectMap;
    private List updateList;
    private Map newRelationMap;
    private Map deleteRelationMap;
    private final Lock lock = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/spirit/org/client/OrgUpdaterImpl);

}
