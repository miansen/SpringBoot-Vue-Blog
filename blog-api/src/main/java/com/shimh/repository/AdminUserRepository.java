package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shimh.entity.AdminUser;

/**
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

	AdminUser findByUsername(String username);
}
