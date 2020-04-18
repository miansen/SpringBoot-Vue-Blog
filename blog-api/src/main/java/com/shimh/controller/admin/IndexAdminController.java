package com.shimh.controller.admin;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shimh.common.annotation.LogAnnotation;
import com.shimh.common.constant.Base;
import com.shimh.common.constant.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.entity.AdminUser;
import com.shimh.oauth.OAuthAdminRealm;
import com.shimh.oauth.OAuthSessionManager;
import com.shimh.oauth.OAuthUsernamePasswordToken;
import com.shimh.service.AdminUserService;

/**
 * 后台登录 API
 * 
 * @author miansen.wang
 * @date 2020-04-18
 */
@RestController
@RequestMapping(value = "/admin")
public class IndexAdminController {

	@Autowired
	private AdminUserService adminUserService;

	@PostMapping("/login")
    @LogAnnotation(module = "后台管理", operation = "后台登录")
	public Result login(@RequestBody AdminUser adminUser) {
		String username = adminUser.getUsername();
		String password = adminUser.getPassword();
		Result result = new Result();
		Subject subject = SecurityUtils.getSubject();
		Collection<Class<? extends Realm>> realmClassList = new ArrayList<>();
		realmClassList.add(OAuthAdminRealm.class);
		OAuthUsernamePasswordToken token = new OAuthUsernamePasswordToken(username, password, realmClassList);
		try {
			subject.login(token);
			adminUser = adminUserService.getByUsername(username);
			subject.getSession().setAttribute(Base.CURRENT_ADMIN_USER, adminUser);
			result.setResultCode(ResultCode.SUCCESS);
			result.simple().put("token", subject.getSession().getId());
		} catch (UnknownAccountException e) {
			result.setResultCode(ResultCode.USER_NOT_EXIST);
		} catch (AuthenticationException e) {
			result.setResultCode(ResultCode.USER_LOGIN_ERROR);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR);
		}
		return result;
	}
}
