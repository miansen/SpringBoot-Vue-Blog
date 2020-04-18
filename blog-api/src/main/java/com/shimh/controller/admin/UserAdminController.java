package com.shimh.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;

import com.shimh.common.annotation.LogAnnotation;
import com.shimh.common.constant.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.entity.User;
import com.shimh.service.ArticleAdminService;
import com.shimh.service.UserAdminService;
import com.shimh.vo.PageVo;
import com.shimh.vo.UserAdminVo;

/**
 * 用户管理 API
 * 
 * @author miansen.wang
 * @date 2020-04-16
 */
@RestController
@RequestMapping(value = "/admin/user")
public class UserAdminController {

	@Autowired
    private UserAdminService userAdminService;
	
	@PostMapping("/save")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "用户管理", operation = "添加用户")
	public Result save(@RequestBody User user) {
		userAdminService.save(user);
		Result result = Result.success();
		result.simple().put("id", user.getId());
		return result;
	}
	
	@PostMapping("/update")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "用户管理", operation = "更新用户")
	public Result update(@RequestBody User user) {
		userAdminService.update(user);
		Result result = Result.success();
		result.simple().put("id", user.getId());
		return result;
	}
	
	@GetMapping(value = "/delete")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "用户管理", operation = "删除用户")
	public Result delete(@RequestParam(name = "id") Long id) {
		userAdminService.remove(id);
		return Result.success();
	}
	
	@GetMapping(value = "/delete/batch")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "用户管理", operation = "批量删除用户")
	public Result batchDelete(@RequestParam(name = "ids") Long[] ids) {
		userAdminService.remove(ids);
		return Result.success();
	}

	@GetMapping(value = "/list")
	@RequiresRoles(value = "admin")
	@FastJsonView(exclude = {@FastJsonFilter(clazz = User.class, props = {"password"})})
	@LogAnnotation(module = "用户管理", operation = "查询用户")
	public Result list(UserAdminVo userAdminVo, PageVo pageVo) {
		Page<User> page = userAdminService.page(userAdminVo, pageVo);
		return Result.success(page);
	}
}
