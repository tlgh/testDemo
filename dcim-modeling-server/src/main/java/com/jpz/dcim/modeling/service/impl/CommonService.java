package com.jpz.dcim.modeling.service.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jpz.dcim.modeling.exception.ServiceException;
import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.model.entity.BaseEntity;

import pers.ksy.common.ArrayUtil;
import pers.ksy.common.StringUtil;
import pers.ksy.common.spring4.SpringUtil;

@Transactional
public abstract class CommonService {

	protected <T extends BaseEntity> BaseEntity save(T baseEntity) {
		baseEntity.setCreateTime(new Date());
		getDao(baseEntity.getClass()).save(baseEntity);
		return baseEntity;
	}

	protected <T extends BaseEntity> BaseEntity update(T baseEntity) {
		return update(baseEntity, null, null);
	}

	protected <T extends BaseEntity> BaseEntity update(T baseEntity, String[] properties, Boolean inclusive) {
		Assert.notNull(baseEntity.getId());
		String[] fields = new String[] { "id", "createTime" };
		if (null != properties) {
			fields = ArrayUtil.addAll(fields, properties);
		}
		if (inclusive == null) {
			inclusive = false;
		}
		BaseDao<BaseEntity, String> dao = getDao(baseEntity.getClass());
		if (null != fields) {
			Class<? extends BaseEntity> clz = baseEntity.getClass();
			T old = (T) dao.get(baseEntity.getId());
			if (null == old) {
				throw new ServiceException(baseEntity.getClass().getSimpleName() + "不存在");
			}
			for (Field field : clz.getDeclaredFields()) {
				boolean inIn = ArrayUtil.indexOf(fields, field.getName()) > 0;
				if ((inIn && inclusive) || (!inIn && !inclusive)) {
					field.setAccessible(true);
					try {
						field.set(old, field.get(baseEntity));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			baseEntity = old;
		}
		baseEntity.setLastModifyTime(new Date());
		dao.update(baseEntity);
		return baseEntity;
	}

	protected <T extends BaseEntity> BaseEntity saveOrUpdate(T baseEntity) {
		return saveOrUpdate(baseEntity, null, null);
	}

	protected <T extends BaseEntity> BaseEntity saveOrUpdate(T baseEntity, String[] fields, Boolean inclusive) {
		if (StringUtil.notEmpty(baseEntity.getId())) {
			update(baseEntity, fields, inclusive);
		} else {
			save(baseEntity);
		}
		return baseEntity;
	}

	public BaseDao<BaseEntity, String> getDao(Class<? extends BaseEntity> t) {
		try {
			Class<? extends BaseDao<? extends BaseEntity, String>> clz = (Class<? extends BaseDao<? extends BaseEntity, String>>) Class
					.forName("com.jpz.dcim.modeling.model.dao." + t.getSimpleName() + "Dao");
			return (BaseDao<BaseEntity, String>) SpringUtil.getBean(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println((false && false));
	}
}
