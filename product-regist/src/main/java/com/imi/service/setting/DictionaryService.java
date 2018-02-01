package com.imi.service.setting;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.imi.entity.setting.Dictionary;

public interface DictionaryService {
	/**
     * getDicEntity(获取单个字典实体)
     *
     * @param id 主键ID
     * @return Dictionary 字典实体
     * @Description: 根据主键获取字典实体
     */
    public Dictionary getDicEntity(Long id) ;

  
    
    public List<Dictionary> findChildDicLst(String key);

    /**
     * save(保存:新建)
     *
     * @param entity 参数类型
     * @return void 返回类型
     */
    public void add(Dictionary entity) ;

    /**
     * 修改
     *
     * @param entity
     * @param user
     */
    public void modify(Dictionary entity);

    /**
     * get(根据ID查询字典信息)
     *
     * @param id 主键id
     * @return Dictionary返回类型
     */
    public Dictionary get(Long id);

    public String getDictJsonData() ;



    /**
     * get(获取所有的字典类型)
     *
     * @return List<Dictionary>返回类型
     */
    public List<Dictionary> getDicTypes();

    /**
     * 获取默认的字典通过字典值
     * 1.仅查出id用于进行判断
     */
    public Long getIdByValue(String value);
    /**
     * 获取字典通过字典值
     */
 
    public Dictionary getByValue(String value) ;

    /**
     * 获取字典列表通过父节点字典值
     */
    @Transactional(readOnly = true)
    public List<Dictionary> findChildren(String parentValue) ;


    /**
     * 获取自己和兄弟节点
     */
    @Transactional(readOnly = true)
    public List<Dictionary> findBrother(String value) ;

    /**
     * findChildren(获取字典集合)
     *
     * @param dicType 字典常量值DictionaryConstant
     * @param bool    是否深度查找  (默认全部)
     * @return List<Dictionary> 字典集合
     * @Description: 根据大分类获取小分类集合
     */
    public List<Dictionary> findChildren(String dicType, Boolean bool);

    
    public String getNameById(Long id) ;

    /**
     * 根据parentId 和 指定的key获取value
     *
     * @param key
     * @param parentId
     * @return
     */
    public Dictionary getValue(String key, Long parentId);
    
    /**
     * 根据parentId 和 指定的key获取value
     *	
     * @param key
     * @param parentId
     * @return
     */
    public Dictionary getValueByType(String key, String type);
}
