package com.shimh.service;

import org.springframework.data.domain.Page;

import com.shimh.entity.User;
import com.shimh.vo.PageVo;
import com.shimh.vo.UserAdminVo;

/**
 * 用户管理 Service 接口
 * 
 * @author miansen.wang
 * @date 2020-04-18
 */
public interface UserAdminService {

	void save(User user);

	void update(User user);

	void remove(Long id);

	void remove(Long[] ids);
	
	User getbyId(Long id);

	Page<User> page(final UserAdminVo articleVo, final PageVo pageVo);

}
