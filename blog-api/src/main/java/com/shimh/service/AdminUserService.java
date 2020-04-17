package com.shimh.service;

import com.shimh.entity.AdminUser;

/**
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface AdminUserService {

	AdminUser getByUsername(String username);
}
