package com.jpz.dcim.modeling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpz.dcim.modeling.model.dao.BaseDao;
import com.jpz.dcim.modeling.service.BaseService;

public class BaseServiceImpl implements BaseService {
	@Autowired
	BaseDao baseDao;
	
	
}
