package com.jpz.dcim.modeling.service;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;

public interface OrganizationService extends BaseService<Organization,String>{
	
	/**
	 * 增加一个部门作为现有部门的子部门
	 * @param org
	 * @param parentId
	 * @return
	 */
	public Organization addOrg(Organization org,String parentId);
	
	
	/**
	 * 将一个部门移动到另一个部门之前
	 * @param org
	 * @param otherId
	 * @return
	 */
	public Organization moveOrgBefore(Organization org,String otherId);
	
	/**
	 * 移动当前部门到最后的位置
	 * @param org
	 * @return
	 */
	public Organization moveToLast(Organization org);
	
	/**
	 * 获取机构目录根节点
	 * @return
	 */
	public Organization getRoot();


	
	

}
