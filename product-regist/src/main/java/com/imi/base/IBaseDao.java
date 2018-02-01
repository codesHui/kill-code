package com.imi.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;
/**
 * 数据操作接口。
 * @author josh.
 * @since 2014-04-28.
 */
public interface IBaseDao<T> {
	/**
	 * 加载数据对象。
	 * @param c
	 * 	数据对象类型。
	 * @param id
	 * 	数据对象主键。
	 * @return 对象。
	 * */
	T load(Class<T> c,Serializable id);
	/**
	 * 保存数据对象。
	 * @param data
	 * 	数据对象。
	 * @return 主键值。
	 * */
	Serializable save(T data);
	/**
	 * 更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void update(T data);
	/**
	 * 保存或更新数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void saveOrUpdate(T data);
	/**
	 * 删除数据对象。
	 * @param data
	 * 	数据对象。
	 * */
	void delete(T data);
	/**
	 * 手动清除实体对象的二级缓存。
	 * @param persistentClass
	 * 实体对象类型。
	 */
	void evict(Class<?> persistentClass);
	/**
	 * 对象状态融合。
	 * @param obj
	 */
	void merge(Object obj);
	
//=========================================Hibernate 扩展方法=======================================================================

    /**
     * 获取当前会话
     *
     * @return
     */
    public Session getSession();
    
    

    /**
     * Flush当前Session.
     */
    public void flush();
    
    
    /**
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
     * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy);

    
    /**
     * 根据查询语句和条件创建一个查询
     *
     * @param queryString
     * @param values
     * @return
     */
    public Query createQuery(final String queryString,
                             final Object... values);

    /**
     * 根据查询语句创建一个查询
     *
     * @param queryString
     * @return
     */
    public Query createQuery(final String queryString);

    /**
     * 根据查询语句和条件创建一个查询
     *
     * @param queryString
     * @param values
     * @return
     */
    public Query createQuery(final String queryString,
                             final Map<String, ?> values);


    /**
     * 按sql查询列表创建SQLQuery对象.
     */
    public SQLQuery createSQLQuery(String sql);
    /**
     * 根据查询SQL与参数列表创建SQLQuery对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public SQLQuery createSQLQuery(final String queryString, final Object... values);

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions);

    /**
     * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query);

    /**
     * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria);
    
//  ======================Hibernate HQL 方法=====================================================================================
 
    /**
     * 按id获取对象.
     */
    public T get(final Long id);

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(final Collection<Long> ids) ;
    /**
     * 获取全部对象.
     */
    public List<T> getAll() ;
 
	
    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions);

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions);



    
    public int batchExecute(final String hql, final Object... values);

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values);

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    public <X> List<X> find(final DetachedCriteria criteria);
    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    public <X> X findUnique(final DetachedCriteria criteria);
    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String hql, final Object... values);

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values);
    
    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) ;

    // form S s where s.status=1
    public <X> X findUnique(String hql);

    public <X> X findSQLUnique(final String sql, final Object... values);

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values);
    
    public Integer executeUpdate(String hql,Map<String, Object> parameters);

}