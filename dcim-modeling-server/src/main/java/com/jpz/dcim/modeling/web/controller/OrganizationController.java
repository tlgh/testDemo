package com.jpz.dcim.modeling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.OrganizationService;
import com.jpz.dcim.modeling.service.UserService;

import pers.ksy.common.annotation.SerializationFilter;
import pers.ksy.common.annotation.SerializationFilters;
import pers.ksy.common.model.Result;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;

	@Deprecated
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@SerializationFilter(exclusive = false, target = User.class, fields = { "id", "name" })
	public Object list() {
		return Result.successResult(organizationService.findAll(), null);
	}

	@RequestMapping(path = "/tree", method = RequestMethod.GET)
	@SerializationFilters(filters = {
			@SerializationFilter(target = Organization.class, fields = { "parent", "members" }),
			@SerializationFilter(exclusive = false, target = User.class, fields = { "id", "name" }) })
	public Object tree() {
		return Result.successResult(organizationService.getRoot(), null);
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.GET)
	@SerializationFilters(filters = {
			@SerializationFilter(target = Organization.class, fields = { "children", "members" }),
			@SerializationFilter(target = User.class, exclusive = false, fields = { "id", "name" }) })
	public Object get(@PathVariable String organizationId) {
		Organization organization = organizationService.get(organizationId);
		organization.setParent(new Organization(organization.getParent().getId()));
		return Result.successResult(organization, null);
	}

	@RequestMapping(path = "/{organizationId}/users", method = RequestMethod.GET)
	@SerializationFilter(target = User.class, fields = { "organization", "password", "roles" })
	public Object users(@PathVariable String organizationId) {
		return Result.successResult(userService.getUsersInOrg(organizationId), null);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Object save(@RequestBody Organization organization) {
		organization = organizationService.addOrg(organization, organization.getParent().getId());
		return Result.successResult(organization.getId(), "新增成功");
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.PUT)
	public Object update(@PathVariable String organizationId, @RequestBody Organization organization) {
		organization.setId(organizationId);
		organizationService.update(organization);
		return Result.successResult("更新成功");
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable String organizationId) {
		organizationService.deleteById(organizationId);
		return Result.successResult("删除成功");
	}
}
