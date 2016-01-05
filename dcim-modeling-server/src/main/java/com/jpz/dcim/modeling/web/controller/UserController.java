package com.jpz.dcim.modeling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pers.ksy.common.annotation.SerializationFilter;
import pers.ksy.common.annotation.SerializationFilters;
import pers.ksy.common.model.Result;
import pers.ksy.common.orm.Conditions;
import pers.ksy.common.orm.MatchMode;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;
import com.jpz.dcim.modeling.service.PartyService;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	@Autowired
	private PartyService userService;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@SerializationFilters(filters = { @SerializationFilter(target = User.class, fields = { "password", "roles" }),
			@SerializationFilter(target = Organization.class, exclusive = false, fields = { "id", "name" }) })
	public Object login(@RequestParam String username, @RequestParam String password) {
		return Result.successResult(userService.login(username, password), null);
	}

	@RequestMapping(path = "/page", method = RequestMethod.GET)
	@SerializationFilter(target = User.class, fields = { "organization", "password" })
	public Object page(String organizationId, String name, int pageIndex, int pageSize) {
		QueryCondition queryCondition = new QueryConditionImpl(User.class, null);
		if (null != organizationId) {
			queryCondition.add(Conditions.eq("organization.id", organizationId));
		}
		if (null != name) {
			queryCondition.add(Conditions.like("name", name, MatchMode.ANYWHERE));
		}
		return userService.findPage(pageIndex, pageSize, queryCondition);
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.GET)
	public Object get(@PathVariable String userId) {
		return Result.successResult(userService.getUser(userId), null);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Object save(@RequestBody User user) {
		userService.addUser(user, user.getOrganization().getId());
		return Result.successResult("新增成功");
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public Object update(@PathVariable String userId, User user) {
		user.setId(userId);
		userService.updateUser(user);
		return Result.successResult("更新成功");
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable String userId) {
		userService.deleteUser(userId);
		return Result.successResult("删除成功");
	}
}
