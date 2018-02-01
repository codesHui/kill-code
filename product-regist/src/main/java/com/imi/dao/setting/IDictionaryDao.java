package com.imi.dao.setting;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.setting.Dictionary;

public   interface IDictionaryDao extends IBaseDao<Dictionary>{
    /**
     * 获取主键编号通过代码值
     */
    public Long getIdByValue(String value);
    /**
     * 获取字典通过字典值
     */
    public Dictionary getByValue(String value);
    /**
     * 获取字典列表通过父节点字典值
     */
    public List<Dictionary> findChildren(String parentValue);

    /**
     * 获取字典列表通过节点字典值
     */
    public List<Dictionary> findBrother(String value) ;

    public Dictionary getValue(String key, Long parentId);


    public Dictionary getValueByType(String key, String type);
}
