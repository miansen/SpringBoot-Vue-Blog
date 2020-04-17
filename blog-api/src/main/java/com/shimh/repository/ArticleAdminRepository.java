package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shimh.entity.Article;

/**
 * 文章管理 Repository
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface ArticleAdminRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

}
