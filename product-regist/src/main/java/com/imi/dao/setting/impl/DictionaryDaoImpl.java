package com.imi.dao.setting.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.IDictionaryDao;
import com.imi.entity.setting.Dictionary;

/**
 * 字典数据访问类
 */
@Repository
public class DictionaryDaoImpl extends BaseDaoImpl<Dictionary> implements IDictionaryDao{

    /**
     * 获取主键编号通过代码值
     */
    public Long getIdByValue(String value) {
        String hql = "select dic.id from Dictionary dic where dic.dicValue=?";
        return findUnique(hql, value);
    }

    /**
     * 获取字典通过字典值
     */
    public Dictionary getByValue(String value) {
        String hql = "from Dictionary dic where dic.dicValue=?";
        return findUnique(hql, value);
    }

    /**
     * 获取字典列表通过父节点字典值
     */
    public List<Dictionary> findChildren(String parentValue) {
        String hql = "from Dictionary dic where dic.parent.dicValue=? order by dic.dicValue";

        return find(hql, parentValue);
    }

    /**
     * 获取字典列表通过节点字典值
     */
    public List<Dictionary> findBrother(String value) {
        String hql = "from Dictionary dic" +
                " where dic.parent=(select current.parent from Dictionary current where current.dicValue=?)" +
                " order by dic.dicValue";
        return find(hql, value);
    }

    public Dictionary getValue(String key, Long parentId) {
        String hql = "from Dictionary dic where dic.parent.id=? and dic.dicType=? and dic.isDeleted=false";
        return findUnique(hql, parentId, key);
    }


    public Dictionary getValueByType(String key, String type) {
        String hql = "from Dictionary dic where dic.dicType=? and dic.dicKey=? and dic.isDeleted=false";
        return findUnique(hql, type, key);
    }

}
