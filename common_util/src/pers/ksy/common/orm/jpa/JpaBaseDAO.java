package pers.ksy.common.orm.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.Order;
import pers.ksy.common.orm.QueryCondition;

@SuppressWarnings("rawtypes")
public interface JpaBaseDAO<T, ID extends Serializable> {

	/**
	 * @see Session.get(Class,Serializable)
	 * @param id
	 * @return 持久化对象
	 */
	public T get(ID id);


	/**
	 * 保存对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	public T save(T t);
	
	/**
	 * 保存对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	public Serializable saveAny(Serializable t) ;

	/**
	 * 更新对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	public T update(T entity);

	/**
	 * 保存或更新对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	public T saveOrUpdate(T entity);

	/**
	 * 删除对象
	 * 
	 * @param entity 实体对象
	 */
	public T delete(T entity);

	/**
	 * 根据ID删除记录
	 * 
	 * @param id 记录ID
	 */
	public T deleteById(ID id);
	


	/**
	 * 刷新对象
	 * 
	 * @param entity
	 */
	public void refresh(Object entity);

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<T> findByPage(int pageIndex, int pageSize, Order[] orders);
	
	/**
	 * 分页查询
	 * 
	 * @param pageIndex 当前页(0~n)
	 * @param pageSize 页面大小(每页最大记录数)
	 * @param isCacheable 是否启用缓存
	 * @param eqConditions equals查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param likeConditions like查询条件(key:hibernate entity 属性名,value 属性值)
	 * @param orders  排序字段
	 * @return 页面查询结果封装对象
	 */
	public Page<T> findByPage(int pageIndex, int pageSize,
			Map<String, Object> eqConditions,
			Map<String, String> likeConditions, Order[] orders);

	/**
	 * 分页查询（重载）
	 * @param dc 查询条件
	 * @param pageIndex 当前页(0~n)
	 * @param pageSize 页面大小(每页最大记录数)
	 * @param isCacheable 是否启用缓存
	 * @return
	 */
	public Page<T> findByPage(QueryCondition qc, int pageIndex, int pageSize);
	
	/**
	 * 获取当前DAO对应entity的DetachedCriteria
	 * @return
	 */
	public QueryCondition getQC();

	/**
	 * 查询所有记录
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 查询所有记录
	 * @return
	 */
	public List<T> findAll(String[] joins);
	
	/**
	 * 单属性查询列表
	 * @param propName 属性名
	 * @param propVal 属性值
	 * @return
	 */
	public List<T> findByProperty(String propName,Object propVal);
	
	/**
	 * 单属性查询列表
	 * @param propName 属性名
	 * @param propVal 属性值
	 * @param joins 级联查询的字段
	 * @return
	 */
	public List<T> findByProperty(String propName,Object propVal,String[] joins);
	
	/**
	 * 单属性查询列表
	 * @param propNames 属性名
	 * @param propVals 属性值
	 * @param joins 级联查询的字段
	 * @return
	 */
	public List<T> findByProperty(String[] propNames,Object[] propVals,String[] joins);
	
	/**
	 * 单属性查询唯一结果
	 * @param propName 属性名
	 * @param propVal 属性值
	 * @return
	 */
	public T getByProperty(String propName,Object propVal);
	
	/**
	 * 单属性查询唯一结果
	 * @param propName 属性名
	 * @param propVal 属性值
	 * @param joins 级联查询的字段
	 * @return
	 */
	public T getByProperty(String propName,Object propVal,String[] joins);
	
	/**
	 * 根据ID更新字段
	 * @param updateSet 需要更新的字段名及值 key-value
	 * @param id
	 * @return
	 */
	public int updatePropertiesById(Map<String, Object> updateSet, ID id);
	
	/**
	 * 根据条件更新字段
	 * @param updateSet
	 * @param updateWhere
	 * @return
	 */
	public int updateProperties(Map<String, Object> updateSet,Map<String,Object> updateWhere);
	
	/**
	 * 使用DetachedCriteria进行list查询
	 * @param dc
	 * @return
	 */
	public List listByQC(QueryCondition qc);

	public List listByQC(QueryCondition qc, int first, int max);
	

	/**
	 * 使用DC查询唯一记录
	 * @param dc
	 * @return
	 */
	public Object uniqueByQC(QueryCondition qc);
	
	/**
	 * 使用DetachedCriteria查询记录总数Count(*)
	 * @param dc 属性值
	 * @return
	 */
	public Long countByQC(QueryCondition qc);
	
	/**
	 * 根据属性条件查询记录总数Count(*)
	 * @param propNames 属性名
	 * @param propVals 属性值
	 * @return
	 */
	public Long countByProperties(String[] propNames,Object[] propVals);
	
	/**
	 * 清空当前类型实体
	 */
	public void deleteAll();
	
	
}
