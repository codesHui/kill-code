package com.imi.service.setting.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imi.dao.setting.IDictionaryDao;
import com.imi.entity.setting.Dictionary;
import com.imi.service.setting.DictionaryService;

/**
 * 字典服务类
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    //private Logger logger = LoggerFactory.getLogger(ComponentService.class);
    @Resource
    private IDictionaryDao dictionaryDao;

    /**
     * getDicEntity(获取单个字典实体)
     *
     * @param id 主键ID
     * @return Dictionary 字典实体
     * @Description: 根据主键获取字典实体
     */
    public Dictionary getDicEntity(Long id) {
        return dictionaryDao.get(id);
    }

  
    
    public List<Dictionary> findChildDicLst(String key){
    	DetachedCriteria criteria = DetachedCriteria.forClass(Dictionary.class);
    	criteria.add(Restrictions.eq("isDeleted", false));
        criteria.add(Restrictions.eq("dicType", key));
        
        return dictionaryDao.find(criteria);
    }

    /**
     * save(保存：修改/新建)
     *
     * @param entity 参数类型
     * @return void 返回类型
     */
    public void add(Dictionary entity) {
  
        dictionaryDao.save(entity);
       
    }

    /**
     * 修改
     *
     * @param entity
     * @param user
     */
    public void modify(Dictionary entity) {
       
        dictionaryDao.save(entity);
    }

    /**
     * get(根据ID查询字典信息)
     *
     * @param id 主键id
     * @return Dictionary返回类型
     */
    public Dictionary get(Long id) {
    	String hql = "select dictionary from Dictionary dictionary where dictionary.id = ?";
        return dictionaryDao.findUnique(hql, id);
    }

    public String getDictJsonData() {
        List<Dictionary> dicLst = dictionaryDao.find("select dictionary from Dictionary dictionary where dictionary.dicType is not null");
        StringBuffer jsonData = new StringBuffer("[");
        int index = 0;
        for (Dictionary item : dicLst) {
            jsonData.append(
                    String.format("{id:%s,pId:%s,name:'%s',typename:'%s'}%s",
                            new Object[]{item.getId(),
                                    (null == item.getParent() ? "0" : item.getParent().getId()),
                                    item.getDicKey(),
                                    item.getDicType(),
                                    (++index < dicLst.size() ? "," : "")
                            }
                    )
            );
        }
        jsonData.append("]");
        return jsonData.toString();
    }



    /**
     * get(获取所有的字典类型)
     *
     * @return List<Dictionary>返回类型
     */
    public List<Dictionary> getDicTypes() {
        String hql = "select dictionary from Dictionary dictionary where dictionary.type is null";
        List<Dictionary> lst = dictionaryDao.find(hql);

        return lst;
    }

    /**
     * 获取默认的字典通过字典值
     * 1.仅查出id用于进行判断
     */
    public Long getIdByValue(String value) {
        return dictionaryDao.getIdByValue(value);
    }

    /**
     * 获取字典通过字典值
     */
    @Transactional(readOnly = true)
    public Dictionary getByValue(String value) {
        return dictionaryDao.getByValue(value);
    }

    /**
     * 获取字典列表通过父节点字典值
     */
    @Transactional(readOnly = true)
    public List<Dictionary> findChildren(String parentValue) {
        return dictionaryDao.findChildren(parentValue);
    }


    /**
     * 获取自己和兄弟节点
     */
    @Transactional(readOnly = true)
    public List<Dictionary> findBrother(String value) {
        return dictionaryDao.findBrother(value);
    }

    /**
     * findChildren(获取字典集合)
     *
     * @param dicType 字典常量值DictionaryConstant
     * @param bool    是否深度查找  (默认全部)
     * @return List<Dictionary> 字典集合
     * @Description: 根据大分类获取小分类集合
     */
    public List<Dictionary> findChildren(String dicType, Boolean bool) {
        String hql = "from Dictionary dic where dic.type = ? and dic.level = 1";
        if (bool) {
            hql = "from Dictionary dic where dic.type = ?";
        }
        return dictionaryDao.find(hql, dicType);
    }

    
    public String getNameById(Long id) {
        return dictionaryDao.load(Dictionary.class, id).getDicKey();
    }

    /**
     * 根据parentId 和 指定的key获取value
     *
     * @param key
     * @param parentId
     * @return
     */
    public Dictionary getValue(String key, Long parentId) {
        return dictionaryDao.getValue(key, parentId);
    }
    
    /**
     * 根据parentId 和 指定的key获取value
     *
     * @param key
     * @param parentId
     * @return
     */
    public Dictionary getValueByType(String key, String type) {
        return dictionaryDao.getValueByType(key, type);
    }


}
