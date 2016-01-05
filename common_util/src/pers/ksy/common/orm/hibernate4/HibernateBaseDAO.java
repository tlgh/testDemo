package pers.ksy.common.orm.hibernate4;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import pers.ksy.common.model.Page;
import pers.ksy.common.orm.jpa.JpaBaseDAO;

@SuppressWarnings("rawtypes")
public interface HibernateBaseDAO<T, ID extends Serializable> extends JpaBaseDAO<T, ID>{


	/**
	 * @see Session.get(Class,Serializable,LockMode)
	 * @param id 对象ID
	 * @param lock 是否锁定，使用LockMode.UPGRADE
	 * @return 持久化对象
	 */
	public T get(ID id, boolean lock);
	

	/**
	 * 通过ID查找对象,不锁定对象
	 * 
	 * @param id 记录的ID
	 * @return 实体对象
	 */
	public T load(ID id);

	/**
	 * @see Session.get(Class,Serializable,LockMode)
	 * @param id 对象ID
	 * @param lock 是否锁定，使用LockMode.UPGRADE
	 * @return 持久化对象
	 */
	public T load(ID id, boolean lock);

	
	/**
	 * 根绝ID使用hql删除记录
	 * @param id 记录ID
	 * @return 
	 */
	public boolean deleteByIdUseHql(ID id);


	/**
	 * 分页查询（重载）
	 * @param dc 查询条件
	 * @param pageIndex 当前页(0~n)
	 * @param pageSize 页面大小(每页最大记录数)
	 * @param isCacheable 是否启用缓存
	 * @return
	 */
	public Page<T> findByPage(DetachedCriteria dc, int pageIndex, int pageSize,
			boolean isCacheable);
	
	/**
	 * 获取当前DAO对应entity的DetachedCriteria
	 * @return
	 */
	public DetachedCriteria getDC();

	
	/**
	 * 使用DetachedCriteria进行list查询
	 * @param dc
	 * @return
	 */
	public List listByDC(DetachedCriteria dc);

	public List listByDC(DetachedCriteria dc, boolean isCacheable);

	@Deprecated
	public List listByDC(DetachedCriteria dc, String[] sels);

	public List listByDC(DetachedCriteria dc, int first, int max,
			boolean isCacheable);
	

	/**
	 * 使用DC查询唯一记录
	 * @param dc
	 * @return
	 */
	public Object uniqueByDC(DetachedCriteria dc);
	
	/**
	 * 使用DetachedCriteria查询记录总数Count(*)
	 * @param dc 属性值
	 * @return
	 */
	public Long countByDC(DetachedCriteria dc);
	
	/**
	 * 根据属性条件查询记录总数Count(*)
	 * @param propNames 属性名
	 * @param propVals 属性值
	 * @return
	 */
	public Long countByProperties(String[] propNames, Object[] propVals);
	
	
}
