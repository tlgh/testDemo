package com.jpz.dcim.modeling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.PartyService;

import pers.ksy.common.annotation.SerializationFilter;
import pers.ksy.common.annotation.SerializationFilters;
import pers.ksy.common.model.Result;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController extends BaseController {
	@Autowired
	private PartyService userService;

	@Deprecated
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@SerializationFilter(exclusive = false, target = User.class, fields = { "id", "name" })
	public Object list() {
		return Result.successResult(userService.organizationList(), null);
	}

	@RequestMapping(path = "/tree", method = RequestMethod.GET)
	@SerializationFilters(filters = {
			@SerializationFilter(target = Organization.class, fields = { "parent", "members" }),
			@SerializationFilter(exclusive = false, target = User.class, fields = { "id", "name" }) })
	public Object tree() {
		return Result.successResult(userService.organizationTree(), null);
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.GET)
	public Object get(@PathVariable String organizationId) {
		return Result.successResult(userService.getOrganization(organizationId), null);
	}

	@RequestMapping(path = "/{organizationId}/users", method = RequestMethod.GET)
	@SerializationFilter(target = User.class, fields = { "organization", "password", "roles" })
	public Object users(@PathVariable String organizationId) {
		return Result.successResult(userService.findUserByOrg(organizationId), null);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Object save(@RequestBody Organization organization) {
		userService.addOrganization(organization, organization.getParent().getId());
		return Result.successResult();
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.PUT)
	public Object update(@PathVariable String organizationId, Organization organization) {
		organization.setId(organizationId);
		userService.updateOrganization(organization);
		return Result.successResult();
	}

	@RequestMapping(path = "/{organizationId}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable String organizationId) {
		userService.deleteOrganization(organizationId);
		return Result.successResult();
	}
}
