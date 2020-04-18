package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shimh.entity.Category;

/**
 * 分类管理 Repository
 * 
 * @author miansen.wang
 * @date 2020-04-19
 */
public interface CategoryAdminRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

}
