package com.imi.base.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Cache;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LobHelper;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.imi.base.IBaseDao;
import com.imi.entity.setting.MemberUser;
import com.softvan.utils.ReflectionUtils;


/**
 * 数据操作接口实现类。
 */
@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> entityClass;
    /**
     * 用于Dao层子类的构造函数. 通过子类的泛型定义取得对象类型Class.
     * 例如：
     * public class UserDao extends HibernateDao<User, Long>{}
     */
    public BaseDaoImpl() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	/**
	 * 设置SessionFactory。
	 * @param sessionFactory
	 * 	Hibernate Session 对象。
	 * */
	public void setSessionFactory(SessionFactory sessionFactory) {
		if(logger.isDebugEnabled()) logger.debug("注入sessionFactory...");
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取当前会话。
	 * @return 当前会话。
	 * */
	protected Session getCurrentSession(){
		if(logger.isDebugEnabled()) logger.debug("获取当前会话对象...");
		return this.sessionFactory == null ? null : this.sessionFactory.getCurrentSession();
	}
	/**
	 * 加载指定主键对象。
	 * @param c
	 * 	对象类型。
	 * @param id
	 * 	主键值。
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<T> c, Serializable id) {
		if(logger.isDebugEnabled()) logger.debug("加载指定主键对象...");
		if(c != null && id != null){
			return (T)this.getCurrentSession().get(c, id);
		}
		return null;
	}
	/**
	 * 保存新增对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public Serializable save(T data) {
		if(logger.isDebugEnabled()) logger.debug("保存新增对象...");
		if(data != null){
			return this.getCurrentSession().save(data);
		}
		return null;
	}
	/**
	 * 更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void update(T data) {
		if(logger.isDebugEnabled()) logger.debug("更新对象...");
		if(data != null){
			this.getCurrentSession().update(data);
		}
	}
	/**
	 * 保存或更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void saveOrUpdate(T data) {
		if(logger.isDebugEnabled()) logger.debug("保存或更新对象...");
		if(data != null) {
			this.getCurrentSession().saveOrUpdate(data);
		}
	}
	/**
	 * 删除对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void delete(T data) {
		if(logger.isDebugEnabled()) logger.debug("删除对象...");
		if(data != null){
			this.getCurrentSession().delete(data);
		}
	}
	/**
	 * 查找对象集合。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @param page
	 * 	页码。
	 * @param rows
	 * 	页数据量
	 * <pre>
	 * 	当page与rows同时为null时，则查询全部数据。
	 * </pre>
	 * @return 结果数据集合。
	 * */
	@SuppressWarnings("unchecked") 
	protected List<T> find(String hql, Map<String, Object> parameters,Integer page, Integer rows) {
		if(logger.isDebugEnabled()) logger.debug("查找对象集合...");
		return this.query(hql, parameters, page, rows);
	}
	
	/**
	 *  查询数据集合。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @param page
	 * 页码。
	 * @param rows
	 * 页数据量。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected List query(String hql, Map<String, Object> parameters,Integer page, Integer rows){
		if(logger.isDebugEnabled()) logger.debug(String.format("查询数据［hql = %1$s］［page = %2$d］［rows = %3$d］...", hql, page, rows));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			if(page == null && rows == null) return query.list();
			if(page == null || page < 1) page = 1;
			//2015.02.03 rows默认10,修改为5
			if(rows == null || rows < 5) rows = 5;
			return  query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		return null;
	}

	/**
	 * 添加参数集合。
	 * @param query
	 * @param parameters
	 */
	protected void addParameters(Query query, Map<String, Object> parameters){
		this.addParameters(query, parameters, true);
	}
	/**
	 * 添加参数集合。
	 * @param query
	 * 查询对象。
	 * @param parameters
	 * 参数集合。
	 * @param hasCache
	 * 是否使用二级缓存中的查询缓存。
	 */
	protected void addParameters(Query query, Map<String, Object> parameters,boolean hasCache){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加参数集合［hasCache = %s］...", hasCache));
		if(query == null || parameters == null || parameters.size() == 0) return;
		Object value = null;
		for(Map.Entry<String, Object> entry : parameters.entrySet()){
			if(entry == null || StringUtils.isEmpty(entry.getKey()) || (value = entry.getValue()) == null) continue;
			if(value.getClass().isArray()){
				query.setParameterList(entry.getKey(), (Object[])value);
			}else if(value instanceof Collection){
				query.setParameterList(entry.getKey(), (Collection<?>)value);
			}else {
				query.setParameter(entry.getKey(), value);
			}
		}
		if(hasCache){
			query.setCacheable(true);//是否启用缓存。
		}else {
			query.setCacheable(false);
			query.setCacheMode(CacheMode.IGNORE);
		}
	}
	/**
	 * 执行HQL语句。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @return
	 * 执行数据条数。
	 */
	public Integer executeUpdate(String hql,Map<String, Object> parameters){
		if(logger.isDebugEnabled()) logger.debug(String.format("执行HQL语句［%s］...", hql));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.executeUpdate();
		}
		return null;
	}
	/**
	 * 统计数据总数。
	 * @param hql
	 *  HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @return 数据总数。
	 * */
	protected Long count(String hql, Map<String, Object> parameters) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计数据［hql = %s］", hql));
		 Object  obj = this.uniqueResult(hql, parameters);
		 return obj == null ? null : (Long)obj;
	}
	/**
	 * 查询唯一结果数据。
	 * @param hql
	 * HQL语句。
	 * @param parameters
	 * 参数集合。
	 * @return
	 * 结果数据。
	 */
	protected Object uniqueResult(String hql,Map<String, Object> parameters){
		if(logger.isDebugEnabled()) logger.debug(String.format("查询单个结果数据［hql = %s］", hql));
		if(StringUtils.isEmpty(hql)) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return query.uniqueResult();
		}
		return null;
	}
	/*
	 * 手动清除二级缓存。
	 * @see com.imi.base.dao.IBaseDao#evict(java.lang.Class)
	 */
	@Override
	public void evict(Class<?> persistentClass) {
		if(logger.isDebugEnabled()) logger.debug("手动清除二级缓存...");
		if(persistentClass != null && this.sessionFactory != null){
			Cache cache = this.sessionFactory.getCache();
			if(cache != null) cache.evictEntityRegion(persistentClass);
		}
	}
	/*
	 * 对象状态融合。
	 * @see com.imi.base.dao.IBaseDao#merge(java.lang.Object)
	 */
	@Override
	public void merge(Object obj) {
		if(logger.isDebugEnabled()) logger.debug("对象状态融合...");
		this.getCurrentSession().merge(obj);
	}
	/**
	 * 获取二进制数据工具对象。
	 * @return
	 */
	protected LobHelper getLobHelper() {
		if(logger.isDebugEnabled()) logger.debug("获取二进制数据工具对象...");
		return this.getCurrentSession().getLobHelper();
	}
	

//=====================================================================	
	   /**
     * 按id获取对象.
     */
    public T get(final Long id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(final Collection<Long> ids) {
        return find(Restrictions.in("id", ids));
    }
	
    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return find();
    }

    /**
     * 获取全部对象, 支持按属性行序.
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按sql查询列表创建SQLQuery对象.
     */
    public SQLQuery createSQLQuery(String sql) {
        return this.getSession().createSQLQuery(sql);
    }

    /**
     * 根据查询SQL与参数列表创建SQLQuery对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public SQLQuery createSQLQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        SQLQuery query = getSession().createSQLQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    @SuppressWarnings("unchecked")
	public <X> List<X> find(final DetachedCriteria criteria, int limit) {
        Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
        executableCriteria.setMaxResults(limit);
        return (List<X>) executableCriteria.list();
    }

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    @SuppressWarnings("unchecked")
	public <X> List<X> find(final DetachedCriteria criteria) {
        return (List<X>) criteria.getExecutableCriteria(getSession()).list();
    }

    /**
     * 离线查询.
     *
     * @param criteria 离线查询条件
     */
    @SuppressWarnings("unchecked")
	public <X> X findUnique(final DetachedCriteria criteria) {
        return (X) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    @SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    @SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    @SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    // form S s where s.status=1
    @SuppressWarnings("unchecked")
	public <X> X findUnique(String hql) {
        return (X) createQuery(hql).uniqueResult();
    }

    @SuppressWarnings("unchecked")
	public <X> X findSQLUnique(final String sql, final Object... values) {
        return (X) createSQLQuery(sql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public Query createQuery(final String queryString) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString,
                             final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        // criteria.setCacheable(true);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
     * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }
    

   
}