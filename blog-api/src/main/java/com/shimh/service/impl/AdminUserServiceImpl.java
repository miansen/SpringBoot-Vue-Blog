package com.shimh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shimh.entity.AdminUser;
import com.shimh.repository.AdminUserRepository;
import com.shimh.service.AdminUserService;

/**
 * @author miansen.wang
 * @date 2020-04-17
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Override
	public AdminUser getByUsername(String username) {
		return adminUserRepository.findByUsername(username);
	}

}
