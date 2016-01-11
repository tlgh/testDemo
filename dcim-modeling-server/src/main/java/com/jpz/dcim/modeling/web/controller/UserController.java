package com.jpz.dcim.modeling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpz.dcim.modeling.model.entity.Organization;
import com.jpz.dcim.modeling.model.entity.User;

import pers.ksy.common.annotation.SerializationFilter;
import pers.ksy.common.annotation.SerializationFilters;
import pers.ksy.common.model.Result;
import pers.ksy.common.orm.Conditions;
import pers.ksy.common.orm.MatchMode;
import pers.ksy.common.orm.QueryCondition;
import pers.ksy.common.orm.QueryConditionImpl;
import com.jpz.dcim.modeling.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

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

		return userService.findByPage(queryCondition, pageIndex, pageSize);
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.GET)
	@SerializationFilters(filters = { @SerializationFilter(target = User.class, fields = { "password", "roles" }),
			@SerializationFilter(target = Organization.class, exclusive = false, fields = { "id", "name" }) })
	public Object get(@PathVariable String userId) {
		return Result.successResult(userService.get(userId), null);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Object save(@RequestBody User user) {
		user = userService.addUser(user, user.getOrganization().getId());
		return Result.successResult(user.getId(), "新增成功");
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public Object update(@PathVariable String userId, @RequestBody User user) {
		user.setId(userId);
		userService.update(user);
		return Result.successResult("更新成功");
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable String userId) {
		userService.deleteById(userId);
		return Result.successResult("删除成功");
	}
}
