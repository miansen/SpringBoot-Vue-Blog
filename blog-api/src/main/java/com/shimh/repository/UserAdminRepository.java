package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shimh.entity.User;

/**
 * @author miansen.wang
 * @date 2020-04-18
 */
public interface UserAdminRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
